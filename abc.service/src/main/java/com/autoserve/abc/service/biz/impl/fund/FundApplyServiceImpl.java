package com.autoserve.abc.service.biz.impl.fund;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.FundApplyDO;
import com.autoserve.abc.dao.dataobject.FundProfitDO;
import com.autoserve.abc.dao.dataobject.search.FundApplySearchDO;
import com.autoserve.abc.dao.intf.FundApplyDao;
import com.autoserve.abc.dao.intf.FundProfitDao;
import com.autoserve.abc.service.biz.convert.FundApplyConverter;
import com.autoserve.abc.service.biz.convert.FundProfitConverter;
import com.autoserve.abc.service.biz.entity.FundApply;
import com.autoserve.abc.service.biz.entity.FundProfit;
import com.autoserve.abc.service.biz.intf.fund.FundApplyService;
import com.autoserve.abc.service.biz.result.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类FundApplyServiceImpl.java的实现描述：
 * 
 * @author wangyongheng 2014/12/08
 */
@Service
public class FundApplyServiceImpl implements FundApplyService {

    @Resource
    FundApplyDao  fundApplyDao;

    @Resource
    FundProfitDao fundProfitDao;

    @Override
    public BaseResult createFundApply(FundApply pojo) {
        BaseResult result = new BaseResult();
        int returnVal = fundApplyDao.insert(FundApplyConverter.toFundApplyDO(pojo));
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "添加合伙人失败！");
        }
        result.setMessage("添加成功！");
        return result;
    }

    @Override
    public BaseResult removeFundApply(FundApply pojo) {
        BaseResult result = new BaseResult();
        int returnVal = fundApplyDao.delete(pojo.getFaFundId());
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "删除合伙人失败！");
        }
        result.setMessage("删除成功！");
        return result;
    }

    @Override
    public BaseResult modifyFundApply(FundApply pojo) {
        BaseResult result = new BaseResult();
        int returnVal = fundApplyDao.update(FundApplyConverter.toFundApplyDO(pojo));
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "修改合伙人信息失败！");
        }
        result.setMessage("修改成功！");
        return result;
    }

    @Override
    public PlainResult<FundApply> queryById(int id) {
        PlainResult<FundApply> result = new PlainResult<FundApply>();
        FundApplyDO applyDO = fundApplyDao.findById(id);
        if (applyDO == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到对应合伙人信息");
            return result;
        }
        FundApply fundApply = FundApplyConverter.toFundApply(applyDO);
        result.setData(fundApply);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageResult<FundApplyDO> queryList(FundApplyDO fa, PageCondition pageCondition) {
        PageResult<FundApplyDO> result = new PageResult<FundApplyDO>(pageCondition);
        int totalCount = fundApplyDao.countListByParam(fa);
        result.setTotalCount(totalCount);
        if (totalCount > 0) {
            result.setData(fundApplyDao.findListByParam(fa, pageCondition));
        }

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public ListResult<FundApplyDO> queryAllApplyList(FundApplyDO fa) {
        ListResult<FundApplyDO> result = new ListResult<FundApplyDO>();
        result.setData(fundApplyDao.findListByParam(fa, null));
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult createFundApply(FundApply fundApply, List<FundProfit> fundProfitList) {
        BaseResult result = new BaseResult();
        // 1.插入有限合伙人表
        FundApplyDO fundApplyDO = FundApplyConverter.toFundApplyDO(fundApply);
        int returnVal = fundApplyDao.insert(fundApplyDO);
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "添加合伙人失败！");
        }
        // 2.插入有限合伙人收益表
        if (fundProfitList != null) {
            for (FundProfit fundProfit : fundProfitList) {
                FundProfitDO fundProfitDo = FundProfitConverter.toFundProfitDO(fundProfit);
                fundProfitDo.setFpFundId(fundApplyDO.getFaFundId()); // 外键：有限合伙表ID
                returnVal += fundProfitDao.insert(fundProfitDo);
            }
        }
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "添加合伙人失败！");
        }
        result.setMessage("添加成功！");
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult updateFundApply(FundApply fundApply, List<FundProfit> fundProfitList) {
        BaseResult result = new BaseResult();
        // 1.修改有限合伙人表
        FundApplyDO fundApplyDO = FundApplyConverter.toFundApplyDO(fundApply);
        int returnVal = fundApplyDao.update(fundApplyDO);
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "修改合伙人失败！");
        }
        // 2.有限合伙人收益表先删除再插入
        FundProfit fp = new FundProfit();
        fp.setFpFundId(fundApply.getFaFundId());
        List<FundProfitDO> fpList = fundProfitDao.queryListByParam(FundProfitConverter.toFundProfitDO(fp));
        for (FundProfitDO fundProfitDO : fpList) {
            fundProfitDao.delete(fundProfitDO.getFpProfitId());
        }
        if (fundProfitList != null) {
            for (FundProfit fundProfit : fundProfitList) {
                FundProfitDO fundProfitDo = FundProfitConverter.toFundProfitDO(fundProfit);
                fundProfitDo.setFpFundId(fundApplyDO.getFaFundId()); // 外键：有限合伙表ID
                returnVal += fundProfitDao.insert(fundProfitDo);
            }
        }
        result.setMessage("修改成功！");
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageResult<FundApplyDO> queryPageListBySearchParam(FundApplySearchDO pojo, PageCondition pageCondition) {
        PageResult<FundApplyDO> result = new PageResult<FundApplyDO>(pageCondition);
        int totalCount = fundApplyDao.countListBySearchParam(pojo);
        result.setTotalCount(totalCount);
        if (totalCount > 0) {
            result.setData(fundApplyDao.findPageListBySearchParam(pojo, pageCondition));
        }
        return result;
    }
}
