package com.autoserve.abc.service.biz.impl.loan;

import java.math.BigDecimal;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.InvestSet;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.InvestSetOpenState;
import com.autoserve.abc.service.biz.enums.LoanPeriodUnit;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.invest.InvestSetService;
import com.autoserve.abc.service.biz.intf.loan.AutoInvestLoanService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

@Service
public class AutoInvestLoanServiceImpl implements AutoInvestLoanService {

	private static final Logger logger = LoggerFactory
			.getLogger(AutoInvestLoanServiceImpl.class);

	@Resource
	private LoanQueryService loanQueryService;
	@Resource
	private InvestSetService investSetService;
	@Resource
	private DoubleDryService DoubleDryService;
	@Resource
	private InvestService investService;
	@Resource
	private AccountInfoService accountInfoService;
	@Resource
	private InvestQueryService investQueryService;
	@Resource
	private DealRecordService dealRecordService;

	@Override
	public void run(Integer loanId) {
		// 查询开启自动投资的设置
		InvestSet investSet = new InvestSet();
		investSet.setIsOpen(InvestSetOpenState.STATE_ENABLE);
		ListResult<InvestSet> resultInvestSet = this.investSetService
				.queryInvest(investSet);
		// checkAutomaticInvest(resultInvestSet);
		// 重新查一次，防止有更新
		// resultInvestSet = this.investSetService.queryInvest(investSet);
		automaticInvest(resultInvestSet, loanId);
	}

	/**
	 * 检查保留金额
	 * 
	 * @param resultInvestSet
	 */
	// private void checkAutomaticInvest(ListResult<InvestSet> resultInvestSet)
	// {
	// for (InvestSet investSetResult : resultInvestSet.getData()) {
	// UserIdentity userIdentity = new UserIdentity();
	// userIdentity.setUserId(investSetResult.getUserId());
	// userIdentity.setUserType(UserType.PERSONAL);
	// PlainResult<AccountInfoDO> result = accountInfoService
	// .queryByUserIdentity(userIdentity);
	// if (result.getData() != null) {
	// Double[] userMonerys = this.DoubleDryService.queryBalance(
	// result.getData().getAccountMark(), "1");
	// Double userMonery = userMonerys[1];
	// if (BigDecimal.valueOf(userMonery)
	// .subtract(investSetResult.getSettMoney())
	// .compareTo(investSetResult.getInvestMoney()) == -1) {
	// investSetResult.setIsOpen(InvestSetOpenState.STATE_DISABLE);
	// this.investSetService.modifyInvest(investSetResult);
	// }
	//
	// }
	// }
	// }

	/**
	 * 查询用户余额
	 * 
	 * @param userId
	 * @return
	 */
	private BigDecimal findUserBalance(Integer userId) {
		UserIdentity userIdentity = new UserIdentity();
		userIdentity.setUserId(userId);
		userIdentity.setUserType(UserType.PERSONAL);
		PlainResult<AccountInfoDO> result = accountInfoService
				.queryByUserIdentity(userIdentity);
		if (result.getData() != null) {
			Double[] userMonerys = this.DoubleDryService.queryBalance(result
					.getData().getAccountMark(), "1");
			return new BigDecimal(userMonerys[1] + "");
		}
		return BigDecimal.ZERO;
	}

	/**
	 * 是否跳过这个investSet
	 * 
	 * @param investSet
	 * @param loan
	 * @return
	 */
	private boolean skip(InvestSet investSet, Loan loan) {
		// 判断标是否是自己的
		if (loan.getLoanUserId() == investSet.getUserId()) {
			return true;
		}
		// 判断该用户是否投资过此标，如果投资跳过
		ListResult<Invest> invests = investService.findListByParam(loan
				.getLoanId());
		if (invests.getData().size() > 0) {
			for (Invest in : invests.getData()) {
				if (in.getUserId().equals(investSet.getUserId())) {
					return true;
				}
			}
		}
		if (!checkInvest(loan, investSet)) {
			return true;
		}
		return false;
	}

	/**
	 * 自动投标
	 * 
	 * @param resultInvestSet
	 */
	public void automaticInvest(ListResult<InvestSet> resultInvestSet,
			int loanId) {
		for (InvestSet investSet : resultInvestSet.getData()) {
			Loan loan = loanQueryService.queryById(loanId).getData();
			// 不是招标中的标，直接结束
			if (!LoanState.BID_INVITING.equals(loan.getLoanState())) {
				return;
			}
			if (this.skip(investSet, loan)) {
				continue;
			}
			Invest inv = new Invest();
			inv.setUserId(investSet.getUserId());
			inv.setBidType(BidType.COMMON_LOAN);
			inv.setBidId(loan.getLoanId());
			inv.setOriginId(loan.getLoanId());
			BigDecimal investMoney = calcInvestMoney(loan, investSet);
			if (investMoney.compareTo(BigDecimal.ZERO) <= 0) {
				continue;
			}
			inv.setInvestMoney(investMoney);
			// 调用投资接口
			PlainResult<Integer> result = investService.createInvest(inv, null);
			if (result.isSuccess()) {
				// 禁用investSet
				InvestSet investSetModify = new InvestSet();
				investSetModify.setId(investSet.getId());
				investSetModify.setIsOpen(InvestSetOpenState.STATE_DISABLE);

				investSetService.modifyInvest(investSetModify);
				logger.debug("自动投标：loanId：{},investUserId:{}, invetMoney:{}",
						loan.getLoanId(), inv.getUserId(), inv.getInvestMoney());
			}

		}
	}

	/**
	 * 计算投资多少钱
	 * 
	 * @param loan
	 * @param set
	 * @return 不可投时，返回0
	 */
	private BigDecimal calcInvestMoney(Loan loan, InvestSet set) {
		BigDecimal balance = this.findUserBalance(set.getUserId());
		// 账户可投金额
		BigDecimal balanceCan = balance.subtract(set.getSettMoney());
		// 标可投金额
		BigDecimal loanCan = loan.getLoanMoney().subtract(
				loan.getLoanCurrentValidInvest());
		BigDecimal invest = balanceCan;
		if (balanceCan.compareTo(loanCan) > 0) {
			invest = loanCan;
		}
		// 用户最高投资金额限制
		if (invest.compareTo(set.getInvestMoney()) > 0) {
			invest = set.getInvestMoney();
		}
		// 标最小金额限制
		if (invest.compareTo(loan.getLoanMinInvest()) < 0) {
			return BigDecimal.ZERO;
		}
		// 标最大金额限制
		if (invest.compareTo(loan.getLoanMaxInvest()) > 0) {
			invest = loan.getLoanMaxInvest();
		}
		return invest;
	}

	/**
	 * 检查项目是否符合自动投标设置的条件
	 * 
	 * @param loan
	 * @param investSetResult
	 * @return
	 */
	public boolean checkInvest(Loan loan, InvestSet investSetResult) {
		int flag;
		if (loan == null || investSetResult == null) {
			logger.error("项目或自动投标设置没有找到！");
			return false;
		}
		// 判断项目最大金额
		if (loan.getLoanMoney().compareTo(investSetResult.getMaxMoney()) > 0) {
			return false;
		}

		// 判断项目最小金额
		flag = loan.getLoanMoney().compareTo(investSetResult.getMinMoney());

		if (flag == 1) {
			// 判断项目类型
			if (loan.getLoanCategory()
					.equals(investSetResult.getLoanCategory())) {
				// 判断项目年利率
				flag = loan.getLoanRate().compareTo(
						investSetResult.getMaxRate());

				if (flag == -1 || flag == 0) {
					// 判断最小年利率
					flag = loan.getLoanRate().compareTo(
							investSetResult.getMinRate());
					if (flag == 1 || flag == 0) {
						// 判断借款期限
						int loanPeriod = loan.getLoanPeriod();
						if (loan.getLoanPeriodUnit()
								.equals(LoanPeriodUnit.YEAR)) {
							loanPeriod = loan.getLoanPeriod() * 12;
						}
						if (loan.getLoanPeriodUnit().equals(LoanPeriodUnit.DAY)) {
							loanPeriod = loan.getLoanPeriod() / 30;
						}
						if (investSetResult.getMinLoanPeriod() <= loanPeriod) {
							if (loanPeriod <= investSetResult
									.getMaxLoanPeriod()) {
								if (loan.getLoanPayType().equals(
										investSetResult.getLoanType())) {
									return true;
								}
							}
						}
					}
				}
			}
		}

		return false;
	}
}
