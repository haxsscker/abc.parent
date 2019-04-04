package com.autoserve.abc.web.convert;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 类ConvertUtils.java的实现描述：TODO 类实现描述
 * 
 * @author pp 2014-11-24 下午04:36:58
 */
public class ConvertUtils {
    private static Logger logger = LoggerFactory.getLogger(ConvertUtils.class);

    public static boolean convert(Object source, Object des) {
        try {

            logger.info("source is : " + JSON.toJSONString(source) + "  prompt is : " + JSON.toJSONString(des));
            BeanUtils.copyProperties(des, source);
            return true;
        } catch (Exception e) {
            logger.info("bean转换异常");
            return false;
        }
    }

}
