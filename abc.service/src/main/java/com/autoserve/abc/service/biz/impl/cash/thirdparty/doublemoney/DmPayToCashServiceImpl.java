package com.autoserve.abc.service.biz.impl.cash.thirdparty.doublemoney;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.TocashRecordDO;
import com.autoserve.abc.dao.dataobject.TocashRecordJDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.TocashRecordDao;
import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.Deal;
import com.autoserve.abc.service.biz.entity.DealDetail;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.enums.ToCashState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * 双钱支付提现的业务逻辑
 * 
 * @author J.YL 2014年11月26日 下午8:51:13
 */
@Service
public class DmPayToCashServiceImpl implements ToCashService {

    @Resource
    private TocashRecordDao            toCashRecordDao;
    @Resource
    private DealRecordService          dealRecord;
    @Resource
    private AccountInfoService         account;
    @Resource
    private SysConfigService           sysConfigService;
    @Autowired
	private PaymentPlanService 		   paymentPlanService;
    @Resource
	private UserService 			   userService;

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

    /**
     * 提现
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult toCash(Integer userId, UserType userType, BigDecimal moneyAmount) {
        BaseResult result = new BaseResult();
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
        toCashDo.setTocashState(ToCashState.PROCESSING.getState());
        toCashDo.setTocashUserId(userId);
        toCashDo.setTocashAccountId(payAccountNo);
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
        List<DealDetail> detailList = new ArrayList<DealDetail>(1);
        detailList.add(detail);

        deal.setDealDetail(detailList);
        deal.setBusinessType(DealType.TOCASH);
        deal.setInnerSeqNo(seqNo);
        deal.setOperator(userId);

        PlainResult<DealReturn> dealResult = dealRecord.createBusinessRecord(deal, toCashCallback);
        if (!dealResult.isSuccess()) {
            throw new BusinessException("提现创建失败");
        }

        // dealRecord.invokePayment(seqNo.getUniqueNo());
        return new BaseResult();
    }

    @Override
    public PageResult<TocashRecordJDO> queryUserInvestorExtr(TocashRecordJDO tocashRecordJDO,
                                                             PageCondition pageCondition, String startDate,
                                                             String endDate) {

        int count = this.toCashRecordDao.countUserInvestorExtr(tocashRecordJDO, pageCondition, startDate, endDate);

        PageResult<TocashRecordJDO> result = new PageResult<TocashRecordJDO>(pageCondition);

        if (count > 0) {

            List<TocashRecordJDO> list = this.toCashRecordDao.userInvestorExtr(tocashRecordJDO, pageCondition,
                    startDate, endDate);

            result.setData(list);

            result.setTotalCount(count);
        }

        return result;
    }

    @Override
    public PageResult<TocashRecordJDO> queryEmpInvestorExtr(TocashRecordJDO tocashRecordJDO,
                                                            PageCondition pageCondition, String startDate,
                                                            String endDate) {

        int count = this.toCashRecordDao.countEmpInvestorExtr(tocashRecordJDO, pageCondition, startDate, endDate);

        PageResult<TocashRecordJDO> result = new PageResult<TocashRecordJDO>(pageCondition);

        if (count > 0) {
            List<TocashRecordJDO> list = this.toCashRecordDao.empInvestorExtr(tocashRecordJDO, pageCondition,
                    startDate, endDate);

            result.setData(list);

            result.setTotalCount(count);
        }
        return result;
    }

    @Override
    public PlainResult<DealReturn> toCashOther(Integer userId, UserType userType, BigDecimal moneyAmount, Map param) {
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
        toCashDo.setTocashState(ToCashState.PROCESSING.getState());
        toCashDo.setTocashUserId(userId);
        toCashDo.setTocashAccountId(accountResult.getData().getAccountMark());
        toCashDo.setTocashAmount(moneyAmount);
        toCashDo.setTocashSeqNo(seqNo.getUniqueNo());
        //使用提现额度   
        Object object = param.get("cashQuota");
        if (object != null) {
            BigDecimal cashQuota = (BigDecimal) object;
            toCashDo.setTocashQuota(cashQuota);
        }
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
        param.put("userId", userId);
        param.put("type", userType.getType());
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
    public PlainResult<DealReturn> platformToCashOther(Integer userId, BigDecimal moneyAmount, Map param) {
        PlainResult<DealReturn> result = new PlainResult<DealReturn>();
       
        String receiveNo = sysConfigService.querySysConfig(SysConfigEntry.PLATFORM_ACCOUNT).getData().getConfValue();
        String payAccountNo = "1042272";
        TocashRecordDO toCashDo = new TocashRecordDO();
        InnerSeqNo seqNo = InnerSeqNo.getInstance();
        toCashDo.setTocashState(ToCashState.PROCESSING.getState());
        toCashDo.setTocashUserId(userId);
        toCashDo.setTocashAccountId("p613");
        toCashDo.setTocashAmount(moneyAmount);
        toCashDo.setTocashSeqNo(seqNo.getUniqueNo());
        //使用提现额度   
        Object object = param.get("cashQuota");
        if (object != null) {
            BigDecimal cashQuota = (BigDecimal) object;
            toCashDo.setTocashQuota(cashQuota);
        }
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
        param.put("userId", userId);
       // param.put("type", userType.getType());
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
    public PageResult<TocashRecordDO> queryListByUserId(TocashRecordDO tocashRecordDO, PageCondition pageCondition,
                                                        String startDate, String endDate) {

        int count = this.toCashRecordDao.countQueryListByUserId(tocashRecordDO, pageCondition, startDate, endDate);
        PageResult<TocashRecordDO> result = new PageResult<TocashRecordDO>(pageCondition);
        if (count > 0) {
            List<TocashRecordDO> list = this.toCashRecordDao.queryListByUserId(tocashRecordDO, pageCondition,
                    startDate, endDate);
            result.setData(list);
            result.setTotalCount(count);
        }

        return result;
    }

    @Override
    public BaseResult updateBySeqNo(TocashRecordDO toCashDo) {
        BaseResult result = new BaseResult();
        int i = toCashRecordDao.updateBySeqNoSelective(toCashDo);
        if (i <= 0) {
            result.setSuccess(false);
            throw new BusinessException(CommonResultCode.ERROR_DB.getCode(), "提现数据更新失败");
        } else {
            result.setSuccess(true);
        }
        return result;
    }

    @Override
    public PlainResult<TocashRecordDO> queryBySeqNo(String seqNo) {
        PlainResult<TocashRecordDO> result = new PlainResult<TocashRecordDO>();
        TocashRecordDO tocashRecordDO = toCashRecordDao.queryBySeqNo(seqNo);
        if (tocashRecordDO == null) {
            result.setSuccess(false);
            throw new BusinessException(CommonResultCode.ERROR_DB.getCode(), "提现记录查询失败！");
        }
        result.setData(tocashRecordDO);
        return result;
    }

    @Override
    public PageResult<TocashRecordDO> countToCashDealByParams(TocashRecordDO tocashRecordDO,
                                                              PageCondition pageCondition, String startDate,
                                                              String endDate) {
        // TODO Auto-generated method stub
        return null;
    }

	
	/**
	 * 1、判断用户是否有使用免费提现的机会（待还本金<M）
	 * 2、查询客户本月使用免费提现的次数，是否达到上限（次数<=n）
	 * 3、如果上述两个条件都不满足，判断客户是否有使用免费提现额度的机会
	 */
    @Override
	public BaseResult calculationPlatformFee(Integer userid,BigDecimal cashMoney,Map<String,Object> map) {
    	BaseResult result=new BaseResult();
    	PlainResult<SysConfig> payCapitalResult = sysConfigService.querySysConfig(SysConfigEntry.WAIT_PAY_CAPITAL);
        if (!payCapitalResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "借款人使用免费提现次数的限制，待还本金的上限查询失败");
        }
        PlainResult<SysConfig> monthfreeTocashTimesResult = sysConfigService.querySysConfig(SysConfigEntry.MONTHFREE_TOCASH_TIMES);
        if (!monthfreeTocashTimesResult.isSuccess()) {
        	return result.setError(CommonResultCode.BIZ_ERROR, "每个用户每月免费提现次数查询失败");
        }
        //用户的待还本金
 		PlainResult<BigDecimal> waitPayCapital=paymentPlanService.queryWaitPayCapital(userid);
 		
 		//查询本月免费提现次数
 		PlainResult<UserDO> userDOResult=userService.findById(userid);
 		if(!userDOResult.isSuccess()){
 			result.setSuccess(false);
 			result.setMessage(userDOResult.getMessage());
 			return result;
 		}
 		boolean flagTimes=false;    //true 表示可以使用免费提现机会，false 表示不可以使用免费提现机会
 		double feePercent=0;        //平台承担的手续费比例,默认用户承担
 		//当月提现次数
 		PlainResult<Integer> resultx=this.countTocashCurrentMonth(userid);
 		if(!resultx.isSuccess()){
 			return result.setError(CommonResultCode.BIZ_ERROR, resultx.getMessage());
 		}
 		if(resultx.getData()<Integer.parseInt(monthfreeTocashTimesResult.getData().getConfValue())){
 			flagTimes=true;
 		}
 		
 		//如果上述两个条件都不满足，判断客户是否有使用免费提现额度的机会
 		if(waitPayCapital.getData().compareTo(new BigDecimal(payCapitalResult.getData().getConfValue()))<0 && flagTimes){			
 			feePercent=100;  //由平台承担
 		}else{
 			//判断是否有免费提现额度	 		
 	 		BigDecimal cashQuota=BigDecimal.ZERO;
 	 		if(userDOResult.getData()!=null){
 	 			cashQuota=userDOResult.getData().getUserCashQuota();
 	 		} 		
 	 		if(cashQuota!=null && cashQuota.compareTo(BigDecimal.ZERO)>0){
 	 			if(cashQuota.compareTo(cashMoney)<=0){
 	 				feePercent=cashQuota.divide(cashMoney,10,BigDecimal.ROUND_HALF_UP).
 	 						multiply(new BigDecimal("100")).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
 	 				map.put("cashQuota",cashQuota);
 	 			}else{
 	 				feePercent=100;
 	 				map.put("cashQuota",cashMoney);
 	 			}
 	 		}else{
 	 			map.put("cashQuota",new BigDecimal(0));
 	 		}
 		}
 		map.put("FeePercent", String.valueOf(feePercent));
		return BaseResult.SUCCESS;
	}
    
    
    @Override
    public  PlainResult<Integer> countTocashCurrentMonth(Integer userId){
    	PlainResult<Integer> result=new PlainResult<Integer>();
    	Integer count=toCashRecordDao.countTocashCurrentMonth(userId);
    	if(count==null){
    		result.setError(CommonResultCode.BIZ_ERROR, "查询当月用户的提现次数失败");
    		return result;
    	}
    	result.setData(count);
    	return result;
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
