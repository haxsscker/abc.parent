package com.autoserve.abc.service.biz.intf.government;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.GovernmentUpdateDetailDO;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

import java.util.List;

/**
 * @author RJQ 2014/12/12 18:20.
 */
public interface GovUpdateDetailService {

    /**
     * 分页查询
     *
     * @param updateDetailDO
     * @param pageCondition
     * @return
     */
    public PageResult<GovernmentUpdateDetailDO> findListByParam(GovernmentUpdateDetailDO updateDetailDO, PageCondition pageCondition);

    /**
     * 批量更新机构字段修改详情
     *
     * @param detailDOs 修改详情
     * @return PlainResult<Integer> 新增记录条数
     */
    public PlainResult<Integer> batchInsert(List<GovernmentUpdateDetailDO> detailDOs);

    /**
     * 根据机构修改记录主键查找该次修改的所有字段信息
     * @param guhId 机构修改记录主键
     * @return
     */
    public ListResult<GovernmentUpdateDetailDO> findListByGovUpdateHistoryId(Integer guhId);
}
