package com.autoserve.abc.web.module.screen.reportAnalysis.json;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.FullTransferFundsSearchDO;
import com.autoserve.abc.service.biz.entity.TransferFundsDetailInfo;
import com.autoserve.abc.service.biz.intf.loan.fulltransfer.FullTransferQueryService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.ExcelFileGenerator;
import com.autoserve.abc.web.util.DateUtil;

/**
 * 划转资金详情导出excel
 * 
 * @author Administrator
 *
 */
public class TransferFundsStatisticsLookUpViewExcel {
	@Resource
	private HttpServletRequest request;
	@Resource
	private HttpServletResponse response;
	@Resource
	private FullTransferQueryService fullTransferQueryService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_FORMAT);

	public void execute(ParameterParser params) throws ParseException {
		FullTransferFundsSearchDO search = new FullTransferFundsSearchDO();
		//搜索条件
		String date_ = params.getString("tdate");//月
		Date date = sdf.parse(date_);
		PageCondition pageCondition = new PageCondition(1, Integer.MAX_VALUE);
		int bidType = params.getInt("len_loan_type");//正常标，转让标
        if (bidType == -1) {
            search.setBidType(null);
        } else {
            search.setBidType(bidType);
        }
        search.setLoanCategory(params.getInt("pro_product_type"));//标类型 4种
        
        search.setLoanNo(params.getString("pro_loan_no"));//标名称
        
		PageResult<TransferFundsDetailInfo> result = fullTransferQueryService
				.queryDetail(search, date, pageCondition);
		
		List<String> fieldName=Arrays.asList(new String[]{"项目名称","借款人","借款金额","年化收益率","项目类型","标种","放款金额","已收手续费","已收担保费","放款日期"});
		List<List<String>> fieldData = new ArrayList<List<String>>();
		for(TransferFundsDetailInfo td:result.getData()){
			List<String> temp = new ArrayList<String>();
			temp.add(td.getLoanNo());
			temp.add(td.getRealName());
			temp.add(td.getLoanMoney()+"");
			temp.add(td.getLoanRate()+"");
			temp.add(td.getProductName());
			temp.add(td.getLoanType()+"");
			temp.add(td.getLendMoney()+"");
			temp.add(td.getCollectFee()+"");
			temp.add(td.getCollectGuarFee()+"");
			temp.add(td.getLendDate());
			fieldData.add(temp);
		}
		ExportExcel(fieldName,fieldData,"资金划转统计表.xls");
	}

	// 导出
	public void ExportExcel(List<?> fieldName, List<?> fieldData, String name) {
		ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator(
				fieldName, fieldData);
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
