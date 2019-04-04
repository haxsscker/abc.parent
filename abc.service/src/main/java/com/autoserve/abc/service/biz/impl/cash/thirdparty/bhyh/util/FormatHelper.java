package com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.data.HexStringByte;

public class FormatHelper {
	/**金额为分的格式 */    
    public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";   
	/**
	 * 
	 * <b>方法：</b>getDate<br>
	 * <b>描述：</b>格式化日期字符串<br>
	 * <b>作者：</b>sunlu<br>
	 * <b>时间：</b>2018-03-29 17:46:03<br>
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 * 
	 */
	public static String formatDate(String dateStr, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(date);
	}

	/**
	 * 
	 * <b>方法：</b>formatDate<br>
	 * <b>描述：</b>格式化Date<br>
	 * <b>作者：</b>sunlu<br>
	 * <b>时间：</b>2018-03-29 17:46:28<br>
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 * 
	 */
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	/**
	 * dateStr转Date
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date formatStr2Date(String dateStr, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 比较2个日期大小 --去除时分秒
	 * date1>date2返回1，date1=date2返回0，否则-1
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareToDate(Date date1, Date date2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String s1 = sdf.format(date1);
		String s2 = sdf.format(date2);
		Date d1 = null;
		try {
			d1 = sdf.parse(s1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date d2 = null;
		try {
			d2 = sdf.parse(s2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (d1.before(d2)){//表d1<d2 
			return -1;
		}else if(d1.equals(d2)){ 
			return 0;
		}else{
			return 1;
		}
	}
	/**
	 * 
	 * <b>方法：</b>formatNumeric<br>
	 * <b>描述：</b>格式化数字<br>
	 * <b>作者：</b>sunlu<br>
	 * <b>时间：</b>2018-03-29 17:46:47<br>
	 * 
	 * @param numericStr
	 * @param precision
	 * @return
	 * 
	 */
	public static String formatNumeric(Object numeric, int precision) {
		DecimalFormat df = new DecimalFormat("#############0." + String.format("%0" + precision + "d", 0));
		return df.format(Double.parseDouble(numeric.toString()));
	}
	/**   
     * 将元为单位的转换为分 替换小数点，支持以逗号区分的金额  
     *   
     * @param amount  
     * @return  
     */    
    public static String changeY2F(Object amount){
    	String amountStr=formatNumeric(amount,2);
        String currency =  amountStr.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额    
        int index = currency.indexOf(".");    
        int length = currency.length();    
        Long amLong = 0l;    
        if(index == -1){    
            amLong = Long.valueOf(currency+"00");    
        }else if(length - index >= 3){    
            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));    
        }else if(length - index == 2){    
            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);    
        }else{    
            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");    
        }    
        return amLong.toString();    
    }
    /**  
     * 将分为单位的转换为元 （除100）  
     *   
     * @param amount  
     * @return  
     * @throws Exception   
     */    
    public static String changeF2Y(String amount){ 
        try {
			if(!amount.matches(CURRENCY_FEN_REGEX)) {    
			    throw new Exception("金额格式有误");    
			}
		} catch (Exception e) {
			e.printStackTrace();
			return amount;
		}    
        return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)).toString();    
    } 
  /**
   * 中文转换成UTF-8编码（16进制字符串)，每个汉字3个字节  
   * @param chineseStr
   * @return
   * @throws Exception
   */

    public static String Chinese2UTF_8(String chineseStr)throws Exception {  
      StringBuffer utf8Str = new StringBuffer();  
      byte[] utf8Decode = chineseStr.getBytes("utf-8");  
      for (byte b : utf8Decode)   
          utf8Str.append(Integer.toHexString(b&0xFF));  
      return utf8Str.toString().toUpperCase();  
    }   
    
    /**
     * 中文转换成GBK码(16进制字符串)，每个汉字2个字节  
     * @param chineseStr
     * @return
     * @throws Exception
     */
    public static String Chinese2GBK(String chineseStr){  
      StringBuffer GBKStr = new StringBuffer();  
      try {
		byte[] GBKDecode = chineseStr.getBytes("GBK");  
		  for (byte b : GBKDecode)   
		      GBKStr.append("%" + Integer.toHexString(b&0xFF));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}  
      return GBKStr.toString().toUpperCase();  
      }  
        
        
    /**
     * 16进制GBK字符串转换成中文  
     * @param GBKStr
     * @return
     * @throws Exception
     */
    public static String GBK2Chinese(String GBKStr){  
    	if(null==GBKStr){
    		return "";
    	}
    	String chineseStr = GBKStr;
    	if(GBKStr.contains("%")){
    		GBKStr=GBKStr.replaceAll("%", "");
    		try {
    			byte[] b = HexStringByte.HexString2Bytes(GBKStr);  
    			chineseStr = new String(b, "GBK");
    		} catch (UnsupportedEncodingException e) {
    			e.printStackTrace();
    		}//输入参数为字节数组  
    	}
	      return chineseStr;  
     }  
    /**
     * 判断字符串编码
     * @param str
     * @return
     */
    public static String getEncoding(String str) {    
        String encode = "GB2312";    
       try {    
           if (str.equals(new String(str.getBytes(encode), encode))) {    
                String s = encode;    
               return s;    
            }    
        } catch (Exception exception) {    
        }    
        encode = "ISO-8859-1";    
       try {    
           if (str.equals(new String(str.getBytes(encode), encode))) {    
                String s1 = encode;    
               return s1;    
            }    
        } catch (Exception exception1) {    
        }    
        encode = "UTF-8";    
       try {    
           if (str.equals(new String(str.getBytes(encode), encode))) {    
                String s2 = encode;    
               return s2;    
            }    
        } catch (Exception exception2) {    
        }    
        encode = "GBK";    
       try {    
           if (str.equals(new String(str.getBytes(encode), encode))) {    
                String s3 = encode;    
               return s3;    
            }    
        } catch (Exception exception3) {    
        }    
       return "";    
    } 
    /**
     * 对字符串进行gbk编码
     * @param str
     * @return
     */
    public static String GBKEncodeStr(String str){  
    	String res="";
    	if(null==str){
    		return "";
    	}
    	try {
			res = URLEncoder.encode(str,"gbk");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return res;
     }  
    /**
     * 对字符串进行gbk解码
     * @param str
     * @return
     */
    public static String GBKDecodeStr(String str){  
    	String res="";
    	if(null==str){
    		return "";
    	}
    	try {
    		if(str.contains("%")){
    			res = URLDecoder.decode(str,"gbk");
    		}else{
    			return str;
    		}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return res;
     }
    /**
     * 格式化路径
     * @param pathString
     * @return
     */
    public static String formatPath(String pathString) { 
    	//windows下
    	if("\\".equals(File.separator)){   
			pathString=pathString.substring(1);
			pathString = pathString.replace("/", "\\");
		}
		//linux下
		if("/".equals(File.separator)){   
			pathString = pathString.replace("\\", "/");
		}
		pathString =  pathString.replace("%20"," ");
		return	pathString; 
    }
    /**
     * ftp文件内容格式化
     * @param ftpFileContentMap
     * key 汇总行summaryMap , value类型:LinkedHashMap<String, String>，
     * key 明细行detailListMap , value类型:List<LinkedHashMap<String, String>>
     * @return
     */
    public static String formatFtpFileContent(Map<String, Object> ftpFileContentMap) { 
    	StringBuffer result=new StringBuffer();
    	if(null != ftpFileContentMap.get("summaryMap")){
    		Map<String, String> summaryMap=(Map<String, String>) ftpFileContentMap.get("summaryMap");//汇总行
    		int i=0;
    		for (Entry<String, String> vo : summaryMap.entrySet()) {
    			result.append(null != vo.getValue()?vo.getValue():"");
    			if(i<summaryMap.size()){
    				result.append("|");
    			}
    			i++;
    		}
    		result.append(FileUtils.LINE_SEPARATOR);
    	}
    	if(null != ftpFileContentMap.get("detailListMap")){
    		List<Map<String, String>> detailListMap=(List<Map<String, String>>) ftpFileContentMap.get("detailListMap");//明细行
    		for(Map<String, String> detailMap : detailListMap){
    			int i=0;
        		for (Entry<String, String> vo : detailMap.entrySet()) {
        			result.append(null != vo.getValue()?vo.getValue():"");
        			if(i<detailMap.size()){
        				result.append("|");
        			}
        			i++;
        		}
        		result.append(FileUtils.LINE_SEPARATOR);
    		}
    	}
    	
		return	result.toString(); 
    }
	public static void main(String[] args) {
		String v="PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iR0IyMzEyIj8+CiAgPHZpaW4+CiAgICA8cnM+CiAgICAgIDxBY2RhdGU+MjAxODA0MjA8L0FjZGF0ZT4KICAgICAgPFRyYW5zSWQ+MjAxODA0MjAwMDA3MDU2OTI0PC9UcmFuc0lkPgogICAgICA8VHJhbnNBbXQ+MTAwMDAwMDwvVHJhbnNBbXQ+CiAgICAgIDxGZWVBbXQ+MzAwPC9GZWVBbXQ+CiAgICAgIDxUcmFuc1N0YXQ+UzE8L1RyYW5zU3RhdD4KICAgICAgPEZhbFJzbj5TVUNDRVNTPC9GYWxSc24+CiAgICA8L3JzPgogIDwvdmlpbj4K";
		System.out.println(GBK2Chinese("8000551000100010000001060631561"));
		System.out.println(GBK2Chinese("PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iR0IyMzEyIj8+CiAgPHZpaW4+CiAgICA8cnM+CiAgICAgIDxBY2RhdGU+MjAxODA0MjA8L0FjZGF0ZT4KICAgICAgPFRyYW5zSWQ+MjAxODA0MjAwMDA3MDU2OTI0PC9UcmFuc0lkPgogICAgICA8VHJhbnNBbXQ+MTAwMDAwMDwvVHJhbnNBbXQ+CiAgICAgIDxGZWVBbXQ+MzAwPC9GZWVBbXQ+CiAgICAgIDxUcmFuc1N0YXQ+UzE8L1RyYW5zU3RhdD4KICAgICAgPEZhbFJzbj5TVUNDRVNTPC9GYWxSc24+CiAgICA8L3JzPgogIDwvdmlpbj4K"));
		System.out.println(GBK2Chinese("2%D0%D0%CE%C4%BC%FE%B4%A6%C0%ED%CA%A7%B0%DC"));
		
		System.out.println(GBKDecodeStr("8000551000100010000001060631561"));
		System.out.println(GBKDecodeStr("PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iR0IyMzEyIj8+CiAgPHZpaW4+CiAgICA8cnM+CiAgICAgIDxBY2RhdGU+MjAxODA0MjA8L0FjZGF0ZT4KICAgICAgPFRyYW5zSWQ+MjAxODA0MjAwMDA3MDU2OTI0PC9UcmFuc0lkPgogICAgICA8VHJhbnNBbXQ+MTAwMDAwMDwvVHJhbnNBbXQ+CiAgICAgIDxGZWVBbXQ+MzAwPC9GZWVBbXQ+CiAgICAgIDxUcmFuc1N0YXQ+UzE8L1RyYW5zU3RhdD4KICAgICAgPEZhbFJzbj5TVUNDRVNTPC9GYWxSc24+CiAgICA8L3JzPgogIDwvdmlpbj4K"));
		System.out.println(GBKDecodeStr("2%D0%D0%CE%C4%BC%FE%B4%A6%C0%ED%CA%A7%B0%DC"));
		System.out.println(GBKEncodeStr("+"));
	}
}
