
package com.gbps.webex.core;

import com.gbps.webex.entity.ResponseCode;
import com.gbps.webex.entity.GbpsResponseCode;
import com.gbps.webex.validator.BusinessLicenseValid;
import com.gbps.webex.validator.CreditCodeValid;
import com.gbps.webex.validator.EmailValid;
import com.gbps.webex.validator.IdCardValid;
import com.gbps.webex.validator.IpValid;
import com.gbps.webex.validator.MobileValid;
import com.gbps.webex.validator.OrgCodeValid;
import com.gbps.webex.validator.TelValid;

import org.hibernate.validator.constraints.Length;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.*;

/**
 *
 * @author  ivy
 */
public final class ValidHandlerManager {

    private final Map<String, ResponseCode> type2CodeMap = new HashMap<>();
    private final ValidHandlerRegistry defaultRegistry = new ValidHandlerRegistry();

    public ResponseCode loadValidResponseCode(String type) {
        return type2CodeMap.getOrDefault(type, GbpsResponseCode.VALID_FIELD_ILLEGAL);
    }

    public void init(ValidHandlerRegistry registry) {
        type2CodeMap.clear();
        initDefaultSetting();

        registerValidTrans(defaultRegistry);
        registerValidTrans(registry);
    }

    private void registerValidTrans(ValidHandlerRegistry registry) {
        if (registry == null || registry.getType2CodeMap() == null) {
            return;
        }

        type2CodeMap.putAll(registry.getType2CodeMap());
    }

    private void initDefaultSetting() {
//        defaultRegistry.clear();
//
//        defaultRegistry.registerValidTrans(NotNull.class, GbpsResponseCode.VALID_FIELD_NOT_EMPTY);
//
//        defaultRegistry.registerValidTrans(Size.class, GbpsResponseCode.VALID_FIELD_STRING_LENGTH_ILLEGAL);
//        defaultRegistry.registerValidTrans(Length.class, GbpsResponseCode.VALID_FIELD_STRING_LENGTH_ILLEGAL);
//        defaultRegistry.registerValidTrans(Pattern.class, GbpsResponseCode.VALID_FIELD_ILLEGAL);
//
//        // NUMERIC_RANGE
//        defaultRegistry.registerValidTrans(Min.class, GbpsResponseCode.VALID_FIELD_NUMERIC_RANGE_ILLEGAL);
//        defaultRegistry.registerValidTrans(Max.class, GbpsResponseCode.VALID_FIELD_NUMERIC_RANGE_ILLEGAL);
//
//        defaultRegistry.registerValidTrans(DecimalMin.class, GbpsResponseCode.VALID_FIELD_NUMERIC_RANGE_ILLEGAL);
//        defaultRegistry.registerValidTrans(DecimalMax.class, GbpsResponseCode.VALID_FIELD_NUMERIC_RANGE_ILLEGAL);
//
//        defaultRegistry.registerValidTrans(Size.class, GbpsResponseCode.VALID_FIELD_NUMERIC_RANGE_ILLEGAL);
//        defaultRegistry.registerValidTrans(Digits.class, GbpsResponseCode.VALID_FIELD_NUMERIC_RANGE_ILLEGAL);
//
//        // IdCardValid
//        defaultRegistry.registerValidTrans(IdCardValid.class, GbpsResponseCode.VALID_FIELD_ID_CARD_ILLEGAL);
//        defaultRegistry.registerValidTrans(TelValid.class, GbpsResponseCode.VALID_FIELD_TEL_ILLEGAL);
//        defaultRegistry.registerValidTrans(MobileValid.class, GbpsResponseCode.VALID_FIELD_TEL_ILLEGAL);
//        defaultRegistry.registerValidTrans(EmailValid.class, GbpsResponseCode.VALID_FIELD_EMAIL_ILLEGAL);
//        defaultRegistry.registerValidTrans(IpValid.class, GbpsResponseCode.VALID_FIELD_IP_ILLEGAL);
//
//        // 社会信用代码 工商注册号 组织机构代码
//        defaultRegistry.registerValidTrans(CreditCodeValid.class, GbpsResponseCode.VALID_FIELD_CREDIT_CODE);
//        defaultRegistry.registerValidTrans(BusinessLicenseValid.class, GbpsResponseCode.VALID_FIELD_BUSINESS_LICENSE);
//        defaultRegistry.registerValidTrans(OrgCodeValid.class, GbpsResponseCode.VALID_FIELD_ORG_CODE);

    }

}

