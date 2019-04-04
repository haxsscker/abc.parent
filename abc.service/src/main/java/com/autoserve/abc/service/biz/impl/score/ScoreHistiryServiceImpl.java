package com.autoserve.abc.service.biz.impl.score;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ScoreHistoryDO;
import com.autoserve.abc.dao.dataobject.ScoreHistoryWithValueDO;
import com.autoserve.abc.dao.dataobject.ScoreUsageRecordDO;
import com.autoserve.abc.dao.intf.ScoreHistoryDao;
import com.autoserve.abc.dao.intf.ScoreUsageRecordDao;
import com.autoserve.abc.service.biz.intf.score.ScoreHistoryService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PageResult;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author RJQ 2014/12/21 22:03.
 */
@Service
public class ScoreHistiryServiceImpl implements ScoreHistoryService {
    @Resource
    private ScoreHistoryDao scoreHistoryDao;
    
    @Resource
    private ScoreUsageRecordDao scoreUsageRecordDao;
    
    @Override
    public BaseResult createScoreHistory(ScoreHistoryDO scoreHistoryDO) {
        BaseResult result = new BaseResult();
        int val =  scoreHistoryDao.insert(scoreHistoryDO);
        if(val <= 0){
            result.setErrorMessage(CommonResultCode.BIZ_ERROR,"新增积分记录失败！");
        }
        return result;
    }

    @Override
    public PageResult<ScoreHistoryWithValueDO> queryScoreHistoryList(ScoreHistoryDO scoreHistoryDO, PageCondition pageCondition) {
        PageResult<ScoreHistoryWithValueDO> result = new PageResult<ScoreHistoryWithValueDO>(pageCondition.getPage(),
                pageCondition.getPageSize());
        int totalCount = scoreHistoryDao.countListByParam(scoreHistoryDO);
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            result.setData(scoreHistoryDao.findListByParam(scoreHistoryDO, pageCondition));
        }

        return result;
    }
    
    @Override
    public int addScoreConvertRecord(ScoreUsageRecordDO record) {
        int i = scoreUsageRecordDao.insert(record);
        return i;
    }

    @Override
    public PageResult<ScoreHistoryWithValueDO> queryScoreListByUserId(ScoreHistoryDO scoreHistoryDO,
                                                                      PageCondition pageCondition) {
        PageResult<ScoreHistoryWithValueDO> result = new PageResult<ScoreHistoryWithValueDO>(pageCondition.getPage(),
                pageCondition.getPageSize());
        int totalCount = scoreHistoryDao.countScoreListByUserId(scoreHistoryDO);
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            result.setData(scoreHistoryDao.findScoreListByUserId(scoreHistoryDO, pageCondition));
        }

        return result;
    }
}
