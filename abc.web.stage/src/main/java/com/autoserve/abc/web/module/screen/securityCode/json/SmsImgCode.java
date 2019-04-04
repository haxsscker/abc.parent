package com.autoserve.abc.web.module.screen.securityCode.json;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Encoder;

import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.Md5Encrypt;
import com.autoserve.abc.service.util.SecurityCode;
import com.autoserve.abc.service.util.SecurityCode.SecurityCodeLevel;
import com.autoserve.abc.service.util.SecurityImage;

/**
 * 生成验证码
 * @author DS
 *
 * 2015年4月28日上午9:30:31
 */
public class SmsImgCode {
    private static final Logger logger = LoggerFactory.getLogger(SmsImgCode.class);
    @Resource
    private HttpSession session;
    @Resource
    private HttpServletResponse response;
    public PlainResult<String> execute() {
    	PlainResult<String> result = new PlainResult<String>();
    	ByteArrayInputStream imageStream = null;
    	try {
    		response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0L);
            response.setContentType("image/jpeg");
    		// 如果开启Hard模式，可以不区分大小写
    		String securityCode = SecurityCode.getSecurityCode(4,SecurityCodeLevel.Hard, false);
    		imageStream = SecurityImage.getImageAsInputStream(securityCode);
    		byte[] data = null;  
    		data = new byte[imageStream.available()];  
    		imageStream.read(data);  
    		// 对字节数组Base64编码  
    		BASE64Encoder encoder = new BASE64Encoder();
			// 返回Base64编码过的字节数组字符串
    		String base64ImageStr = encoder.encode(data);
    		result.setData(base64ImageStr);
    		// 放入session中
    		String md5SecurityCode = Md5Encrypt.md5(securityCode);
    		session.setAttribute("smsSecurityCode", md5SecurityCode);//发送短信验证码，区别其他图形验证码
    		logger.info("发送短信图形验证码："+securityCode+";"+md5SecurityCode);
    		return result;
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.setSuccess(false);
			return result;
		}finally{
			if(imageStream!=null){
				try {
					imageStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
    }
}
