package com.autoserve.abc.service.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SystemGetPropeties {
    /**
	 * 从配置文件中获取到私钥和平台信息
	 * @param key
	 * @return
	 */
	public static String getStrString(String key){
		Properties props = new Properties();
		try{
//		String pathString = SystemGetPropeties.class.getClassLoader().getResource("mpi.properties").getFile();
		String pathString = SystemGetPropeties.class.getClassLoader().getResource("p2pmerchant.properties").getFile();
		if("/".equals(File.separator)){   
			pathString = pathString.replace("\\",File.separator);
		}
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
	public static String getChinaPnrString(String key) {
		Properties props = new Properties();
		try {
			String pathString = SystemGetPropeties.class.getClassLoader().getResource("chinapnr.properties").getPath();
			//String pathString="/e:/eclipse/workSpaces/abc.parent/abc.web.huayi/target/classes/chinapnr.properties";
			if("/".equals(File.separator)){   
				pathString = pathString.replace("\\",File.separator);
			}
			//pathString = pathString +"chinapnr.properties"; 
			pathString =  pathString.replace("%20"," ");
			FileInputStream inputStream=new FileInputStream(pathString);
			props.load(inputStream);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String value = props.getProperty(key);
//		value = value.replaceAll(" ","");
//		if (value == null) {
//			return "";
//		}
		return value;
	}
	//获取src文件编译后的目录
	public static String getSysPath() {		
			String pathString = SystemGetPropeties.class.getClassLoader().getResource("/").getPath();
//			String pathString ="D:/Workspaces/huayiworkspace/huayi/abc.web.huayi/target/classes";
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
	
	//获取boss-abc.properties 配置文件参数
    public static String getBossString(String key) {
        Properties props = new Properties();
        try {
            String pathString = SystemGetPropeties.class.getClassLoader().getResource("boss-abc.properties").getPath();
            if ("/".equals(File.separator)) {
                pathString = pathString.replace("\\", File.separator);
            }
            pathString = pathString.replace("%20", " ");
            FileInputStream inputStream = new FileInputStream(pathString);
            props.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String value = props.getProperty(key);
        return value;
    }
    public static String getSftpString(String key){
		Properties props = new Properties();
		try{
		String pathString = SystemGetPropeties.class.getClassLoader().getResource("sftp.properties").getFile();
		if("/".equals(File.separator)){   
			pathString = pathString.replace("\\",File.separator);
		}
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
}
