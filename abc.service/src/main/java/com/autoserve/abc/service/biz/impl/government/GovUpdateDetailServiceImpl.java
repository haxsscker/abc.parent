package com.autoserve.abc.service.biz.impl.government;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.GovernmentUpdateDetailDO;
import com.autoserve.abc.dao.intf.GovernmentUpdateDetailDao;
import com.autoserve.abc.service.biz.intf.government.GovUpdateDetailService;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author RJQ 2014/12/12 18:21.
 */
@Service
public class GovUpdateDetailServiceImpl implements GovUpdateDetailService {
    @Resource
    private GovernmentUpdateDetailDao detailDao;

    @Override
    public PageResult<GovernmentUpdateDetailDO> findListByParam(GovernmentUpdateDetailDO updateDetailDO, PageCondition pageCondition) {
        List<GovernmentUpdateDetailDO> updateDetailDOs = detailDao.findListByParam(updateDetailDO, pageCondition);
        PageResult<GovernmentUpdateDetailDO> result = new PageResult<GovernmentUpdateDetailDO>(pageCondition);
        if (updateDetailDOs == null || updateDetailDOs.size() == 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到字段修改详情");
            return result;
        }
        result.setData(updateDetailDOs);
        return result;
    }

    @Override
    public PlainResult<Integer> batchInsert(List<GovernmentUpdateDetailDO> detailDOs) {
        PlainResult<Integer> result = new PlainResult<Integer>();
        int returnVal = detailDao.batchInsert(detailDOs);
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "批量插入机构字段修改详情失败");
            return result;
        }
        result.setData(returnVal);
        return result;
    }

    @Override
    public ListResult<GovernmentUpdateDetailDO> findListByGovUpdateHistoryId(Integer guhId) {
        ListResult<GovernmentUpdateDetailDO> result = new ListResult<GovernmentUpdateDetailDO>();
        List<GovernmentUpdateDetailDO> updateDetailDOs = detailDao.findListByGovUpdateHistoryId(guhId);
        if (updateDetailDOs == null || updateDetailDOs.size() == 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR,"查询字段修改详情失败！");
            return result;
        }
        result.setData(updateDetailDOs);
        return result;
    }
}
