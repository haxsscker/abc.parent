package com.autoserve.abc.web.convert;

import com.autoserve.abc.dao.dataobject.LogFileRecordDO;
import com.autoserve.abc.service.biz.enums.UploadStatus;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.sysset.LogFileRecordVO;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RJQ 2015/1/11 15:51.
 */
public class LogFileRecordVOConverter {
    public static LogFileRecordVO convertToVO(LogFileRecordDO logFileRecordDO) {
        LogFileRecordVO vo = new LogFileRecordVO();
        if (logFileRecordDO == null) {
            return vo;
        }

        vo.setId(logFileRecordDO.getRecordId());
        vo.setFileName(logFileRecordDO.getRecordFileName());
        vo.setFileDate(logFileRecordDO.getRecordFileDate());
        vo.setTryTimes(logFileRecordDO.getRecordTryTimes());
        vo.setReferId(logFileRecordDO.getRecordReferId());
        vo.setServerMacAddress(logFileRecordDO.getRecordServerMac());
        vo.setOssUrl(logFileRecordDO.getRecordOssUrl());
        vo.setFileSize(logFileRecordDO.getRecordFileSize());
        vo.setCreateTime(new DateTime(logFileRecordDO.getRecordCreateTime()).toString(DateUtil.DATE_TIME_FORMAT));
        vo.setUpdateTime(new DateTime(logFileRecordDO.getRecordUpdateTime()).toString(DateUtil.DATE_TIME_FORMAT));
        vo.setStatus(UploadStatus.valueOf(logFileRecordDO.getRecordStatus()).name());

        return vo;
    }

    public static List<LogFileRecordVO> convertToVOList(List<LogFileRecordDO> logFileRecordDOs) {
        List<LogFileRecordVO> result = new ArrayList<LogFileRecordVO>();
        if (logFileRecordDOs == null || logFileRecordDOs.isEmpty()) {
            return result;
        }
        for (LogFileRecordDO logFileRecordDO : logFileRecordDOs) {
            result.add(convertToVO(logFileRecordDO));
        }
        return result;
    }
}
