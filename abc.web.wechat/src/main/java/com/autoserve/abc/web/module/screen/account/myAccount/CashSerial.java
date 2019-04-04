package com.autoserve.abc.web.module.screen.account.myAccount;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.RechargeService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.SystemGetPropeties;
import com.autoserve.abc.web.util.Pagebean;

/**
 * @ClassName: CashSerial
 * @Description: 交易记录查询
 * @author Wang Ming
 * @date 2015年5月12日 上午10:20:16
 */
public class CashSerial {
	@Autowired
	private HttpSession session;
	@Autowired
	private ToCashService tocashservice;
	@Autowired
	private RechargeService rechargeservice;
	@Autowired
	private InvestQueryService investQueryService;
	@Autowired
	private IncomePlanService incomePlanService;
	@Resource
	private DealRecordService dealrecordservice;
	@Resource
	private AccountInfoService accountinfoservice;

	public void execute(Context context, ParameterParser params) {

		User user = (User) session.getAttribute("user");
		// 空数据
		String startDate = null;
		String endDate = null;

		UserIdentity userIdentity = new UserIdentity();
		if (user.getUserType() == null || "".equals(user.getUserType())
				|| user.getUserType() == UserType.PERSONAL) {
			user.setUserType(UserType.PERSONAL);
		} else {
			user.setUserType(UserType.ENTERPRISE);
		}
		userIdentity.setUserType(user.getUserType());
		userIdentity.setUserId(user.getUserId());
		PlainResult<Account> account = accountinfoservice
				.queryByUserId(userIdentity);

		int currentPage = params.getInt("currentPage") + 1;
		int pageSize = 30;

		PageCondition pageCondition = new PageCondition(currentPage, pageSize);

		// 全部交易记录查询
		DealRecordDO dealrecorddo = new DealRecordDO();
		dealrecorddo.setDrPayAccount(account.getData().getAccountNo());
		String platformAcount = SystemGetPropeties
				.getStrString("PlatformMoneymoremore");
		//开户判断
		if (account.getData().getAccountNo() != null) {
			PageResult<DealRecordDO> result = dealrecordservice
					.queryDealByParams(dealrecorddo, pageCondition, startDate,
							endDate);

			for (DealRecordDO dealRecordDO : result.getData()) {
//				if (dealRecordDO.getDrDetailType() == 11) { // 平台手续费
//					dealRecordDO.setDrInnerSeqNo(dealRecordDO.getDrInnerSeqNo()
//							+ "333");
//				} else if (dealRecordDO.getDrDetailType() == 12) { // 担保服务费
//					dealRecordDO.setDrInnerSeqNo(dealRecordDO.getDrInnerSeqNo()
//							+ "111");
//				} else if (dealRecordDO.getDrDetailType() == 14) { // 转让手续费
//					dealRecordDO.setDrInnerSeqNo(dealRecordDO.getDrInnerSeqNo()
//							+ "222");
//				}
				dealRecordDO.createDrTypeStr(account.getData().getAccountNo());
			}
			Pagebean<DealRecordDO> pagebean = new Pagebean<DealRecordDO>(
					currentPage, pageSize, result.getData(),
					result.getTotalCount());

			context.put("payAccount", account.getData().getAccountNo());
			context.put("pagebean", pagebean);

			// 增加平台账户获取
			context.put("merCustId", platformAcount);
		}
	}

}
