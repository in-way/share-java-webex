

package com.gbps.webex.validator;


import com.sunsharing.share.common.text.TextValidator;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义hibernate valid身份证校验器
 * @author
 */
public class IdCardValidator implements ConstraintValidator<IdCardValid, String> {

    private boolean allow15;

    public void initialize(IdCardValid constraintAnnotation) {
        allow15 = constraintAnnotation.allow15();
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        return allow15 ? TextValidator.isIdCardExact(value) : TextValidator.isIdCard18Exact(value);
    }
}
