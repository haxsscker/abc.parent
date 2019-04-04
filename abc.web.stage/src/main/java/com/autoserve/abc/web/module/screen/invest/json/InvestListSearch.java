package com.autoserve.abc.web.module.screen.invest.json;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.Pagebean;

/**
 * 投资理财列表
 */
public class InvestListSearch {

	@Resource
	private LoanService loanService;
	@Resource
	 LoanQueryService  loanQueryService;
	
	  public  Pagebean<Loan> execute(Context context, ParameterParser params) {
		  int currentPage = params.getInt("currentPage");
		  int pageSize  = params.getInt("pageSize");
		  if(currentPage==0)
			  currentPage=1;
		  		pageSize=10;
		  PageCondition pageCondition = new PageCondition(currentPage,pageSize);
		  
		  Loan loan = new Loan();
		  int  loanState = params.getInt("loanState");     //普通标的状态
		  loan.setLoanState(LoanState.valueOf(loanState));
		  PageResult<Loan>  result_search = this.loanQueryService.queryLoanListByParam(loan, pageCondition);
		  
		  Pagebean<Loan> pagebean = new Pagebean<Loan>(currentPage, pageSize, result_search.getData(), result_search.getTotalCount());

		  return pagebean;
	  }
}
