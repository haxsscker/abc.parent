package com.autoserve.abc.web.module.screen.mobile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.InvestSearchDO;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyCreditClean;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyCreditReceived;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyCreditTender;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.result.PageResult;

public class TransferManage {
	
	@Resource
	private InvestQueryService investQueryService;

	public JsonMobileVO execute(Context context, ParameterParser params) throws IOException {
		JsonMobileVO result = new JsonMobileVO();
		
		try {
			String catalog = params.getString("catalog");
			Integer userId = params.getInt("userId");
			
			Integer pageSize = params.getInt("pageSize");
			Integer showPage = params.getInt("showPage");
			
			if(userId == null || "".equals(userId)) {
				result.setResultCode("201");
				result.setResultMessage("请求用户id不能为空");
				return result;
			}
			
			if ("1".equals(catalog)) {
				//收款中的项目
				InvestSearchDO searchDO = new InvestSearchDO();
				searchDO.setInvestUserId(userId);
				PageResult<MyCreditReceived> pageResult = investQueryService.queryMyCreditReceived(searchDO, new PageCondition(showPage, pageSize));
				List<MyCreditReceived> list = pageResult.getData();
				
				Map<String, Object> objMap = new HashMap<String, Object>();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();
				
				Map<String, Object> loanMap = new HashMap<String, Object>();
				
				for (MyCreditReceived myCreditReceived : list) {
					loanMap = new HashMap<String, Object>();
					loanMap.put("loanId", myCreditReceived.getLoanId());
					loanMap.put("loanTitle", myCreditReceived.getLoanNo());
					loanMap.put("loanRate", myCreditReceived.getLoanRate());
					loanMap.put("transferMoney", myCreditReceived.getTransferLoanMoney());
					loanMap.put("investTime", myCreditReceived.getInvestDate());
					loanMap.put("endDate", myCreditReceived.getEndDate());
					loanMap.put("cleanMoney", myCreditReceived.getCleanMoney());
					loanMap.put("receivedMoney", myCreditReceived.getReceivedMoney());
					loanMap.put("validTransferMoney", myCreditReceived.getTransferMoney());
					loanList.add(loanMap);
				}
				
//				loanMap = new HashMap<String, Object>();
//				loanMap.put("loanId", "99");
//				loanMap.put("loanTitle", "8888");
//				loanMap.put("loanRate", "15.00");
//				loanMap.put("transferMoney", "2000.00");
//				loanMap.put("investTime", "2015-02-01 14:12:15");
//				loanMap.put("endDate", "2015-02-01");
//				loanMap.put("cleanMoney", 2.00);
//				loanMap.put("receivedMoney", 3.00);
//				loanMap.put("validTransferMoney", 4.00);
//				loanList.add(loanMap);
				
				objMap.put("pageCount", pageResult.getTotalCount());
				objMap.put("list", JSON.toJSON(loanList));
				
				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("2".equals(catalog)) {
				//转让操作
				result.setResultCode("200");
			} else if ("3".equals(catalog)) {
				//转让中的债权
				InvestSearchDO searchDO = new InvestSearchDO();
				searchDO.setInvestUserId(userId);
				PageResult<MyCreditTender> pageResult = investQueryService.queryMyCreditTender(searchDO, new PageCondition(showPage, pageSize));
				List<MyCreditTender> list = pageResult.getData();
				
				Map<String, Object> objMap = new HashMap<String, Object>();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();
				
				Map<String, Object> loanMap = new HashMap<String, Object>();
				
				for (MyCreditTender myCreditTender : list) {
					loanMap = new HashMap<String, Object>();
					loanMap.put("loanId", myCreditTender.getLoanId());
					loanMap.put("loanTitle", myCreditTender.getLoanNo());
					loanMap.put("loanRate", myCreditTender.getLoanRate());
					loanMap.put("transferMoney", myCreditTender.getTransferLoanMoney());
					loanMap.put("validTransferMoney", myCreditTender.getTransferMoney());
					loanMap.put("transferProgress", myCreditTender.getTransferMoney().divide(myCreditTender.getTransferLoanMoney(), 2).doubleValue() * 100);
					loanMap.put("endDate", myCreditTender.getEndDate());
					loanList.add(loanMap);
				}
				
//				loanMap = new HashMap<String, Object>();
//				loanMap.put("loanId", "99");
//				loanMap.put("loanTitle", "8888");
//				loanMap.put("loanRate", "15.00");
//				loanMap.put("transferMoney", "4564.00");
//				loanMap.put("validTransferMoney", "454.00");
//				loanMap.put("transferProgress", "45");
//				loanMap.put("endDate", "2015-02-02");
//				loanList.add(loanMap);
				
				objMap.put("pageCount", pageResult.getTotalCount());
				objMap.put("list", JSON.toJSON(loanList));
				
				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("4".equals(catalog)) {
				//已完成的债权
				InvestSearchDO searchDO = new InvestSearchDO();
				searchDO.setInvestUserId(userId);
				PageResult<MyCreditClean> pageResult = investQueryService.queryMyCreditClean(searchDO, new PageCondition(showPage, pageSize));
				List<MyCreditClean> list = pageResult.getData();
				
				Map<String, Object> objMap = new HashMap<String, Object>();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();
				
				Map<String, Object> loanMap = new HashMap<String, Object>();
				
				for (MyCreditClean myCreditClean : list) {
					loanMap = new HashMap<String, Object>();
					loanMap.put("loanId", myCreditClean.getLoanId());
					loanMap.put("loanTitle", myCreditClean.getLoanNo());
					loanMap.put("loanRate", myCreditClean.getLoanRate());
					loanMap.put("creditTotalMoney", myCreditClean.getCreditTotalMoney());
					loanMap.put("transferMoney", myCreditClean.getTransferMoney());
					loanMap.put("transferTime", myCreditClean.getTransferDate());
					loanMap.put("endDate", myCreditClean.getEndDate());
					loanList.add(loanMap);
				}
				
//				loanMap = new HashMap<String, Object>();
//				loanMap.put("loanId", "99");
//				loanMap.put("loanTitle", "8888");
//				loanMap.put("loanRate", "15.00");
//				loanMap.put("creditTotalMoney", 600.00);
//				loanMap.put("transferMoney", 500.00);
//				loanMap.put("transferTime", "2015-02-02 12:12:12");
//				loanMap.put("endDate", "2015-02-02");
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
