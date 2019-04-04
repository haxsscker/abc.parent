package com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.data;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpClientError;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.data.HttpSendResult;

public class SimpleHttpsClient {
	private static Logger logger = LoggerFactory.getLogger(SimpleHttpsClient.class);
	private Map<Integer, Integer> registerPortList = new HashMap();

	public SimpleHttpsClient() {
		Protocol.registerProtocol("https", new Protocol("https",
				new SimpleHttpsClient.SimpleHttpsSocketFactory(), 443));
		this.registerPort(Integer.valueOf(443));
	}

	public HttpSendResult postRequest(String url, Map<String, String> params,
			int timeout, String characterSet) {
		if (characterSet == null || "".equals(characterSet)) {
			characterSet = "UTF-8";
		}

		HttpSendResult result = new HttpSendResult();
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Connection", "close");
		postMethod.addRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=" + characterSet);
		NameValuePair[] data = this.createNameValuePair(params);
		postMethod.setRequestBody(data);
		Integer port = this.getPort(url);
		if (this.isRegisterPort(port)) {
			Protocol client = new Protocol("https",
					new SimpleHttpsClient.SimpleHttpsSocketFactory(),
					port.intValue());
			Protocol.registerProtocol("https ", client);
			this.registerPort(port);
		}

		HttpClient client1 = new HttpClient();
		client1.getParams().setSoTimeout(timeout);

		try {
			int ex = client1.executeMethod(postMethod);
			InputStream is = postMethod.getResponseBodyAsStream();
			String responseBody = IOUtils.toString(is, characterSet);
			result.setStatus(ex);
			result.setResponseBody(responseBody);
		} catch (ConnectTimeoutException e) {
			logger.error("--------------银行接口连接超时，请稍后重试--------------------");
			e.printStackTrace();
        }catch (SocketTimeoutException e) {
			logger.error("--------------读取超时，请稍后查询--------------------");
			e.printStackTrace();
			result.setResponseBody("RED_TIMEOUT");
			return result;
        } catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			postMethod.releaseConnection();
		}

		return result;
	}

	public HttpSendResult postRequest(String url, Map<String, String> params,
			int timeout) {
		return this.postRequest(url, params, timeout, "UTF-8");
	}

	public HttpSendResult getRequest(String url, Map<String, String> params,
			int timeout, String characterSet) {
		if (characterSet == null || "".equals(characterSet)) {
			characterSet = "UTF-8";
		}

		HttpSendResult result = new HttpSendResult();
		Integer port = this.getPort(url);
		if (this.isRegisterPort(port)) {
			Protocol httpclient = new Protocol("https",
					new SimpleHttpsClient.SimpleHttpsSocketFactory(),
					port.intValue());
			Protocol.registerProtocol("https ", httpclient);
			this.registerPort(port);
		}

		url = this.appendUrlParam(url, params);
		HttpClient httpclient1 = new HttpClient();
		GetMethod httpget = new GetMethod(url);

		try {
			int ex = httpclient1.executeMethod(httpget);
			InputStream is = httpget.getResponseBodyAsStream();
			String responseBody = IOUtils.toString(is, characterSet);
			result.setStatus(ex);
			result.setResponseBody(responseBody);
		} catch (Exception arg14) {
			arg14.printStackTrace();
		} finally {
			httpget.releaseConnection();
		}

		return result;
	}

	public HttpSendResult getRequest(String url, Map<String, String> params,
			int timeout) {
		return this.getRequest(url, params, timeout, "UTF-8");
	}

	private boolean isRegisterPort(Integer port) {
		return this.registerPortList.get(port) != null;
	}

	private void registerPort(Integer port) {
		this.registerPortList.put(port, port);
	}

	private Integer getPort(String uri) {
		try {
			URL e = new URL(uri);
			int port = e.getPort();
			if (port == -1) {
				if (uri.indexOf("https://") == 0) {
					port = 443;
				} else {
					port = 80;
				}
			}

			return Integer.valueOf(port);
		} catch (MalformedURLException arg3) {
			throw new RuntimeException(arg3);
		}
	}

	private NameValuePair[] createNameValuePair(Map<String, String> params) {
		NameValuePair[] pairs = new NameValuePair[params.size()];
		int index = 0;

		String key;
		for (Iterator arg4 = params.keySet().iterator(); arg4.hasNext(); pairs[index++] = new NameValuePair(
				key, (String) params.get(key))) {
			key = (String) arg4.next();
		}

		return pairs;
	}

	private String appendUrlParam(String url, Map<String, String> params) {
		String result = "";
		if (url.contains("?") && url.contains("=")) {
			result = url + "&";
		} else {
			result = url + "?";
		}

		String key;
		for (Iterator arg4 = params.keySet().iterator(); arg4.hasNext(); result = result
				+ key + "=" + (String) params.get(key) + "&") {
			key = (String) arg4.next();
		}

		if (result.charAt(result.length() - 1) == 38) {
			result = result.substring(0, result.length() - 1);
		}

		return result;
	}

	private class SimpleHttpsSocketFactory implements ProtocolSocketFactory {
		private SSLContext sslcontext = null;

		private SSLContext createEasySSLContext() {
			try {
				X509TrustManager e = new X509TrustManager() {
					public void checkClientTrusted(
							X509Certificate[] ax509certificate, String s)
							throws CertificateException {
					}

					public void checkServerTrusted(
							X509Certificate[] ax509certificate, String s)
							throws CertificateException {
					}

					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}
				};
				TrustManager[] trustMgrs = new TrustManager[] { e };
				SSLContext context = SSLContext.getInstance("SSL");
				context.init((KeyManager[]) null, trustMgrs,
						(SecureRandom) null);
				return context;
			} catch (Exception arg3) {
				arg3.printStackTrace();
				throw new HttpClientError(arg3.toString());
			}
		}

		private SSLContext getSSLContext() {
			if (this.sslcontext == null) {
				this.sslcontext = this.createEasySSLContext();
			}

			return this.sslcontext;
		}

		public Socket createSocket(String host, int port,
				InetAddress clientHost, int clientPort) throws IOException,
				UnknownHostException {
			return this.getSSLContext().getSocketFactory()
					.createSocket(host, port, clientHost, clientPort);
		}

		public Socket createSocket(String host, int port,
				InetAddress localAddress, int localPort,
				HttpConnectionParams params) throws IOException,
				UnknownHostException, ConnectTimeoutException {
			if (params == null) {
				throw new IllegalArgumentException("Parameters may not be null");
			} else {
				int timeout = params.getConnectionTimeout();
				SSLSocketFactory socketfactory = this.getSSLContext()
						.getSocketFactory();
				if (timeout == 0) {
					return socketfactory.createSocket(host, port, localAddress,
							localPort);
				} else {
					Socket socket = socketfactory.createSocket();
					InetSocketAddress localaddr = new InetSocketAddress(
							localAddress, localPort);
					InetSocketAddress remoteaddr = new InetSocketAddress(host,
							port);
					socket.bind(localaddr);
					socket.connect(remoteaddr, timeout);
					return socket;
				}
			}
		}

		public Socket createSocket(String host, int port) throws IOException,
				UnknownHostException {
			return this.getSSLContext().getSocketFactory()
					.createSocket(host, port);
		}

		public Socket createSocket(Socket socket, String host, int port,
				boolean autoClose) throws IOException, UnknownHostException {
			return this.getSSLContext().getSocketFactory()
					.createSocket(socket, host, port, autoClose);
		}

		public boolean equals(Object obj) {
			return obj != null && obj.getClass().equals(SSLSocketFactory.class);
		}

		public int hashCode() {
			return SimpleHttpsClient.SimpleHttpsSocketFactory.class.hashCode();
		}
	}
}
