

package com.gbps.webex.core;


public final class WebExConstant {
    private WebExConstant() {

    }

    public static final String ERR_CODE_PREFIX = "";

    public static final String MODEL_ATTR_BEFORE_REQ = "__share__before_req";

    public static final String REQ_ATTR_REQ_TIME = "__share_req_time";

    public static final String REQ_ATTR_ACTION = "__share_req_action";

    public static final String STAND_DATE_FMT_STR = "yyyy-MM-dd";
    public static final String STAND_TIME_FMT_STR = "HH:mm:ss";
    public static final String STAND_DATE_TIME_FMT_STR = "yyyy-MM-dd HH:mm:ss";

    public static String transShareCode(int code) {
        // return ERR_CODE_PREFIX + Integer.toString(code);
        return Integer.toString(code);
    }
}

