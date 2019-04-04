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
import com.autoserve.abc.dao.dataobject.LoanReportDO;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.ExcelFileGenerator;
import com.autoserve.abc.web.util.DateUtil;
/**
 * 
 * @author DS
 *
 * 2015上午10:13:43
 */
public class LoanReportExcel {
	@Resource
	private LoanQueryService loanQueryService;
	@Autowired
    private HttpSession session;
	@Resource
	private HttpServletRequest request;
	@Resource
	private HttpServletResponse response;
	
	private static final Logger logger = LoggerFactory.getLogger(LoanReportView.class);
    public void execute(Context context, ParameterParser params,Navigator nav) {
    	SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_TIME_FORMAT);
        Date startLoanDate = params.getDate("startLoanDate", sdf);
        Date endLoanDate = params.getDate("endLoanDate", sdf);
        LoanReportDO loanReportDO = new LoanReportDO();
		try {
			if(null != startLoanDate){
				loanReportDO.setStartLoanDate(startLoanDate);
	        }
	        if(null != endLoanDate){
	        	loanReportDO.setEndLoanDate(endLoanDate);
	        }
		} catch (Exception e) {
			logger.warn("参数解析失败");
		}
    	
    	PageResult<LoanReportDO> result = loanQueryService.queryLoanReport(loanReportDO, new PageCondition(0, loanQueryService.queryCountLoanReport(loanReportDO)));
    	
		List<String> fieldName=Arrays.asList(new String[]{"发标日期","项目编号","项目类型","借款人","借款用途","保证方式","还款方式","借款金额","满标金额","借款期限","年化率","招标到期日","满标日","借款到期日","付息日","实际借款天数","平台服务费","担保费","待还本金","待还利息","已还本金","已还利息","逾期本金","逾期利息","是否结清"});
		List<List<String>> fieldData = new ArrayList<List<String>>();
		for(LoanReportDO report:result.getData()){
			List<String> temp = new ArrayList<String>();
			temp.add(DateUtil.formatDate(report.getLoan_invest_starttime(),DateUtil.DATE_TIME_FORMAT));
			temp.add(report.getLoan_no());
			temp.add(report.getLoan_category());
			temp.add(report.getUser_name());
			temp.add(report.getLoan_use());
			temp.add(report.getGuaranty_mode());
			
			temp.add(report.getLoan_pay_type());
			temp.add(report.getLoan_money());
			temp.add(report.getLoan_current_valid_invest());
			temp.add(report.getLoan_period());
			temp.add(report.getLoan_rate());
			temp.add(DateUtil.formatDate(report.getLoan_invest_endtime(),DateUtil.DATE_TIME_FORMAT));
			temp.add(DateUtil.formatDate(report.getLoan_invest_fulltime(),DateUtil.DATE_TIME_FORMAT));
			temp.add(DateUtil.formatDate(report.getLoan_expire_date(),DateUtil.DATE_TIME_FORMAT));
			temp.add(report.getLoan_pay_date());
			
			temp.add(report.getCollecttime());
			temp.add(report.getService_fee());
			temp.add(report.getGuar_fee());
			temp.add(report.getPp_pay_capital());
			temp.add(report.getPp_pay_interest());
			temp.add(report.getPp_pay_collect_capital());
			temp.add(report.getPp_pay_collect_interest());
			
			temp.add(report.getYq_pay_capital());
			temp.add(report.getYq_pay_interest());
			temp.add(report.getJieqing());
			fieldData.add(temp);
		}
		ExportExcel(fieldName,fieldData,"项目发布统计表.xls");

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
