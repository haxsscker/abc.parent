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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RedReportDO;
import com.autoserve.abc.service.biz.intf.redenvelope.RedService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.ExcelFileGenerator;
import com.autoserve.abc.web.util.DateUtil;

/**
 * @author DS 2015上午10:13:43
 */
public class RedReportExcel {
    @Autowired
    private RedService          redService;
    @Autowired
    private HttpSession         session;
    @Resource
    private HttpServletRequest  request;
    @Resource
    private HttpServletResponse response;

    public void execute(Context context, ParameterParser params, Navigator nav) {
    	SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_FORMAT);
        Date startRedDate = params.getDate("startRedDate", sdf);
        Date endRedDate = params.getDate("endRedDate", sdf);
        RedReportDO redReportDO = new RedReportDO();
    	if(null != startRedDate){
        	redReportDO.setStartRedDate(startRedDate);
        }
        if(null != endRedDate){
        	redReportDO.setEndRedDate(endRedDate);
        }
        
        Date startRedUseDate = params.getDate("startRedUseDate", sdf);
        Date endRedUseDate = params.getDate("endRedUseDate", sdf);
    	if(null != startRedUseDate){
        	redReportDO.setStartRedUseTime(startRedUseDate);
        }
        if(null != endRedUseDate){
        	redReportDO.setEndRedUseTime(endRedUseDate);
        }
        
        String type = params.getString("type");
        if(StringUtils.isNotEmpty(type)){
        	redReportDO.setRs_type(type);
        }
        
        String state = params.getString("state");
        if(StringUtils.isNotEmpty(state)){
        	redReportDO.setRs_state(state);
        }
        
        String loan_no = params.getString("loan_no");
        redReportDO.setLoan_no(loan_no);
        
        
    	
        PageResult<RedReportDO> result = redService.redReport(redReportDO,new PageCondition(0, redService.redReportCount(redReportDO)));

        List<String> fieldName = Arrays.asList(new String[] { "派发日期", "派发类型", "派发来源", "派发金额", "注册用户名", "姓名", "手机号",
                "截止有效期", "有效标志", "使用红包项目代码", "使用红包金额", "投资金额","红包使用时间" });
        List<List<String>> fieldData = new ArrayList<List<String>>();
        for (RedReportDO redReport : result.getData()) {
            List<String> temp = new ArrayList<String>();
            temp.add(redReport.getRs_sendtime());
            temp.add(redReport.getRs_type_name());
            temp.add(redReport.getRs_theme());
            temp.add(redReport.getRs_amt());
            temp.add(redReport.getUser_name());
            temp.add(redReport.getUser_real_name());
            temp.add(redReport.getUser_phone());
            temp.add(redReport.getRs_closetime());
            temp.add(redReport.getRed_type_name());
            temp.add(redReport.getLoan_no());
            temp.add(redReport.getRu_amount());
            temp.add(redReport.getIn_invest_money());
            temp.add(redReport.getRedUseTime());
            fieldData.add(temp);
        }
        ExportExcel(fieldName, fieldData, "红包明细表.xls");

    }

    //导出
    public void ExportExcel(List<?> fieldName, List<?> fieldData, String name) {
        ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator(fieldName, fieldData);
        try {
            response.setCharacterEncoding("gb2312");
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String(name.getBytes("GB2312"), "iso8859-1"));
            response.setContentType("application/ynd.ms-excel;charset=UTF-8");
            excelFileGenerator.expordExcel(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
