package com.autoserve.abc.service.biz.intf.government;

import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.GovernmentUpdateHistoryDO;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

import java.util.Date;
import java.util.List;

/**
 * @author RJQ 2014/11/19 17:24.
 */
public interface GovUpdateHistoryService {
    /**
     * 新增机构信息更改记录
     *
     * @param historyDO 更新记录
     * @return BaseResult
     */
    public BaseResult createHistory(GovernmentUpdateHistoryDO historyDO);

    /**
     * 查询机构修改记录
     *
     * @param id 机构id
     * @return
     */
    public PlainResult<GovernmentUpdateHistoryDO> findById(Integer id);

    /**
     * 将审核结果在机构更新记录表中更新
     *
     * @param guhId        机构跟新记录表ID
     * @param reviewResult 审核结果
     * @return BaseResult
     */
    public BaseResult updateGovHistoryState(int guhId, ReviewState reviewResult);

    /**
     * 查询机构修改记录列表
     *
     * @param historyDO     查询条件
     * @param pageCondition 分页条件
     * @return PageResult<GovernmentUpdateHistoryDO>
     */
    public PageResult<GovernmentUpdateHistoryDO> queryList(GovernmentUpdateHistoryDO historyDO, PageCondition pageCondition);

    /**
     * 搜索机构修改记录列表
     *
     * @param historyDO       查询条件
     * @param updateStartDate 修改开始时间
     * @param updateEndDate   修改结束时间
     * @param updateEmpName   修改人
     * @param pageCondition   分页条件
     * @return
     */
    public PageResult<GovernmentUpdateHistoryDO> searchList(GovernmentUpdateHistoryDO historyDO, Date updateStartDate,
                                                            Date updateEndDate, String updateEmpName, PageCondition pageCondition);

    /**
     * 根据修改记录表的ID，查找此次修改的字段和修改前，修改后的值
     *
     * @param guhId 修改记录表的ID
     * @return PlainResult<JSONObject>
     */
    public PlainResult<JSONObject> findOldAndNewValue(int guhId);

    /**
     * 查询最后一次更新信息
     *
     * @param govId 机构id
     * @return PlainResult<GovernmentUpdateHistoryDO>
     */
    public PlainResult<GovernmentUpdateHistoryDO> findLastUpdateHistory(int govId);

    /**
     * 查询最后一次修改信息列表
     *
     * @param govIds 机构id
     * @return ListResult<GovernmentUpdateHistoryDO>
     */
    public ListResult<GovernmentUpdateHistoryDO> findLastUpdateHistoryList(List<Integer> govIds);

    /**
     * 查询机构所有修改记录列表主键
     * @param govId 机构id
     * @return ListResult<Integer>
     */
//    public ListResult<Integer> findUpdateHistoryIdsByGovId(int govId);
}
