
package com.gbps.webex.entity;
public enum GbpsResponseCode implements ResponseCode {

    SUCCESS(1200, "请求成功"),
    NOT_AUTH(1401, "授权已过期，禁止访问"),
    LOGIN_ON_OTHER_DEVICE(1402, "已在其他地方登陆"),
    NOT_PERMISSION(1403, "请求未授权，禁止访问"),
    NOT_FOUND(1404, "请求路径不正确"),
    INNER_SYSTEM_ERROR(1500, "系统内部异常"),
    INPUT_ILLEGAL(1700, "入参格式异常"),
    INPUT_JSON_ILLEGAL(1701, "入参报文非JSON"),
    INPUT_REQUIRED_MISS(1702, "入参报文缺少通用节点"),
    VALID_FIELD_ILLEGAL(1800, "字段校验异常"),
    VALID_FIELD_NOT_EMPTY(1801, "非空校验异常"),
    VALID_FIELD_NUMERIC_ILLEGAL(1802, "数值格式校验异常"),
    VALID_FIELD_NUMERIC_RANGE_ILLEGAL(1803, "数值范围校验异常"),
    VALID_FIELD_STRING_LENGTH_ILLEGAL(1804, "字符长度校验异常"),
    VALID_FIELD_DATE_FORMAT_ILLEGAL(1805, "日期格式校验异常"),
    VALID_FIELD_DATE_RANGE_ILLEGAL(1806, "日期范围校验异常"),
    VALID_FIELD_BOOLEAN_ILLEGAL(1807, "布尔值校验异常"),
    VALID_FIELD_EMAIL_ILLEGAL(1808, "Email校验异常"),
    VALID_FIELD_TEL_ILLEGAL(1809, "电话号校验异常"),
    VALID_FIELD_ID_CARD_ILLEGAL(1810, "身份证校验异常"),
    VALID_FIELD_NOT_ALLOW_REPEAT(1811, "数据不允许重复"),
    VALID_FIELD_IP_ILLEGAL(1812, "IP校验异常"),
    VALID_UPLOAD_NOT_ALLOW_SUFFIX(1813, "上传文件格式不符"),
    VALID_FIELD_CREDIT_CODE(1814, "社会信用代码校验异常"),
    VALID_FIELD_BUSINESS_LICENSE(1815, "营业执照注册号校验异常"),
    VALID_FIELD_ORG_CODE(1816, "组织机构代码校验异常"),
    BIZ_ILLEGAL(1899, "业务规则校验异常");

    GbpsResponseCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    private int status;
    private String message;

    @Override
    public int getCode() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }


}


