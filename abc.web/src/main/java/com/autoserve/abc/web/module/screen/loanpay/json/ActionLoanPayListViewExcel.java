package com.autoserve.abc.web.module.screen.loanpay.json;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.dao.dataobject.PaymentPlanJDO;
import com.autoserve.abc.dao.dataobject.search.PaymentPlanSearchDO;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanPayType;
import com.autoserve.abc.service.biz.enums.PayType;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.module.screen.BaseController;
import com.autoserve.abc.web.util.DateUtil;
import com.google.common.collect.Lists;

public class ActionLoanPayListViewExcel extends BaseController {

	@Resource
	private PaymentPlanService paymentPlanService;
	@Resource
	private EmployeeService employeeService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public void execute(Context context, ParameterParser params, Navigator nav) {

		Integer empId = LoginUserUtil.getEmpId();
		PlainResult<EmployeeDO> employeeDO = employeeService.findById(empId);
		String searchForm = params.getString("SearchForm");
		PaymentPlanSearchDO searchDO = new PaymentPlanSearchDO();
		if(!"kefu".equals(employeeDO.getData().getEmpName())){
			searchDO.setGovId(employeeDO.getData().getEmpOrgId());
		}
		//条件
		String loanNo = params.getString("loanNO");
		searchDO.setLoanNO(loanNo);
		
		Date payTime1 = params.getDate("payTime1", sdf);
		if(payTime1!=null){
			searchDO.setPayTime1(DateUtil.setTime(payTime1, 0, 0, 0));
		}
		
		Date payTime2 = params.getDate("payTime2", sdf);
		if(payTime2!=null){
			searchDO.setPayTime2(DateUtil.setTime(payTime2, 23, 59, 59));
		}
		
		String payType_ = params.getString("payType");
		if(StringUtils.isNotEmpty(payType_)){
			searchDO.setPayType(Integer.parseInt(payType_));
		}
		
		String payState = params.getString("payState");
		List<Integer> payStates = Lists.newArrayList();
		if(StringUtils.isNotEmpty(payState)){
			payStates.add(Integer.parseInt(payState));
		}else{
			payStates.addAll(Arrays.asList(0, 1, 2));
		}
		searchDO.setPayStates(payStates);
		

		PageResult<PaymentPlanJDO> result = paymentPlanService.queryPlanList2(
				searchDO, new PageCondition(1, Integer.MAX_VALUE));

		List<String> fieldName = Arrays.asList(new String[] { "项目名称", "项目类型",
				"借款人", "借款金额", "年化收益率", "借款期限","期限类型", "期数", "应还日期", "逾期天数", "本期应还本金",
				"本期应还利息","本期应还服务费","本期已还本金","本期已还利息","本期已还服务费", "还款方式", "担保机构", "还款类型", "还款状态" });
		List<List<String>> fieldData = new ArrayList<List<String>>();
		for (PaymentPlanJDO pp : result.getData()) {
			List<String> temp = new ArrayList<String>();
			temp.add(pp.getPro_loan_no());
			temp.add(LoanCategory.valueOf(pp.getPdo_product_name()).prompt);
			temp.add(pp.getPro_add_emp());
			temp.add(pp.getPro_loan_money());
			temp.add(pp.getPro_loan_rate()+"%");
			temp.add(pp.getPro_borrowing_period()+"");
			int periodUnit = pp.getPro_borrowing_period_unit();
			if(periodUnit==1){
				temp.add("年");
			}else if(periodUnit==2){
				temp.add("月");
			}else if(periodUnit==3){
				temp.add("日");
			}
			temp.add(pp.getPro_loan_period()+"期");
			temp.add(DateUtil.formatDate(pp.getPro_pay_date(), "yyyy-MM-dd")); //应还日期
			//逾期天数
			Integer overdueDay = pp.getPro_overdue_days();
		
			if(overdueDay==null){
				temp.add("-");
			}else if(overdueDay<0){
				temp.add(0+"");
			}else{
				temp.add(overdueDay+"");
			}
			temp.add(pp.getPro_pay_money()+"");
			temp.add(pp.getPro_pay_rate()+"");//利息
			temp.add(pp.getPro_service_fee()+"");
			temp.add(pp.getPro_collect_money()+"");
			temp.add(pp.getPro_collect_rate()+"");
			temp.add(pp.getCollect_service_fee()+"");
			temp.add(LoanPayType.valueOf(Integer.valueOf(pp.getPro_pay_type())).prompt);
			temp.add(pp.getGov_name());
			
			//还款类型
			Integer payType = pp.getPpPayType();
			if(payType==null){
				temp.add("");
			}else {
				temp.add(PayType.valueOf(payType).prompt);
			}
			
			int state = pp.getPro_payment_status();
			if(state==-1){
				temp.add("未激活");
			}else if(state==0){
				temp.add("未还清");
			}else if(state==1){
				temp.add("付款中");
			}else if(state==2){
				temp.add("已还清");
			}else if(state==3){
				temp.add("被取消");
			}
			fieldData.add(temp);
		}
		ExportExcel(fieldName, fieldData, "贷款还款.xls");
	}


}
