package com.autoserve.abc.web.module.screen.reportAnalysis.json;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CashInvesterViewDO;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.CashInvesterService;
import com.autoserve.abc.service.biz.intf.cash.UserAccountService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.module.screen.BaseController;
import com.autoserve.abc.web.vo.reportAnalysis.InvestorCaptialVO;
import com.google.common.collect.Lists;

public class InvestorCapitalExcel extends BaseController {
	@Resource
	private CashInvesterService cashInvesterService;

	@Resource
	private UserAccountService userAccountService;

	@Resource
	private AccountInfoService accountInfoService;

	@Resource
	private InvestService investService;

	public void execute(ParameterParser params) {

		String investorName = null;
		String investorRealName = params.getString("cst_real_name");

		PageCondition pageCondition = new PageCondition(1, Integer.MAX_VALUE);
		PageResult<CashInvesterViewDO> queryResult = cashInvesterService
				.queryCashInvester(investorName, investorRealName,
						pageCondition);
		// if (!queryResult.isSuccess()) {
		// resultVO.setMessage(queryResult.getMessage());
		// resultVO.setTotal(0);
		// resultVO.setRows(new ArrayList<InvestorCaptialVO>());
		// return resultVO;
		// }
		List<CashInvesterViewDO> queryData = queryResult.getData();
		List<UserIdentity> userIds = new ArrayList<UserIdentity>();
		for (CashInvesterViewDO civd : queryData) {
			UserIdentity ui = new UserIdentity();
			ui.setUserId(civd.getAccountUserId());
			ui.setUserType(UserType.valueOf(civd.getAccountUserType()));
			userIds.add(ui);
		}
//		List<String> accountNos = Lists.transform(queryData,
//				new Function<CashInvesterViewDO, String>() {
//					@Override
//					public String apply(CashInvesterViewDO ac) {
//						return ac.getAccountNo();
//					}
//
//				});
//		ListResult<UserAccountDO> ua = userAccountService
//				.queryByAccountNo(accountNos);
		// if (!ua.isSuccess()) {
		// resultVO.setMessage(ua.getMessage());
		// resultVO.setTotal(0);
		// resultVO.setRows(new ArrayList<InvestorCaptialVO>());
		// return resultVO;
		// }
//		List<UserAccountDO> userAccounts = ua.getData();
//		Map<String, UserAccountDO> userAccountMap = Maps.uniqueIndex(
//				userAccounts, new Function<UserAccountDO, String>() {
//
//					@Override
//					public String apply(UserAccountDO ua) {
//						return ua.getUaAccountNo();
//					}
//
//				});
		List<InvestorCaptialVO> resultList = new ArrayList<InvestorCaptialVO>();
		for (CashInvesterViewDO civd : queryData) {
			// if (userAccountMap.get(civd.getAccountNo()) == null) {
			// continue;
			// }
			InvestorCaptialVO temp = new InvestorCaptialVO();
			temp.setCustomer_name(civd.getAccountUserName());
			temp.setReal_name(civd.getUserRealName());

			temp.setPurchase_money(civd.getBlBuyTotal());
			temp.setPurchasefee(civd.getBlFee());
			temp.setPro_collect_money(civd.getPiCollectCapital());
			temp.setPro_collect_over_rate(civd.getPiCollectFine());
			temp.setPro_collect_rate(civd.getPiCollectInterest());
			temp.setPro_invest_money(civd.getInValidInvestMoney());

			temp.setTransfer_money(investService.zrzq(civd.getAccountUserId()));
			temp.setTransfer_fee(civd.getTlTransferFee());// 转让手续费

			temp.setBuy_money(investService.mrzq(civd.getAccountUserId()));
			resultList.add(temp);
		}

		// excel
		List<String> fieldName = Lists.newArrayList("客户名称", "真实姓名", "回收本金",
				"回收利息", "回收罚息", "投资金额", "买入债权", "转出债权", "转让手续费");
		List<List<Object>> fieldData = Lists.newArrayList();
		for (InvestorCaptialVO ga : resultList) {
			List<Object> line = Lists.newArrayList();
			line.add(ga.getCustomer_name());
			line.add(ga.getReal_name());
			line.add(ga.getPro_collect_money());
			line.add(ga.getPro_collect_rate());//回收利息
			line.add(ga.getPro_collect_over_rate());
			line.add(ga.getPro_invest_money());
			line.add(ga.getBuy_money());
			line.add(ga.getTransfer_money());
			line.add(ga.getTransfer_fee());
			fieldData.add(line);
		}
		this.ExportExcel(fieldName, fieldData, "投资人资金对账表.xls");

	}
}
