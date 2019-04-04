package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.LogFileRecordDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogFileRecordDao extends BaseDao<LogFileRecordDO, Integer> {
    public LogFileRecordDO findByFilename(String filename);

    public List<LogFileRecordDO> findRetryJobsByMacAddress(String macAddress);

    public List<LogFileRecordDO> find(@Param("logFileRecordDO") LogFileRecordDO logFileRecordDO, @Param("pageCondition") PageCondition pageCondition);

    /**
     * 根据参数获取记录条数
     *
     * @param logFileRecordDO
     * @return
     */
    public int countListByParam(@Param("logFileRecordDO") LogFileRecordDO logFileRecordDO);

    /**
     * 按条件查询分页结果
     *
     * @param logFileRecordDO 查询条件，为空的话传new LogFileRecordDO()
     * @param pageCondition   分页和排序条件，可选
     * @return List<LogFileRecordDO>
     */
    List<LogFileRecordDO> findListByParam(@Param("logFileRecordDO") LogFileRecordDO logFileRecordDO,
                                          @Param("pageCondition") PageCondition pageCondition);

    int deleteByIds(@Param("ids") List<Integer> ids);
}
