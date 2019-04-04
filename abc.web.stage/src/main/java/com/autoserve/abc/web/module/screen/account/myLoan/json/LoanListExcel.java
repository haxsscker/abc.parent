package com.autoserve.abc.web.module.screen.account.myLoan.json;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.stage.myborrow.BorrowStatistics;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.LoanPeriodUnit;
import com.autoserve.abc.service.biz.intf.cash.CashBorrowerService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.ExcelFileGenerator;
/**
 * 
 * @author DS
 *
 * 2015下午1:25:32
 */
public class LoanListExcel {
	@Autowired
    private HttpSession session;
	@Resource
    private LoanService loanService;
	@Resource
	private CashBorrowerService cashBorrowerService;
	@Resource
	private HttpServletResponse response;
    public void execute(Context context, ParameterParser params) {

      User user = (User) session.getAttribute("user");
      //查询统计明细      
      PageResult<BorrowStatistics> pageResult = loanService.queryBorrowStatistics(user.getUserId(),
    		  new PageCondition(0,loanService.queryBorrowStatistics(user.getUserId(),new PageCondition()).getTotalCount()));
      List<String> fieldName=Arrays.asList(new String[]{"借款标题","借款金额","借款期限","已还本息","待还本息","未还期数"});
	List<List<String>> fieldData = new ArrayList<List<String>>();
	for(BorrowStatistics borrowStatistics:pageResult.getData()){
		List<String> temp = new ArrayList<String>();
		temp.add(borrowStatistics.getLoanNo());
		temp.add(borrowStatistics.getLoanMoney().toString());
		if(borrowStatistics.getLoanPeriodType()==1){
			temp.add(borrowStatistics.getLoanPeriod()+LoanPeriodUnit.YEAR.getPrompt());
		} else if(borrowStatistics.getLoanPeriodType()==2){
			temp.add(borrowStatistics.getLoanPeriod()+LoanPeriodUnit.MONTH.getPrompt());
		}else if(borrowStatistics.getLoanPeriodType()==3){
			temp.add(borrowStatistics.getLoanPeriod()+LoanPeriodUnit.DAY.getPrompt());
		}
		if(borrowStatistics.getAlreadyMoney()!=null &&  borrowStatistics.getAlreadyMoney().doubleValue()!=0.00){
			temp.add(borrowStatistics.getAlreadyMoney().toString());
		}else{
			temp.add("0.00");
		}
		if(borrowStatistics.getWaitMoney()!=null && borrowStatistics.getWaitMoney().doubleValue()!=0.00){
			temp.add(borrowStatistics.getWaitMoney().toString());
		}else{
			temp.add("0.00");
		}
		if(borrowStatistics.getWaitPeriod()!=null && borrowStatistics.getWaitPeriod().doubleValue()!=0.00){
			temp.add(borrowStatistics.getWaitPeriod().toString());
		}else{
			temp.add(0+"");
		}
		fieldData.add(temp);
	}
 		ExportExcel(fieldName,fieldData,"待还明细.xls");
    }
    
  //导出
    public void ExportExcel(List<?> fieldName,List<?> fieldData,String name){
    	ExcelFileGenerator excelFileGenerator=new ExcelFileGenerator(fieldName, fieldData);
		try {
			response.setCharacterEncoding("gb2312");
			response.setHeader("Content-Disposition", "attachment;filename="+new String(name.getBytes("GB2312"),"iso8859-1"));
			response.setContentType("application/ynd.ms-excel;charset=UTF-8");
			excelFileGenerator.expordExcel(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
