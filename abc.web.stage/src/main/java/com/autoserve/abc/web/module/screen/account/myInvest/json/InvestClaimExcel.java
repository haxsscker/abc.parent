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
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyCreditClean;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyCreditReceived;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyCreditTender;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.Arith;
import com.autoserve.abc.service.util.ExcelFileGenerator;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.NumberUtil;
/**
 * 
 * @author DS
 *
 * 2015下午7:09:52
 */
public class InvestClaimExcel {
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
    	String type=(String)params.getString("type");
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
    	if(type!=null && "HKZ".equals(type)){
    		MyCreditReceived(investSearchJDO);
    	}
    	if(type!=null && "TBZ".equals(type)){
    		MyCreditTender(investSearchJDO);
    	}
    	if(type!=null && "YJQ".equals(type)){
    		MyCreditClean(investSearchJDO);
    	}
    }
    
    //回款中
    public void MyCreditReceived(InvestSearchJDO investSearchJDO){
    	//未支付
    	PageResult<MyCreditReceived> pageResult=investQueryService.queryMyCreditReceived(investSearchJDO, 
    			new PageCondition(0,investQueryService.queryMyCreditReceived(investSearchJDO,new PageCondition()).getTotalCount()));		
		List<String> fieldName=Arrays.asList(new String[]{"项目名称","年利率","转让金额","成交日期","已回款","待回款","已转让"});
		List<List<String>> fieldData = new ArrayList<List<String>>();
		for(MyCreditReceived myCreditReceived:pageResult.getData()){
			List<String> temp = new ArrayList<String>();
			temp.add(myCreditReceived.getTransferLoanNo());
			temp.add(myCreditReceived.getLoanRate().toString()+"%");
			temp.add(myCreditReceived.getTransferLoanMoney().toString());
			temp.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(myCreditReceived.getInvestDate()));
			temp.add(myCreditReceived.getCleanMoney().toString());
			temp.add(myCreditReceived.getReceivedMoney().toString());
			temp.add(myCreditReceived.getTransferMoney().toString());
			fieldData.add(temp);
		}
		ExportExcel(fieldName,fieldData,"回款中.xls");
    }
    //投标中
    public void MyCreditTender(InvestSearchJDO investSearchJDO){
    	PageResult<MyCreditTender>  pageResult=investQueryService.queryMyCreditTender(investSearchJDO,
    			new PageCondition(0,investQueryService.queryMyCreditTender(investSearchJDO,new PageCondition()).getTotalCount()));
    	List<String> fieldName=Arrays.asList(new String[]{"项目名称","年利率","转让金额","已转让金额","投标金额","投资日期","进度"});
		List<List<String>> fieldData = new ArrayList<List<String>>();
		for(MyCreditTender myCreditTender:pageResult.getData()){
			List<String> temp = new ArrayList<String>();
			temp.add(myCreditTender.getTransferLoanNo());
			temp.add(myCreditTender.getLoanRate().toString()+"%");
			temp.add(myCreditTender.getTransferLoanMoney().toString());	
			temp.add(myCreditTender.getCurrentMoney().toString());
			temp.add(myCreditTender.getTransferMoney().toString());
			temp.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(myCreditTender.getCreateDate()));
			//进度
			temp.add(Arith.calcPercent(myCreditTender.getCurrentMoney(), myCreditTender.getTransferLoanMoney()).intValue()+"%");
			fieldData.add(temp);
		}
		ExportExcel(fieldName,fieldData,"投标中.xls");
    }
    //已回款
    public void MyCreditClean(InvestSearchJDO investSearchJDO){
    	PageResult<MyCreditClean> pageResult=investQueryService.queryMyCreditClean(investSearchJDO, 
    			new PageCondition(0,investQueryService.queryMyCreditClean(investSearchJDO,new PageCondition()).getTotalCount()));
    	List<String> fieldName=Arrays.asList(new String[]{"项目名称","年利率","债权总价","转让价格","转让时间","结清日期"});
		List<List<String>> fieldData = new ArrayList<List<String>>();
		for(MyCreditClean myCreditClean:pageResult.getData()){
			List<String> temp = new ArrayList<String>();
			temp.add(myCreditClean.getTransferLoanNo());
			temp.add(myCreditClean.getLoanRate().toString()+"%");
			temp.add(myCreditClean.getCreditTotalMoney().toString());
			temp.add(myCreditClean.getTransferMoney().toString());
			temp.add(new SimpleDateFormat("yyyy-MM-dd").format(myCreditClean.getTransferDate()));
			temp.add(new SimpleDateFormat("yyyy-MM-dd").format(myCreditClean.getEndDate()));
			fieldData.add(temp);
		}
		ExportExcel(fieldName,fieldData,"已结清.xls");
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
