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
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.UserInvestInfoReportDO;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.ExcelFileGenerator;
/**
 * 
 * @author DS
 *
 * 2015上午10:13:43
 */
public class UserInvestInfoReportExcel {
	@Resource
	private UserService userService;
	@Autowired
    private HttpSession session;
	@Resource
	private HttpServletRequest request;
	@Resource
	private HttpServletResponse response;

	private static final Logger logger = LoggerFactory.getLogger(UserInvestInfoReportView.class);
	public void execute(Context context, ParameterParser params,Navigator nav) {
    	UserDO user = new UserDO();
    	String name = params.getString("name");
		Date startRegisterDate = params.getDate("startRegisterDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		Date endRegisterDate = params.getDate("endRegisterDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    	try {
			if (name != null && !"".equals(name)) {
				user.setUserRealName(name);
			}
			if (startRegisterDate != null ) {
				user.setStartRegisterDate(startRegisterDate);
			}
			if (endRegisterDate != null ) {
				user.setEndRegisterDate(endRegisterDate);
			}
		} catch (Exception e) {
			logger.warn("参数解析失败");
		}
    	PageResult<UserInvestInfoReportDO> result = userService.queryUserInvestInfo(user, new PageCondition(0, userService.countQueryUserInvestInfo(user)));
    	
		List<String> fieldName=Arrays.asList(new String[]{"注册用户名","姓名","身份证号","手机号","邮箱","注册时间","推荐人用户名","推荐人姓名","推荐人手机","投资合计","已收本金","已收利息","侍收本金","侍收利息","借款合计","已还本金","已还利息","侍还本金","侍还利息","邮编","家庭电话","所在省市","联系地址"});
		List<List<String>> fieldData = new ArrayList<List<String>>();
		for(UserInvestInfoReportDO report:result.getData()){
			List<String> temp = new ArrayList<String>();
			temp.add(report.getUser_name());
			temp.add(report.getUser_real_name());
			temp.add(report.getUser_doc_no());
			temp.add(report.getUser_phone());
			temp.add(report.getUser_email());
			temp.add(report.getUser_register_date());
			temp.add(report.getTj_user_name());
			temp.add(report.getTj_user_real_name());
			temp.add(report.getTj_user_phone());
			temp.add(report.getTotal_invest_money() == null?"":report.getTotal_invest_money().toString());
			temp.add(report.getIncome_capital() == null?"":report.getIncome_capital().toString());
			temp.add(report.getIncome_interest() == null?"":report.getIncome_interest().toString());
			temp.add(report.getIncome_ds_capital() == null?"":report.getIncome_ds_capital().toString());
			temp.add(report.getIncome_ds_interest() == null?"":report.getIncome_ds_interest().toString());
			temp.add(report.getTotal_valid_invest() == null?"":report.getTotal_valid_invest().toString());
			temp.add(report.getPay_capital() == null?"":report.getPay_capital().toString());
			temp.add(report.getPay_interest() == null?"":report.getPay_interest().toString());
			temp.add(report.getPay_dh_capital() == null?"":report.getPay_dh_capital().toString());
			temp.add(report.getPay_dh_interest() == null?"":report.getPay_dh_interest().toString());
			temp.add(report.getPost());
			temp.add(report.getHomePhone());
			temp.add(report.getCity());
			temp.add(report.getAddress());
			
			fieldData.add(temp);
		}
		ExportExcel(fieldName,fieldData,"注册用户明细表.xls");

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
