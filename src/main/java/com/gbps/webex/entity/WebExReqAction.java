

package com.gbps.webex.entity;

import com.gbps.webex.core.WebExConstant;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;


public class WebExReqAction {

    private final String url;
    private final String httpMethod;
    private LocalDateTime reqTime;
    private LocalDateTime finishTime;

    public static WebExReqAction getWithRequest(HttpServletRequest httpServletRequest) {
        if (httpServletRequest == null) {
            return new WebExReqAction("UnKnow", "UnKnow");
        }

        WebExReqAction action = (WebExReqAction) httpServletRequest.getAttribute(WebExConstant.REQ_ATTR_ACTION);
        if (action != null) {
            return action;
        }

        String uri = httpServletRequest.getRequestURI();
        String method = httpServletRequest.getMethod();
        return new WebExReqAction(uri, method);
    }

    public WebExReqAction(String url, String httpMethod) {
        this.url = url;
        this.httpMethod = httpMethod;
    }

    public WebExReqAction prepare() {
        reqTime = LocalDateTime.now();
        return this;
    }

    public WebExReqAction complete() {
        finishTime = LocalDateTime.now();
        return this;
    }

    public String getUrl() {
        return url;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public long getRunTimes() {
        if (reqTime == null) {
            return -1L;
        }

        if (finishTime == null) {
            return -1L;
        }

        return Duration.between(reqTime, finishTime).toMillis();
    }

}

