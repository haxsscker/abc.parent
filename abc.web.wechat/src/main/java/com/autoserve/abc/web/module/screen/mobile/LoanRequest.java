package com.autoserve.abc.web.module.screen.mobile;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchDO;
import com.autoserve.abc.dao.dataobject.search.LoanSearchDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.web.util.Pagebean;

/**
 * 项目投资
 * 
 * @author tabor
 * 
 */
public class LoanRequest {

	@Resource
	private LoanQueryService loanQueryService;
	@Resource
	private UserService userService;
	@Resource
	private AccountInfoService accountInfoService;
	@Resource
	private InvestQueryService investQueryService;
	@Resource
	private InvestService investService;
	@Resource
	private DealRecordService dealRecordService;
	@Resource
    private DoubleDryService doubleDryService;

	public JsonMobileVO execute(Context context, ParameterParser params)
			throws IOException {
		JsonMobileVO result = new JsonMobileVO();

		try {
			String catalog = params.getString("catalog");

			if ("1".equals(catalog)) {
				// 投资列表
				Integer prodId = params.getInt("prodId");
				Integer pageSize = params.getInt("pageSize");
				Integer showPage = params.getInt("showPage");
//
//				PageCondition pageCondition = new PageCondition(showPage, pageSize);
//				
//				Loan loan = new Loan();
//				loan.setLoanState(LoanState.BID_INVITING); // 招标中
//				PageResult<Loan> result_investing = this.loanQueryService
//						.queryLoanListByParam(loan, pageCondition);
//				loan.setLoanState(LoanState.FULL_WAIT_REVIEW); // 满标待审核
//				PageResult<Loan> result_wait = this.loanQueryService
//						.queryLoanListByParam(loan, pageCondition);
//				loan.setLoanState(LoanState.FULL_REVIEW_PASS); // 满标审核通过
//				PageResult<Loan> result_pass = this.loanQueryService
//						.queryLoanListByParam(loan, pageCondition);
//				loan.setLoanState(LoanState.REPAYING); // 还款中
//				PageResult<Loan> result_repaying = this.loanQueryService
//						.queryLoanListByParam(loan, pageCondition);
//
//				List<Loan> loanListResult = new ArrayList<Loan>();
//				List<List<Loan>> data = new ArrayList<List<Loan>>();
//				data.add(result_investing.getData());
//				data.add(result_wait.getData());
//				data.add(result_pass.getData());
//				data.add(result_repaying.getData());
//				for (List<Loan> list : data) {
//					for (Loan loan2 : list) {
//						loanListResult.add(loan2);
//					}
//				}
//				Pagebean<Loan> pagebean = new Pagebean<Loan>(showPage, pageSize, loanListResult, loanListResult.size());
//
				Map<String, Object> objMap = new HashMap<String, Object>();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();
//
				Map<String, Object> loanMap = new HashMap<String, Object>();
//
//				for (Loan loan3 : pagebean.getRecordList()) {
//					loanMap = new HashMap<String, Object>();
//					loanMap.put("loanId", loan3.getLoanId());
//					loanMap.put("loanTitle", loan3.getLoanNo());
//					loanMap.put("loanMoney", loan3.getLoanMoney());
//					loanMap.put("loanRate", loan3.getLoanRate());
//					loanMap.put("loanPeriod", loan3.getLoanPeriod() + loan3.getLoanPeriodUnit().getPrompt());
//					loanMap.put("loanProgress", loan3.getLoanCurrentValidInvest()
//									.divide(loan3.getLoanMoney(), 2)
//									.doubleValue() * 100);
//					loanList.add(loanMap);
//				}
				
				LoanSearchDO searchDO = new LoanSearchDO();
				searchDO.setLoanCategory(prodId);
//				if(loanState()==null || loanState().size()==0){
					searchDO.setLoanState(Arrays.asList(LoanState.BID_INVITING.state,LoanState.FULL_WAIT_REVIEW.state,LoanState.FULL_REVIEW_PASS.state
							,LoanState.FULL_REVIEW_FAIL.state,LoanState.BID_CANCELED.state,LoanState.MONEY_TRANSFERING.state,LoanState.REPAYING.state));
//				}
//				int currentPage = params.getInt("currentPage");
//				int pageSize  = params.getInt("pageSize");
//				if(currentPage==0){
//					currentPage=1;
//					pageSize=10;
//				}			 
				PageCondition pageCondition = new PageCondition(showPage,pageSize);
				PageResult<Loan> pageResult= this.loanQueryService.queryLoanListBySearchParam(searchDO, pageCondition);
				Pagebean<Loan> pagebean = new Pagebean<Loan>(showPage, pageSize, pageResult.getData(), pageResult.getTotalCount());
				//百分比
				Map<Integer,BigDecimal> resultLoanListMap = new HashMap<Integer,BigDecimal>();		  
				for(Loan temp : pagebean.getRecordList()){
					BigDecimal percent = temp.getLoanCurrentValidInvest().divide(temp.getLoanMoney(), 50, 
							BigDecimal.ROUND_HALF_UP)
					.multiply(new BigDecimal(100));
					resultLoanListMap.put(temp.getLoanId(), percent);
				}
				
				for (Loan loan3 : pagebean.getRecordList()) {
				loanMap = new HashMap<String, Object>();
				loanMap.put("loanId", loan3.getLoanId());
				loanMap.put("loanTitle", loan3.getLoanNo());
				loanMap.put("loanMoney", loan3.getLoanMoney());
				loanMap.put("loanRate", loan3.getLoanRate());
				loanMap.put("loanPeriod", loan3.getLoanPeriod() + loan3.getLoanPeriodUnit().getPrompt());
				loanMap.put("loanProgress", loan3.getLoanCurrentValidInvest()
								.divide(loan3.getLoanMoney(), 2)
								.doubleValue() * 100);
				loanList.add(loanMap);
			}

				objMap.put("pageCount", pagebean.getPageCount());
				objMap.put("list", JSON.toJSON(loanList));

				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("2".equals(catalog)) {
				// 投资详情
				Integer loanId = params.getInt("loanId");

				Loan loanParam = new Loan();
				loanParam.setLoanId(loanId);
				Loan loan = loanQueryService.queryByParam(loanParam).getData();

				Map<String, Object> objMap = new HashMap<String, Object>();
				objMap.put("loanId", loan.getLoanId());
				objMap.put("loanTitle", loan.getLoanNo());
				objMap.put("loanStartTime", new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss").format(loan.getLoanCreatetime()));
				objMap.put("loanMoney", loan.getLoanMoney());
				objMap.put("loanRate", loan.getLoanRate());
				objMap.put("effectiveDate", new SimpleDateFormat("yyyy-MM-dd")
						.format(loan.getLoanInvestEndtime()));
				objMap.put("loanPayType", loan.getLoanPayType().getPrompt());
				objMap.put("currentInvest", loan.getLoanCurrentValidInvest());
				objMap.put(
						"currentValidInvest",
						loan.getLoanMoney().subtract(
								loan.getLoanCurrentValidInvest()));

				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("3".equals(catalog)) {
				// 投资记录
				Integer loanId = params.getInt("loanId");
				Integer pageSize = params.getInt("pageSize");
				Integer showPage = params.getInt("showPage");

				InvestSearchDO searchDO = new InvestSearchDO();
				searchDO.setOriginId(loanId);
				PageResult<Invest> pageResult = investQueryService
						.queryInvestList(searchDO, new PageCondition(showPage,
								pageSize));
				List<Invest> list = pageResult.getData();

				Map<String, Object> objMap = new HashMap<String, Object>();
				Map<String, Object> loanMap = new HashMap<String, Object>();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();

				for (Invest invest : list) {
					loanMap = new HashMap<String, Object>();
					loanMap.put("investor",
							userService.findEntityById(invest.getUserId())
									.getData().getUserName());
					loanMap.put("investTime", new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss").format(invest
							.getCreatetime()));
					loanMap.put("investMoney", invest.getInvestMoney());
					loanList.add(loanMap);
				}

				// for (int i = 0; i < 1; i++) {
				// loanMap = new HashMap<String, Object>();
				// loanMap.put("investor", "xxxx");
				// loanMap.put("investTime", "2015-12-15 17:08:20");
				// loanMap.put("investAmount", "180.00");
				// loanList.add(loanMap);
				// }

				objMap.put("pageCount", pageResult.getTotalCount());
				objMap.put("list", JSON.toJSON(loanList));

				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("4".equals(catalog)) {
				// 还款计划
				Map<String, Object> objMap = new HashMap<String, Object>();
				Map<String, Object> loanMap = new HashMap<String, Object>();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();

				for (int i = 0; i < 11; i++) {
					loanMap = new HashMap<String, Object>();
					loanMap.put("planDate", "2015-12-15 17:08:20");
					loanMap.put("interest", "5.00");
					loanMap.put("pricipal", "200.00");
					loanList.add(loanMap);
				}

				objMap.put("pageCount", "1");
				objMap.put("list", JSON.toJSON(loanList));

				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("5".equals(catalog)) {
				// 投资操作
				Integer userId = params.getInt("userId");
				Integer loanId = params.getInt("loanId");
				Double investMoney = params.getDouble("investAmount");
				String dealPwd = params.getString("dealPwd");

				if (userId == 0) {
					result.setResultCode("201");
					result.setResultMessage("请求用户id不能为空");
					return result;
				} else if (loanId == 0) {
					result.setResultCode("201");
					result.setResultMessage("请求项目id不能为空");
					return result;
				} else if (investMoney <= 0) {
					result.setResultCode("201");
					result.setResultMessage("请输入投资金额");
					return result;
				} else if (dealPwd == null || "".equals(dealPwd)) {
					result.setResultCode("201");
					result.setResultMessage("请输入交易密码");
					return result;
				}
				// 检查用户信息
				if (!MobileHelper.check(userService, userId, result)) {
					return result;
				}

				// 投资
				User user = userService.findEntityById(userId).getData();
				if (user != null) { // 校验交易密码
					PlainResult<UserDO> userDoResult = userService.findById(user.getUserId());
					if (dealPwd == null || !CryptUtils.md5(dealPwd).equals(
									userDoResult.getData().getUserDealPwd())) {
						result.setResultCode("201");
						result.setResultMessage("支付密码不正确!");
						return result;
					}
				}
				//判断账户余额
				UserIdentity userIdentity = new UserIdentity();
				userIdentity.setUserId(user.getUserId());
				if (user.getUserType() == null || user.getUserType().getType() == 1) {
					user.setUserType(UserType.PERSONAL);
				} else {
					user.setUserType(UserType.ENTERPRISE);
				}
				userIdentity.setUserType(user.getUserType());
				Account account = accountInfoService.queryByUserId(userIdentity).getData();
				String accountMark = account.getAccountMark();

				// 网贷平台子账户可用余额|总可用余额(子账户可用余额+公共账户可用余额)|子账户冻结余额”（例:100.00|200.00|10.00）
				Double[] accountBacance = doubleDryService.queryBalance(accountMark, "1");
				if(investMoney - accountBacance[1]>0){
					result.setResultCode("200");
					result.setResultMessage("可用余额不足，请充值!");
					result.setResult("chongzhi");
					return result;
				}

				BidType bidType = BidType.COMMON_LOAN;
				// if (loanId != null && flagLoan == 1) { // 普通标
				// bidType = BidType.COMMON_LOAN;
				// }
				// if (loanId != null && flagLoan == 2) { // 转让标
				// bidType = BidType.TRANSFER_LOAN;
				// }

				// int userId = user.getUserId();
				// if (bidType == null) {
				// bidType = BidType.BUY_LOAN;
				// }

				// int bidId = params.getInt("loanId");
				Invest inv = new Invest();
				inv.setUserId(userId);
				inv.setBidType(bidType); // 设置标的类型

				// if (BidType.COMMON_LOAN.equals(bidType)) {
				inv.setBidId(loanId);
				inv.setOriginId(loanId);
				// } else {
				// inv.setBidId(loanId);
				// }

				// Double investMoney = params.getDouble("investedMoney");
				// if (investMoney != null) {
				inv.setInvestMoney(BigDecimal.valueOf(investMoney));
				// }

				PlainResult<Integer> investCreateResult = investService
						.createInvest(inv);
				if (!investCreateResult.isSuccess()) {
					result.setResultCode("201");
					result.setResultMessage(investCreateResult.getMessage());
					return result;
				}

				// 支付
				// if (BidType.BUY_LOAN.equals(inv.getBidType())) {
				// return JsonBaseVO.SUCCESS;
				// } else {
				PlainResult<Invest> investQueryResult = investQueryService
						.queryById(investCreateResult.getData());
				if (!investQueryResult.isSuccess()) {
					result.setResultCode("201");
					result.setResultMessage(investCreateResult.getMessage());
					return result;
				}

				BaseResult invokeResult = dealRecordService
						.invokePayment(investQueryResult.getData()
								.getInnerSeqNo());
				if (!invokeResult.isSuccess()) {
					result.setResultCode("201");
					result.setResultMessage(investCreateResult.getMessage());
					return result;
				}
				result.setResultCode("200");
				result.setResultMessage("投资成功");
				return result;
				// }
			} else {
				result.setResultCode("201");
				result.setResultMessage("请求参数异常");
			}
		} catch (Exception e) {
			result.setResultCode("201");
			result.setResultMessage("请求异常");
		}
		return result;
	}
}
