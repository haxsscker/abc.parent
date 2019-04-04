package com.autoserve.abc.web.module.screen.account.myLoan.json;
import java.text.SimpleDateFormat;
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
import com.autoserve.abc.dao.dataobject.stage.myborrow.MyBorrowReceived;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.ExcelFileGenerator;
/**
 * 
 * @author DS
 *
 * 2015下午1:16:51
 */
public class RepayPlanExcel {
	@Autowired
    private HttpSession session;
	@Resource
    private PaymentPlanService paymentPlanService;
	@Resource
	private HttpServletResponse response;
    public void execute(Context context, ParameterParser params) {
    	 User user = (User) session.getAttribute("user");
    	 PaymentPlan paymentPlan = new PaymentPlan();
    	 paymentPlan.setLoanee(user.getUserId());
    	 PageResult<PaymentPlan> pageResult = paymentPlanService.queryPaymentPlanByDay(user.getUserId(),
    			 new PageCondition(0,paymentPlanService.queryPaymentPlanByDay(user.getUserId(),new PageCondition()).getTotalCount()));
    	 List<String> fieldName=Arrays.asList(new String[]{"时间","已还本金","已还利息","已还本息","已还罚金","已还服务费","未还本金","未还利息","未还本息","未还服务费","总额"});
    	
 		List<List<String>> fieldData = new ArrayList<List<String>>();
 		for(PaymentPlan paymentPlan2:pageResult.getData()){
 			List<String> temp = new ArrayList<String>();
 			temp.add(new SimpleDateFormat("yyyy-MM-dd").format(paymentPlan2.getPaytime()));
 			temp.add(paymentPlan2.getPayCollectCapital().toString());
 			temp.add(paymentPlan2.getPayCollectInterest().toString());
 			temp.add(paymentPlan2.getPayCollectCapital().add(paymentPlan2.getPayCollectInterest()).toString());
 			temp.add(paymentPlan2.getPayCollectFine().toString());
 			temp.add(paymentPlan2.getCollectServiceFee().toString());
 			temp.add(paymentPlan2.getPayCapital().subtract(paymentPlan2.getPayCollectCapital()).toString());
 			temp.add(paymentPlan2.getPayInterest().subtract(paymentPlan2.getPayCollectInterest()).toString());
 			temp.add(paymentPlan2.getPayCapital().subtract(paymentPlan2.getPayCollectCapital()).add(paymentPlan2.getPayInterest().subtract(paymentPlan2.getPayCollectInterest())).toString());
 			//temp.add(paymentPlan2.getPayFine().subtract(paymentPlan2.getPayCollectFine()).toString());
 			temp.add(paymentPlan2.getPayServiceFee().subtract(paymentPlan2.getCollectServiceFee()).toString());
 			temp.add(paymentPlan2.getPayTotalMoney().toString());
 			fieldData.add(temp);
 		}
 		ExportExcel(fieldName,fieldData,"还款计划.xls");
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
