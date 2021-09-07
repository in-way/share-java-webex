/*
 * @(#) ValidateException
 * 版权声明  , 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2017
 * <br> Company:
 * <br> @author  ivy
 * <br> 2017-09-22 09:55:08
 * <br> @version 1.0
 * ————————————————————————————————
 *    修改记录
 *    修改者：
 *    修改时间：
 *    修改原因：
 * ————————————————————————————————
 */

package com.gbps.webex.exception;

import com.sunsharing.share.common.base.exception.ResponseCode;
import com.sunsharing.share.common.base.exception.ShareResponseCode;
import com.sunsharing.share.common.base.exception.ShareBusinessException;

/**
 * Created by  ivy on 2017/9/22.
 */
public class ValidateException extends ShareBusinessException {
    private String field;

    public ValidateException(String field) {
        this(field, ShareResponseCode.VALID_FIELD_ILLEGAL);
    }

    public ValidateException(String field, ResponseCode code) {
        super(code);
        this.field = field;
    }

    public ValidateException(String field, int code, String message) {
        super(code, message);
        this.field = field;
    }

    @Override
    public String toString() {
        return String.format("field=%s,code=%s,message=%s",
            field,
            code.getCode(),
            code.getMessage());
    }

    @Override
    public String getMessage() {
        return String.format("%s:%s", this.field , super.getMessage());
    }
}

