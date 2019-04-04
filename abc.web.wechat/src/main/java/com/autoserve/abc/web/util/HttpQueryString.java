package com.autoserve.abc.web.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpQueryString {

	public static Map<String, String> convertQueryStringToMap(String query) {  
	    String[] params = query.split("&");  
	    Map<String, String> map = new HashMap<String, String>();  
	    for (String param : params) {  
	        String name = param.split("=")[0];  
	        String value = param.split("=")[1];  
	        map.put(name, value);  
	    }
	    
	    return map;  
	}
	
	
	public static String buildHttpQuery(Map<String, String> queryMap) {
		StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> entry : queryMap.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                urlEncodeUTF8(entry.getKey().toString()),
                urlEncodeUTF8(entry.getValue().toString())
            ));
        }
        
        return sb.toString();  
	}
	
	
    private static String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }
	
	
}
