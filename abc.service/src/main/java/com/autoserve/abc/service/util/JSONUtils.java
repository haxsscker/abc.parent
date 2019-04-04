package com.autoserve.abc.service.util;

import com.alibaba.fastjson.JSON;

public class JSONUtils {

    public static String toJSONString(Object o) {
        return JSON.toJSONString(o);
    }
}
