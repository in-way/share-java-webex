

package com.gbps.webex.advice;

import com.gbps.webex.GbpsWebExConfigure;
import com.gbps.webex.core.ExceptionHandlerManager;
import com.gbps.webex.core.ValidHandlerManager;

import com.gbps.webex.entity.*;
import com.gbps.webex.log.WebExLogHandler;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import lombok.Data;


public abstract class AbstractShareExceptionHandlerAdvice implements ApplicationContextAware {

    @Autowired
    protected ValidHandlerManager validMessageManager;

    @Autowired
    protected ExceptionHandlerManager exceptionHandlerManager;

    @Autowired
    protected GbpsWebExConfigure config;

//    @Autowired
//    protected WebExLogHandler logHandler;

    private ApplicationContext applicationContext;

    private Set<HandlerMethodInfo> noJsonOutHandlers = new HashSet<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        applicationContext.getBeansOfType(RequestMappingHandlerMapping.class).values()
            .forEach(
                handlerMapping -> {
                    noJsonOutHandlers.addAll(
                        handlerMapping.getHandlerMethods().values()
                            .stream().filter(hm -> !isJsonResult(hm))
                            .map(HandlerMethodInfo::buildWith)
                            .collect(Collectors.toSet())
                    );
                });
    }


    @ExceptionHandler
    @ResponseBody
    public ResponseEntity handlerAllException(HttpServletRequest req, Throwable thr, HandlerMethod handlerMethod) {
        // trans Exception
        thr = transThrowable(thr);
        beforeHandleException(req, thr);

        HandlerMethodInfo handlerMethodInfo = HandlerMethodInfo.buildWith(handlerMethod);
        if (noJsonOutHandlers.contains(handlerMethodInfo)) {
            return ResponseEntity.badRequest().body(thr.getMessage());
        }
        return ResponseEntity.ok(afterHandleException(req, thr, innerHandleException(req, thr)));
    }

    protected final Throwable transThrowable(Throwable thr) {
        return exceptionHandlerManager.transThrowable(thr);
    }


    protected GbpsResponse handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                   HttpServletRequest request) {
        return GbpsResponse.fail(GbpsResponseCode.VALID_FIELD_ILLEGAL, convertValidDetail(ex.getBindingResult()));
    }

    protected GbpsResponse handlerBindException(BindException ex, HttpServletRequest request) {
        return GbpsResponse.fail(GbpsResponseCode.VALID_FIELD_ILLEGAL, convertValidDetail(ex.getBindingResult()));
    }

    /**
     * 异常处理前会执行。
     * @param req 当前的请求Request
     * @param ex 捕获的Exception
     */
    protected void beforeHandleException(HttpServletRequest req, Throwable ex) {

    }

    protected Optional<GbpsResponse> tryHandleException(HttpServletRequest req, Throwable ex) {
        return Optional.empty();
    }

    /**
     * 异常处理后会执行。
     * @param req 当前的请求Request
     * @param ex 捕获的Exception
     * @param GbpsResponse 返回的GbpsResponse
     * @return 加工后的返回结果
     */
    protected GbpsResponse afterHandleException(HttpServletRequest req, Throwable ex, GbpsResponse GbpsResponse) {
//        logHandler.logOnException(req, WebExReqAction.getWithRequest(req), GbpsResponse, ex);
        return GbpsResponse;
    }

    private final GbpsResponse innerHandleException(HttpServletRequest request, Throwable thr) {
        // 子类异常可以自行先处理。
        Optional<GbpsResponse> optional = tryHandleException(request, thr);
        if (optional.isPresent()) {
            return afterHandleException(request, thr, optional.get());
        }

        // 校验处理
        if (thr instanceof MethodArgumentNotValidException) {
            return handlerMethodArgumentNotValidException((MethodArgumentNotValidException) thr, request);
        }

        if (thr instanceof BindException) {
            return handlerBindException((BindException) thr, request);
        }

        return exceptionHandlerManager.handlerException(thr, config.isDev());
    }

    private List<ValidMessage> convertValidDetail(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
            .filter(e -> e instanceof FieldError)
            .map(
                e -> {
                    FieldError fe = (FieldError) e;
                    String message = fe.getDefaultMessage();
                    String validType = fe.getCode();
                    ResponseCode code = validMessageManager.loadValidResponseCode(validType);
                    return new ValidMessage(fe.getField(),
                        code.getCode(),
                        message);
                }
            ).collect(Collectors.toList());
    }

    private boolean isJsonResult(HandlerMethod handlerMethod) {
        MethodParameter retType = handlerMethod.getReturnType();

        Class retClazz = retType.getParameterType();

        if (retClazz == ResponseEntity.class || retClazz == HttpEntity.class) {
            ParameterizedType parameterizedType = (ParameterizedType) retType.getGenericParameterType();
            // if(genericParameterType instanceof Par)

            Type actualTypeArg = parameterizedType.getActualTypeArguments()[0];
            if (actualTypeArg instanceof Class) {
                retClazz = (Class) actualTypeArg;
            } else {
                return true;// 如果还带范型，那基本就不是Resource 和 Byte[] 了把
            }
        }

        return !(retClazz == byte[].class
            || Resource.class.isAssignableFrom(retClazz));

    }


    @Data
    private static class HandlerMethodInfo {
        private Method method;

        public static HandlerMethodInfo buildWith(HandlerMethod handlerMethod) {
            return new HandlerMethodInfo(handlerMethod.getMethod());
        }

        public HandlerMethodInfo(Method method) {
            this.method = method;
        }

    }

}

