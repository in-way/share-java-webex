/*
 * @(#) IdNoValid
 * 版权声明  , 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2017
 * <br> Company:
 * <br> @author  ivy
 * <br> 2017-09-14 10:21:40
 * <br> @version 1.0
 * ————————————————————————————————
 *    修改记录
 *    修改者：
 *    修改时间：
 *    修改原因：
 * ————————————————————————————————
 */

package com.gbps.webex.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


/**
 * hibernate自定义身份证校验注解
 *
 * @author xuxd
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdCardValidator.class)
@Documented
public @interface IdCardValid {

    String message() default "身份证号验证错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean allow15() default true;
}
