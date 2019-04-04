package com.autoserve.abc.web.module.screen.mobile;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.common.PageCondition.Order;
import com.autoserve.abc.dao.dataobject.search.InvestSearchJDO;
import com.autoserve.abc.dao.dataobject.stage.myborrow.MyBorrowClean;
import com.autoserve.abc.dao.dataobject.stage.myborrow.MyBorrowReceived;
import com.autoserve.abc.dao.dataobject.stage.myborrow.MyBorrowTender;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.enums.LoanPeriodUnit;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.PayType;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.loan.repay.RepayService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;

/**
 * 借款管理
 * 
 * @author Bo.Zhang
 *
 */
public class LoanManage {
	
	@Resource
	private LoanService loanService;
	
	@Resource
	private PaymentPlanService paymentPlanService;
	
	@Resource
	private RepayService rapayService;

	public JsonMobileVO execute(Context context, ParameterParser params) throws IOException {
		JsonMobileVO result = new JsonMobileVO();
		
		try {
			String catalog = params.getString("catalog");
			Integer userId = params.getInt("userId");
			
			Integer pageSize = params.getInt("pageSize");
			Integer showPage = params.getInt("showPage");
			
			if(userId == 0) {
				result.setResultCode("201");
				result.setResultMessage("请求用户id不能为空");
				return result;
			}
			
			if ("1".equals(catalog)) {
				//招标中的融资
				InvestSearchJDO searchDO =new InvestSearchJDO();
	    		searchDO.setUserId(userId);
	    		searchDO.setLoanState(2);
				PageResult<MyBorrowTender> pageResult = loanService.queryMyBorrowTender(searchDO, new PageCondition(showPage, pageSize, "applyDate", Order.DESC));
				List<MyBorrowTender> list = pageResult.getData();
				
				Map<String, Object> objMap = new HashMap<String, Object>();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();
				
				Map<String, Object> loanMap = new HashMap<String, Object>();
				
				for (MyBorrowTender myBorrowTender : list) {
					loanMap = new HashMap<String, Object>();
					loanMap.put("loanId", myBorrowTender.getLoanId());
					loanMap.put("loanTitle", myBorrowTender.getLoanNo());
					loanMap.put("loanMoney", myBorrowTender.getLoanMoney());
					loanMap.put("loanRate", myBorrowTender.getLoanRate());
					loanMap.put("loanPeriod", myBorrowTender.getLoanPeriod() + LoanPeriodUnit.valueOf(myBorrowTender.getLoanPeriodType()).getPrompt());
					loanMap.put("releaseTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(myBorrowTender.getSuccessDate()));
					if(myBorrowTender.getInvestMoney() == null) {
						loanMap.put("loanProgress", BigDecimal.ZERO.divide(myBorrowTender.getLoanMoney(), 2).doubleValue() * 100);
					} else {
						loanMap.put("loanProgress", myBorrowTender.getInvestMoney().divide(myBorrowTender.getLoanMoney(), 2).doubleValue() * 100);
					}
					if(myBorrowTender.getState() == 0) {
						loanMap.put("loanStatus", LoanState.BID_INVITING.getPrompt());
					}else {
						loanMap.put("loanStatus", LoanState.valueOf(myBorrowTender.getState()).getPrompt());
					}
					loanList.add(loanMap);
				}
				
				objMap.put("pageCount", pageResult.getTotalCount());
				objMap.put("list", JSON.toJSON(loanList));
				
				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("2".equals(catalog)) {
				//还款中的融资
				InvestSearchJDO searchDO =new InvestSearchJDO();
	    		searchDO.setUserId(userId);
	    		searchDO.setLoanState(1);
				PageResult<MyBorrowReceived> pageResult = loanService.queryMyBorrowReceived(searchDO, new PageCondition(showPage, pageSize, "applyDate", Order.DESC));
				List<MyBorrowReceived> list = pageResult.getData();
				
				Map<String, Object> objMap = new HashMap<String, Object>();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();
				
				Map<String, Object> loanMap = new HashMap<String, Object>();
				
				for (MyBorrowReceived myBorrowReceived : list) {
					loanMap = new HashMap<String, Object>();
					loanMap.put("loanId", myBorrowReceived.getLoanId());
					loanMap.put("loanTitle", myBorrowReceived.getLoanNo());
					loanMap.put("loanMoney", myBorrowReceived.getLoanMoney());
					loanMap.put("loanRate", myBorrowReceived.getLoanRate());
					loanMap.put("loanTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(myBorrowReceived.getLoantime()));
					loanMap.put("payTime", new SimpleDateFormat("yyyy-MM-dd").format(myBorrowReceived.getPaytime()));
					loanMap.put("payTotalMoney", myBorrowReceived.getPayMoney());
					loanMap.put("loanPeriod", myBorrowReceived.getLoanPeriod());
					loanMap.put("loanStatus", LoanState.REPAYING.prompt);
					loanList.add(loanMap);
				}
				
//				loanMap = new HashMap<String, Object>();
//				loanMap.put("loanId", "11");
//				loanMap.put("loanTitle", "3333");
//				loanMap.put("loanMoney", "53.02");
//				loanMap.put("loanRate", "15.00");
//				loanMap.put("loanTime", "2015-02-12 12:12:12");
//				loanMap.put("payTime", "2015-02-12 12:12:12");
//				loanMap.put("payTotalMoney", "255");
//				loanMap.put("loanPeriod", "55");
//				loanMap.put("loanStatus", "45");
//				loanList.add(loanMap);
				
				objMap.put("pageCount", pageResult.getTotalCount());
				objMap.put("list", JSON.toJSON(loanList));
				
				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("3".equals(catalog)) {
				//还款明细
				Integer loanId = params.getInt("loanId");
				
				PaymentPlan paymentPlan = new PaymentPlan();
				paymentPlan.setLoanId(loanId);
				PageResult<PaymentPlan> pageResult = paymentPlanService.queryPaymentPlanList(paymentPlan, new PageCondition(showPage, pageSize));
				
				Map<String, Object> objMap = new HashMap<String, Object>();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();

				Map<String, Object> loanMap = new HashMap<String, Object>();
				
				List<PaymentPlan> list = pageResult.getData();
				for (PaymentPlan plan : list) {
					loanMap = new HashMap<String, Object>();
					loanMap.put("repayId", plan.getId());
					loanMap.put("payTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(plan.getPaytime()));
					if(plan.getCollecttime() == null) {
						loanMap.put("collectTime", "---");
					} else {
						loanMap.put("collectTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(plan.getCollecttime()));
					}
					loanMap.put("payCapital", plan.getPayCapital());
					loanMap.put("payInterest", plan.getPayInterest());
					loanMap.put("payFine", plan.getPayFine());
					loanMap.put("payState", plan.getPayState().getPrompt());
					if(System.currentTimeMillis() >= plan.getPaytime().getTime()) {
						loanMap.put("payState", "可还款");
					}
					loanList.add(loanMap);
				}
				
				objMap.put("pageCount", pageResult.getTotalCount());
				objMap.put("list", JSON.toJSON(loanList));
				
				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("4".equals(catalog)) {
				//还款操作
				Integer loanId = params.getInt("loanId");
				Integer repayId = params.getInt("repayId");
				
				BaseResult baseResult = rapayService.repay(loanId, repayId, PayType.COMMON_CLEAR, userId);
				if(baseResult.isSuccess()) {
					result.setResultCode("200");
					result.setResultMessage("还款成功");
				}else{
					result.setResultCode("201");
					result.setResultMessage(baseResult.getMessage());
				}
			} else if ("5".equals(catalog)) {
				//已完成的融资
				InvestSearchJDO searchDO =new InvestSearchJDO();
	    		searchDO.setUserId(userId);
	    		searchDO.setLoanState(3);
				PageResult<MyBorrowClean> pageResult = loanService.queryMyBorrowClean(searchDO, new PageCondition(showPage, pageSize, "applyDate",Order.DESC));
				List<MyBorrowClean> list = pageResult.getData();
				
				Map<String, Object> objMap = new HashMap<String, Object>();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();
				
				Map<String, Object> loanMap = new HashMap<String, Object>();
				
				for (MyBorrowClean myBorrowClean : list) {
					loanMap = new HashMap<String, Object>();
					loanMap.put("loanId", myBorrowClean.getLoanId());
					loanMap.put("loanTitle", myBorrowClean.getLoanNo());
					loanMap.put("loanMoney", myBorrowClean.getLoanMoney());
					loanMap.put("loanRate", myBorrowClean.getLoanRate());
					loanMap.put("loanCapital", myBorrowClean.getPayCapital());
					loanMap.put("loanInterest", myBorrowClean.getPayInterest());
					loanMap.put("loanFine", myBorrowClean.getPayFine());
					loanMap.put("loanSquareTime", new SimpleDateFormat("yyyy-MM-dd").format(myBorrowClean.getEndDate()));
					loanList.add(loanMap);
				}
				
//				loanMap = new HashMap<String, Object>();
//				loanMap.put("loanId", 1);
//				loanMap.put("loanTitle", "333");
//				loanMap.put("loanMoney", "300.00");
//				loanMap.put("loanRate", "15.00");
//				loanMap.put("loanCapital", "20.00");
//				loanMap.put("loanInterest", "20.00");
//				loanMap.put("loanFine", "20.00");
//				loanMap.put("loanSquareTime", new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis()));
//				loanList.add(loanMap);
				
				objMap.put("pageCount", pageResult.getTotalCount());
				objMap.put("list", JSON.toJSON(loanList));
				
				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
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
