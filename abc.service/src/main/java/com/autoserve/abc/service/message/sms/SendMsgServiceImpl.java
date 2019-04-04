package com.autoserve.abc.service.message.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autoserve.abc.service.util.Md5Util;

public class SendMsgServiceImpl implements SendMsgService {

	private static final Logger logger = LoggerFactory
			.getLogger(SendMsgServiceImpl.class);

	private String messageAccount;
	private String messagePassword;
	private String soapObjectNamespace;
	private String soapObjectName;
	private String androidHttpTransportURL;
	private String transportCallURL;

	private String msgHttpSendFlag;
	private String msgHttpSendUrl;
	private String msgHttpAccount;
	private String msgHttpPassword;

	@Override
	public boolean sendMsg(String telephone, String content, String personName,
			String messageTypeId) {
		logger.info("telephone：{}，content：{}，personName：{}，messageTypeId：{}",telephone,content,personName,messageTypeId);
		if ("0".equals(msgHttpSendFlag)) {
			String md5pass = Md5Util.md5(messagePassword);
			SoapObject request = new SoapObject(soapObjectNamespace,
					soapObjectName);
			request.addProperty("orgAccount", messageAccount);
			request.addProperty("orgAccountPwd", md5pass);
			request.addProperty("messageTypeId", messageTypeId);
			request.addProperty("content", content);
			request.addProperty("mobile", telephone);
			request.addProperty("personName", personName);
			// 获得序列化的Envelope
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.bodyOut = request;
			AndroidHttpTransport transport = new AndroidHttpTransport(
					androidHttpTransportURL);
			transport.debug = true;
			// 注册Envelope
			(new MarshalBase64()).register(envelope);
			// 调用WebService5
			try {
				transport.call(transportCallURL, envelope);
				Object result = envelope.getResponse();

				if (logger.isInfoEnabled()) {
					logger.info(String.valueOf(request.getProperty(3)));
					logger.info(String.valueOf(result));
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("短信发送失败!");
				return false;
			}
		} else if ("1".equals(msgHttpSendFlag)) {
			PostMethod method = null;
			InputStream is = null;

			try {
				String sign = "新华久久";

				// 创建StringBuffer对象用来操作字符串
				StringBuffer sb = new StringBuffer(msgHttpSendUrl);

				// 向StringBuffer追加用户名
				sb.append("name=").append(msgHttpAccount);

				// 向StringBuffer追加密码（登陆网页版，在管理中心--基本资料--接口密码，是28位的）
				sb.append("&pwd=").append(msgHttpPassword);

				// 向StringBuffer追加手机号码
				sb.append("&mobile=").append(telephone);

				// 向StringBuffer追加消息内容转URL标准码
				sb.append("&content=" + URLEncoder.encode(content, "UTF-8"));

				// 追加发送时间，可为空，为空为及时发送
				sb.append("&stime=");

				// 加签名
				sb.append("&sign=" + URLEncoder.encode(sign, "UTF-8"));

				// type为固定值pt extno为扩展码，必须为数字 可为空
				sb.append("&type=pt&extno=");
				// 创建url对象
				// System.out.println("sb:"+sb.toString());
				HttpClient client = new HttpClient();

				method = new PostMethod(sb.toString());

				HttpMethodParams param = method.getParams();
				param.setContentCharset("UTF-8");

				client.executeMethod(method);
				// 打印服务器返回的状态
//				System.out.println(method.getStatusLine());
				// 打印返回的信息
				is = method.getResponseBodyAsStream();

				BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				StringBuffer buf = new StringBuffer();
				String line;
				while (null != (line = br.readLine())) {
					buf.append(line).append("\n");
				}
				System.out.println("Httpstatus:"+method.getStatusLine()+",Response:"+buf.toString()+",mobile:"+telephone+",content:"+content);
				// 返回发送结果
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("短信发送失败!");
				return false;
			} finally {
				if (null != is)
				{
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (null != method) {
					// 释放连接
					method.releaseConnection();
				}
			}
		}
		else
		{
			try {
				// 必须的验证参数
		        String username = "100259";
		        String password = "456321";
		        String url = "http://61.129.57.233:7891/mt";
		        
		        // 短信相关的必须参数
		        
		        // 计算签名，注意这里的消息内容转字节时，编码对应dc=15
//		        byte[] messageBytes = content.getBytes("UTF-8");
//		        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//		        String timestamp = sdf.format(new java.util.Date() );
//		        String sign = calculateSign(username, password, timestamp, messageBytes);
		        
		        StringBuilder sb = new StringBuilder(200);
		        sb.append(url);
		        sb.append("?dc=15"); // 表明发送的是中文
		        sb.append("&sm=").append( URLEncoder.encode("【新华久久】"+content, "UTF-8") );
		        sb.append("&da=").append( telephone );
		        sb.append("&un=").append( username );
		        sb.append("&pw=").append( password ); 
		        sb.append("&tf=3"); // 表示短信内容为 urlencode+utf8
		        sb.append("&rd=1"); // 需要状态报告
		        sb.append("&rf=2"); 
		        
		        // 装配GET所需的参数 
//		        StringBuilder sb = new StringBuilder(200);
//		        sb.append( url );
//		        sb.append("?dc=15"); // 表明发送的是中文dc=15
//		        sb.append("&sm=").append( Hex.encodeHexString( messageBytes ) ); // HEX方式
//		        sb.append("&da=").append( telephone );
//		        sb.append("&un=").append( username );
//		        sb.append("&pw=").append( URLEncoder.encode( sign, "UTF-8") ); // 这里使用签名,不是密码
//		        sb.append("&ts=").append(timestamp); // 指示服务器使用签名(数字摘要)验证方式
//		        sb.append("&tf=0"); // 表示短信内容为 HEX
//		        sb.append("&rd=1"); // 需要状态报告
		        
		        String request = sb.toString();
		        System.out.println( request );
		        
		        // 以GET方式发起请求
				System.out.println("result: " + httpGet(request));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return true;
	}
	
	@Override
	public boolean sendMsg(String[] telephone, String content) {

		PostMethod method = null;
		InputStream is = null;

		try {
			StringBuffer mobiles = new StringBuffer();
			if (null == telephone || telephone.length < 1)
			{
				return false;
			}
			else
			{
				for (int i=0;i<telephone.length;i++)
				{
					if (i != 0)
					{
						mobiles.append(',').append(telephone[i]);
					}
					else
					{
						mobiles.append(telephone[i]);
					}
				}
			}
			
			String sign = "新华久久贷";

			// 创建StringBuffer对象用来操作字符串
			StringBuffer sb = new StringBuffer(msgHttpSendUrl);

			// 向StringBuffer追加用户名
			sb.append("name=").append(msgHttpAccount);

			// 向StringBuffer追加密码（登陆网页版，在管理中心--基本资料--接口密码，是28位的）
			sb.append("&pwd=").append(msgHttpPassword);

			// 向StringBuffer追加手机号码
			sb.append("&mobile=").append(mobiles.toString());

			// 向StringBuffer追加消息内容转URL标准码
			sb.append("&content=" + URLEncoder.encode(content, "UTF-8"));

			// 追加发送时间，可为空，为空为及时发送
			sb.append("&stime=");

			// 加签名
			sb.append("&sign=" + URLEncoder.encode(sign, "UTF-8"));

			// type为固定值pt extno为扩展码，必须为数字 可为空
			sb.append("&type=pt&extno=");
			// 创建url对象
			// System.out.println("sb:"+sb.toString());
			HttpClient client = new HttpClient();

			method = new PostMethod(sb.toString());

			HttpMethodParams param = method.getParams();
			param.setContentCharset("UTF-8");

			client.executeMethod(method);
			// 打印服务器返回的状态
//			System.out.println(method.getStatusLine());
			// 打印返回的信息
			is = method.getResponseBodyAsStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuffer buf = new StringBuffer();
			String line;
			while (null != (line = br.readLine())) {
				buf.append(line).append("\n");
			}
			System.out.println("Httpstatus:"+method.getStatusLine()+",Response:"+buf.toString()+",mobile:"+telephone.toString()+",content:"+content);
			// 返回发送结果
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("短信发送失败!");
			return false;
		} finally {
			if (null != is)
			{
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != method) {
				// 释放连接
				method.releaseConnection();
			}
		}

		return true;
	}
	
	/**
	 * JSON+HTTP GET
	 * 
	 * @param url
	 * @param jsonStr
	 * @return String
	 * @throws CommException
	 */
	public String httpGet(String url)
			throws Exception {
		HttpClient httpClient = new HttpClient();

		GetMethod getMethod = new GetMethod(url);

		// ���ñ���,httppostͬʱ���ñ������url.encode
		httpClient.getParams().setContentCharset("UTF-8");
		getMethod.addRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		// ִ��postMethod
		int statusCode = 0;
		try {
			statusCode = httpClient.executeMethod(getMethod);
		} catch (HttpException e) {
		} catch (IOException e) {
		}
		// HttpClient����Ҫ����ܺ�̷����������POST��PUT�Ȳ����Զ�����ת��
		// 301����302
		if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
				|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
			// ��ͷ��ȡ��ת��ĵ�ַ
			Header locationHeader = getMethod.getResponseHeader("location");
			String location = null;
			if (locationHeader != null) {
				location = locationHeader.getValue();
				logger.error("The page was redirected to:" + location);
			} else {
				logger.error("Location field value is null.");
			}
			return null;
		} else {
			String str = "";
			try {
				str = getMethod.getResponseBodyAsString();
				return str;
			} catch (IOException e) {
				// logger.error(e.getMessage());
			}
		}
		return null;
	}

	public static String calculateSign(String username, String password, String timestamp, byte[] message) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update( username.getBytes("UTF8") );
        md.update( password.getBytes("UTF8") );
        md.update( timestamp.getBytes("UTF8") );
        md.update( message );
        byte[] md5result = md.digest();
        return Base64.encodeBase64String( md5result );
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

	public void setTransportCallURL(String transportCallURL) {
		this.transportCallURL = transportCallURL;
	}

	public void setMsgHttpSendFlag(String msgHttpSendFlag) {
		this.msgHttpSendFlag = msgHttpSendFlag;
	}

	public void setMsgHttpSendUrl(String msgHttpSendUrl) {
		this.msgHttpSendUrl = msgHttpSendUrl;
	}

	public void setMsgHttpAccount(String msgHttpAccount) {
		this.msgHttpAccount = msgHttpAccount;
	}

	public void setMsgHttpPassword(String msgHttpPassword) {
		this.msgHttpPassword = msgHttpPassword;
	}

	/**
	 * 转换返回值类型为UTF-8格式.
	 * 
	 * @param is
	 * @return
	 */
	public String convertStreamToString(InputStream is) {
		StringBuilder sb1 = new StringBuilder();
		byte[] bytes = new byte[4096];
		int size = 0;

		try {
			while ((size = is.read(bytes)) > 0) {
				String str = new String(bytes, 0, size, "UTF-8");
				sb1.append(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != is) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb1.toString();
	}

}
