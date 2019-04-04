package com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.http;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import chinapnr.Base64;

public class HttpClientHandler {

    /** 汇付HOST **/
    //public static final String HTTP_HOST = "http://test.chinapnr.com/muser/publicRequests";

    /**
     * MAP类型数组转换成NameValuePair类型
     * 
     */
    public static List<NameValuePair> buildNameValuePair(Map<String, String> params) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }

        return nvps;
    }

    public static String getBase64Encode(String str) {
        if (str == null || "".equals(str)) {
            return "";
        }
        try {
            byte[] bt = str.getBytes("UTF-8");
            str = String.valueOf(Base64.encode(bt));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getBase64Decode(String str) {
        if (str == null || "".equals(str)) {
            return "";
        }
        char[] ch = str.toCharArray();
        byte[] bt = Base64.decode(ch);
        try {
            str = new String(bt,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    
//    public static String doHttp(String urlStr,String charSet,Map<String, String> parameters, String timeOut) throws Exception{
//    	String responseString="";
//    	PostMethod xmlpost;
// 	    int statusCode = 0;
// 	    HttpClient httpclient = new HttpClient();
// 	    httpclient.setConnectionTimeout(new Integer(timeOut).intValue());
// 	    xmlpost = new PostMethod(urlStr);
// 	    httpclient.getParams().setParameter(
// 	    		HttpMethodParams.HTTP_CONTENT_CHARSET, charSet);
//        try{
//        	Map<String,String> map = parameters;
//        	
//			NameValuePair[] nvps=new NameValuePair[map.size()];
//			int i=0;
//			for(String key : map.keySet()){
//				nvps[i] = new NameValuePair(key, map.get(key));
//				i++;
//			}
//			xmlpost.setRequestBody(nvps);
//        	statusCode = httpclient.executeMethod(xmlpost);
//	    	responseString = xmlpost.getResponseBodyAsString();
//            if(statusCode<HttpURLConnection.HTTP_OK || statusCode>=HttpURLConnection.HTTP_MULT_CHOICE){
//                System.err.println("失败返回码[" + statusCode + "]");
//                throw new Exception("请求接口失败，失败码[ "+ statusCode +" ]");
//            }
//        }catch(IOException e){
//            e.printStackTrace();
//            System.err.println(e.toString());
//            throw e;
//        }
//        return responseString;
//    }
}
