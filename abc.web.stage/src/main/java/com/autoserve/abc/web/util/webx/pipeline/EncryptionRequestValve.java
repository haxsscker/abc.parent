package com.autoserve.abc.web.util.webx.pipeline;

import static com.alibaba.citrus.turbine.util.TurbineUtil.getTurbineRunData;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.support.AbstractValve;
import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.service.requestcontext.parser.ParserRequestContext;
import com.alibaba.citrus.service.requestcontext.util.RequestContextUtil;
import com.alibaba.citrus.turbine.TurbineRunDataInternal;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.util.Base64Utils;
import com.autoserve.abc.service.util.RSAUtils;
import com.autoserve.abc.service.util.SystemGetPropeties;
import com.autoserve.abc.service.util.AesUtils.AES;
import com.autoserve.abc.web.util.DateUtil;

/**
 * 手机端的请求，进行参数解密
 * @author DS
 *
 * 2015年8月12日 下午6:10:27
 */
public class EncryptionRequestValve extends AbstractValve {

    private static final Logger log = LoggerFactory.getLogger(EncryptionRequestValve.class);

    @Autowired
    private UserService         userService;
    @Autowired
    private HttpServletRequest  request;
    @Autowired
    private HttpServletResponse response;
    /**
     * 是否解密手机端请求
     */
    private static boolean encry = true;
    static {
    	if("false".equals(SystemGetPropeties.getStrString("encry"))){
    		encry = false;
    	}else {
    		encry = true;
    	}
    }

    @Override
    public void invoke(PipelineContext pipelineContext) throws Exception {
	        try {
	        	if(encry && (URLDecoder.decode(request.getRequestURI(), "utf-8").contains("/mobile/") 
	        			|| URLDecoder.decode(request.getRequestURI(), "utf-8").contains("/mobileIos/"))){
	        		try{
	        			log.debug("-------------------------app request start-----------------------------");	
						ParserRequestContext parserRequestContext = RequestContextUtil
								.findRequestContext(request, ParserRequestContext.class);		
						ParameterParser params = parserRequestContext.getParameters();
						String[] keyStrs=params.getKeys();
						for(String keyStr:keyStrs){
							String valueStr=params.getString(keyStr);
							if(valueStr!=null && !"".equals(valueStr)){
								log.debug("解密前===>keyStr:{},valueStr:{}",keyStr,valueStr.replace(" ", "+"));
								if(URLDecoder.decode(request.getRequestURI(), "utf-8").contains("/mobile/")){   //android(rsa解密)
									byte[] encodedData = RSAUtils.decryptByPrivateKey(Base64Utils.decode(valueStr.replace(" ", "+")), SystemGetPropeties.getStrString("serPrivateKey"));
									valueStr=new String(encodedData,"utf-8");
								}else{    //ios(aes解密)
									String iosPasswordKey=SystemGetPropeties.getStrString("iosPasswordKeyPrefix")+DateUtil.calculationAesSuffix();
									valueStr=AES.getInstance(iosPasswordKey).decrypt(valueStr);
								}
								log.debug("解密后===>keyStr:{},valueStr:{}",keyStr,valueStr);
							}
							//userid转换成useruuid
							if("userId".equalsIgnoreCase(keyStr)){
								if(valueStr!=null && !"".equals(valueStr)){
									UserDO userDO=new UserDO();
									userDO.setUserUuid(valueStr);
									ListResult<UserDO> userDOResult=userService.queryList(userDO);
									if(userDOResult.getData().size()==1){
										userDO=userDOResult.getData().get(0);
										valueStr=String.valueOf(userDO.getUserId());
									}									
								}
							}
							params.setObject(keyStr, valueStr);
						}
			        	log.debug("-------------------------app request  end-----------------------------");
	        		}catch(Exception e){
		        		TurbineRunDataInternal rundata = (TurbineRunDataInternal) getTurbineRunData(request);
			        	rundata.forwardTo("/mobile/error.json").end();
			            log.warn(e.getMessage(), e);
		        	}
	        	}
	        } catch (Exception e) {
	        	log.warn(e.getMessage(), e);
	        } finally {
	            pipelineContext.invokeNext();
//	            System.out.println("=====================================");
//	            System.out.println(request.getAttribute("userId"));
//	            Enumeration<?> enumx=request.getAttributeNames();
//	            if(enumx.hasMoreElements()){
//	            	String name=(String)enumx.nextElement();
//	            	System.out.println(name);
//	            	System.out.println(request.getAttribute(name));
//	            }
//	            System.out.println("=====================================");
	        }
    }
}
