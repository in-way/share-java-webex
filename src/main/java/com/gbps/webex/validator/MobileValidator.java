/*
 * @(#) MobileValidator
 * 版权声明  , 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2017
 * <br> Company:
 * <br> @author  ivy
 * <br> 2017-09-20 14:45:16
 * <br> @version 1.0
 * ————————————————————————————————
 *    修改记录
 *    修改者：
 *    修改时间：
 *    修改原因：
 * ————————————————————————————————
 */

package com.gbps.webex.validator;

import com.sunsharing.share.common.text.TextValidator;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by  ivy on 2017/9/20.
 */
public class MobileValidator implements ConstraintValidator<MobileValid, String> {

    private boolean exact = false;

    @Override
    public void initialize(MobileValid constraintAnnotation) {
        exact = constraintAnnotation.exact();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        return exact ? TextValidator.isMobileExact(value) : TextValidator.isMobileSimple(value);
    }
}

