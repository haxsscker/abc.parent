/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.cash.thirdparty.easypay;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.CashRecordDO;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.UserAccountDO;
import com.autoserve.abc.dao.intf.AccountInfoDao;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.BatchPayData;
import com.autoserve.abc.service.biz.entity.BatchPayQuery;
import com.autoserve.abc.service.biz.entity.Deal;
import com.autoserve.abc.service.biz.entity.DealDetail;
import com.autoserve.abc.service.biz.entity.EasyPayData;
import com.autoserve.abc.service.biz.entity.ItemOfBatchPay;
import com.autoserve.abc.service.biz.enums.CashOperateState;
import com.autoserve.abc.service.biz.enums.CashOperateType;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.EasyPayConfig;
import com.autoserve.abc.service.biz.enums.RequestMethodType;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.ToCashQueryList;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.CashRecordService;
import com.autoserve.abc.service.biz.intf.cash.PayMentService;
import com.autoserve.abc.service.biz.intf.cash.UserAccountService;
import com.autoserve.abc.service.biz.intf.common.AreaService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.http.HttpCallService;
import com.autoserve.abc.service.http.HttpCallServiceImpl;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.service.util.EasyPayUtils;

/**
 * 易生支付具体业务实现
 * 
 * @author J.YL 2014年11月18日 下午1:22:32
 */
//@Service
public class EasyPayServiceImpl implements PayMentService {
    private static final Logger logger = LoggerFactory.getLogger(EasyPayServiceImpl.class);
    @Resource
    private AccountInfoService  account;
    @Resource
    private AccountInfoDao      accountInfoDao;
    @Resource
    private UserAccountService  userAccount;
    @Resource
    private CashRecordService   cashRecordService;
    @Resource
    private AreaService         areaService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult transferMoney(String seqNo, List<DealRecordDO> dealRecords) {
        List<UserAccountDO> userAccountDos = preHandle(seqNo, dealRecords);
        Map<String, UserAccountDO> userAccountMap = new HashMap<String, UserAccountDO>();
        for (UserAccountDO uad : userAccountDos) {
            userAccountMap.put(uad.getUaAccountNo(), uad);
        }
        for (DealRecordDO record : dealRecords) {
            String payAccount = record.getDrPayAccount();
            String receiveAccount = record.getDrReceiveAccount();
            BigDecimal moneyAmount = record.getDrMoneyAmount();
            //付款账户
            UserAccountDO payUser = userAccountMap.get(payAccount);
            if (payUser == null) {
                logger.error("[EasyPayServiceImpl] transferMoney 付款账户的资金记录不存在，账户号为：{}", payAccount);
                throw new BusinessException(CommonResultCode.ERROR_DATA_NOT_EXISTS.getCode(), "账户未开通资金账户！");
            }
            // 如果交易详情是满标资金划转，则从冻结金额中扣除 
            DealDetailType dealDetailType = DealDetailType.valueOf(record.getDrDetailType());
            if (dealDetailType.equals(DealDetailType.APPROPRIATE_MONEY)
                    || dealDetailType.equals(DealDetailType.DEBT_TRANSFER_MONEY)
                    || dealDetailType.equals(DealDetailType.PURCHASE_MONEY)) {
                BigDecimal payTotal = payUser.getUaTotalMoney();
                BigDecimal payFrozen = payUser.getUaFrozen();
                payUser.setUaTotalMoney(payTotal.subtract(moneyAmount));
                payUser.setUaFrozen(payFrozen.subtract(moneyAmount));
            } else {
                BigDecimal payTotal = payUser.getUaTotalMoney();
                BigDecimal payUseable = payUser.getUaUseableMoney();
                payUser.setUaTotalMoney(payTotal.subtract(moneyAmount));
                payUser.setUaUseableMoney(payUseable.subtract(moneyAmount));
            }
            //收款账户
            UserAccountDO receiveUser = userAccountMap.get(receiveAccount);
            if (receiveUser == null) {
                logger.error("[EasyPayServiceImpl] transferMoney 收款账户的资金记录不存在，账户号为：{}", receiveAccount);
                throw new BusinessException(CommonResultCode.ERROR_DATA_NOT_EXISTS.getCode(), "账户未开通资金账户！");
            }
            BigDecimal receiveTotal = receiveUser.getUaTotalMoney();
            BigDecimal receiveUserable = receiveUser.getUaUseableMoney();
            receiveUser.setUaTotalMoney(receiveTotal.add(moneyAmount));
            receiveUser.setUaUseableMoney(receiveUserable.add(moneyAmount));
        }
        return posHandle(userAccountDos);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult refundMoney(String seqNo, List<DealRecordDO> dealRecords) {
        List<UserAccountDO> userAccountDos = preHandle(seqNo, dealRecords);
        Map<String, UserAccountDO> userAccountMap = new HashMap<String, UserAccountDO>();
        for (UserAccountDO uad : userAccountDos) {
            userAccountMap.put(uad.getUaAccountNo(), uad);
        }
        for (DealRecordDO record : dealRecords) {
            String payAccount = record.getDrPayAccount();
            String receiveAccount = record.getDrReceiveAccount();
            BigDecimal moneyAmount = record.getDrMoneyAmount();
            //退款账户
            UserAccountDO payUser = userAccountMap.get(payAccount);
            if (payUser == null) {
                logger.error("[EasyPayServiceImpl] refundMoney 付款账户的资金记录不存在，账户号为：{}", payAccount);
                throw new BusinessException(CommonResultCode.ERROR_DATA_NOT_EXISTS.getCode(), "账户未开通资金账户！");
            }
            BigDecimal payTotal = payUser.getUaTotalMoney();
            BigDecimal payUseable = payUser.getUaUseableMoney();
            payUser.setUaTotalMoney(payTotal.subtract(moneyAmount));
            payUser.setUaUseableMoney(payUseable.subtract(moneyAmount));

            //收款账户
            UserAccountDO receiveUser = userAccountMap.get(receiveAccount);
            if (receiveUser == null) {
                logger.error("[EasyPayServiceImpl] refundMoney 收款账户的资金记录不存在，账户号为：{}", receiveAccount);
                throw new BusinessException(CommonResultCode.ERROR_DATA_NOT_EXISTS.getCode(), "账户未开通资金账户！");
            }
            BigDecimal receiveTotal = receiveUser.getUaTotalMoney();
            BigDecimal receiveUserable = receiveUser.getUaUseableMoney();
            receiveUser.setUaUseableMoney(receiveUserable.add(moneyAmount));
            receiveUser.setUaTotalMoney(receiveTotal.add(moneyAmount));
        }
        return posHandle(userAccountDos);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult freeze(String seqNo, List<DealRecordDO> dealRecords) {
        List<UserAccountDO> userAccountDos = preHandle(seqNo, dealRecords);
        Map<String, UserAccountDO> userAccountMap = new HashMap<String, UserAccountDO>();
        for (UserAccountDO uad : userAccountDos) {
            userAccountMap.put(uad.getUaAccountNo(), uad);
        }
        for (DealRecordDO record : dealRecords) {
            String payAccount = record.getDrPayAccount();
            BigDecimal moneyAmount = record.getDrMoneyAmount();
            //冻结账户
            UserAccountDO payUser = userAccountMap.get(payAccount);
            if (payUser == null) {
                logger.error("[EasyPayServiceImpl] freeze 冻结账户的资金记录不存在，账户号为：{}", payAccount);
                throw new BusinessException(CommonResultCode.ERROR_DATA_NOT_EXISTS.getCode(), "账户未开通资金账户！");
            }
            BigDecimal frozen = payUser.getUaFrozen();
            BigDecimal useable = payUser.getUaUseableMoney();
            payUser.setUaFrozen(frozen.add(moneyAmount));
            payUser.setUaUseableMoney(useable.subtract(moneyAmount));
        }
        return posHandle(userAccountDos);
    }

    /**
     * 给付款账户解冻
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult unFreeze(String seqNo, List<DealRecordDO> dealRecords) {
        List<UserAccountDO> userAccountDos = preHandle(seqNo, dealRecords);
        Map<String, UserAccountDO> userAccountMap = new HashMap<String, UserAccountDO>();
        for (UserAccountDO uad : userAccountDos) {
            userAccountMap.put(uad.getUaAccountNo(), uad);
        }
        for (DealRecordDO record : dealRecords) {
            String payAccount = record.getDrPayAccount();
            BigDecimal moneyAmount = record.getDrMoneyAmount();
            UserAccountDO payUser = userAccountMap.get(payAccount);
            BigDecimal frozen = payUser.getUaFrozen();
            BigDecimal useable = payUser.getUaUseableMoney();
            payUser.setUaFrozen(frozen.subtract(moneyAmount));
            payUser.setUaUseableMoney(useable.add(moneyAmount));
        }
        return posHandle(userAccountDos);
    }

    /**
     * 调用第三方接口执行，非本地操作
     */
    @Override
    public BaseResult reCharge(String seqNo, List<DealRecordDO> dealRecords) {
        //  充值为跳转到第三方接口执行
        return BaseResult.SUCCESS;
    }

    /**
     * 调用第三方接口，非本地操作
     */
    @Override
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult toCash(String seqNo, List<DealRecordDO> dealRecords) {
        BaseResult freezeResult = freeze(seqNo, dealRecords);
        if (!freezeResult.isSuccess()) { // 冻结失败 
            logger.error("[EasyPayServiceImpl][toCash] 提现失败 ,交易流水号：{} 资金余额不足，无法提现！", seqNo);
            throw new BusinessException(CommonResultCode.BIZ_ERROR.getCode(), "资金余额不足，无法提现！");
        }
        CashRecordDO cashRecord = cashRecordService.queryCashRecordBySeqNo(seqNo).getData();
        if (cashRecord == null) {
            logger.error("[EasyPayServiceImpl][toCash] 提现失败 ,交易流水号：{} 未找到资金操作记录", seqNo);
            throw new BusinessException(CommonResultCode.BIZ_ERROR.getCode(), "未找到资金操作记录");
        }
        // 发起web接口调用        cashRecord.getCrRequestUrl()
        String url = cashRecord.getCrRequestUrl();
        Map<String, String> para = JSON.parseObject(cashRecord.getCrRequestParameter(), HashMap.class);
        HttpCallService callService = new HttpCallServiceImpl();
        String easyReturn = callService.httpPost(url, para).getData();
        Document doc = null;

        try {
            doc = DocumentHelper.parseText(easyReturn);
        } catch (DocumentException e) {
            logger.error("[EasyPayService][toCash] 提现操作易生支付接口返回数据错误");
            throw new BusinessException(CommonResultCode.FAIL_HTTP_CALL.getCode(), "支付接口返回数据错误");
        }
        Element root = doc.getRootElement();
        Element status = root.element("status");
        if (status.getTextTrim().equals(EasyPayConfig.BatchToCash.SUCCESS.state)) {//成功则将提现请求加入job中定时查询状态
            BatchPayQuery bpq = new BatchPayQuery();
            bpq.setTransCode(EasyPayConfig.QueryTransCodeType.PAYQUERY.value);
            bpq.setTranDate(DateUtil.formatDate(new Date(), DateUtil.DEFAULT_DAY_STYLE_NOSPACE));
            bpq.setTradeBatchNo(seqNo);
            //加入到job中 以下注释的代码为测试时使用
            //            String result = null;
            //            while (result == null) {
            //                result = BatchPayUtils.queryByBatchNO(bpq);
            //                // result = "GAk+YhdzEyr486VKjS3vjgYqw9GH4T/agcAgTCgWfKH9HbxQvwD4pw+duEwN2lYdLV0f2MU7esEARRukGnNmOjwzfOaPJEs3P+eeykBvzfh3LihoOi2i95+j2Vr7Lrcbc9nBfWPMiZNQ5shOe+mZ6Hs09sbvCRB4QLwmjb3/Zpk+CSEtnOrrNG1pY2DIB9QUyHXQ/Bs+Xb0iZwyxP2TU3bYgAk3dY58Dpaax43Liekm0jRzVU+fga528DodzP7RZUlZKrZXiaVLCVQ8PfmYwaq2LQ03vmWj5u5wRm2Q5YMFlN2qV3sbBUebZo6DlZ90zlBLhkAzSWanO1sXdIhcVsJK6C4UiD1dcfc7imlbUbx42lRifZdsK8i67NQ7zwcnJAEz/fXLqde5G5n+hxAtA3q1jwUa8vCsWwb3tB1grnL5OKu1hRfiZds+DbtoVC1iwX9Sxp+qlOSTe/Sh9f6ciJlEbeXXwxbm/crJcCTLKzcjOfQlOd8xg6yQ/NnqLaWnEX0boOHD/HNIvhcXcJEcMUgEtwPZ10gIPbFBEB6Nr1CNkAMMBw+CVOjoCWOHiZcZwP6NxCxO4ItFpnjZ8VHqSVbloLyD5Y1d6XLRBBlq71B8QQpezNt6vqFhrlBCKnbIh4VHr7DGI0nnG7TcGRHXHLkI+3UbEMKrtp5KKxTs+aahlhmFheU3j+90BLG6Fb6My+XGWCUuUjUXJqsiqO9KQeENsaVkf3DNQNpF0U8D3zZ1+1pHFHg9bBZD6AwpqVC7B96e63QVJP6ps/YayW/twXtd8XE+fXnowm5f1gPnNOnfvba93KqklIdF7/lZTgD9+Mp7GGGdxhE/aXVEQSrYyN7gBTkUjcGO8qec7ofgdswm+vjWqXhRz4SbHSAgggluu4cAixdTtEZWChPjIFgkaKcm74BQqamBqzUlBeuqP846XWZpBrv1ZFo22jtw+m8ivdzlzQgwN3wwccMkXD0RmER+FUN3hBNquqV1F5xO+c+aC2d/4s6QfSctj+I6F7iRU";
            //                if (!result.startsWith("<Resp>")) {
            //                    try {
            //                        Base64 decode = new Base64();
            //                        byte[] rb = decode.decode(result);
            //                        result = BatchPaySignature.decrypt(rb);
            //
            //                    } catch (Exception e1) {
            //                        logger.error("提现结果查询返回数据解密出错 ", e1);
            //                    }
            //                }
            //            }
            ToCashQueryList.add(bpq);
            //            Document doc1 = null;
            //            try {
            //                doc1 = DocumentHelper.parseText(result);
            //            } catch (DocumentException e) {
            //                logger.error("[ToCashJob][run] 解析查询提现结果数据：", e);
            //            }
            //            Element root1 = doc1.getRootElement();
            //            Element batchCurrnum = root1.element("batchCurrnum");
            //            Element batchContent = root1.element("batchContent");
            //            Element detailInfo = batchContent.element("detailInfo");
            //            String batchCurrString = batchCurrnum.getTextTrim();
            //            String detailInfoString = detailInfo.getTextTrim();
            //            List<String> res = Splitter.on(',').splitToList(detailInfoString);
            //            System.out.println("adf");
        } else {
            //提交提现请求出错   解冻提现金额
            unFreeze(seqNo, dealRecords);
            logger.warn(root.element("reason").getTextTrim());
            throw new BusinessException(CommonResultCode.BIZ_ERROR.getCode(), "第三方支付提现请求失败");
        }
        return new BaseResult();
    }

    /**
     * 根据交易前的预处理
     * 
     * @param seqNo
     * @param dealRecords
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    private List<UserAccountDO> preHandle(String seqNo, List<DealRecordDO> dealRecords) {
        List<String> userAccountList = new ArrayList<String>();
        Set<String> accountSet = new HashSet<String>();
        BigDecimal totalAmount = new BigDecimal(0.0);
        for (DealRecordDO record : dealRecords) {
            accountSet.add(record.getDrPayAccount());
            accountSet.add(record.getDrReceiveAccount());
            totalAmount = totalAmount.add(record.getDrMoneyAmount());
        }
        ListResult<Account> queryResult = account.queryByAccountNos(new ArrayList<String>(accountSet));
        List<Account> userAccounts = queryResult.getData();
        for (Account ua : userAccounts) {
            userAccountList.add(ua.getAccountNo());
        }
        List<UserAccountDO> userAccountDos = userAccount.queryByAccountNo(userAccountList).getData();
        return userAccountDos;
    }

    /**
     * 资金操作结果的统一验证
     * 
     * @param userAccountDos
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    private BaseResult posHandle(List<UserAccountDO> userAccountDos) {
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
            userAccount.batchCreateUserAccount(userAccountDos);
        }
        BaseResult result = new BaseResult();
        result.setSuccess(payState);
        result.setMessage(message);
        return result;
    }

    /**
     * 根据Deal 如果 Deal的 DealType是充值则构造EasyPayData 如果DealType是提现。则构造BatchPayData
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public CashRecordDO constructParam(Deal deals) {
        DealType busType = deals.getBusinessType();
        List<DealDetail> detail = deals.getDealDetail();
        if (detail == null || detail.isEmpty()) {
            throw new BusinessException(CommonResultCode.BIZ_ERROR.getCode(), "交易详情为空");
        }
        BigDecimal total = BigDecimal.ZERO;
        for (DealDetail det : detail) {
            total = total.add(det.getMoneyAmount());
        }
        CashRecordDO result = new CashRecordDO();
        switch (busType) {
            case INVESTER: {
                result.setCrMoneyAmount(total);
                result.setCrOperateDate(new Date());
                result.setCrOperateType(CashOperateType.FREEZE.getType());
                result.setCrSeqNo(deals.getInnerSeqNo().getUniqueNo());
                result.setCrRequestMethod("GET");
                result.setCrRequestParameter(JSON.toJSONString(deals));
                result.setCrRequestUrl("localhost");
                result.setCrResponseState(CashOperateState.NOCALLBACK.getState());
                break;
            }
            case PAYBACK: {
                result.setCrMoneyAmount(total);
                result.setCrOperateDate(new Date());
                result.setCrOperateType(CashOperateType.TRANSFER.getType());
                result.setCrSeqNo(deals.getInnerSeqNo().getUniqueNo());
                result.setCrRequestMethod("GET");
                result.setCrRequestParameter(JSON.toJSONString(deals));
                result.setCrRequestUrl("localhost");
                result.setCrResponseState(CashOperateState.NOCALLBACK.getState());
                break;
            }
            case PURCHASE: {
                result.setCrMoneyAmount(total);
                result.setCrOperateDate(new Date());
                result.setCrOperateType(CashOperateType.FREEZE.getType());
                result.setCrSeqNo(deals.getInnerSeqNo().getUniqueNo());
                result.setCrRequestMethod("GET");
                result.setCrRequestParameter(JSON.toJSONString(deals));
                result.setCrRequestUrl("localhost");
                result.setCrResponseState(CashOperateState.NOCALLBACK.getState());
                break;
            }
            case RECHARGE: {//构造第三方支付充值接口调用参数
                result = this.buildReChargeRecord(deals);
                break;
            }
            case REFUND: {
                result.setCrMoneyAmount(total);
                result.setCrOperateDate(new Date());
                result.setCrOperateType(CashOperateType.REFUND.getType());
                result.setCrSeqNo(deals.getInnerSeqNo().getUniqueNo());
                result.setCrRequestMethod("GET");
                result.setCrRequestParameter(JSON.toJSONString(deals));
                result.setCrRequestUrl("localhost");
                result.setCrResponseState(CashOperateState.NOCALLBACK.getState());
                break;
            }
            case TOCASH: {//构造第三方支付提现接口调用参数
                result = this.buildToCashRecord(deals);
                break;
            }
            case TRANSFER: {

                result.setCrMoneyAmount(total);
                result.setCrOperateDate(new Date());
                result.setCrOperateType(CashOperateType.TRANSFER.getType());
                result.setCrSeqNo(deals.getInnerSeqNo().getUniqueNo());
                result.setCrRequestMethod("GET");
                result.setCrRequestParameter(JSON.toJSONString(deals));
                result.setCrRequestUrl("localhost");
                result.setCrResponseState(CashOperateState.NOCALLBACK.getState());
                break;
            }
            case WITHDRAWAL_INVESTER: {
                result.setCrMoneyAmount(total);
                result.setCrOperateDate(new Date());
                result.setCrOperateType(CashOperateType.UNFREEZE.getType());
                result.setCrSeqNo(deals.getInnerSeqNo().getUniqueNo());
                result.setCrRequestMethod("GET");
                result.setCrRequestParameter(JSON.toJSONString(deals));
                result.setCrRequestUrl("localhost");
                result.setCrResponseState(CashOperateState.NOCALLBACK.getState());
                break;
            }
            case ABORT_BID: {
                result.setCrMoneyAmount(total);
                result.setCrOperateDate(new Date());
                result.setCrOperateType(CashOperateType.UNFREEZE.getType());
                result.setCrSeqNo(deals.getInnerSeqNo().getUniqueNo());
                result.setCrRequestMethod("GET");
                result.setCrRequestParameter(JSON.toJSONString(deals));
                result.setCrRequestUrl("localhost");
                result.setCrResponseState(CashOperateState.NOCALLBACK.getState());
                break;
            }
            default:
                break;
        }
        return result;
    }

    /**
     * 构建充值请求记录 不发起请求
     * 
     * @param deals
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    private CashRecordDO buildToCashRecord(Deal deals) {
        CashRecordDO recordDO = new CashRecordDO();
        recordDO.setCrSeqNo(deals.getInnerSeqNo().getUniqueNo());
        recordDO.setCrOperateDate(new Date());
        List<DealDetail> dealDetails = deals.getDealDetail();
        if (dealDetails == null || dealDetails.isEmpty())
            throw new BusinessException("交易详情为空");
        BigDecimal totalMoney = BigDecimal.ZERO;
        int batchCount = 0;
        BatchPayData data = new BatchPayData();
        List<ItemOfBatchPay> items = new ArrayList<ItemOfBatchPay>();
        int num = 1;
        Map<String, Account> accountMap = new HashMap<String, Account>();
        Set<String> areaSet = new HashSet<String>();
        Set<String> accountNoSet = new HashSet<String>();
        //批量取出账户数据
        for (DealDetail detail : dealDetails) {
            accountNoSet.add(detail.getPayAccountId());
            accountNoSet.add(detail.getReceiveAccountId());
        }
        List<Account> accounts = account.queryByAccountNos(new ArrayList<String>(accountNoSet)).getData();
        //构建Account map 批量取出area数据
        for (Account ac : accounts) {
            accountMap.put(ac.getAccountNo(), ac);
            areaSet.add(ac.getAccountBankArea());
        }
        Map<String, String> areaMap = areaService.queryMapByAreaCodes(new ArrayList<String>(areaSet));
        for (DealDetail detail : dealDetails) {
            batchCount++;
            ItemOfBatchPay item = new ItemOfBatchPay();
            Account toDO = accountMap.get(detail.getPayAccountId());
            //   AccountInfoDO toDO = accountInfoDao.findByAccountNo(detail.getReceiveAccountId());
            item.setCertificateNum(toDO.getAccountUserCard());
            item.setTradeAccountName(toDO.getAccountBankName());
            //对公对私标识，个人用户为对私账户 ，企业和合作伙伴为对公账户
            if (0 == toDO.getAccountUserType().compareTo(UserType.PERSONAL)) {
                item.setTradeAccountType(EasyPayConfig.TradeAccountType.PERSONAL.value);
            } else {
                item.setTradeAccountType(EasyPayConfig.TradeAccountType.PUBLIC.value);
            }
            item.setTradeAmount(detail.getMoneyAmount());
            totalMoney = totalMoney.add(detail.getMoneyAmount());
            //从area表中查处开户行的地区  现在存的是地区的NO
            String areaName = areaMap.get(toDO.getAccountBankArea());
            String[] area = areaName.split("-");
            String city = area[1];
            String province = area[0];
            item.setTradeCity(city);
            item.setTradeProvince(province);
            item.setTradeBranchBank(city.substring(0, city.length() - 1) + "分行");
            item.setTradeCardname(toDO.getAccountUserName());
            item.setTradeCardnum(toDO.getAccountUserAccount());
            item.setTradeMobile(toDO.getAccountUserPhone());
            item.setTradeNum(num);
            num++;
            item.setTradeSubbranchBank(toDO.getAccountBankBranchName());
            items.add(item);
        }
        data.setBatchCount(batchCount);
        data.setItems(items);
        data.setTradeBatchNo(deals.getInnerSeqNo().getUniqueNo());
        data.setBatchAmount(totalMoney);
        logger.debug(JSON.toJSONString(data));
        String vmStr = BatchPayUtils.buildTextFromVM(data);
        Map<String, String> map = BatchPayUtils.builParameter(vmStr);
        recordDO.setCrMoneyAmount(totalMoney);
        recordDO.setCrOperateType(DealType.TOCASH.type);
        recordDO.setCrRequestMethod(RequestMethodType.POST.value);
        recordDO.setCrRequestParameter(JSON.toJSONString(map));
        recordDO.setCrRequestUrl(EasyPayConfig.PAY_URL_PREFIX + EasyPayConfig.TransCodeType.AGENTPAY.surfix);
        return recordDO;
    }

    /**
     * 构建充值请求记录 不发起请求 有web层调用发起
     * 
     * @param deals
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    private CashRecordDO buildReChargeRecord(Deal deals) {
        CashRecordDO recordDO = new CashRecordDO();
        recordDO.setCrResponseState(CashOperateState.NOCALLBACK.getState());
        recordDO.setCrSeqNo(deals.getInnerSeqNo().getUniqueNo());
        recordDO.setCrOperateDate(new Date());
        //充值  Deal 的dealDetail属性只有一条
        List<DealDetail> dealDetails = deals.getDealDetail();
        if (dealDetails == null || dealDetails.isEmpty())
            throw new BusinessException("交易详情为空");
        DealDetail detail = dealDetails.get(0);
        recordDO.setCrMoneyAmount(detail.getMoneyAmount());
        recordDO.setCrOperateType(DealType.RECHARGE.type);
        recordDO.setCrRequestMethod(RequestMethodType.GET.value);
        AccountInfoDO fromDO = accountInfoDao.findByAccountNo(detail.getPayAccountId());
        AccountInfoDO toDO = accountInfoDao.findByAccountNo(detail.getReceiveAccountId());
        if (fromDO == null || toDO == null)
            throw new BusinessException("转账账户或收款账户不存在");
        EasyPayData payData = new EasyPayData();
        payData.setTotal_fee(detail.getMoneyAmount());
        try {
            payData.setNotify_url(new URL(EasyPayConfig.NOTIFY_URL));
            payData.setReturn_url(new URL(EasyPayConfig.RETURN_URL));
        } catch (MalformedURLException e) {
            throw new BusinessException("notify_url或者return_url 格式错误");
        }
        payData.setOut_trade_no(deals.getInnerSeqNo().getUniqueNo()); //设置交易流水号
        payData.setSeller_email(toDO.getAccountUserEmail()); //设置商户的邮箱
        String url = EasyPayUtils.buildEasyPayUrl(payData); //构造请求的URL 
        String[] urlARR = url.split("\\?");
        if (urlARR == null || urlARR.length != 2)
            throw new BusinessException("构造的URL 不合法");
        recordDO.setCrRequestParameter(urlARR[1]);
        recordDO.setCrRequestUrl(urlARR[0]); //在此不做访问的操作  前端拿到url做相应跳转
        return recordDO;
    }

    @Override
    public PlainResult<Map<String, String>> tranfulAll(String seqNo, List<DealRecordDO> dealRecords) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PlainResult<Map<String, String>> loanFree(String seqNo, List<DealRecordDO> dealRecords) {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public Map<String, String> investToMargin(String money, String accountNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> zQTransfer(Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}

}
