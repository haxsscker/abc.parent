/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.upload.local;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autoserve.abc.service.biz.intf.upload.FileUploadService;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 文件上传到本地
 * 
 * @author segen189 2015年2月26日 下午3:01:35
 */
public class LocalUploadServiceImpl implements FileUploadService {
    private static final Logger log              = LoggerFactory.getLogger(LocalUploadServiceImpl.class);

    private String              uploadDirectory;
    private String              uploadDirectoryPrefix;

    private static final String virtualUploadDir = "/my/virtual";

    public void setUploadDirectory(String uploadDirectory) {
        this.uploadDirectory = uploadDirectory;
    }

    public void setUploadDirectoryPrefix(String uploadDirectoryPrefix) {
        this.uploadDirectoryPrefix = uploadDirectoryPrefix;
    }

    private String generateFileName(String fileName) {
        return UUID.randomUUID().toString() + getFileType(fileName);
        //可以改进为：(文件名+ip+时间)
    }
    
    @Override
    public String getBasePath(){
    	int prefixLen = uploadDirectoryPrefix.length();
    	return uploadDirectory.substring(0, uploadDirectory.length()-prefixLen);
    }

    private String getFileType(String fileName) {
        if (StringUtils.isNotBlank(fileName)) {
            fileName = fileName.trim();

            int dotInd = fileName.lastIndexOf('.');
            if (dotInd >= 0) {
                String result = fileName.substring(dotInd);
                return result.replace('?', '_').replace('#', '_');
            }
        }
        return StringUtils.EMPTY;
    }

    @Override
    public PlainResult<String> uploadFile(FileItem fileItem) {
        return uploadFile(fileItem, generateFileName(fileItem.getName()));
    }

    @Override
    public PlainResult<String> uploadFile(FileItem fileItem, String destFileName) {
        PlainResult<String> result = new PlainResult<String>();

        InputStream inStream = null;
        FileOutputStream outputStream = null;
        try {
            //按天进行分类（客户需求）
            String absolutDestFileName;
            if (uploadDirectory.charAt(uploadDirectory.length() - 1) == File.separatorChar) {
                absolutDestFileName = uploadDirectory + new SimpleDateFormat("yyyyMMdd").format(new Date())
                        + File.separatorChar;
            } else {
                absolutDestFileName = uploadDirectory + File.separatorChar
                        + new SimpleDateFormat("yyyyMMdd").format(new Date()) + File.separatorChar;
            }
            //判断文件是否存在，不存在创建文件
            File tempFile = new File(absolutDestFileName);
            if (!tempFile.exists()) {
                tempFile.mkdir();
            }
            absolutDestFileName += destFileName;
            File destFile = new File(absolutDestFileName);
            if (destFile.exists()) {
                result.setError(CommonResultCode.BIZ_ERROR, "文件已存在，上传失败");
                return result;
            }

            inStream = fileItem.getInputStream();
            outputStream = new FileOutputStream(destFile);

            byte[] writeBuf = new byte[2048];
            while (inStream.read(writeBuf) != -1) {
                outputStream.write(writeBuf);
            }
            result.setData(uploadDirectoryPrefix + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/"
                    + destFileName);

        } catch (IOException e) {
        	e.printStackTrace();
            log.error("error message", e);
            result.setError(CommonResultCode.BIZ_ERROR, "文件上传失败");
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                }
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                }
            }
        }

        return result;
    }
}
