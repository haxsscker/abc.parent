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
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchJDO;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyInvestClean;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyInvestReceived;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyInvestTender;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.LoanPayType;
import com.autoserve.abc.service.biz.enums.LoanPeriodUnit;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.CryptUtils;

/**
 * 投资管理
 * 
 * @author Bo.Zhang
 *
 */
public class InvestManage {
	
	@Resource
	private InvestQueryService investQueryService;
	
	@Resource
	private TransferLoanService transferLoanService;
	
	@Resource
	private UserService userService;

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
				//投标中的项目
				InvestSearchJDO investSearchJDO = new InvestSearchJDO();
				investSearchJDO.setUserId(userId);
				PageResult<MyInvestTender> pageResult = investQueryService.queryMyInvestTender(investSearchJDO, new PageCondition(showPage, pageSize));
				List<MyInvestTender> list = pageResult.getData();
				
				Map<String, Object> objMap = new HashMap<String, Object>();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();
				
				Map<String, Object> loanMap = new HashMap<String, Object>();
				
				for (MyInvestTender myInvestTender : list) {
					loanMap = new HashMap<String, Object>();
					loanMap.put("loanTitle", myInvestTender.getLoanNo());
					loanMap.put("loanMoney", myInvestTender.getLoanMoney());
					loanMap.put("loanRate", myInvestTender.getLoanRate());
					loanMap.put("loanPeriod", "" + myInvestTender.getLoanPeriod() + LoanPeriodUnit.valueOf(myInvestTender.getLoanPeriodType()).getPrompt());
					loanMap.put("loanPayType", LoanPayType.valueOf(myInvestTender.getLoanPayType()).getPrompt());
					loanMap.put("investTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(myInvestTender.getTenderDate()));
					loanMap.put("investMoney", myInvestTender.getInvestMoney());
					loanMap.put("loanProgress", myInvestTender.getValidInvest().divide(myInvestTender.getLoanMoney(), 2).doubleValue() * 100);
					loanList.add(loanMap);
				}
				
//				loanMap = new HashMap<String, Object>();
//				loanMap.put("loanTitle", "888888888");
//				loanMap.put("loanMoney", 200.00);
//				loanMap.put("loanRate", 15.00);
//				loanMap.put("loanPeriod", "15月");
//				loanMap.put("loanPayType", "等本等息");
//				loanMap.put("investTime", "2015-02-01 14:28:45");
//				loanMap.put("investMoney", 200.00);
//				loanMap.put("loanProgress", 100);
//				loanList.add(loanMap);
				
				objMap.put("pageCount", pageResult.getTotalCount());
				objMap.put("list", JSON.toJSON(loanList));
				
				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("2".equals(catalog)) {
				//撤投操作
//				Integer investId = params.getInt("investId");
//				
//				BaseResult baseResult = investService.withdrawInvest(investId, userId);
//				if(baseResult.isSuccess()) {
//					result.setResultCode("200");
//					result.setResultMessage("撤投成功");
//				} else {
//					result.setResultCode("201");
//					result.setResultMessage(baseResult.getMessage());
//				}
			} else if ("3".equals(catalog)) {
				//收款中的项目
				InvestSearchJDO investSearchJDO = new InvestSearchJDO();
				investSearchJDO.setUserId(userId);
				PageResult<MyInvestReceived> pageResult = investQueryService.queryMyInvestReceived(investSearchJDO, new PageCondition(showPage, pageSize));
				List<MyInvestReceived> list = pageResult.getData();
				
				Map<String, Object> objMap = new HashMap<String, Object>();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();
				
				Map<String, Object> loanMap = new HashMap<String, Object>();
				
				for (MyInvestReceived myInvestReceived : list) {
					loanMap = new HashMap<String, Object>();
					loanMap.put("loanId", myInvestReceived.getLoanId());
					loanMap.put("investId", myInvestReceived.getInvestId());
					loanMap.put("loanTitle", myInvestReceived.getLoanNo());
					loanMap.put("loanMoney", myInvestReceived.getLoanMoney());
					loanMap.put("loanRate", myInvestReceived.getLoanRate());
					loanMap.put("receivedDate", new SimpleDateFormat("yyyy-MM-dd").format(myInvestReceived.getReceivedDate()));
					//xiatt 20160725 下次还款金额没有时，移动端页面展示null
					//loanMap.put("receivedMoney", myInvestReceived.getReceivedMoney());
					loanMap.put("receivedMoney", myInvestReceived.getReceivedMoney()==null 
							? 0.00 : myInvestReceived.getReceivedMoney());
					loanMap.put("dsMoney", myInvestReceived.getDsMoney());
					loanMap.put("investTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(myInvestReceived.getInvestDate()));
					loanMap.put("investMoney", myInvestReceived.getInvestMoney());
					loanMap.put("endDate", new SimpleDateFormat("yyyy-MM-dd").format(myInvestReceived.getEndDate()));
					loanMap.put("investStatus", TransferLoanState.valueOf(myInvestReceived.getTransferState()).getPrompt());
					loanList.add(loanMap);
				}
				
//				loanMap = new HashMap<String, Object>();
//				loanMap.put("loanId", 12);
//				loanMap.put("loanTitle", "89898");
//				loanMap.put("loanMoney", 200.00);
//				loanMap.put("loanRate", 15.00);
//				loanMap.put("receivedDate", "2015-02-10");
//				loanMap.put("receivedMoney", "10.00");
//				loanMap.put("investTime", "2015-02-01 14:28:45");
//				loanMap.put("investMoney", 200.00);
//				loanMap.put("endDate", "2015-02-01");
//				loanList.add(loanMap);
				
				objMap.put("pageCount", pageResult.getTotalCount());
				objMap.put("list", JSON.toJSON(loanList));
				
				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("4".equals(catalog)) {
				//收款明细
				result.setResultCode("200");
			} else if ("5".equals(catalog)) {
				//已完成的项目
				InvestSearchJDO investSearchJDO = new InvestSearchJDO();
				investSearchJDO.setUserId(userId);
				PageResult<MyInvestClean> pageResult = investQueryService.queryMyInvestClean(investSearchJDO, new PageCondition(showPage, pageSize));
				List<MyInvestClean> list = pageResult.getData();

				
				Map<String, Object> objMap = new HashMap<String, Object>();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();
				
				Map<String, Object> loanMap = new HashMap<String, Object>();
				
				for (MyInvestClean myInvestClean : list) {
					loanMap = new HashMap<String, Object>();
					loanMap.put("loanId", myInvestClean.getLoanId());
					loanMap.put("loanTitle", myInvestClean.getLoanNo());
					loanMap.put("loanMoney", myInvestClean.getLoanMoney());
					loanMap.put("loanRate", myInvestClean.getLoanRate());
					loanMap.put("receivedDate", new SimpleDateFormat("yyyy-MM-dd").format(myInvestClean.getReceivedDate()));
					loanMap.put("receivedMoney", myInvestClean.getReceivedMoney());
					loanMap.put("investTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(myInvestClean.getInvestDate()));
					loanMap.put("investMoney", myInvestClean.getInvestMoney());
					loanMap.put("investStatus", InvestState.valueOf(myInvestClean.getState()).getPrompt());
					loanList.add(loanMap);
				}
				
//				loanMap = new HashMap<String, Object>();
//				loanMap.put("loanId", 12);
//				loanMap.put("loanTitle", "89898");
//				loanMap.put("loanMoney", 200.00);
//				loanMap.put("loanRate", 15.00);
//				loanMap.put("receivedDate", "2015-02-10");
//				loanMap.put("receivedMoney", "10.00");
//				loanMap.put("investTime", "2015-02-01 14:28:45");
//				loanMap.put("investMoney", 200.00);
//				loanMap.put("investStatus", "已结清");
//				loanList.add(loanMap);
				
				objMap.put("pageCount", pageResult.getTotalCount());
				objMap.put("list", JSON.toJSON(loanList));
				
				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if("6".equals(catalog)) {
				//转让操作
				Double transAmount = params.getDouble("transAmount");
				String dealPwd = params.getString("dealPwd");
				Integer loanId = params.getInt("loanId");
				Integer investId = params.getInt("investId");
				
				UserDO userDO = userService.findById(userId).getData();
				if (dealPwd == null || !CryptUtils.md5(dealPwd).equals(userDO.getUserDealPwd())) {
					result.setResultCode("201");
					result.setResultMessage("支付密码不正确!");
					return result;
				}
				
				TransferLoan pojo = new TransferLoan();

		        pojo.setUserId(userId);
		        pojo.setOriginId(loanId);

		        pojo.setInvestId(investId);//根据投资ID进行转让

		        pojo.setTransferMoney(BigDecimal.valueOf(transAmount));

		        pojo.setTransferDiscountFee(BigDecimal.valueOf(20)); // 转让折让费，不做控制
		        pojo.setTransferTotal(BigDecimal.valueOf(100)); //转让债权总额，不做控制
		        pojo.setTransferPeriod(1); // 转让期数，不做控制

		        PlainResult<Integer> plainResult = transferLoanService.createTransferLoan(pojo);

		        if (plainResult.isSuccess()) {
		        	result.setResultCode("200");
		            result.setResultMessage("发起转让成功，转让标id为：" + plainResult.getData());
		        } else {
		        	result.setResultCode("201");
		            result.setResultMessage(plainResult.getMessage());
		        }
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
