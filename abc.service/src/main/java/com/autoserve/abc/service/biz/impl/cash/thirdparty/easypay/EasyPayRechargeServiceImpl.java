/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.cash.thirdparty.easypay;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RechargeRecordDO;
import com.autoserve.abc.dao.dataobject.TocashRecordJDO;
import com.autoserve.abc.dao.dataobject.UserIdentityDO;
import com.autoserve.abc.dao.intf.RechargeRecordDao;
import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.callback.center.CashCallBackCenter;
import com.autoserve.abc.service.biz.entity.*;
import com.autoserve.abc.service.biz.enums.*;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.RechargeService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 易生支付充值实现
 * 
 * @author J.YL 2014年11月26日 下午8:23:51
 */
//@Service
public class EasyPayRechargeServiceImpl implements RechargeService {
    private static final Logger        logger           = LoggerFactory.getLogger(EasyPayRechargeServiceImpl.class);
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
    public PlainResult<DealReturn> recharge(Integer userId, UserType type, BigDecimal moneyAmount,Map map) {
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
        recharge.setRechargeAccountId(payAccountNo);
        recharge.setRechargeAmount(moneyAmount);
        InnerSeqNo seqNo = InnerSeqNo.getInstance();
        recharge.setRechargeSeqNo(seqNo.getUniqueNo());
        recharge.setRechargeState(RechargeState.PROCESSING.getState());

        rechargeDao.insert(recharge);
        DealDetail detail = new DealDetail();
        detail.setMoneyAmount(moneyAmount);
        detail.setPayAccountId(payAccountNo);
        detail.setDealDetailType(DealDetailType.RECHARGE_MONEY);
        detail.setReceiveAccountId(receiveAccountNo);

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

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    private BaseResult doPaidSuccess(DealNotify data) {
        RechargeRecordDO rechargeDo = new RechargeRecordDO();
        rechargeDo.setRechargeAmount(data.getTotalFee());
        rechargeDo.setRechargeSeqNo(data.getInnerSeqNo());
        rechargeDo.setRechargeState(data.getState().getState());
        rechargeDao.updateBySeqNoSelective(rechargeDo);

        UserIdentityDO userIdentity = rechargeDao.queryUserIdentityBySeqNo(data.getInnerSeqNo());
        if(!userService.modifyUserBusinessState(userIdentity.getUserId(), UserType.valueOf(userIdentity.getUserType()), UserBusinessState.RECHARGED).isSuccess()){
            logger.warn("修改用户业务状态失败: 用户ID={}, 用户类型={}, 交易流水号={}", userIdentity.getUserId(), userIdentity.getUserType(), data.getInnerSeqNo());
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
	public PageResult<RechargeRecordDO> queryRechargeRecordByparam(
			RechargeRecordDO rechargeRecordDO, PageCondition pageCondition,
			String startDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseResult updateBackStatus(RechargeRecordDO rechargeDo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> backRecharge(Integer empId, UserType platform, BigDecimal bigdecimal,
			Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateRecharge(Integer empId, BigDecimal moneyAmount, RechargeRecordDO recharge) {
		// TODO Auto-generated method stub
		
	}

}
