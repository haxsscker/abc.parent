package com.autoserve.abc.service.biz.impl.sys;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.LogFileRecordDO;
import com.autoserve.abc.dao.intf.LogFileRecordDao;
import com.autoserve.abc.service.biz.convert.LogFileRecordConverter;
import com.autoserve.abc.service.biz.entity.LogFileRecord;
import com.autoserve.abc.service.biz.intf.sys.LogFileService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.DateUtil;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yuqing.zheng
 *         Created on 2014-12-08,15:19
 */
@Service
public class LogFileServiceImpl implements LogFileService {
    private static final Logger logger = LoggerFactory.getLogger(LogFileServiceImpl.class);

    @Autowired
    private LogFileRecordDao logFileRecordDao;

    @Override
    public PageResult<LogFileRecord> queryLogsByServerMac(String macAddress, PageCondition pageCondition) {
        LogFileRecordDO logDO = new LogFileRecordDO();
        logDO.setRecordServerMac(macAddress);

        return findLogList(logDO, pageCondition);
    }

    @Override
    public PageResult<LogFileRecord> queryLogsByDate(Date date, PageCondition pageCondition) {
        LogFileRecordDO logDO = new LogFileRecordDO();
        String logFileDate = new DateTime(date).toString(DateUtil.DEFAULT_DAY_STYLE);
        logDO.setRecordFileDate(logFileDate);

        return findLogList(logDO, pageCondition);
    }

    private PageResult<LogFileRecord> findLogList(LogFileRecordDO logDO, PageCondition pageCondition) {
        PageResult<LogFileRecord> result = new PageResult<LogFileRecord>(pageCondition.getPage(), pageCondition.getPageSize());

        List<LogFileRecordDO> logDOList = logFileRecordDao.find(logDO, pageCondition);
        if (CollectionUtils.isEmpty(logDOList)) {
            logger.warn("未找到日志记录");
            result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS, "日志记录");
            result.setData(new ArrayList<LogFileRecord>());
            return result;
        }

        List<LogFileRecord> logFileRecords = new ArrayList<LogFileRecord>();
        for (LogFileRecordDO log : logDOList) {
            logFileRecords.add(LogFileRecordConverter.toLogFileRecord(log));
        }
        result.setData(logFileRecords);

        return result;
    }

    @Override
    public PageResult<LogFileRecordDO> queryList(LogFileRecordDO logFileRecordDO, PageCondition pageCondition) {
        PageResult<LogFileRecordDO> result = new PageResult<LogFileRecordDO>(pageCondition);
        int totalCount = logFileRecordDao.countListByParam(logFileRecordDO);
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            result.setData(logFileRecordDao.findListByParam(logFileRecordDO, pageCondition));
        }

        return result;
    }

    @Override
    public BaseResult removeByIds(List<Integer> ids) {
        logFileRecordDao.deleteByIds(ids);
        return BaseResult.SUCCESS;
    }

    @Override
    public BaseResult removeAll() {
        logFileRecordDao.deleteByIds(null);
        return BaseResult.SUCCESS;
    }
}
