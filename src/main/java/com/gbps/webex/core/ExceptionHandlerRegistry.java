

package com.gbps.webex.core;


import com.gbps.webex.entity.ResponseCode;

import java.util.HashMap;
import java.util.Map;


public class ExceptionHandlerRegistry {
    private Map<Class<? extends Throwable>, ExceptionTrans> registerExceptionTransMap = new HashMap<>();

    private Map<Class<? extends Throwable>, ExceptionUnPacker> registerExceptionUnPackerMap = new HashMap<>();

    public void clear() {
        registerExceptionTransMap.clear();
        registerExceptionUnPackerMap.clear();
    }

    public <T extends Throwable> void registerUnPacker(Class<T> exClazz, ExceptionUnPacker<T, ? extends Throwable> unPacker) {
        registerExceptionUnPackerMap.put(exClazz, unPacker);
    }

    /**
     * 注册异常信息。
     * @param exClazz 异常类
     * @param dumpIfDev 是否在开发模式时，返回堆栈信息。
     * @param codeCreator 错误码构造器，可以使用JDK8的lambda表达式
     * @param detailCreator 详细信息构造器，可以使用JDK8的lambda表达式
     */
    public <T extends Throwable> void registerException(Class<T> exClazz,
                                                        boolean dumpIfDev,
                                                        ExceptionTrans.CodeCreator<T> codeCreator,
                                                        ExceptionTrans.DetailCreator<T> detailCreator) {
        registerException(exClazz, new ExceptionTrans(dumpIfDev, codeCreator, detailCreator));
    }

    /**
     * 注册异常信息。
     * @param exClazz 异常类
     * @param dumpIfDev 是否在开发模式时，返回堆栈信息。
     * @param responseCode 错误码
     * @param detailCreator 详细信息构造器，可以使用JDK8的lambda表达式
     */
    public <T extends Throwable> void registerException(Class<T> exClazz,
                                                        boolean dumpIfDev,
                                                        ResponseCode responseCode,
                                                        ExceptionTrans.DetailCreator<T> detailCreator) {
        registerException(exClazz, dumpIfDev, ex -> responseCode, detailCreator);
    }

    private void registerException(Class<? extends Throwable> exClazz, ExceptionTrans exceptionTrans) {
        registerExceptionTransMap.put(exClazz, exceptionTrans);
    }

    Map<Class<? extends Throwable>, ExceptionTrans> getRegisterExceptionTransMap() {
        return registerExceptionTransMap;
    }


    Map<Class<? extends Throwable>, ExceptionUnPacker> getRegisterExceptionUnPackerMap() {
        return registerExceptionUnPackerMap;
    }


}

