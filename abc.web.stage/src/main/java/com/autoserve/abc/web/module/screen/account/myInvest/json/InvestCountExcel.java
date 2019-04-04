package com.autoserve.abc.web.module.screen.account.myInvest.json;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.autoserve.abc.dao.dataobject.stage.myinvest.InvestStatistics;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.cash.CashInvesterService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.ExcelFileGenerator;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.DateUtil;
/**
 * 
 * @author DS
 *
 * 2015下午8:37:05
 */
public class InvestCountExcel {
	@Autowired
	private DeployConfigService deployConfigService;
	@Autowired
    private HttpSession session;
	@Autowired
	private InvestQueryService investQueryService;
	@Autowired
	private CashInvesterService cashInvesterService;
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
    	 PageResult<InvestStatistics>  pageResult=investQueryService.queryInvestStatistics(user.getUserId(),
    			 new PageCondition(0,investQueryService.queryInvestStatistics(user.getUserId(),new PageCondition()).getTotalCount()));
    	List<String> fieldName=Arrays.asList(new String[]{"项目标题","已回本金","已回利息","待回款","待回利息","待回本金","到期日","总额"});
 		List<List<String>> fieldData = new ArrayList<List<String>>();
 		for(InvestStatistics investStatistics:pageResult.getData()){
 			List<String> temp = new ArrayList<String>();
 			temp.add(investStatistics.getLoanNo());
 			temp.add(investStatistics.getAlreadyCapital().toString());
 			temp.add(investStatistics.getAlreadyInterest().toString());
 			temp.add(investStatistics.getWaitTotalMoney().toString());
 			temp.add(investStatistics.getWaitInterest().toString());
 			temp.add(investStatistics.getWaitCapital().toString());
 			temp.add(DateUtil.formatDate(investStatistics.getLoanExpireDate(),DateUtil.DATE_FORMAT));
 			temp.add(investStatistics.getTotalMoney().toString());
 			fieldData.add(temp);
 		}
 		ExportExcel(fieldName,fieldData,"回款中的标.xls");
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
