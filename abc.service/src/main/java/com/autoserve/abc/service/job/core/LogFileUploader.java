package com.autoserve.abc.service.job.core;

import com.autoserve.abc.service.oss.OssService;
import com.autoserve.abc.service.util.MacAddressUtil;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author yuqing.zheng
 *         Created on 2014-12-01,10:51
 */
public class LogFileUploader {
    private static final Logger logger = LoggerFactory.getLogger(LogFileUploader.class);

    @Autowired
    private OssService ossService;

    private String logDir;

    private String LOG_BUCKET;

    public UploadContext uploadLogFile(UploadContext uploadContext) {
        uploadContext.file =  logDir + "/" + uploadContext.filename;
        File logFile = new File(uploadContext.file);
        uploadContext.fileSize = FileUtils.byteCountToDisplaySize(logFile.length());

        try {
            String url = ossService.uploadFile(logFile, LOG_BUCKET, uploadContext.filename);
            uploadContext.tryTimes++;
            uploadContext.ossUrl = url;
        } catch (FileNotFoundException e) {
            logger.error("找不到日志文件 " + uploadContext.file);
        }

        return uploadContext;
    }

    public void setLogDir(String logDir) {
        this.logDir = logDir;
    }

    public void setLOG_BUCKET(String LOG_BUCKET) {
        String bucket = LOG_BUCKET + "-" + MacAddressUtil.getMacAddress();
        this.LOG_BUCKET = bucket.toLowerCase().replace('.', '-');
    }
}
