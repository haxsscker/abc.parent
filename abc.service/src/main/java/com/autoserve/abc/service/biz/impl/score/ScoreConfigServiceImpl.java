package com.autoserve.abc.service.biz.impl.score;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ScoreConfigDO;
import com.autoserve.abc.dao.intf.ScoreConfigDao;
import com.autoserve.abc.service.biz.intf.score.ScoreConfigService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author RJQ 2014/12/21 22:11.
 */
@Service
public class ScoreConfigServiceImpl implements ScoreConfigService {
    @Resource
    private ScoreConfigDao scoreConfigDao;

    @Override
    public BaseResult createScoreConfig(ScoreConfigDO scoreConfigDO) {
        BaseResult result = new BaseResult();
        int returnVal = scoreConfigDao.insert(scoreConfigDO);
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "添加积分配置失败！");
        }
        return result;
    }

    @Override
    public PlainResult<ScoreConfigDO> findScoreConfigById(int id) {
        PlainResult<ScoreConfigDO> plainResult = new PlainResult<ScoreConfigDO>();
        ScoreConfigDO scoreConfigDO = scoreConfigDao.findById(id);
        if (scoreConfigDO != null) {
            plainResult.setData(scoreConfigDO);
        }
        return plainResult;
    }

    @Override
    public BaseResult modifyScoreConfig(ScoreConfigDO scoreConfigDO) {
        BaseResult result = new BaseResult();
        int returnVal = scoreConfigDao.updateByPrimaryKey(scoreConfigDO);
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "修改积分配置失败！");
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageResult<ScoreConfigDO> queryScoreConfigList(ScoreConfigDO scoreConfigDO, PageCondition pageCondition) {
        PageResult<ScoreConfigDO> result = new PageResult<ScoreConfigDO>(pageCondition.getPage(),
                pageCondition.getPageSize());
        int totalCount = scoreConfigDao.countListByParam(scoreConfigDO);
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            result.setData(scoreConfigDao.findListByParam(scoreConfigDO, pageCondition));
        }

        return result;
    }

    @Override
    public BaseResult removeScoreConfig(int id) {
        BaseResult result = new BaseResult();
        int returnVal = scoreConfigDao.delete(id);
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "删除失败！");
        }
        return result;
    }
}
