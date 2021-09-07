/*
 * @(#) WebExLogHandler
 * 版权声明  , 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2017
 * <br> Company:
 * <br> @author  ivy
 * <br> 2017-10-11 09:50:51
 * ————————————————————————————————
 */

package com.gbps.webex.log;

import com.gbps.webex.entity.GbpsResponse;
import com.gbps.webex.entity.WebExReqAction;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by  ivy on 2017/10/11.
 */
public interface WebExLogHandler {

    /**
     * 异常日志打印Handler。
     * 函数内处理异常打印信息。
     * @param req 原始的httpRequest
     * @param action 封装后的action
     * @param response 返回数据的结构（ShareResponse）。警告：ShareResponse不是不可变对象，不推荐在函数内改变其内容。
     * @param ex 原始异常
     */
    void logOnException(HttpServletRequest req, WebExReqAction action, GbpsResponse response, Throwable ex);

    /**
     * action前日志打印Handler。
     * @param req 原始的httpRequest
     * @param action 封装后的action
     */
    void logOnPreAction(HttpServletRequest req, WebExReqAction action);

    /**
     * action后日志打印Handler。
     * @param req 原始的httpRequest
     * @param action 封装后的action
     * @param response 返回数据的结构（ShareResponse）。警告：ShareResponse不是不可变对象，不推荐在函数内改变其内容。
     */
    void logOnComplete(HttpServletRequest req, WebExReqAction action, GbpsResponse response);

}