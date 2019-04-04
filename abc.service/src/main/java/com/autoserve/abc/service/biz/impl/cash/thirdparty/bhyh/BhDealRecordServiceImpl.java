package com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.CashRecordDO;
import com.autoserve.abc.dao.dataobject.ChargeRecordDO;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.InvestDO;
import com.autoserve.abc.dao.dataobject.InvestOrderDO;
import com.autoserve.abc.dao.dataobject.stage.statistics.RecentDeal;
import com.autoserve.abc.dao.intf.DealRecordDao;
import com.autoserve.abc.dao.intf.InvestDao;
import com.autoserve.abc.dao.intf.InvestOrderDao;
import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.callback.center.CashCallBackCenter;
import com.autoserve.abc.service.biz.convert.CashRecordConverter;
import com.autoserve.abc.service.biz.convert.DealConverter;
import com.autoserve.abc.service.biz.convert.DealRecordConverter;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.CashRecord;
import com.autoserve.abc.service.biz.entity.Deal;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.entity.DealRecord;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.entity.EasyPayNotifyData;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.EasyPayConfig;
import com.autoserve.abc.service.biz.enums.EasyPayConfig.EasyPayVerifyResult;
import com.autoserve.abc.service.biz.enums.EasyPayTradeState;
import com.autoserve.abc.service.biz.enums.FeeType;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.CashRecordService;
import com.autoserve.abc.service.biz.intf.cash.ChargeRecordService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.cash.PayMentService;
import com.autoserve.abc.service.biz.intf.cash.UserAccountService;
import com.autoserve.abc.service.biz.intf.invest.InvestOrderService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.util.EasyPayUtils;

/**
 * 交易Service接口定义
 * 
 * @author J.YL 2014年11月21日 下午4:19:59
 */
@Service
public class BhDealRecordServiceImpl implements DealRecordService {

	private final Logger logger = LoggerFactory
			.getLogger(BhDealRecordServiceImpl.class);
	@Resource
	private DealRecordDao dealRecordDao;
	@Resource
	private CashRecordService cash;
	@Resource
	private AccountInfoService account;
	@Resource
	private ChargeRecordService chargeRecord;
	@Resource
	private UserAccountService userAccount;
	@Resource
	private PayMentService payMent;
	@Resource
	private InvestOrderService investOrderService;
	@Autowired
	private InvestOrderDao investOrderDao;
	@Resource
	private CashRecordService cashRecordService;
	@Resource
    private DoubleDryService    doubleDryService;
	@Resource
    private InvestDao               investDao;

	@Override
	public PlainResult<DealReturn> queryBusinessRecord(Deal deal) {
		return null;
	}

	/**
	 * 创建交易记录和资金操作记录
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public PlainResult<DealReturn> createBusinessRecord(Deal deal,
			Callback<DealNotify> callBack) {

		/**
		 * 返回数据
		 */
		DealReturn dealReturn = new DealReturn();
		/**
		 * dealReturn 中的dealRecord
		 */
		List<DealRecord> dealRecord = new ArrayList<DealRecord>();

		List<CashRecord> cashRecord = new ArrayList<CashRecord>();

		CashRecordDO cashRecordDo = payMent.constructParam(deal);

		DealType dealType = deal.getBusinessType();
		// 将每次传入的callback存入类中
		CashCallBackCenter.registCallBack(dealType, callBack);
		// 假设 支付接口支持支持批量支付
		PlainResult<Integer> cashRecordResult = cash
				.createCashRecord(cashRecordDo);
		if (!cashRecordResult.isSuccess()) {
			throw new BusinessException(CommonResultCode.ERROR_DB.getCode(),
					"[DealRecordServiceImpl][createBusinessRecord] 创建资金交易记录出错");
		}
		cashRecord.add(CashRecordConverter.toCashRecord(cashRecordDo));
		List<DealRecordDO> dealRecordDoList = DealConverter
				.toDealRecordDO(deal);
		// 资金记录创建后 交易记录外键关联至资金记录
		for (DealRecordDO dealRecordDo : dealRecordDoList) {
			dealRecordDo.setDrCashId(cashRecordResult.getData());
			if (deal.getBusinessType().getType() == 3) {//3:还款
				//设置钱多多付款人帐号
				String payAccount = dealRecordDo.getDrPayAccount();//此处存储形式为userId|userType
				String payUserId = "";
				String payUserType = "";
				if (payAccount.split("\\|").length > 1) {
					payUserId = payAccount.split("\\|")[0];
					payUserType = payAccount.split("\\|")[1];
					AccountInfoDO inAccountInfo = account.queryByAccountMark(
							Integer.parseInt(payUserId),
							Integer.parseInt(payUserType));
					payAccount = inAccountInfo.getAccountNo();
					dealRecordDo.setDrPayAccount(payAccount);
				}
				//设置钱多多收款人帐号
				String receiveAccount = dealRecordDo.getDrReceiveAccount();
				if (receiveAccount.split("\\|").length > 1) {
					String receiveUserId = receiveAccount.split("\\|")[0];
					String receiveUserType = receiveAccount.split("\\|")[1];
					AccountInfoDO inAccountInfo = account.queryByAccountMark(
							Integer.parseInt(receiveUserId),
							Integer.parseInt(receiveUserType));
					receiveAccount = inAccountInfo.getAccountNo();
					dealRecordDo.setDrReceiveAccount(receiveAccount);
				}
			}
			dealRecord.add(DealRecordConverter.toDealRecord(dealRecordDo));
			// 判断交易金额的正负，交易金额为负数则抛出异常
			if (dealRecordDo.getDrMoneyAmount().compareTo(BigDecimal.ZERO) < 0) {
				logger.warn(
						"[DealRecordServiceImpl][createBusinessRecord]资金交易额为负数,innerSeqNo:{}",
						deal.getInnerSeqNo().toString());
				throw new BusinessException(
						CommonResultCode.BIZ_ERROR.getCode(),
						"[DealRecordServiceImpl][createBusinessRecord]资金交易额为负数" + dealRecordDo.getDrMoneyAmount());
			}
		}

		// 保存交易记录
		int flag = dealRecordDao.batchInsert(dealRecordDoList);
		if (flag <= 0) {// 插入不成功处理
			throw new BusinessException(CommonResultCode.ERROR_DB.getCode(),
					"[DealRecordServiceImpl][createBusinessRecord] 批量插入交易记录出错");
		}
		dealReturn.setDealRecords(dealRecord);
		dealReturn.setCashRecords(cashRecord);
		dealReturn.setDrInnerSeqNo(deal.getInnerSeqNo().getUniqueNo());
		dealReturn.setParams(cashRecordDo.getCrRequestParameter());
		PlainResult<DealReturn> result = new PlainResult<DealReturn>();
		result.setData(dealReturn);
		return result;
	}

	/**
	 * 更新交易记录状态&进行回调操作&更新一堆记录表&更新用户资金表
	 * 
	 * @return BaseResult 返回结果为true时表明业务逻辑处理成功，否则业务逻辑处理失败
	 * @notice 这里的更新策略是基于第三方支付支持批量资金操作的
	 * @notice 这里的返回状态决定是否向EasyPay发送确认消息
	 * @see 其中对资金操作记录表的更新是在web层收到Notify消息时进行的。
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	@Override
	public BaseResult modifyDealRecordState(String seqNo, String tradeStatus,
			BigDecimal totalFee) {
		BaseResult result = new BaseResult();
		DealRecordDO dealRecordDo = new DealRecordDO();
		// 接口支持批量 故cashSeqNo和innerSeqNo相同 否则此处为outSeqNo
		dealRecordDo.setDrInnerSeqNo(seqNo);
		EasyPayTradeState easyPayTradeState = EasyPayTradeState
				.valueOf(tradeStatus);
		int newState = DealState.NOCALLBACK.getState();
		switch (easyPayTradeState) {
			case WAIT_BUYER_PAY: {// 等待付款 do nothing
				return result;
			}
			case TRADE_FINISHED: {// 交易完成
				dealRecordDo.setDrState(DealState.SUCCESS.getState());
				newState = DealState.SUCCESS.getState();
				break;
			}
			case TRADE_FAILURE: {// 交易失败
				result.setSuccess(false);
				dealRecordDo.setDrState(DealState.FAILURE.getState());
				newState = DealState.FAILURE.getState();
				break;
			}
		}
		// 先查看交易记录的状态，为等待响应则继续 否则只做docallback 不update表数据
		boolean goon = false;
		// 当前数据库中交易的状态
		int dealStatus = DealState.SUCCESS.getState();
		BaseResult notifyState = null;
		BigDecimal dealRecordTotalFee = BigDecimal.ZERO;
		DealNotify notify = new DealNotify();
		// 查出内部交易号对应的所有交易记录
		List<DealRecordDO> dealRecords = dealRecordDao
				.findDealRecordsByInnerSeqNo(seqNo);
		if (dealRecords.isEmpty()) {
			throw new BusinessException(CommonResultCode.BIZ_ERROR.getCode(),
					String.format("交易流水号:%s ,无交易记录!", seqNo));
		}
		for (DealRecordDO dr : dealRecords) {
			DealState ds = DealState.valueOf(dr.getDrState());
			switch (ds) {
				case FAILURE: {
					dealStatus = DealState.FAILURE.getState();
					break;
				}
				case NOCALLBACK: {
					goon = true;
					break;
				}
				case SUCCESS:
					break;
				default:
					break;
			}
			dealRecordTotalFee = dealRecordTotalFee.add(dr.getDrMoneyAmount());
		}
		/*
		 * if (0 != dealRecordTotalFee.compareTo(totalFee)) { // 本次资金操作终止 并发起退款
		 * logger
		 * .error("[DealRecordService][modifyDealRecordState] 交易总金额：{},付款金额：{}",
		 * totalFee.doubleValue(), totalFee); throw new
		 * BusinessException(CommonResultCode.BIZ_ERROR.getCode(),
		 * String.format("交易流水号:%s ,交易总金额错误!", seqNo)); }
		 */
		// 状态已经更新 只需进行callback
		if (!goon) {
			DealType type = DealType.valueOf(dealRecords.get(0).getDrType());
			Callback<DealNotify> callBack = CashCallBackCenter
					.getCallBackByType(type);
			notify.setInnerSeqNo(seqNo);
			notify.setTotalFee(totalFee);
			notify.setState(DealState.valueOf(dealStatus));
			notifyState = callBack.doCallback(notify);
			if (!notifyState.isSuccess()) {
				// 如果回调不成功，则认为业务处理失败，不发送确认
				result.setSuccess(false);
			}
			return result;
		}
		logger.debug(
				"[DealRecordServiceImpl][modifyDealRecordState] seqNo:{} 查出来的交易状态:{}",
				seqNo, JSON.toJSON(dealRecords));
		// 更新交易记录状态
		int flag = 0;
		for (DealRecordDO dr : dealRecords) {
			dr.setDrState(dealRecordDo.getDrState());
			flag += dealRecordDao.updateDealRecordStateById(dr);
		}

		if (flag <= 0) {// 更新交易
			logger.warn(
					"[DealRecordServiceImpl][modifyDealRecordState] 更新交易记录状态警告：无交易记录可更新。交易流水号：{}",
					seqNo);
			throw new BusinessException(CommonResultCode.ERROR_DB.getCode(),
					"交易状态更新失败");
		}

		// 根据获取交易的交易类型 非交易详细类型
		int dealType = dealRecords.get(0).getDrType();
		DealType type = DealType.valueOf(dealType);
		Callback<DealNotify> callBack = CashCallBackCenter
				.getCallBackByType(type);

		notify.setInnerSeqNo(seqNo);
		notify.setTotalFee(totalFee);
		notify.setState(DealState.valueOf(newState));
		notifyState = callBack.doCallback(notify);
		// 交易成功 更新用户账户资金状态和相关记录表
		if (DealState.valueOf(newState).equals(DealState.SUCCESS)) {
			// update记录表(暂时为收费记录表)
			modifyAccountTables(dealRecords);
			// update UserAccount表中的用户账户资金信息
			// modifyAccountFinancial(dealRecords, DealState.valueOf(newState));
		}
		// 根据notify结果返回
		if (!notifyState.isSuccess()) {
			result.setSuccess(false);
		}
		return result;
	}

	// 更新一堆记录表
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	private BaseResult modifyAccountTables(List<DealRecordDO> dealRecords) {
		BaseResult result = new BaseResult();
		Set<String> accountSet = new HashSet<String>();
		Set<Integer> loanSet = new HashSet<Integer>();

		for (DealRecordDO record : dealRecords) {
			accountSet.add(record.getDrPayAccount());
			accountSet.add(record.getDrReceiveAccount());
			loanSet.add(record.getDrBusinessId());
		}
		Map<String, Integer> userAccountIdMapper = new HashMap<String, Integer>();
		ListResult<Account> queryResult = account
				.queryByAccountNos(new ArrayList<String>(accountSet));
		if (queryResult.isSuccess()) {
			for (Account account : queryResult.getData()) {
				userAccountIdMapper.put(account.getAccountNo(),
						account.getAccountUserId());
			}
		}
		Map<Integer, Integer> loanMapper = new HashMap<Integer, Integer>();
		for (DealRecordDO record : dealRecords) {
			DealDetailType detailType = DealDetailType.valueOf(record
					.getDrDetailType());
			switch (detailType) {
			case INVESTE_MONEY: {
				break;
			}
			case PLA_FEE: {
				// 记录平台手续费
				ChargeRecordDO chargePla = new ChargeRecordDO();
				chargePla.setCrFee(record.getDrMoneyAmount());
				chargePla.setCrFeeType(FeeType.PLA_FEE.getType());
				Integer loanId = record.getDrBusinessId();
				chargePla.setCrLoanId(loanId);
				chargePla.setCrLoanType(loanMapper.get(loanId));
				chargePla.setCrSeqNo(record.getDrInnerSeqNo());
				PlainResult<Integer> createResult = chargeRecord
						.createChargeRecord(chargePla);
				if (!createResult.isSuccess()) {
					logger.error(
							"[DealRecordService][modifyAccountTables]记录平台手续费错误:{}",
							createResult.getMessage());
					throw new BusinessException("数据库插入错误");
				}
				break;
			}
			case PLA_SERVE_FEE: {
				// 记录平台服务费
				ChargeRecordDO chargePlaSer = new ChargeRecordDO();
				chargePlaSer.setCrFee(record.getDrMoneyAmount());
				chargePlaSer.setCrFeeType(FeeType.PLA_SERVE_FEE.getType());
				Integer loanId = record.getDrBusinessId();
				chargePlaSer.setCrLoanId(loanId);
				chargePlaSer.setCrLoanType(loanMapper.get(loanId));
				chargePlaSer.setCrSeqNo(record.getDrInnerSeqNo());
				PlainResult<Integer> createResult = chargeRecord
						.createChargeRecord(chargePlaSer);
				if (!createResult.isSuccess()) {
					logger.error(
							"[DealRecordService][modifyAccountTables]记录平台手续费错误:{}",
							createResult.getMessage());
					throw new BusinessException("数据库插入错误");
				}
				break;
			}
			case DEBT_TRANSFER_FEE: {
				// 转让手续费
				ChargeRecordDO chargeTransfer = new ChargeRecordDO();
				chargeTransfer.setCrFee(record.getDrMoneyAmount());
				chargeTransfer.setCrFeeType(FeeType.TRANSFER_FEE.getType());
				Integer loanId = record.getDrBusinessId();
				chargeTransfer.setCrLoanId(loanId);
				chargeTransfer.setCrLoanType(loanMapper.get(loanId));
				chargeTransfer.setCrSeqNo(record.getDrInnerSeqNo());
				PlainResult<Integer> createResult = chargeRecord
						.createChargeRecord(chargeTransfer);
				if (!createResult.isSuccess()) {
					logger.error(
							"[DealRecordService][modifyAccountTables]记录平台手续费错误:{}",
							createResult.getMessage());
					throw new BusinessException("数据库插入错误");
				}
				break;
			}
			case PURCHASE_FEE: {
				// 收购手续费
				ChargeRecordDO chargePurchase = new ChargeRecordDO();
				chargePurchase.setCrFee(record.getDrMoneyAmount());
				chargePurchase.setCrFeeType(FeeType.PURCHASE_FEE.getType());
				Integer loanId = record.getDrBusinessId();
				chargePurchase.setCrLoanId(loanId);
				chargePurchase.setCrLoanType(loanMapper.get(loanId));
				chargePurchase.setCrSeqNo(record.getDrInnerSeqNo());
				PlainResult<Integer> createResult = chargeRecord
						.createChargeRecord(chargePurchase);
				if (!createResult.isSuccess()) {
					logger.error(
							"[DealRecordService][modifyAccountTables]记录平台手续费错误:{}",
							createResult.getMessage());
					throw new BusinessException("数据库插入错误");
				}
				break;
			}
			case INSURANCE_FEE: {
				// 担保手续费
				ChargeRecordDO chargeInsurance = new ChargeRecordDO();
				chargeInsurance.setCrFee(record.getDrMoneyAmount());
				chargeInsurance.setCrFeeType(FeeType.INSURANCE_FEE.getType());
				Integer loanId = record.getDrBusinessId();
				chargeInsurance.setCrLoanId(loanId);
				chargeInsurance.setCrLoanType(loanMapper.get(loanId));
				chargeInsurance.setCrSeqNo(record.getDrInnerSeqNo());
				PlainResult<Integer> createResult = chargeRecord
						.createChargeRecord(chargeInsurance);
				if (!createResult.isSuccess()) {
					logger.error(
							"[DealRecordService][modifyAccountTables]记录平台手续费错误:{}",
							createResult.getMessage());
					throw new BusinessException("数据库插入错误");
				}
				break;
			}
			case PAYBACK_CAPITAL: {
				break;
			}
			case PAYBACK_INTEREST: {
				break;
			}
			case PAYBACK_OVERDUE_FINE: {
				break;
			}
			case REFUND_MONEY: {
				break;
			}
			case APPROPRIATE_MONEY: {
				break;
			}
			case RECHARGE_MONEY: {
				break;
			}
			case TOCASH_MONEY: {
				break;
			}
			case PURCHASE_MONEY: {
				break;
			}
			case WITHDRAWAL_INVESTER_MONEY: {
				break;
			}
			case DEBT_TRANSFER_MONEY: {
				break;
			}
			case ABORT_BID_MONEY: {
				break;
			}
			default:
				break;
			}
		}
		return result;
	}

	/**
	 * 更新调用第三方接口返回的用户资金信息
	 * 
	 * @param dealRecords
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	private BaseResult modifyAccountFinancial(List<DealRecordDO> dealRecords,
			DealState dealState) {
		Set<String> accountSet = new HashSet<String>();

		for (DealRecordDO record : dealRecords) {
			accountSet.add(record.getDrPayAccount());
			accountSet.add(record.getDrReceiveAccount());
		}
		ListResult<Account> queryResult = account
				.queryByAccountNos(new ArrayList<String>(accountSet));
		return userAccount.updateThridPartReturn(queryResult.getData(),
				dealRecords);
	}

	/**
	 * 向第三方支付接口发起调用请求 &更新用户资金表（非第三方支付操作，本地操作）
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	@Override
	public BaseResult invokePayment(String innerSeqNo) {
		// 调用Payment服务进行服务调用
		List<DealRecordDO> dealRecords = dealRecordDao
				.findDealRecordsByInnerSeqNo(innerSeqNo);
		if (CollectionUtils.isEmpty(dealRecords)) {
			BaseResult ret = new BaseResult();
			ret.setSuccess(false);
			ret.setCode(CommonResultCode.ERROR_DATA_NOT_EXISTS.getCode());
			ret.setMessage("交易记录不存在！");
			return ret;
		}
		boolean needInvokeThridPart = false;
		BigDecimal totalAmount = BigDecimal.ZERO;
		for (DealRecordDO record : dealRecords) {
			totalAmount = totalAmount.add(record.getDrMoneyAmount());
		}
		// 使用payment的接口方法进行用户数据更新
		DealType dealType = DealType.valueOf(dealRecords.get(0).getDrType());
		BaseResult payResult = new BaseResult();
		PlainResult<Map<String, String>> resultMap = new PlainResult<Map<String, String>>();
		// 此处除了调用第三方支付接口的用户数据需要更新
		int maxGoon = 3;
		switch (dealType) {
		case INVESTER:
			//resultMap = payMent.tranfulAll(innerSeqNo, dealRecords);
			//payResult = doublePayMentNotify(resultMap.getData());
			resultMap = doubleDryService.backInvest(innerSeqNo, dealRecords);
			payResult = bhyhPayMentNotify(resultMap.getData());
			break;
		case PAYBACK:
			resultMap = payMent.tranfulAll(innerSeqNo, dealRecords);
			payResult = PayBackNotify(resultMap.getData());
			break;
		case PURCHASE:
			payResult = payMent.freeze(innerSeqNo, dealRecords);
			break;
		case RECHARGE:
			needInvokeThridPart = true;
			payResult = payMent.reCharge(innerSeqNo, dealRecords);
			break;
		case REFUND:
			payResult = payMent.refundMoney(innerSeqNo, dealRecords);
			break;
		case TOCASH:
			needInvokeThridPart = true;
			payResult = payMent.toCash(innerSeqNo, dealRecords);
			break;
		case WITHDRAWAL_INVESTER:
			payResult = payMent.unFreeze(innerSeqNo, dealRecords);
			break;
		case TRANSFER:
			resultMap = payMent.tranfulAll(innerSeqNo, dealRecords);
			logger.info("resultMap.getData()):"+resultMap.getData().toString());
			boolean isGoonC = true;
			int gonoC = 0;
			while(isGoonC && gonoC < maxGoon){	
				try{
					if(gonoC != 0){
						Thread.sleep(1000);
						logger.info("等待一秒。。。。。。");
					}
					gonoC = gonoC + 1;
					payResult = auditPayMentNotify(resultMap.getData());
					isGoonC = false;
				}catch(Exception e){
					//上面发生异常以后，返回“转账的结果”
					payResult = getTranfulAllResult(resultMap.getData());
					logger.error(e.getMessage());
					logger.error(DealType.TRANSFER.des+"回调异常，本次为第"+gonoC+"次回调");
				}
			}		
			break;
		case ABORT_BID:
			resultMap = payMent.loanFree(innerSeqNo, dealRecords);
			payResult = loanFreeNotify(resultMap.getData());
			break;
		default:
			throw new BusinessException(CommonResultCode.BIZ_ERROR.getCode(),
					"交易类型不存在");
		}

		/*
		 * String tradeStatus = EasyPayTradeState.TRADE_FINISHED.getState(); if
		 * (!payResult.isSuccess()) { tradeStatus =
		 * EasyPayTradeState.TRADE_FAILURE.getState(); } BaseResult modResult =
		 * modifyDealRecordState(innerSeqNo, tradeStatus, totalAmount);
		 * //更新资金操作记录表状态 本地操作在这里更新，第三方调用由web层更新 CashRecordDO cashRecord = new
		 * CashRecordDO();
		 * cashRecord.setCrResponseState(CashOperateState.SUCCESS.getState());
		 * cashRecord.setCrSeqNo(innerSeqNo);
		 * cash.modifyCashRecordState(cashRecord); for (int i = 0;
		 * !modResult.isSuccess() && i < 3; ++i) { //重试3次 modResult =
		 * modifyDealRecordState(innerSeqNo, tradeStatus, totalAmount); try {
		 * Thread.sleep(500); } catch (InterruptedException e) {
		 * logger.error("[DealRecordServiceImpl][invokepayment]{}", e); } }
		 */
		return payResult;
	}
	
	/**
	 * 判断划转交易是否成功
	 * xiatt
	 */
	private BaseResult getTranfulAllResult(Map<String,String> notifyData){
		BaseResult result = new BaseResult();
		String transferaudit = notifyData.get("transferaudit").toString();
		transferaudit = transferaudit.replace("{", "");
		transferaudit = transferaudit.replace("}", "");
		transferaudit = transferaudit.replace(" ", "");
		String[] array = transferaudit.split(",");
		Map<String, String> returnParams = new HashMap<String, String>();
		for (int i = 1; i < array.length; i++) {
			String[] array1 = array[i].split("=");
			if (array1.length < 2) {
				returnParams.put(array1[0], "");
			} else {
				returnParams.put(array1[0], array1[1]);
			}
		}
		String resultCode = returnParams.get("ResultCode");
		if (!"88".equals(resultCode)) {
			result.setSuccess(false);
		}
		return result;
	}
	

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public BaseResult unfrozenDealMoney(String innerSeqNo) {
		List<DealRecordDO> dealRecords = dealRecordDao
				.findDealRecordsByInnerSeqNo(innerSeqNo);
		if (CollectionUtils.isEmpty(dealRecords)) {
			throw new BusinessException(
					CommonResultCode.ERROR_DATA_NOT_EXISTS.getCode(), "无交易记录");
		}
		for (DealRecordDO deal : dealRecords) {
			// 交易状态尚未结束，不允许解冻
			if (DealState.valueOf(deal.getDrState()).equals(
					DealState.NOCALLBACK)) {
				throw new BusinessException(
						CommonResultCode.BIZ_ERROR.getCode(), "解冻交易失败,交易进行中");
			}
		}
		BaseResult result = payMent.unFreeze(innerSeqNo, dealRecords);
		return result;
	}

	@Override
	public List<DealRecordDO> queryDealRecordsByInnerSeqNo(String innerSeqNo) {
		List<DealRecordDO> dealRecords = dealRecordDao
				.findDealRecordsByInnerSeqNo(innerSeqNo);
		return dealRecords;
	}

	/**
	 * 易生支付回调业务逻辑处理，成功 success 为true 否则为false
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
	@Override
	public BaseResult payMentNotify(Map notifyData) {
		// 使用EasyPay的支付接口，将支付接口返回数据转成EasyPay的通知数据
		EasyPayNotifyData easyPayNotifyData = new EasyPayNotifyData();

		easyPayNotifyData.setNotifyId(notifyData.get("notify_id").toString());
		easyPayNotifyData.setOutTradeNo(notifyData.get("out_trade_no")
				.toString());
		easyPayNotifyData.setTradeStatus(notifyData.get("trade_status")
				.toString());
		easyPayNotifyData.setTotalFee(new BigDecimal((String) notifyData
				.get("total_fee")));
		easyPayNotifyData.setSign(notifyData.get("sign").toString());

		BaseResult result = new BaseResult();

		PlainResult<EasyPayConfig.EasyPayVerifyResult> responseTxt = EasyPayUtils
				.verifyNotify(easyPayNotifyData.getNotifyId());
		EasyPayVerifyResult verifyResult = responseTxt.getData();
		if (verifyResult == null) {
			result.setSuccess(false);
			result.setMessage("验证出错");
			logger.warn(
					"[DealRecordService][payMentNotify] notifyId:{} seqNo:{}notify verify error",
					easyPayNotifyData.getNotifyId(),
					easyPayNotifyData.getOutTradeNo());
			return result;
		}
		EasyPayTradeState easyPayTradeState = EasyPayTradeState
				.valueOf(easyPayNotifyData.getTradeStatus());
		String mySign = EasyPayUtils.buildMySign(notifyData, EasyPayConfig.KEY);
		if (mySign.equals(easyPayNotifyData.getSign())
				&& verifyResult.value
						.equals(EasyPayConfig.EasyPayVerifyResult.True.value)) { // 验证通过
																					// BaseResult
			BaseResult modifyResult = modifyDealRecordState(
					easyPayNotifyData.getOutTradeNo(),
					easyPayTradeState.getState(),
					easyPayNotifyData.getTotalFee());
			if (!modifyResult.isSuccess()) {
				result.setSuccess(false);
			}
		} else {
			result.setSuccess(false);
		}
		return result;
	}

	/**
	 * 银行接口回调业务逻辑处理，成功 success 为true 否则为false
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ,  rollbackFor = Exception.class)
	public BaseResult bhyhPayMentNotify(Map notifyData) {
		BigDecimal mount = BigDecimal.ZERO;
		BaseResult result = new BaseResult();
		String OrderNo = "";
		OrderNo = notifyData.get("MerBillNo").toString();
		String money = notifyData.get("TransAmt").toString();
		try {
			money =FormatHelper.changeF2Y(money);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//mount = BigDecimal.valueOf(Double.valueOf(money)).add(mount);
		mount = mount.add(new BigDecimal(money+""));//不加红包的钱
		String resultCode = "";
		if (!("".equals(notifyData.get("RespCode").toString()) || notifyData
				.get("RespCode").toString() == null)) {
			resultCode = notifyData.get("RespCode").toString();
		}
		if("000000".equals(resultCode)){
			String LoanNo = "";//外部流水号
			LoanNo = notifyData.get("TransId").toString();
			String FreezeId = "";//冻结编号
			FreezeId = notifyData.get("FreezeId").toString();
			InvestOrderDO order = new InvestOrderDO();
			order.setIoInnerSeqNo(OrderNo+"0");
			order.setIoOutSeqNo(LoanNo);
			order.setIoFreezeId(FreezeId);
			BaseResult baseResult = investOrderService.updateBySeqNo(order);
			if (!baseResult.isSuccess()) {
				return result.setError(CommonResultCode.BIZ_ERROR,
						"更新第三方业务流水记录失败");
			}
			InvestDO investDO = new InvestDO();
			investDO.setInInnerSeqNo(OrderNo);
			investDO.setIoOutSeqNo(LoanNo);
			investDO.setInFreezeId(FreezeId);
			int in=investDao.updateInInnerSeqNo(investDO);
			if(in<=0){
				return result.setError(CommonResultCode.BIZ_ERROR,
						"更新投资记录失败");
			}
			CashRecordDO cashRecord = new CashRecordDO();
			//cashRecord.setCrSeqNo(OrderNo.substring(0, OrderNo.length() - 1));
			cashRecord.setCrSeqNo(OrderNo);
			// 保存回调
			cashRecord.setCrResponse(JSON.toJSONString(notifyData));
			cashRecord.setCrResponseState(200);
			cashRecordService.modifyCashRecordState(cashRecord);
			//String money = notifyData.get("TransAmt").toString();
		}
		String status;
		if ("000000".equals(resultCode)) {
			status = "TRADE_FINISHED";
		} else {
			status = "TRADE_FAILURE";
		}
//		BaseResult modifyResult = modifyDealRecordState(
//				OrderNo.substring(0, OrderNo.length() - 1), status, mount);
		BaseResult modifyResult = modifyDealRecordState(
				OrderNo, status, mount);
		if (!modifyResult.isSuccess()) {
			result.setSuccess(false);
		} else {
			result.setSuccess(true);
		}
		result.setMessage(notifyData.get("RespDesc").toString());

		return result;
	}
	
	/**
	 * 双乾支付回调业务逻辑处理，成功 success 为true 否则为false
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
	public BaseResult doublePayMentNotify(Map notifyData) {
		BigDecimal mount = BigDecimal.ZERO;
		BaseResult result = new BaseResult();
		String LoanList = notifyData.get("LoanJsonList").toString();
		JSONArray list = JSON.parseArray(LoanList);
		JSONObject LoanJsonListMap = new JSONObject();
		String OrderNo = "";
		for (int i = 0; i < list.size(); i++) {
			LoanJsonListMap = (JSONObject) list.get(i);
			OrderNo = LoanJsonListMap.get("OrderNo").toString();
			String LoanNo = "";//外部流水号
			if (!("".equals(LoanJsonListMap.get("LoanNo")) || LoanJsonListMap
					.get("LoanNo") == null)) {
				LoanNo = LoanJsonListMap.get("LoanNo").toString();
			}

			InvestOrderDO order = new InvestOrderDO();
			order.setIoInnerSeqNo(OrderNo);
			order.setIoOutSeqNo(LoanNo);
			BaseResult baseResult = investOrderService.updateBySeqNo(order);
			if (!baseResult.isSuccess()) {
				return result.setError(CommonResultCode.BIZ_ERROR,
						"更新第三方业务流水记录失败");
			}
			String money = LoanJsonListMap.get("Amount").toString();
			//mount = BigDecimal.valueOf(Double.valueOf(money)).add(mount);
			if(i==0){
				mount = mount.add(new BigDecimal(money+""));//不加红包的钱
			}

		}
		
		CashRecordDO cashRecord = new CashRecordDO();
		cashRecord.setCrSeqNo(OrderNo.substring(0, OrderNo.length() - 1));
		// 保存回调
		cashRecord.setCrResponse(JSON.toJSONString(notifyData));
		cashRecord.setCrResponseState(200);
		cashRecordService.modifyCashRecordState(cashRecord);
		String money = LoanJsonListMap.get("Amount").toString();
		String resultCode = "";
		if (!("".equals(notifyData.get("ResultCode").toString()) || notifyData
				.get("ResultCode").toString() == null)) {
			resultCode = notifyData.get("ResultCode").toString();
		}

		String status;
		if ("88".equals(resultCode)) {
			status = "TRADE_FINISHED";
		} else {
			status = "TRADE_FAILURE";
		}
		BaseResult modifyResult = modifyDealRecordState(
				OrderNo.substring(0, OrderNo.length() - 1), status, mount);
		if (!modifyResult.isSuccess()) {
			result.setSuccess(false);
		} else {
			result.setSuccess(true);
		}
		result.setMessage(notifyData.get("Message").toString());

		return result;
	}

	/**
	 * 双乾审核回调业务逻辑处理，成功 success 为true 否则为false
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
	public BaseResult auditPayMentNotify(Map notifyData) {
		String transferaudit = notifyData.get("transferaudit").toString();
		transferaudit = transferaudit.replace("{", "");
		transferaudit = transferaudit.replace("}", "");
		transferaudit = transferaudit.replace(" ", "");
		String[] array = transferaudit.split(",");
		Map<String, String> returnParams = new HashMap<String, String>();
		for (int i = 1; i < array.length; i++) {
			String[] array1 = array[i].split("=");
			if (array1.length < 2) {
				returnParams.put(array1[0], "");
			} else {
				returnParams.put(array1[0], array1[1]);
			}
		}
		String OrderNo = returnParams.get("Remark1");
		String resultCode = returnParams.get("ResultCode");
		String money = returnParams.get("Remark2");
		CashRecordDO cashRecord = new CashRecordDO();
		cashRecord.setCrSeqNo(OrderNo);
		cashRecord.setCrResponse(JSON.toJSONString(notifyData));
		cashRecord.setCrResponseState(200);
		cashRecordService.modifyCashRecordState(cashRecord);
		BigDecimal mount = BigDecimal.ONE;
		BaseResult result = new BaseResult();
		mount = new BigDecimal(money);
		String status;
		if ("88".equals(resultCode)) {
			status = "TRADE_FINISHED";
		} else {
			status = "TRADE_FAILURE";
		}
		BaseResult modifyResult = modifyDealRecordState(OrderNo, status, mount);
		if (!modifyResult.isSuccess()) {
			result.setSuccess(false);
		} else {
			result.setSuccess(true);
		}
		result.setMessage(returnParams.get("Message"));
		return result;
	}

	/**
	 * 根据seqNos查询交易的list
	 */
	@Override
	public ListResult<DealRecord> queryDealRecordsByInnerSeqNo(
			List<String> seqNos) {
		if (CollectionUtils.isEmpty(seqNos)) {
			return new ListResult<DealRecord>();
		}
		List<DealRecord> result = new ArrayList<DealRecord>();
		List<DealRecordDO> res = dealRecordDao
				.findDealRecordsByInnerSeqNos(seqNos);
		for (DealRecordDO dealRecordDO : res) {
			DealRecord dealRecord = DealRecordConverter
					.toDealRecord(dealRecordDO);
			result.add(dealRecord);
		}
		ListResult<DealRecord> resultList = new ListResult<DealRecord>();
		resultList.setData(result);
		return resultList;
	}

	/**
	 * 双乾更新交易记录状态&进行回调操作&更新一堆记录表&更新用户资金表
	 * 
	 * @return BaseResult 返回结果为true时表明业务逻辑处理成功，否则业务逻辑处理失败
	 * @notice 这里的更新策略是基于第三方支付支持批量资金操作的
	 * @notice 这里的返回状态决定是否向EasyPay发送确认消息
	 * @see 其中对资金操作记录表的更新是在web层收到Notify消息时进行的。
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	@Override
	public BaseResult modifyDealRecordStateWithDouble(Map params) {
		BaseResult result = new BaseResult();
		DealRecordDO dealRecordDo = new DealRecordDO();
		// 接口支持批量 故cashSeqNo和innerSeqNo相同 否则此处为outSeqNo
		String seqNo = params.get("MerBillNo").toString();
		dealRecordDo.setDrInnerSeqNo(seqNo);

		// 判断交易状态
		String status = "";
		if (params.containsKey("RespCode")) {
			status = params.get("RespCode").toString();
		}
		if ("000000".equals(status)) {
			dealRecordDo.setDrState(DealState.SUCCESS.getState());
			result.setSuccess(true);
		} else {
			dealRecordDo.setDrState(DealState.FAILURE.getState());
			result.setSuccess(false);
		}
		List<DealRecordDO> drs = dealRecordDao
				.findDealRecordsByInnerSeqNo(seqNo);
		if (drs.isEmpty()) {
			throw new BusinessException(CommonResultCode.BIZ_ERROR.getCode(),
					String.format("交易流水号:%s ,无交易记录!", seqNo));
		}
		logger.debug(
				"[DealRecordServiceImpl][modifyDealRecordState] seqNo:{} 查出来的交易状态:{}",
				seqNo, JSON.toJSON(drs));
		// 更新交易记录状态
		int flag = dealRecordDao.updateDealRecordState(dealRecordDo);
		if (flag <= 0) {// 更新交易
			logger.warn(
					"[DealRecordServiceImpl][modifyDealRecordState] 更新交易记录状态警告：无交易记录可更新。交易流水号：{}",
					seqNo);
			throw new BusinessException(CommonResultCode.ERROR_DB.getCode(),
					"交易状态更新失败");
		}

		// 查出内部交易号对应的所有交易记录
		List<DealRecordDO> dealRecords = dealRecordDao
				.findDealRecordsByInnerSeqNo(seqNo);
		// 根据获取交易的交易类型 非交易详细类型
		/*
		 * int dealType = dealRecords.get(0).getDrType(); DealType type =
		 * DealType.valueOf(dealType); Callback<DealNotify> callBack =
		 * CashCallBackCenter.getCallBackByType(type);
		 * notify.setInnerSeqNo(seqNo); //notify.setTotalFee(totalFee);
		 * notify.setState(DealState.valueOf(newState)); notifyState =
		 * callBack.doCallback(notify);
		 */

		modifyAccountTables(dealRecords);

		return result;
	}

	@Override
	public BaseResult PayBackNotify(Map notifyData) {
		BigDecimal mount = BigDecimal.ZERO;
		String OrderNo = "";
		String LoanList = notifyData.get("LoanJsonList").toString();
		JSONArray list = JSON.parseArray(LoanList);
		JSONObject LoanListJson = (JSONObject) list.get(0);
		String inOrderNo = LoanListJson.get("OrderNo").toString();
		OrderNo = inOrderNo.substring(0, inOrderNo.length() - 1);

		CashRecordDO cashRecord = new CashRecordDO();
		cashRecord.setCrSeqNo(OrderNo);
		// 保存回调
		cashRecord.setCrResponse(JSON.toJSONString(notifyData));
		cashRecord.setCrResponseState(200);
		cashRecordService.modifyCashRecordState(cashRecord);

		for (int i = 0; i < list.size(); i++) {
			JSONObject LoanJsonListMap = (JSONObject) list.get(i);
			String money = LoanJsonListMap.get("Amount").toString();
			BigDecimal getMount = new BigDecimal(money);
			mount = mount.add(getMount);
		}
		BaseResult result = new BaseResult();
		String resultCode = notifyData.get("ResultCode").toString();
		String status;

		if ("88".equals(resultCode)) {
			status = "TRADE_FINISHED";
		} else {
			status = "TRADE_FAILURE";
		}
		BaseResult modifyResult = modifyDealRecordState(OrderNo, status, mount);
		if (!modifyResult.isSuccess()) {
			result.setSuccess(false);
		} else {
			result.setSuccess(true);
		}
		result.setMessage(notifyData.get("Message").toString());
		return result;
	}

	@Override
	public ListResult<RecentDeal> findMyRecentDeal(String accountNo1,String accountNo2) {
		ListResult<RecentDeal> list = new ListResult<RecentDeal>();
		List<RecentDeal> RecentDealList = dealRecordDao
				.findMyRecentDeal(accountNo1,accountNo2);
		// 如果使用了红包减去红包金额
//		for (RecentDeal dr : RecentDealList) {
//			if (DealType.TRANSFER.type==dr.getDealType()) {
//				BigDecimal redAmount = investOrderDao
//						.findRedAmountByOutSeqNo(dr.getOutSeqNo());
//				if (redAmount != null) {
//					dr.setDealMoney((dr.getDealMoney().subtract(redAmount)));
//				}
//			}
//		}
		list.setData(RecentDealList);
		return list;
	}

	@Override
	public PageResult<DealRecordDO> queryDealByParams(
			DealRecordDO dealRecordDO, PageCondition pageCondition,
			String startDate, String endDate) {
		PageResult<DealRecordDO> pageResult = new PageResult<DealRecordDO>(
				pageCondition);

		int count = this.dealRecordDao.countDealByParams(dealRecordDO,
				startDate, endDate);
		if (count > 0) {
			List<DealRecordDO> result = this.dealRecordDao.findDealByParams(
					dealRecordDO, pageCondition, startDate, endDate);
			// 如果使用了红包减去红包金额
//			for (DealRecordDO dr : result) {
//				BigDecimal redAmount = investOrderDao
//						.findRedAmountByOutSeqNo(dr.getDrOutSeqNo());
//				if (redAmount != null) {
//					dr.setDrMoneyAmount(dr.getDrMoneyAmount().subtract(
//							redAmount));
//				}
//			}
			pageResult.setData(result);
			pageResult.setTotalCount(count);
		}
		return pageResult;
	}

	@Override
	public BaseResult loanFreeNotify(Map<String, String> notifyData) {
		String OrderNo = notifyData.get("Remark1");
		String resultCode = notifyData.get("ResultCode");
		String money = notifyData.get("Remark2");
		CashRecordDO cashRecord = new CashRecordDO();
		cashRecord.setCrSeqNo(OrderNo);
		cashRecord.setCrResponse(JSON.toJSONString(notifyData));
		cashRecord.setCrResponseState(200);
		cashRecordService.modifyCashRecordState(cashRecord);
		BigDecimal mount = BigDecimal.ONE;
		BaseResult result = new BaseResult();
		mount = new BigDecimal(money);
		String status;
		if ("88".equals(resultCode)) {
			status = "TRADE_FINISHED";
		} else {
			status = "TRADE_FAILURE";
		}
		BaseResult modifyResult = modifyDealRecordState(OrderNo, status, mount);
		if (!modifyResult.isSuccess()) {
			result.setSuccess(false);
		} else {
			result.setSuccess(true);
		}
		result.setMessage(notifyData.get("Message"));
		return result;
	}
	
	@Override
	public BaseResult modifyDealRecordState(String seqNo, Integer investId,
			String tradeStatus, BigDecimal totalFee) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseResult modifyDealRecordStateByCrdit(String seqNo,
			String tradeStatus, BigDecimal totalFee) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseResult chinapnrPayMentNotify(Map notifyData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseResult chinapnrPayBackNotify(Map notifyData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DealRecordDO> queryDealRecordsByOutSeqNo(String outSeqNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateDealRecordState(DealRecordDO updateDealRecord) {
		return dealRecordDao.updateDealRecordState(updateDealRecord);
	}

	@Override
	public int batchInsert(List<DealRecordDO> dealRecordDoList) {
		return dealRecordDao.batchInsert(dealRecordDoList);
	}

	@Override
	public void insertRecord(DealRecordDO dealRecord) {
		dealRecordDao.insertRecord(dealRecord);
		
	}

	@Override
	public void updateRecord(DealRecordDO dealRecord) {
		dealRecordDao.updateRecord(dealRecord);
		
	}

	@Override
	public PageResult<DealRecordDO> queryRecord(DealRecordDO dealRecord, PageCondition pageCondition) {
		 PageResult<DealRecordDO> pageResult = new PageResult<DealRecordDO>(pageCondition.getPage(),
	                pageCondition.getPageSize());
	        int totalCount = dealRecordDao.countRecordReport(dealRecord);
	        pageResult.setTotalCount(totalCount);

	        if (totalCount > 0) {
	            List<DealRecordDO> list = dealRecordDao.recordReport(dealRecord, pageCondition);
	            pageResult.setData(list);
	        }

	        return pageResult;
	}
	public int queryCountRecordReport(DealRecordDO dealRecord){
		int totalCount = dealRecordDao.countRecordReport(dealRecord);
		return totalCount;
		
	}

	@Override
	public PageResult<DealRecordDO> queryGuarRecord(DealRecordDO dealRecord,
			PageCondition pageCondition) {
		PageResult<DealRecordDO> pageResult = new PageResult<DealRecordDO>(pageCondition.getPage(),
                pageCondition.getPageSize());
        int totalCount = dealRecordDao.countGuarRecordReport(dealRecord);
        pageResult.setTotalCount(totalCount);

        if (totalCount > 0) {
            List<DealRecordDO> list = dealRecordDao.guarRecordReport(dealRecord, pageCondition);
            pageResult.setData(list);
        }

        return pageResult;
	}

	@Override
	public PageResult<DealRecordDO> queryDealZzlsByParams(
			DealRecordDO dealRecordDO, PageCondition pageCondition) {
		PageResult<DealRecordDO> pageResult = null;
		if(null == pageCondition){
			List<DealRecordDO> result = this.dealRecordDao.findDealZzlsByParams(
					dealRecordDO, null);
			pageResult = new PageResult<DealRecordDO>(1,result.size());
			pageResult.setTotalCount(result.size());
			pageResult.setData(result);
		}else{
			pageResult = new PageResult<DealRecordDO>(pageCondition);
			int count = this.dealRecordDao.countDealZzlsByParams(dealRecordDO);
			pageResult.setTotalCount(count);
			if (count > 0) {
				List<DealRecordDO> result = this.dealRecordDao.findDealZzlsByParams(
						dealRecordDO, pageCondition);
				pageResult.setData(result);
			}
		}

		return pageResult;
	}
}
