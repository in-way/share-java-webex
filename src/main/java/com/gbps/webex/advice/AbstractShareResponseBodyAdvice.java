
package com.gbps.webex.advice;

import com.gbps.webex.annotation.GbpsRest;
import com.gbps.webex.core.WebExConstant;
import com.gbps.webex.entity.GbpsResponse;
import com.gbps.webex.entity.WebExReqAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(annotations = {GbpsRest.class})
public abstract class AbstractShareResponseBodyAdvice extends AbstractJsonpResponseBodyAdvice {

    @Autowired
    protected HttpServletRequest springRequest;



    public AbstractShareResponseBodyAdvice() {
        this("__callback");
    }

    public AbstractShareResponseBodyAdvice(String... jsonpFunction) {
        super(jsonpFunction);
    }

    @ModelAttribute(WebExConstant.MODEL_ATTR_BEFORE_REQ)
    public final Object beforeRequest() {
        //record some into data;
        WebExReqAction action = WebExReqAction.getWithRequest(springRequest).prepare();
        springRequest.setAttribute(WebExConstant.REQ_ATTR_ACTION, action);
        return null;
    }

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType,
                                           MethodParameter returnType, ServerHttpRequest request,
                                           ServerHttpResponse response) {
        super.beforeBodyWriteInternal(bodyContainer, contentType, returnType, request, response);
        Object value = bodyContainer.getValue();

        if (needConvertResponsePacket(bodyContainer.getJsonpFunction(), value)) {
            bodyContainer.setValue(GbpsResponse.autoSuccess(value));
        }

        value = bodyContainer.getValue();

        WebExReqAction action = WebExReqAction.getWithRequest(springRequest).complete();

    }

    protected final boolean needConvertResponsePacket(String jsonpFunction, Object retValue) {
        // jsonp 不转换...

        if (jsonpFunction != null) {
            return false;
        }

        // 如果返回ResponsePacket 必须非空。
        if (retValue == null) {
            return true;
        }

        // 如果返回时ResponsePacket 不做转换。
        return !GbpsResponse.class.isAssignableFrom(retValue.getClass());
    }

}

