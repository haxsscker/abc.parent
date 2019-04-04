package com.autoserve.abc.service.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.NullLogChute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 组装第三方请求数据
 * 
 * @author pp 2014-11-25 下午04:02:59
 */
public class VelocityUtil {
    private static Logger logger = LoggerFactory.getLogger(VelocityUtil.class);
    static {
        try {
            Properties p = new Properties();
            p.setProperty(RuntimeConstants.INPUT_ENCODING, "GBK");
            p.setProperty(RuntimeConstants.OUTPUT_ENCODING, "GBK");
            Velocity.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, new NullLogChute());
            Velocity.init(p);
        } catch (Exception e) {
            logger.info(e.toString());
        }
    }

    public static String evaluate(Map<String, Object> map, InputStream is) {
        StringWriter writer = null;
        InputStreamReader reader = null;
        try {
            writer = new StringWriter();
            reader = new InputStreamReader(is, "GBK");
            Velocity.evaluate(convertVelocityContext(map), writer, "velocity", reader);
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }

        }
        return null;
    }

    /**
     * 此方法可以自定义velocity函数
     * 
     * @param map
     * @return
     */
    static VelocityContext convertVelocityContext(Map<String, Object> map) {
        VelocityContext context = new VelocityContext();
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                context.put(entry.getKey(), entry.getValue());
            }
        }
        return context;
    }

    private VelocityUtil() {
    }
}
