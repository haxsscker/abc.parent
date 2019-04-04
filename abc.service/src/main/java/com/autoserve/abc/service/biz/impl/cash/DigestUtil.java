package com.autoserve.abc.service.biz.impl.cash;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autoserve.abc.service.util.RsaHelper;
import com.autoserve.abc.service.util.SystemGetPropeties;

/**
 * @Filename DigestUtil.java
 * @Description 签名工具类
 * @Version 1.0
 * @Author bohr
 * @Email qzhanbo@yiji.com
 * @History <li>Author: bohr.qiu</li> <li>Date: 2012-10-15</li> <li>Version: 1.0
 *          </li> <li>Content: create</li>
 */
public class DigestUtil {

    private static final String TIME_ZONE       = "UTC";
    private static final String UTC_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS Z";
    /**
     * 签名编码
     */
    public static final String  UTF8            = "utf-8";
    /**
     * 签名key
     */
    public static final String  SIGN_KEY        = "sign";

    /**
     * 渠道id key
     */
    public static final String  CHANNEL_ID_KEY  = "channelId";

    /**
     * 签名类型key，支持DigestALGEnum
     */
    public static final String  SIGN_TYPE_KEY   = "signType";

    /**
     * utc时间key
     */
    public static final String  TIMESTAMP_KEY   = "utc_time_stamp";

    private static final Logger log             = LoggerFactory.getLogger(DigestUtil.class);

    /**
     * 以Map中key的字符顺序排序后签名，如果secretKey不为空，排在最后面签名。<br/>
     * 比如：Map中值如下：<br/>
     * keyA=valueA<br/>
     * keyB=valueB<br/>
     * keyA1=valueA1<br/>
     * <br/>
     * security_check_code为yjf<br/>
     * 待签名字符串为：<br/>
     * keyA=valueA&keyA1=valueA1&keyB=valueByjf<br/>
     * <b>注意:</b>SIGN_KEY不会被签名
     * 
     * @param dataMap
     * @param securityCheckKey 密钥
     * @param de 摘要算法
     * @return
     */
    public static <T> String digest(Map<String, T> dataMap, String securityCheckKey, DigestALGEnum de) {
        return digest(dataMap, securityCheckKey, de, UTF8);
    }

    /**
     * 签名并带上时间戳
     * 
     * @param dataMap
     * @param securityCheckKey
     * @param de
     * @param charset
     * @return
     */
    public static String digestWithTimeStamp(Map<String, Object> dataMap, String securityCheckKey, DigestALGEnum de,
                                             String charset) {
        dataMap.put(TIMESTAMP_KEY, getUTCTime());
        return digest(dataMap, securityCheckKey, de, charset);
    }

    /**
     * 签名并带上时间戳，使用utf-8编码
     * 
     * @param dataMap
     * @param securityCheckKey
     * @param de
     * @return
     */
    public static String digestWithTimeStamp(Map<String, Object> dataMap, String securityCheckKey, DigestALGEnum de) {
        return digestWithTimeStamp(dataMap, securityCheckKey, de, UTF8);
    }

    /**
     * 以Map中key的字符顺序排序后签名，如果secretKey不为空，排在最后面签名。<br/>
     * 比如：Map中值如下：<br/>
     * keyA=valueA<br/>
     * keyB=valueB<br/>
     * keyA1=valueA1<br/>
     * <br/>
     * security_check_code为yjf<br/>
     * 待签名字符串为：<br/>
     * keyA=valueA&keyA1=valueA1&keyB=valueByjf<br/>
     * <b>注意:</b>SIGN_KEY不会被签名
     * 
     * @param dataMap
     * @param securityCheckKey 密钥
     * @param de 摘要算法
     * @return
     */
    public static <T> String digest(Map<String, T> dataMap, String securityCheckKey, DigestALGEnum de, String encoding) {
        if (dataMap == null) {
            throw new IllegalArgumentException("数据不能为空");
        }
        if (securityCheckKey == null) {
            throw new IllegalArgumentException("安全校验码数据不能为空");
        }
        if (de == null) {
            throw new IllegalArgumentException("摘要算法不能为空");
        }
        if (StringUtil.isBlank(encoding)) {
            throw new IllegalArgumentException("字符集不能为空");
        }

        TreeMap<String, T> treeMap = new TreeMap<String, T>(dataMap);
        StringBuilder sb = new StringBuilder();
        for (Entry<String, T> entry : treeMap.entrySet()) {
            if (entry.getValue() == null) {
                throw new IllegalArgumentException(entry.getKey() + " 待签名值不能为空");
            }
            if (entry.getKey().equals(SIGN_KEY)) {
                continue;
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue().toString()).append("&");
        }
        sb.deleteCharAt(sb.length() - 1);

        sb.append(securityCheckKey);

        byte[] toDigest;
        try {
            String str = sb.toString();
            toDigest = str.getBytes(encoding);
            MessageDigest md = MessageDigest.getInstance(de.getName());
            md.update(toDigest);
            return new String(Hex.encodeHex(md.digest()));
        } catch (Exception e) {
            throw new RuntimeException("签名失败", e);
        }
    }

    private static String getParameter(HttpServletRequest request, String parameter) {
        String para = request.getParameter(parameter);
        if (para == null) {
            throw new IllegalArgumentException(parameter + "不能为空");
        }
        return StringUtil.trimToEmpty(para);
    }

    /**
     * 从request中获取待签名数据，校验签名是否正确
     * 
     * @param request
     * @param securityCheckKey
     * @param de 签名算法,如果request中有SIGN_TYPE_KEY，则以SIGN_TYPE_KEY指定的摘要算法摘要
     * @param charset 字符集
     * @param expireTime 过期时间
     * @param timeUnit 过期时间单位
     */
    public static void checkWithTimestamp(HttpServletRequest request, String securityCheckKey, DigestALGEnum de,
                                          String charset, long expireTime, TimeUnit timeUnit) {
        String timestamp = getParameter(request, TIMESTAMP_KEY);
        checkTimeout(timestamp, expireTime, timeUnit);
        check(request, securityCheckKey, de, charset);
    }

    /**
     * 从request中获取待签名数据，校验签名是否正确
     * 
     * @param request
     * @param securityCheckKey
     * @param de 签名算法,如果request中有SIGN_TYPE_KEY，则以SIGN_TYPE_KEY指定的摘要算法摘要
     * @param charset 编码
     * @return
     */
    public static void check(HttpServletRequest request, String securityCheckKey, DigestALGEnum de, String charset) {
        if (securityCheckKey == null) {
            throw new IllegalArgumentException("安全校验码不能为空");
        }
        if (request == null) {
            throw new IllegalArgumentException("request对象不能为空");
        }
        String signType = request.getParameter(SIGN_TYPE_KEY);
        signType = (signType == null) ? de.getName() : signType;
        if (DigestALGEnum.getByName(signType) == null) {
            throw new IllegalArgumentException("不支持的摘要算法类型:" + signType);
        }
        String sign = getParameter(request, SIGN_KEY);
        TreeMap<String, String[]> treeMap = new TreeMap<String, String[]>(request.getParameterMap());
        StringBuilder sb = new StringBuilder();
        for (Entry<String, String[]> entry : treeMap.entrySet()) {
            if (entry.getValue() == null) {
                throw new IllegalArgumentException(entry.getKey() + " 待签名值不能为空");
            }
            if (entry.getKey().equals(SIGN_KEY)) {
                continue;
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue()[0]).append("&");
        }
        sb.deleteCharAt(sb.length() - 1);

        sb.append(securityCheckKey);

        byte[] toDigest;
        String digest;
        try {
            String str = sb.toString();
            toDigest = str.getBytes(charset);
            MessageDigest md = MessageDigest.getInstance(signType);
            md.update(toDigest);
            digest = new String(Hex.encodeHex(md.digest()));
        } catch (Exception e) {
            throw new RuntimeException("签名失败", e);
        }
        if (!sign.equals(digest)) {
            throw new RuntimeException("签名校验失败");
        }
    }

    /**
     * 从返回的数据，校验签名是否正确
     * 
     * @param map
     * @param publickey
     * @param charset 编码
     * @return
     */
    public static void check(String dataStr, Map<String, String> map, String publickey, String charset) {
        if (publickey == null) {
            throw new IllegalArgumentException("验签公钥不能为空");
        }
        if (map == null) {
            throw new IllegalArgumentException("返回map集合不能为空");
        }
        try {
            RsaHelper rsa = RsaHelper.getInstance();
            boolean verifySignature = rsa.verifySignature(map.get("SignInfo"), dataStr,
                    SystemGetPropeties.getStrString("publickey"));
            String verifySignatureStr = Boolean.toString(verifySignature);
            log.info("验签:" + verifySignatureStr);
            log.info("返回码:" + map.get("ResultCode"));
            if (!verifySignature) {
                throw new IllegalArgumentException("验签失败");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("验签失败");
        }
    }

    /**
     * @param request
     * @param securityCheckKey
     * @param de
     * @return
     */
    public static void check(HttpServletRequest request, String securityCheckKey, DigestALGEnum de) {
        check(request, securityCheckKey, de, UTF8);
    }

    public static <T> String digest(Map<String, T> data, DigestALGEnum de) {
        return digest(data, null, de);
    }

    public static <T> String digestWithSHA256(Map<String, T> data) {
        return digest(data, null, DigestALGEnum.SHA256);
    }

    public static <T> String digestWithSHA256(Map<String, T> data, String securityCheckKey) {
        return digest(data, securityCheckKey, DigestALGEnum.SHA256);
    }

    public static <T> String digestWithMD5(Map<String, T> data) {
        return digest(data, null, DigestALGEnum.MD5);
    }

    public static <T> String digestWithMD5(Map<String, T> data, String securityCheckKey) {
        return digest(data, securityCheckKey, DigestALGEnum.MD5);
    }

    /**
     * 获得utc时间
     * 
     * @return
     */
    public static String getUTCTime() {
        DateFormat utcFormat = new SimpleDateFormat(UTC_TIME_FORMAT);
        TimeZone utcTime = TimeZone.getTimeZone(TIME_ZONE);
        utcFormat.setTimeZone(utcTime);
        Calendar calendar = java.util.Calendar.getInstance();
        return utcFormat.format(calendar.getTime());
    }

    /**
     * 验证请求是否超时
     * 
     * @param timestamp 待验证的时间
     * @param time 时长
     * @param timeUnit 时间单位
     */
    public static void checkTimeout(String timestamp, long expireTime, TimeUnit timeUnit) {
        DateFormat utcFormat = new SimpleDateFormat(UTC_TIME_FORMAT);
        TimeZone utcTime = TimeZone.getTimeZone(TIME_ZONE);
        utcFormat.setTimeZone(utcTime);
        Date dt = null;
        try {
            dt = utcFormat.parse(timestamp);

        } catch (ParseException e) {
            throw new RuntimeException("时间格式异常", e);
        }
        Date now = new Date();
        if (now.getTime() - dt.getTime() > timeUnit.toMillis(expireTime)) {
            throw new RuntimeException("请求已过期");
        }
    }
}
