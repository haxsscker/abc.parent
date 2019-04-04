package com.autoserve.abc.service.biz.impl.government;

import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.GovernmentUpdateHistoryDO;
import com.autoserve.abc.dao.intf.GovernmentUpdateHistoryDao;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.intf.government.GovUpdateHistoryService;
import com.autoserve.abc.service.biz.result.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author RJQ 2014/11/19 17:25.
 */
@Service
public class GovUpdateHistoryServiceImpl implements GovUpdateHistoryService {

    @Resource
    private GovernmentUpdateHistoryDao historyDao;

    @Override
    public PlainResult<GovernmentUpdateHistoryDO> findById(Integer id) {
        PlainResult<GovernmentUpdateHistoryDO> result = new PlainResult<GovernmentUpdateHistoryDO>();
        GovernmentUpdateHistoryDO historyDO = historyDao.findById(id);
        if (historyDO == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到指定机构修改记录");
            return result;
        }
        result.setData(historyDO);
        return result;
    }

    @Override
    public BaseResult createHistory(GovernmentUpdateHistoryDO historyDO) {
        BaseResult result = new BaseResult();
        int returnVal = historyDao.insert(historyDO);
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "创建机构更新记录失败！");
        }
        return result;
    }

    /**
     * 将审核结果在机构更新记录表中更新
     *
     * @param guhId        机构跟新记录表ID
     * @param reviewResult 审核结果
     * @return
     */
    @Override
	public BaseResult updateGovHistoryState(int guhId, ReviewState reviewResult) {
        BaseResult result = new BaseResult();
        GovernmentUpdateHistoryDO historyDO = new GovernmentUpdateHistoryDO();
        historyDO.setGuhId(guhId);
        historyDO.setGuhAuditState(reviewResult.getState());
        int returnVal = historyDao.updateByPrimaryKeySelective(historyDO);
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "更新审核结果失败");
        }
        return result;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PlainResult<JSONObject> findOldAndNewValue(int guhId) {
        //根据guhId找到机构id，再查询机构的详细信息
        /*PlainResult<GovernmentDO> plainResult = governmentService.findByGovUpdateHistoryId(guhId);
        if(!plainResult.isSuccess()){

        }
        //根据guhId找到此次修改的所有字段，及其修改前后的值
        GovernmentUpdateDetailDO detailDO = new GovernmentUpdateDetailDO();
        detailDO.setGuhUpdateHistoryId(guhId);
        List<GovernmentUpdateDetailDO> list = detailDao.findListByParam(detailDO, new PageCondition());
        //封装成JSONObject
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("govInfo", JSON.toJSONString(governmentDO));

        JSONArray jsonArray = new JSONArray();
        for (GovernmentUpdateDetailDO detail : list) {
            JSONObject obj1 = (JSONObject) JSON.toJSON(detail);
            jsonArray.add(obj1);
        }
        jsonObject.put("updateDetail", jsonArray);

        PlainResult<JSONObject> result = new PlainResult<JSONObject>();
        result.setData(jsonObject);
        return result;*/
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageResult<GovernmentUpdateHistoryDO> queryList(GovernmentUpdateHistoryDO historyDO, PageCondition pageCondition) {
        PageResult<GovernmentUpdateHistoryDO> result = new PageResult<GovernmentUpdateHistoryDO>(pageCondition);
        int totalCount = historyDao.countListByParam(historyDO);
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            result.setData(historyDao.findListByParam(historyDO, pageCondition));
        }

        return result;
    }

    @Override
    public PageResult<GovernmentUpdateHistoryDO> searchList(GovernmentUpdateHistoryDO historyDO, Date updateStartDate,
                                                            Date updateEndDate, String updateEmpName, PageCondition pageCondition) {
        PageResult<GovernmentUpdateHistoryDO> result = new PageResult<GovernmentUpdateHistoryDO>(pageCondition);
        int totalCount = historyDao.countListByMap(historyDO, updateStartDate, updateEndDate, updateEmpName);
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            result.setData(historyDao.findListByMap(historyDO, updateStartDate, updateEndDate, updateEmpName, pageCondition));
        }

        return result;
    }

    @Override
    public PlainResult<GovernmentUpdateHistoryDO> findLastUpdateHistory(int govId) {
        PlainResult<GovernmentUpdateHistoryDO> result = new PlainResult<GovernmentUpdateHistoryDO>();
        GovernmentUpdateHistoryDO historyDO = historyDao.findLastUpdateHistory(govId);
        result.setData(historyDO);
        return result;
    }

    @Override
    public ListResult<GovernmentUpdateHistoryDO> findLastUpdateHistoryList(List<Integer> govIds) {
        ListResult<GovernmentUpdateHistoryDO> result = new ListResult<GovernmentUpdateHistoryDO>();
        List<GovernmentUpdateHistoryDO> list = historyDao.findLastUpdateHistoryList(govIds, govIds.size());
        result.setData(list);
        return result;
    }

//    @Override
//    public ListResult<Integer> findUpdateHistoryIdsByGovId(int govId) {
//        ListResult<Integer> result = new ListResult<Integer>();
//        GovernmentUpdateHistoryDO historyDO = new GovernmentUpdateHistoryDO();
//        historyDO.setGuhGovid(govId);
//        List<GovernmentUpdateHistoryDO> historyDOs = historyDao.findListByParam(historyDO, null);
//
//        List<Integer> updateHisIds = new ArrayList<Integer>();
//        for(GovernmentUpdateHistoryDO his: historyDOs){
//            updateHisIds.add(his.getGuhId());
//        }
//        result.setData(updateHisIds);
//        return result;
//    }
}
