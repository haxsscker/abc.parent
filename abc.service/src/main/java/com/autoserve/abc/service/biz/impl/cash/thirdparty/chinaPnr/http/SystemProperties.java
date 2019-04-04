package com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class SystemProperties {
	/**
	 * 读取SRC下面的配置文件，根据key获取value
	 * 
	 * @param key
	 * @return
	 */
	public static String getDBString(String key) {
		Properties props = new Properties();
		try {
			String pathString= SystemProperties.class.getClassLoader().getResource("config.properties").getFile();
			//pathString=pathString.substring(pathString.indexOf("file:/")+6);
			pathString =  pathString.replace("%20"," ");
			FileInputStream inputStream=new FileInputStream(pathString);
			props.load(inputStream);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String value = props.getProperty(key);
		
		if (value == null) {
			return "";
		}
		return value;
	}
	
	public static String getSysString(String key) {
		Properties props = new Properties();
		try {
			String pathString = SystemProperties.class.getClassLoader().getResource("/").getPath();
			//windows下
			if("\\".equals(File.separator)){   
				//pathString  = pathString.substring(1,pathString.indexOf("/WEB-INF/classes"));
				pathString = pathString.replace("/", "\\");
			}
			//linux下
			if("/".equals(File.separator)){   
				//pathString  = pathString.substring(0,pathString.indexOf("/WEB-INF/classes"));
				pathString = pathString.replace("\\", "/");
			}
			pathString = pathString +"system.properties";
			//pathString=pathString.substring(pathString.indexOf("file:/")+6);
			pathString =  pathString.replace("%20"," ");
			FileInputStream inputStream=new FileInputStream(pathString);
			props.load(inputStream);
			inputStream.close();
		} catch (IOException e) {
		}
		String value = props.getProperty(key);
		
		if (value == null) {
			return "";
		}
		return value;
	}
	
	public static String getSrcString(String key) {
		Properties props = new Properties();
		try {
			String pathString = SystemProperties.class.getClassLoader().getResource("/").getPath();
			if("/".equals(File.separator)){   
				pathString = pathString.replace("\\",File.separator);
			}
			pathString = pathString +"mpi.properties";
			pathString =  pathString.replace("%20"," ");
			FileInputStream inputStream=new FileInputStream(pathString);
			props.load(inputStream);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String value = props.getProperty(key);
		
		if (value == null) {
			return "";
		}
		return value;
	}

	 // 写入properties信息  
	public static void writeProperties(String filePath, String parameterName,  String parameterValue) {
		Properties prop = new Properties();
		try {
			if("\\".equals(File.separator)){   
				filePath = filePath.replace("/", "\\");
			}
			if("/".equals(File.separator)){   
				filePath = filePath.replace("\\", "/");
			}
			filePath =  filePath.replace("%20"," ");
			InputStream fis = new FileInputStream(filePath);  // 从输入流中读取属性列表（键和元素对）
			prop.load(fis);  // 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			OutputStream fos = new FileOutputStream(filePath);
			prop.setProperty(parameterName, parameterValue);  // 以适合使用 load 方法加载到 Properties表中的格式，  
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			prop.store(fos, "Update '" + parameterName+ "' value"); 
		} 
		catch (IOException e) {
			System.err.println("Visit " + filePath + " for updating "  + parameterName + " value error");  
		}
	}
	
	public static String getChinaPrnString(String key) {
		Properties props = new Properties();
		try {
			//String s =SystemProperties.class.getClassLoader().getResource("/").toString();
			String pathString = SystemProperties.class.getClassLoader().getResource("/").getPath();
			if("/".equals(File.separator)){   
				pathString = pathString.replace("\\",File.separator);
			}
			pathString = pathString +"chinapnr.properties";
			pathString =  pathString.replace("%20"," ");
			FileInputStream inputStream=new FileInputStream(pathString);
			props.load(inputStream);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String value = props.getProperty(key);
		value = value.replaceAll(" ","");
		if (value == null) {
			return "";
		}
		return value;
	}
	//获取src文件编译后的目录
	public static String getSysPath() {		
			String pathString = SystemProperties.class.getClassLoader().getResource("/").getPath();
			//windows下
			if("\\".equals(File.separator)){   
				//pathString  = pathString.substring(1,pathString.indexOf("/WEB-INF/classes"));
				pathString=pathString.substring(1);
				pathString = pathString.replace("/", "\\");
			}
			//linux下
			if("/".equals(File.separator)){   
				//pathString  = pathString.substring(0,pathString.indexOf("/WEB-INF/classes"));
				pathString = pathString.replace("\\", "/");
			}
			pathString =  pathString.replace("%20"," ");
			return	pathString; 
	}
}
