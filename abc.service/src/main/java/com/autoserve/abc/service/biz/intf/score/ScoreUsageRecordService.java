package com.autoserve.abc.service.biz.intf.score;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ScoreUsageRecordDO;
import com.autoserve.abc.dao.dataobject.ScoreUsageRecordWithNameDO;
import com.autoserve.abc.service.biz.entity.ScoreUsageRecord;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

import java.util.Date;

/**
 * @author RJQ 2014/12/21 22:20.
 */
public interface ScoreUsageRecordService {
    /**
     * 积分兑现审核后修改相关信息
     *
     * @param surId       积分兑现主键
     * @param reviewState 审核状态
     * @return BaseResult
     */
    public BaseResult modifyScoreUsageRecordAfterReview(int surId, ReviewState reviewState);

    /**
     * 查询积分兑现审核列表
     *
     * @param scoreUsageRecordDO 查询条件，全查询的话new一个新对象传入
     * @param pageCondition      分页条件
     * @return
     */
    public PageResult<ScoreUsageRecord> queryReviewOpLogBySurId(ScoreUsageRecord record, PageCondition pageCondition);

    /**
     * 根据条件查询积分兑现审核列表
     *
     * @param record          积分兑现详情
     * @param scoreStart      积分兑现的起始分值
     * @param scoreEnd        积分兑现的结束分值
     * @param toCashStartDate 兑现的开始时间
     * @param toCashEndDate   兑现的结束时间
     * @param pageCondition   分页条件
     * @return
     */
    public PageResult<ScoreUsageRecord> queryReviewOpLogBySurId(ScoreUsageRecord record, String userName, Integer scoreStart,
                                                                Integer scoreEnd, Date toCashStartDate, Date toCashEndDate,
                                                                PageCondition pageCondition);

    /**
     * 查询积分兑换使用情况
     *
     * @param id 积分兑换使用情况id
     * @return
     */
    public PlainResult<ScoreUsageRecordWithNameDO> queryScoreUsageRecordById(Integer id);

    /**
     * 查询积分兑换使用情况即审核列表
     *
     * @param scoreUsageRecordDO 查询条件，可选
     * @param pageCondition      分页条件
     * @return PageResult<ScoreUsageRecordDO>
     */
    public PageResult<ScoreUsageRecordWithNameDO> queryScoreUsageRecordList(ScoreUsageRecordDO scoreUsageRecordDO,
                                                                            PageCondition pageCondition);

    /**
     * 搜索积分兑换使用情况即审核列表
     *
     * @param record          积分兑现详情
     * @param scoreStart      积分兑现的起始分值
     * @param scoreEnd        积分兑现的结束分值
     * @param toCashStartDate 兑现的开始时间
     * @param toCashEndDate   兑现的结束时间
     * @param pageCondition   分页条件
     * @return
     */
    public PageResult<ScoreUsageRecordWithNameDO> searchScoreUsageRecordList(String userName, ScoreUsageRecordDO record, Integer scoreStart,
                                                                             Integer scoreEnd, Date toCashStartDate, Date toCashEndDate,
                                                                             PageCondition pageCondition);
}
