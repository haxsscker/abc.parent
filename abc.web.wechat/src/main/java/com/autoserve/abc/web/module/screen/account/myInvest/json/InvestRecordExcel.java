package com.autoserve.abc.web.module.screen.account.myInvest.json;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.InvestSearchJDO;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyInvestClean;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyInvestReceived;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyInvestTender;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.LoanPayType;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.ExcelFileGenerator;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.NumberUtil;
/**
 * 
 * @author DS
 *
 * 2015上午10:13:43
 */
public class InvestRecordExcel {
	@Autowired
	private DeployConfigService deployConfigService;
	@Autowired
    private HttpSession session;
	@Autowired
	private InvestQueryService investQueryService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private PaymentPlanService paymentPlanService;
	@Resource
	private HttpServletRequest request;
	@Resource
	private HttpServletResponse response;
    public void execute(Context context, ParameterParser params,Navigator nav) {
    	//登录URL
    	User user=(User)session.getAttribute("user");
    	if(user==null){
    		nav.forwardTo(deployConfigService.getLoginUrl(request));
    		return;
    	}  
    	context.put("user", user);
    	String investType=(String)params.getString("investType");
    	//开始时间
    	String startDate=params.getString("startDate");
    	//结束时间
    	String endDate=params.getString("endDate");
    	//时间区间
    	String Recent=params.getString("Recent");
    	//项目名称
    	String loanno=params.getString("loanno");
    	//查询条件
    	InvestSearchJDO investSearchJDO=new InvestSearchJDO();
    	investSearchJDO.setUserId(user.getUserId());
    	investSearchJDO.setLoanName(loanno);
    	if(Recent!=null && !"".equals(Recent)){   		
    		investSearchJDO.setRecent(Integer.parseInt(Recent));
    		context.put("recent"+Recent, "riqi-current");
    	}else{
    		investSearchJDO.setRecent(null);
    	}
    	if(startDate!=null){
			try {
				Date start=new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
				investSearchJDO.setStartDate(start);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(endDate!=null){
			try {
				Date end=new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
				investSearchJDO.setEndDate(end);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
    	if(investType!=null && "HKZ".equals(investType)){
    		MyInvestReceived(investSearchJDO);
    	}
    	if(investType!=null && "TBZ".equals(investType)){
    		MyInvestTender(investSearchJDO);
    	}
    	if(investType!=null && "YHK".equals(investType)){
    		MyInvestClean(investSearchJDO);
    	}
    }
    
    //回款中
    public void MyInvestReceived(InvestSearchJDO investSearchJDO){
    	//未支付
		PageResult<MyInvestReceived> pageResult=investQueryService.queryMyInvestReceived(investSearchJDO,
				new PageCondition(0,investQueryService.queryMyInvestReceived(investSearchJDO,new PageCondition()).getTotalCount()));		
		List<String> fieldName=Arrays.asList(new String[]{"项目名称","年利率","项目金额","投资金额","投资时间","待收本金","下期回款日","下期回款金额","到期日"});
		List<List<String>> fieldData = new ArrayList<List<String>>();
		for(MyInvestReceived myInvestReceived:pageResult.getData()){
			List<String> temp = new ArrayList<String>();
			temp.add(myInvestReceived.getLoanNo());
			temp.add(myInvestReceived.getLoanRate().toString()+"%");
			temp.add(myInvestReceived.getLoanMoney().toString());
			temp.add(myInvestReceived.getInvestMoney().toString());
			temp.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(myInvestReceived.getInvestDate()));
			temp.add(myInvestReceived.getDsMoney().toString());
			temp.add(new SimpleDateFormat("yyyy-MM-dd").format(myInvestReceived.getReceivedDate()));
			temp.add(myInvestReceived.getReceivedMoney().toString());
			temp.add(new SimpleDateFormat("yyyy-MM-dd").format(myInvestReceived.getEndDate()));
			fieldData.add(temp);
		}
		ExportExcel(fieldName,fieldData,"回款中.xls");
    }
    //投标中
    public void MyInvestTender(InvestSearchJDO investSearchJDO){
    	PageResult<MyInvestTender>  pageResult=investQueryService.queryMyInvestTender(investSearchJDO, 
    			new PageCondition(0,investQueryService.queryMyInvestTender(investSearchJDO,new PageCondition()).getTotalCount()));
    	List<String> fieldName=Arrays.asList(new String[]{"项目名称","年利率","项目金额","投资金额","期限","还款方式","投标时间","进度"});
		List<List<String>> fieldData = new ArrayList<List<String>>();
		for(MyInvestTender myInvestTender:pageResult.getData()){
			List<String> temp = new ArrayList<String>();
			temp.add(myInvestTender.getLoanNo());
			temp.add(myInvestTender.getLoanRate().toString()+"%");
			temp.add(myInvestTender.getLoanMoney().toString());
			temp.add(myInvestTender.getInvestMoney().toString());
			if(myInvestTender.getLoanPeriodType()==1){
				temp.add(String.valueOf(myInvestTender.getLoanPeriod())+"年");
			}else if(myInvestTender.getLoanPeriodType()==2){
				temp.add(String.valueOf(myInvestTender.getLoanPeriod())+"个月");
			}else if(myInvestTender.getLoanPeriodType()==3){
				temp.add(String.valueOf(myInvestTender.getLoanPeriod())+"日");
			}
			if(myInvestTender.getLoanPayType()==1){
				//temp.add(LoanPayType.DEBX.getPrompt());
			}else if(myInvestTender.getLoanPayType()==2){
				temp.add(LoanPayType.AYHX_DQHB.getPrompt());
			}else if(myInvestTender.getLoanPayType()==3){
				//temp.add(LoanPayType.DEBJ.getPrompt());
			}else if(myInvestTender.getLoanPayType()==4){
				temp.add(LoanPayType.DQBX.getPrompt());
			}
			temp.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(myInvestTender.getTenderDate()));
			//进度
			NumberUtil numberUtil=new NumberUtil();
			temp.add(numberUtil.percent(myInvestTender.getValidInvest().divide(myInvestTender.getLoanMoney())));			
			fieldData.add(temp);
		}
		ExportExcel(fieldName,fieldData,"投标中.xls");
    }
    //已回款
    public void MyInvestClean(InvestSearchJDO investSearchJDO){
    	PageResult<MyInvestClean> pageResult=investQueryService.queryMyInvestClean(investSearchJDO,
    			new PageCondition(0,investQueryService.queryMyInvestClean(investSearchJDO,new PageCondition()).getTotalCount()));
    	List<String> fieldName=Arrays.asList(new String[]{"项目名称","年利率","项目金额","投资金额","投资时间","回款金额","状态"});
		List<List<String>> fieldData = new ArrayList<List<String>>();
		for(MyInvestClean myInvestClean:pageResult.getData()){
			List<String> temp = new ArrayList<String>();
			temp.add(myInvestClean.getLoanNo());
			temp.add(myInvestClean.getLoanRate().toString()+"%");
			temp.add(myInvestClean.getLoanMoney().toString());
			temp.add(myInvestClean.getInvestMoney().toString());
			temp.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(myInvestClean.getInvestDate()));
			temp.add(myInvestClean.getReceivedMoney().toString());
			if(myInvestClean.getState()==5){
				temp.add("被转让");
			}else if(myInvestClean.getState()==7){
				temp.add("收益完成");
			}	
			fieldData.add(temp);
		}
		ExportExcel(fieldName,fieldData,"已回款.xls");
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
