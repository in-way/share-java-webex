/*
 * @(#) SessionTimeOutException
 * 版权声明  , 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2017
 * <br> Company:
 * <br> @author  ivy
 * <br> 2017-09-15 15:25:51
 * <br> @version 1.0
 * ————————————————————————————————
 *    修改记录
 *    修改者：
 *    修改时间：
 *    修改原因：
 * ————————————————————————————————
 */

package com.gbps.webex.exception;

import com.sunsharing.share.common.base.exception.ShareResponseCode;
import com.sunsharing.share.common.base.exception.ShareBusinessException;

/**
 * Created by  ivy on 2017/9/15.
 */
public class AuthException extends ShareBusinessException {
    public AuthException() {
        super(ShareResponseCode.NOT_AUTH);
    }

    public static void main(String[] args) {
        AuthException authException = new AuthException();
    }
}

