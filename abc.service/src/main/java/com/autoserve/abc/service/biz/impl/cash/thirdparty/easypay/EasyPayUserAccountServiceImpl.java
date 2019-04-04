/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.cash.thirdparty.easypay;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.UserAccountDO;
import com.autoserve.abc.dao.intf.UserAccountDao;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.UserAccountMoney;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.intf.cash.UserAccountService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * 平台模式下用户资金账户ServiceImpl
 * 
 * @author J.YL 2014年11月26日 下午4:11:53
 */
//@Service
public class EasyPayUserAccountServiceImpl implements UserAccountService {
    private static final Logger logger = LoggerFactory.getLogger(EasyPayUserAccountServiceImpl.class);
    @Resource
    private UserAccountDao      userAccountDao;

    /**
     * 用户账户号查询用户资金财务状况
     */
    @Override
    public PlainResult<UserAccountMoney> queryUserAccountByAccountNo(String accountNo) {
        PlainResult<UserAccountMoney> result = new PlainResult<UserAccountMoney>();
        UserAccountDO userAccount = userAccountDao.findByAccountNo(accountNo);
        if (userAccount == null) {
            throw new BusinessException(CommonResultCode.ERROR_DATA_NOT_EXISTS.getCode(), String.format("账号为%s 无资金记录",
                    accountNo));
        }
        UserAccountMoney accountMoney = new UserAccountMoney();
        accountMoney.setFrozeenMoney(userAccount.getUaFrozen());
        accountMoney.setTotalMoney(userAccount.getUaTotalMoney());
        accountMoney.setUseableMoney(userAccount.getUaUseableMoney());
        result.setData(accountMoney);
        return result;
    }

    /**
     * 更新账户资金财务状况 已弃用
     * 
     * @deprecated
     */
    @Deprecated
    @Override
    public BaseResult updateUserAccountFinancial(List<Account> userAccounts, List<DealRecordDO> dealRecords) {
        List<String> userAccountList = new ArrayList<String>();
        for (Account ua : userAccounts) {
            userAccountList.add(ua.getAccountNo());
        }
        List<UserAccountDO> userAccountDos = null;
        List<Integer> ids = userAccountDao.findIdsByAccountNos(userAccountList);
        if (ids == null) {
            userAccountDos = new ArrayList<UserAccountDO>();
        } else {
            userAccountDos = userAccountDao.findByIds(ids);
        }
        Map<String, UserAccountDO> userAccountMap = new HashMap<String, UserAccountDO>();
        for (UserAccountDO uad : userAccountDos) {
            userAccountMap.put(uad.getUaAccountNo(), uad);
        }
        for (DealRecordDO record : dealRecords) {
            String payAccount = record.getDrPayAccount();
            String receiveAccount = record.getDrReceiveAccount();
            BigDecimal moneyAmount = record.getDrMoneyAmount();
            //根据交易类型判断对用户账户资金的操作，投资进行的是冻结，其余都是进行资金划转
            DealType dealDetailType = DealType.valueOf(record.getDrType());
            switch (dealDetailType) {
                case PAYBACK: {
                    //付款账户
                    UserAccountDO payUser = userAccountMap.get(payAccount);
                    BigDecimal payTotal = payUser.getUaTotalMoney();
                    BigDecimal payUseable = payUser.getUaUseableMoney();
                    payUser.setUaTotalMoney(payTotal.subtract(moneyAmount));
                    payUser.setUaUseableMoney(payUseable.subtract(moneyAmount));

                    //收款账户
                    UserAccountDO receiveUser = userAccountMap.get(receiveAccount);
                    BigDecimal receiveTotal = receiveUser.getUaTotalMoney();
                    BigDecimal receiveUserable = receiveUser.getUaUseableMoney();
                    receiveUser.setUaTotalMoney(receiveTotal.add(moneyAmount));
                    receiveUser.setUaUseableMoney(receiveUserable.add(moneyAmount));
                    break;
                }
                case INVESTER: {
                    //冻结账户
                    UserAccountDO payUser = userAccountMap.get(payAccount);
                    BigDecimal frozen = payUser.getUaFrozen();
                    BigDecimal useable = payUser.getUaUseableMoney();
                    payUser.setUaFrozen(frozen.add(moneyAmount));
                    payUser.setUaUseableMoney(useable.subtract(moneyAmount));
                    break;
                }
                case RECHARGE: {
                    //充值账户 更新的钱为正数
                    UserAccountDO payUser = userAccountMap.get(payAccount);
                    BigDecimal payTotal = payUser.getUaTotalMoney();
                    BigDecimal payUseable = payUser.getUaUseableMoney();
                    payUser.setUaTotalMoney(payTotal.add(moneyAmount));
                    payUser.setUaUseableMoney(payUseable.add(moneyAmount));

                    //收款账户为平台账户
                    UserAccountDO receiveUser = userAccountMap.get(receiveAccount);
                    BigDecimal receiveTotal = receiveUser.getUaTotalMoney();
                    BigDecimal receiveUserable = receiveUser.getUaUseableMoney();
                    payUser.setUaUseableMoney(receiveUserable.add(moneyAmount));
                    receiveUser.setUaTotalMoney(receiveTotal.add(moneyAmount));
                    break;
                }
                case REFUND: {
                    //退款发起账户
                    UserAccountDO payUser = userAccountMap.get(payAccount);
                    BigDecimal payTotal = payUser.getUaTotalMoney();
                    BigDecimal payUseable = payUser.getUaUseableMoney();
                    payUser.setUaTotalMoney(payTotal.subtract(moneyAmount));
                    payUser.setUaUseableMoney(payUseable.subtract(moneyAmount));

                    //收款账户
                    UserAccountDO receiveUser = userAccountMap.get(receiveAccount);
                    BigDecimal receiveTotal = receiveUser.getUaTotalMoney();
                    BigDecimal receiveUserable = receiveUser.getUaUseableMoney();
                    payUser.setUaUseableMoney(receiveUserable.add(moneyAmount));
                    receiveUser.setUaTotalMoney(receiveTotal.add(moneyAmount));
                    break;
                }
                case TOCASH: {
                    //提现时payAccount为平台账户
                    UserAccountDO payUser = userAccountMap.get(payAccount);
                    BigDecimal payTotal = payUser.getUaTotalMoney();
                    BigDecimal payUseable = payUser.getUaUseableMoney();
                    payUser.setUaTotalMoney(payTotal.subtract(moneyAmount));
                    payUser.setUaUseableMoney(payUseable.subtract(moneyAmount));

                    //收款账户receiveAccount为用户账户，均是直接减钱
                    UserAccountDO receiveUser = userAccountMap.get(receiveAccount);
                    BigDecimal receiveTotal = receiveUser.getUaTotalMoney();
                    BigDecimal receiveUserable = receiveUser.getUaUseableMoney();
                    payUser.setUaUseableMoney(receiveUserable.subtract(moneyAmount));
                    receiveUser.setUaTotalMoney(receiveTotal.subtract(moneyAmount));
                    break;
                }
                case PURCHASE:
                    break;
                default:
                    break;
            }
        }
        boolean payState = true;
        String message = "交易执行成功";
        for (UserAccountDO uad : userAccountDos) {
            if (uad.getUaTotalMoney().doubleValue() < 0.0 || uad.getUaFrozen().doubleValue() < 0.0
                    || uad.getUaUseableMoney().doubleValue() < 0.0) {
                logger.warn("[EasyPayUserAccountServiceImpl][updateUserAccountFinancial] accountNo:{} ",
                        uad.getUaAccountNo());
                payState = false;
                message = "交易执行失败，账户：" + uad.getUaAccountNo() + "资金不足";
            }
        }
        if (payState) {
            userAccountDao.batchInsert(userAccountDos);
        }
        BaseResult result = new BaseResult();
        result.setSuccess(payState);
        result.setMessage(message);
        return result;
    }

    /**
     * 前提是用户必须先开户 第三方接口返回之后对用户资金信息进行的更新
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    @Override
    public BaseResult updateThridPartReturn(List<Account> userAccounts, List<DealRecordDO> dealRecords) {
        List<String> userAccountList = new ArrayList<String>();
        for (Account ua : userAccounts) {
            userAccountList.add(ua.getAccountNo());
        }
        List<UserAccountDO> userAccountDos = null;
        List<Integer> ids = userAccountDao.findIdsByAccountNos(userAccountList);
        if (ids == null) {
            userAccountDos = new ArrayList<UserAccountDO>();
        } else {
            userAccountDos = userAccountDao.findByIds(ids);
        }
        Map<String, UserAccountDO> userAccountMap = new HashMap<String, UserAccountDO>();
        for (UserAccountDO uad : userAccountDos) {
            userAccountMap.put(uad.getUaAccountNo(), uad);
        }
        boolean isModified = false;
        for (DealRecordDO record : dealRecords) {
            String payAccount = record.getDrPayAccount();
            String receiveAccount = record.getDrReceiveAccount();
            BigDecimal moneyAmount = record.getDrMoneyAmount();
            //根据交易类型判断对用户账户资金的操作，投资进行的是冻结，其余都是进行资金划转
            DealType dealType = DealType.valueOf(record.getDrType());
            switch (dealType) {
                case RECHARGE: {
                    //充值账户 更新的钱为正数
                    UserAccountDO payUser = userAccountMap.get(payAccount);
                    BigDecimal payTotal = payUser.getUaTotalMoney();
                    BigDecimal payUseable = payUser.getUaUseableMoney();
                    payUser.setUaTotalMoney(payTotal.add(moneyAmount));
                    payUser.setUaUseableMoney(payUseable.add(moneyAmount));

                    //收款账户为平台账户
                    UserAccountDO receiveUser = userAccountMap.get(receiveAccount);
                    BigDecimal receiveTotal = receiveUser.getUaTotalMoney();
                    BigDecimal receiveUserable = receiveUser.getUaUseableMoney();
                    receiveUser.setUaUseableMoney(receiveUserable.add(moneyAmount));
                    receiveUser.setUaTotalMoney(receiveTotal.add(moneyAmount));
                    isModified = true;
                    break;
                }
                case TOCASH: {
                    //提现时payAccount为用户账户 从冻结金额中减去提现金额
                    UserAccountDO payUser = userAccountMap.get(payAccount);
                    BigDecimal payTotal = payUser.getUaTotalMoney();
                    BigDecimal payFrozen = payUser.getUaFrozen();
                    payUser.setUaTotalMoney(payTotal.subtract(moneyAmount));
                    payUser.setUaFrozen(payFrozen.subtract(moneyAmount));

                    //收款账户receiveAccount为平台账户，均是直接减钱
                    UserAccountDO receiveUser = userAccountMap.get(receiveAccount);
                    BigDecimal receiveTotal = receiveUser.getUaTotalMoney();
                    BigDecimal receiveFrozen = receiveUser.getUaFrozen();
                    payUser.setUaFrozen(receiveFrozen.subtract(moneyAmount));
                    receiveUser.setUaTotalMoney(receiveTotal.subtract(moneyAmount));
                    break;
                }
                default:
                    break;
            }
        }
        if (isModified) {
            userAccountDao.batchInsert(userAccountDos);
        }
        return new BaseResult();
    }

    @Override
    public ListResult<UserAccountDO> queryByAccountNo(List<String> accountNo) {
        ListResult<UserAccountDO> result = new ListResult<UserAccountDO>();
        List<Integer> ids = null;
        if (!CollectionUtils.isEmpty(accountNo)) {
            ids = userAccountDao.findIdsByAccountNos(accountNo);
        }
        if (CollectionUtils.isEmpty(ids)) {
            result.setData(new ArrayList<UserAccountDO>());
            return result;
        }
        List<UserAccountDO> userAccountDos = userAccountDao.findByIds(ids);//userAccountDao.findByAccountNos(accountNo);
        result.setData(userAccountDos);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public PlainResult<Integer> batchCreateUserAccount(List<UserAccountDO> userAccounts) {
        PlainResult<Integer> result = new PlainResult<Integer>();
        int count = userAccountDao.batchInsert(userAccounts);
        if (count <= 0) {
            result.setCode(CommonResultCode.ERROR_DB.getCode());
            result.setSuccess(false);
            result.setMessage("[UserAccount] 批量更新用户资金状态错误");
        } else {
            result.setData(count);
        }
        return result;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    public BaseResult createUserAccount(String accountNo) {
        BaseResult result = new BaseResult();
        UserAccountDO userAccount = new UserAccountDO();
        userAccount.setUaAccountNo(accountNo);
        userAccount.setUaFrozen(BigDecimal.ZERO);
        userAccount.setUaOperateDate(new Date());
        userAccount.setUaTotalMoney(BigDecimal.ZERO);
        userAccount.setUaUseableMoney(BigDecimal.ZERO);
        int flag = userAccountDao.insert(userAccount);
        if (flag <= 0) {
            logger.warn("[EasyPayUserAccountServiceImpl][createUserAccount] 创建资金账户记录失败");
            result.setMessage("初始化账户资金失败");
            result.setSuccess(false);
        }
        return result;
    }
}
