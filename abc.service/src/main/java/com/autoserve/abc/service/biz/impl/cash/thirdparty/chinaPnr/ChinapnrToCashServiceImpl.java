package com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.TocashRecordDO;
import com.autoserve.abc.dao.dataobject.TocashRecordJDO;
import com.autoserve.abc.dao.intf.TocashRecordDao;
import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.Deal;
import com.autoserve.abc.service.biz.entity.DealDetail;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.enums.ToCashState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;

@Service
public class ChinapnrToCashServiceImpl implements ToCashService {
	 @Resource
	    private TocashRecordDao            toCashRecordDao;
	    @Resource
	    private DealRecordService          dealRecord;
	    @Resource
	    private AccountInfoService         account;
	    @Resource
	    private SysConfigService           sysConfigService;

	    private final Callback<DealNotify> toCashCallback = new Callback<DealNotify>() {

	                                                          @Override
	                                                          @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	                                                          public BaseResult doCallback(DealNotify data) {
	                                                              switch (data.getState()) {
	                                                                  case NOCALLBACK:
	                                                                      return new BaseResult()
	                                                                              .setError(CommonResultCode.BIZ_ERROR,
	                                                                                      "交易状态的值不符合预期");
	                                                                  case SUCCESS:
	                                                                      return doPaidSuccess(data);
	                                                                  case FAILURE:
	                                                                      return doPaidFailure(data);
	                                                              }
	                                                              return new BaseResult();
	                                                          }

	                                                      };

	    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	    private BaseResult doPaidSuccess(DealNotify data) {
	        TocashRecordDO toCashDo = new TocashRecordDO();
	        toCashDo.setTocashAmount(data.getTotalFee());
	        toCashDo.setTocashSeqNo(data.getInnerSeqNo());
	        toCashDo.setTocashState(data.getState().getState());
	        toCashRecordDao.updateBySeqNoSelective(toCashDo);
	        return new BaseResult();
	    }

	    /**
	     * 提现失败 解冻
	     * 
	     * @param data
	     * @return
	     */
	    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	    private BaseResult doPaidFailure(DealNotify data) {
	        TocashRecordDO toCashDo = new TocashRecordDO();
	        toCashDo.setTocashAmount(data.getTotalFee());
	        toCashDo.setTocashSeqNo(data.getInnerSeqNo());
	        toCashDo.setTocashState(data.getState().getState());
	        toCashRecordDao.updateBySeqNoSelective(toCashDo);
	        //提现失败 解冻
	        dealRecord.unfrozenDealMoney(data.getInnerSeqNo());
	        return new BaseResult();
	    }
	@Override
	public BaseResult toCash(Integer userId, UserType userType,
			BigDecimal moneyAmount) {
		return null;
	}

	@Override
	public PlainResult<DealReturn> toCashOther(Integer userId,
		 UserType userType, BigDecimal moneyAmount, Map param) {
		 PlainResult<DealReturn> result = new PlainResult<DealReturn>();
	     UserIdentity userIdentity = new UserIdentity();
	     userIdentity.setUserId(userId);
	     userIdentity.setUserType(userType);
	     PlainResult<Account> accountResult = account.queryByUserId(userIdentity);
	     if (!accountResult.isSuccess()) {
	        result.setSuccess(false);
	        result.setMessage(accountResult.getMessage());
	        return result;
	     }
	    String receiveNo = sysConfigService.querySysConfig(SysConfigEntry.PLATFORM_ACCOUNT).getData().getConfValue();
	    String payAccountNo = accountResult.getData().getAccountNo();
	    TocashRecordDO toCashDo = new TocashRecordDO();
	    InnerSeqNo seqNo = InnerSeqNo.getInstance();
	    toCashDo.setTocashState(ToCashState.BEFORE.getState());
	    toCashDo.setTocashUserId(userId);
	    toCashDo.setTocashAccountId(accountResult.getData().getAccountMark().split("_")[1]);
	    toCashDo.setTocashAmount(moneyAmount);
	    toCashDo.setTocashSeqNo(seqNo.getUniqueNo());
	    int flag = toCashRecordDao.insert(toCashDo);
	   
	    if (flag <= 0) {
	        throw new BusinessException(CommonResultCode.ERROR_DB.getCode(), "提现记录插入错误");
	    }
	    
	    Deal deal = new Deal();

	    deal.setBusinessId(toCashDo.getTocashId());

	    DealDetail detail = new DealDetail();
	    detail.setDealDetailType(DealDetailType.TOCASH_MONEY);
	    detail.setMoneyAmount(moneyAmount);
	    detail.setPayAccountId(payAccountNo);
	    detail.setReceiveAccountId(receiveNo);
//	    param.put("userId", userId);
//	    param.put("type", userType.getType());
        param.put("merPriv", seqNo.getUniqueNo());
	    detail.setData(param);
	    List<DealDetail> detailList = new ArrayList<DealDetail>(1);
	    detailList.add(detail);

	    deal.setDealDetail(detailList);
	    deal.setBusinessType(DealType.TOCASH);
	    deal.setInnerSeqNo(seqNo);
	    deal.setOperator(userId);

	    result = dealRecord.createBusinessRecord(deal, toCashCallback);
	    if (!result.isSuccess()) {
	        throw new BusinessException("提现创建失败");
	    }
	    
	    //dealRecord.invokePayment(seqNo.getUniqueNo());
	    return result;
	}

	@Override
	public PageResult<TocashRecordJDO> queryUserInvestorExtr(
			TocashRecordJDO tocashRecordJDO, PageCondition pageCondition,
			String startDate, String endDate) {
		return null;
	}

	@Override
	public PageResult<TocashRecordJDO> queryEmpInvestorExtr(
			TocashRecordJDO tocashRecordJDO, PageCondition pageCondition,
			String startDate, String endDate) {
		return null;
	}
	

	@Override
	public PageResult<TocashRecordDO> queryListByUserId(
			TocashRecordDO tocashRecordDO, PageCondition pageCondition,
			String startDate, String endDate) {
		int count = this.toCashRecordDao.countQueryListByUserId(tocashRecordDO, pageCondition, startDate, endDate);
        PageResult<TocashRecordDO> result = new PageResult<TocashRecordDO>(pageCondition);
        if (count > 0) {
            List<TocashRecordDO> list = this.toCashRecordDao.queryListByUserId(tocashRecordDO, pageCondition, startDate, endDate);
            result.setData(list);
            result.setTotalCount(count);
        }
		return result;
	}

	@Override
	public BaseResult updateBySeqNo(TocashRecordDO toCashDo) {
		BaseResult result = new BaseResult();
		int i = toCashRecordDao.updateBySeqNoSelective(toCashDo);
		if(i<=0){
		result.setSuccess(false);
		throw new BusinessException(CommonResultCode.ERROR_DB.getCode(), "提现数据更新失败");
		}else{
		result.setSuccess(true);
		}
		return result;
		
	}

	@Override
	public PageResult<TocashRecordDO> countToCashDealByParams(
			TocashRecordDO tocashRecordDO, PageCondition pageCondition,
			String startDate, String endDate) {
		int count = this.toCashRecordDao.countDealByParams(tocashRecordDO, null, startDate, endDate);
	    PageResult<TocashRecordDO> result = new PageResult<TocashRecordDO>(pageCondition);
	    if (count > 0) {
	        List<TocashRecordDO> list = this.toCashRecordDao.queryDealByParams(tocashRecordDO, pageCondition, startDate, endDate);
	        result.setData(list);
	        result.setTotalCount(count);
	    }
		return result;
	}

	@Override
	public PlainResult<TocashRecordDO> queryBySeqNo(String seqNo) {
		return null;
	}

	@Override
	public BaseResult calculationPlatformFee(Integer userid,
			BigDecimal cashMoney, Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlainResult<Integer> countTocashCurrentMonth(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlainResult<DealReturn> platformToCashOther(Integer userId,
			BigDecimal moneyAmount, Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> toBackCash(Integer empId, UserType userType, BigDecimal cashMoney,
			Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCash(Integer empId, BigDecimal tocashAmount, TocashRecordDO toCashDo) {
		// TODO Auto-generated method stub
		
	}

}
