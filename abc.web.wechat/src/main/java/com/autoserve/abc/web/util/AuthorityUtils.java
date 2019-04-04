package com.autoserve.abc.web.util;

import com.autoserve.abc.web.annotation.AuthorityVerify;
import org.springframework.web.method.HandlerMethod;

public class AuthorityUtils {

    /**
     * 判断一个方法上是否标注了 权限管理
     * @param hand
     * @return
     */
    public static boolean isNeedVerify(HandlerMethod hand) {
        AuthorityVerify need = hand.getMethodAnnotation(AuthorityVerify.class);
        if (need == null) {
            need = hand.getMethod().getDeclaringClass().getAnnotation(AuthorityVerify.class);
        }
        if (need == null)
            return false;
        else
            return true;
    }
}
