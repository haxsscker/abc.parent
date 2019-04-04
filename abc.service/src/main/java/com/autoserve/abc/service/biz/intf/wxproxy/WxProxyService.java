package com.autoserve.abc.service.biz.intf.wxproxy;

import java.util.Map;

import com.autoserve.abc.dao.dataobject.WxTokenDO;
import com.autoserve.abc.service.biz.result.PlainResult;

public interface WxProxyService {
    /**
     * 生成token
     * 
     * @param AppID
     * @param AppSecret
     * @return
     */
    public PlainResult<Map<String, String>> generateToken(String AppID, String AppSecret);

    /**
     * 本地生成token并插入数据库
     * 
     * @param AppID
     * @param AppSecret
     * @return
     */
    public PlainResult<WxTokenDO> CreatToken(String AppID, String AppSecret);

    /**
     * 获取token
     * 
     * @return
     */
    public PlainResult<WxTokenDO> findToken();

    /**
     * 更新token表
     * 
     * @param wxTokenDO
     * @return
     */
    public int update(WxTokenDO wxTokenDO);

    public String eventBackMsg();

    /**
     * 根据微信用户的OpenID获取微信用户信息
     * 
     * @param OpenID
     * @return
     */
    public PlainResult<Map<String, String>> getWeChatUserInfo(String OpenId);

    /**
     * 更新微信的自定义菜单
     * 
     * @param body 菜单字符串
     * @return
     */
    public PlainResult<Map<String, String>> updateWeChatMenu(String body);

    /**
     * 根据OAuth2.0授权code获取访问用户Openid
     * 
     * @param appid 公众号ID
     * @param secret 公众号应用密钥
     * @param code 返回code
     * @return
     */
    public PlainResult<Map<String, String>> getOpenidByCode(String appid, String secret, String code);

}
