package com.autoserve.abc.service.biz.impl.loan.repay;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.cglib.beans.BeanCopier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AheadRepay;
import com.autoserve.abc.dao.dataobject.IncomePlanDO;
import com.autoserve.abc.dao.dataobject.PaymentPlanDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.enums.AheadRepayState;
import com.autoserve.abc.dao.intf.AheadRepayDao;
import com.autoserve.abc.dao.intf.IncomePlanDao;
import com.autoserve.abc.dao.intf.PaymentPlanDao;
import com.autoserve.abc.dao.intf.UserDao;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.enums.IncomePlanState;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.PayState;
import com.autoserve.abc.service.biz.enums.PayType;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.AuthorizeUtil;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.repay.AheadRepayService;
import com.autoserve.abc.service.biz.intf.loan.repay.RepayService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.DateUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class DmAheadRepayServiceImpl implements AheadRepayService {
	private static final Log      log = LogFactory.getLog(ChinapnrRepayServiceImpl.class);
	@Autowired
	private PaymentPlanDao paymentPlanDao;
	@Autowired
	private IncomePlanDao incomePlanDao;
	@Autowired
	private RepayService repayService;
	@Autowired
	private AheadRepayDao aheadRepayDao;
	@Autowired
	private LoanService loanService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private AccountInfoService accountInfoService;
	@Resource
	private DoubleDryService doubleDryService;
	@Autowired
	private SysConfigService sysConfigService;

	@Override
	public BaseResult apply(AheadRepay aheadRepay) {

		BaseResult result = new BaseResult();
		Loan loan = loanService.findLoanById(aheadRepay.getLoanId()).getData();

		AheadRepay t = new AheadRepay();
		t.setLoanId(aheadRepay.getLoanId());
		List<AheadRepayState> states = Lists.newArrayList(
				AheadRepayState.WAIT_AUDIT, AheadRepayState.AUDIT_PASS);
		t.setSearchState(states);
		t = aheadRepayDao.findOne(t);
		if (t != null) {
			result.setSuccess(false);
			result.setMessage("已存在提前还款申请，不可重复申请");
			return result;
		}

		// 查询还款计划
		List<PaymentPlanDO> paymentList = paymentPlanDao
				.findByLoanId(aheadRepay.getLoanId());
		result = this.check(paymentList ,loan);
		if(!result.isSuccess()){
			return result;
		}
		UserDO userDO = userDao.findById(aheadRepay.getUserId());
		// 计算应付本金，利息
		BigDecimal shouldCapital = this.calcShouldPayCapital(paymentList);
		BigDecimal shouldInterest = this.calcShouldPayInterest(paymentList);
		//计算应付平台服务费
		BigDecimal shouldServeFee = this.calcShouldPayServeFee(paymentList);
		
		BigDecimal allMoney = shouldCapital.add(shouldInterest).add(shouldServeFee);
		//还款授权
		Date authorizeRepayStartDate=userDO.getAuthorizeRepayStartDate();
		Date authorizeRepayEndDate=userDO.getAuthorizeRepayEndDate();
		BigDecimal authorizeRepayAmount=null != userDO.getAuthorizeRepayAmount()?userDO.getAuthorizeRepayAmount():BigDecimal.ZERO;
		if(!"60".equals(userDO.getAuthorizeRepayType())){
        	return result.setError(CommonResultCode.BIZ_ERROR, "借款人还未开启还款授权，请先去授权！");
		}else if(!"有效".equals(AuthorizeUtil.isAuthorize(authorizeRepayStartDate,authorizeRepayEndDate))){
			return result.setError(CommonResultCode.BIZ_ERROR, "借款人还款授权"+AuthorizeUtil.isAuthorize(authorizeRepayStartDate,authorizeRepayEndDate)+",请去修改！");
		}else if(allMoney.doubleValue()>authorizeRepayAmount.doubleValue()){
			return result.setError(CommonResultCode.BIZ_ERROR, "还款金额超过借款人的还款授权金额,请去修改！");
		}
		BigDecimal avlBalance = doubleDryService.queryRZAvlBalance(aheadRepay.getUserId());
		if (avlBalance.compareTo(allMoney) < 0) {
			result.setSuccess(false);
			result.setMessage("余额不足");
			return result;
		}
		// add by 夏同同  20160415  新增录入金额信息，用于后台展示
		aheadRepay.setShouldCapital(shouldCapital);
		aheadRepay.setShouldInterest(shouldInterest);
		aheadRepay.setShouldServeFee(shouldServeFee);
		aheadRepay.setShouldAll(allMoney);
		aheadRepay.setState(AheadRepayState.WAIT_AUDIT);
		aheadRepay.setCreateDate(new Date());
		aheadRepayDao.insert(aheadRepay);
		return result;
	}

	@Override
	public BigDecimal calcShouldPayCapital(List<PaymentPlanDO> list) {
		BigDecimal capital = BigDecimal.ZERO;
		for (PaymentPlanDO p : list) {
			if (p.getPpPayState() == PayState.UNCLEAR.state) {
				capital = capital.add(p.getPpPayCapital());
			}
		}
		return capital;
	}

	@Override
	public BigDecimal calcShouldPayInterest(List<PaymentPlanDO> paymentList) {
		Date prevPayDate = this.getPrevPaymentDate(paymentList);
		PaymentPlanDO nextPayment = this.getNextPayment(paymentList);
		BigDecimal allInterest = nextPayment.getPpPayInterest();
		
		//提前还款利息天数
		int shouldPayDayConfig = 30;
		PlainResult<SysConfig> aheadPayDay = sysConfigService.querySysConfig(SysConfigEntry.AHEAD_PAY_DAY);
        if (aheadPayDay.isSuccess()) {
        	shouldPayDayConfig = Integer.parseInt(aheadPayDay.getData().getConfValue());
        }
		
		//有还款行为则按原逻辑计算
		if(null != prevPayDate)
		{
			int shouldPayDay = DateUtil.substractDay(new Date(), prevPayDate);
			if(shouldPayDay<=0){
				return BigDecimal.ZERO;//当期已提前还掉，利息为0
			}
			int allDay = DateUtil.substractDay(nextPayment.getPpPaytime(),
					prevPayDate);
			// 应付利息
			return allInterest.multiply(new BigDecimal(shouldPayDay)).divide(
							new BigDecimal(allDay), 2, BigDecimal.ROUND_HALF_UP);
		}
		//否则按不满30天算30天利息
		else
		{
			prevPayDate = paymentList.get(0).getPpCreateTime();
			int shouldPayDay = DateUtil.substractDay(new Date(), prevPayDate);
			if (shouldPayDay<=0)
			{
				return BigDecimal.ZERO;//当期已提前还掉，利息为0
			}
			else if (shouldPayDay < shouldPayDayConfig)
			{
				shouldPayDay = shouldPayDayConfig;
				int allDay = DateUtil.substractDay(nextPayment.getPpPaytime(),
						prevPayDate);
				// 应付利息
				return allInterest.multiply(new BigDecimal(shouldPayDay)).divide(
								new BigDecimal(allDay), 2, BigDecimal.ROUND_HALF_UP);
			}
			else 
			{
				int allDay = DateUtil.substractDay(nextPayment.getPpPaytime(),
						prevPayDate);
				// 应付利息
				return allInterest.multiply(new BigDecimal(shouldPayDay)).divide(
								new BigDecimal(allDay), 2, BigDecimal.ROUND_HALF_UP);
			}
		}
	}
	/**
	 * 服务费按年华率计算
	 */
	@Override
	public BigDecimal calcShouldPayServeFee(List<PaymentPlanDO> paymentList) {
		Date prevPayDate = this.getPrevPaymentDate(paymentList);
		PaymentPlanDO nextPayment = this.getNextPayment(paymentList);
		BigDecimal allServiceFee = nextPayment.getPpPayServiceFee();
		
		//提前还款利息天数
		int shouldPayDayConfig = 30;
		PlainResult<SysConfig> aheadPayDay = sysConfigService.querySysConfig(SysConfigEntry.AHEAD_PAY_DAY);
        if (aheadPayDay.isSuccess()) {
        	shouldPayDayConfig = Integer.parseInt(aheadPayDay.getData().getConfValue());
        }
		
		//有还款行为则按原逻辑计算
		if(null != prevPayDate)
		{
			int shouldPayDay = DateUtil.substractDay(new Date(), prevPayDate);
			if(shouldPayDay<=0){
				return BigDecimal.ZERO;//当期已提前还掉，利息为0
			}
			int allDay = DateUtil.substractDay(nextPayment.getPpPaytime(),
					prevPayDate);
			// 应付服务费
			return allServiceFee.multiply(new BigDecimal(shouldPayDay)).divide(
							new BigDecimal(allDay), 2, BigDecimal.ROUND_HALF_UP);
		}
		//否则按不满30天算30天服务费
		else
		{
			prevPayDate = paymentList.get(0).getPpCreateTime();
			int shouldPayDay = DateUtil.substractDay(new Date(), prevPayDate);
			if (shouldPayDay<=0)
			{
				return BigDecimal.ZERO;//当期已提前还掉，服务费为0
			}
			else if (shouldPayDay < shouldPayDayConfig)
			{
				shouldPayDay = shouldPayDayConfig;
				int allDay = DateUtil.substractDay(nextPayment.getPpPaytime(),
						prevPayDate);
				// 应付服务费
				return allServiceFee.multiply(new BigDecimal(shouldPayDay)).divide(
								new BigDecimal(allDay), 2, BigDecimal.ROUND_HALF_UP);
			}
			else 
			{
				int allDay = DateUtil.substractDay(nextPayment.getPpPaytime(),
						prevPayDate);
				// 应付服务费
				return allServiceFee.multiply(new BigDecimal(shouldPayDay)).divide(
								new BigDecimal(allDay), 2, BigDecimal.ROUND_HALF_UP);
			}
		}
	}
	/*@Override
	public BigDecimal calcShouldPayServeFee(List<PaymentPlanDO> paymentList) {
		//PaymentPlanDO nextPayment = this.getNextPayment(paymentList);
		BigDecimal shouldServeFee = BigDecimal.ZERO;
		//int isPayAllHandFeeConfig = 1;//默认是1
		BigDecimal aheadHandFeeRebateConfig = new BigDecimal(100);
		//PlainResult<SysConfig> aheadHandFee = sysConfigService.querySysConfig(SysConfigEntry.AHEADREPAY_HAND_FEE);
		PlainResult<SysConfig> aheadHandFeeRebate = sysConfigService.querySysConfig(SysConfigEntry.AHEADREPAY_HAND_FEE_REBATE);

        //if (aheadHandFee.isSuccess()) {
        //	isPayAllHandFeeConfig = Integer.parseInt(aheadHandFee.getData().getConfValue());
        //}
        
        if (aheadHandFeeRebate.isSuccess()) {
        	aheadHandFeeRebateConfig = new BigDecimal(aheadHandFeeRebate.getData().getConfValue());
        }
        
		//if(isPayAllHandFeeConfig==1){
			for (PaymentPlanDO p : paymentList) {
				if(p.getPpPayState()!= PayState.CLEAR.state && p.getPpPayState()!= PayState.CANCELED.state && p.getPpPayState()!= PayState.INACTIVED.state){
					shouldServeFee = shouldServeFee.add(p.getPpPayServiceFee());
				}
			}
			//提前还款服务费打折
			//shouldServeFee = (shouldServeFee.subtract(nextPayment.getPpPayHandFee())).multiply(aheadHandFeeRebateConfig).divide(new BigDecimal(100)).add(nextPayment.getPpPayHandFee());
			shouldServeFee = shouldServeFee.multiply(aheadHandFeeRebateConfig).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
		//}else{
		//	shouldServeFee = nextPayment.getPpPayServiceFee();
		//}
		return shouldServeFee;
	}*/
	
	/**
	 * 获取上次还款日期，第一期取满标划转时间
	 * @param paymentList
	 * @return
	 */
	private Date getPrevPaymentDate(List<PaymentPlanDO> paymentList){
		Date prevPayDate = null;// 上期还款日
		for (PaymentPlanDO p : paymentList) {
			if (p.getPpPayState() == PayState.CLEAR.state) {
				prevPayDate = p.getPpPaytime();
			}
		}
		// 是第一期，取满标划转的时间
//		if (prevPayDate == null) {
//			prevPayDate = paymentList.get(0).getPpCreateTime();
//		}
		return prevPayDate;
	}
	
	/**
	 * 获取下次应还计划
	 * @param paymentList
	 * @return
	 */
	@Override
	public PaymentPlanDO getNextPayment(List<PaymentPlanDO> paymentList){
		PaymentPlanDO nextPayment = null;// 下期还款计划
		for (PaymentPlanDO p : paymentList) {
			if (p.getPpPayState() != PayState.CLEAR.state) {
				nextPayment = p;
				break;
			}
		}
		return nextPayment;
	}
	
	@Override
	public BaseResult check(List<PaymentPlanDO> paymentList,Loan loan){
		BaseResult result = new BaseResult();
		if (loan.getLoanState() != LoanState.REPAYING) {
			result.setSuccess(false);
			result.setMessage("该项目状态为" + loan.getLoanState().prompt + ",不可提前还款");
			return result;
		}
		
		Date prevPayDate = this.getPrevPaymentDate(paymentList);
		// 是第一期，取满标划转的时间
		if (prevPayDate == null) {
			prevPayDate = paymentList.get(0).getPpCreateTime();
		}
		
		PaymentPlanDO nextPayment = this.getNextPayment(paymentList);
		
		int shouldPayDay = DateUtil.substractDay(new Date(), prevPayDate);
//		if(shouldPayDay<0){
//			result.setSuccess(false);
//			result.setMessage("当期已经提前还款，请付息日之后再操作");
//			return result;
//		}
		int allDay = DateUtil.substractDay(nextPayment.getPpPaytime(),
				prevPayDate);
		if(shouldPayDay>allDay){
			result.setSuccess(false);
			result.setMessage("该项目已逾期，请先将逾期还款还清");
			return result;
		}
		for(PaymentPlanDO payment: paymentList){
			if(payment.getPpReplaceState()&&payment.getPpPayState()==PayState.UNCLEAR.state){
				result.setSuccess(false);
				result.setMessage("请先将担保代还的还款还清");
				return result;
			}
		}
		return result;
	}
	
	@Override
	@Transactional
	public BaseResult audit(int id, int auditUserId, boolean pass, String auditOpinion){
		BaseResult result = new BaseResult();
		AheadRepay aheadRepay  = new AheadRepay();
		aheadRepay.setId(id);
		aheadRepay = aheadRepayDao.findOne(aheadRepay);
		if(aheadRepay.getState()!=AheadRepayState.WAIT_AUDIT){
			result.setSuccess(false);
			result.setMessage("该记录已"+aheadRepay.getState().desc);
			return result;
		}
		aheadRepay.setAuditOpinion(auditOpinion);
		if(pass){
			result = this.aheadRepay(aheadRepay.getLoanId());
			if(result.isSuccess()){
				aheadRepay.setAuditUserId(auditUserId);
				aheadRepay.setState(AheadRepayState.AUDIT_PASS);
				aheadRepay.setAuditDate(new Date());
				//edit by 夏同同 20160519 避免第三方交互成功后，因下面的异常造成本地数据回滚
				//捕获异常
				try{
					aheadRepayDao.update(aheadRepay);
				}catch(Exception e){
					log.error("更新表abc_ahead_repay异常！");
					log.error(e.getMessage());
				}
			}else if(CommonResultCode.FAIL_BUSY.code==result.getCode()){
				aheadRepay.setAuditUserId(auditUserId);
				aheadRepay.setState(AheadRepayState.AUDIT_WAITING);
				aheadRepay.setAuditDate(new Date());
				aheadRepayDao.update(aheadRepay);
			}
			return result;
		} else {
			aheadRepay.setAuditUserId(auditUserId);
			aheadRepay.setState(AheadRepayState.AUDIT_NOT_PASS);
			aheadRepay.setAuditDate(new Date());
			aheadRepayDao.update(aheadRepay);
		}
		return result;
	}

	/**
	 * 提前还款<br>
	 * 1.重新生成还款计划表<br>
	 * 2.重新生成收益计划表<br>
	 * 3.调用还款接口还款
	 * @param loanId
	 * @return
	 */
	@Transactional
	private BaseResult aheadRepay(int loanId) {
		BaseResult result = new BaseResult();
		Loan loan = loanService.findLoanById(loanId).getData();
		// 查询还款计划
		List<PaymentPlanDO> paymentList = paymentPlanDao.findByLoanId(loanId);
		result = this.check(paymentList, loan);
		if(!result.isSuccess()){
			return result;
		}
		PaymentPlanDO nextPayment = this.getNextPayment(paymentList);
		// 计算应付本金，利息
		BigDecimal shouldCapital = this.calcShouldPayCapital(paymentList);
		BigDecimal shouldInterest = this.calcShouldPayInterest(paymentList);
		
		//计算应付平台服务费
		BigDecimal shouldServeFee = this.calcShouldPayServeFee(paymentList);
		
		BigDecimal allMoney = shouldCapital.add(shouldInterest).add(shouldServeFee);
		BigDecimal avlBalance = doubleDryService.queryRZAvlBalance(loan.getLoanUserId());
		if (avlBalance.compareTo(allMoney) < 0) {
			result.setSuccess(false);
			result.setMessage("余额不足");
			return result;
		}

		// 重新生成还款计划
		PaymentPlanDO newPayment = this.modifyPaymentPlan(paymentList,
				nextPayment, shouldCapital, shouldInterest,shouldServeFee);

		// 查询待收益计划
		IncomePlanDO incomeSearch = new IncomePlanDO();
		incomeSearch.setPiLoanId(loanId);
		incomeSearch.setPiIncomePlanState(IncomePlanState.GOING.state);
		List<IncomePlanDO> incomeList = incomePlanDao.findListByParam(
				incomeSearch, null);
		// 重新生成收益计划
		this.modifyIncomePlan(incomeList, nextPayment, newPayment,
				shouldInterest);
		// 还款
		result = repayService.repay(loanId, newPayment.getPpId(),
				PayType.AHEAD_CLEAR, loan.getLoanUserId());
		return result;
	}

	/**
	 * 重新生成还款计划<br>
	 * 1.将未还清的计划全部取消<br>
	 * 2.插入新的还款计划
	 * 
	 * @param paymentList
	 * @param nextPayment
	 * @param shouldCapital
	 * @param shouldInterest
	 * @return 新插入的还款计划
	 */
	private PaymentPlanDO modifyPaymentPlan(List<PaymentPlanDO> paymentList,
			PaymentPlanDO nextPayment, BigDecimal shouldCapital,
			BigDecimal shouldInterest,BigDecimal shouldServeFee) {
		// 原来的未还清计划全部取消
		for (PaymentPlanDO p : paymentList) {
			if (p.getPpPayState() != PayState.CLEAR.state) {
				PaymentPlanDO p_ = new PaymentPlanDO();
				p_.setPpId(p.getPpId());
				p_.setPpPayState(PayState.CANCELED.state);
				paymentPlanDao.update(p_);
			}
		}
		// 插入新的还款计划
		PaymentPlanDO newPayment = new PaymentPlanDO();
		BeanCopier copy = BeanCopier.create(PaymentPlanDO.class,
				PaymentPlanDO.class, false);
		copy.copy(nextPayment, newPayment, null);
		newPayment.setPpPayCapital(shouldCapital);
		newPayment.setPpPayInterest(shouldInterest);
		newPayment.setPpPayServiceFee(shouldServeFee);
		newPayment.setPpPayTotalMoney(shouldCapital.add(shouldInterest).add(shouldServeFee));
		newPayment.setPpPaytime(new Date());
		paymentPlanDao.insert(newPayment);
		return newPayment;
	}

	/**
	 * 重新生成收益计划<br>
	 * 1.将所有待收益的收益计划全部取消<br>
	 * 2.生成新的收益计划
	 * 
	 * @param incomeList
	 * @param nextPayment
	 * @param newPayment
	 * @param shouldInterest
	 */
	private void modifyIncomePlan(List<IncomePlanDO> incomeList,
			PaymentPlanDO nextPayment, PaymentPlanDO newPayment,
			BigDecimal shouldInterest) {
		Map<Integer, BigDecimal> incomeCapitalMap = Maps.newHashMap();// 待收本金
		// 所有待收益计划全部取消
		for (IncomePlanDO income : incomeList) {
			IncomePlanDO income_ = new IncomePlanDO();
			income_.setPiId(income.getPiId());
			income_.setPiIncomePlanState(IncomePlanState.CANCELED.state);
			incomePlanDao.update(income_);
			// 统计待收本金
			BigDecimal sumCapital = incomeCapitalMap.get(income.getPiInvestId());
			if (sumCapital == null) {
				sumCapital = BigDecimal.ZERO;
			}
			incomeCapitalMap.put(income.getPiInvestId(), income
					.getPiPayCapital().add(sumCapital));
		}
		// 过滤出下期待收益计划
		List<IncomePlanDO> nextIncomeList = Lists.newArrayList();
		for (IncomePlanDO income : incomeList) {
			if (income.getPiPaymentPlanId().equals(nextPayment.getPpId())) {
				nextIncomeList.add(income);
			}
		}

		BeanCopier copyier = BeanCopier.create(IncomePlanDO.class,
				IncomePlanDO.class, false);
		// 按比例瓜分利息，生成新的收益计划
		BigDecimal sumInterest = BigDecimal.ZERO;
		List<IncomePlanDO> newIncomeList = Lists.newArrayList();
		for (int i = 0; i < nextIncomeList.size(); i++) {
			IncomePlanDO income = nextIncomeList.get(i);
			IncomePlanDO newIncome = new IncomePlanDO();
			copyier.copy(income, newIncome, null);
			if (i == nextIncomeList.size() - 1) {
				newIncome.setPiPayInterest(shouldInterest.subtract(sumInterest));
			} else {
				BigDecimal interest = shouldInterest.multiply(
						income.getPiPayInterest()).divide(
						nextPayment.getPpPayInterest(), 2,
						BigDecimal.ROUND_DOWN);
				sumInterest = sumInterest.add(interest);
				newIncome.setPiPayInterest(interest);
			}
			newIncome.setPiPayCapital(incomeCapitalMap.get(income
					.getPiInvestId()));// 本金
			newIncome.setPiPayTotalMoney(newIncome.getPiPayCapital().add(
					newIncome.getPiPayInterest()));
			newIncome.setPiPaymentPlanId(newPayment.getPpId());
			newIncome.setPiPaytime(new Date());
			newIncomeList.add(newIncome);
		}
		incomePlanDao.batchInsert(newIncomeList);
	}

	@Override
	public PageResult<AheadRepay> findPage(AheadRepay aheadRepay,
			PageCondition pageCondition) {
		PageResult<AheadRepay> result = new PageResult<AheadRepay>(
				pageCondition.getPage(), pageCondition.getPageSize());
		int count = aheadRepayDao.countList(aheadRepay);
		result.setTotalCount(count);
		result.setData(aheadRepayDao.findList(aheadRepay, pageCondition));
		return result;
	}
}
