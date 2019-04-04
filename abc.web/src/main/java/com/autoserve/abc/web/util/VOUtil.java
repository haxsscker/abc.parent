package com.autoserve.abc.web.util;

import com.autoserve.abc.web.vo.JsonPageVO;

import java.util.Collections;

/**
 * @author yuqing.zheng
 *         Created on 2015-01-07,22:20
 */
public class VOUtil {
    @SuppressWarnings("unchecked")
    public static <T> JsonPageVO<T> emptyPageVO() {
        JsonPageVO<T> result = new JsonPageVO<T>();
        result.setSuccess(true);
        result.setTotal(0);
        result.setRows(Collections.EMPTY_LIST);

        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> JsonPageVO<T> emptyPageVO(String message) {
        JsonPageVO<T> result = new JsonPageVO<T>();
        result.setSuccess(true);
        result.setTotal(0);
        result.setRows(Collections.EMPTY_LIST);

        result.setMessage(ErrorMessageUtil.getLocalMessage(message));

        return result;
    }
}
