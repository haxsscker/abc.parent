package com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.RechargeRecordDO;
import com.autoserve.abc.dao.dataobject.TocashRecordJDO;
import com.autoserve.abc.dao.dataobject.UserIdentityDO;
import com.autoserve.abc.dao.intf.RechargeRecordDao;
import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.callback.center.CashCallBackCenter;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.Deal;
import com.autoserve.abc.service.biz.entity.DealDetail;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.RechargeState;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.enums.UserBusinessState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.RechargeService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * 双钱支付充值实现
 * 
 * @author J.YL 2014年11月26日 下午8:23:51
 */
@Service
public class BhPayRechargeServiceImpl implements RechargeService {
    private static final Logger        logger           = LoggerFactory.getLogger(BhPayRechargeServiceImpl.class);
    @Resource
    private RechargeRecordDao          rechargeDao;
    @Resource
    private AccountInfoService         account;
    @Resource
    private DealRecordService          dealRecord;
    @Resource
    private UserService                userService;
    @Resource
    private SysConfigService           sysConfigService;
    @Resource
    private DealRecordService          dealRecordService;

    private final Callback<DealNotify> rechargeCallback = new Callback<DealNotify>() {
                                                            @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
                                                            @Override
                                                            public BaseResult doCallback(DealNotify data) {
                                                                switch (data.getState()) {
                                                                    case NOCALLBACK:
                                                                        return new BaseResult().setError(
                                                                                CommonResultCode.BIZ_ERROR,
                                                                                "交易状态的值不符合预期");
                                                                    case SUCCESS:
                                                                        return doPaidSuccess(data);
                                                                    case FAILURE:
                                                                        return doPaidFailure(data);
                                                                }
                                                                return new BaseResult();
                                                            }
                                                        };

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public PlainResult<DealReturn> recharge(Integer userId, UserType type, BigDecimal moneyAmount, Map map) {
        PlainResult<DealReturn> result = new PlainResult<DealReturn>();
        RechargeRecordDO recharge = new RechargeRecordDO();
        UserIdentity userIdentity = new UserIdentity();
        userIdentity.setUserId(userId);
        userIdentity.setUserType(type);
        PlainResult<Account> payAccountResult = account.queryByUserId(userIdentity);
        Account payAccount = null;
        if (payAccountResult.isSuccess()) {
            payAccount = payAccountResult.getData();
        } else {
            result.setSuccess(false);
            result.setCode(payAccountResult.getCode());
            result.setMessage(payAccountResult.getMessage());
            result.setData(null);
            return result;
        }
        String payAccountNo = payAccount.getAccountNo();
        String receiveAccountNo = sysConfigService.querySysConfig(SysConfigEntry.PLATFORM_ACCOUNT).getData()
                .getConfValue();
        recharge.setRechargeUserId(payAccount.getAccountUserId());
        recharge.setRechargeAccountId(payAccount.getAccountNo());
        recharge.setRechargeAmount(moneyAmount);
        InnerSeqNo seqNo = InnerSeqNo.getInstance();
        recharge.setRechargeSeqNo(seqNo.getUniqueNo());
        recharge.setRechargeState(RechargeState.PROCESSING.getState());

        rechargeDao.insert(recharge);
        DealDetail detail = new DealDetail();
        detail.setMoneyAmount(moneyAmount);
        detail.setPayAccountId("");
        detail.setDealDetailType(DealDetailType.RECHARGE_MONEY);
        detail.setReceiveAccountId(payAccountNo);
        map.put("userId", userId);
        map.put("type", type.getType());
        detail.setData(map);

        Deal deal = new Deal();

        deal.setBusinessType(DealType.RECHARGE);
        List<DealDetail> detailList = new ArrayList<DealDetail>(1);
        detailList.add(detail);
        deal.setDealDetail(detailList);
        deal.setInnerSeqNo(seqNo);
        deal.setOperator(userId);
        deal.setBusinessId(recharge.getRechargeId());
        DealReturn dealReturn = dealRecord.createBusinessRecord(deal, rechargeCallback).getData();
        result.setData(dealReturn);
        //dealRecord.invokePayment(seqNo.getUniqueNo());
        return result;
    }
    
    /**
     * 后台商户充值交易记录
     */
    @Override
	public Map<String, String> backRecharge(Integer empId, UserType platform, BigDecimal moneyAmount,
			Map<String, String> map) {
    	  Map<String, String> resultMap = new LinkedHashMap <String, String>();
    	  RechargeRecordDO recharge = new RechargeRecordDO();
    	  DealRecordDO dealRecord = new DealRecordDO();
    	  InnerSeqNo seqNo = InnerSeqNo.getInstance();
    	  Random random = new Random();  
    	  String id="";  
    	  for (int i=0;i<6;i++)  
    	  {  
    	      id+=random.nextInt(10);  
    	  } 
    	  dealRecord.setDrId(Integer.parseInt(id));
    	  dealRecord.setDrMoneyAmount(moneyAmount);
    	  dealRecord.setDrDetailType(6); //充值
    	  dealRecord.setDrType(4);  //充值
    	  dealRecord.setDrInnerSeqNo(seqNo.getUniqueNo());
    	  dealRecord.setDrState(0);//等待中
    	  dealRecord.setDrOperator(empId);
		  dealRecord.setDrReceiveAccount("800055100010001");
    	  dealRecordService.insertRecord(dealRecord);
    	  
    	  recharge.setRechargeId(Integer.parseInt(id));
          recharge.setRechargeUserId(empId);
          recharge.setRechargeAmount(moneyAmount);
          recharge.setRechargeSeqNo(seqNo.getUniqueNo());
          recharge.setRechargeState(RechargeState.PROCESSING.getState());
          int num= rechargeDao.insert(recharge);
          if(num>0){
        	  resultMap.put("resultData", "sucess");
        	  resultMap.put("id", id);
        	  resultMap.put("MerBillNo", seqNo.getUniqueNo());
          }else{
        	  resultMap.put("resultData", "fail");
          }
		  return resultMap;
	}
    
    @Override
	public void updateRecharge(Integer empId,BigDecimal moneyAmount,RechargeRecordDO rechargeDo) {
    	 //InnerSeqNo seqNo = InnerSeqNo.getInstance();
    	 DealRecordDO dealRecord = new DealRecordDO();
    	 dealRecord.setDrOperator(empId);
    	 dealRecord.setDrInnerSeqNo(rechargeDo.getRechargeSeqNo());
    	 dealRecord.setDrState(rechargeDo.getRechargeState());
    	 dealRecordService.updateDealRecordState(dealRecord);
    	 
	     rechargeDo.setRechargeUserId(empId);
	   	 rechargeDo.setRechargeAmount(moneyAmount);
         //rechargeDo.setRechargeSeqNo(rechargeDo.getRechargeSeqNo());
         rechargeDao.updateChargeStatus(rechargeDo);
		
	}

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    private BaseResult doPaidSuccess(DealNotify data) {
        RechargeRecordDO rechargeDo = new RechargeRecordDO();
        rechargeDo.setRechargeAmount(data.getTotalFee());
        rechargeDo.setRechargeSeqNo(data.getInnerSeqNo());
        rechargeDo.setRechargeState(data.getState().getState());
        rechargeDao.updateBySeqNoSelective(rechargeDo);

        UserIdentityDO userIdentity = rechargeDao.queryUserIdentityBySeqNo(data.getInnerSeqNo());
        if (!userService.modifyUserBusinessState(userIdentity.getUserId(),
                UserType.valueOf(userIdentity.getUserType()), UserBusinessState.RECHARGED).isSuccess()) {
            logger.warn("修改用户业务状态失败: 用户ID={}, 用户类型={}, 交易流水号={}", userIdentity.getUserId(), userIdentity.getUserType(),
                    data.getInnerSeqNo());
        }

        return BaseResult.SUCCESS;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    private BaseResult doPaidFailure(DealNotify data) {
        RechargeRecordDO rechargeDo = new RechargeRecordDO();
        rechargeDo.setRechargeAmount(data.getTotalFee());
        rechargeDo.setRechargeSeqNo(data.getInnerSeqNo());
        rechargeDo.setRechargeState(data.getState().getState());
        rechargeDao.updateBySeqNoSelective(rechargeDo);
        return new BaseResult();
    }

    @PostConstruct
    private void registCallBack() {
        CashCallBackCenter.registCallBack(DealType.RECHARGE, rechargeCallback);
    }

    @Override
    public PageResult<TocashRecordJDO> queryUserRecharge(TocashRecordJDO tocashRecordJDO, PageCondition pageCondition,
                                                         String startDate, String endDate) {
        Integer count = this.rechargeDao.countUserListByParam(tocashRecordJDO, pageCondition, startDate, endDate);

        PageResult<TocashRecordJDO> result = new PageResult<TocashRecordJDO>(pageCondition);

        if (count > 0) {
            List<TocashRecordJDO> list = this.rechargeDao.userListByParam(tocashRecordJDO, pageCondition, startDate,
                    endDate);
            result.setData(list);

            result.setTotalCount(count);
        }

        return result;
    }

    @Override
    public PageResult<TocashRecordJDO> queryEmpRecharge(TocashRecordJDO tocashRecordJDO, PageCondition pageCondition,
                                                        String startDate, String endDate) {
        PageResult<TocashRecordJDO> result = new PageResult<TocashRecordJDO>(pageCondition);

        Integer count = this.rechargeDao.countEmpListByParam(tocashRecordJDO, pageCondition, startDate, endDate);

        if (count > 0) {
            List<TocashRecordJDO> list = this.rechargeDao.empListByParam(tocashRecordJDO, pageCondition, startDate,
                    endDate);

            result.setData(list);

            result.setTotalCount(count);
        }

        return result;
    }

    @Override
    public PageResult<RechargeRecordDO> queryRechargeRecordByparam(RechargeRecordDO rechargeRecordDO,
                                                                   PageCondition pageCondition, String startDate,
                                                                   String endDate) {
        PageResult<RechargeRecordDO> result = new PageResult<RechargeRecordDO>(pageCondition);
        Integer count = this.rechargeDao.countRechargeRecordByparam(rechargeRecordDO, startDate, endDate);
        if (count > 0) {
            List<RechargeRecordDO> list = this.rechargeDao.rechargeRecordByparam(rechargeRecordDO, pageCondition,
                    startDate, endDate);

            result.setData(list);

            result.setTotalCount(count);
        }

        return result;
    }

    @Override
    public BaseResult updateBackStatus(RechargeRecordDO rechargeDo) {
        BaseResult result = new BaseResult();
        int i = rechargeDao.updateBySeqNoSelective(rechargeDo);
        if (i <= 0) {
            result.setSuccess(false);
            throw new BusinessException(CommonResultCode.BIZ_ERROR.getCode(), "更新充值记录表失败");
        } else {
            result.setSuccess(true);
        }
        return result;
    }


}
