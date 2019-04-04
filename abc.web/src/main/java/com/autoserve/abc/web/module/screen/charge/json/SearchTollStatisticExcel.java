package com.autoserve.abc.web.module.screen.charge.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.ChargeRecord;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.intf.cash.ChargeRecordService;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.ExcelFileGenerator;
import com.autoserve.abc.web.convert.ChargeRecordVOConverter;
import com.autoserve.abc.web.vo.charge.ChargeRecordVO;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class SearchTollStatisticExcel {

	@Autowired
	private HttpSession session;
	@Resource
	private HttpServletRequest request;
	@Resource
	private HttpServletResponse response;
	@Resource
	private LoanQueryService loanQueryService;
	@Resource
	private ChargeRecordService chargeRecordService;
	@Resource
	private GovernmentService governmentService;
	@Resource
	private UserService userService;

	private static Logger logger = LoggerFactory
			.getLogger(SearchTollStatisticExcel.class);

	public void execute(Context context, ParameterParser params, Navigator nav) {

		PageCondition pageCondition = new PageCondition(1, Integer.MAX_VALUE);
		//搜索条件
		Loan searchloan = new Loan();
		searchloan.setLoanNo(params.getString("project_number"));
		Integer loanCategory = params.getInt("type");
		if(!loanCategory.equals(0)){
			searchloan.setLoanCategory(LoanCategory.valueOf(Integer
					.valueOf(loanCategory)));
		}
		String chargefee_min = params.getString("chargefee_min");
		String charge_fee_max = params.getString("charge_fee_max");

		//查询
		PageResult<Loan> result = loanQueryService.querySearchLoanListByParam(
				searchloan, pageCondition, null, null,
				chargefee_min, charge_fee_max);
		List<Loan> queryResult = result.getData();
		List<Integer> loanIds = Lists.transform(queryResult,
				new Function<Loan, Integer>() {
					@Override
					public Integer apply(Loan input) {
						return input.getLoanId();
					}
				});
		ListResult<ChargeRecord> resultDO = new ListResult<ChargeRecord>();
		if (!CollectionUtils.isEmpty(loanIds)) {
			// 查处所有费用
			resultDO = chargeRecordService.queryChargeRecordByLoanId(loanIds);
		}
		List<ChargeRecordVO> list = new ArrayList<ChargeRecordVO>();

		for (Loan loan : queryResult) {

			ChargeRecordVO vo = new ChargeRecordVO();
			vo = ChargeRecordVOConverter.toChargeRecordVO(loan, vo);
			// 查出项目担保机构名称
			GovernmentDO governmentDO = governmentService.findById(
					loan.getLoanGuarGov()).getData();
			if (governmentDO != null) {
				vo.setGuarantee_institutions(governmentDO.getGovName());
			}
			// 借款人名称
			UserDO userDO = userService.findById(loan.getLoanUserId())
					.getData();
			if (userDO != null) {
				vo.setBorrower(userDO.getUserName());
			}

			for (ChargeRecord chargeRecord : resultDO.getData()) {
				if (chargeRecord.getLoanId().equals(loan.getLoanId())) {
					// 计算费用
					switch (chargeRecord.getFeeType()) {
					case PLA_FEE:
						vo.addChargefee(chargeRecord.getFee());
						break;
					case PLA_SERVE_FEE:
						vo.addServicefee(chargeRecord.getFee());
						break;
					case INSURANCE_FEE:
						vo.addServicefee(chargeRecord.getFee());
						break;
					case TRANSFER_FEE:
						vo.addChargefee(chargeRecord.getFee());
						break;
					case PURCHASE_FEE:
						vo.addChargefee(chargeRecord.getFee());
						break;
					}
				}
			}

			list.add(vo);
		}

		//excel
		List<String> fieldName = Arrays.asList(new String[] { "项目名称", "项目类型","借款人","借款金额","年化收益率","借款期限","收取服务费","收取手续费","开始日期","到期日期","担保机构" });
		List<List<String>> fieldData = new ArrayList<List<String>>();
		for (ChargeRecordVO cr : list) {
			List<String> temp = new ArrayList<String>();
			temp.add(cr.getProject_number());
			temp.add(cr.getProject_type());
			temp.add(cr.getBorrower());
			temp.add(cr.getBorrowing_amount()+"");
			temp.add(cr.getAnnual_rate()+"");
			temp.add(cr.getLoan_period());
			temp.add(cr.getService_fee()+"");
			temp.add(cr.getCharge_fee()+"");
			temp.add(cr.getStar_date());
			temp.add(cr.getEnd_date());
			temp.add(cr.getGuarantee_institutions());
			fieldData.add(temp);
		}
		ExportExcel(fieldName, fieldData, "费用统计.xls");

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
