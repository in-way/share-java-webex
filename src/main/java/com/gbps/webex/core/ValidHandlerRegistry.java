

package com.gbps.webex.core;


import com.gbps.webex.entity.ResponseCode;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class ValidHandlerRegistry {
    private final Map<String, ResponseCode> type2CodeMap = new HashMap<>();

    public void clear() {
        type2CodeMap.clear();
    }

    /**
     * 注册valid和code的对应关系。
     * @param annotation valid对应的注解。
     * @param code 对应要返回code信息。
     */
    public void registerValidTrans(Class<? extends Annotation> annotation, ResponseCode code) {
        type2CodeMap.put(annotation.getSimpleName(),code);
    }

    Map<String, ResponseCode> getType2CodeMap() {
        return type2CodeMap;
    }
}

