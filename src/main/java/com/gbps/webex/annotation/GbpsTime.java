/*
 * @(#) ShareTime
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

package com.gbps.webex.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gbps.webex.core.WebExConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 基于Jackson包装标准Time格式注解。
 * 参考：<br/>
 * {@link com.fasterxml.jackson.annotation.JsonFormat}
 * @author  ivy
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER,
    ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonFormat(pattern = WebExConstant.STAND_DATE_TIME_FMT_STR)
public @interface GbpsTime {
}
