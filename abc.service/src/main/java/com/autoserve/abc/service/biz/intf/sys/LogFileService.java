package com.autoserve.abc.service.biz.intf.sys;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.LogFileRecordDO;
import com.autoserve.abc.service.biz.entity.LogFileRecord;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;

import java.util.Date;
import java.util.List;

/**
 * @author yuqing.zheng
 *         Created on 2014-12-08,15:05
 */
public interface LogFileService {
    /**
     * 获取某一台服务器的日志列表
     *
     * @param macAddress 服务器的mac地址
     * @return
     */
    public PageResult<LogFileRecord> queryLogsByServerMac(String macAddress, PageCondition pageCondition);

    /**
     * 获取所有服务器在指定日期的日志列表
     *
     * @param date 指定日志日期，日志文件以天为单位
     * @return
     */
    public PageResult<LogFileRecord> queryLogsByDate(Date date, PageCondition pageCondition);

    /**
     * 查询列表
     *
     * @param logFileRecordDO 查询条件，可选
     * @param pageCondition   分页条件
     * @return PageResult<LogFileRecordDO>
     */
    public PageResult<LogFileRecordDO> queryList(LogFileRecordDO logFileRecordDO, PageCondition pageCondition);


    /**
     * 删除ids列表中的错误日志
     *
     * @param ids 要删除的日志id集合
     * @return BaseResult
     */
    BaseResult removeByIds(List<Integer> ids);

    /**
     * 删除全部错误日志
     *
     * @return BaseResult
     */
    BaseResult removeAll();
}
