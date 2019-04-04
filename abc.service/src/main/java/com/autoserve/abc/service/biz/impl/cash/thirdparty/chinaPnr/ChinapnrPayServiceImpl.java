package com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.CashRecordDO;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.InvestOrderDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.UserAccountDO;
import com.autoserve.abc.dao.intf.AccountInfoDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.BatchPayData;
import com.autoserve.abc.service.biz.entity.BatchPayQuery;
import com.autoserve.abc.service.biz.entity.Deal;
import com.autoserve.abc.service.biz.entity.DealDetail;
import com.autoserve.abc.service.biz.entity.EasyPayData;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.ItemOfBatchPay;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.CashOperateState;
import com.autoserve.abc.service.biz.enums.CashOperateType;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.EasyPayConfig;
import com.autoserve.abc.service.biz.enums.RequestMethodType;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.ToCashQueryList;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data.BidDetail;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data.BorrowerDetail;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data.FeeDetail;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data.HuiFuData;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data.OrdDetail;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.easypay.BatchPayUtils;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.CashRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.biz.intf.cash.PayMentService;
import com.autoserve.abc.service.biz.intf.cash.UserAccountService;
import com.autoserve.abc.service.biz.intf.common.AreaService;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.invest.InvestOrderService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.http.AbcHttpCallService;
import com.autoserve.abc.service.http.AbcHttpCallServiceImpl;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.service.util.EasyPayUtils;
import com.autoserve.abc.service.util.RsaHelper;
import com.autoserve.abc.service.util.SystemGetPropeties;

@Service
public class ChinapnrPayServiceImpl implements PayMentService {

	/**
	 * 汇付支付具体业务实现
	 * 
	 * @author J.YL 2014年11月18日 下午1:22:32
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ChinapnrPayServiceImpl.class);
	@Resource
	private AccountInfoService account;
	@Resource
	private AccountInfoDao accountInfoDao;
	@Resource
	private UserAccountService userAccount;
	@Resource
	private CashRecordService cashRecordService;
	@Resource
	private AreaService areaService;
	@Resource
	private AbcHttpCallService abcHttpCallService;
	@Resource
	private DoubleDryService doubleDryService;
	@Resource
	private UserService userService;
	@Resource
	private EmployeeService employeeService;
	@Resource
	private HuifuPayService chinapnrPayService;
	@Resource
	private IncomePlanService incomePlanService;
	@Resource
	private LoanDao loanDao;
	@Resource
	private InvestOrderService investOrderService;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public BaseResult transferMoney(String seqNo, List<DealRecordDO> dealRecords) {
		BaseResult result = new BaseResult();
		String requestList = new String();
		PlainResult<CashRecordDO> cashdao = cashRecordService
				.queryCashRecordBySeqNo(seqNo);
		requestList = cashdao.getData().getCrRequestParameter();
		String requestArray[] = requestList.split("\\|");
		String LoanNoList = requestArray[0];
		String LoanJsonList = requestArray[1];
		int size = LoanJsonList.split("\\]\\[").length;
		for (int i = 0; i < size; i++) {
			LoanJsonList = LoanJsonList.replace("][", ",");
		}
		String money = cashdao.getData().getCrMoneyAmount().toString();
		Integer status = cashdao.getData().getCrOperateType();
		BaseResult resultStr = new BaseResult();
		switch (status) {
		case 1: {
			resultStr = doubleDryService.payBack(LoanJsonList);
			break;
		}
		case 3: {
			resultStr = doubleDryService.fullTranfer(LoanNoList, LoanJsonList,
					seqNo, money);
			break;
		}
		}
		// 发送请求,获取数据
		if (resultStr.isSuccess()) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		return result;
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
			// 退款账户
			UserAccountDO payUser = userAccountMap.get(payAccount);
			BigDecimal payTotal = payUser.getUaTotalMoney();
			BigDecimal payUseable = payUser.getUaUseableMoney();
			payUser.setUaTotalMoney(payTotal.subtract(moneyAmount));
			payUser.setUaUseableMoney(payUseable.subtract(moneyAmount));

			// 收款账户
			UserAccountDO receiveUser = userAccountMap.get(receiveAccount);
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
		BaseResult baseResult = new BaseResult();
		String LoanJsonList = new String();
		PlainResult<CashRecordDO> cashdao = cashRecordService
				.queryCashRecordBySeqNo(seqNo);
		LoanJsonList = cashdao.getData().getCrRequestParameter();
		// 发送请求,获取数据
		PlainResult<Map<String, String>> resultStr = doubleDryService
				.invest(LoanJsonList);
		if (resultStr.isSuccess()) {
			baseResult.setSuccess(true);
		} else {
			baseResult.setSuccess(false);
		}
		/*
		 * List<UserAccountDO> userAccountDos = preHandle(seqNo, dealRecords);
		 * Map<String, UserAccountDO> userAccountMap = new HashMap<String,
		 * UserAccountDO>(); for (UserAccountDO uad : userAccountDos) {
		 * userAccountMap.put(uad.getUaAccountNo(), uad); } for (DealRecordDO
		 * record : dealRecords) { String payAccount = record.getDrPayAccount();
		 * BigDecimal moneyAmount = record.getDrMoneyAmount(); //冻结账户
		 * UserAccountDO payUser = userAccountMap.get(payAccount); BigDecimal
		 * frozen = payUser.getUaFrozen(); BigDecimal useable =
		 * payUser.getUaUseableMoney();
		 * payUser.setUaFrozen(frozen.add(moneyAmount));
		 * payUser.setUaUseableMoney(useable.subtract(moneyAmount)); }
		 */
		return baseResult;
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
		// 充值为跳转到第三方接口执行
		return BaseResult.SUCCESS;
	}

	/**
	 * 调用第三方接口，非本地操作
	 */
	@Override
	@SuppressWarnings("unchecked")
	public BaseResult toCash(String seqNo, List<DealRecordDO> dealRecords) {
		BaseResult freezeResult = freeze(seqNo, dealRecords);
		if (!freezeResult.isSuccess()) { // 冻结失败
			logger.error(
					"[EasyPayServiceImpl][toCash] 提现失败 ,交易流水号：{} 资金余额不足，无法提现！",
					seqNo);
			throw new BusinessException(CommonResultCode.BIZ_ERROR.getCode(),
					"资金余额不足，无法提现！");
		}
		CashRecordDO cashRecord = cashRecordService.queryCashRecordBySeqNo(
				seqNo).getData();
		if (cashRecord == null) {
			logger.error(
					"[EasyPayServiceImpl][toCash] 提现失败 ,交易流水号：{} 未找到资金操作记录",
					seqNo);
			throw new BusinessException(CommonResultCode.BIZ_ERROR.getCode(),
					"未找到资金操作记录");
		}
		// 发起web接口调用 cashRecord.getCrRequestUrl()
		String url = cashRecord.getCrRequestUrl();
		Map<String, String> para = JSON.parseObject(
				cashRecord.getCrRequestParameter(), HashMap.class);
		AbcHttpCallService callService = new AbcHttpCallServiceImpl();
		String easyReturn = callService.httpPost(url, para).getData();
		Document doc = null;

		try {
			doc = DocumentHelper.parseText(easyReturn);
		} catch (DocumentException e) {
			logger.error("[EasyPayService][toCash] 提现操作易生支付接口返回数据错误");
			throw new BusinessException(
					CommonResultCode.FAIL_HTTP_CALL.getCode(), "支付接口返回数据错误");
		}
		Element root = doc.getRootElement();
		Element status = root.element("status");
		if (status.getTextTrim()
				.equals(EasyPayConfig.BatchToCash.SUCCESS.state)) {// 成功则将提现请求加入job中定时查询状态
			BatchPayQuery bpq = new BatchPayQuery();
			bpq.setTransCode(EasyPayConfig.QueryTransCodeType.PAYQUERY.value);
			bpq.setTranDate(DateUtil.formatDate(new Date(),
					DateUtil.DEFAULT_DAY_STYLE_NOSPACE));
			bpq.setTradeBatchNo(seqNo);
			// 加入到job中 以下注释的代码为测试时使用
			// String result = null;
			// while (result == null) {
			// result = BatchPayUtils.queryByBatchNO(bpq);
			// // result =
			// "GAk+YhdzEyr486VKjS3vjgYqw9GH4T/agcAgTCgWfKH9HbxQvwD4pw+duEwN2lYdLV0f2MU7esEARRukGnNmOjwzfOaPJEs3P+eeykBvzfh3LihoOi2i95+j2Vr7Lrcbc9nBfWPMiZNQ5shOe+mZ6Hs09sbvCRB4QLwmjb3/Zpk+CSEtnOrrNG1pY2DIB9QUyHXQ/Bs+Xb0iZwyxP2TU3bYgAk3dY58Dpaax43Liekm0jRzVU+fga528DodzP7RZUlZKrZXiaVLCVQ8PfmYwaq2LQ03vmWj5u5wRm2Q5YMFlN2qV3sbBUebZo6DlZ90zlBLhkAzSWanO1sXdIhcVsJK6C4UiD1dcfc7imlbUbx42lRifZdsK8i67NQ7zwcnJAEz/fXLqde5G5n+hxAtA3q1jwUa8vCsWwb3tB1grnL5OKu1hRfiZds+DbtoVC1iwX9Sxp+qlOSTe/Sh9f6ciJlEbeXXwxbm/crJcCTLKzcjOfQlOd8xg6yQ/NnqLaWnEX0boOHD/HNIvhcXcJEcMUgEtwPZ10gIPbFBEB6Nr1CNkAMMBw+CVOjoCWOHiZcZwP6NxCxO4ItFpnjZ8VHqSVbloLyD5Y1d6XLRBBlq71B8QQpezNt6vqFhrlBCKnbIh4VHr7DGI0nnG7TcGRHXHLkI+3UbEMKrtp5KKxTs+aahlhmFheU3j+90BLG6Fb6My+XGWCUuUjUXJqsiqO9KQeENsaVkf3DNQNpF0U8D3zZ1+1pHFHg9bBZD6AwpqVC7B96e63QVJP6ps/YayW/twXtd8XE+fXnowm5f1gPnNOnfvba93KqklIdF7/lZTgD9+Mp7GGGdxhE/aXVEQSrYyN7gBTkUjcGO8qec7ofgdswm+vjWqXhRz4SbHSAgggluu4cAixdTtEZWChPjIFgkaKcm74BQqamBqzUlBeuqP846XWZpBrv1ZFo22jtw+m8ivdzlzQgwN3wwccMkXD0RmER+FUN3hBNquqV1F5xO+c+aC2d/4s6QfSctj+I6F7iRU";
			// if (!result.startsWith("<Resp>")) {
			// try {
			// Base64 decode = new Base64();
			// byte[] rb = decode.decode(result);
			// result = BatchPaySignature.decrypt(rb);
			//
			// } catch (Exception e1) {
			// logger.error("提现结果查询返回数据解密出错 ", e1);
			// }
			// }
			// }
			ToCashQueryList.add(bpq);
			// Document doc1 = null;
			// try {
			// doc1 = DocumentHelper.parseText(result);
			// } catch (DocumentException e) {
			// logger.error("[ToCashJob][run] 解析查询提现结果数据：", e);
			// }
			// Element root1 = doc1.getRootElement();
			// Element batchCurrnum = root1.element("batchCurrnum");
			// Element batchContent = root1.element("batchContent");
			// Element detailInfo = batchContent.element("detailInfo");
			// String batchCurrString = batchCurrnum.getTextTrim();
			// String detailInfoString = detailInfo.getTextTrim();
			// List<String> res =
			// Splitter.on(',').splitToList(detailInfoString);
			// System.out.println("adf");
		} else {
			// 提交提现请求出错 解冻提现金额
			unFreeze(seqNo, dealRecords);
			logger.warn(root.element("reason").getTextTrim());
			throw new BusinessException(CommonResultCode.BIZ_ERROR.getCode(),
					"第三方支付提现请求失败");
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
	private List<UserAccountDO> preHandle(String seqNo,
			List<DealRecordDO> dealRecords) {
		List<String> userAccountList = new ArrayList<String>();
		Set<String> accountSet = new HashSet<String>();
		BigDecimal totalAmount = new BigDecimal(0.0);
		for (DealRecordDO record : dealRecords) {
			accountSet.add(record.getDrPayAccount());
			accountSet.add(record.getDrReceiveAccount());
			totalAmount = totalAmount.add(record.getDrMoneyAmount());
		}
		ListResult<Account> queryResult = account
				.queryByAccountNos(new ArrayList<String>(accountSet));
		List<Account> userAccounts = queryResult.getData();
		for (Account ua : userAccounts) {
			userAccountList.add(ua.getAccountNo());
		}
		List<UserAccountDO> userAccountDos = userAccount.queryByAccountNo(
				userAccountList).getData();
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
			if (uad.getUaTotalMoney().doubleValue() < 0.0
					|| uad.getUaFrozen().doubleValue() < 0.0
					|| uad.getUaUseableMoney().doubleValue() < 0.0) {
				logger.warn(
						"[EasyPayUserAccountServiceImpl][updateUserAccountFinancial] accountNo:{} ",
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
	public CashRecordDO constructParam(Deal deals) {
		DealType busType = deals.getBusinessType();
		List<DealDetail> detail = deals.getDealDetail();
		if (detail == null || detail.isEmpty()) {
			throw new BusinessException(CommonResultCode.BIZ_ERROR.getCode(),
					"交易详情为空");
		}
		BigDecimal total = BigDecimal.ZERO;
		for (DealDetail det : detail) {
			total = total.add(det.getMoneyAmount());
		}
		RsaHelper rsa = RsaHelper.getInstance();
		Map params = new HashMap();
		CashRecordDO result = new CashRecordDO();
		// List<SecondaryJsonList> SecondaryJsonList = new
		// ArrayList<SecondaryJsonList>();
		String SecondaryJsonList = "";

		switch (busType) {
		case INVESTER: {
			List<DealDetail> dealDetails = deals.getDealDetail();
			List<HuiFuData> list = new ArrayList<HuiFuData>();
			List<OrdDetail> ordDetailList = new ArrayList<OrdDetail>();
			DealDetail dal = dealDetails.get(0);
			params = dal.getData();

			String bidType = params.get("bidType").toString();
			if (bidType.equals(BidType.COMMON_LOAN.toString())) {
				InnerSeqNo orgNo = deals.getInnerSeqNo(); // 订单号
				String orderNo = String.valueOf(orgNo);

				// 汇付投资接口参数
				String url = params.get("url").toString();
				String version = params.get("version").toString();
				String cmdId = params.get("cmdId").toString();
				String merCustId = params.get("merCustId").toString();
				String ordId = params.get("ordId").toString();
				String ordDate = params.get("ordDate").toString();
				String transAmt = params.get("transAmt").toString();
				String borrowerRate = params.get("borrowerRate").toString();
				String usrCustId = params.get("usrCustId").toString();
				String borrowerAmt = params.get("borrowerAmt").toString();
				String maxTenderRate = params.get("maxTenderRate").toString();
				String borrowerCustId = params.get("borrowerCustId").toString();
				String proId = params.get("proId").toString();
				String isFreeze = params.get("isFreeze").toString();
				String freezeOrdId = params.get("freezeOrdId").toString();
				String retUrl = params.get("retUrl").toString();
				String bgRetUrl = params.get("bgRetUrl").toString();

				// 2015/4/28 使用红包就要使用代金券 此时需要把proid返回回来 拼接
				String merPriv = orderNo + "," + proId; // 商户私有域 发送平台内部交易流水号 和
														// proId 拼接 以 | 分隔

				String reqExt = params.get("reqExt").toString();

				HuiFuData data = new HuiFuData();
				data.setUrl(url);
				data.setVersion(version);
				data.setCmdId(cmdId);
				data.setMerCustId(merCustId);
				data.setOrdId(ordId);
				data.setOrdDate(ordDate);
				data.setTransAmt(transAmt);
				data.setBorrowerRate(borrowerRate);
				data.setUsrCustId(usrCustId);
				data.setBorrowerAmt(borrowerAmt);
				data.setMaxTenderRate(maxTenderRate);
				data.setBorrowerCustId(borrowerCustId);
				data.setProId(proId);
				data.setIsFreeze(isFreeze);
				data.setFreezeOrdId(freezeOrdId);
				data.setRetUrl(retUrl);
				data.setBgRetUrl(bgRetUrl);
				data.setMerPriv(merPriv);
				data.setReqExt(reqExt);

				data.setInCustId(bidType);

				OrdDetail order = new OrdDetail();
				order.setBorrowerAmt(borrowerAmt);
				order.setBorrowerCustId(borrowerCustId);
				order.setBorrowerRate(borrowerRate);
				order.setProId(proId);
				ordDetailList.add(order);
				String borrowerDetails = JSON.toJSONString(ordDetailList)
						.replaceAll("borrowerCustId", "BorrowerCustId")
						.replaceAll("borrowerAmt", "BorrowerAmt")
						.replaceAll("borrowerRate", "BorrowerRate")
						.replaceAll("proId", "proId");

				data.setBorrowerDetails(borrowerDetails);

				StringBuffer sf = new StringBuffer();
				sf.append(data.getVersion()).append(data.getCmdId())
						.append(data.getMerCustId()).append(data.getOrdId())
						.append(data.getOrdDate()).append(data.getTransAmt())
						.append(data.getUsrCustId())
						.append(data.getMaxTenderRate())
						.append(borrowerDetails).append(data.getIsFreeze())
						.append(data.getFreezeOrdId()).append(data.getRetUrl())
						.append(data.getBgRetUrl()).append(data.getMerPriv())
						.append(data.getReqExt());
				String chkValue = rsa.getChkValue(sf.toString());

				data.setChkValue(chkValue);

				System.out.println(chkValue.length());

				list.add(data);

				String operateMoney = dal.getMoneyAmount().toString();
				BigDecimal money = new BigDecimal(operateMoney);
				result.setCrSeqNo(orderNo);

				result.setCrOperateType(CashOperateType.TRANSFER.getType());
				result.setCrRequestParameter(JSON.toJSONString(data));
				result.setCrMoneyAmount(money);
				result.setCrResponseState(CashOperateState.NOCALLBACK
						.getState());
			}
			if (bidType.equals(BidType.TRANSFER_LOAN.toString())) {

				Map<String, String> bidMap = new HashMap<String, String>();
				InnerSeqNo orgNo = deals.getInnerSeqNo(); // 订单号
				String orderNo = String.valueOf(orgNo);

				String url = params.get("url").toString();
				String version = params.get("version").toString();
				String cmdId = params.get("cmdId").toString();
				String merCustId = params.get("merCustId").toString();
				String sellCustId = params.get("sellCustId").toString();// 债权转让客户号
				String creditAmt = params.get("creditAmt").toString();// 债权转让
																		// 转出本金
				String creditDealAmt = params.get("creditDealAmt").toString();
				String bidOrdId = params.get("bidOrdId").toString();// 被转让的投标订单号
				String bidOrdDate = params.get("bidOrdDate").toString();// 被转让的投标日期
				String bidCreditAmt = params.get("bidCreditAmt").toString();// 转让金额
				String borrowerCustId = params.get("borrowerCustId").toString();// 借款人客户号
				String borrowerCreditAmt = params.get("borrowerCreditAmt")
						.toString();// 明细转让金额
				String prinAmt = params.get("prinAmt").toString();// 已还款金额
				// String proId = params.get("proId").toString();
				String transferLoanId = params.get("transferLoanId").toString();
				String fee = params.get("fee").toString();// 放款或扣款的手续费
				String divAcctId = params.get("divAcctId").toString();// 分账客户号
				String divAmt = params.get("divAmt").toString();// 分帐金额
				String buyCustId = params.get("buyCustId").toString();// 债权转让承接人
				String ordId = params.get("ordId").toString();
				String ordDate = params.get("ordDate").toString();
				String retUrl = params.get("retUrl").toString();
				String bgRetUrl = params.get("bgRetUrl").toString();
				String merPriv = orderNo + "," + transferLoanId; // 商户私有域
																	// 发送平台内部交易流水号
																	// 新增债权转让id
				String reqExt = params.get("reqExt").toString();

				/* 借款人明细 */
				BorrowerDetail borrowerDetail = new BorrowerDetail();
				borrowerDetail.setBorrowerCustId(borrowerCustId);
				borrowerDetail.setBorrowerCreditAmt(creditAmt);
				borrowerDetail.setPrinAmt(prinAmt);
				// borrowerDetail.setProId(proId);
				List<BorrowerDetail> listborrd = new ArrayList<BorrowerDetail>();
				listborrd.add(borrowerDetail);
				/* 债券转让明细 */
				BidDetail bidDetail = new BidDetail();
				bidDetail.setBidOrdId(bidOrdId);
				bidDetail.setBidOrdDate(bidOrdDate);
				bidDetail.setBidCreditAmt(creditAmt);
				bidDetail.setBorrowerDetails(listborrd);
				List<BidDetail> lstBd = new ArrayList<BidDetail>();
				lstBd.add(bidDetail);
				Map<String, List<BidDetail>> bidDetailMap = new HashMap<String, List<BidDetail>>();
				bidDetailMap.put("BidDetails", lstBd);

				List<FeeDetail> listDivDetails = new ArrayList<FeeDetail>();
				/* 分账账户明细，只有平台 */
				FeeDetail feeDetail = new FeeDetail();
				feeDetail.setDivAcctId(divAcctId);
				feeDetail.setDivAmt(fee);
				listDivDetails.add(feeDetail);

				String divDetails = JSON.toJSONString(listDivDetails)
						.replaceAll("divAcctId", "DivAcctId")
						.replaceAll("divAmt", "DivAmt");
				String bidDetails = JSON.toJSONString(bidDetailMap)
						.replaceAll("bid", "Bid")
						.replaceAll("borrower", "Borrower")
						.replaceAll("prinAmt", "PrinAmt");

				OrdDetail order = new OrdDetail();
				order.setBorrowerAmt(borrowerCreditAmt);
				order.setBorrowerCustId(borrowerCustId);
				order.setPrinAmt(prinAmt);
				// order.setProId(proId);
				ordDetailList.add(order);

				HuiFuData data = new HuiFuData();
				data.setUrl(url);
				data.setVersion(version);
				data.setCmdId(cmdId);
				data.setMerCustId(merCustId);
				data.setSellCustId(sellCustId);
				data.setCreditAmt(creditAmt);
				data.setCreditDealAmt(creditDealAmt);
				data.setBidDetails(bidDetails);
				data.setFee(fee);
				data.setDivDetails(divDetails);
				data.setBuyCustId(buyCustId);
				data.setOrdId(ordId);
				data.setOrdDate(ordDate);
				data.setRetUrl(retUrl);
				data.setBgRetUrl(bgRetUrl);
				data.setRespExt("");
				data.setMerPriv(merPriv);

				StringBuffer sf = new StringBuffer();
				sf.append(version).append(cmdId).append(merCustId)
						.append(sellCustId).append(creditAmt)
						.append(creditDealAmt).append(bidDetails).append(fee)
						.append(divDetails).append(buyCustId).append(ordId)
						.append(ordDate).append(retUrl).append(bgRetUrl)
						.append(merPriv).append(reqExt);
				String chkValue = rsa.getChkValue(sf.toString());
				System.out.println(chkValue);
				data.setChkValue(chkValue);

				String operateMoney = dal.getMoneyAmount().toString();
				BigDecimal money = new BigDecimal(operateMoney);
				result.setCrSeqNo(orderNo);
				result.setCrOperateType(CashOperateType.TRANSFER.getType());
				result.setCrRequestParameter(JSON.toJSONString(data));
				result.setCrMoneyAmount(money);
				result.setCrResponseState(CashOperateState.NOCALLBACK
						.getState());

				// PlainResult<Map> pojo =
				// chinapnrPayService.CreditAssign(data);
			}
			break;
		}
		case PAYBACK: {
			List<DealDetail> dealDetails = deals.getDealDetail();
			InnerSeqNo orgNo = deals.getInnerSeqNo();
			String orderNo = String.valueOf(orgNo);
			StringBuffer LoanJson = new StringBuffer();
			BigDecimal money = BigDecimal.ZERO;
			BigDecimal mount = BigDecimal.ZERO;
			String outUserId = "";
			String outUserType = "";
			String inUserId = "";
			String inUserType = "";
			List<DealDetail> list = new ArrayList();
			DealDetail dal = null;
			DealDetail outDealList = null;
			String inUserDoubleMoneyType = "";
			String outUserDoubleMoneyType = "";
			String fee ="0.00";
			String divDetails ="";
			StringBuffer sf = new StringBuffer();
			String merCustId = SystemGetPropeties
					.getChinaPnrString("MerCustId");
			int listSize = 1;
			Map<String, String> paramsMap = new HashMap<String, String>();
			HuiFuData data = new HuiFuData();
			String FeeObjFlag ="";
			int takeServeFeeNum =0;
			for (DealDetail dealDetail : dealDetails) {
				paramsMap = dealDetail.getData();
				if (dal == null) {
					outDealList = new DealDetail();
					dal = new DealDetail();
					dal.setReceiveAccountId(dealDetail.getReceiveAccountId());
					dal.setMoneyAmount(money);
				}

				mount = mount.add(dealDetail.getMoneyAmount());
				//加上违约罚金 夏同同/2016-04-10
				//if (DealDetailType.PLA_SERVE_FEE.equals(dealDetail.getDealDetailType())) {
				if (DealDetailType.PLA_SERVE_FEE.equals(dealDetail.getDealDetailType()) ||
                		DealDetailType.PAYBACK_BREACH_FINE.equals(dealDetail.getDealDetailType())) {
					FeeObjFlag ="O";
					inUserDoubleMoneyType = merCustId;
					//分账转给平台
	        		fee = String.valueOf(dealDetail.getMoneyAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
	    			FeeDetail feeDetail=new FeeDetail();
	    			feeDetail.setDivAcctId(SystemGetPropeties.getChinaPnrString("ServFeeAcctId"));
	    			feeDetail.setDivCustId(merCustId);//ServFeeAcctId
	    			feeDetail.setDivAmt(String.valueOf(dealDetail.getMoneyAmount().setScale(2, BigDecimal.ROUND_HALF_UP)));
	    			List<FeeDetail> feeDetailList=new ArrayList<FeeDetail>();
	    			feeDetailList.add(feeDetail);
	    			divDetails= JSON.toJSONString(feeDetailList).replaceAll("divAcctId", "DivAcctId").replaceAll("divCustId", "DivCustId").replaceAll("divAmt", "DivAmt");
				} else {
					String receiveAccountId = dealDetail.getReceiveAccountId();
					inUserId = receiveAccountId.split("\\|")[0];
					inUserType = receiveAccountId.split("\\|")[1];
					AccountInfoDO inAccountInfo = account.queryByAccountMark(
							Integer.parseInt(inUserId),
							Integer.parseInt(inUserType));
					inUserDoubleMoneyType = inAccountInfo.getAccountNo();
				}

				String payAccount = dealDetail.getPayAccountId();
				outUserId = payAccount.split("\\|")[0];
				outUserType = payAccount.split("\\|")[1];
				AccountInfoDO outAccountInfo = account.queryByAccountMark(
						Integer.parseInt(outUserId),
						Integer.parseInt(outUserType));
				outUserDoubleMoneyType = outAccountInfo.getAccountNo();

				outDealList.setPayAccountId(outUserDoubleMoneyType);
				outDealList.setReceiveAccountId(inUserDoubleMoneyType);
				outDealList.setMoneyAmount(mount);
				// 获取投资订单记录
				String orderList = paramsMap.get("orderList");
				String subOrdId = orderList.split(",")[0];
				String subOrdDate = orderList.split(",")[1];
				// 出账客户号
				String outCustId = outUserDoubleMoneyType;
				// 入账客户号
				String inCustId = inUserDoubleMoneyType;
				String takeServeFee = paramsMap.get("takeServeFee");
				
				if("true".equals(takeServeFee)){
					takeServeFeeNum =4;
				}else{
					takeServeFeeNum =3;
				}
				// 交易金额
				if (listSize == takeServeFeeNum) {
					String bgRetUrl = SystemGetPropeties
							.getChinaPnrString("notifyurlprefix")
							+ SystemGetPropeties
									.getChinaPnrString("RepaymentBgRetUrl");
					data.setUrl(SystemGetPropeties.getChinaPnrString("url"));
					data.setVersion(SystemGetPropeties
							.getChinaPnrString("Version2"));
					data.setCmdId("Repayment");
					data.setMerCustId(merCustId);
					data.setOrdId(String.valueOf(System.currentTimeMillis()));
					data.setOrdDate(new SimpleDateFormat("yyyyMMdd")
							.format(new Date()));
					data.setOutCustId(outCustId);
					data.setSubOrdId(subOrdId);
					data.setSubOrdDate(subOrdDate);
					data.setOutAcctId("");
					data.setTransAmt(mount.toString());
					data.setInCustId(inCustId);
					data.setInAcctId("");
					data.setFeeObjFlag(FeeObjFlag);
					data.setBgRetUrl(bgRetUrl);
					data.setIdNo(orderNo);// dealRecord的内部交易流水号
					data.setReqExt("");
					data.setFee(fee);
					data.setDivDetails(divDetails);
					data.setMerPriv("");
					sf.append(JSON.toJSONString(data)).append("|");
					list.add(outDealList);
					listSize = 0;
					mount = BigDecimal.ZERO;
					mount = mount.add(dealDetail.getMoneyAmount());
					outDealList = new DealDetail();
					dal = new DealDetail();
					dal.setReceiveAccountId(dealDetail.getReceiveAccountId());
				}
				listSize++;

			}
			if (dealDetails.size() % takeServeFeeNum != 0) {
				list.add(outDealList);
			}

			result.setCrSeqNo(orderNo);
			result.setCrRequestParameter(sf.toString());
			result.setCrRequestUrl("");
			result.setCrMoneyAmount(dealDetails.get(0).getMoneyAmount());
			result.setCrOperateType(CashOperateType.REFUND.getType());
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
		case RECHARGE: {

			InnerSeqNo orgNo = deals.getInnerSeqNo(); // 订单号
			String orderNo = String.valueOf(orgNo);
			// 构造第三方支付充值接口调用参数
			result.setCrMoneyAmount(total);
			result.setCrOperateDate(new Date());
			result.setCrOperateType(CashOperateType.RECHARGE.getType());
			result.setCrRequestMethod("GET");
			DealDetail dal = detail.get(0);
			params = dal.getData();
			String userId = params.get("userId").toString();
			String userType = params.get("type").toString();
			AccountInfoDO accountInfoDO = account.queryByAccountMark(
					Integer.parseInt(userId), Integer.parseInt(userType));
			String url = params.get("url").toString();
			// 需要发送的参数
			String version = params.get("version").toString();
			String cmdId = params.get("cmdId").toString();
			String merCustId = params.get("merCustId").toString();
			String usrCustId = params.get("usrCustId").toString();
			String ordId = params.get("ordId").toString();
			String ordDate = params.get("ordDate").toString();
			String gateBusiId = params.get("gateBusiId").toString();
			String openBankId = params.get("openBankId").toString();
			String dcFlag = params.get("dcFlag").toString();
			String transAmt = params.get("transAmt").toString();
			String bgRetUrl = params.get("bgRetUrl").toString();
			String retUrl = params.get("retUrl").toString();
			String merPriv = orderNo;

			StringBuffer sf = new StringBuffer();
			sf.append(version).append(cmdId).append(merCustId)
					.append(usrCustId).append(ordId).append(ordDate)
					.append(gateBusiId).append(openBankId).append(dcFlag)
					.append(transAmt).append(retUrl).append(bgRetUrl)
					.append(merPriv);
			String chkValue = rsa.getChkValue(sf.toString());
			HuiFuData data = new HuiFuData();
			data.setUrl(url);
			data.setVersion(version);
			data.setCmdId(cmdId);
			data.setMerCustId(merCustId);
			data.setUsrCustId(usrCustId);
			data.setOrdId(ordId);
			data.setOrdDate(ordDate);
			data.setGateBusiId(gateBusiId);
			data.setOpenBankId(openBankId);
			data.setDcFlag(dcFlag);
			data.setTransAmt(transAmt);
			data.setRetUrl(retUrl);
			data.setBgRetUrl(bgRetUrl);
			data.setMerPriv(merPriv);
			data.setChkValue(chkValue);

			result.setCrSeqNo(orderNo);
			result.setCrRequestParameter(JSON.toJSONString(data));
			result.setCrRequestUrl(url);
			result.setCrOperateType(CashOperateType.RECHARGE.getType());
			result.setCrMoneyAmount(total);
			result.setCrResponseState(CashOperateState.NOCALLBACK.getState());

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
		case TOCASH: {

			result.setCrMoneyAmount(total);
			result.setCrOperateDate(new Date());
			result.setCrOperateType(CashOperateType.FREEZE.getType());
			result.setCrSeqNo(deals.getInnerSeqNo().getUniqueNo());
			result.setCrRequestMethod("GET");
			List<DealDetail> dealDetails = deals.getDealDetail();
			DealDetail dal = dealDetails.get(0);

			params = dal.getData();

			HuiFuData data = new HuiFuData();
			data.setUrl((String) params.get("url"));
			data.setVersion((String) params.get("version"));
			data.setCmdId((String) params.get("cmdId"));
			data.setOrdId((String) params.get("ordId"));
			data.setUsrCustId((String) params.get("usrCustId"));
			data.setBgRetUrl((String) params.get("bgRetUrl"));
			data.setRetUrl((String) params.get("retUrl"));
			data.setMerCustId((String) params.get("merCustId"));
			data.setCharSet((String) params.get("charSet"));
			data.setMerPriv((String) params.get("merPriv"));

			data.setTransAmt((String) params.get("amount"));
			data.setServFee((String) params.get("servFee"));
			data.setServFeeAcctId((String) params.get("servFeeAcctId"));
			data.setOpenAcctId((String) params.get("openAcctId"));
			data.setRemark((String) params.get("remark"));
			data.setReqExt((String) params.get("reqExt"));
			PlainResult<Map> pojo = chinapnrPayService.cash(data);
			params.put("chkValue", (pojo.getData().get("chkValue")));
			String submitUrl = (String) params.get("url");

			result.setCrOperateType(CashOperateType.TOCASH.getType());
			result.setCrMoneyAmount(total);
			result.setCrRequestParameter(JSON.toJSONString(params));
			result.setCrRequestUrl(submitUrl);
			result.setCrResponseState(CashOperateState.NOCALLBACK.getState());
			break;
		}
		case TRANSFER: {
			List<DealDetail> dealDetails = deals.getDealDetail();
			int listSize = dealDetails.size();
			DealDetail dal = new DealDetail();
			String loanId = deals.getBusinessId().toString();
			InnerSeqNo orgNo = deals.getInnerSeqNo();
			String plaFee ="";
			String insuranceFee ="";
			String debtTransferFee ="";
			String orderNo = String.valueOf(orgNo);
			BigDecimal money = BigDecimal.ZERO;
			for (int i = 0; i < listSize; i++) {
				dal = dealDetails.get(i);
				if (DealDetailType.PLA_FEE.equals(dal.getDealDetailType())) {
					plaFee = dal.getMoneyAmount().toString();

				}
				if (DealDetailType.INSURANCE_FEE
						.equals(dal.getDealDetailType())) {
					insuranceFee = dal.getMoneyAmount().toString();
				}
				if (DealDetailType.DEBT_TRANSFER_FEE.equals(dal
						.getDealDetailType())) {
					debtTransferFee = dal.getMoneyAmount().toString();
				}
			}
			if("".equals(plaFee)){
				plaFee ="0.00";
			}
			if("".equals(insuranceFee)){
				insuranceFee ="0.00";
			}
			if("".equals(debtTransferFee)){
				debtTransferFee ="0.00";
			}
			//手续费|担保费|转让费|标号
			String requestParameter = plaFee+"|"+insuranceFee+"|"+debtTransferFee + "|" + loanId;
			String submitUrl = SystemGetPropeties
					.getStrString("submiturlprefix")
					+ SystemGetPropeties.getStrString("auditSubmitUrl");
			result.setCrOperateType(CashOperateType.UNFREEZE.getType());
			result.setCrSeqNo(orderNo);
			result.setCrMoneyAmount(money);
			result.setCrRequestParameter(requestParameter);
			result.setCrRequestUrl(submitUrl);
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
		case ABORT_BID: {// 未被调用

			String BgRetUrl = SystemGetPropeties
					.getChinaPnrString("notifyurlprefix")
					+ SystemGetPropeties.getChinaPnrString("UnFreezeBgRetUrl");
			// 系统流标操作调用汇付解冻接口
			// String outSeqNo = investOrderDO.getIoOutSeqNo().toString();
			// String ordId = outSeqNo.split(",")[0];//投资时的订单号
			// String ordDate = outSeqNo.split(",")[1];//投资时的订单日期
			// String freezeTrxId = outSeqNo.split(",")[2];//投资时的冻结标识
			HuiFuData data = new HuiFuData();
			data.setUrl(SystemGetPropeties.getChinaPnrString("url").toString());

			String ordId = String.valueOf(System.currentTimeMillis());
			String ordDate = new SimpleDateFormat("yyyyMMdd")
					.format(new Date());
			data.setUrl(SystemGetPropeties.getChinaPnrString("url"));
			data.setVersion(SystemGetPropeties.getChinaPnrString("Version"));
			data.setCmdId("UsrUnFreeze");
			data.setMerCustId(SystemGetPropeties.getChinaPnrString("MerCustId"));
			data.setOrdId(ordId);
			data.setOrdDate(ordDate);
			// data.setTrxId(freezeTrxId.toString());
			data.setRetUrl("");
			data.setBgRetUrl(BgRetUrl);
			data.setMerPriv("");

			result.setCrSeqNo(deals.getInnerSeqNo().getUniqueNo());
			result.setCrRequestParameter(JSON.toJSONString(data));
			result.setCrRequestUrl(BgRetUrl);
			// result.setCrMoneyAmount(new BigDecimal(TransAmt).setScale(2,
			// BigDecimal.ROUND_HALF_UP));
			result.setCrOperateType(CashOperateType.UNFREEZE.getType());
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
		// 批量取出账户数据
		for (DealDetail detail : dealDetails) {
			accountNoSet.add(detail.getPayAccountId());
			accountNoSet.add(detail.getReceiveAccountId());
		}
		List<Account> accounts = account.queryByAccountNos(
				new ArrayList<String>(accountNoSet)).getData();
		// 构建Account map 批量取出area数据
		for (Account ac : accounts) {
			accountMap.put(ac.getAccountNo(), ac);
			areaSet.add(ac.getAccountBankArea());
		}
		Map<String, String> areaMap = areaService
				.queryMapByAreaCodes(new ArrayList<String>(areaSet));
		for (DealDetail detail : dealDetails) {
			batchCount++;
			ItemOfBatchPay item = new ItemOfBatchPay();
			Account toDO = accountMap.get(detail.getPayAccountId());
			// AccountInfoDO toDO =
			// accountInfoDao.findByAccountNo(detail.getReceiveAccountId());
			item.setCertificateNum(toDO.getAccountUserCard());
			item.setTradeAccountName(toDO.getAccountBankName());
			// 对公对私标识，个人用户为对私账户 ，企业和合作伙伴为对公账户
			if (0 == toDO.getAccountUserType().compareTo(UserType.PERSONAL)) {
				item.setTradeAccountType(EasyPayConfig.TradeAccountType.PERSONAL.value);
			} else {
				item.setTradeAccountType(EasyPayConfig.TradeAccountType.PUBLIC.value);
			}
			item.setTradeAmount(detail.getMoneyAmount());
			totalMoney = totalMoney.add(detail.getMoneyAmount());
			// 从area表中查处开户行的地区 现在存的是地区的NO
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
		recordDO.setCrRequestUrl(EasyPayConfig.PAY_URL_PREFIX
				+ EasyPayConfig.TransCodeType.AGENTPAY.surfix);
		return recordDO;
	}

	/**
	 * 构建充值请求记录 不发起请求 有web层调用发起
	 * 
	 * @param deals
	 * @return
	 */
	private CashRecordDO buildReChargeRecord(Deal deals) {
		CashRecordDO recordDO = new CashRecordDO();
		recordDO.setCrResponseState(CashOperateState.NOCALLBACK.getState());
		recordDO.setCrSeqNo(deals.getInnerSeqNo().getUniqueNo());
		recordDO.setCrOperateDate(new Date());
		// 充值 Deal 的dealDetail属性只有一条
		List<DealDetail> dealDetails = deals.getDealDetail();
		if (dealDetails == null || dealDetails.isEmpty())
			throw new BusinessException("交易详情为空");
		DealDetail detail = dealDetails.get(0);
		recordDO.setCrMoneyAmount(detail.getMoneyAmount());
		recordDO.setCrOperateType(DealType.RECHARGE.type);
		recordDO.setCrRequestMethod(RequestMethodType.GET.value);
		AccountInfoDO fromDO = accountInfoDao.findByAccountNo(detail
				.getPayAccountId());
		AccountInfoDO toDO = accountInfoDao.findByAccountNo(detail
				.getReceiveAccountId());
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
		payData.setOut_trade_no(deals.getInnerSeqNo().getUniqueNo()); // 设置交易流水号
		payData.setSeller_email(toDO.getAccountUserEmail()); // 设置商户的邮箱
		String url = EasyPayUtils.buildEasyPayUrl(payData); // 构造请求的URL
		String[] urlARR = url.split("\\?");
		if (urlARR == null || urlARR.length != 2)
			throw new BusinessException("构造的URL 不合法");
		recordDO.setCrRequestParameter(urlARR[1]);
		recordDO.setCrRequestUrl(urlARR[0]); // 在此不做访问的操作 前端拿到url做相应跳转
		return recordDO;
	}

	private UserType queryUserTypeByUserId(int userId) {
		PlainResult<User> userResult = userService.findEntityById(userId);
		if (!userResult.isSuccess() || userResult.getData() == null) {
			throw new BusinessException("用户类型查询失败");
		}

		return userResult.getData().getUserType();
	}

	@Override
	public PlainResult<Map<String, String>> tranfulAll(String seqNo,
			List<DealRecordDO> dealRecords) {
		PlainResult<Map<String, String>> result = new PlainResult<Map<String, String>>();
		String requestList = new String();
		PlainResult<CashRecordDO> cashdao = cashRecordService
				.queryCashRecordBySeqNo(seqNo);
		requestList = cashdao.getData().getCrRequestParameter();
		Integer status = cashdao.getData().getCrOperateType();
		String LoanNoList = "";
		String LoanJsonList = "";
		LoanJsonList = requestList;
		Map<String, String> params = new HashMap<String, String>();
		BigDecimal money = BigDecimal.ZERO;
		switch (status) {
		case 0: {
			result = chinapnrPayService.invest(LoanJsonList);
			break;
		}
		case 1: {
			String[] array = LoanJsonList.split("\\|");
			int length = array.length;
			for(int i =0;i<=length-1;i++){
				String value =array[i];
				result = chinapnrPayService.repayment(array[i]);
				if ("000".equals(result.getData().get("RespCode"))) {
					BigDecimal addmoney = new BigDecimal(result.getData().get("TransAmt"));
					money = money.add(addmoney);
				} else {
					result.setSuccess(false);
					return result;
				}
			}
			params.put("seq", seqNo);
			params.put("money", money.toString());
			result.setData(params);
			break;
		}
		case 3: {
			
			String DivDetails = "";
			String FeeObjFlag = "";
			String SubOrdId = "";
			String SubOrdDate = "";
			String FreezeTrxId = "";
			String ReqExt = "";
			BigDecimal oldPlaFee = BigDecimal.ZERO;
			BigDecimal oldInsuranceFee = BigDecimal.ZERO;
			BigDecimal oldDebtTransferFee = BigDecimal.ZERO;
			
			String MerCustId = SystemGetPropeties
					.getChinaPnrString("MerCustId");
			String BgRetUrl = SystemGetPropeties
					.getChinaPnrString("notifyurlprefix")
					+ SystemGetPropeties.getChinaPnrString("CreditBgRetUrl");
			String[] moneyList = LoanJsonList.split("\\|");
			String LoanId = moneyList[3];
			// 从abc_invest_order获取该原始标号在钱多多平台投资的标号
			List<InvestOrderDO> listLoanSeq = investOrderService
					.queryInvestOrderByTransLoanId(Integer.parseInt(LoanId));
			LoanDO loan = loanDao.findById(Integer.parseInt(LoanId));
			AccountInfoDO accountInfoDO = account.queryByAccountMark(
					loan.getLoanUserId(), loan.getLoanEmpType());
			String InCustId = accountInfoDO.getAccountNo();
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			for (int i = 0; i < listLoanSeq.size(); i++) {
				BigDecimal Fee = BigDecimal.ZERO;// 手续费为0
				InvestOrderDO investOrderDO = listLoanSeq.get(i);
				if (investOrderDO.getIoOrderState() != 3) {
					SubOrdId = investOrderDO.getIoOutSeqNo().split(",")[0];
					SubOrdDate = investOrderDO.getIoOutSeqNo().split(",")[1];
					FreezeTrxId = investOrderDO.getIoOutSeqNo().split(",")[2];
					HuiFuData data = new HuiFuData();
					data.setUrl(SystemGetPropeties.getChinaPnrString("url"));
					data.setVersion(SystemGetPropeties.getChinaPnrString("Version2"));
					data.setCmdId("Loans");
					data.setMerCustId(MerCustId);// 商户客户号
					data.setOrdId(String.valueOf(System.currentTimeMillis()));
					data.setOrdDate(sf.format(new Date()));
					data.setInCustId(InCustId);
					money = money.add(investOrderDO.getIoOrderMoney());
					data.setTransAmt(investOrderDO.getIoOrderMoney().toString());
					accountInfoDO = account.queryByAccountMark(investOrderDO.getIoUserId(),queryUserTypeByUserId(Integer.parseInt(investOrderDO.getIoUserId().toString())).getType());
					data.setOutCustId(accountInfoDO.getAccountNo());
					
					data.setSubOrdId(SubOrdId);
					data.setSubOrdDate(SubOrdDate);
					List<FeeDetail> feeDetailList=new ArrayList<FeeDetail>();
					FeeDetail feeDetail= null;
					if(!"0.00".equals(moneyList[0])){
						String PlaFee = calculateCost(loan.getLoanMoney().toString(),
								investOrderDO.getIoOrderMoney().toString(),
								moneyList[0],listLoanSeq.size(),i,oldPlaFee.toString());
						feeDetail = new FeeDetail();
		    			feeDetail.setDivAcctId(SystemGetPropeties.getChinaPnrString("ServFeeAcctId"));
		    			feeDetail.setDivCustId(MerCustId);//ServFeeAcctId
		    			feeDetail.setDivAmt(String.valueOf(new BigDecimal(PlaFee).setScale(2, BigDecimal.ROUND_HALF_UP)));
		    			feeDetailList.add(feeDetail);
		    			FeeObjFlag = "I";
		    			oldPlaFee =oldPlaFee.add(new BigDecimal(PlaFee));
		    			Fee =Fee.add(new BigDecimal(PlaFee));
					 }
					if(!"0.00".equals(moneyList[1])){
						String InsuranceFee =calculateCost(loan.getLoanMoney().toString(),
								investOrderDO.getIoOrderMoney().toString(),
								moneyList[1],listLoanSeq.size(),i,oldInsuranceFee.toString());
						feeDetail = new FeeDetail();
		    			feeDetail.setDivAcctId(SystemGetPropeties.getChinaPnrString("ServFeeAcctId"));
		    			feeDetail.setDivCustId(MerCustId);//ServFeeAcctId
		    			feeDetail.setDivAmt(String.valueOf(new BigDecimal(InsuranceFee).setScale(2, BigDecimal.ROUND_HALF_UP)));
		    			feeDetailList.add(feeDetail);
		    			FeeObjFlag = "I";
		    			Fee = Fee.add(new BigDecimal(InsuranceFee));
		    			oldInsuranceFee = oldInsuranceFee.add(new BigDecimal(InsuranceFee));
					 }
					if(!"0.00".equals(moneyList[2])){
						String DebtTransferFee = calculateCost(loan.getLoanMoney().toString(),
								investOrderDO.getIoOrderMoney().toString(),
								moneyList[2],listLoanSeq.size(),i,oldDebtTransferFee.toString());
						feeDetail = new FeeDetail();
		    			feeDetail.setDivAcctId(SystemGetPropeties.getChinaPnrString("ServFeeAcctId"));
		    			feeDetail.setDivCustId(MerCustId);//ServFeeAcctId
		    			feeDetail.setDivAmt(String.valueOf(new BigDecimal(DebtTransferFee).setScale(2, BigDecimal.ROUND_HALF_UP)));
		    			feeDetailList.add(feeDetail);
		    			FeeObjFlag = "I";
		    			Fee = Fee.add(new BigDecimal(DebtTransferFee));
		    			oldDebtTransferFee = oldDebtTransferFee.add(new BigDecimal(DebtTransferFee));
					 }
					DivDetails= JSON.toJSONString(feeDetailList).replaceAll("divAcctId", "DivAcctId").replaceAll("divCustId", "DivCustId").replaceAll("divAmt", "DivAmt");
					data.setFee(String.valueOf(Fee.setScale(2, BigDecimal.ROUND_HALF_UP)));
					data.setDivDetails(DivDetails);
					data.setFeeObjFlag(FeeObjFlag);
					data.setIsDefault("N");
					data.setIsUnFreeze("Y");
					data.setUnFreezeOrdId(String.valueOf(System
							.currentTimeMillis()) + 1);
					data.setFreezeTrxId(FreezeTrxId);
					data.setBgRetUrl(BgRetUrl);
					data.setMerPriv(seqNo);
					data.setReqExt(ReqExt);
					result = chinapnrPayService.crdit(JSON.toJSONString(data));
					if ("000".equals(result.getData().get("RespCode"))) {
						InvestOrderDO order = new InvestOrderDO();
						order.setIoInnerSeqNo(investOrderDO.getIoInnerSeqNo());
						order.setIoOrderState(3);
						;
						investOrderService.updateBySeqNo(order);

					} else {
						result.setSuccess(false);
						return result;
					}
				}
			}
			params.put("money", money.toString());
			params.put("seq", seqNo);
			result.setData(params);
			break;
		}
		}

		return result;
	}
	 public static String calculateCost(String allMoney,String investMoney,String money,Integer listSize,Integer oldSize,String oldCost){
	    	double Money = Double.parseDouble(allMoney);
	    	double investCost = Double.parseDouble(investMoney);
	    	double tempresult =investCost/Money;
	    	double resultCost = Double.parseDouble(money);
	    	double oldCostMoney =Double.parseDouble(oldCost);
	    	double  resultMoney =resultCost*tempresult;
	    	if(listSize - oldSize == 1){
	    		resultMoney =resultCost - oldCostMoney;
	    	}
	    	return String.valueOf(resultMoney);
	    }
	@Override
	public PlainResult<Map<String, String>> loanFree(String seqNo,
			List<DealRecordDO> dealRecords) {
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
