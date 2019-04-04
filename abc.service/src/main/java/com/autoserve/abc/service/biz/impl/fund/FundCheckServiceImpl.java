package com.autoserve.abc.service.biz.impl.fund;

import com.autoserve.abc.dao.dataobject.FundCheckDO;
import com.autoserve.abc.dao.intf.FundCheckDao;
import com.autoserve.abc.service.biz.convert.FundCheckConverter;
import com.autoserve.abc.service.biz.entity.FundCheck;
import com.autoserve.abc.service.biz.intf.fund.FundCheckService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 类FundCheckServiceImpl.java的实现描述：TODO 类实现描述
 * 
 * @author wangyongheng 2014/12/08
 */
@Service
public class FundCheckServiceImpl implements FundCheckService{

	@Resource
	FundCheckDao fundCheckDao;
	
	@Override
	public BaseResult createFundCheck(FundCheck pojo) {
		BaseResult result = new BaseResult();
		int returnVal= fundCheckDao.insert(FundCheckConverter.toFundCheckDO(pojo));
		if(returnVal<=0){
			result.setErrorMessage(CommonResultCode.BIZ_ERROR, "添加合伙人预约审核失败！");
		}
		result.setMessage("添加成功！");
		return result;
	}

	@Override
	public BaseResult removeFundCheck(FundCheck pojo) {
		BaseResult result = new BaseResult();
		int returnVal = fundCheckDao.delete(pojo.getFcCheckId());
		if(returnVal<=0){
			result.setErrorMessage(CommonResultCode.BIZ_ERROR, "删除合伙人预约审核失败！");
		}
		result.setMessage("删除成功！");
		return result;
	}

	@Override
	public BaseResult modifyFundCheck(FundCheck pojo) {
		BaseResult result = new BaseResult();
		int returnVal = fundCheckDao.update(FundCheckConverter.toFundCheckDO(pojo));
		if(returnVal<=0){
			result.setErrorMessage(CommonResultCode.BIZ_ERROR, "修改合伙人预约审核信息失败！");
		}
		result.setMessage("修改成功！");
		return result;
	}

	@Override
	public PlainResult<FundCheck> queryById(int id) {
		PlainResult<FundCheck> result = new PlainResult<FundCheck>();
        FundCheckDO fundCheckDO = fundCheckDao.findById(id);
		if(fundCheckDO==null){
			result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到对应合伙人预约审核信息");
            return result;
		}
        FundCheck fundCheck= FundCheckConverter.toFundCheck(fundCheckDO);
		result.setData(fundCheck);
		return result;
	}

}
