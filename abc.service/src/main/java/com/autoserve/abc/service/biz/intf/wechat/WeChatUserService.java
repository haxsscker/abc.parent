package com.autoserve.abc.service.biz.intf.wechat;

public interface WeChatUserService {

    /**
     * 自动生成平台账号
     * 
     * @param wechatid 微信账号的 OpenID
     * @param wechatname 微信账号的ID
     * @return
     */
    public String getUserNameToWeChat(String wechatid, String wechatname);
}
