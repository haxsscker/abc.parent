package com.autoserve.abc.service.biz.intf.score;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ScoreHistoryDO;
import com.autoserve.abc.dao.dataobject.ScoreHistoryWithValueDO;
import com.autoserve.abc.dao.dataobject.ScoreUsageRecordDO;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;

/**
 * 用户和积分的关联记录服务接口
 *
 * @author RJQ 2014/12/21 21:57.
 */
public interface ScoreHistoryService {

    public BaseResult createScoreHistory(ScoreHistoryDO scoreHistoryDO);

    /**
     * 查询积分记录列表（包含积分类型名和值）
     * @param scoreHistoryDO
     * @param pageCondition
     * @return
     */
    public PageResult<ScoreHistoryWithValueDO> queryScoreHistoryList(ScoreHistoryDO scoreHistoryDO,
                                                                     PageCondition pageCondition);
    
    
    /**
     * 添加用户积分兑换记录
     * 
     * @param record
     * @return
     */

    public int addScoreConvertRecord(ScoreUsageRecordDO record);

    /**
     * 查询用户积分获得和使用记录
     * 
     * @param scoreHistoryDO
     * @param pageCondition
     * @return
     */
    public PageResult<ScoreHistoryWithValueDO> queryScoreListByUserId(ScoreHistoryDO scoreHistoryDO,
                                                                      PageCondition pageCondition);

}
