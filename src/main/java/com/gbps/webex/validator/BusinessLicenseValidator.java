/*
 * @(#) EmailValidator
 * 版权声明  , 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2017
 * <br> Company:
 * <br> @author  ivy
 * <br> 2017-09-20 14:50:38
 * <br> @version 1.0
 * ————————————————————————————————
 *    修改记录
 *    修改者：
 *    修改时间：
 *    修改原因：
 * ————————————————————————————————
 */

package com.gbps.webex.validator;

import com.sunsharing.share.common.text.IdUtil;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 营业执照注册号校验器
 */
public class BusinessLicenseValidator implements ConstraintValidator<BusinessLicenseValid, String> {

    @Override
    public void initialize(BusinessLicenseValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        return IdUtil.isBusinessLicenseExact(value);
    }
}

