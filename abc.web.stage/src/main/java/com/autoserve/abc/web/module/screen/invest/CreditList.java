package com.autoserve.abc.web.module.screen.invest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.TransferLoanJDO;
import com.autoserve.abc.dao.dataobject.search.TransferLoanSearchDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.loan.manage.TransferLoanManageService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.util.Arith;
import com.autoserve.abc.web.util.Pagebean;

/**
 * 债权转让列表
 * @author DS
 *
 * 2015下午6:53:32
 */


public class CreditList {
	
	 @Resource
	 private TransferLoanService transferLoanService;
	 @Resource
	 private LoanService loanService;
	 @Resource
	 private TransferLoanManageService transferLoanManageService;
	 @Resource
	 private HttpSession session;
	 
	 public void execute(@Params TransferLoanSearchDO pojo,Context context, ParameterParser params) {
		//项目状态
		  User user = (User) session.getAttribute("user");
		  if(pojo.getTransferLoanStates()==null || pojo.getTransferLoanStates().size()==0){
			  pojo.setTransferLoanStates(Arrays.asList(TransferLoanState.TRANSFERING.state,TransferLoanState.FULL_WAIT_REVIEW.state
					  ,TransferLoanState.FULL_REVIEW_PASS.state,TransferLoanState.FULL_REVIEW_FAIL.state
					  ,TransferLoanState.MONEY_TRANSFERING.state,TransferLoanState. MONEY_TRANSFERED.state));
		  }
		  int currentPage = params.getInt("currentPage");
		  int pageSize  = params.getInt("pageSize");
		  if(currentPage==0)
			  currentPage=1;
		  		pageSize=10;
		  		
		  PageCondition pageCondition = new PageCondition(currentPage,pageSize);
		  PageResult<TransferLoanJDO> result=transferLoanService.queryListByParam(pojo, pageCondition);
		  Pagebean<TransferLoanJDO> pagebean = new Pagebean<TransferLoanJDO>(currentPage, pageSize, result.getData(), result.getTotalCount());
		  //百分比
		  Map<Integer,BigDecimal> resultTransferLoanListMap = new HashMap<Integer,BigDecimal>();		  
		  for(TransferLoanJDO temp : pagebean.getRecordList()){
			  BigDecimal percent = Arith.calcPercent(temp.getTlCurrentValidInvest(), temp.getTlTransferMoney());
			  resultTransferLoanListMap.put(temp.getTlId(), percent);
			  if(temp.getTimelimit()<=0 && temp.getTlState()!=7 && temp.getTlState()!=9){
				  BaseResult changeResult = transferLoanManageService.cancelTransferLoan(temp.getTlId(),user.getUserId(),"");
				  temp.setTlState(9);  
				  if (!changeResult.isSuccess()) {
	                    throw new BusinessException("转让标强制满标失败！"+changeResult.getMessage());
	                }
			  }
		  }
		  context.put("pagebean", pagebean);
		  context.put("resultTransferLoanListMap", resultTransferLoanListMap);
		  context.put("Loanstate", pojo.getTransferLoanStates());
		  context.put("LoanCategory", pojo.getLoanCategory());
	    }
}
