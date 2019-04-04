package com.autoserve.abc.web.module.screen.reportAnalysis.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CashBorrowerViewDO;
import com.autoserve.abc.service.biz.entity.UserCompany;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.CashBorrowerService;
import com.autoserve.abc.service.biz.intf.user.UserCompanyService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.module.screen.BaseController;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.reportAnalysis.GuaranteeAgenciesVO;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class GuaranteeAgenciesExcel extends BaseController {
	@Resource
	private CashBorrowerService cashBorrower;
	@Resource
	private UserCompanyService userCompanyService;

	public JsonPageVO<GuaranteeAgenciesVO> execute(
			@Param("pro_user_name") String borrowerName, ParameterParser params) {

		PageCondition pageCondition = new PageCondition(1, Integer.MAX_VALUE);
		PageResult<CashBorrowerViewDO> result = cashBorrower.queryCashBorrower(
				borrowerName, pageCondition);
		List<CashBorrowerViewDO> cashBorrower = result.getData();
		JsonPageVO<GuaranteeAgenciesVO> resultVO = new JsonPageVO<GuaranteeAgenciesVO>();
		List<GuaranteeAgenciesVO> resultData = new ArrayList<GuaranteeAgenciesVO>();
		List<Integer> companyUserId = new ArrayList<Integer>();
		for (CashBorrowerViewDO cash : cashBorrower) {
			if (UserType.valueOf(cash.getUserType())
					.equals(UserType.ENTERPRISE)) {
				companyUserId.add(cash.getPpLoanee());
			}
		}
		Map<Integer, UserCompany> companyName = null;
		if (!companyUserId.isEmpty()) {
			ListResult<UserCompany> uc = userCompanyService
					.queryUserCompaniesByUserIds(companyUserId);
			if (!uc.isSuccess()) {
				resultVO.setSuccess(false);
				resultVO.setMessage(uc.getMessage());
				return resultVO;
			}
			List<UserCompany> userCompanies = uc.getData();
			companyName = Maps.uniqueIndex(userCompanies,
					new Function<UserCompany, Integer>() {
						@Override
						public Integer apply(UserCompany userCompany) {
							return userCompany.getUserId();
						}
					});
		}
		for (CashBorrowerViewDO cash : cashBorrower) {
			GuaranteeAgenciesVO temp = new GuaranteeAgenciesVO();
			temp.setProposer(cash.getUserName());
			if (UserType.valueOf(cash.getUserType())
					.equals(UserType.ENTERPRISE) && companyName != null) {
				UserCompany userCompany = companyName.get(cash.getPpLoanee());
				String company = null;
				if (userCompany != null) {
					company = userCompany.getCompanyName();
				}
				temp.setPro_user_name(company);
			} else {
				temp.setPro_user_name(cash.getUserName());
			}
			temp.setProposerRealName(cash.getUserRealName());
			temp.setProposertype(UserType.valueOf(cash.getUserType()).getDes());
			// 融资总额为应还本金
			temp.setRecharge_amount(cash.getPpPayCapital());
			temp.setRecovery_principal(cash.getPpPayCollectCapital());
			temp.setRecovery_interest(cash.getPpPayCollectInterest());
			temp.setRecovery_amount(cash.getPpPayCollectFine());
			// add by 夏同同  20160419
			temp.setRecovery_breach_amount(cash.getPpPayCollectBreachFine());
			temp.setPro_collect_service_fee(cash.getPpCollectServiceFee());
			temp.setPro_collect_guar_fee(cash.getPpCollectGuarFee());
			temp.setPro_collect_total(cash.getPpCollectTotal());
			temp.setForeclosure_investing(cash
					.getPpPayTotalMoney()
					.subtract(cash.getPpCollectTotal())
					.add(cash.getPpPayServiceFee().subtract(
							cash.getPpCollectServiceFee())));
			temp.setNot_pay_money(cash.getPpPayCapital().subtract(
					cash.getPpPayCollectCapital()));
			temp.setNot_pay_over_rate(cash.getPpRemainFine());
			temp.setNot_pay_rate(cash.getPpPayInterest().subtract(
					cash.getPpPayCollectInterest()));
			temp.setNot_pay_service_fee(cash.getPpPayServiceFee().subtract(
					cash.getPpCollectServiceFee()));
			resultData.add(temp);
		}
		// excel
		List<String> fieldName = Lists.newArrayList("申请人", "真实姓名", "申请人类型",
				"借款人", "融资总额", "已还本金", "已还利息", "已还逾期罚金","已还违约罚金", "已还服务费", "已还担保费",
				"已还款总额", "未还款总额", "未还本金", "未还利息", "未还服务费");
		List<List<Object>> fieldData = Lists.newArrayList();
		for (GuaranteeAgenciesVO ga : resultData) {
			List<Object> line = Lists.newArrayList();
			line.add(ga.getProposer());
			line.add(ga.getProposerRealName());
			line.add(ga.getProposertype());
			line.add(ga.getPro_user_name());
			line.add(ga.getRecharge_amount());// 融资总额
			line.add(ga.getRecovery_principal());
			line.add(ga.getRecovery_interest());
			line.add(ga.getRecovery_amount());
			line.add(ga.getRecovery_breach_amount());
			line.add(ga.getPro_collect_service_fee());
			line.add(ga.getPro_collect_guar_fee());
			line.add(ga.getPro_collect_total());
			line.add(ga.getForeclosure_investing());
			line.add(ga.getNot_pay_money());
			line.add(ga.getNot_pay_rate());
			line.add(ga.getNot_pay_service_fee());
			fieldData.add(line);
		}
		this.ExportExcel(fieldName, fieldData, "借款人资金对账表.xls");

		return resultVO;
	}
}
