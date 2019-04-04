package com.autoserve.abc.web.module.screen.invest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.LoanSearchDO;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.Arith;
import com.autoserve.abc.web.util.Pagebean;
/**
 * 投资理财列表
 * @author DS
 *
 * 2015下午6:54:34
 */
public class OrientList {

	@Resource
	private LoanService loanService;
	@Resource
	 LoanQueryService  loanQueryService;
	
	  public void execute(@Params LoanSearchDO searchDO,Context context, ParameterParser params) {
		  //项目状态
		  if(searchDO.getLoanState()==null || searchDO.getLoanState().size()==0){
			  searchDO.setLoanState(Arrays.asList(LoanState.BID_INVITING.state,LoanState.FULL_WAIT_REVIEW.state,LoanState.FULL_REVIEW_PASS.state
					  ,LoanState.FULL_REVIEW_FAIL.state,LoanState.BID_CANCELED.state,LoanState.MONEY_TRANSFERING.state,LoanState.REPAYING.state
					  ,LoanState.REPAY_COMPLETED.state));
		  }
		  int currentPage = params.getInt("currentPage");
		  int pageSize  = params.getInt("pageSize");
		  if(currentPage==0){
			  currentPage=1;
			  pageSize=10;
		  }			 
		  PageCondition pageCondition = new PageCondition(currentPage,pageSize);
		  searchDO.setFlag(0);
		  searchDO.setIsShow(0);
		  
		  PageResult<Loan> result = this.loanQueryService.queryLoanListBySearchParam(searchDO, pageCondition);
		  Pagebean<Loan> pagebean = new Pagebean<Loan>(currentPage, pageSize, result.getData(), result.getTotalCount());
		 
		  //百分比
		  Map<Integer,BigDecimal> resultLoanListMap = new HashMap<Integer,BigDecimal>();	
		  //距离开始时间间隔
		  Map<Integer, Long> duarStartTimeMap = new HashMap<Integer, Long>();
		  //距离结束时间间隔
		  Map<Integer, Long> duarEndTimeMap = new HashMap<Integer, Long>();
		  
		  Long currTime = System.currentTimeMillis();
		  
		  for(Loan temp : pagebean.getRecordList()){
			  
			  BigDecimal percent = Arith.calcPercent(temp.getLoanCurrentInvest(), temp.getLoanMoney());
			  resultLoanListMap.put(temp.getLoanId(), percent);
			  
			  duarStartTimeMap.put(temp.getLoanId(), temp.getLoanInvestStarttime().getTime() - currTime);
			  
			  duarEndTimeMap.put(temp.getLoanId(), currTime - temp.getLoanInvestEndtime().getTime());
		  }
		  context.put("pagebean", pagebean);
		  context.put("resultLoanListPercent", resultLoanListMap);
		  context.put("duarStartTimeMap", duarStartTimeMap);
		  context.put("duarEndTimeMap", duarEndTimeMap);
		  context.put("Loanstate", searchDO.getLoanState());
		  context.put("LoanCategory", searchDO.getLoanCategory());
	  }
	  
}
