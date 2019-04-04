package com.autoserve.abc.service.biz.impl.fund;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.dataobject.FundProfitDO;
import com.autoserve.abc.dao.intf.FundProfitDao;
import com.autoserve.abc.service.biz.convert.FundProfitConverter;
import com.autoserve.abc.service.biz.entity.FundProfit;
import com.autoserve.abc.service.biz.intf.fund.FundProfitService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 类FundProfitServiceImpl.java的实现描述：TODO 类实现描述
 * 
 * @author wangyongheng 2014/12/19
 */
@Service
public class FundProfitServiceImpl implements FundProfitService {

    @Resource
    FundProfitDao fundProfitDao;

    @Override
    public BaseResult createFundProfit(FundProfit pojo) {
        BaseResult result = new BaseResult();
        int returnVal = fundProfitDao.insert(FundProfitConverter.toFundProfitDO(pojo));
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "添加合伙人预约收益失败！");
        }
        result.setMessage("添加成功！");
        return result;
    }

    @Override
    public BaseResult removeFundProfit(FundProfit pojo) {
        BaseResult result = new BaseResult();
        int returnVal = fundProfitDao.delete(pojo.getFpProfitId());
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "删除合伙人预约收益失败！");
        }
        result.setMessage("删除成功！");
        return result;
    }

    @Override
    public BaseResult modifyFundProfit(FundProfit pojo) {
        BaseResult result = new BaseResult();
        int returnVal = fundProfitDao.update(FundProfitConverter.toFundProfitDO(pojo));
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "修改合伙人预约收益信息失败！");
        }
        result.setMessage("修改成功！");
        return result;
    }

    @Override
    public PlainResult<FundProfit> queryById(int id) {
        PlainResult<FundProfit> result = new PlainResult<FundProfit>();
        FundProfitDO profitDO = fundProfitDao.findById(id);
        if (profitDO == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到对应合伙人预约收益信息");
            return result;
        }
        FundProfit fundProfit = FundProfitConverter.toFundProfit(profitDO);
        result.setData(fundProfit);
        return result;
    }

    @Override
    public ListResult<FundProfit> queryList(FundProfit pojo) {
        ListResult<FundProfit> result = new ListResult<FundProfit>();
        List<FundProfitDO> fundProfitDOList = fundProfitDao.queryListByParam(FundProfitConverter.toFundProfitDO(pojo));
        if (CollectionUtils.isEmpty(fundProfitDOList)) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到对应合伙人预约收益信息");
            return result;
        }
        List<FundProfit> fundProfitList = FundProfitConverter.toFundProfitList(fundProfitDOList);
        result.setData(fundProfitList);
        return result;
    }

}
