package com.autoserve.abc.service.message.identity;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.util.Base64;
import com.autoserve.abc.service.util.Md5Util;

public class SendIdentityServiceImpl implements SendIdentityService {
    private static final Logger logger = LoggerFactory.getLogger(SendIdentityServiceImpl.class);
    private String              messageAccount="";
    private String              messagePassword="";
    private String              soapObjectNamespace="http://lxdFuction.IDCard/";
    private String              soapObjectName="CardIDCheckSJAVA";
    private String              androidHttpTransportURL="http://211.141.249.207:8880/Webservice/IDCardCheckService.asmx";

    @Override
    public Map<String, String> sendIdentity(String realName, String identityNo) {
        Object result;
        String md5pass = Md5Util.md5(messagePassword);
        SoapObject request = new SoapObject(soapObjectNamespace, soapObjectName);
        request.addProperty("orgAccount", messageAccount);
        request.addProperty("orgAccountPwd", md5pass);
        request.addProperty("type", "1");
        request.addProperty("info", realName + "," + identityNo);
        // 获得序列化的Envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = request;
        AndroidHttpTransport transport = new AndroidHttpTransport(androidHttpTransportURL);
        transport.debug = true;
        // 注册Envelope
        (new MarshalBase64()).register(envelope);
        String str;
        String status;
        String sex;
        String policeadd;
        Map<String, String> params = new HashMap<String, String>();
        // 调用WebService5
        try {
            String callUrl = soapObjectNamespace + soapObjectName;
            transport.call(callUrl, envelope);
            result = envelope.getResponse();
            byte[] by = Base64.base64ToByteArray(result.toString());
            str = new String(by, "gb2312");
            Document doc = DocumentHelper.parseText(str);
            Element root = doc.getRootElement();
            Element weighTime = root.element("message");
            status = weighTime.elementText("status");
            //调用实名认证接口成功
            if ("0".equals(status)) {
                Element weigh = root.element("policeCheckInfos");
                Element weigh1 = weigh.element("policeCheckInfo");
                String compStatus = weigh1.elementText("compStatus");
                String compResult = weigh1.elementText("compResult");
                //认证成功
                if ("0".equals(compStatus)) {
                    sex = weigh1.elementText("sex2");
                    params.put("sex", sex);
                    policeadd = weigh1.elementText("policeadd");
                    params.put("policeadd", policeadd);
                }
                //比对结果
                params.put("status", status);
                params.put("compStatus", compStatus);
                params.put("compResult",compResult);
            }else{
            	params.put("status", "1");
            	params.put("compResult","调用实名认证接口失败!");
            }
            if (logger.isInfoEnabled()) {
                logger.info(String.valueOf(request.getProperty(3)));
                logger.info(String.valueOf(result));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("SendIdentityServiceImpl:身份认证失败!");
            return null;
        }
        return params;
    }

    public void setMessageAccount(String messageAccount) {
        this.messageAccount = messageAccount;
    }

    public void setMessagePassword(String messagePassword) {
        this.messagePassword = messagePassword;
    }

    public void setSoapObjectNamespace(String soapObjectNamespace) {
        this.soapObjectNamespace = soapObjectNamespace;
    }

    public void setSoapObjectName(String soapObjectName) {
        this.soapObjectName = soapObjectName;
    }

    public void setAndroidHttpTransportURL(String androidHttpTransportURL) {
        this.androidHttpTransportURL = androidHttpTransportURL;
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
//		String str="PD94bWwgdmVyc2lvbj0nMS4wJyBlbmNvZGluZz0nVVRGLTgnPz48ZGF0YT48bWVzc2FnZT48c3RhdHVzPjA8L3N0YXR1cz48dmFsdWU+tKbA7bPJuaY8L3ZhbHVlPjwvbWVzc2FnZT48cG9saWNlQ2hlY2tJbmZvcz48cG9saWNlQ2hlY2tJbmZvIG5hbWU9J8vvwaK71CcgaWQ9JzU0MjEyODE5NzYwNTIyNjIxNSc+PG1lc3NhZ2U+PHN0YXR1cz4wPC9zdGF0dXM+PHZhbHVlPtDFz6LIz9aks8m5pjwvdmFsdWU+PC9tZXNzYWdlPjxuYW1lIGRlc2M9J9DVw/snPsvvwaK71DwvbmFtZT48aWRlbnRpdHljYXJkIGRlc2M9J8ntt93WpLrFJz41NDIxMjgxOTc2MDUyMjYyMTU8L2lkZW50aXR5Y2FyZD48Y29tcFN0YXR1cyBkZXNjPSexyLbU17TMrCc+MjwvY29tcFN0YXR1cz48Y29tcFJlc3VsdCBkZXNjPSexyLbUveG5+yc+v+LW0M7etMu6xTwvY29tcFJlc3VsdD48L3BvbGljZUNoZWNrSW5mbz48L3BvbGljZUNoZWNrSW5mb3M+PG9wZXJhdFJlc3VsdD60psDts8m5pjwvb3BlcmF0UmVzdWx0PjwvZGF0YT4=";
//    	String str="PD94bWwgdmVyc2lvbj0nMS4wJyBlbmNvZGluZz0nVVRGLTgnPz48ZGF0YT48bWVzc2FnZT48c3RhdHVzPjA8L3N0YXR1cz48dmFsdWU+tKbA7bPJuaY8L3ZhbHVlPjwvbWVzc2FnZT48cG9saWNlQ2hlY2tJbmZvcz48cG9saWNlQ2hlY2tJbmZvIG5hbWU9J7ahy6cnIGlkPSczNDEyMjQxOTkxMDUxMDMzMzInPjxtZXNzYWdlPjxzdGF0dXM+MDwvc3RhdHVzPjx2YWx1ZT7Qxc+iyM/WpLPJuaY8L3ZhbHVlPjwvbWVzc2FnZT48bmFtZSBkZXNjPSfQ1cP7Jz62ocunPC9uYW1lPjxpZGVudGl0eWNhcmQgZGVzYz0nye233dakusUnPjM0MTIyNDE5OTEwNTEwMzMzMjwvaWRlbnRpdHljYXJkPjxjb21wU3RhdHVzIGRlc2M9J7HIttTXtMysJz4wPC9jb21wU3RhdHVzPjxjb21wUmVzdWx0IGRlc2M9J7HIttS94bn7Jz7Su9bCPC9jb21wUmVzdWx0Pjxwb2xpY2VhZGQgZGVzYz0n1K3KvLei1qS12Cc+sLK71cqhw8mzx8/YPC9wb2xpY2VhZGQ+PGNoZWNrUGhvdG8gZGVzYz0n1dXGrCc+bnVsbDwvY2hlY2tQaG90bz48YmlydGhkYXkyIGRlc2M9J7P2yfrI1cbaMic+MTk5MTA1MTA8L2JpcnRoZGF5Mj48c2V4MiBkZXNjPSfQ1LHwMic+xNA8L3NleDI+PC9wb2xpY2VDaGVja0luZm8+PC9wb2xpY2VDaGVja0luZm9zPjxvcGVyYXRSZXN1bHQ+tKbA7bPJuaY8L29wZXJhdFJlc3VsdD48L2RhdGE+";
//    	String str="PD94bWwgdmVyc2lvbj0nMS4wJyBlbmNvZGluZz0nVVRGLTgnPz48ZGF0YT48bWVzc2FnZT48c3RhdHVzPjA8L3N0YXR1cz48dmFsdWU+tKbA7bPJuaY8L3ZhbHVlPjwvbWVzc2FnZT48cG9saWNlQ2hlY2tJbmZvcz48cG9saWNlQ2hlY2tJbmZvIG5hbWU9J7m00MXI8CcgaWQ9JzQzMDEyMjE5NTUwMzA0MDg3WCc+PG1lc3NhZ2U+PHN0YXR1cz4wPC9zdGF0dXM+PHZhbHVlPtDFz6LIz9aks8m5pjwvdmFsdWU+PC9tZXNzYWdlPjxuYW1lIGRlc2M9J9DVw/snPrm00MXI8DwvbmFtZT48aWRlbnRpdHljYXJkIGRlc2M9J8ntt93WpLrFJz40MzAxMjIxOTU1MDMwNDA4N1g8L2lkZW50aXR5Y2FyZD48Y29tcFN0YXR1cyBkZXNjPSexyLbU17TMrCc+MjwvY29tcFN0YXR1cz48Y29tcFJlc3VsdCBkZXNjPSexyLbUveG5+yc+v+LW0M7etMu6xTwvY29tcFJlc3VsdD48L3BvbGljZUNoZWNrSW5mbz48L3BvbGljZUNoZWNrSW5mb3M+PG9wZXJhdFJlc3VsdD60psDts8m5pjwvb3BlcmF0UmVzdWx0PjwvZGF0YT4=";
//    	String str="PD94bWwgdmVyc2lvbj0nMS4wJyBlbmNvZGluZz0nVVRGLTgnPz48ZGF0YT48bWVzc2FnZT48c3RhdHVzPjA8L3N0YXR1cz48dmFsdWU+tKbA7bPJuaY8L3ZhbHVlPjwvbWVzc2FnZT48cG9saWNlQ2hlY2tJbmZvcz48cG9saWNlQ2hlY2tJbmZvIG5hbWU9J9S9yubAvCcgaWQ9JzU0MjEyMjE5NjcwMjE1ODE0Myc+PG1lc3NhZ2U+PHN0YXR1cz4wPC9zdGF0dXM+PHZhbHVlPtDFz6LIz9aks8m5pjwvdmFsdWU+PC9tZXNzYWdlPjxuYW1lIGRlc2M9J9DVw/snPtS9yubAvDwvbmFtZT48aWRlbnRpdHljYXJkIGRlc2M9J8ntt93WpLrFJz41NDIxMjIxOTY3MDIxNTgxNDM8L2lkZW50aXR5Y2FyZD48Y29tcFN0YXR1cyBkZXNjPSexyLbU17TMrCc+MjwvY29tcFN0YXR1cz48Y29tcFJlc3VsdCBkZXNjPSexyLbUveG5+yc+v+LW0M7etMu6xTwvY29tcFJlc3VsdD48L3BvbGljZUNoZWNrSW5mbz48L3BvbGljZUNoZWNrSW5mb3M+PG9wZXJhdFJlc3VsdD60psDts8m5pjwvb3BlcmF0UmVzdWx0PjwvZGF0YT4=";
//		byte[] by = Base64.base64ToByteArray(str.toString());
//        System.out.println(new String(by, "gb2312"));
	}

}
