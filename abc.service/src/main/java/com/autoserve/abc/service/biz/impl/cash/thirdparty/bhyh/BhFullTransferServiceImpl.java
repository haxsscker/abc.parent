package com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.BuyLoanDO;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.FullTransferRecordDO;
import com.autoserve.abc.dao.dataobject.FullTransferRecordJDO;
import com.autoserve.abc.dao.dataobject.IncomePlanDO;
import com.autoserve.abc.dao.dataobject.InvestOrderDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.PaymentPlanDO;
import com.autoserve.abc.dao.dataobject.TransferLoanDO;
import com.autoserve.abc.dao.dataobject.search.FullTransferRecordSearchDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchDO;
import com.autoserve.abc.dao.intf.BuyLoanDao;
import com.autoserve.abc.dao.intf.DealRecordDao;
import com.autoserve.abc.dao.intf.FullTransferRecordDao;
import com.autoserve.abc.dao.intf.IncomePlanDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.PaymentPlanDao;
import com.autoserve.abc.dao.intf.TransferLoanDao;
import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.convert.BuyLoanConverter;
import com.autoserve.abc.service.biz.convert.LoanConverter;
import com.autoserve.abc.service.biz.convert.TransferLoanConverter;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.Deal;
import com.autoserve.abc.service.biz.entity.DealDetail;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.entity.FeeSetting;
import com.autoserve.abc.service.biz.entity.IncomePlan;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.AccountCategory;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.BuyLoanState;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.FeeType;
import com.autoserve.abc.service.biz.enums.FullTransferType;
import com.autoserve.abc.service.biz.enums.IncomePlanState;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanPeriodUnit;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.PayState;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.data.SftpTool;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ConfigHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ExchangeDataUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileNameUtil;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.SeqnoHelper;
import com.autoserve.abc.service.biz.impl.loan.plan.PlanBuilder;
import com.autoserve.abc.service.biz.impl.loan.plan.PlanBuilderFactory;
import com.autoserve.abc.service.biz.intf.activity.ActivityService;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.invest.InvestOrderService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.loan.fulltransfer.FullTransferService;
import com.autoserve.abc.service.biz.intf.loan.manage.BuyLoanManageService;
import com.autoserve.abc.service.biz.intf.loan.manage.TransferLoanManageService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.redenvelope.RedService;
import com.autoserve.abc.service.biz.intf.sys.FeeSettingService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.job.thread.InvestPrizeThread;
import com.autoserve.abc.service.message.sms.SendMsgService;

/**
 * 满标资金划转服务实现
 * 
 * @author segen189 2014年11月29日 下午2:34:19
 */
@Service
public class BhFullTransferServiceImpl implements FullTransferService {
    private static final Logger       log = LoggerFactory.getLogger(BhFullTransferServiceImpl.class);

    @Resource
    private LoanDao                   loanDao;

    @Resource
    private TransferLoanDao           transferLoanDao;

    @Resource
    private BuyLoanDao                buyLoanDao;

    @Resource
    private BuyLoanManageService      buyLoanManageService;

    @Resource
    private TransferLoanManageService transferLoanManageService;

    @Resource
    private PaymentPlanDao            paymentPlanDao;
    @Resource
    private IncomePlanDao 			incomePlanDao;
    @Resource
    private FullTransferRecordDao fullTransferRecordDao;

    @Resource
    private InvestQueryService        investQueryService;

    @Resource
    private TransferLoanService       transferLoanService;

    @Resource
    private IncomePlanService         incomePlanService;

    @Resource
    private PaymentPlanService        paymentPlanService;

    @Resource
    private FeeSettingService         feeSettingService;

    @Resource
    private DealRecordService         dealRecordService;

    @Resource
    private AccountInfoService        accountInfoService;

    @Resource
    private UserService               userService;

    @Resource
    private EmployeeService           employeeService;

    @Resource
    private SysConfigService          sysConfigService;

    @Resource
    private Callback<DealNotify>      moneyTransferedCallback;

    @Resource
    private GovernmentService         governmentService;

    @Resource
    private DealRecordDao             dealRecordDao;

    @Resource
    private InvestOrderService        investOrderService;
    
    @Resource
	private ActivityService 		  activityService;
    
    @Resource
	private RedService 				  redService;
    
    @Resource
	private SendMsgService 			  sendMsgService;

    /**
     * 普通标满标资金划转<br>
     * 1. 前置条件判断<br>
     * 2. 计算手续费<br>
     * 3. 计算担保费<br>
     * 4. 计算服务费<br>
     * 5. 更新标状态<br>
     * 6. 添加交易记录<br>
     * 6.1 交易账户查询<br>
     * 6.2 放款记录<br>
     * 6.3 收取手续费记录<br>
     * 6.4 收取担保费记录<br>
     * 7. 添加满标资金划转记录， 状态为 DealState.NOCALLBACK<br>
     * 8. 生成还款计划, 状态为 LoanPayState.INACTIVED<br>
     * 9. 生成收益计划, 状态为 IncomePlanState.INACTIVED<br>
     * 10. 执行资金划转交易
     */
    // check 执行资金交易的要保证异步，拆分service
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult commonBidMoneyTransfer(int loanId, double serviceOperateFee, BigDecimal actualGuarFee, int periods,
                                             FullTransferType transferType, int operatorId) {
        BaseResult result = new BaseResult();
        if(null==actualGuarFee){
        	actualGuarFee = BigDecimal.ZERO;
        }
        // 1. 前置条件
        if (serviceOperateFee < 0) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "实收服务费不能为负数");
        }

        LoanDO loanDO = loanDao.findByLoanIdWithLock(loanId);

        if (loanDO == null) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "借款不存在");
        }

        LoanCategory loanCategory = LoanCategory.valueOf(loanDO.getLoanCategory());
        //
        //        int totalMonths;
        //        if (loanDO.getLoanPeriodType().equals(LoanPeriodUnit.YEAR.getUnit())) {
        //            totalMonths = loanDO.getLoanPeriod() * 12;
        //        } else {
        //            totalMonths = loanDO.getLoanPeriod();
        //        }
        //
        //        if (totalMonths % periods != 0) {
        //            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "还款总期数必须要能被借款总月数整除");
        //        }

        if (!loanDO.getLoanState().equals(LoanState.FULL_REVIEW_PASS.getState())) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "当前标的状态不能进行满标资金划转");
        }

        //        if (!loanDO.getLoanCurrentInvest().equals(loanDO.getLoanCurrentValidInvest())) {
        //            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "当前标的状态不能进行满标资金划转");
        //        }
        InvestSearchDO searchDO = new InvestSearchDO();
        searchDO.setBidId(loanId);
        searchDO.setBidType(BidType.COMMON_LOAN.getType());
        searchDO.setInvestStates(Arrays.asList(InvestState.PAID.getState()));

        ListResult<Invest> investResult = investQueryService.queryInvestList(searchDO);
        List<Invest> investList = investResult.getData();
        if (!investResult.isSuccess() || CollectionUtils.isEmpty(investList)) {
            return result.setError(CommonResultCode.BIZ_ERROR, "投资记录查询失败");
        }


 
        //备注：前台传入的服务费，如果是分期支付服务费，则前台传入的服务费为0
        BigDecimal collectOperatingFee = BigDecimal.ZERO;  
        
        //服务费计算公式：项目金额*天数*费率
        //PlainResult<BigDecimal> serveFeeResult = feeSettingService.calcFee(loanId, FeeType.PLA_SERVE_FEE);
        //修改项目费用收取方式--》按每个项目设置的收费方式计算
        PlainResult<BigDecimal> serveFeeResult = feeSettingService.calcFee(loanId);
        BigDecimal serveFee = serveFeeResult.getData();
        if("1".equals(loanDO.getHandFeeKind())){//线下收取
        	if (serviceOperateFee > 0) {//前台传入实收费用
        		return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "线下收取不能传入实收费用");
        	}
        }else if("2".equals(loanDO.getHandFeeKind())){//如果是分期支付服务费，则服务费是根据服务费计算公式得出
        	if (serviceOperateFee > 0) {//前台传入实收费用
        		collectOperatingFee = BigDecimal.valueOf(serviceOperateFee);
        	}else{
        		collectOperatingFee = serveFee;
        	}
        }
        // 3. 计算担保费
        //现在不收担保费用
        final BigDecimal guarFee = BigDecimal.ZERO;
//        if (loanDO.getLoanGuarGov() == null) {
//            guarFee = BigDecimal.ZERO;
//        } else {
        	//担保费计算公式：项目金额*天数*费率/360  （天数=项目到期日-放款时间）
//        	 PlainResult<BigDecimal> insureFeeResult = feeSettingService.calcFee(loanId, FeeType.INSURANCE_FEE);
//        	 guarFee = insureFeeResult.getData();
//            PlainResult<FeeSetting> insureFeeResult = feeSettingService.queryByFeeTypeLoanCategory(
//                    FeeType.INSURANCE_FEE, loanCategory, loanDO.getLoanMoney());
//            if (!insureFeeResult.isSuccess()) {
//                return result.setError(CommonResultCode.BIZ_ERROR, "担保费查询失败");
//            }
//
//            insureFee = calculateFee(insureFeeResult.getData(), loanDO.getLoanCurrentValidInvest());
//        }

  
        AccountInfoDO borrorerAcountInfo = accountInfoService.qureyAccountByUserIdAndUserType(loanDO.getLoanUserId(),loanDO.getLoanEmpType());
        if (null==borrorerAcountInfo || StringUtils.isEmpty(borrorerAcountInfo.getAccountNo())) {
            return result.setError(CommonResultCode.BIZ_ERROR, "借款人开户信息查询失败");
        }
        String innerSeqNo = ConfigHelper.getConfig("merchantId")+SeqnoHelper.getId(16);
        
        
        // 6. 添加交易记录
        // 6.1 交易账户查询
        // 6.2 放款记录
        // 6.3 收取手续费记录

        // 6.1 查询借款人账户、投资人账户、担保公司账户、平台账户
        List<UserIdentity> userList = new ArrayList<UserIdentity>();

        // 借款人
        UserIdentity loanee = new UserIdentity();
        loanee.setUserId(loanDO.getLoanUserId());
        loanee.setUserType(queryUserTypeByUserId(loanee.getUserId()));
        loanee.setAccountCategory(AccountCategory.LOANACCOUNT.getType());
        userList.add(loanee);
        AccountInfoDO accountInfoDO = accountInfoService.qureyAccountByUserIdAndUserType(loanee.getUserId(),loanee.getUserType().getType());
        // 投资人
        final Map<Integer, UserType> investUserTypeMap = new HashMap<Integer, UserType>();
        for (Invest invest : investList) {
            UserIdentity investor = new UserIdentity();
            investor.setUserId(invest.getUserId());
            investor.setUserType(queryUserTypeByUserId(investor.getUserId()));
            investor.setAccountCategory(AccountCategory.INVESTACCOUNT.getType());
            userList.add(investor);

            investUserTypeMap.put(invest.getId(), investor.getUserType());
        }

        // 担保公司
//        Employee guarEmployee = null;
//        if (loanDO.getLoanGuarGov() != null) {
//            PlainResult<Employee> employeeResult = employeeService.findByGovId(loanDO.getLoanGuarGov());
//            if (!employeeResult.isSuccess() || employeeResult.getData() == null) {
//                throw new BusinessException("担保机构查询失败");
//            }
//
//            guarEmployee = employeeResult.getData();
//
//            UserIdentity guarGov = new UserIdentity();
//            guarGov.setUserId(guarEmployee.getEmpId());
//            guarGov.setUserType(UserType.PARTNER);
//            userList.add(guarGov);
//        }

        ListResult<Account> accountResult = accountInfoService.queryByUserIds(userList);
        List<Account> userAccountList = accountResult.getData();
        if (!accountResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "投资人开户信息查询失败");
        }

        // 借款人账户、投资人账户、担保公司账户
        final Map<String, String> userIdAndAccountMap = new HashMap<String, String>();
        for (Account acc : userAccountList) {
            userIdAndAccountMap.put(acc.getAccountUserId() + "|" + acc.getAccountUserType().getType(),
                    acc.getAccountNo());
        }

        //从abc_invest_order获取该原始标所有的投资记录
        List<InvestOrderDO> listLoanSeq = investOrderService.queryInvestOrderByTransLoanId(loanId);
        // 平台账户
        final String platformAccount = ConfigHelper.getConfig("merchantId");
        //添加待处理交易记录
        List<DealRecordDO> dealRecordDoList = new ArrayList<DealRecordDO>();
        String payAccountId = "";
        DealRecordDO dealRecord = null;
        BigDecimal sumInvestMoney = BigDecimal.ZERO;
        //红包投资返还在回调中处理
        for(InvestOrderDO investOrderDO:listLoanSeq){
        	payAccountId = userIdAndAccountMap.get(investOrderDO.getIoUserId() + "|"
        			+ queryUserTypeByUserId(investOrderDO.getIoUserId()).getType());
        	dealRecord = new DealRecordDO();
        	if("0".equals(investOrderDO.getIoInnerSeqNo().substring(investOrderDO.getIoInnerSeqNo().length()-1))){
	            //投资划转记录
	            dealRecord.setDrBusinessId(loanId);
	            dealRecord.setDrMoneyAmount(investOrderDO.getIoOrderMoney());
	            dealRecord.setDrOperateDate(new Date());
	            dealRecord.setDrOperator(operatorId);
	            dealRecord.setDrPayAccount(payAccountId);
	            dealRecord.setDrReceiveAccount(accountInfoDO.getAccountNo());
	            dealRecord.setDrInnerSeqNo(innerSeqNo);
	            dealRecord.setDrType(DealType.TRANSFER.getType());
	            dealRecord.setDrDetailType(DealDetailType.APPROPRIATE_MONEY.getType());
	            dealRecord.setDrState(DealState.NOCALLBACK.getState());
	            sumInvestMoney=sumInvestMoney.add(investOrderDO.getIoOrderMoney());
        	}   
        	dealRecordDoList.add(dealRecord);
        }
        if(sumInvestMoney.doubleValue()<loanDO.getLoanCurrentValidInvest().doubleValue()){
        	return result.setError(CommonResultCode.BIZ_ERROR, "投资总金额小于借款金额！");
        }
        // 5. 更新标状态
        LoanDO toModify = new LoanDO();
        toModify.setLoanId(loanDO.getLoanId());
        toModify.setLoanState(LoanState.MONEY_TRANSFERING.getState());
        toModify.setSeqNo(innerSeqNo);//修改标的流水号，方便回调处理
        int r=loanDao.update(toModify);
        if(r<=0){
        	log.error("更新标的流水号失败");
        	throw new BusinessException("更新标的流水号失败");
        }
        // 6.3 收取手续费记录（线下方式不收取手续费，所以屏蔽）
//        if("1".equals(loanDO.getHandFeeKind())){
//        	dealRecord = new DealRecordDO();
//            dealRecord.setDrBusinessId(loanId);
//            dealRecord.setDrMoneyAmount(collectOperatingFee);
//            dealRecord.setDrOperateDate(new Date());
//            dealRecord.setDrOperator(operatorId);
//            dealRecord.setDrPayAccount(accountInfoDO.getAccountNo());
//            dealRecord.setDrReceiveAccount(platformAccount);
//            dealRecord.setDrInnerSeqNo(innerSeqNo);
//            dealRecord.setDrType(DealType.TRANSFER.getType());
//            dealRecord.setDrDetailType(DealDetailType.PLA_SERVE_FEE.getType());
//            dealRecord.setDrState(DealState.NOCALLBACK.getState());
//            dealRecordDoList.add(dealRecord);
//        }
        
        
        // 保存交易记录
		int flag = dealRecordService.batchInsert(dealRecordDoList);
		if (flag <= 0) {// 插入不成功处理
			log.error("批量插入交易记录出错");
			throw new BusinessException(CommonResultCode.ERROR_DB.getCode(),"批量插入交易记录出错");
		}

        // 7. 添加满标资金划转记录
        FullTransferRecordDO fullTransRecord = new FullTransferRecordDO();
        fullTransRecord.setFtrBidId(loanId);
        fullTransRecord.setFtrOriginId(loanId);
        fullTransRecord.setFtrOperator(operatorId);
        fullTransRecord.setFtrUserId(loanDO.getLoanUserId());
        fullTransRecord.setFtrBidType(BidType.COMMON_LOAN.getType());
        fullTransRecord.setFtrSeqNo(innerSeqNo);
        fullTransRecord.setFtrTransferDate(new Date());
        fullTransRecord.setFtrTransferType(transferType.getType());
        if("1".equals(loanDO.getHandFeeKind())){
        	fullTransRecord.setFtrTransferMoney(loanDO.getLoanCurrentValidInvest());
        }else{
        	fullTransRecord.setFtrTransferMoney(loanDO.getLoanCurrentValidInvest());
        }
        
               
        if (fullTransRecord.getFtrTransferMoney().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("满标资金划转金额须是正数");
        }

        

        fullTransRecord.setFtrGuarFee(guarFee);
        
        if("1".equals(loanDO.getHandFeeKind())){
        	fullTransRecord.setFtrPayFee(collectOperatingFee);//应收服务费
        	fullTransRecord.setFtrRealPayFee(BigDecimal.ZERO);//实收服务费
        }else{
        	fullTransRecord.setFtrPayFee(collectOperatingFee);//应收服务费
        	fullTransRecord.setFtrRealPayFee(BigDecimal.valueOf(serviceOperateFee));//实收服务费
       }
        
        //实收担保费
        fullTransRecord.setFtrRealGuarFee(actualGuarFee);
        fullTransRecord.setFtrDealState(DealState.NOCALLBACK.getState());
        // 满标资金划转记录id
        FullTransferRecordDO oldFullTransRecord = fullTransferRecordDao.findByBidId(loanId);
        if(null == oldFullTransRecord){
        	int r1=fullTransferRecordDao.insert(fullTransRecord);
        	if(r1<=0){
        		log.error("满标资金划转记录保存失败");
        		throw new BusinessException("满标资金划转记录保存失败");
        	}
        }else{
        	fullTransRecord.setFtrId(oldFullTransRecord.getFtrId());
        	int r2=fullTransferRecordDao.updateByBidId(fullTransRecord);
        	if(r2<=0){
        		log.error("满标资金划转记录保存失败");
        		throw new BusinessException("满标资金划转记录保存失败");
        	}
        }
        final int fullTransferRecordId = fullTransRecord.getFtrId();
        //删除这个项目之前的还款计划
        paymentPlanDao.batchDeletePlanByLoanId(loanId);
        // 8. 生成还款计划
        PlanBuilder planBuilder = PlanBuilderFactory.createPlanBuilder(LoanPeriodUnit.DAY);
        List<PaymentPlan> paymentPlanList = new ArrayList<PaymentPlan>();
        if("1".equals(loanDO.getHandFeeKind())){//线下支付平台服务费，不需要在计划表中生成
        	paymentPlanList = planBuilder.buildPaymentPlanListIncludeHandFee(LoanConverter.toLoan(loanDO),
                    0.00, fullTransferRecordId, periods);
        }else{//分期支付平台服务费，需要在计划表中生成
        	paymentPlanList = planBuilder.buildPaymentPlanListIncludeHandFee(LoanConverter.toLoan(loanDO),
                    collectOperatingFee.doubleValue(), fullTransferRecordId, periods);
        }
        
        PlainResult<Integer> createPaymentResult = paymentPlanService.batchCreatePaymentPlanList(paymentPlanList);
        if (!createPaymentResult.isSuccess()) {
        	log.error("还款计划创建失败");
            throw new BusinessException("还款计划创建失败");
        }

        ListResult<PaymentPlan> queryPaymentResult = paymentPlanService.queryPaymentPlanList(fullTransferRecordId);
        if (!queryPaymentResult.isSuccess()) {
        	log.error("还款计划创建失败");
            throw new BusinessException("还款计划创建失败");
        }
        //删除这个项目之前的收益还款计划
        incomePlanDao.batchDeletePlanByLoanId(loanId);
        // 9. 生成收益计划
        List<IncomePlan> incomePlanList = planBuilder.buildIncomePlanList(LoanConverter.toLoan(loanDO), investList,
                queryPaymentResult.getData(), fullTransferRecordId);
        PlainResult<Integer> createIncomeResult = incomePlanService.batchCreateIncomePlanList(incomePlanList);
        if (!createIncomeResult.isSuccess()) {
        	log.error("收益计划创建失败");
            throw new BusinessException("收益计划创建失败");
        }
        /**
         * 调用放款接口
         */
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("BorrowId", String.valueOf(loanId));
        paramsMap.put("MerBillNo", innerSeqNo);
        //获取放款申请文件数据fileData
        Map<String, Object> ftpFileContentMap= new HashMap<String, Object>();
        Map<String, String> summaryMap=new LinkedHashMap<String, String>();
        summaryMap.put("char_set", ConfigHelper.getConfig("charset"));
        summaryMap.put("partner_id", ConfigHelper.getConfig("merchantId"));
        summaryMap.put("MerBillNo", innerSeqNo);
        summaryMap.put("TransAmt", FormatHelper.changeY2F(loanDO.getLoanMoney()));
        if("1".equals(loanDO.getHandFeeKind())){//线下收取费用
        	summaryMap.put("FeeAmt", FormatHelper.changeY2F(BigDecimal.ZERO));
        }else{//分期收取，划转时不收费用，在还款时收取
        	summaryMap.put("FeeAmt", FormatHelper.changeY2F(BigDecimal.ZERO));
        }
        summaryMap.put("BorrowId", String.valueOf(loanId));
        summaryMap.put("BorrowerAmt", FormatHelper.changeY2F(loanDO.getLoanMoney()));
        summaryMap.put("BorrCustId", borrorerAcountInfo.getAccountNo());
        summaryMap.put("ReleaseType", "0");
        summaryMap.put("MerPriv", "");
        summaryMap.put("TotalNum", String.valueOf(investList.size()));
        List<Map<String, String>> detailListMap= new ArrayList<Map<String,String>>();
        Map<String, String> detailMap =null;
        for(int i=0,len=investList.size();i<len;i++){
        	detailMap = new LinkedHashMap<String, String>();
        	detailMap.put("ID", String.valueOf(investList.get(i).getId()));
        	detailMap.put("PlaCustId", investList.get(i).getInAccountNo());
        	detailMap.put("TransAmt", FormatHelper.changeY2F(investList.get(i).getValidInvestMoney()));
        	detailMap.put("FreezeId", investList.get(i).getInFreezeId());
        	detailListMap.add(detailMap);
        }
        ftpFileContentMap.put("summaryMap", summaryMap);
        ftpFileContentMap.put("detailListMap", detailListMap);
        paramsMap.put("fileData", FormatHelper.formatFtpFileContent(ftpFileContentMap));
        Map<String, String> resultMap = this.fullTranfer(paramsMap);
        if(!"000000".equals(resultMap.get("RespCode"))){
        	log.error("调用放款接口失败===="+resultMap.get("RespDesc"));
        	if("RED_TIMEOUT".equals(resultMap.get("RespCode"))){
        		result.setErrorMessage(CommonResultCode.ERROR,resultMap.get("RespDesc"));
            }else{
            	throw new BusinessException(CommonResultCode.ERROR.getCode(),"调用放款接口失败===="+resultMap.get("RespDesc"));
            }
        }else{
        	result.setMessage("放款申请成功，等待处理！");
        }
        // 10.投标分析
        try 
        {
        	InvestSearchDO investSearchDO = new InvestSearchDO();
        	investSearchDO.setBidId(loanId);
			investSearchDO.setBidType(BidType.COMMON_LOAN.getType());
			
			new InvestPrizeThread(investSearchDO, investQueryService, activityService, 
					redService, sendMsgService, userService).start();
        }
        catch (Exception ex)
        {
        	ex.printStackTrace();
        	log.error("投标分析任务执行异常", ex);
        }

        return result;
    }
    
    /**
     * 满标资金划转(调用接口)
     * 1、ftp上传放款文件明细
     * 2、放款申请接口
     */
    private Map<String, String> fullTranfer(Map<String, String> map) {
    	Map<String, String> resultMap = new HashMap<String, String>();
    	SftpTool ftp = new SftpTool();
    	// 本地路径
        String localPath = ConfigHelper.getSftpLocalPath();
        // 目标路径
        String remotePath = ConfigHelper.getSftpRemotePath()+FormatHelper.formatDate(new Date(), "yyyyMMdd");
        //上传文件名
        String merBillNo = map.get("MerBillNo");
        String fileName = FileNameUtil.getFileName("FileRelease", "txt",merBillNo);
        //上送成功标志文件名
        String uploadSuccessFileName = FileNameUtil.getFileNameBySuffix(fileName, "OK");
        //数据写入文件
        String fileData = map.get("fileData");
        boolean isWriteSuccess = FileUtils.writeByBufferedWriter(fileData, localPath+fileName);
        if(isWriteSuccess){
        	//ftp上传文件
        	ftp.connect();
        	boolean isUploadSuccess = ftp.uploadFile(remotePath, fileName, localPath, fileName);
        	isUploadSuccess = ftp.uploadFile(remotePath, uploadSuccessFileName, localPath, uploadSuccessFileName);
        	ftp.disconnect();
        	if(isUploadSuccess){
        		//发送请求,获取数据
        		Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        		paramsMap.put("biz_type", "FileRelease");
        		paramsMap.put("MerBillNo", merBillNo);
        		paramsMap.put("BorrowId", map.get("BorrowId"));
        		paramsMap.put("FileName", fileName);
        		paramsMap.put("BgRetUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("moneyTransferNotifyUrl"));
        		paramsMap.put("MerPriv", "");
        		resultMap = ExchangeDataUtils.submitData(paramsMap);
        	}else{
        		resultMap.put("RespCode", "fail");
        		resultMap.put("RespDesc", "放款文件上传失败，请重新上传!");
        	}
        }else{
        	resultMap.put("RespCode", "fail");
    		resultMap.put("RespDesc", "放款文件写入失败!");
        }
        return resultMap;
    }
    /**
     * 转让标满标资金划转<br>
     * 1. 前置条件判断<br>
     * 1.1 加行级锁住还款计划<br>
     * 1.2 满标前有还款行为，则直接流标<br>
     * 2. 计算转让费<br>
     * 3. 更新标状态<br>
     * 4. 添加交易记录<br>
     * 4.1 交易账户查询<br>
     * 4.2 放款记录<br>
     * 4.3 收取转让费记录<br>
     * 5. 添加满标资金划转记录， 状态为 DealState.NOCALLBACK<br>
     * 6. 生成收益计划, 状态为 IncomePlanState.INACTIVED<br>
     * 7. 执行资金划转交易
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult transferBidMoneyTransfer(int transLoanId, double actrualOperateFee,
                                               FullTransferType transferType, int operatorId) {
        BaseResult result = new BaseResult();

        // 1. 前置条件判断
        if (actrualOperateFee < 0) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "实收手续费不能为负数");
        }

        TransferLoanDO transferLoanDO = transferLoanDao.findById(transLoanId);
        LoanDO loanDO = loanDao.findById(transferLoanDO.getTlOriginId());

        if (!transferLoanDO.getTlState().equals(TransferLoanState.FULL_REVIEW_PASS.getState())) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "当前标的状态不能进行满标资金划转");
        }

        // 1.1 加行级锁住还款计划
        PaymentPlanDO paymentLock = paymentPlanDao.findWithLock(transferLoanDO.getTlNextPaymentId());
        if (paymentLock == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "还款时加行级锁失败");
        }

        // 1.2 满标前有还款行为，则直接流标
        if (!paymentLock.getPpPayState().equals(PayState.UNCLEAR.getState())) {
            //            TransferLoanDO toModify = new TransferLoanDO();
            //            toModify.setTlId(transferLoanDO.getTlId());
            //            toModify.setTlState(TransferLoanState.BID_CANCELED.getState());
            //            transferLoanDao.update(toModify);
            BaseResult cancelTransResult = transferLoanManageService.cancelTransferLoan(transLoanId, operatorId,
                    "转让标发起后到满标资金划转期间借款人有还款行为");
            if (!cancelTransResult.isSuccess()) {
                result.setError(CommonResultCode.BIZ_ERROR, "转让标发起后到满标资金划转期间借款人有还款行为，但流标失败");
            }

            return result.setError(CommonResultCode.BIZ_ERROR, "转让标发起后到满标资金划转期间借款人有还款行为");
        }

        // 目前的债权人转让是将债权人的所有未收益的收益计划查出来，进行转让
        // 如果要支持债权人部分转让，则此处需要查出的是转让人要部分转让的未收益的收益计划
        IncomePlan queryParam = new IncomePlan();
        queryParam.setInvestId(transferLoanDO.getTlInvestId());
        queryParam.setIncomePlanState(IncomePlanState.GOING);
        final ListResult<IncomePlan> transferIncomeResult = incomePlanService.queryIncomePlanList(queryParam);
        if (!transferIncomeResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "债权人的收益计划查询失败");
        } 
       
//        else if (CollectionUtils.isEmpty(transferIncomeResult.getData())) {
//            // 流标
//            //            TransferLoanDO toModify = new TransferLoanDO();
//            //            toModify.setTlId(transferLoanDO.getTlId());
//            //            toModify.setTlState(TransferLoanState.BID_CANCELED.getState());
//            //            transferLoanDao.update(toModify);
//            BaseResult cancelTransResult = transferLoanManageService.cancelTransferLoan(transLoanId, 0,
//                    "债权人的收益计划查询结果为空");
//            if (!cancelTransResult.isSuccess()) {
//                result.setError(CommonResultCode.BIZ_ERROR, "债权人的收益计划查询结果为空");
//            }
//
//            return result.setError(CommonResultCode.BIZ_ERROR, "债权人的收益计划查询结果为空");
//        }

        PlainResult<SysConfig> platformAccountResult = sysConfigService.querySysConfig(SysConfigEntry.PLATFORM_ACCOUNT);
        if (!platformAccountResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "平台账户查询失败");
        }

        InvestSearchDO investSearchDO = new InvestSearchDO();
        //原始标号transLoanId
        investSearchDO.setBidId(transLoanId);
        investSearchDO.setBidType(BidType.TRANSFER_LOAN.getType());
        investSearchDO.setInvestStates(Arrays.asList(InvestState.PAID.getState()));
        ListResult<Invest> investResult = investQueryService.queryInvestList(investSearchDO);
        List<Invest> investList = investResult.getData();
        if (!investResult.isSuccess() || CollectionUtils.isEmpty(investList)) {
            return result.setError(CommonResultCode.BIZ_ERROR, "转让标投资记录查询失败");
        }

        // 2. 计算转让手续费
        PlainResult<FeeSetting> transferFeeResult = feeSettingService.queryByFeeTypeLoanCategory(FeeType.TRANSFER_FEE,
                LoanCategory.valueOf(loanDO.getLoanCategory()), transferLoanDO.getTlTransferMoney());
        if (!transferFeeResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "平台转让手续费查询失败");
        }
        //实收转让费
        BigDecimal collectTransferFee = new BigDecimal(actrualOperateFee+"");
        //转让手续费：转出金额*费率
        BigDecimal expectTransferFee = calculateFee(transferFeeResult.getData(), transferLoanDO.getTlTransferMoney());
        if (collectTransferFee.compareTo(expectTransferFee) > 0) {
            return result.setError(CommonResultCode.BIZ_ERROR, "实收转让手续费不能大于应收转让手续费");
        }
        
        // 3. 更新标状态
        TransferLoanDO toModify = new TransferLoanDO();
        toModify.setTlId(transferLoanDO.getTlId());
        toModify.setTlState(TransferLoanState.MONEY_TRANSFERING.getState());
        toModify.setTlTransferFee(collectTransferFee);
        transferLoanDao.update(toModify);

        // 4. 添加交易记录
        // 4.1 交易账户查询
        Deal deal = new Deal();
        List<DealDetail> dealDetailList = new ArrayList<DealDetail>();

        // 查询转让人账户、投资人账户、平台账户
        List<UserIdentity> userList = new ArrayList<UserIdentity>();

        // 转让人
        UserIdentity transfer = new UserIdentity();
        transfer.setUserId(transferLoanDO.getTlUserId());
        transfer.setUserType(queryUserTypeByUserId(transfer.getUserId()));
        transfer.setAccountCategory(AccountCategory.INVESTACCOUNT.getType());
        userList.add(transfer);

        // 投资人
        final Map<Integer, UserType> investUserTypeMap = new HashMap<Integer, UserType>();
        for (Invest invest : investList) {
            UserIdentity investor = new UserIdentity();
            investor.setUserId(invest.getUserId());
            investor.setUserType(queryUserTypeByUserId(investor.getUserId()));
            investor.setAccountCategory(AccountCategory.INVESTACCOUNT.getType());
            userList.add(investor);

            investUserTypeMap.put(invest.getId(), investor.getUserType());
        }

        ListResult<Account> accountResult = accountInfoService.queryByUserIds(userList);
        List<Account> userAccountList = accountResult.getData();
        if (!accountResult.isSuccess()) {
            throw new BusinessException("收益计划创建失败");
        }

        // 转让人账户、投资人账户
        final Map<String, String> userIdAndAccountMap = new HashMap<String, String>();
        for (Account acc : userAccountList) {
            userIdAndAccountMap.put(acc.getAccountUserId() + "|" + acc.getAccountUserType().getType(),
                    acc.getAccountNo());
        }
        // 平台账户
        final String platformAccount = platformAccountResult.getData().getConfValue();

        //从abc_invest_order获取该原始标号在钱多多平台投资的标号
        List<InvestOrderDO> listLoanSeq = investOrderService.queryInvestOrderByBidId(transLoanId,
                BidType.TRANSFER_LOAN.getType());
        StringBuffer loanNo = new StringBuffer();
        for (int i = 0; i < listLoanSeq.size(); i++) {
        	InvestOrderDO investOrderDO =listLoanSeq.get(i);
        	 String seq = investOrderDO.getIoOutSeqNo();
             if (i == 0) {
                 loanNo = loanNo.append(seq);
             } else {
                 loanNo = loanNo.append(",").append(seq);
             }
        }
        String LoanNoList = loanNo.toString();
        Map params = new HashMap();
        AccountInfoDO accountInfoDO = accountInfoService.queryByAccountMark(transfer.getUserId(), transfer
                .getUserType().getType());
        // 4.2 放款记录(使用investOrder)
        for (InvestOrderDO investOrderDO : listLoanSeq) {
            DealDetail investDetail = new DealDetail();

            String payAccountId = userIdAndAccountMap.get(investOrderDO.getIoUserId() + "|"
                    + queryUserTypeByUserId(investOrderDO.getIoUserId()).getType());
            String receiveAccountId = userIdAndAccountMap.get(transfer.getUserId() + "|"
                    + transfer.getUserType().getType());
            investDetail.setDealDetailType(DealDetailType.DEBT_TRANSFER_MONEY);
            investDetail.setPayAccountId(payAccountId);
            investDetail.setReceiveAccountId(receiveAccountId);
            params.put("LoanNoList", LoanNoList);
            investDetail.setMoneyAmount(investOrderDO.getIoOrderMoney());
            investDetail.setData(params);
            dealDetailList.add(investDetail);
        }

        // 4.3 收取转让费记录
        if (collectTransferFee != null) {
            DealDetail operateDetail = new DealDetail();
            operateDetail.setDealDetailType(DealDetailType.DEBT_TRANSFER_FEE);
//            String payAccountId = userIdAndAccountMap.get(transfer.getUserId() + "|" + transfer.getUserType().getType());
            operateDetail.setPayAccountId(accountInfoDO.getAccountNo());
            operateDetail.setReceiveAccountId(platformAccount);
            operateDetail.setMoneyAmount(collectTransferFee);
            params.put("userId", transfer.getUserId());
            params.put("type", transfer.getUserType().getType());
            operateDetail.setData(params);
            dealDetailList.add(operateDetail);
        }

        deal.setBusinessId(loanDO.getLoanId());
        deal.setBusinessType(DealType.TRANSFER);
        deal.setInnerSeqNo(InnerSeqNo.getInstance());
        deal.setOperator(operatorId);
        deal.setDealDetail(dealDetailList);

        PlainResult<DealReturn> dealResult = dealRecordService.createBusinessRecord(deal, moneyTransferedCallback);
        if (!dealResult.isSuccess()) {
            throw new BusinessException("满标资金划转交易记录创建失败");
        }

        // 5. 添加满标资金划转记录， 状态为 DealState.NOCALLBACK
        FullTransferRecordDO fullTransRecord = new FullTransferRecordDO();

        fullTransRecord.setFtrBidId(transLoanId);
        fullTransRecord.setFtrOriginId(loanDO.getLoanId());
        fullTransRecord.setFtrOperator(operatorId);
        fullTransRecord.setFtrUserId(transferLoanDO.getTlUserId());
        fullTransRecord.setFtrBidType(BidType.TRANSFER_LOAN.getType());
        fullTransRecord.setFtrSeqNo(dealResult.getData().getDrInnerSeqNo());
        fullTransRecord.setFtrTransferDate(new Date());
        fullTransRecord.setFtrTransferType(transferType.getType());

        fullTransRecord.setFtrTransferMoney(transferLoanDO.getTlCurrentValidInvest().subtract(collectTransferFee));
        if (fullTransRecord.getFtrTransferMoney().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("满标资金划转金额须是正数");
        }

        fullTransRecord.setFtrPayFee(expectTransferFee);
        fullTransRecord.setFtrGuarFee(BigDecimal.ZERO);

        fullTransRecord.setFtrRealPayFee(collectTransferFee);
        fullTransRecord.setFtrRealGuarFee(BigDecimal.ZERO);
        fullTransRecord.setFtrDealState(DealState.NOCALLBACK.getState());

        // 满标资金划转记录id
        fullTransferRecordDao.insert(fullTransRecord);
        final int fullTransferRecordId = fullTransRecord.getFtrId();
        //转让标调用审核接口

        // 6. 生成收益计划, 状态为 IncomePlanState.INACTIVED
        
        //查询上期还款计划,用于转让利息计算
		Date prePayTime = null;
		IncomePlan firstIncome = transferIncomeResult.getData().get(0);
		if (firstIncome.getLoanPeriod() != 1) {
			//第二期或以后期数，取上期还款计划的应还日期，只考虑一转，前台控制不允许一期之内二转
			PaymentPlanDO prePayment = new PaymentPlanDO();
			prePayment.setPpLoanId(loanDO.getLoanId());
			prePayment.setPpLoanPeriod(firstIncome.getLoanPeriod() - 1);
			List<PaymentPlanDO> prePaymentList = paymentPlanDao
					.findListByParam(prePayment, null);
			if (CollectionUtils.isEmpty(prePaymentList)
					|| prePaymentList.size() != 1) {
				throw new BusinessException("查询上期还款计划失败");
			}
			prePayTime = prePaymentList.get(0).getPpPaytime();
		} else {
			//第一期 取收益计划创建时间
			prePayTime = firstIncome.getCreateDate();
		}
        
        PlanBuilder planBuilder = PlanBuilderFactory.createPlanBuilder(LoanPeriodUnit.DAY);

        List<IncomePlan> incomePlanList = planBuilder.buildTransferIncomePlanList(
                TransferLoanConverter.toTransferLoan(transferLoanDO), transferIncomeResult.getData(), investList,
                fullTransferRecordId, prePayTime);

        PlainResult<Integer> createIncomeResult = incomePlanService.batchCreateIncomePlanList(incomePlanList);
        if (!createIncomeResult.isSuccess()) {
            throw new BusinessException("收益计划创建失败");
        }

        // 目前的债权人转让是将债权人的所有未收益的收益计划查出来，进行转让
        // 如果要支持债权人部分转让，则应只改变用来转让的那几期的状态
        //        PlainResult<Integer> batchModifyIncomeStateResult = incomePlanService
        //                .batchModifyStateByUserIdAndFullTransRecordId(transferLoanDO.getTlUserId(),
        //                        paymentLock.getPpFullTransRecordId(), IncomePlanState.GOING, IncomePlanState.TRANSFERED);
        //        if (!batchModifyIncomeStateResult.isSuccess()) {
        //            throw new BusinessException("收益计划修改失败");
        //        }

        // 7. 执行资金划转交易
        BaseResult dealInvoke = dealRecordService.invokePayment(deal.getInnerSeqNo().getUniqueNo());
        if (!dealInvoke.isSuccess()) {
            log.warn("满标资金划转交易执行失败! {}", dealInvoke.getMessage());
            throw new BusinessException("满标资金划转交易执行失败");
        }
        
        // 8.投标分析
//        try
//        {
//        	investSearchDO = new InvestSearchDO();
//        	investSearchDO.setBidId(transLoanId);
//            investSearchDO.setBidType(BidType.TRANSFER_LOAN.getType());
//			
//            new InvestPrizeThread(investSearchDO, investQueryService, activityService, 
//            		redService, sendMsgService, userService).start();
//        }
//        catch (Exception ex)
//        {
//        	ex.printStackTrace();
//        	log.error("投标分析任务执行异常", ex);
//        }

        return result;
    }

    /**
     * 收购标满标资金划转<br>
     * 1. 前置条件判断<br>
     * 1.1 加行级锁住还款计划<br>
     * 1.2 满标前有还款行为，则直接流标<br>
     * 2. 计算收购费<br>
     * 3. 更新标状态<br>
     * 4. 添加交易记录<br>
     * 4.1 交易账户查询<br>
     * 4.2 放款记录<br>
     * 4.3 收取转让费记录<br>
     * 5. 添加满标资金划转记录， 状态为 DealState.NOCALLBACK<br>
     * 6. 生成收益计划, 状态为 IncomePlanState.INACTIVED<br>
     * 7. 执行资金划转交易
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult buyBidMoneyTransfer(int buyLoanId, double actrualOperateFee, FullTransferType transferType,
                                          int operatorId) {
        BaseResult result = new BaseResult();

        // 1. 前置条件判断
        if (actrualOperateFee < 0) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "实收手续费不能为负数");
        }

        BuyLoanDO buyLoanDO = buyLoanDao.findById(buyLoanId);
        LoanDO loanDO = loanDao.findById(buyLoanDO.getBlOriginId());

        if (!buyLoanDO.getBlState().equals(BuyLoanState.FULL_REVIEW_PASS.getState())) {
            return new BaseResult().setError(CommonResultCode.BIZ_ERROR, "当前标的状态不能进行满标资金划转");
        }

        PlainResult<PaymentPlan> newPaymentResult = paymentPlanService.queryNextPaymentPlan(loanDO.getLoanId());
        if (!newPaymentResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "查询借款标要进行的下一期还款计划失败");
        }

        // 1.1 加行级锁住还款计划
        PaymentPlanDO paymentLock = paymentPlanDao.findWithLock(buyLoanDO.getBlNextPaymentId());
        if (paymentLock == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "还款时加行级锁失败");
        }

        // 1.2 满标前有还款行为，则直接流标
        if (!paymentLock.getPpPayState().equals(PayState.UNCLEAR.getState())) {
            //            BuyLoanDO toModify = new BuyLoanDO();
            //            toModify.setBlId(buyLoanDO.getBlId());
            //            toModify.setBlState(BuyLoanState.BID_CANCELED.getState());
            //            buyLoanDao.update(toModify);
            BaseResult cancelBuyResult = buyLoanManageService.cancelBuyLoan(buyLoanId, 0, "收购标发起后到满标资金划转期间借款人有还款行为");
            if (!cancelBuyResult.isSuccess()) {
                result.setError(CommonResultCode.BIZ_ERROR, "收购标发起后到满标资金划转期间借款人有还款行为，自动流标失败");
            }

            return result.setError(CommonResultCode.BIZ_ERROR, "收购标发起后到满标资金划转期间借款人有还款行为");
        }

        PlainResult<SysConfig> platformAccountResult = sysConfigService.querySysConfig(SysConfigEntry.PLATFORM_ACCOUNT);
        if (!platformAccountResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "平台账户查询失败");
        }

        InvestSearchDO investSearchDO = new InvestSearchDO();
        investSearchDO.setBidId(buyLoanId);
        investSearchDO.setBidType(BidType.BUY_LOAN.getType());
        investSearchDO.setInvestStates(Arrays.asList(InvestState.PAID.getState()));
        ListResult<Invest> investResult = investQueryService.queryInvestList(investSearchDO);
        List<Invest> investList = investResult.getData();
        if (!investResult.isSuccess() || CollectionUtils.isEmpty(investList)) {
            return result.setError(CommonResultCode.BIZ_ERROR, "收购标投资记录查询失败");
        }

        // 2. 计算收购手续费
        PlainResult<FeeSetting> buyFeeResult = feeSettingService.queryByFeeTypeLoanCategory(FeeType.PURCHASE_FEE,
                LoanCategory.valueOf(loanDO.getLoanCategory()), loanDO.getLoanMoney());
        if (!buyFeeResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "平台收购手续费查询失败");
        }

        BigDecimal collectBuyFee = new BigDecimal(actrualOperateFee);
        BigDecimal expectBuyFee = calculateFee(buyFeeResult.getData(), loanDO.getLoanCurrentValidInvest());
        if (collectBuyFee.compareTo(expectBuyFee) > 0) {
            return result.setError(CommonResultCode.BIZ_ERROR, "实收收购手续费不能大于应收收购手续费");
        }

        // 3. 更新标状态
        int count = buyLoanDao.updateState(buyLoanId, BuyLoanState.FULL_REVIEW_PASS.getState(),
                BuyLoanState.MONEY_TRANSFERING.getState());
        if (count != 1) {
            throw new BusinessException("收购标的状态更改失败");
        }

        // 4. 添加交易记录
        // 4.1 交易账户查询
        Deal deal = new Deal();
        List<DealDetail> dealDetailList = new ArrayList<DealDetail>();

        // 查询收购人账户、投资人账户、平台账户
        List<UserIdentity> userList = new ArrayList<UserIdentity>();

        // 收购人
        UserIdentity buyer = new UserIdentity();
        buyer.setUserId(buyLoanDO.getBlUserId());
        buyer.setUserType(queryUserTypeByUserId(buyer.getUserId()));
        buyer.setAccountCategory(AccountCategory.INVESTACCOUNT.getType());
        userList.add(buyer);

        // 投资人
        final Map<Integer, UserType> investUserTypeMap = new HashMap<Integer, UserType>();
        for (Invest invest : investList) {
            UserIdentity investor = new UserIdentity();
            investor.setUserId(invest.getUserId());
            investor.setUserType(queryUserTypeByUserId(investor.getUserId()));
            investor.setAccountCategory(AccountCategory.INVESTACCOUNT.getType());
            userList.add(investor);

            investUserTypeMap.put(invest.getId(), investor.getUserType());
        }

        ListResult<Account> accountResult = accountInfoService.queryByUserIds(userList);
        List<Account> userAccountList = accountResult.getData();
        if (!accountResult.isSuccess()) {
            throw new BusinessException("收益计划创建失败");
        }

        // 收购人账户、投资人账户
        final Map<String, String> userIdAndAccountMap = new HashMap<String, String>();
        for (Account acc : userAccountList) {
            userIdAndAccountMap.put(acc.getAccountUserId() + "|" + acc.getAccountUserType().getType(),
                    acc.getAccountNo());
        }

        // 平台账户
        final String platformAccount = platformAccountResult.getData().getConfValue();

        // 4.2 放款记录
        for (Invest invest : investList) {
            DealDetail investDetail = new DealDetail();
            investDetail.setDealDetailType(DealDetailType.PURCHASE_MONEY);

            String payAccountId = userIdAndAccountMap.get(buyer.getUserId() + "|" + buyer.getUserType().getType());
            String receiveAccountId = userIdAndAccountMap.get(invest.getUserId() + "|"
                    + investUserTypeMap.get(invest.getId()).getType());

            investDetail.setPayAccountId(payAccountId);
            investDetail.setReceiveAccountId(receiveAccountId);
            investDetail.setMoneyAmount(invest.getValidInvestMoney());
            dealDetailList.add(investDetail);
        }

        // 4.3 收取收购费记录
        if (collectBuyFee != null) {
            DealDetail operateDetail = new DealDetail();
            operateDetail.setDealDetailType(DealDetailType.PURCHASE_FEE);

            String payAccountId = userIdAndAccountMap.get(buyer.getUserId() + "|" + buyer.getUserType().getType());

            operateDetail.setPayAccountId(payAccountId);
            operateDetail.setReceiveAccountId(platformAccount);
            operateDetail.setMoneyAmount(collectBuyFee);
            dealDetailList.add(operateDetail);
        }

        deal.setBusinessId(loanDO.getLoanId());
        deal.setBusinessType(DealType.TRANSFER);
        deal.setInnerSeqNo(InnerSeqNo.getInstance());
        deal.setOperator(operatorId);
        deal.setDealDetail(dealDetailList);

        PlainResult<DealReturn> dealResult = dealRecordService.createBusinessRecord(deal, moneyTransferedCallback);
        if (!dealResult.isSuccess()) {
            throw new BusinessException("满标资金划转交易记录创建失败");
        }

        // 5. 添加满标资金划转记录， 状态为 DealState.NOCALLBACK
        FullTransferRecordDO fullTransRecord = new FullTransferRecordDO();

        fullTransRecord.setFtrBidId(buyLoanId);
        fullTransRecord.setFtrOriginId(loanDO.getLoanId());
        fullTransRecord.setFtrOperator(operatorId);
        fullTransRecord.setFtrUserId(buyLoanDO.getBlUserId());
        fullTransRecord.setFtrBidType(BidType.BUY_LOAN.getType());
        fullTransRecord.setFtrSeqNo(dealResult.getData().getDrInnerSeqNo());
        fullTransRecord.setFtrTransferDate(new Date());
        fullTransRecord.setFtrTransferType(transferType.getType());

        fullTransRecord.setFtrTransferMoney(buyLoanDO.getBlCurrentValidInvest().subtract(collectBuyFee));
        if (fullTransRecord.getFtrTransferMoney().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("满标资金划转金额须是正数");
        }

        fullTransRecord.setFtrPayFee(collectBuyFee);
        fullTransRecord.setFtrGuarFee(BigDecimal.ZERO);

        fullTransRecord.setFtrRealPayFee(BigDecimal.ZERO);
        fullTransRecord.setFtrRealGuarFee(BigDecimal.ZERO);
        fullTransRecord.setFtrDealState(DealState.NOCALLBACK.getState());

        // 满标资金划转记录id
        fullTransferRecordDao.insert(fullTransRecord);
        final int fullTransferRecordId = fullTransRecord.getFtrId();

        // 6. 生成收益计划, 状态为 IncomePlanState.INACTIVED
        PlanBuilder planBuilder = PlanBuilderFactory.createPlanBuilder(LoanPeriodUnit.MONTH);

        // 目前的投资人认购是将投资人的所有未收益的收益计划查出来，进行认购
        // 如果要支持投资人部分认购，则此处需要查出的是投资人要用来部分认购的未收益的收益计划
        final List<Integer> beneficiaryIdList = new ArrayList<Integer>(investList.size());
        for (Invest invest : investList) {
            beneficiaryIdList.add(invest.getUserId());
        }

        Map params = new HashMap();
        DealDetail operateDetail = new DealDetail();
        //获取loanjsonList
        StringBuffer loanjsonList = new StringBuffer();
        //用于获取批量乾多多流水号集合
        List<String> listSeqNo = new ArrayList<String>();
        if (listSeqNo.size() > 0) {
            for (int i = 0; i < listSeqNo.size(); i++) {
                loanjsonList.append(listSeqNo.get(i)).append(",");
            }
        }
        params.put("loanjsonList", loanjsonList);
        operateDetail.setData(params);
        dealDetailList.add(operateDetail);

        ListResult<IncomePlan> buyIncomeResult = incomePlanService.queryIncomePlanList(IncomePlanState.GOING,
                loanDO.getLoanId(), beneficiaryIdList);
        if (!buyIncomeResult.isSuccess() || CollectionUtils.isEmpty(buyIncomeResult.getData())) {
            throw new BusinessException("认购人的收益计划查询失败");
        }

        List<IncomePlan> incomePlanList = planBuilder.buildBuyIncomePlanList(BuyLoanConverter.toBuyLoan(buyLoanDO),
                buyIncomeResult.getData(), investList, fullTransferRecordId);

        PlainResult<Integer> createIncomeResult = incomePlanService.batchCreateIncomePlanList(incomePlanList);
        if (!createIncomeResult.isSuccess()) {
            throw new BusinessException("收益计划创建失败");
        }

        //        PlainResult<Integer> batchModifyIncomeStateResult = incomePlanService.batchUpdateStateByLoanId(
        //                loanDO.getLoanId(), beneficiaryIdList, IncomePlanState.GOING, IncomePlanState.BUYED);
        //        if (!batchModifyIncomeStateResult.isSuccess()) {
        //            throw new BusinessException("认购人的收益计划修改失败");
        //        }

        // 7. 执行资金划转交易
        BaseResult dealInvoke = dealRecordService.invokePayment(deal.getInnerSeqNo().getUniqueNo());
        if (!dealInvoke.isSuccess()) {
            log.warn("满标资金划转交易执行失败! {}", dealInvoke.getMessage());
            throw new BusinessException("满标资金划转交易执行失败");
        }

        return result;
    }

    private BigDecimal calculateFee(FeeSetting feeSetting, BigDecimal base) {
        if (feeSetting == null) {
            return null;
        }

        switch (feeSetting.getChargeType()) {
            case BY_DEAL: {
                return feeSetting.getAccurateAmount();
            }
            case BY_RATIO: {
                double fee = base.doubleValue() * feeSetting.getRate() / 100;
                //                if (fee < feeSetting.getMinAmount().doubleValue()) {
                //                    return feeSetting.getMinAmount();
                //                } else if (fee > feeSetting.getMaxAmount().doubleValue()) {
                //                    return feeSetting.getMaxAmount();
                //                } else {
                return new BigDecimal(fee).setScale(2, BigDecimal.ROUND_HALF_UP);
                //                }
            }
            default:
                return null;
        }
    }

    @Override
    public PageResult<FullTransferRecordJDO> queryMoneyTransferList(FullTransferRecordSearchDO fullTransferRecordSearchDO,
                                                                    PageCondition pageCondition) {
        int count = fullTransferRecordDao.countMoneyTransferList(fullTransferRecordSearchDO, pageCondition);
        PageResult<FullTransferRecordJDO> result = new PageResult<FullTransferRecordJDO>(pageCondition);
        if (count > 0) {
            List<FullTransferRecordJDO> list = this.fullTransferRecordDao.getMoneyTransferList(
                    fullTransferRecordSearchDO, pageCondition);
            result.setData(list);
            result.setTotalCount(count);
        }
        return result;
    }

    @Override
    public PageResult<FullTransferRecordJDO> queryAttFulScaTsfListView(FullTransferRecordSearchDO fullTransferRecordSearchDO,
                                                                       PageCondition pageCondition) {
        int count = fullTransferRecordDao.countAttFulScaTsfListView(fullTransferRecordSearchDO, pageCondition);
        PageResult<FullTransferRecordJDO> result = new PageResult<FullTransferRecordJDO>(pageCondition);
        if (count > 0) {
            List<FullTransferRecordJDO> list = this.fullTransferRecordDao.getAttFulScaTsfListView(
                    fullTransferRecordSearchDO, pageCondition);

            result.setData(list);
            result.setTotalCount(count);
        }
        return result;
    }

    @Override
    public PageResult<FullTransferRecordJDO> queryBuyFullTransferListView(FullTransferRecordSearchDO fullTransferRecordSearchDO,
                                                                          PageCondition pageCondition) {
        int count = fullTransferRecordDao.countBuyFullTransferListView(fullTransferRecordSearchDO, pageCondition);
        PageResult<FullTransferRecordJDO> result = new PageResult<FullTransferRecordJDO>(pageCondition);
        if (count > 0) {
            List<FullTransferRecordJDO> list = this.fullTransferRecordDao.getBuyFullTransferListView(
                    fullTransferRecordSearchDO, pageCondition);
            result.setData(list);
            result.setTotalCount(count);
        }
        return result;
    }

    private UserType queryUserTypeByUserId(int userId) {
        PlainResult<User> userResult = userService.findEntityById(userId);
        if (!userResult.isSuccess() || userResult.getData() == null) {
            throw new BusinessException("用户类型查询失败");
        }

        return userResult.getData().getUserType();
    }

}
