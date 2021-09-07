

package com.gbps.webex.entity;

public interface ResponseCode{
    int getCode();
    String getMessage();

    default String strCode() {
        return Integer.toString(this.getCode());
    }

    static ResponseCode of(int code, String message) {
        return new SimpleResponseCode(code, message);
    }
}