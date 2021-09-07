

package com.gbps.webex.entity;

import com.gbps.webex.core.WebExConstant;

public class ValidMessage {

    private String field;
    private String code;
    private String message;

    public ValidMessage(String field, int code, String message) {
        this(field, WebExConstant.transShareCode(code), message);
    }

    public ValidMessage(String field, String code, String message) {
        this.field = field;
        this.code = code;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

