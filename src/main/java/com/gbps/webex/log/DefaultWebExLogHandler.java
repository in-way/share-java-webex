/*
 * @(#) DefaultWebExLogHandler
 * 版权声明  , 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2017
 * <br> Company:
 * <br> @author  ivy
 * <br> 2017-10-11 09:54:20
 * ————————————————————————————————
 */

package com.gbps.webex.log;

import com.gbps.webex.entity.GbpsResponse;
import com.gbps.webex.entity.WebExReqAction;
import com.sunsharing.share.common.base.ExceptionUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;

/**
 * Created by  ivy on 2017/10/11.
 */
public final class DefaultWebExLogHandler implements WebExLogHandler {

    // 考虑兼容性问题，先使用Slf4j
    private Logger logger = LoggerFactory.getLogger(DefaultWebExLogHandler.class);

    @Override
    public void logOnException(HttpServletRequest req, WebExReqAction action, GbpsResponse response, Throwable ex) {
        // logger.
        logger.warn("action={}, 状态码={}, 异常信息：{}",
            new Object[]{action.getUrl(), response.getStatus(), ExceptionUtil.stackTraceText(ex)});
    }

    @Override
    public void logOnPreAction(HttpServletRequest req, WebExReqAction action) {
        logger.debug("action={} 准备执行，",
            new Object[]{action.getUrl()});
    }


    @Override
    public void  logOnComplete(HttpServletRequest req, WebExReqAction action, GbpsResponse response) {
        logger.debug("action={} 执行完成，执行时间={}ms，状态码={}",
            new Object[]{action.getUrl(), action.getRunTimes(), response.getStatus()});
    }
}

