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
import com.autoserve.abc.dao.dataobject.UserRecommendDO;
import com.autoserve.abc.dao.intf.UserDao;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.ExcelFileGenerator;
/**
 * 
 */
public class AccountManagementReportExcel {
	@Resource
	private UserService userService;
	@Resource
	private UserDao userDao;
	@Autowired
    private HttpSession session;
	@Resource
	private HttpServletRequest request;
	@Resource
	private HttpServletResponse response;

	private static final Logger logger = LoggerFactory.getLogger(UserInvestInfoReportView.class);
	public void execute(Context context, ParameterParser params,Navigator nav) {
    	String name = params.getString("userName");
    	String realName = params.getString("userRealName");
    	String userState = params.getString("userState");
    	String userRecommendUserid = params.getString("userRecommendUserid");
		Date startRegisterDate = params.getDate("startRegisterDate", new SimpleDateFormat("yyyy-MM-dd"));
		Date endRegisterDate = params.getDate("endRegisterDate", new SimpleDateFormat("yyyy-MM-dd"));
		UserRecommendDO userRecommendDO = new UserRecommendDO();
    	try {
			if (name != null && !"".equals(name)) {
				userRecommendDO.setUserName(name);
			}
			if (realName != null && !"".equals(realName)) {
				userRecommendDO.setUserRealName(realName);
			}
			if (userState != null && !"".equals(userState)) {
				userRecommendDO.setUserState(Integer.parseInt(userState));
			}
			if (userRecommendUserid != null && !"".equals(userRecommendUserid)) {
				userRecommendDO.setRecommendUserName(userRecommendUserid);
			}
			if (startRegisterDate != null ) {
				userRecommendDO.setStartRegisterDate(startRegisterDate);
			}
			if (endRegisterDate != null ) {
				userRecommendDO.setEndRegisterDate(endRegisterDate);
			}
		} catch (Exception e) {
			logger.warn("客户信息－个人客户－搜索查询 查询参数解析失败");
		}
    	 //默认查询个人客户
        userRecommendDO.setUserType(UserType.PERSONAL.getType());
        PageResult<UserRecommendDO> result = userService.queryRecommendList(userRecommendDO, new PageCondition(0,userDao.countRecommendListByParam(userRecommendDO)));
    	
		List<String> fieldName=Arrays.asList(new String[]{"用户ID","客户名称","真实姓名","性别","证件类型","证件号码","手机号码","邮箱","免费提现额度","所在地","推荐人名称","推荐人用户名","注册日期","状态"});
		List<List<String>> fieldData = new ArrayList<List<String>>();
		for(UserRecommendDO report:result.getData()){
			List<String> temp = new ArrayList<String>();
			temp.add(report.getUserId().toString());
			temp.add(report.getUserName());
			temp.add(report.getUserRealName());
			String sex ="-";
			if (report.getUserSex() != null && report.getUserSex() == 0)
                 sex = "女";
            else if (report.getUserSex() != null && report.getUserSex() == 1)
                 sex = "男";
			temp.add(sex);
			temp.add(report.getUserDocType() == null ? "-" : report.getUserDocType());
			temp.add(report.getUserDocNo() == null ? "-" : report.getUserDocNo());
			temp.add(report.getUserPhone() == null ? "-" : report.getUserPhone());
			temp.add(report.getUserEmail() == null ? "-" : report.getUserEmail());
			temp.add(report.getUserCashQuota() == null ? "-" : report.getUserCashQuota().toString());
			temp.add(report.getUserNative() == null?"":report.getUserNative().toString());
			temp.add(report.getRecommendUserRealName() == null?"":report.getRecommendUserRealName().toString());
			temp.add(report.getRecommendUserName() == null?"":report.getRecommendUserName().toString());
			temp.add(report.getUserRegisterDate() == null?"":new SimpleDateFormat("yyyy-MM-dd").format(report.getUserRegisterDate()));
			temp.add(report.getUserState() == 1?"启用":"停用");
			
			fieldData.add(temp);
		}
		ExportExcel(fieldName,fieldData,"个人客户.xls");

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
