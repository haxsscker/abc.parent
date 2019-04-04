package com.autoserve.abc.service.job;

import com.autoserve.abc.dao.dataobject.LogFileRecordDO;
import com.autoserve.abc.dao.intf.LogFileRecordDao;
import com.autoserve.abc.service.job.core.LogFileUploader;
import com.autoserve.abc.service.job.core.UploadContext;
import com.autoserve.abc.service.util.MacAddressUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 负责对失败的日志上传进行重试的job
 *
 * @author yuqing.zheng
 *         Created on 2014-11-29,14:17
 */
public class FileUploadRetryJob implements BaseJob {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadRetryJob.class);

    @Autowired
    private LogFileRecordDao logFileRecordDao;

    @Autowired
    private LogFileUploader logFileUploader;

    /**
     * 对于每一个日志文件的最大重试上传次数，默认为10
     * 可通过配置文件中job.upload.maxRetryTimes修改
     */
    private int MAX_TRY_TIMES = 10;

    @Override
	public void run() {
        logger.info("失败日志文件重传任务开始");

        List<LogFileRecordDO> retryJobs = getRetryJobs();
        if (CollectionUtils.isEmpty(retryJobs)) {
            logger.info("没有需要重试上传的文件记录，结束。");
            return;
        }

        logger.info("共找到{}个需要重试上传的日志文件", retryJobs.size());
        for (LogFileRecordDO retryJob : retryJobs) {
            retryJob(retryJob);
        }

        logger.info("失败日志文件重传任务结束");
    }

    /**
     * 找出本机所有需要重试的上传任务（status = -1）
     */
    private List<LogFileRecordDO> getRetryJobs() {
        String macAddress = MacAddressUtil.getMacAddress();
        return logFileRecordDao.findRetryJobsByMacAddress(macAddress);
    }

    private void retryJob(LogFileRecordDO retryJob) {
        UploadContext uploadContext = new UploadContext();
        uploadContext.filename = retryJob.getRecordFileName();
        uploadContext.tryTimes = retryJob.getRecordTryTimes();

        logger.info("重传文件名: {}, 当前重试次数: {}", uploadContext.filename, String.valueOf(uploadContext.tryTimes));
        uploadContext = logFileUploader.uploadLogFile(uploadContext);
        if (StringUtils.isBlank(uploadContext.ossUrl)) {
            logger.warn("重试日志文件上传至OSS失败！filename: {}, 当前重试次数: {}", uploadContext.filename, String.valueOf(uploadContext.tryTimes));
            uploadContext.uploadResult = false;
        } else {
            logger.warn("重传成功！filename: {}, 当前重试次数: {}", uploadContext.filename, String.valueOf(uploadContext.tryTimes));
            uploadContext.uploadResult = true;
        }

        modifyUploadRecord(retryJob, uploadContext);
    }

    private void modifyUploadRecord(LogFileRecordDO retryJob, UploadContext uploadContext) {
        retryJob.setRecordOssUrl(uploadContext.ossUrl);
        retryJob.setRecordTryTimes(uploadContext.tryTimes);

        if (uploadContext.uploadResult) {
            retryJob.setRecordStatus(1);
        } else {
            // 如果重试次数已达上限，标记该job失败，否则扔标记为“待重试”
            if (uploadContext.tryTimes > MAX_TRY_TIMES) {
                retryJob.setRecordTryTimes(0);
                logger.info("日志文件 {} 上传失败已达最大重试次数，文件上传任务最终失败，将不再进行重试，请手动进行上传", uploadContext.filename);
            } else {
                retryJob.setRecordTryTimes(-1);
            }
        }

        logFileRecordDao.update(retryJob);
    }

    public void setMAX_TRY_TIMES(int MAX_TRY_TIMES) {
        this.MAX_TRY_TIMES = MAX_TRY_TIMES;
    }
}
