

package com.gbps.webex.core;

import com.gbps.webex.entity.GbpsResponse;
import com.gbps.webex.entity.GbpsResponseCode;

import com.gbps.webex.exception.GbpsBusinessException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ExceptionHandlerManager {

    private final Map<Class<? extends Throwable>, ExceptionTrans> registerExceptionTransMap = new HashMap<>();
    private final ExceptionHandlerRegistry defaultRegistry = new ExceptionHandlerRegistry();

    private final Map<Class<? extends Throwable>, ExceptionUnPacker> registerExceptionUnPackersMap = new HashMap<>();

    public void init(ExceptionHandlerRegistry registry) {
        registerExceptionTransMap.clear();
        initDefaultRegistry();

        registerException(defaultRegistry);
        registerException(registry);

        registerUnPackers(registry);
    }

    public GbpsResponse handlerException(Throwable ex, boolean dev) {
        ExceptionTrans exceptionTrans = foundDetailCreator(ex);
        if (exceptionTrans == null) {
            return GbpsResponse.fail(GbpsResponseCode.INNER_SYSTEM_ERROR, "????????????");
        }
        return GbpsResponse.fail(
            exceptionTrans.getResponseCode(ex),
            exceptionTrans.createDetail(ex, dev)
        );
    }

    public Throwable transThrowable(Throwable ex) {
        if (registerExceptionUnPackersMap.isEmpty()) {
            return ex;
        }

        Class clazz = ex.getClass();
        ExceptionUnPacker exceptionUnPacker = null;
        while (exceptionUnPacker == null) {
            exceptionUnPacker = registerExceptionUnPackersMap.get(clazz);
            clazz = clazz.getSuperclass();
            if (clazz == null) {
                break;
            }
        }

        if (exceptionUnPacker == null) {
            return ex;
        }

        return exceptionUnPacker.unPack(ex);
    }

    private void registerException(ExceptionHandlerRegistry registry) {
        if (registry == null || registry.getRegisterExceptionTransMap() == null) {
            return;
        }
        registerExceptionTransMap.putAll(registry.getRegisterExceptionTransMap());
    }

    private void registerException(Class<? extends Throwable> exClazz, ExceptionTrans exceptionTrans) {
        registerExceptionTransMap.put(exClazz, exceptionTrans);
    }

    private void registerUnPackers(ExceptionHandlerRegistry registry) {
        if (registry == null || registry.getRegisterExceptionUnPackerMap() == null) {
            return;
        }
        registerExceptionUnPackersMap.putAll(registry.getRegisterExceptionUnPackerMap());
    }

    private ExceptionTrans foundDetailCreator(Throwable ex) {
        Class clazz = ex.getClass();
        ExceptionTrans exceptionTrans = null;
        while (exceptionTrans == null) {
            exceptionTrans = registerExceptionTransMap.get(clazz);
            clazz = clazz.getSuperclass();
            if (clazz == null) {
                break;
            }
        }
        return exceptionTrans;
    }

    private void initDefaultRegistry() {
        defaultRegistry.clear();
        registerDefaultInputErrorException();
        registerDefaultSystemInnerException();
        registerDefaultNotFoundException();

        //default shareBusinessException
        defaultRegistry.registerException(GbpsBusinessException.class,
            false,
            (GbpsBusinessException ex) -> ex.getResponseCode(),
            (GbpsBusinessException ex) -> ex.getMessage());

        defaultRegistry.registerException(Exception.class,
            true,
            GbpsResponseCode.INNER_SYSTEM_ERROR,
            ex -> "????????????");

        defaultRegistry.registerException(Throwable.class,
            true,
            GbpsResponseCode.INNER_SYSTEM_ERROR,
            ex -> "????????????");
    }

    /**
     * ????????????????????????????????????
     * <br/>{@link HttpMessageNotWritableException}
     * <br/>{@link MissingServletRequestPartException}
     * <br/>{@link AsyncRequestTimeoutException}
     */
    private void registerDefaultSystemInnerException() {
        // HttpMessageNotWritableException
        defaultRegistry.registerException(HttpMessageNotWritableException.class,
            true,
            GbpsResponseCode.INNER_SYSTEM_ERROR,
            ex -> "???????????????????????????");
        // MissingServletRequestPartException
        defaultRegistry.registerException(MissingServletRequestPartException.class,
            true,
            GbpsResponseCode.INNER_SYSTEM_ERROR,
            ex -> "???????????????????????????");
        // AsyncRequestTimeoutException
//        defaultRegistry.registerException(AsyncRequestTimeoutException.class,
//            true,
//            GbpsResponseCode.INNER_SYSTEM_ERROR,
//            ex -> "?????????????????????");
    }

    /**
     * ???????????????????????????????????????
     * {@link NoSuchRequestHandlingMethodException}
     * {@link HttpRequestMethodNotSupportedException}
     * {@link HttpMediaTypeNotSupportedException}
     * {@link HttpMediaTypeNotAcceptableException}
     * {@link NoHandlerFoundException}
     */
    private void registerDefaultNotFoundException() {
        // NoSuchRequestHandlingMethodException
        defaultRegistry.registerException(NoSuchRequestHandlingMethodException.class,
            false,
            GbpsResponseCode.NOT_FOUND,
            ex -> "????????????????????????" + ex.getMethodName());
        // HttpRequestMethodNotSupportedException
        defaultRegistry.registerException(HttpRequestMethodNotSupportedException.class,
            false,
            GbpsResponseCode.NOT_FOUND,
            ex -> "Http Method?????????????????????Method???"
                + Arrays.toString(ex.getSupportedMethods())
        );
        // HttpMediaTypeNotSupportedException
        defaultRegistry.registerException(HttpMediaTypeNotSupportedException.class,
            false,
            GbpsResponseCode.NOT_FOUND,
            ex -> "Http MediaType?????????");
        // HttpMediaTypeNotAcceptableException
        defaultRegistry.registerException(HttpMediaTypeNotAcceptableException.class,
            false,
            GbpsResponseCode.NOT_FOUND,
            ex -> "Http MediaType?????????");
        // NoHandlerFoundException
        defaultRegistry.registerException(NoHandlerFoundException.class,
            false,
            GbpsResponseCode.NOT_FOUND,
            ex -> "??????????????????" + ex.getRequestURL());
    }

    /**
     * ???????????????????????????????????????
     * {@link MissingPathVariableException}
     * {@link MissingServletRequestParameterException}
     * {@link ServletRequestBindingException}
     * {@link MethodArgumentConversionNotSupportedException}
     * {@link ConversionNotSupportedException}
     * {@link TypeMismatchException}
     * {@link HttpMessageNotReadableException}
     */
    private void registerDefaultInputErrorException() {
        // MissingPathVariableException
        defaultRegistry.registerException(MissingPathVariableException.class,
            false,
            GbpsResponseCode.INPUT_ILLEGAL,
            ex -> "??????????????????????????????????????????" + ex.getVariableName());
        // MissingServletRequestParameterException
        defaultRegistry.registerException(MissingServletRequestParameterException.class,
            false,
            GbpsResponseCode.INPUT_ILLEGAL,
            ex -> "????????????????????????????????????" + ex.getParameterName());
        // ServletRequestBindingException
        defaultRegistry.registerException(ServletRequestBindingException.class,
            false,
            GbpsResponseCode.INPUT_ILLEGAL,
            ex -> "ServletRequestBindingException");
        // MethodArgumentConversionNotSupportedException
        defaultRegistry.registerException(MethodArgumentConversionNotSupportedException.class,
            false,
            GbpsResponseCode.INPUT_ILLEGAL,
            ex -> String.format("?????????????????????????????????%s, ???????????????%s",
                ex.getName(),
                ex.getRequiredType().getSimpleName()));
        // ConversionNotSupportedException
        defaultRegistry.registerException(ConversionNotSupportedException.class,
            false,
            GbpsResponseCode.INPUT_ILLEGAL,
            ex -> String.format("?????????????????????????????????%s, ???????????????%s",
                ex.getPropertyName(),
                ex.getRequiredType().getSimpleName()));
        // MethodArgumentTypeMismatchException
        defaultRegistry.registerException(MethodArgumentTypeMismatchException.class,
            false,
            GbpsResponseCode.INPUT_ILLEGAL,
            ex -> String.format("?????????????????????????????????%s, ???????????????%s",
                ex.getName(),
                ex.getRequiredType().getSimpleName()));
        // TypeMismatchException
        defaultRegistry.registerException(TypeMismatchException.class,
            false,
            GbpsResponseCode.INPUT_ILLEGAL,
            ex -> String.format("?????????????????????????????????%s, ???????????????%s",
                ex.getPropertyName(),
                ex.getRequiredType().getSimpleName()));
        // HttpMessageNotReadableException
        defaultRegistry.registerException(HttpMessageNotReadableException.class,
            false,
            GbpsResponseCode.INPUT_ILLEGAL,
            ex -> "???????????????????????????");
    }

}

