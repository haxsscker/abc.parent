package com.autoserve.abc.service.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.autoserve.abc.service.biz.entity.EasyPayData;
import com.autoserve.abc.service.biz.enums.EasyPayConfig;
import com.autoserve.abc.service.biz.enums.EasyPayConfig.EasyPayVerifyResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.http.HttpCallService;
import com.autoserve.abc.service.http.HttpCallServiceImpl;

/**
 * easypay 支付工具类
 * 
 * @author pp 2014-11-27 上午11:30:39
 */
public class EasyPayUtils {
    /**
     * 根据notifyId 验证返回的notify_url是否合法
     */
    public static PlainResult<EasyPayConfig.EasyPayVerifyResult> verifyNotify(String notifyId) {
        HttpCallService callService = new HttpCallServiceImpl();
        String url = EasyPayConfig.EASY_VERIFY_URL + "?partner=" + EasyPayConfig.PARTNER + "&notify_id=" + notifyId;
        String value = callService.httpGet(url).getData();
        PlainResult<EasyPayConfig.EasyPayVerifyResult> result = new PlainResult<EasyPayConfig.EasyPayVerifyResult>();
        result.setData(EasyPayVerifyResult.valueof(value));
        return result;
    }

    /**
     * 充值 建立充值的url。
     */
    public static String buildEasyPayUrl(EasyPayData data) {
        Map<String, String> map = JSON.parseObject(JSON.toJSONString(data), new TypeReference<Map<String, String>>() {
        });
        if (CollectionUtils.isEmpty(map)) {
            return "";
        }

        String sign = buildMySign(map, EasyPayConfig.KEY);
        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);
        StringBuilder urlSb = new StringBuilder(EasyPayConfig.EASYPAY_URL);
        urlSb.append("?");
        for (String key : keys) {
            String value = map.get(key);
            if (StringUtils.isBlank(value) || "sign".equalsIgnoreCase(key) || "sign_type".equalsIgnoreCase(key)) {
                continue;
            }
            urlSb.append(key + "=" + value + "&");
        }
        urlSb.append("sign=" + sign + "&");
        urlSb.append("sign_type=" + EasyPayConfig.SIGN_TYPE);

        return urlSb.toString();
    }

    /**
     * 功能：生成签名结果
     * 
     * @param sArray 要签名的数组
     * @param key 安全校验码
     * @return 签名结果字符串
     */
    public static String buildMySign(Map<String, String> sArray, String key) {
        if (!CollectionUtils.isEmpty(sArray)) {
            StringBuilder preStr = createLinkString(sArray); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            return Md5Encrypt.md5(preStr.append(key).toString());//把拼接后的字符串再与安全校验码直接连接起来,并且生成加密串
        }
        return null;
    }

    /**
     * 功能：把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * 
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static StringBuilder createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        StringBuilder preStr = new StringBuilder();
        for (String key : keys) {
            String value = params.get(key);
            if (StringUtils.isBlank(value) || "sign".equalsIgnoreCase(key) || "sign_type".equalsIgnoreCase(key)) {
                continue;
            }
            preStr.append(key).append("=").append(value).append("&");
        }

        return preStr.deleteCharAt(preStr.length() - 1);
    }

    /**
     * 将易生支付POST过来反馈信息转换一下
     * 
     * @param requestParams 返回参数信息
     * @return Map 返回一个只有字符串值的MAP
     */
    @SuppressWarnings("unchecked")
    public static Map transformRequestMap(Map requestParams) {
        Map params = new HashMap();
        if (requestParams != null && requestParams.size() > 0) {
            String name = "";
            String[] values;
            for (Object key : requestParams.keySet()) {
                name = (String) key;
                values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                params.put(name, valueStr);
            }
        }
        return params;
    }
}
