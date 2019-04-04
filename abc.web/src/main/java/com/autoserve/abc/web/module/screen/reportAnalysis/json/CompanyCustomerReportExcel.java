package com.autoserve.abc.web.module.screen.reportAnalysis.json;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CompanyUserJDO;
import com.autoserve.abc.dao.intf.CompanyCustomerDao;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.ExcelFileGenerator;
/**
 * 
 */
public class CompanyCustomerReportExcel {
	@Resource
	private CompanyCustomerService companyCustomerService;
	@Resource
	private CompanyCustomerDao companyCustomerDao;
	@Autowired
    private HttpSession session;
	@Resource
	private HttpServletRequest request;
	@Resource
	private HttpServletResponse response;

	private static final Logger logger = LoggerFactory.getLogger(UserInvestInfoReportView.class);
	public void execute(Context context, ParameterParser params,Navigator nav) {
    	CompanyUserJDO companyUserJDO = new CompanyUserJDO();
    	String name = params.getString("ccCompanyName");
    	String referees = params.getString("referees");
    	String userState = params.getString("userState");
		Date startRegisterDate = params.getDate("startRegisterDate", new SimpleDateFormat("yyyy-MM-dd"));
		Date endRegisterDate = params.getDate("endRegisterDate", new SimpleDateFormat("yyyy-MM-dd"));
    	try {
            // 公司名称
			if (name != null && !"".equals(name)) {
				 companyUserJDO.setCcCompanyName(name);
			}
			// 状态
			if (userState != null && !"".equals(userState)) {
				companyUserJDO.setUserState(Integer.parseInt(userState));
			}
			// 推荐人
			if (referees != null && !"".equals(referees)) {
				//字段还没有，暂时不实现
			}
			if (startRegisterDate != null ) {
				companyUserJDO.setStartRegisterDate(startRegisterDate);
			}
			if (endRegisterDate != null ) {
				companyUserJDO.setEndRegisterDate(endRegisterDate);
			}
		} catch (Exception e) {
			logger.warn("参数解析失败");
		}
    	PageResult<CompanyUserJDO> result = companyCustomerService.queryList(companyUserJDO, new PageCondition(0,companyCustomerDao.countListCompanyUserByParam(companyUserJDO)));
    	
		List<String> fieldName=Arrays.asList(new String[]{"公司名称","法定代表人","注册资金","平台用户名","联系人","联系手机号","注册日期","状态"});
		List<List<String>> fieldData = new ArrayList<List<String>>();
		for(CompanyUserJDO report:result.getData()){
			List<String> temp = new ArrayList<String>();
			temp.add(report.getCcCompanyName().toString());
			temp.add(report.getCcCorporate());
			temp.add(report.getCcRegisterCapital() == null ?"":report.getCcRegisterCapital().toString());
			temp.add(report.getUserName());
			temp.add(report.getCcContactName() == null ?"":report.getCcContactName().toString());
			temp.add(report.getCcContactPhone());
			temp.add(report.getCcRegisterDate()== null ?"":new SimpleDateFormat("yyyy-MM-dd").format(report.getCcRegisterDate()));
			temp.add(report.getUserState() == 1?"启用":"停用");
			
			fieldData.add(temp);
		}
		ExportExcel(fieldName,fieldData,"公司客户.xls");

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
