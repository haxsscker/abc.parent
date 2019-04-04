package com.autoserve.abc.web.module.screen.mobile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.tools.generic.NumberTool;
import org.joda.time.DateTime;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchJDO;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyCreditClean;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyCreditReceived;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyCreditTender;
import com.autoserve.abc.service.biz.entity.FeeSetting;
import com.autoserve.abc.service.biz.entity.IncomePlan;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.enums.FeeType;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.intf.sys.FeeSettingService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.Arith;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.service.util.DateUtil;

public class TransferManage {

	@Resource
	private InvestQueryService investQueryService;
	@Resource
	private IncomePlanService incomePlanService;
	@Resource
	private LoanService loanService;
	@Resource
	private TransferLoanService transferLoanService;
	@Resource
	private FeeSettingService feeSettingService;
	@Resource
	private SysConfigService sysConfigService;
	@Resource
	private LoanQueryService loanQueryService;

	@Resource
	private UserService userService;
	
	java.text.DecimalFormat df=new java.text.DecimalFormat("0.00");
	public JsonMobileVO execute(Context context, ParameterParser params)
			throws IOException {
		JsonMobileVO result = new JsonMobileVO();

		try {
			String catalog = params.getString("catalog");
			Integer userId = params.getInt("userId");

			Integer pageSize = params.getInt("pageSize");
			Integer showPage = params.getInt("showPage");

			if (userId == null) {
				result.setResultCode("201");
				result.setResultMessage("请求用户id不能为空");
				return result;
			}

			if ("1".equals(catalog)) {
				// 收款中的项目
				InvestSearchJDO searchDO = new InvestSearchJDO();
				searchDO.setUserId(userId);
				PageResult<MyCreditReceived> pageResult = investQueryService
						.queryMyCreditReceived(searchDO, new PageCondition(
								showPage, pageSize));
				List<MyCreditReceived> list = pageResult.getData();

				Map<String, Object> objMap = new HashMap<String, Object>();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();

				Map<String, Object> loanMap = new HashMap<String, Object>();

				for (MyCreditReceived myCreditReceived : list) {
					loanMap = new HashMap<String, Object>();
					loanMap.put("loanId", myCreditReceived.getLoanId());
					loanMap.put("loanTitle", myCreditReceived.getTransferLoanNo());
					loanMap.put("loanRate", myCreditReceived.getLoanRate());
					loanMap.put("transferMoney",
							myCreditReceived.getTransferLoanMoney());
					loanMap.put("investTime", DateUtil.formatDate(myCreditReceived.getInvestDate()));
					loanMap.put("endDate", DateUtil.formatDate(myCreditReceived.getEndDate()));
					loanMap.put("cleanMoney", myCreditReceived.getCleanMoney());
					loanMap.put("receivedMoney",
							myCreditReceived.getReceivedMoney());
					loanMap.put("validTransferMoney",
							myCreditReceived.getTransferMoney());
					loanMap.put("investId", myCreditReceived.getInvestId());
					loanMap.put("transferState", myCreditReceived.getTransferState());
					loanList.add(loanMap);
				}
				objMap.put("pageCount", pageResult.getTotalCount());
				objMap.put("list", JSON.toJSON(loanList));

				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			}
			// 转让处理
			else if ("2".equals(catalog)) {
				return transferDeal(params);
			} else if ("3".equals(catalog)) {
				// 投标中的债权
				// /mobile/transferManage.json?catalog=3&userId=133&showPage=1&pageSize=10
				InvestSearchJDO searchDO = new InvestSearchJDO();
				searchDO.setUserId(userId);
				PageResult<MyCreditTender> pageResult = investQueryService
						.queryMyCreditTender(searchDO, new PageCondition(
								showPage, pageSize));
				List<MyCreditTender> list = pageResult.getData();

				Map<String, Object> objMap = new HashMap<String, Object>();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();

				Map<String, Object> loanMap = new HashMap<String, Object>();

				for (MyCreditTender myCreditTender : list) {
					loanMap = new HashMap<String, Object>();
					loanMap.put("loanId", myCreditTender.getLoanId());
					loanMap.put("loanTitle", myCreditTender.getTransferLoanNo());
					loanMap.put("loanRate", myCreditTender.getLoanRate());
					loanMap.put("transferMoney",
							myCreditTender.getTransferLoanMoney());
					loanMap.put("validTransferMoney",
							myCreditTender.getTransferMoney());
					loanMap.put("transferProgress", Arith.calcPercent(myCreditTender.getCurrentMoney(), myCreditTender.getTransferLoanMoney()).intValue());
					loanMap.put("endDate", myCreditTender.getEndDate());
					loanMap.put("currentMoney", myCreditTender.getCurrentMoney());
					loanMap.put("createDate", DateUtil.formatDate(myCreditTender.getCreateDate()));
					loanList.add(loanMap);
				}
				objMap.put("pageCount", pageResult.getTotalCount());
				objMap.put("list", JSON.toJSON(loanList));

				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("4".equals(catalog)) {
				// 已完成的债权
				InvestSearchJDO searchDO = new InvestSearchJDO();
				searchDO.setUserId(userId);
				PageResult<MyCreditClean> pageResult = investQueryService
						.queryMyCreditClean(searchDO, new PageCondition(
								showPage, pageSize));
				List<MyCreditClean> list = pageResult.getData();

				Map<String, Object> objMap = new HashMap<String, Object>();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();

				Map<String, Object> loanMap = new HashMap<String, Object>();

				for (MyCreditClean myCreditClean : list) {
					loanMap = new HashMap<String, Object>();
					loanMap.put("loanId", myCreditClean.getLoanId());
					loanMap.put("loanTitle", myCreditClean.getTransferLoanNo());
					loanMap.put("loanRate", myCreditClean.getLoanRate());
					loanMap.put("creditTotalMoney",
							myCreditClean.getCreditTotalMoney());
					loanMap.put("transferMoney",
							myCreditClean.getTransferMoney());
					loanMap.put("transferTime", DateUtil.formatDate(myCreditClean.getTransferDate()));
					loanMap.put("endDate", DateUtil.formatDate(myCreditClean.getEndDate()));
					loanList.add(loanMap);
				}
				objMap.put("pageCount", pageResult.getTotalCount());
				objMap.put("list", JSON.toJSON(loanList));

				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			}
			// 转让前费用显示
			else if ("5".equals(catalog)) {
				return transfer(params);
			} else {
				result.setResultCode("404");
				result.setResultMessage("catalog不存在");
			}
		} catch (Exception e) {
			result.setResultCode("201");
			result.setResultMessage("请求异常");
		}

		return result;
	}

	/**
	 * 转让处理
	 * /mobile/transferManage.json?catalog=2&userId=132&originId=388&investId
	 * =41&launchMoney=1000.66&dealPsw=123456
	 * 
	 * @param params
	 * @return
	 */
	private JsonMobileVO transferDeal(ParameterParser params) {
		JsonMobileVO vo = new JsonMobileVO();
		try {
			int userId = params.getInt("userId");
			int originId = params.getInt("originId");// 原始标id
			int investId = params.getInt("investId");// 投资id
			// BidType bidType = BidType.valueOf(params.getInt("bidType"));
			double launchMoney = params.getDouble("launchMoney");// 转让金额
			String dealPsw = params.getString("dealPsw");
			// 发起转让
			return launchTransfer(userId, originId, launchMoney, investId,
					dealPsw);
		} catch (Exception e) {
			e.printStackTrace();
			vo.setResultMessage(e.getMessage());
		}
		vo.setResultCode("500");
		vo.setResultMessage("转让失败");
		return vo;
	}

	/**
	 * 转让前费用显示 /mobile/transferManage.json?catalog=5&investId=45&userId=132
	 * 
	 * @param params
	 * @return
	 */
	private JsonMobileVO transfer(ParameterParser params) {
		JsonMobileVO vo = new JsonMobileVO();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		vo.setResult(resultMap);
		Integer investId = params.getInt("investId");// 投资id
		Integer bidType = 1; // 标类型 普通标0，转让标1
		Integer userId = params.getInt("userId");// 用户id
		PlainResult<Invest> invests = investQueryService.queryById(investId);
		if (!invests.isSuccess()) {
			vo.setResultCode(invests.getCode() + "");
			vo.setResultMessage(invests.getMessage());
			return vo;
		}
		// 债权总价值
		IncomePlan incomePlan = new IncomePlan();
		incomePlan.setBeneficiary(userId);
		incomePlan.setInvestId(investId);
		PlainResult<BigDecimal> palinResult = incomePlanService
				.queryBondMoney(incomePlan);
		if (!palinResult.isSuccess()) {
			vo.setResultCode(palinResult.getCode() + "");
			vo.setResultMessage(palinResult.getMessage());
			return vo;
		}
		// 转让手续费
		BigDecimal counterFee = new BigDecimal(0);
		Loan loan = null;
		if (bidType != null) {
			switch (invests.getData().getBidType()) {
			case COMMON_LOAN:
				loan = queryOriginLoan(invests.getData().getBidId());
				break;
			case TRANSFER_LOAN:
				PlainResult<TransferLoan> transferLoanResult = transferLoanService
						.queryById(invests.getData().getBidId());
				if (transferLoanResult.getData() != null) {
					loan = queryOriginLoan(transferLoanResult.getData()
							.getOriginId());
				}
				break;
			default:
				break;
			}
		}
		if (loan != null && loan.getLoanCategory() != null) {
			PlainResult<FeeSetting> feeSettingResult = feeSettingService
					.queryByFeeTypeLoanCategory(FeeType.TRANSFER_FEE,
							loan.getLoanCategory(), palinResult.getData());
			if (feeSettingResult.getData() != null) {
				counterFee = calculateFee(feeSettingResult.getData(),
						palinResult.getData());
			}

		}
		resultMap.put("transferTotal", df.format(palinResult.getData()));// 债权总价值
		resultMap.put("transferFee", df.format(counterFee));// 转让手续费
		resultMap.put("transferIncome",
				df.format(palinResult.getData().subtract(counterFee)));// 转让收益额
		return vo;
	}
	
//	public static void main(String[] args) {
//		double   c=3.154215;
//		   java.text.DecimalFormat myformat=new java.text.DecimalFormat("0.00");
//		   String str = myformat.format(new BigDecimal("23423523.235"));    
//		System.out.println(str);
//	}

	private JsonMobileVO launchTransfer(int userId, int originId,
			double launchMoney, int investId, String dealPsw) {
		JsonMobileVO result = new JsonMobileVO();
		PlainResult<UserDO> userResult = userService.findById(userId);
		if (!userResult.isSuccess()) {
			result.setResultCode(userResult.getCode() + "");
			result.setResultMessage(userResult.getMessage());
			return result;
		}
		if (dealPsw == null
				|| !CryptUtils.md5(dealPsw).equals(
						userResult.getData().getUserDealPwd())) {
			result.setResultCode("201");
			result.setResultMessage("密码错误");
			return result;
		}
		// 首次债权转让距放款周期判断
		PlainResult<Loan> loanResult = loanQueryService.queryById(originId);

		PlainResult<SysConfig> periodResult = this.sysConfigService
				.querySysConfig(SysConfigEntry.LOAN_TRANSFER_PERIOD);

		PlainResult<SysConfig> periodTypeResult = this.sysConfigService
				.querySysConfig(SysConfigEntry.LOAN_TRANSFER_PERIOD_TYPE);

		int flagDay = 0;

		DateTime fullTransferedtime = new DateTime(loanResult.getData()
				.getLoanFullTransferedtime());

		if (periodTypeResult.getData().getConfValue().equals("1")) {

			flagDay = fullTransferedtime.plusDays(
					Integer.valueOf(periodResult.getData().getConfValue()))
					.compareTo(DateTime.now());
			if (flagDay > 0) {
				result.setResultCode("201");
				result.setResultMessage("距离划转放款日期没到"
						+ periodResult.getData().getConfValue() + "日");
				return result;
			}

		} else if (periodTypeResult.getData().getConfValue().equals("2")) {

			flagDay = fullTransferedtime.plusMonths(
					Integer.valueOf(periodResult.getData().getConfValue()))
					.compareTo(DateTime.now());
			if (flagDay > 0) {
				result.setResultCode("201");
				result.setResultMessage("距离划转放款日期没到"
						+ periodResult.getData().getConfValue() + "月");
				return result;
			}
		}

		
		TransferLoan pojo = new TransferLoan();
		pojo.setUserId(userId);
		pojo.setInvestId(investId);
		pojo.setOriginId(originId);
		pojo.setTransferMoney(BigDecimal.valueOf(launchMoney));

		pojo.setTransferDiscountFee(BigDecimal.valueOf(20)); // 转让折让费，不做控制
		pojo.setTransferTotal(BigDecimal.valueOf(100)); // 转让债权总额，不做控制
		pojo.setTransferPeriod(1); // 转让期数，不做控制

		PlainResult<Integer> plainResult = transferLoanService
				.createTransferLoan(pojo);
		if (plainResult.isSuccess()) {
			result.setResultCode("200");
			result.setResultMessage("转让成功");
		} else {
			result.setResultCode("500");
			result.setResultMessage(plainResult.getMessage());
		}
		return result;
	}

	// 获取转让标所对应的原始标
	private Loan queryOriginLoan(Integer id) {
		ListResult<Loan> listsLoan = loanService.queryByIds(Arrays.asList(id),
				null);
		if (listsLoan != null && listsLoan.getData() != null
				&& listsLoan.getData().size() != 0) {
			return listsLoan.getData().get(0);
		}
		return null;
	}

	/*
	 * 计算转让手续费（转出金额*费率）
	 */
	private BigDecimal calculateFee(FeeSetting feeSetting, BigDecimal base) {
		if (feeSetting == null) {
			return null;
		}

		switch (feeSetting.getChargeType()) {
		case BY_DEAL: {
			return feeSetting.getAccurateAmount();
		}
		case BY_RATIO: {
			double fee = base.doubleValue() * feeSetting.getRate() / 100;
			return new BigDecimal(fee).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		default:
			return new BigDecimal(0);
		}

	}

}
