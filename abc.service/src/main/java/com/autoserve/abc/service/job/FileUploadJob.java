package com.autoserve.abc.service.job;

import com.autoserve.abc.dao.dataobject.LogFileRecordDO;
import com.autoserve.abc.dao.intf.LogFileRecordDao;
import com.autoserve.abc.service.job.core.LogFileUploader;
import com.autoserve.abc.service.job.core.UploadContext;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.service.util.MacAddressUtil;
import com.autoserve.abc.service.util.UuidUtil;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 负责将前一日的日志文件上传至OSS的job，每日凌晨三点执行
 *
 * @author yuqing.zheng
 *         Created on 2014-11-28,11:27
 */
public class FileUploadJob implements BaseJob {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadJob.class);

    public static final String FILE_NAME_SUFFIX = ".system.log";

    @Autowired
    private LogFileRecordDao logFileRecordDao;

    @Autowired
    private LogFileUploader logFileUploader;

    @Override
	public void run() {
        logger.info("日志文件上传任务开始");

        String filename = getYesterdayFileName();
        // OSS调用表与FileUploadJobRecord表通过一个名为refer_id的uuid字符串来关联
        // PS: OSS调用表暂时不做记录，为空
        String referId = UuidUtil.generateUuid();

        UploadContext uploadContext = new UploadContext();
        uploadContext.filename = filename;
        uploadContext.referId = referId;

        logger.info("上传文件名: {}，refer_id: {}", filename, referId);
        uploadContext = logFileUploader.uploadLogFile(uploadContext);

        if (StringUtils.isBlank(uploadContext.ossUrl)) {
            logger.warn("日志文件上传至OSS失败！filename: {}, refer_id: {}", uploadContext.filename, uploadContext.referId);
            uploadContext.uploadResult = false;
        } else {
            logger.info("上传成功！filename: {}", uploadContext.filename);
            uploadContext.uploadResult = true;
        }

        // 将上传结果保存到FileUploadJobRecord表中
        insertJobRecord(uploadContext);

        logger.info("日志文件上传任务结束");
    }

    /**
     * 将日志上传结果保存到FileUploadJobRecord表中
     */
    private void insertJobRecord(UploadContext uploadContext) {
        LogFileRecordDO recordDO = new LogFileRecordDO();
        recordDO.setRecordFileName(uploadContext.filename);
        recordDO.setRecordFileDate(uploadContext.filename.substring(0, 10));
        recordDO.setRecordStatus(uploadContext.uploadResult ? 1 : -1);
        recordDO.setRecordTryTimes(uploadContext.tryTimes);
        recordDO.setRecordReferId(uploadContext.referId);
        recordDO.setRecordOssUrl(uploadContext.ossUrl);
        recordDO.setRecordServerMac(MacAddressUtil.getMacAddress());
        recordDO.setRecordFileSize(uploadContext.fileSize);
        logFileRecordDao.insert(recordDO);
    }

    private String getYesterdayFileName() {
        DateTime dt = new DateTime();
        dt.minusDays(1);
        return dt.toString(DateUtil.DEFAULT_DAY_STYLE).concat(FILE_NAME_SUFFIX);
    }
}
