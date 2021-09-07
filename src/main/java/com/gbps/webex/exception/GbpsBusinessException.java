

package com.gbps.webex.exception;


import com.gbps.webex.entity.ResponseCode;

import java.io.Serializable;


public class GbpsBusinessException extends RuntimeException implements Serializable {
    private final ResponseCode code;

    public GbpsBusinessException(ResponseCode code) {
        super(code.getMessage(), (Throwable)null, false, false);
        this.code = code;
    }

    public GbpsBusinessException(int code, String message) {
        super(message, (Throwable)null, false, false);
        this.code = ResponseCode.of(code, message);
    }

    public String toString() {
        return String.format("code=%s,message=%s", this.code.getCode(), this.code.getMessage());
    }

    public ResponseCode getResponseCode() {
        return this.code;
    }

}

