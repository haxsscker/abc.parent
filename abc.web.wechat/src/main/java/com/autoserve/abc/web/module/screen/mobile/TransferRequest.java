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
import com.autoserve.abc.dao.dataobject.TransferLoanJDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.search.TransferLoanSearchDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.LoanPeriodUnit;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.web.util.Pagebean;

/**
 * 债权转让
 * 
 * @author Bo.Zhang
 *
 */
public class TransferRequest {

	@Resource
    private TransferLoanService transferLoanService;
	@Resource
	private LoanQueryService loanQueryService;
	@Resource
	private UserService userService;
	@Resource
	private AccountInfoService accountInfoService;
	@Resource
	private InvestService investService;
	@Resource
	private InvestQueryService investQueryService;
	@Resource
    private DealRecordService dealRecordService;
	@Resource
    private DoubleDryService doubleDryService;
	
	public JsonMobileVO execute(Context context, ParameterParser params) throws IOException {
		JsonMobileVO result = new JsonMobileVO();
		
		try {
			String catalog = params.getString("catalog");
			
			if ("1".equals(catalog)) {
				//转让列表
				Integer pageSize = params.getInt("pageSize");
				Integer showPage = params.getInt("showPage");
				
//				TransferLoanSearchDO transferLoanSearchDO = new TransferLoanSearchDO();
//				transferLoanSearchDO.setLoanState(2);
//				PageResult<TransferLoanJDO> pageResult = transferLoanService.queryListByParam(transferLoanSearchDO, new PageCondition(showPage, pageSize));
//				List<TransferLoanJDO> list = pageResult.getData();
				
				Map<String, Object> objMap = new HashMap<String, Object>();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();
				
				Map<String, Object> loanMap = new HashMap<String, Object>();
				
//				for (TransferLoanJDO transferLoanJDO : list) {
//					loanMap = new HashMap<String, Object>();
//					loanMap.put("transferId", transferLoanJDO.getTlOriginId());
//					loanMap.put("loanTitle", transferLoanJDO.getLoanNo());
//					loanMap.put("transferMoney", transferLoanJDO.getLoanMoney());
//					loanMap.put("transferRate", transferLoanJDO.getLoanRate());
//					loanMap.put("transferPeriod", transferLoanJDO.getLoanPeriod() + LoanPeriodUnit.valueOf(transferLoanJDO.getLoanPeriodType()).getPrompt());
////					loanMap.put("transferProgress", transferLoanJDO.getcurrentInves.divide(transferLoanJDO.gettransferMoney, 2).doubleValue() * 100);
//					loanMap.put("transferProgress", 50);
//					loanList.add(loanMap);
//				}
				
				TransferLoanSearchDO pojo = new TransferLoanSearchDO();
				if(pojo.getTransferLoanStates()==null || pojo.getTransferLoanStates().size()==0){
					  pojo.setTransferLoanStates(Arrays.asList(TransferLoanState.TRANSFERING.state,TransferLoanState.FULL_WAIT_REVIEW.state
							  ,TransferLoanState.FULL_REVIEW_PASS.state,TransferLoanState.FULL_REVIEW_FAIL.state
							  ,TransferLoanState.BID_CANCELED.state,TransferLoanState.MONEY_TRANSFERING.state));
				  }
//				  int currentPage = params.getInt("currentPage");
//				  int pageSize  = params.getInt("pageSize");
//				  if(currentPage==0)
//					  currentPage=1;
//				  		pageSize=10;
				  		
				  PageCondition pageCondition = new PageCondition(showPage,pageSize);
				  PageResult<TransferLoanJDO> pageResult=transferLoanService.queryListByParam(pojo, pageCondition);
				  Pagebean<TransferLoanJDO> pagebean = new Pagebean<TransferLoanJDO>(showPage, pageSize, pageResult.getData(), pageResult.getTotalCount());
				
				for (TransferLoanJDO transferLoanJDO : pagebean.getRecordList()) {
					loanMap = new HashMap<String, Object>();
					loanMap.put("transferId", transferLoanJDO.getTlId());
					loanMap.put("loanTitle", transferLoanJDO.getLoanNo());
					loanMap.put("transferMoney", transferLoanJDO.getTlTransferMoney());
					loanMap.put("transferRate", transferLoanJDO.getLoanRate());
					loanMap.put("transferPeriod", transferLoanJDO.getLoanPeriod() + LoanPeriodUnit.valueOf(transferLoanJDO.getLoanPeriodType()).getPrompt());
					loanMap.put("transferProgress", transferLoanJDO.getTlCurrentValidInvest().divide(transferLoanJDO.getTlTransferMoney(), 2).doubleValue() * 100);
//					loanMap.put("transferProgress", 50);
					loanList.add(loanMap);
				}
				
				objMap.put("pageCount", pagebean.getPageCount());
				objMap.put("list", JSON.toJSON(loanList));
				
				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("2".equals(catalog)) {
				//转让详情
//				Integer transferId = params.getInt("transferId");
//				
//				Loan loanParam = new Loan();
//				loanParam.setLoanId(transferId);
//				Loan loan = loanQueryService.queryByParam(loanParam).getData();
//
//				Map<String, Object> objMap = new HashMap<String, Object>();
//				objMap.put("transferId", loan.getLoanId());
//				objMap.put("loanTitle", loan.getLoanNo());
//				objMap.put("transferStartTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(loan.getLoanCreatetime()));
//				objMap.put("transferMoney", loan.getLoanMoney());
//				objMap.put("transferRate", loan.getLoanRate());
//				objMap.put("transferPeriod", new SimpleDateFormat("yyyy-MM-dd").format(loan.getLoanInvestEndtime()));
//				objMap.put("loanPayType", loan.getLoanPayType().getPrompt());
//				objMap.put("currentInvest", loan.getLoanCurrentInvest());
//				objMap.put("currentValidInvest", loan.getLoanMoney().subtract(loan.getLoanCurrentInvest()));
				
				Integer transferId = params.getInt("transferId");
				
				PlainResult<TransferLoan> transferPlainResult = transferLoanService.queryById(transferId);
				TransferLoan transferLoan = transferPlainResult.getData();
				PlainResult<Loan> loanPlainResult = loanQueryService.queryById(transferLoan.getOriginId());
				Loan loan = loanPlainResult.getData();
				
				Map<String, Object> objMap = new HashMap<String, Object>();
				objMap.put("transferId", transferLoan.getId());
				objMap.put("loanTitle", loan.getLoanNo());
				objMap.put("transferStartTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(transferLoan.getCreatetime()));
				objMap.put("loanPayType", loan.getLoanPayType().getPrompt());
				objMap.put("transferMoney", transferLoan.getTransferMoney());
				objMap.put("transferRate", transferLoan.getTransferRate());
//				objMap.put("transferPeriod", new SimpleDateFormat("yyyy-MM-dd").format(transferLoan.getInvestEndtime()));
				objMap.put("transferPeriod", loan.getLoanPeriod() + LoanPeriodUnit.valueOf(loan.getLoanPeriodUnit().getUnit()).getPrompt());
				objMap.put("currentInvest", transferLoan.getCurrentValidInvest());
				objMap.put("currentValidInvest", transferLoan.getTransferMoney().subtract(transferLoan.getCurrentValidInvest()));
				
				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("3".equals(catalog)) {
				//转让收购
				Integer userId = params.getInt("userId");
				Integer transferId = params.getInt("transferId");
				Double investMoney = params.getDouble("investCount");
				String dealPwd = params.getString("dealPwd");

				if (userId == 0) {
					result.setResultCode("201");
					result.setResultMessage("请求用户id不能为空");
					return result;
				} else if (transferId == 0) {
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
					PlainResult<UserDO> userDoResult = userService
							.findById(user.getUserId());
					if (dealPwd == null || !CryptUtils.md5(dealPwd).equals(userDoResult.getData().getUserDealPwd())) {
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
				
				BidType bidType = BidType.TRANSFER_LOAN;
//				if (loanId != null && flagLoan == 1) { // 普通标
//					bidType = BidType.COMMON_LOAN;
//				}
//				if (loanId != null && flagLoan == 2) { // 转让标
//					bidType = BidType.TRANSFER_LOAN;
//				}

//				int userId = user.getUserId();
//				if (bidType == null) {
//					bidType = BidType.BUY_LOAN;
//				}

//				int bidId = params.getInt("loanId");
				Invest inv = new Invest();
				inv.setUserId(userId);
				inv.setBidType(bidType); // 设置标的类型

//				if (BidType.COMMON_LOAN.equals(bidType)) {
				inv.setBidId(transferId);
//				inv.setOriginId(transferId);
//				} else {
//					inv.setBidId(loanId);
//				}

//				Double investMoney = params.getDouble("investedMoney");
//				if (investMoney != null) {
					inv.setInvestMoney(BigDecimal.valueOf(investMoney));
//				}

				PlainResult<Integer> investCreateResult = investService.createInvest(inv);
				if (!investCreateResult.isSuccess()) {
					result.setResultCode("201");
					result.setResultMessage(investCreateResult.getMessage());
					return result;
				}

				// 支付
//				if (BidType.BUY_LOAN.equals(inv.getBidType())) {
//					return JsonBaseVO.SUCCESS;
//				} else {
				PlainResult<Invest> investQueryResult = investQueryService.queryById(investCreateResult.getData());
				if (!investQueryResult.isSuccess()) {
					result.setResultCode("201");
					result.setResultMessage(investCreateResult.getMessage());
					return result;
				}

				BaseResult invokeResult = dealRecordService.invokePayment(investQueryResult.getData().getInnerSeqNo());
				if(!invokeResult.isSuccess()) {
					result.setResultCode("201");
					result.setResultMessage(investCreateResult.getMessage());
					return result;
				}
				result.setResultCode("200");
				result.setResultMessage("认购成功");
				return result;
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
