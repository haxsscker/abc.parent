package com.autoserve.abc.web.module.screen.webnotify.hfnotify;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件锁
 * @author andy
 *
 */

public class InvestBackFileLock {
	
	public static Map<String,Object> lockFile(String key,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		String filePath = request.getSession().getServletContext().getRealPath("/");
		File cache = new File(filePath+"cache\\");
		
		//检测文件是否存在，不存在则创建！存在则删除return false
		if(!cache.exists()){
			cache.mkdir();
		}
		
		File file = new File(cache+"\\"+key+".txt");
		
		if(file.exists())return map;
		try {
			RandomAccessFile fis = new RandomAccessFile(file, "rws");
			FileChannel flc = fis.getChannel();
			map.put("FileChannel", flc);// 获得文件通道
			 FileLock flin=null; //声明文件锁对象
			 flin = flc.tryLock(); // 获取文件非阻塞锁，如果不能获取，继续等待0.5s.
		     map.put("FileLock"+key, flin);
		     return map;
		}catch (Exception e) {
			return map;
		}
	}
	
	public static void unlockfile(String key,Map<String,Object> map,HttpServletRequest request){
		
		String filePath = request.getSession().getServletContext().getRealPath("/");
		File cache = new File(filePath+"cache\\");
		
		//删除文件
				try {
					File file = new File(cache+"\\"+key+".txt");
					FileLock flin=(FileLock) map.get("FileLock"+key);
					FileChannel flc=(FileChannel) map.get("FileChannel");
					flin.release();
					flc.close();
					file.delete();
				} catch (IOException e) {
					e.printStackTrace();
				}
	}
	
}
