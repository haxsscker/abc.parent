package com.autoserve.abc.service.biz.impl.cash.thirdparty.easypay;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.autoserve.abc.service.biz.entity.BatchPayData;
import com.autoserve.abc.service.biz.entity.BatchPayQuery;
import com.autoserve.abc.service.biz.entity.SignatureData;
import com.autoserve.abc.service.biz.enums.EasyPayConfig;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.http.HttpCallService;
import com.autoserve.abc.service.http.HttpCallServiceImpl;
import com.autoserve.abc.service.util.VelocityUtil;

/**
 * 类BuildBatchRequest.java的实现描述:委托结算http请求工具类
 * 
 * @author pp 2014-11-26 下午02:34:38
 */
public class BatchPayUtils {
    private static String BatchPayRequestTemplatePath = "/batchpay/batch-pay-request-template.vm";
    private static String BatchPayQueryTemplatePath   = "/batchpay/batch-pay-query-template.vm";

    public static String sendBatchPay(BatchPayData data) {
        String url = EasyPayConfig.PAY_URL_PREFIX;
        if (EasyPayConfig.TransCodeType.AGENTCOLL.value.equals(data.getTransCode())) {
            url = url + EasyPayConfig.TransCodeType.AGENTCOLL.surfix;
        } else if (EasyPayConfig.TransCodeType.AGENTPAY.value.equals(data.getTransCode())) {
            url = url + EasyPayConfig.TransCodeType.AGENTPAY.surfix;
        } else {
            throw new BusinessException("TransCode 不合法 ");
        }
        String xmlData = buildTextFromVM(data);
        Map<String, String> para;
        para = builParameter(xmlData);
        HttpCallService callService = new HttpCallServiceImpl();
        return callService.httpPost(url, para).getData();
    }

    public static String queryByBatchNO(BatchPayQuery queryData) {
        String url = EasyPayConfig.PAY_URL_PREFIX;
        String transCode = queryData.getTransCode();
        if (EasyPayConfig.QueryTransCodeType.COLLQUERY.value.equals(transCode)) {
            url += EasyPayConfig.QueryTransCodeType.COLLQUERY.surfix;
        } else if (EasyPayConfig.QueryTransCodeType.COLLSINGLEQUERY.value.equals(transCode)) {
            url += EasyPayConfig.QueryTransCodeType.COLLSINGLEQUERY.surfix;
        } else if (EasyPayConfig.QueryTransCodeType.PAYQUERY.value.equals(transCode)) {
            url += EasyPayConfig.QueryTransCodeType.PAYQUERY.surfix;
        } else if (EasyPayConfig.QueryTransCodeType.PAYSINGLEQUERY.value.equals(transCode)) {
            url += EasyPayConfig.QueryTransCodeType.PAYSINGLEQUERY.surfix;
        } else {
            throw new BusinessException("TransCode 不合法");
        }
        String xmlData = buildQueryTextFromVM(queryData);
        Map<String, String> para;
        para = builParameter(xmlData);
        HttpCallService callService = new HttpCallServiceImpl();
        return callService.httpPost(url, para).getData();
    }

    /**
     * @param url 请求的URL
     * @param xmlContent xml文件转化为string
     * @return
     */
    public static Map<String, String> builParameter(String xmlContent) {
        String encryptStr = null;
        try {
            encryptStr = BatchPaySignature.encryption(xmlContent);
        } catch (Exception e) {
            throw new BusinessException("构建参数异常");
        }
        SignatureData signData = new SignatureData(EasyPayConfig.PARTNER, encryptStr);
        String sign = BatchPaySignature.buildBatchPaySign(signData);
        Map<String, String> map = new HashMap<String, String>();
        map.put("sign", sign);
        map.put("signType", EasyPayConfig.SIGN_TYPE);
        map.put("batchBizid", EasyPayConfig.PARTNER);
        map.put("batchVersion", EasyPayConfig.BATCH_VERSION);
        map.put("batchContent", encryptStr);
        return map;
    }

    public static String buildQueryTextFromVM(BatchPayQuery data) {
        InputStream inputStream = BatchPayUtils.class.getResourceAsStream(BatchPayQueryTemplatePath);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("data", data);
        String content = VelocityUtil.evaluate(paramMap, inputStream);
        return content;
    }

    /**
     * 代收代付 调用接口
     */
    public static String buildTextFromVM(BatchPayData data) {
        InputStream inputStream = BatchPayUtils.class.getResourceAsStream(BatchPayRequestTemplatePath);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("data", data);
        String content = VelocityUtil.evaluate(paramMap, inputStream);
        return content;
    }
}
