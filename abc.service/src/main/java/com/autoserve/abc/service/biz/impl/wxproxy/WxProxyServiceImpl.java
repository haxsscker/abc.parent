package com.autoserve.abc.service.biz.impl.wxproxy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.dataobject.WxTokenDO;
import com.autoserve.abc.dao.intf.WxTokenDao;
import com.autoserve.abc.service.biz.enums.WxState;
import com.autoserve.abc.service.biz.impl.cash.MiscUtil;
import com.autoserve.abc.service.biz.intf.wxproxy.WxProxyService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.http.AbcHttpCallService;
import com.autoserve.abc.service.http.AbcHttpCallServiceImpl;

@Service
public class WxProxyServiceImpl implements WxProxyService {
    private static final Logger      logger             = LoggerFactory.getLogger(WxProxyServiceImpl.class);
    @Resource
    private final AbcHttpCallService abcHttpCallService = new AbcHttpCallServiceImpl();
    @Resource
    private WxTokenDao               wxTokenDao;

    @Override
    public PlainResult<Map<String, String>> generateToken(String AppID, String AppSecret) {
        logger.info("发送微信第三方接口生成token");
        PlainResult<Map<String, String>> result = new PlainResult<Map<String, String>>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", AppID);
        params.put("secret", AppSecret);
        params.put("grant_type", "client_credential");
        String submitUrl = "https://api.weixin.qq.com/cgi-bin/token";
        String resultStr = abcHttpCallService.httpGet(submitUrl, params).getData();
        Map<String, String> paramsResult = new HashMap<String, String>();
        paramsResult = MiscUtil.parseJSON(resultStr.toString());
        String token = paramsResult.get("access_token");
        result.setData(paramsResult);
        return result;
    }

    @Override
    public PlainResult<WxTokenDO> CreatToken(String AppID, String AppSecret) {
        PlainResult<Map<String, String>> getResult = generateToken(AppID, AppSecret);
        String accessToken = getResult.getData().get("access_token");
        String token = getStringRandom(8);
        WxTokenDO wxToken = new WxTokenDO();
        wxToken.setWxId(1);
        wxToken.setWxName(AppSecret);
        wxToken.setWxAccessToken(accessToken);
        wxToken.setWxOpenId(AppID);
        wxToken.setWxToken(token);
        wxToken.setWxState(WxState.TO_AUDIT.getType());
        List<WxTokenDO> WxTokenList = wxTokenDao.find();
        if (WxTokenList.size() > 0) {
            wxTokenDao.update(wxToken);
        } else {
            wxTokenDao.insert(wxToken);
        }
        PlainResult<WxTokenDO> result = new PlainResult<WxTokenDO>();
        result.setData(wxToken);
        return result;
    }

    /**
     * 生成length位随机数字加字母
     * 
     * @param length
     * @return
     */
    public static String getStringRandom(int length) {
        String val = "";
        Random random = new Random();
        //参数length，表示生成几位随机数  
        for (int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字  
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母  
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    @Override
    public PlainResult<WxTokenDO> findToken() {
        PlainResult<WxTokenDO> result = new PlainResult<WxTokenDO>();
        List<WxTokenDO> WxTokenList = wxTokenDao.find();
        result.setData(WxTokenList.get(0));
        return result;
    }

    @Override
    public int update(WxTokenDO wxTokenDO) {

        return wxTokenDao.update(wxTokenDO);

    }

    @Override
    public String eventBackMsg() {
        PlainResult<WxTokenDO> result = findToken();
        String submitUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="
                + result.getData().getWxAccessToken();
        Map<String, String> params = new HashMap<String, String>();
        params.put("touser", "gh_915e6134e7b6");
        params.put("msgtype", "text");
        Map<String, String> param = new HashMap<String, String>();
        param.put("content", "Hello World");
        params.put("text", param.toString());
        String resultStr = abcHttpCallService.httpPost(submitUrl, params).getData();

        return resultStr;
    }

    @Override
    public PlainResult<Map<String, String>> getWeChatUserInfo(String OpenID) {

        logger.info("获取用户信息,用户的OpenID：" + OpenID);
        PlainResult<WxTokenDO> tokenResult = findToken();
        String accessToken = tokenResult.getData().getWxAccessToken();
        PlainResult<Map<String, String>> result = new PlainResult<Map<String, String>>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("access_token", accessToken);
        params.put("openid", OpenID);
        params.put("lang", "zh_CN");
        String submitUrl = "https://api.weixin.qq.com/cgi-bin/user/info";
        String resultStr = abcHttpCallService.httpPost(submitUrl, params).getData();
        Map<String, String> paramsResult = new HashMap<String, String>();
        paramsResult = MiscUtil.parseJSON(resultStr.toString());
        result.setData(paramsResult);
        return result;

    }

    @Override
    public PlainResult<Map<String, String>> updateWeChatMenu(String body) {

        PlainResult<WxTokenDO> tokenResult = findToken();
        String accessToken = tokenResult.getData().getWxAccessToken();
        String submitUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken;
        String resultStr = abcHttpCallService.urlConnectionPost(submitUrl, body).getData();
        Map<String, String> paramsResult = new HashMap<String, String>();
        paramsResult = MiscUtil.parseJSON(resultStr.toString());
        PlainResult<Map<String, String>> result = new PlainResult<Map<String, String>>();
        result.setData(paramsResult);
        return result;

    }

    @Override
    public PlainResult<Map<String, String>> getOpenidByCode(String appid, String secret, String code) {
        String submitUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", appid);
        params.put("secret", secret);
        params.put("code", code);
        params.put("grant_type", "authorization_code");
        String resultStr = abcHttpCallService.httpPost(submitUrl, params).getData();
        Map<String, String> paramsResult = new HashMap<String, String>();
        paramsResult = MiscUtil.parseJSON(resultStr.toString());
        PlainResult<Map<String, String>> result = new PlainResult<Map<String, String>>();
        result.setData(paramsResult);
        return result;
    }

}
