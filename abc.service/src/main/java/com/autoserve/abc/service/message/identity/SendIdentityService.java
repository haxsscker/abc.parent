package com.autoserve.abc.service.message.identity;

import java.util.Map;

public interface SendIdentityService {
    /**
     * 实名认证
     * 
     * @param realName 真实姓名
     * @param identityNo 身份证号
     * @return
     */
    public Map<String, String> sendIdentity(String realName, String identityNo);
}
