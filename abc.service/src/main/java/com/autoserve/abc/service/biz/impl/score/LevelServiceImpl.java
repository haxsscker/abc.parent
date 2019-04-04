package com.autoserve.abc.service.biz.impl.score;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.LevelDO;
import com.autoserve.abc.dao.intf.LevelDao;
import com.autoserve.abc.service.biz.intf.score.LevelService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * 用户等级服务实现类
 * 
 * @author RJQ 2014/11/22 10:51.
 */
@Service
public class LevelServiceImpl implements LevelService {

    @Resource
    private LevelDao levelDao;

    @Override
    public PlainResult<LevelDO> findLevelByScore(BigDecimal score) {
        PlainResult<LevelDO> result = new PlainResult<LevelDO>();
        LevelDO levelDO = levelDao.findByScore(score);
        result.setData(levelDO);
        if (levelDO == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到对应等级！");
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageResult<LevelDO> findAllLevel(LevelDO levelDO, PageCondition pageCondition) {
        PageResult<LevelDO> result = new PageResult<LevelDO>(pageCondition.getPage(), pageCondition.getPageSize());
        int totalCount = levelDao.countListByParam(levelDO);
        result.setTotalCount(totalCount);
        if (totalCount > 0) {
            result.setData(levelDao.findListByParam(levelDO, pageCondition));
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<LevelDO> findLevelByName(String name) {
        LevelDO levelDO = new LevelDO();
        levelDO.setLevName(name);
        List<LevelDO> result = levelDao.findListByParam(levelDO, null);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult modifyLevel(List<LevelDO> levelDOs) {
        BaseResult result = new BaseResult();
        if (levelDOs == null || levelDOs.size() == 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "参数有误");
            return result;
        }
        levelDao.deleteAll();
        int returnVal = levelDao.batchInsert(levelDOs);
        if (returnVal <= 0) {
            throw new BusinessException("修改等级失败！");
        }
        result.setMessage("修改等级成功！");
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult createLevel(LevelDO levelDO) {
        BaseResult result = new BaseResult();
        int val = levelDao.insert(levelDO);
        if (val <= 0) {
            throw new BusinessException("增加等级管理失败！");
        }
        result.setMessage("增加等级管理成功！");
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public PlainResult<LevelDO> findLevelByID(Integer leveid) {
        PlainResult<LevelDO> result = new PlainResult<LevelDO>();
        LevelDO levelDO = levelDao.findById(leveid);
        result.setData(levelDO);
        if (levelDO == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到对应等级！");
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult editLevel(LevelDO levelDO) {
        BaseResult result = new BaseResult();
        int val = levelDao.update(levelDO);
        if (val <= 0) {
            throw new BusinessException("修改等级管理失败！");
        }
        result.setMessage("修改等级管理成功！");
        return result;
    }

    @Override
    public BaseResult delLevel(Integer leveid) {
        BaseResult result = new BaseResult();
        int val = levelDao.delete(leveid);
        if (val <= 0) {
            throw new BusinessException("删除等级管理失败！");
        }
        result.setMessage("删除等级管理成功！");
        return result;
    }
}
