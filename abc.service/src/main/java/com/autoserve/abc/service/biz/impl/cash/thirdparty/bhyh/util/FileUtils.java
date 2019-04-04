package com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {
	private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	/**
	 * 读取文件txt（在每行的末尾追加换行符）
	 * @param filePath 文件绝对路径
	 * @return
	 */
	public static String readByBufferedReader(String filePath) {  
		StringBuffer result = new StringBuffer();
        try {  
            File file = new File(filePath);  
//            BufferedReader bufread = new BufferedReader(new FileReader(file)); 
            BufferedReader bufread = new BufferedReader(new InputStreamReader(  
                    new FileInputStream(file), "GBK"));  
            String read;  
            int line=1;
            while ((read = bufread.readLine()) != null) {  
                result.append(read).append(LINE_SEPARATOR);
                logger.info("第"+line+"行："+read);
//                System.out.println("第"+line+"行："+read);
                line++;
            }  
            bufread.close();  
        } catch (FileNotFoundException ex) {  
            ex.printStackTrace();  
        } catch (IOException ex) {  
            ex.printStackTrace();  
        }  
        return result.toString();
    } 
	/**
	 * 写入文件txt
	 * @param content 文件内容（换行用LINE_SEPARATOR分割）
	 * @param filePath 文件绝对路径
	 * @return
	 */
	public static boolean writeByBufferedWriter(String content,String filePath) {  
		boolean isWriteSuccess = true;
        try {  
            File file = new File(filePath);
            if (!file.exists()) {  
            	file.createNewFile();
            } else{
            	file.delete();
            	file.createNewFile();
            }
//            FileWriter fw = new FileWriter(file, true);  
//            BufferedWriter bw = new BufferedWriter(fw); 
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(  
                    new FileOutputStream(file,true), "GBK")); 
            if(content.contains(LINE_SEPARATOR)){
            	String[] strArr=content.split(LINE_SEPARATOR);
            	for(int i=0;i<strArr.length;i++){
            		bw.write(strArr[i]);
            		if(i<strArr.length-1){
            			bw.write(LINE_SEPARATOR);
            		}
            	}
            }else{
            	bw.write(content);  
            }
            bw.flush();  
            bw.close();  
            logger.info("写入文件成功========"+filePath);
        } catch (IOException e) {  
        	isWriteSuccess = false;
            e.printStackTrace();  
        }  
        return isWriteSuccess;
    }
	
	/** 
     * 压缩文件 
     *  
     * @param srcfile 
     */  
    public static void zipFiles(File srcfile,String targetFilePath) {  
  
        ZipOutputStream out = null;  
        try {  
            out = new ZipOutputStream(new FileOutputStream(new File(targetFilePath)));  
            if(srcfile.isFile()){  
                zipFile(srcfile, out, "");  
            } else{  
                File[] list = srcfile.listFiles();  
                for (int i = 0; i < list.length; i++) {  
                    compress(list[i], out, "");  
                }  
            }  
              
            System.out.println("压缩完毕");  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (out != null)  
                    out.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }
	
    
    /** 
     * 压缩单个文件 
     *  
     * @param srcfile 
     */  
    public static void zipFile(File srcfile, ZipOutputStream out, String basedir) {  
        if (!srcfile.exists())  
            return;  
  
        byte[] buf = new byte[1024];  
        FileInputStream in = null;  
  
        try {  
            int len;  
            in = new FileInputStream(srcfile);  
            out.putNextEntry(new ZipEntry(basedir + srcfile.getName()));  
  
            while ((len = in.read(buf)) > 0) {  
                out.write(buf, 0, len);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (out != null)  
                    out.closeEntry();  
                if (in != null)  
                    in.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    } 
    
    /** 
     * 压缩文件夹里的文件 
     * 起初不知道是文件还是文件夹--- 统一调用该方法 
     * @param file 
     * @param out 
     * @param basedir 
     */  
    private static void compress(File file, ZipOutputStream out, String basedir) {  
        /* 判断是目录还是文件 */  
        if (file.isDirectory()) {  
            zipDirectory(file, out, basedir);  
        } else {  
            zipFile(file, out, basedir);  
        }  
    }
    
    /** 
     * 压缩文件夹 
     * @param dir 
     * @param out 
     * @param basedir 
     */  
    public static void zipDirectory(File dir, ZipOutputStream out, String basedir) {  
        if (!dir.exists())  
            return;  
  
        File[] files = dir.listFiles();  
        for (int i = 0; i < files.length; i++) {  
            /* 递归 */  
            compress(files[i], out, basedir + dir.getName() + "/");  
        }  
    } 
    
    //复制文件
    public static void copyFileUsingFileStreams(File source, File dest)
            throws IOException {    
        InputStream input = null;    
        OutputStream output = null;    
        try {
               input = new FileInputStream(source);
               output = new FileOutputStream(dest);        
               byte[] buf = new byte[1024];        
               int bytesRead;        
               while ((bytesRead = input.read(buf)) > 0) {
                   output.write(buf, 0, bytesRead);
               }
        } finally {
            input.close();
            output.close();
        }
    }
    
	public static void main(String[] args) throws IOException {
		//String filePath="D:/pdf/pdf/20180508/测试合同上传_xh99d.pdf";
		//copyFileUsingFileStreams(new File(filePath),new File(filePath.replace(new File(filePath).getName(), "")+"800055100010001_20180508_ContractFileUpload_8000551000100010000000700368272.pdf"));
		
//		String contractPath = "D:/pdf/pdf/20180508/测试合同上传_xh99d.pdf";
//		String fileName = "800055100010001_20180508_ContractFileUpload_8000551000100010000000700368272.zip";
//		String newPdffileName = "800055100010001_20180508_ContractFileUpload_8000551000100010000000700368272.pdf";
//		// 本地路径
//		File pdffile = new File(contractPath);
//        String localPath = contractPath.replace(pdffile.getName(), "");
//        File newpdffile = new File(localPath+newPdffileName);
//        //复制原文件，并重命名，防止中文文件名乱码
//        FileUtils.copyFileUsingFileStreams(pdffile,newpdffile);
//		FileUtils.zipFiles(newpdffile,localPath+fileName);
		FileUtils.writeByBufferedWriter("测试中文乱码","D:/ftpLocalFile/800055100010001_20180509_ExistUserRegister_8000551000100010000001014257457.txt");
		FileUtils.readByBufferedReader("D:/ftpLocalFile/800055100010001_20180509_ExistUserRegister_8000551000100010000001014257457.txt");
	}

}
