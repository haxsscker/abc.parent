package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.LogFileRecordDO;
import com.autoserve.abc.service.biz.entity.LogFileRecord;
import com.autoserve.abc.service.biz.enums.UploadStatus;
import com.autoserve.abc.service.exception.BusinessException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuqing.zheng
 *         Created on 2014-12-08,15:49
 */
public class LogFileRecordConverter {
    public static LogFileRecord toLogFileRecord(LogFileRecordDO logFileRecordDO) {
        LogFileRecord logFileRecord = new LogFileRecord();
        if (logFileRecordDO == null) {
            return logFileRecord;
        }

        logFileRecord.setId(logFileRecordDO.getRecordId());
        logFileRecord.setFileName(logFileRecordDO.getRecordFileName());
        logFileRecord.setFileDate(logFileRecordDO.getRecordFileDate());
        logFileRecord.setStatus(UploadStatus.valueOf(logFileRecordDO.getRecordStatus()));
        logFileRecord.setTryTimes(logFileRecordDO.getRecordTryTimes());
        logFileRecord.setCreateTime(logFileRecordDO.getRecordCreateTime());
        logFileRecord.setUpdateTime(logFileRecordDO.getRecordUpdateTime());
        logFileRecord.setReferId(logFileRecordDO.getRecordReferId());
        logFileRecord.setServerMacAddress(logFileRecordDO.getRecordServerMac());
        logFileRecord.setOssUrl(logFileRecordDO.getRecordOssUrl());
        logFileRecord.setFileSize(logFileRecordDO.getRecordFileSize());

        return logFileRecord;
    }

    public static List<LogFileRecord> convertList(List<LogFileRecordDO> list) {
        if (list == null || list.isEmpty())
            throw new BusinessException("传入的list为空");
        List<LogFileRecord> result = new ArrayList<LogFileRecord>();
        for (LogFileRecordDO logFileRecordDO : list) {
            result.add(toLogFileRecord(logFileRecordDO));
        }
        return result;
    }
}
