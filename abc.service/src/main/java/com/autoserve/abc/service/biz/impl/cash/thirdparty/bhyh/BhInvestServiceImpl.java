package com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testng.collections.Maps;

import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.BuyLoanDO;
import com.autoserve.abc.dao.dataobject.BuyLoanSubscribeDO;
import com.autoserve.abc.dao.dataobject.CompanyCustomerDO;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.InvestDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.PaymentPlanDO;
import com.autoserve.abc.dao.dataobject.RedUseDO;
import com.autoserve.abc.dao.dataobject.RedsendDO;
import com.autoserve.abc.dao.dataobject.SysMessageInfoDO;
import com.autoserve.abc.dao.dataobject.TransferLoanDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.stage.statistics.CollectedAndStill;
import com.autoserve.abc.dao.dataobject.stage.statistics.TenderOverview;
import com.autoserve.abc.dao.intf.BuyLoanDao;
import com.autoserve.abc.dao.intf.BuyLoanSubscribeDao;
import com.autoserve.abc.dao.intf.DealRecordDao;
import com.autoserve.abc.dao.intf.FullTransferRecordDao;
import com.autoserve.abc.dao.intf.IncomePlanDao;
import com.autoserve.abc.dao.intf.InvestDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.PaymentPlanDao;
import com.autoserve.abc.dao.intf.RedUseDao;
import com.autoserve.abc.dao.intf.RedsendDao;
import com.autoserve.abc.dao.intf.TransferLoanDao;
import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.convert.InvestConverter;
import com.autoserve.abc.service.biz.convert.LoanConverter;
import com.autoserve.abc.service.biz.convert.TransferLoanConverter;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.ActivityRecord;
import com.autoserve.abc.service.biz.entity.BuyLoan;
import com.autoserve.abc.service.biz.entity.BuyLoanSubscribe;
import com.autoserve.abc.service.biz.entity.BuyLoanTraceRecord;
import com.autoserve.abc.service.biz.entity.Deal;
import com.autoserve.abc.service.biz.entity.DealDetail;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.entity.FeeSetting;
import com.autoserve.abc.service.biz.entity.IncomePlan;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.InvestOrder;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.AccountCategory;
import com.autoserve.abc.service.biz.enums.ActivityType;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.BuyLoanState;
import com.autoserve.abc.service.biz.enums.BuyLoanSubscribeState;
import com.autoserve.abc.service.biz.enums.BuyLoanTraceOperation;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.FeeType;
import com.autoserve.abc.service.biz.enums.IncomePlanState;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanPeriodUnit;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.OrderState;
import com.autoserve.abc.service.biz.enums.RedState;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.enums.RsState;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.service.biz.enums.UserBusinessState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.AuthorizeUtil;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.impl.loan.plan.PlanBuilder;
import com.autoserve.abc.service.biz.impl.loan.plan.PlanBuilderFactory;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.cash.PayMentService;
import com.autoserve.abc.service.biz.intf.invest.ActivityRecordService;
import com.autoserve.abc.service.biz.intf.invest.InvestOrderService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.loan.BuyLoanService;
import com.autoserve.abc.service.biz.intf.loan.BuyLoanSubscribeService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.redenvelope.RedsendService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.intf.sys.FeeSettingService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.sys.SysMessageInfoService;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.message.mail.MailSenderInfo;
import com.autoserve.abc.service.message.mail.SendMailService;
import com.autoserve.abc.service.message.mail.SimpleMailSenderService;
import com.autoserve.abc.service.util.Arith;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.service.util.Jpush.JpushUtils;

/**
 * 投资服务
 * 
 * @author segen189 2014年11月19日 下午2:26:50
 */
@Service
public class BhInvestServiceImpl implements InvestService {
    private static final Logger     log = LoggerFactory.getLogger(BhInvestServiceImpl.class);

    @Resource
    private InvestDao               investDao;

    @Resource
    private InvestOrderService      investOrderService;

    @Resource
    private ActivityRecordService   activityRecordService;

    @Resource
    private DealRecordService       dealRecordService;

    @Resource
    private LoanDao                 loanDao;

    @Resource
    private TransferLoanDao         transferLoanDao;

    @Resource
    private BuyLoanDao              buyLoanDao;

    @Resource
    private BuyLoanSubscribeDao     buyLoanSubscribeDao;

    @Resource
    private LoanService             loanService;

    @Resource
    private TransferLoanService     transferLoanService;

    @Resource
    private BuyLoanService          buyLoanService;

    @Resource
    private BuyLoanSubscribeService buyLoanSubscribeService;

    @Resource
    private IncomePlanService       incomePlanService;

    @Resource
    private AccountInfoService      accountInfoService;

    @Resource
    private UserService             userService;

    @Resource
    private Callback<DealNotify>    investPaidCallback;

    @Resource
    private ReviewService           reviewService;

    @Resource
    private Callback<DealNotify>    investWithdrawedCallback;

    @Resource
    private SysConfigService        sysConfigService;

    @Resource
    private RedsendService          redsendService;

    @Resource
    private RedUseDao               redUseDao;

    @Resource
    private IncomePlanDao           incomePlanDao;

    @Resource
    private PaymentPlanDao          paymentPlanDao;
    
    @Autowired
    private RedsendDao redSendDao;
    
    @Resource
    private InvestQueryService        investQueryService;
    @Resource
    private FullTransferRecordDao     fullTransferRecordDao;

    @Resource
    private PaymentPlanService        paymentPlanService;

	@Autowired
    private PayMentService payMentService;
	
	@Resource
	private FeeSettingService feeSettingService;
	@Resource
	private DealRecordDao dealRecordDao;
    @Resource
    private SendMailService  sendMailService;
    @Resource
	private HttpSession session;
    @Resource
	private DoubleDryService doubleDryService;
    @Resource
    private CompanyCustomerService  companyCustomerService;
    @Resource
    private SimpleMailSenderService simpleMailSender;
    
    @Autowired
	private SysMessageInfoService messageInfoService;
    /**
     * Semaphore是非常有用的一个组件，它相当于是一个并发控制器，是用于管理信号量的。构造的时候传入可供管理的信号量的数值，
	 * 这个数值就是控制并发数量的，我们需要控制并发的代码，执行前先通过acquire方法获取信号，执行后通过release归还信号 。
	 * 每次acquire返回成功后，Semaphore可用的信号量就会减少一个，如果没有可用的信号，
	 * acquire调用就会阻塞，等待有release调用释放信号后，acquire才会得到信号并返回。
     */
    private final Semaphore semaphore = new Semaphore(1);
    //原子变量
    private final AtomicInteger atomicInteger = new AtomicInteger(0);
    /**
     * 检查投资条件是否满足
     * 1、投资户是否开户
     * 2、平台交易密码（银行存管密码在开户时已经设置）
     * 3、出借是否授权
     * 4、是否风险评估
     * @param userId
     * @return
     */
    @Override
    public Map<String,String> checkInvestConditions(Integer userId){
    	Map<String,String> resultMap = new HashMap<String, String>();
    	resultMap.put("status", "true");
    	resultMap.put("allConditions", "OK");
    	 // 查询用户类型、用户名称
        PlainResult<UserDO> userResult = this.userService.findById(userId);
        if (!userResult.isSuccess() || userResult.getData() == null) {
             resultMap.put("status", "false");
             resultMap.put("message", "用户查询失败");
             return resultMap;
        }
        if(UserType.PERSONAL.type==userResult.getData().getUserType()){
        	resultMap.put("userType", "PERSONAL");
        	//1、投资户是否开户
        	UserDO userDO = new UserDO();
        	userDO.setUserId(userId);
        	userDO.setUserType(userResult.getData().getUserType());
        	userDO.setAccountCategory(AccountCategory.INVESTACCOUNT.type);
        	AccountInfoDO investAccount = accountInfoService.getAccountByCategory(userDO);
        	if(null == investAccount || StringUtils.isEmpty(investAccount.getAccountNo())){
        		resultMap.put("accountCondition", "NO");
        		resultMap.put("allConditions", "NO");
        	}else{
        		resultMap.put("accountCondition", "OK");
        	}
        	//2、平台交易密码
        	if(StringUtils.isEmpty(userResult.getData().getUserDealPwd())){
        		resultMap.put("dealPwdCondition", "NO");
        		resultMap.put("allConditions", "NO");
        	}else{
        		resultMap.put("dealPwdCondition", "OK");
        	}
        	//3、出借是否授权
        	//投标授权有效开始日
        	Date investStartDate=userResult.getData().getAuthorizeInvestStartDate();
        	//投标授权有效结束日
        	Date investEndDate=userResult.getData().getAuthorizeInvestEndDate();
        	//出借授权
        	String authorizeInvestType = userResult.getData().getAuthorizeInvestType();
        	//授权状态
        	String authorizeStatus = "";
        	if(null != investStartDate && null != investEndDate){
        		authorizeStatus = AuthorizeUtil.isAuthorize(investStartDate,investEndDate);
        	}
        	if(!"11".equals(authorizeInvestType)){
        		resultMap.put("authorizeCondition", "NO");
        		resultMap.put("allConditions", "NO");
        	}else if("11".equals(authorizeInvestType) && "未生效".equals(authorizeStatus)){
        		resultMap.put("authorizeCondition", "ineffective");
        		resultMap.put("allConditions", "NO");
        	}else if("11".equals(authorizeInvestType) && "已过期".equals(authorizeStatus)){
        		resultMap.put("authorizeCondition", "expired");
        		resultMap.put("allConditions", "NO");
        	}else if("11".equals(authorizeInvestType) && "有效".equals(authorizeStatus)){
        		resultMap.put("authorizeCondition", "OK");
        	}
        	//4、是否风险评估
        	if(null == userResult.getData().getAssId() || 0 == userResult.getData().getAssId()){
        		resultMap.put("riskCondition", "NO");
        		resultMap.put("allConditions", "NO");
        	}else{
        		resultMap.put("riskCondition", "OK");
        	}
        }else if(UserType.ENTERPRISE.type==userResult.getData().getUserType()){
        	resultMap.put("userType", "ENTERPRISE");
        	//1、融资户是否开户
        	UserDO userDO = new UserDO();
        	userDO.setUserId(userId);
        	userDO.setUserType(userResult.getData().getUserType());
        	userDO.setAccountCategory(AccountCategory.LOANACCOUNT.type);
        	AccountInfoDO loanAccount = accountInfoService.getAccountByCategory(userDO);
        	if(null == loanAccount || StringUtils.isEmpty(loanAccount.getAccountNo())){
        		resultMap.put("accountCondition", "NO");
        		resultMap.put("allConditions", "NO");
        	}else{
        		resultMap.put("accountCondition", "OK");
        	}
        	//2、缴费是否授权
        	//缴费授权有效开始日
        	Date feeStartDate=userResult.getData().getAuthorizeFeeStartDate();
        	//缴费授权有效结束日
        	Date feeEndDate=userResult.getData().getAuthorizeFeeEndDate();
        	//缴费授权
        	String authorizeFeeType = userResult.getData().getAuthorizeFeeType();
        	//授权状态
        	String authorizeStatus = "";
        	if(null != feeStartDate && null != feeEndDate){
        		authorizeStatus = AuthorizeUtil.isAuthorize(feeStartDate,feeEndDate);
        	}
        	if(!"59".equals(authorizeFeeType)){
        		resultMap.put("authorizeFeeCondition", "NO");
        		resultMap.put("allConditions", "NO");
        	}else if("59".equals(authorizeFeeType) && "未生效".equals(authorizeStatus)){
        		resultMap.put("authorizeFeeCondition", "ineffective");
        		resultMap.put("allConditions", "NO");
        	}else if("59".equals(authorizeFeeType) && "已过期".equals(authorizeStatus)){
        		resultMap.put("authorizeFeeCondition", "expired");
        		resultMap.put("allConditions", "NO");
        	}else if("59".equals(authorizeFeeType) && "有效".equals(authorizeStatus)){
        		resultMap.put("authorizeFeeCondition", "OK");
        	}
        	//3、还款是否授权
        	//还款授权有效开始日
        	Date repayStartDate=userResult.getData().getAuthorizeRepayStartDate();
        	//还款授权有效结束日
        	Date repayEndDate=userResult.getData().getAuthorizeRepayEndDate();
        	//还款授权
        	String authorizeRepayType = userResult.getData().getAuthorizeRepayType();
        	//授权状态
        	String authorizeRepayStatus = "";
        	if(null != repayStartDate && null != repayEndDate){
        		authorizeRepayStatus = AuthorizeUtil.isAuthorize(repayStartDate,repayEndDate);
        	}
        	if(!"60".equals(authorizeRepayType)){
        		resultMap.put("authorizeRepayCondition", "NO");
        		resultMap.put("allConditions", "NO");
        	}else if("60".equals(authorizeRepayType) && "未生效".equals(authorizeRepayStatus)){
        		resultMap.put("authorizeRepayCondition", "ineffective");
        		resultMap.put("allConditions", "NO");
        	}else if("60".equals(authorizeRepayType) && "已过期".equals(authorizeRepayStatus)){
        		resultMap.put("authorizeRepayCondition", "expired");
        		resultMap.put("allConditions", "NO");
        	}else if("60".equals(authorizeRepayType) && "有效".equals(authorizeRepayStatus)){
        		resultMap.put("authorizeRepayCondition", "OK");
        	}
        }
        return resultMap;
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public /*synchronized*/ PlainResult<Integer> createInvest(Invest pojo) {
        PlainResult<Integer> result = new PlainResult<Integer>();

        if (pojo == null) {
            result.setErrorMessage(CommonResultCode.ILLEGAL_PARAM_BLANK);
            return result;
        }

        // 默认不使用红包的情况下，应付金额等于实际投资金额
        pojo.setInvestPayMoney(pojo.getInvestMoney());
        try {
			semaphore.acquire();
			switch (pojo.getBidType()) {
			    case COMMON_LOAN:
			        return investCommonLoan(pojo, null);
			    case TRANSFER_LOAN:
			        return investTransferLoan(pojo);
			    case BUY_LOAN:
			        return investBuyLoan(pojo);
			    default:
			        throw new BusinessException("未知的标类型");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new BusinessException("标的状态发生改变，请刷新后重试");
		} finally {
            semaphore.release();
        }
    }

    /**
     * 投资普通标下单过程：<br>
     * 1. 执行数据库进行投资前置条件判断<br>
     * 1.1 基本前置条件判断<br>
     * 1.2 投资普通标前置条件判断<br>
     * 2. 修改普通标的投资金额、状态<br>
     * 3. 添加业务活动记录<br>
     * 4. 添加投资活动记录<br>
     * 5. 添加订单记录<br>
     * 6. 添加资金支付交易记录<br>
     * 7. 记录用户投资状态<br>
     * 8. 执行数据库操作<br>
     */
    private PlainResult<Integer> investCommonLoan(Invest pojo, List<Integer> redsendIdList) {
        PlainResult<Integer> result = new PlainResult<Integer>();
        // 设置用户类型、用户名称
        PlainResult<User> userResult = userService.findEntityById(pojo.getUserId());
        if (!userResult.isSuccess() || userResult.getData() == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "用户类型查询失败");
        }
        //判断投资人信息
        UserDO userDO = new UserDO();
        userDO.setUserId(pojo.getUserId());
        userDO.setUserType(userResult.getData().getUserType().type);
        userDO.setAccountCategory(AccountCategory.INVESTACCOUNT.type);
        AccountInfoDO investAccount = accountInfoService.getAccountByCategory(userDO);
        if(null == investAccount || StringUtils.isEmpty(investAccount.getAccountNo())){
        	 return result.setError(CommonResultCode.BIZ_ERROR, CommonResultCode.NO_INVEST_ACCOUNT.message);
        }
        //投资户余额
        Double[] accountBacance = doubleDryService.queryBalanceDetail(investAccount.getAccountNo());
        if(pojo.getInvestMoney().doubleValue()>accountBacance[0]){
        	return result.setError(CommonResultCode.BIZ_ERROR, "投资人投资户余额不足！");
        }
        //投标授权有效开始日
		Date investStartDate=userResult.getData().getAuthorizeInvestStartDate();
		//投标授权有效结束日
		Date investEndDate=userResult.getData().getAuthorizeInvestEndDate();
		//投标授权金额
		BigDecimal authorizeInvestAmount=null != userResult.getData().getAuthorizeInvestAmount()?userResult.getData().getAuthorizeInvestAmount():BigDecimal.ZERO;
		if(!"11".equals(userResult.getData().getAuthorizeInvestType())){
			return result.setError(CommonResultCode.BIZ_ERROR, "您还未开启出借授权，请先去授权！");
		}else if(!"有效".equals(AuthorizeUtil.isAuthorize(investStartDate,investEndDate))){
			return result.setError(CommonResultCode.BIZ_ERROR, "出借授权"+AuthorizeUtil.isAuthorize(investStartDate,investEndDate)+",请去修改！");
		}else if(pojo.getInvestMoney().doubleValue()>authorizeInvestAmount.doubleValue()){
			return result.setError(CommonResultCode.BIZ_ERROR, "投资金额超过您的出借授权金额,请去修改！");
		}
        // 1. 执行数据库进行投资前置条件判断: 是否已经满标，是否满足项目标的投标条件
        // 1.1 基本前置条件判断
        final LoanDO loanDO = loanDao.findByLoanIdWithLock(pojo.getBidId());
        if (loanDO == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "投资的标不存在");
        } else if (pojo.getUserId().equals(loanDO.getLoanUserId())) {
            return result.setError(CommonResultCode.BIZ_ERROR, "投资人和借款人不能为同一个人");
        }
        //判断此标是否已经进行强制满标(有效可投金额大于0，有效投资金额小于项目金额，项目状态10~16)
        if(loanDO.getLoanCurrentValidInvest().compareTo(BigDecimal.ZERO)>0   &&   
        		loanDO.getLoanCurrentValidInvest().compareTo(loanDO.getLoanMoney())<0    		
        		&& loanDO.getLoanState()>=10 && loanDO.getLoanState()<=16){
        	return result.setError(CommonResultCode.BIZ_ERROR, "此标已进行强制满标，不可投资");
        }
        if(loanDO.getLoanState()!=9){
        	return result.setError(CommonResultCode.BIZ_ERROR, "不是招标中的标，不可投资");
        }
        // 1.2 投资普通标前置条件判断
        Date nowDate = new Date();
        if (loanDO.getLoanInvestEndtime() != null && loanDO.getLoanInvestEndtime().before(nowDate)) {
            return result.setError(CommonResultCode.BIZ_ERROR, "投资结束时间已到，不可投资");
        } else if (loanDO.getLoanInvestStarttime() != null && loanDO.getLoanInvestStarttime().after(nowDate)) {
            return result.setError(CommonResultCode.BIZ_ERROR, "投资投资开始时间未到，不可投资");
        }

        if (loanDO.getLoanMaxInvest() != null && pojo.getInvestMoney().compareTo(loanDO.getLoanMaxInvest()) > 0) {
            return result.setError(CommonResultCode.BIZ_ERROR, "投资金额不能大于当前标设置的最大可投限制");
        }

        if (pojo.getInvestMoney().compareTo(loanDO.getLoanMinInvest()) < 0
                && loanDO.getLoanMinInvest().compareTo(loanDO.getLoanMoney().subtract(loanDO.getLoanCurrentValidInvest())) <= 0) {
            return result.setError(CommonResultCode.BIZ_ERROR, "投资金额不能小于当前标设置的最小可投限制");
        }
        if (loanDO.getLoanMinInvest().compareTo(loanDO.getLoanMoney().subtract(loanDO.getLoanCurrentValidInvest())) >0
        		&& pojo.getInvestMoney().compareTo(loanDO.getLoanMoney().subtract(loanDO.getLoanCurrentValidInvest()))!=0) {
        	return result.setError(CommonResultCode.BIZ_ERROR, "可投金额小于最小投资金额时,必须一次性投满");
        }

        BigDecimal newCurrentInvest = loanDO.getLoanCurrentInvest().add(pojo.getInvestMoney());
        BigDecimal newCurrentValidInvest = loanDO.getLoanCurrentValidInvest().add(pojo.getInvestMoney());
        if (newCurrentValidInvest.compareTo(loanDO.getLoanMoney()) > 0) {
            return result.setError(CommonResultCode.BIZ_ERROR, "投资金额不能超过当前标的实际可投金额");
        }
                
        PlainResult<Boolean> orderResult = queryOrderExistence(pojo.getBidId(), pojo.getBidType(), pojo.getUserId());
        if (!orderResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "查询订单表判断是否投资多次失败");
        }
        
        // 定向标的需要判断投资码
        if ("0".equals(loanDO.getIsShow()) && null != loanDO.getInvestCode() && !"".equals(loanDO.getInvestCode()))
        {
        	if (!loanDO.getInvestCode().equals(pojo.getInvestCode()))
        	{
        		return result.setError(CommonResultCode.BIZ_ERROR, "投标码错误");
        	}
        }

        /*
         * else if (!Boolean.FALSE.equals(orderResult.getData())) { return
         * result.setError(CommonResultCode.BIZ_ERROR, "不允许重复投资"); }
         */

        // 2. 修改普通标的投资金额、状态
        LoanDO toModify = new LoanDO();
        toModify.setLoanId(pojo.getBidId());
        toModify.setLoanCurrentInvest(newCurrentInvest);

        int modCount = loanDao.update(toModify);
        if (modCount <= 0) {
            throw new BusinessException("普通标投资金额修改失败");
        }

        // 3. 添加业务活动记录
        ActivityRecord activity = new ActivityRecord();
        activity.setCreator(pojo.getUserId());
        activity.setActivityType(ActivityType.INVEST_COMMON_LOAN);
        // activity.setForeignId(foreignId);
        InnerSeqNo innerSeqNo = InnerSeqNo.getInstance();
        //计算是否有红包
        //BigDecimal redAmount = pojo.getInvestMoney().subtract(pojo.getInvestPayMoney());
        ListResult<RedsendDO> list = redsendService.queryByIdList(redsendIdList);
	    BigDecimal redAmount = BigDecimal.ZERO;
	      for (RedsendDO redsendDO : list.getData()) {
	    	  redAmount = redAmount.add(BigDecimal.valueOf(redsendDO.getRsValidAmount()));
	      }
	      
        // 4. 添加投资活动记录        
        Invest invest = new Invest();
        invest.setOriginId(pojo.getOriginId());
        invest.setUserId(pojo.getUserId());
        invest.setInvestMoney(pojo.getInvestMoney());
        //invest.setValidInvestMoney(validInvestMoney); // callback
        pojo.setInvestState(InvestState.UNPAID);
        invest.setInvestState(InvestState.UNPAID);
        invest.setBidType(pojo.getBidType());
        invest.setBidId(pojo.getBidId());
        invest.setInnerSeqNo(innerSeqNo.toString());
        log.info("投资活动记录流水号：abc_invest.in_inner_seq_no========"+invest.getInnerSeqNo());

        // 5. 添加订单记录
        InvestOrder order = new InvestOrder();
        order.setOrderMoney(pojo.getInvestMoney());
        order.setOriginId(pojo.getOriginId());
        order.setOrderState(OrderState.UNPAID);
        order.setBidType(pojo.getBidType());
        order.setBidId(pojo.getBidId());
        order.setUserId(pojo.getUserId());
        order.setInnerSeqNo(innerSeqNo.toString() + 0);//没有使用红包加个0?
        log.info("投资订单记录流水号：abc_invest_order.io_inner_seq_no========"+order.getInnerSeqNo());
        // 6. 执行数据库添加
        // 添加订单记录
        PlainResult<Integer> createOrderResult = investOrderService.createInvestOrder(order);
        if (!createOrderResult.isSuccess()) {
            log.warn(createOrderResult.getMessage());
            throw new BusinessException("订单创建失败");
        }

        // 添加投资活动记录
        InvestDO investDO = InvestConverter.toInvestDO(invest);

        investDao.insert(investDO);
        pojo.setId(investDO.getInId());
        pojo.setInnerSeqNo(invest.getInnerSeqNo());
        // 添加业务活动记录
        activity.setForeignId(investDO.getInId());
        BaseResult activityResult = activityRecordService.createActivityRecord(activity);
        if (!activityResult.isSuccess()) {
            log.warn(activityResult.getMessage());
            throw new BusinessException("业务活动记录创建失败");
        }

        // 6. 添加资金支付交易记录
        List<UserIdentity> userList = new ArrayList<UserIdentity>(2);

        // 投资人
        UserIdentity investor = new UserIdentity();
        investor.setUserId(invest.getUserId());
        investor.setUserType(queryUserTypeByUserId(investor.getUserId()));
        investor.setAccountCategory(AccountCategory.INVESTACCOUNT.getType());
        userList.add(investor);

        // 借款人
        UserIdentity loanee = new UserIdentity();
        loanee.setUserId(loanDO.getLoanUserId());
        loanee.setUserType(queryUserTypeByUserId(loanee.getUserId()));
        loanee.setAccountCategory(AccountCategory.LOANACCOUNT.getType());
        userList.add(loanee);

        ListResult<Account> accountResult = accountInfoService.queryByUserIds(userList);
        if (!accountResult.isSuccess() || accountResult.getData().size() != 2) {
            log.warn(accountResult.getMessage());
            throw new BusinessException("用户账户查询失败");
        }

        List<Account> userAccountList = accountResult.getData();
        String investorAccountNo;
        String loaneeAccountNo;
        String payUserNo;
        String receiveUserNo;
        
        if (userAccountList.get(0).getAccountUserId().equals(investor.getUserId())) {
            investorAccountNo = userAccountList.get(0).getAccountNo();
            loaneeAccountNo = userAccountList.get(1).getAccountNo();
            payUserNo = userAccountList.get(0).getAccountNo();
            receiveUserNo = userAccountList.get(1).getAccountNo();
        } else {
            investorAccountNo = userAccountList.get(1).getAccountNo();
            loaneeAccountNo = userAccountList.get(0).getAccountNo();

            payUserNo = userAccountList.get(1).getAccountNo();
            receiveUserNo = userAccountList.get(0).getAccountNo();
        }
        
        Deal deal = new Deal();
        List<DealDetail> detailList = new ArrayList<DealDetail>(2);
        // 投资DealDetail
        if (pojo.getInvestPayMoney().compareTo(BigDecimal.ZERO) > 0) {

            Map params = new HashMap();
            params.put("payUserNo", payUserNo);
            params.put("receiveUserNo", receiveUserNo);
            params.put("BatchNo", pojo.getBidId());
            params.put("Secondary", loanDO.getLoanSecondaryAllocation());
            if (!"2".equals(loanDO.getLoanSecondaryAllocation())) {
                params.put("SecondaryUser", loanDO.getLoanSecondaryUser());
            }

            DealDetail dealDetail = new DealDetail();
            dealDetail.setData(params);
            dealDetail.setMoneyAmount(pojo.getInvestMoney());//冻结金额
            dealDetail.setPayAccountId(investorAccountNo);
//            dealDetail.setReceiveAccountId(loaneeAccountNo);
            dealDetail.setReceiveAccountId("");//避免借款人交易记录中多出一条 sunlu
            dealDetail.setDealDetailType(DealDetailType.INVESTE_MONEY);
            detailList.add(dealDetail);
        }

        //平台红包DealDetail（需要修改params！！！！！！！！！）
        if (redAmount.compareTo(BigDecimal.ZERO) > 0) {
        	
        	// 平台账户
            PlainResult<SysConfig> platformAccountResult = sysConfigService.querySysConfig(SysConfigEntry.PLATFORM_ACCOUNT);
            if (!platformAccountResult.isSuccess()) {
                throw new BusinessException("平台账户查询失败");
            }
            
            //判断使用限额
            double redRatio = loanDO.getInvestReduseRatio();
            if (redRatio > 0.00)
            {
            	if (redAmount.compareTo(pojo.getInvestMoney().multiply(new BigDecimal(redRatio)).divide(new BigDecimal("100"), 2)) > 0)
            	{
            		throw new BusinessException("红包总额已超过使用限额");
            	}
            }
            
            // 使用红包
            BaseResult useRedResult = useRedBeforeInvestCommonLoan(pojo, loanDO, redsendIdList);
            if (!useRedResult.isSuccess()) {
                log.warn(useRedResult.getMessage());
                throw new BusinessException(useRedResult.getMessage());
            }

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("payUserNo", platformAccountResult.getData().getConfValue());
           // params.put("receiveUserNo", receiveUserNo);
            params.put("receiveUserNo", payUserNo);//红包给投资人,第三方参数
            params.put("BatchNo", pojo.getBidId());
            params.put("Secondary", loanDO.getLoanSecondaryAllocation());
            if (!"2".equals(loanDO.getLoanSecondaryAllocation())) {
                params.put("SecondaryUser", loanDO.getLoanSecondaryUser());
            }
            //投资时去除记录，满标划转时会添加红包返还记录，避免投资人出现重复的红包记录
            /*DealDetail redDetail = new DealDetail();
            redDetail.setData(params);
            redDetail.setMoneyAmount(redAmount);
            redDetail.setPayAccountId(platformAccountResult.getData().getConfValue());
            //redDetail.setReceiveAccountId(loaneeAccountNo);
            redDetail.setReceiveAccountId(investorAccountNo);//红包给投资人，dealRecord表记录
            redDetail.setDealDetailType(DealDetailType.INVESTE_MONEY);
            detailList.add(redDetail);*/

            // 5. 添加订单记录
            InvestOrder redSendOrder = new InvestOrder();
            redSendOrder.setOrderMoney(redAmount);
            redSendOrder.setOriginId(pojo.getOriginId());
            redSendOrder.setOrderState(OrderState.UNPAID);
            redSendOrder.setBidType(pojo.getBidType());
            redSendOrder.setBidId(pojo.getBidId());
            redSendOrder.setUserId(pojo.getUserId());
            redSendOrder.setInnerSeqNo(innerSeqNo.toString() + 1);
            log.info("投资使用红包金额========"+redAmount.doubleValue());
            log.info("投资红包订单记录流水号：abc_invest_order.io_inner_seq_no========"+redSendOrder.getInnerSeqNo());
            // 6. 执行数据库添加
            // 添加订单记录
            PlainResult<Integer> createRedSendOrderResult = investOrderService.createInvestOrder(redSendOrder);
            if (!createRedSendOrderResult.isSuccess()) {
                log.warn(createOrderResult.getMessage());
                throw new BusinessException("订单创建失败");
            }
        }

        deal.setInnerSeqNo(innerSeqNo);
        deal.setBusinessType(DealType.INVESTER);
        deal.setOperator(pojo.getUserId());
        deal.setDealDetail(detailList);
        deal.setBusinessId(pojo.getOriginId());
        PlainResult<DealReturn> dealResult = dealRecordService.createBusinessRecord(deal, investPaidCallback);
        if (!dealResult.isSuccess()) {
            log.warn(dealResult.getMessage());
            throw new BusinessException("交易创建失败");
        }

        // 设置订单和投资的内部交易流水号参数
        order.setInnerSeqNo(dealResult.getData().getDrInnerSeqNo());
        invest.setInnerSeqNo(order.getInnerSeqNo());

        // 7. 记录用户投资状态
        BaseResult userModResult = userService.modifyUserBusinessState(investor.getUserId(), investor.getUserType(),
                UserBusinessState.INVESTED);
        if (!userModResult.isSuccess()) {
            log.warn(userModResult.getMessage());
            throw new BusinessException("用户投资状态记录失败");
        }

        // 9. 发起资金支付
        BaseResult invokeResult = dealRecordService.invokePayment(deal.getInnerSeqNo().getUniqueNo());
        if (!invokeResult.isSuccess()) {
            log.warn(invokeResult.getMessage());
            //            throw new BusinessException("发起资金支付失败");
            throw new BusinessException(invokeResult.getMessage());
        }
        result.setData(investDO.getInId());
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    private BaseResult useRedBeforeInvestCommonLoan(Invest pojo, LoanDO loanDO, List<Integer> redsendIdList) {
        BaseResult result = new BaseResult();

        if (CollectionUtils.isNotEmpty(redsendIdList)) {
            List<RedsendDO> redsendList = redSendDao.findListByIdsLock(redsendIdList);
          
            if (loanDO == null) {
                return result.setError(CommonResultCode.BIZ_ERROR, "要进行投资的红包不存在");
            }

            BigDecimal redTotal = BigDecimal.ZERO;

            // 遍历校验红包
            for (RedsendDO redsendDO : redsendList) {
                if (redsendDO.getRsState() != RedState.EFFECTIVE.state) {
                    return result.setError(CommonResultCode.BIZ_ERROR, "红包状态不是有效状态");
                }
                if(!redsendDO.getRsUserid().equals(pojo.getUserId())){
                	return result.setError(CommonResultCode.BIZ_ERROR, "非法使用红包");
                }
                if(DateUtil.compareDay(redsendDO.getRsClosetime(), new Date())<0){
                	return result.setError(CommonResultCode.BIZ_ERROR, "红包已过期");
                }
                

                String[] redscopes = StringUtils.split(redsendDO.getRsUseScope(), ",");
                boolean canUse = false;
                for (String scope : redscopes) {
                    if (scope.equals(LoanCategory.valueOf(loanDO.getLoanCategory()).prompt)) {
                        canUse = true;
                        break;
                    }
                }

                if (!canUse) {
                    return result.setError(CommonResultCode.BIZ_ERROR, "所选择的红包使用范围限制");
                }

                redTotal = redTotal.add(BigDecimal.valueOf(redsendDO.getRsValidAmount()));
            }

            // 红包发放记录状态修改
            BaseResult redSendModResult = redsendService.batchModifyState(redsendIdList, RsState.WITHOUT_USE,
                    RsState.USE);
            if (!redSendModResult.isSuccess()) {
                return result.setError(CommonResultCode.BIZ_ERROR, "红包发放记录状态修改失败");
            }

            // 添加红包使用记录
            List<RedUseDO> redUseList = new ArrayList<RedUseDO>(redsendList.size());

            for (RedsendDO redsendDO : redsendList) {
                RedUseDO redUseDO = new RedUseDO();

                redUseDO.setRuAmount(redsendDO.getRsValidAmount());
                redUseDO.setRuBizId(pojo.getId());//投资记录id
                redUseDO.setRuDeductDiscount(0D);
                redUseDO.setRuDeductFee(0D);
                redUseDO.setRuDesc("投资项目：" + loanDO.getLoanNo());
                redUseDO.setRuRedvsendId(redsendDO.getRsId());
                redUseDO.setRuRemainderAmount(0D);
                redUseDO.setRuType(redsendDO.getRsType());
                redUseDO.setRuUsecount(1);//使用次数
                redUseDO.setRuUserid(redsendDO.getRsUserid());
                redUseDO.setRuUsetime(new Date());

                redUseList.add(redUseDO);
            }

            redUseDao.batchInsert(redUseList);

            // 投资应支付金额
            BigDecimal needPayMoney = pojo.getInvestMoney().subtract(redTotal);
            if (needPayMoney.compareTo(BigDecimal.ZERO) <= 0) {
                needPayMoney = BigDecimal.ZERO;
            }

           // pojo.setInvestPayMoney(needPayMoney);
            pojo.setInvestPayMoney(pojo.getInvestMoney());
        } else {
            // 投资应支付金额
            pojo.setInvestPayMoney(pojo.getInvestMoney());
        }

        return result;
    }

    /**
     * 投资转让标下单过程：<br>
     * 1. 执行数据库进行投资前置条件判断<br>
     * 1.1 基本前置条件判断<br>
     * 1.2 投资转让标前置条件判断<br>
     * 2. 修改转让标的投资金额、状态<br>
     * 3. 添加业务活动记录<br>
     * 4. 添加投资活动记录<br>
     * 5. 添加订单记录<br>
     * 6. 添加资金支付交易记录<br>
     * 7. 记录用户投资状态<br>
     * 8. 执行数据库操作<br>
     */
    private PlainResult<Integer> investTransferLoan(Invest pojo) {
        PlainResult<Integer> result = new PlainResult<Integer>();
        PlainResult<User> userResult = userService.findEntityById(pojo.getUserId());
        if (!userResult.isSuccess() || userResult.getData() == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "用户类型查询失败");
        }
        //投标授权有效开始日
  		Date investStartDate=userResult.getData().getAuthorizeInvestStartDate();
  		//投标授权有效结束日
  		Date investEndDate=userResult.getData().getAuthorizeInvestEndDate();
  		//投标授权金额
  		BigDecimal authorizeInvestAmount=null != userResult.getData().getAuthorizeInvestAmount()?userResult.getData().getAuthorizeInvestAmount():BigDecimal.ZERO;
  		if(!"11".equals(userResult.getData().getAuthorizeInvestType())){
  			return result.setError(CommonResultCode.BIZ_ERROR, "您还未开启出借授权，请先去授权！");
  		}else if(!"有效".equals(AuthorizeUtil.isAuthorize(investStartDate,investEndDate))){
  			return result.setError(CommonResultCode.BIZ_ERROR, "出借授权"+AuthorizeUtil.isAuthorize(investStartDate,investEndDate)+",请去修改！");
  		}else if(pojo.getInvestMoney().doubleValue()>authorizeInvestAmount.doubleValue()){
  			return result.setError(CommonResultCode.BIZ_ERROR, "投资金额超过您的出借授权金额,请去修改！");
  		}
        
        // 1. 执行数据库进行投资前置条件判断: 是否已经满标，是否满足项目标的投标条件
        // 1.1 基本前置条件判断
        final TransferLoanDO transferLoanDO = transferLoanDao.findByTransferLoanIdWithLock(pojo.getBidId());

        if (pojo.getUserId().equals(transferLoanDO.getTlUserId())) {
            return result.setError(CommonResultCode.BIZ_ERROR, "投资人和转让人不能为同一个人");
        }
        
        Integer nextPaymentId = transferLoanDO.getTlNextPaymentId();
        if(nextPaymentId!=null){
        	PaymentPlanDO paymentPlan = paymentPlanDao.findById(nextPaymentId);
        	if(paymentPlan.getPpPayState()==2){//已还清
        		return result.setError(CommonResultCode.BIZ_ERROR, "原始标有还款行为，不允许投资！");
        	}
        }
        
        //原始标是否过期
        LoanDO loanDO1 = loanDao.findById(transferLoanDO.getTlOriginId());
        if(loanDO1.getLoanExpireDate().before(new Date())){
        	return result.setError(CommonResultCode.BIZ_ERROR, "原始项目已过期，不允许投资！");
        }
        	

        // 1.2 投资转让标前置条件判断
        Date nowDate = new Date();
        if (transferLoanDO.getTlInvestEndtime() != null && transferLoanDO.getTlInvestEndtime().before(nowDate)) {
            return result.setError(CommonResultCode.BIZ_ERROR, "投资结束时间已到，不可投资");
        } else if (transferLoanDO.getTlInvestStarttime() != null
                && transferLoanDO.getTlInvestStarttime().after(nowDate)) {
            return result.setError(CommonResultCode.BIZ_ERROR, "投资投资开始时间未到，不可投资");
        }

        // 检查转让标的投资人是不是原始借款人
        LoanDO loanDO = loanDao.findById(transferLoanDO.getTlOriginId());
        if (loanDO == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "原始的普通标不存在");
        } else if (loanDO.getLoanUserId().equals(pojo.getUserId())) {
            return result.setError(CommonResultCode.BIZ_ERROR, "原始的普通标借款人不能投资本转让标");
        }

        if (pojo.getUserId().equals(transferLoanDO.getTlUserId())) {
            return result.setError(CommonResultCode.BIZ_ERROR, "投资人和转让人不能为同一个人");
        } else if (TransferLoanState.TRANSFERING.getState() != transferLoanDO.getTlState()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "当前的转让标不可投资");
        }

        BigDecimal newCurrentInvest = transferLoanDO.getTlCurrentInvest().add(pojo.getInvestMoney());
        if (newCurrentInvest.compareTo(transferLoanDO.getTlTransferMoney()) > 0) {
            return result.setError(CommonResultCode.BIZ_ERROR, "投资金额不能超过当前转让标的实际可投金额");
        }

        PlainResult<Boolean> orderResult = queryOrderExistence(pojo.getBidId(), pojo.getBidType(), pojo.getUserId());
        if (!orderResult.isSuccess()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "查询订单表判断是否投资多次失败");
        }
        /*
         * else if (!Boolean.FALSE.equals(orderResult.getData())) { return
         * result.setError(CommonResultCode.BIZ_ERROR, "不允许重复投资"); }
         */
        // 2. 修改转让标的投资金额
        TransferLoan toModifyTransLoan = new TransferLoan();
        toModifyTransLoan.setId(pojo.getBidId());
        toModifyTransLoan.setCurrentInvest(newCurrentInvest);
        BaseResult transLoanModifyResult = transferLoanService.modifyTransferLoanInfo(toModifyTransLoan, null);
        if (!transLoanModifyResult.isSuccess()) {
            log.warn(transLoanModifyResult.getMessage());
            throw new BusinessException("转让标投资金额修改失败");
        }
        InnerSeqNo innerSeqNo = InnerSeqNo.getInstance();
        // 3. 添加业务活动记录
        ActivityRecord activity = new ActivityRecord();
        activity.setCreator(pojo.getUserId());
        activity.setActivityType(ActivityType.INVEST_TRANSFER_LOAN);
        // activity.setForeignId(foreignId);

        // 4. 添加投资活动记录
        Invest invest = new Invest();
        invest.setOriginId(transferLoanDO.getTlOriginId());
        invest.setUserId(pojo.getUserId());
        invest.setInvestMoney(pojo.getInvestMoney());
        //invest.setValidInvestMoney(validInvestMoney); // callback
        pojo.setInvestState(InvestState.EARNING);
        invest.setInvestState(InvestState.EARNING);
        invest.setBidType(pojo.getBidType());
        invest.setBidId(pojo.getBidId());
        invest.setInnerSeqNo(innerSeqNo.toString() + 0);

        // 5. 添加订单记录
        InvestOrder order = new InvestOrder();
        order.setOrderMoney(pojo.getInvestMoney());
        order.setOriginId(transferLoanDO.getTlOriginId());
        order.setOrderState(OrderState.PAID);
        order.setBidType(pojo.getBidType());
        order.setBidId(pojo.getBidId());
        order.setUserId(pojo.getUserId());
        order.setInnerSeqNo(innerSeqNo.toString() + 0);
        log.info("转让标投资记录流水号："+innerSeqNo.toString() + 0);
        // 6. 添加资金支付交易记录
        List<UserIdentity> userList = new ArrayList<UserIdentity>(2);

        // 投资人
        UserIdentity investor = new UserIdentity();
        investor.setUserId(invest.getUserId());
        investor.setUserType(queryUserTypeByUserId(investor.getUserId()));
        investor.setAccountCategory(AccountCategory.INVESTACCOUNT.getType());
        userList.add(investor);

        // 借款人（债权转让转让人）
        UserIdentity loanee = new UserIdentity();
        loanee.setUserId(transferLoanDO.getTlUserId());
        loanee.setUserType(queryUserTypeByUserId(loanee.getUserId()));
        loanee.setAccountCategory(AccountCategory.INVESTACCOUNT.getType());//债权转让和普通标不一样，转让人收款账户是投资户
        userList.add(loanee);

        ListResult<Account> accountResult = accountInfoService.queryByUserIds(userList);
        if (!accountResult.isSuccess() || accountResult.getData().size() != 2) {
            log.warn(accountResult.getMessage());
            throw new BusinessException("用户账户查询失败");
        }

        List<Account> userAccountList = accountResult.getData();

        String investorAccountNo;
        String loaneeAccountNo;
        String payUserNo;
        String receiveUserNo;
        if (userAccountList.get(0).getAccountUserId().equals(investor.getUserId())) {
            investorAccountNo = userAccountList.get(0).getAccountNo();
            loaneeAccountNo = userAccountList.get(1).getAccountNo();
            payUserNo = userAccountList.get(0).getAccountNo();
            receiveUserNo = userAccountList.get(1).getAccountNo();
        } else {
            investorAccountNo = userAccountList.get(1).getAccountNo();
            loaneeAccountNo = userAccountList.get(0).getAccountNo();

            payUserNo = userAccountList.get(1).getAccountNo();
            receiveUserNo = userAccountList.get(0).getAccountNo();
        }
        Map params = new HashMap();
        params.put("payUserNo", payUserNo);
        params.put("receiveUserNo", receiveUserNo);
        params.put("BatchNo", pojo.getBidId());

        Deal deal = new Deal();

        DealDetail dealDetail = new DealDetail();
        dealDetail.setData(params);
        dealDetail.setMoneyAmount(pojo.getInvestMoney());
        dealDetail.setPayAccountId(investorAccountNo);
        //dealDetail.setReceiveAccountId(loaneeAccountNo);
        dealDetail.setDealDetailType(DealDetailType.INVESTE_MONEY);
        deal.setInnerSeqNo(innerSeqNo);
        deal.setBusinessType(DealType.INVESTER);
        deal.setOperator(pojo.getUserId());
        deal.setDealDetail(Arrays.asList(dealDetail));
        deal.setBusinessId(transferLoanDO.getTlOriginId());
        PlainResult<FeeSetting> plainResult=feeSettingService.queryByFeeTypeLoanCategory(FeeType.TRANSFER_FEE, LoanConverter.toLoan(loanDO).getLoanCategory(), pojo.getInvestMoney());
        if(!plainResult.isSuccess()){
        	log.error("没有设置对应（最小金额"+pojo.getInvestMoney()+"元）的转让手续费率");
        	throw new BusinessException("没有设置对应（最小金额"+pojo.getInvestMoney()+"元）的转让手续费率");
        }
        //添加转让手续费记录
		double feeRate = plainResult.getData().getRate();
		// 平台账户
        PlainResult<SysConfig> platformAccountResult = sysConfigService.querySysConfig(SysConfigEntry.PLATFORM_ACCOUNT);
        if (!platformAccountResult.isSuccess()) {
            throw new BusinessException("平台账户查询失败");
        }
		
        
        
        PlainResult<DealReturn> dealResult = dealRecordService.createBusinessRecord(deal, investPaidCallback);
        if (!dealResult.isSuccess()) {
            log.warn(dealResult.getMessage());
            throw new BusinessException("交易创建失败");
        }

        // 设置订单和投资的内部交易流水号参数
        //  order.setInnerSeqNo(dealResult.getData().getDrInnerSeqNo());
        invest.setInnerSeqNo(dealResult.getData().getDrInnerSeqNo());

        // 7. 记录用户投资状态
        BaseResult userModResult = userService.modifyUserBusinessState(investor.getUserId(), investor.getUserType(),
                UserBusinessState.INVESTED);
        if (!userModResult.isSuccess()) {
            log.warn(userModResult.getMessage());
            throw new BusinessException("用户投资状态记录失败");
        }

        // 8. 执行数据库添加
        // 添加订单记录
        PlainResult<Integer> createOrderResult = investOrderService.createInvestOrder(order);
        if (!createOrderResult.isSuccess()) {
            log.warn(createOrderResult.getMessage());
            throw new BusinessException("订单创建失败");
        }

        // 添加投资活动记录
        InvestDO investDO = InvestConverter.toInvestDO(invest);
        investDao.insert(investDO);
        investDO = investDao.findByParam(investDO);
        pojo.setInnerSeqNo(invest.getInnerSeqNo());
        pojo.setId(investDO.getInId());
        invest.setId(investDO.getInId());
        // 添加业务活动记录
        activity.setForeignId(investDO.getInId());
        BaseResult activityResult = activityRecordService.createActivityRecord(activity);
        if (!activityResult.isSuccess()) {
            log.warn(activityResult.getMessage());
            throw new BusinessException("业务活动记录创建失败");
        }

        // 9. 发起资金支付
        /*BaseResult invokeResult = dealRecordService.invokePayment(deal.getInnerSeqNo().getUniqueNo());
        if (!invokeResult.isSuccess()) {
            log.warn(invokeResult.getMessage());
            throw new BusinessException("发起资金支付失败");
        }*/
        
		
		String status = "TRADE_FINISHED";
		
		BaseResult modifyResult = dealRecordService.modifyDealRecordState(
				innerSeqNo.toString(), status, pojo.getInvestMoney());
		if (!modifyResult.isSuccess()) {
			result.setSuccess(false);
		} else {
			DealRecordDO dealRecordDo = new DealRecordDO();
	        dealRecordDo.setDrDetailType(DealDetailType.DEBT_TRANSFER_FEE.getType());
	        dealRecordDo.setDrInnerSeqNo(innerSeqNo.toString());
	        dealRecordDo.setDrMoneyAmount(pojo.getInvestMoney().multiply(new BigDecimal(feeRate)).divide(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP));
	        dealRecordDo.setDrReceiveAccount(platformAccountResult.getData().getConfValue());
	        dealRecordDo.setDrPayAccount(loaneeAccountNo);
	        dealRecordDo.setDrType(DealType.TRANSFER.getType());
	        dealRecordDo.setDrState(DealState.SUCCESS.getState());
	        dealRecordDo.setDrOperateDate(DateUtil.addSeconds(new Date(), 5));
	        dealRecordDao.insertSelective(dealRecordDo);
	        //添加投资划转记录（因为债权转让投资完成即完成划转，没有冻结操作，所以之前伪造了一个冻结记录，仅为了对账方便）
	        DealRecordDO dealRecordDo2 = new DealRecordDO();
	        dealRecordDo2.setDrDetailType(DealDetailType.DEBT_TRANSFER_MONEY.getType());
	        dealRecordDo2.setDrInnerSeqNo(innerSeqNo.toString());
	        dealRecordDo2.setDrMoneyAmount(pojo.getInvestMoney());
	        dealRecordDo2.setDrReceiveAccount(loaneeAccountNo);
	        dealRecordDo2.setDrPayAccount(investorAccountNo);
	        dealRecordDo2.setDrType(DealType.TRANSFER.getType());
	        dealRecordDo2.setDrState(DealState.SUCCESS.getState());
	        dealRecordDo2.setDrOperateDate(DateUtil.addSeconds(new Date(), 5));
	        dealRecordDao.insertSelective(dealRecordDo2);
			
	        //构造转让人一个投资实例，相当于转让人和受让人共同投资该转让标
	        Invest oldInvest = new Invest();
	        oldInvest.setId(transferLoanDO.getTlInvestId());
	        oldInvest.setOriginId(transferLoanDO.getTlOriginId());
	        oldInvest.setUserId(transferLoanDO.getTlUserId());
	        oldInvest.setValidInvestMoney(transferLoanDO.getTlTransferMoney().subtract(newCurrentInvest));
	        oldInvest.setInvestMoney(transferLoanDO.getTlTransferMoney().subtract(newCurrentInvest));
	       // investDao.update(InvestConverter.toInvestDO(oldInvest));//更新转让人原始投资金额
	        List<Invest> investList = new ArrayList<Invest>();
	        invest.setValidInvestMoney(pojo.getInvestMoney());
	        investList.add(invest);
	        

	        
	        final int fullTransferRecordId = fullTransferRecordDao.findByBidId(loanDO.getLoanId()).getFtrId();
	        
	        ListResult<PaymentPlan> queryPaymentResult = paymentPlanService.queryPaymentPlanList(fullTransferRecordId);
	        if (!queryPaymentResult.isSuccess()) {
	            throw new BusinessException("还款计划查询失败");
	        }
	        
	        // 目前的债权人转让是将债权人的所有未收益的收益计划查出来，进行转让
	        IncomePlan queryParam = new IncomePlan();
	        queryParam.setInvestId(transferLoanDO.getTlInvestId());
	        queryParam.setIncomePlanState(IncomePlanState.GOING);
	        final ListResult<IncomePlan> transferIncomeResult = incomePlanService.queryIncomePlanList(queryParam);
	        if (!transferIncomeResult.isSuccess()) {
	            return result.setError(CommonResultCode.BIZ_ERROR, "债权人的收益计划查询失败");
	        } 
	        
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
	        
	        
	        //删除原先旧的收益计划表数据
			PlainResult<Integer> batchResult = incomePlanService.batchUpdateStateByInvestId(transferLoanDO.getTlInvestId(), IncomePlanState.GOING, IncomePlanState.DELETED);
			if (!batchResult.isSuccess()) {
	            throw new BusinessException("转让人旧收益计划表数据删除失败");
	        }
	        // 9. 重新生成债权人收益计划
	        PlanBuilder planBuilder = PlanBuilderFactory.createPlanBuilder(LoanPeriodUnit.DAY);
	        //如果满标了走buildTransferIncomePlanList方法
	        if(transferLoanDO.getTlTransferMoney().compareTo(newCurrentInvest)!=0){
	        	investList.add(oldInvest);	        
	        }
	        	
        	List<IncomePlan> incomePlanList = planBuilder.buildTransferIncomePlanListForTrans(
                    TransferLoanConverter.toTransferLoan(transferLoanDO), transferIncomeResult.getData(), investList,
                    fullTransferRecordId, prePayTime);
        	PlainResult<Integer> createIncomeResult = incomePlanService.batchCreateIncomePlanList(incomePlanList);
        	if (!createIncomeResult.isSuccess()) {
                throw new BusinessException("收益计划创建失败");
            }
        	log.info("投资转让标重新生成收益计划表成功！投资记录id为："+pojo.getId()+"；原始标的投资记录id为："+transferLoanDO.getTlInvestId());
	        
			
			
			result.setSuccess(true);
		}
		
		/********************************************调用第三方接口转让债权***********************************************/
		//获取原始投资外部流水号
		BigDecimal transfee = pojo.getInvestMoney().multiply(new BigDecimal(feeRate)).divide(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);//这里必须设置精度，否则转换分时不四舍五入，与本地交易记录不一致
        InvestDO orgInvest = investDao.findById(transferLoanDO.getTlInvestId());
		Map<String,String> params1 = new LinkedHashMap<String,String>();
		params1.put("TransAmt", FormatHelper.changeY2F(pojo.getInvestMoney()));
		params1.put("OldTransId", orgInvest.getIoOutSeqNo());
		params1.put("MerFeeAmt", FormatHelper.changeY2F(transfee));
		params1.put("FeeType", "1");
		params1.put("OutAmt", FormatHelper.changeY2F(pojo.getInvestMoney()));
		params1.put("InAmt", FormatHelper.changeY2F(pojo.getInvestMoney().subtract(transfee)));
		//受让人账户

    	PlainResult<Account> result1 = accountInfoService.queryByUserId(investor);
    	Account acc1= result1.getData();
    	 
    	//转让人账户
	    PlainResult<Account> result2 = accountInfoService.queryByUserId(loanee);
	    Account acc2 = result2.getData();
	    params1.put("OutCustId", acc1.getAccountNo());
		params1.put("InCustId", acc2.getAccountNo());
		params1.put("SmsCode", StringUtils.isEmpty(pojo.getVerification())?String.valueOf(session.getAttribute("bhSmsVerification")):pojo.getVerification());
		params1.put("BorrowId", String.valueOf(transferLoanDO.getTlOriginId()));
		params1.put("MerPriv", String.valueOf(invest.getUserId()));
		Map<String, String> resultMap  = payMentService.zQTransfer(params1);
		if (!"000000".equals(resultMap.get("RespCode"))) {
			log.info("调用第三方债权转让接口失败："+resultMap.toString());
            throw new BusinessException(String.valueOf(resultMap.get("RespDesc")).replace("[UAP]", ""));
        }
		log.info("调用第三方债权转让接口成功");
		  // 3. 满标后直接更新标状态为已划转
        
	        TransferLoanDO toModify = new TransferLoanDO();
	        toModify.setTlId(transferLoanDO.getTlId());
	        if(transferLoanDO.getTlTransferMoney().compareTo(newCurrentInvest)==0){
	        	toModify.setTlState(TransferLoanState.MONEY_TRANSFERED.getState());
	        }
	        toModify.setTlTransferFee(newCurrentInvest.multiply(new BigDecimal(feeRate)).divide(new BigDecimal("100")));
	        transferLoanDao.update(toModify);
        
        sendMailService.sendMailToCreditoUser(transferLoanDO.getTlId(), transferLoanDO.getTlLoanNo());
        //发送邮件和推送给原始标借款人
        PlainResult<UserDO> userDO = userService.findById(transferLoanDO.getTlUserId());
    	PlainResult<CompanyCustomerDO> enterprise = companyCustomerService.findByUserId(transferLoanDO.getTlUserId());
    	String loanUserName;
    	if (UserType.ENTERPRISE.getType() == userDO.getData().getUserType()){
    		loanUserName = enterprise.getData().getCcCompanyName();
    	}else{
    		loanUserName = userDO.getData().getUserRealName();
        }
    	
    	PlainResult<UserDO> investUserDO = userService.findById(pojo.getUserId());

		LoanDO orgLoanDo = loanDao.findByLoanId(transferLoanDO.getTlOriginId());
        PlainResult<UserDO> orgUserDo = userService.findById(orgLoanDo.getLoanUserId());
    	PlainResult<CompanyCustomerDO> orgEnterprise = companyCustomerService.findByUserId(orgLoanDo.getLoanUserId());
    	String orgLoanUserEmail;
    	if (UserType.ENTERPRISE.getType() == orgUserDo.getData().getUserType()){
    		orgLoanUserEmail = orgEnterprise.getData().getCcContactEmail();
    	}else{
    		orgLoanUserEmail = orgUserDo.getData().getUserEmail();
        }
		// 设置给原始标借款人发送邮件
		String content = "尊敬的客户：</br>您好，根据《合同法》和相关法律的规定以及我方与"+investUserDO.getData().getUserRealName()+"在"+DateUtil.formatDate(new Date(),"yyyy年MM月dd日")+"签订的《债权转让协议书》，"
				  +"</br>您（或贵司）所欠我方债务本金"+pojo.getInvestMoney()+"元及相应的利息、罚息，于"+DateUtil.formatDate(new Date(),"yyyy年MM月dd日")+"起转让给"+investUserDO.getData().getUserRealName()+"，</br>与此转让债权相关的其他一切权利一并转让。请您（或贵司）自接到该债权转让通知书后应向"+investUserDO.getData().getUserRealName()+"履行全部义务。</br>特此通知。                       </br>转让人："+loanUserName+"      </br>"+DateUtil.formatDate(new Date(),"yyyy年MM月dd日");

		sendMailService.sendMailToOrgLoanUser(content, orgLoanUserEmail);// 发送html格式
		log.debug("原始标的借款人email:"+ orgLoanUserEmail);
		log.debug("发送给原始标的借款人邮件内容:"+ content);
		log.debug("发送给原始标的借款人站内信内容:"+ content.replace("</br>", ""));
		SysMessageInfoDO messageDO = new SysMessageInfoDO();
		messageDO.setSysUserId(135);
		messageDO.setSysToUser(orgUserDo.getData().getUserId());
		messageDO.setSysMessageContent(content.replace("</br>", ""));
		messageDO.setSysMessageTitle("债权转让通知书");
		messageDO.setSysUserName("kefu");
		this.messageInfoService.createMessage(messageDO);
		
		/**消息推送给app**/
    	/*try{
    		JpushUtils.sendPush_alias(investUserDO.getData().getUserName(),content.replace("</br>", ""));
    	}catch(Exception e){
    		log.error(e.getMessage());
    	}*/
		
        result.setData(investDO.getInId());
        return result;
    }

    /**
     * 投资收购标下单过程：<br>
     * 1. 执行数据库进行投资前置条件判断<br>
     * 1.1 基本前置条件判断<br>
     * 1.2 投资收购标前置条件判断<br>
     * 2. 添加业务活动记录<br>
     * 3. 添加投资活动记录<br>
     * 4. 执行数据库操作<br>
     * 5. 修改认购人状态<br>
     * 6. 修改收购标的投资金额、状态，如果满标则发起审核<br>
     */
    private PlainResult<Integer> investBuyLoan(Invest pojo) {
        PlainResult<Integer> result = new PlainResult<Integer>();

        // 1. 执行数据库进行投资前置条件判断: 是否已经满标，是否满足项目标的投标条件
        // 1.1 基本前置条件判断
        final BuyLoanDO buyLoanDO = buyLoanDao.findByBuyLoanIdWithLock(pojo.getBidId());

        if (buyLoanDO == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "要认购的收购标不存在");
        } else if (pojo.getUserId().equals(buyLoanDO.getBlUserId())) {
            return result.setError(CommonResultCode.BIZ_ERROR, "认购人和收购人不能为同一个人");
        }

        // 1.2 投资收购标前置条件判断
        Date nowDate = new Date();
        if (buyLoanDO.getBlEndTime() != null && buyLoanDO.getBlEndTime().before(nowDate)) {
            return result.setError(CommonResultCode.BIZ_ERROR, "认购结束时间已到，不可投资");
        } else if (buyLoanDO.getBlStartTime() != null && buyLoanDO.getBlStartTime().after(nowDate)) {
            return result.setError(CommonResultCode.BIZ_ERROR, "认购开始时间未到，不可投资");
        }

        if (pojo.getUserId().equals(buyLoanDO.getBlUserId())) {
            return result.setError(CommonResultCode.BIZ_ERROR, "投资人和收购人不能为同一个人");
        } else if (BuyLoanState.BUYING.getState() != buyLoanDO.getBlState()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "当前的收购标不可投资");
        }

        BuyLoanSubscribeDO buySubscribeDO = buyLoanSubscribeDao.findOneWithLock(buyLoanDO.getBlId(),
                buyLoanDO.getBlOriginId(), pojo.getUserId(), BuyLoanSubscribeState.WAITING.getState());
        if (buySubscribeDO == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "不具备认购资格认购失败");
        }

        BuyLoanSubscribeDO alreadySubscribe = buyLoanSubscribeDao.findOneByParam(
                pojo.getUserId(),
                buyLoanDO.getBlOriginId(),
                Arrays.asList(BuyLoanSubscribeState.SUBSCRIBING.getState(),
                        BuyLoanSubscribeState.SUBSCRIBE_PASS.getState()));
        if (alreadySubscribe != null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "已经认购了同一原始借款的收购，不可以再认购");
        }

        PlainResult<BigDecimal> incomeCountResult = incomePlanService.sumCapitalByUserIdAndLoanId(pojo.getUserId(),
                buyLoanDO.getBlOriginId(), IncomePlanState.GOING);
        if (!incomeCountResult.isSuccess()) {
            log.warn(incomeCountResult.getMessage());
            return result.setError(CommonResultCode.BIZ_ERROR, "收益计划查询失败");
        }

        // 转让债券，目前是全额认购
        BigDecimal transferMoney = incomeCountResult.getData();

        BigDecimal newCurrentInvest = buyLoanDO.getBlCurrentInvest().add(transferMoney);
        if (newCurrentInvest.compareTo(buyLoanDO.getBlBuyTotal()) > 0) {
            return result.setError(CommonResultCode.BIZ_ERROR, "投资金额不能超过收购标的实际可投金额");
        }

        // 2. 添加业务活动记录
        ActivityRecord activity = new ActivityRecord();
        activity.setCreator(pojo.getUserId());
        activity.setActivityType(ActivityType.INVEST_BUY_LOAN);
        // activity.setForeignId(foreignId);

        // 3. 添加投资活动记录
        Invest invest = new Invest();
        invest.setOriginId(buyLoanDO.getBlOriginId());
        invest.setUserId(pojo.getUserId());
        invest.setInvestMoney(transferMoney);
        invest.setValidInvestMoney(transferMoney);
        // pojo.setInvestState(InvestState.PAID);
        invest.setInvestState(InvestState.PAID);
        invest.setBidType(pojo.getBidType());
        invest.setBidId(pojo.getBidId());
        //invest.setInnerSeqNo(innerSeqNo);

        // 4. 执行数据库添加
        // 添加投资活动记录
        InvestDO investDO = InvestConverter.toInvestDO(invest);
        investDao.insert(investDO);

        // 添加业务活动记录
        activity.setForeignId(investDO.getInId());
        BaseResult activityResult = activityRecordService.createActivityRecord(activity);
        if (!activityResult.isSuccess()) {
            log.warn(activityResult.getMessage());
            throw new BusinessException("业务活动记录创建失败");
        }

        // 5. 修改认购人状态
        // 收益金额 = (收购总金额/收购总债券－1)*转让债券
        BigDecimal buyMoney = buyLoanDO.getBlBuyMoney();
        BigDecimal buyTotal = buyLoanDO.getBlBuyTotal();
        BigDecimal earnMoney = Arith.div(buyMoney, buyTotal).subtract(BigDecimal.ONE).multiply(transferMoney);

        BuyLoanSubscribe modSubscribe = new BuyLoanSubscribe();
        modSubscribe.setBuyId(pojo.getBidId());
        modSubscribe.setUserId(pojo.getUserId());
        modSubscribe.setState(BuyLoanSubscribeState.SUBSCRIBING);
        modSubscribe.setTransferTime(new Date());
        modSubscribe.setTransferMoney(transferMoney);
        modSubscribe.setEarnMoney(earnMoney);
        BaseResult subscribeResult = buyLoanSubscribeService.modifyByBuyLoanIdAndUserId(modSubscribe);
        if (!subscribeResult.isSuccess()) {
            throw new BusinessException("认购人状态修改失败");
        }

        // 6. 修改收购标的投资金额，如果满标则发起审核
        BuyLoan toModify = new BuyLoan();
        BuyLoanTraceRecord traceRecord = null;

        toModify.setId(pojo.getBidId());
        toModify.setCurrentInvest(newCurrentInvest);
        toModify.setCurrentValidInvest(newCurrentInvest);
        if (toModify.getCurrentValidInvest().equals(buyLoanDO.getBlBuyTotal())) {
            toModify.setBuyLoanState(BuyLoanState.FULL_WAIT_REVIEW);
            toModify.setFullTime(new Date());

            // 发起审核流程
            Review review = new Review();
            review.setApplyId(buyLoanDO.getBlId());
            review.setType(ReviewType.PURCHASE_FULL_BID_REVIEW);
            review.setCurrRole(BaseRoleType.PLATFORM_SERVICE);
            BaseResult reviewRes = reviewService.initiateReview(review);
            if (!reviewRes.isSuccess()) {
                log.error("发起收购项目满标审核失败！BuyLoanId={}", buyLoanDO.getBlId());
                throw new BusinessException("发起收购项目满标审核失败");
            }

            // 项目跟踪状态记录
            traceRecord = new BuyLoanTraceRecord();
            traceRecord.setCreator(0);
            traceRecord.setBuyLoanId(buyLoanDO.getBlOriginId());
            traceRecord.setBuyLoanTraceOperation(BuyLoanTraceOperation.buying);
            traceRecord.setOldBuyLoanState(BuyLoanState.FULL_WAIT_REVIEW);
            traceRecord.setNewBuyLoanState(BuyLoanState.BUYING);
            traceRecord.setNote("收购标项目满标回到认购中，buyLoanId=" + buyLoanDO.getBlId());
        }

        BaseResult buyLoanModifyResult = buyLoanService.modifyBuyLoanInfo(toModify, traceRecord);
        if (!buyLoanModifyResult.isSuccess()) {
            log.warn(buyLoanModifyResult.getMessage());
            throw new BusinessException("收购标投资金额修改失败");
        }

        result.setData(investDO.getInId());
        return result;
    }

    /**
     * 多次投资的校验 查询订单表是否存在对同一个类型的同一个标有未支付的或支付成功的订单
     */
    private PlainResult<Boolean> queryOrderExistence(int bidId, BidType bidType, int userId) {
        PlainResult<Boolean> orderResult = investOrderService.queryExistence(bidId, bidType, userId,
                Arrays.asList(OrderState.UNPAID, OrderState.PAID));
        return orderResult;
    }

    /**
     * 撤销投资过程：<br>
     * 1. 撤投条件是否满足判断<br>
     * 1.1 检查参数<br>
     * 1.2 当前标允许撤投的状态<br>
     * 2. 更新标的投资金额<br>
     * 3. 执行撤资交易<br>
     * <br>
     * 撤资交易时回调：<br>
     * 1. 撤资成功<br>
     * 1.1 更新投资的状态<br>
     * 1.2 判断更新标的有效投资金额、如果以前标状态为满标则修改为招标中<br>
     * 2. 撤资失败<br>
     * 2.1 回滚标的投资金额<br>
     * 注意考虑：撤资、投资同时进行 <br>
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult withdrawInvest(int investId, int userId) {
        BaseResult result = new BaseResult();

        // 1. 撤投条件是否满足判断
        // 1.1 检查参数：investId 与userId是否对应
        InvestDO investDO = investDao.findById(investId);
        if (investDO == null || investDO.getInUserId() != userId) {
            return result.setError(CommonResultCode.BIZ_ERROR, "用户的投资不存在");
        }

        if (investDO.getInBidType().equals(BidType.COMMON_LOAN.getType())) {
            return withdrawCommonLoanInvest(investDO);
        } else if (investDO.getInBidType().equals(BidType.TRANSFER_LOAN.getType())) {
            return withdrawTransferLoanInvest(investDO);
        } else if (investDO.getInBidType().equals(BidType.BUY_LOAN.getType())) {
            return withdrawBuyLoanInvest(investDO);
        } else {
            return result.setError(CommonResultCode.BIZ_ERROR, "未知的标类型");
        }
    }

    /**
     * 撤销投资过程：<br>
     * 1. 撤投条件是否满足判断<br>
     * 1.1 检查参数<br>
     * 1.2 当前标允许撤投的状态<br>
     * 2. 更新标的投资金额<br>
     * 3. 执行撤资交易<br>
     */
    private BaseResult withdrawCommonLoanInvest(InvestDO investDO) {
        BaseResult result = new BaseResult();

        final LoanDO loanDO = loanDao.findByLoanIdWithLock(investDO.getInBidId());

        // 1.2 当普通标允许撤投的状态为：招标中、满标待审、满标审核通过
        if (loanDO.getLoanState() != LoanState.BID_INVITING.getState()
                && loanDO.getLoanState() != LoanState.FULL_WAIT_REVIEW.getState()
                && loanDO.getLoanState() != LoanState.FULL_REVIEW_PASS.getState()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "当前标的状态不允许撤投");
        }

        // 2. 更新标的投资金额
        Loan toModify = new Loan();
        toModify.setLoanId(loanDO.getLoanId());
        toModify.setLoanCurrentInvest(loanDO.getLoanCurrentInvest().subtract(investDO.getInValidInvestMoney()));

        BaseResult modifyResult = loanService.modifyLoanInfo(toModify, null);
        if (!modifyResult.isSuccess()) {
            log.warn(modifyResult.getMessage());
            throw new BusinessException("撤销投资时普通标投资金额修改失败");
        }

        // 3. 执行撤资交易
        // 借款人
        UserIdentity loanee = new UserIdentity();
        loanee.setUserId(loanDO.getLoanUserId());
        loanee.setUserType(queryUserTypeByUserId(loanee.getUserId()));
        loanee.setAccountCategory(AccountCategory.LOANACCOUNT.getType());
        // 投资人
        UserIdentity investor = new UserIdentity();
        investor.setUserId(investDO.getInUserId());
        investor.setUserType(queryUserTypeByUserId(investor.getUserId()));
        investor.setAccountCategory(AccountCategory.INVESTACCOUNT.getType());
        invokeWithdraw(loanee, investor, investDO.getInValidInvestMoney(), loanDO.getLoanId());

        return result;
    }

    /**
     * 撤销投资过程：<br>
     * 1. 撤投条件是否满足判断<br>
     * 1.1 检查参数<br>
     * 1.2 当前标允许撤投的状态<br>
     * 2. 更新标的投资金额<br>
     * 3. 执行撤资交易<br>
     */
    private BaseResult withdrawTransferLoanInvest(InvestDO investDO) {
        BaseResult result = new BaseResult();

        final TransferLoanDO transferLoanDO = transferLoanDao.findByTransferLoanIdWithLock(investDO.getInBidId());

        // 1.2 当转让标允许撤投的状态为：转让中、满标待审、满标审核通过
        if (transferLoanDO.getTlState() != TransferLoanState.TRANSFERING.getState()
                && transferLoanDO.getTlState() != TransferLoanState.FULL_WAIT_REVIEW.getState()
                && transferLoanDO.getTlState() != TransferLoanState.FULL_REVIEW_PASS.getState()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "当前标的状态不允许撤投");
        }

        // 2. 更新标的投资金额
        TransferLoan toModify = new TransferLoan();
        toModify.setId(transferLoanDO.getTlId());
        toModify.setCurrentInvest(transferLoanDO.getTlCurrentInvest().subtract(investDO.getInValidInvestMoney()));

        BaseResult modifyResult = transferLoanService.modifyTransferLoanInfo(toModify, null);
        if (!modifyResult.isSuccess()) {
            log.warn(modifyResult.getMessage());
            throw new BusinessException("撤销投资时转让标投资金额修改失败");
        }

        // 3. 执行撤资交易
        // 借款人
        UserIdentity loanee = new UserIdentity();
        loanee.setUserId(transferLoanDO.getTlUserId());
        loanee.setUserType(queryUserTypeByUserId(loanee.getUserId()));
        loanee.setAccountCategory(AccountCategory.LOANACCOUNT.getType());
        // 投资人
        UserIdentity investor = new UserIdentity();
        investor.setUserId(investDO.getInUserId());
        investor.setUserType(queryUserTypeByUserId(investor.getUserId()));
        investor.setAccountCategory(AccountCategory.INVESTACCOUNT.getType());

        invokeWithdraw(loanee, investor, investDO.getInValidInvestMoney(), transferLoanDO.getTlOriginId());

        return result;
    }

    /**
     * 撤销投资过程：<br>
     * 1. 撤投条件是否满足判断<br>
     * 1.1 检查参数<br>
     * 1.2 当前标允许撤投的状态<br>
     * 2. 更新标的投资金额<br>
     * 3. 执行数据库修改<br>
     */
    private BaseResult withdrawBuyLoanInvest(InvestDO investDO) {
        BaseResult result = new BaseResult();

        BuyLoanDO buyLoanDO = buyLoanDao.findByBuyLoanIdWithLock(investDO.getInBidId());

        // 1.2 当收购标允许撤投的状态为：收购中、满标待审、满标审核通过
        if (buyLoanDO.getBlState() != BuyLoanState.BUYING.getState()
                && buyLoanDO.getBlState() != BuyLoanState.FULL_WAIT_REVIEW.getState()
                && buyLoanDO.getBlState() != BuyLoanState.FULL_REVIEW_PASS.getState()) {
            return result.setError(CommonResultCode.BIZ_ERROR, "当前标的状态不允许撤投");
        }

        // 2 更新标的投资金额
        BuyLoan toModify = new BuyLoan();
        BuyLoanTraceRecord traceRecord = null;

        toModify.setId(buyLoanDO.getBlId());
        BigDecimal newCurrentInvest = buyLoanDO.getBlCurrentInvest().subtract(investDO.getInValidInvestMoney());
        toModify.setCurrentInvest(newCurrentInvest);
        toModify.setCurrentValidInvest(newCurrentInvest);

        if (buyLoanDO.getBlState().equals(BuyLoanState.FULL_WAIT_REVIEW.getState())) {
            toModify.setBuyLoanState(BuyLoanState.BUYING);

            // 项目跟踪状态记录
            traceRecord = new BuyLoanTraceRecord();
            traceRecord.setCreator(0);
            traceRecord.setBuyLoanId(buyLoanDO.getBlOriginId());
            traceRecord.setBuyLoanTraceOperation(BuyLoanTraceOperation.buying);
            traceRecord.setOldBuyLoanState(BuyLoanState.FULL_WAIT_REVIEW);
            traceRecord.setNewBuyLoanState(BuyLoanState.BUYING);
            traceRecord.setNote("收购标项目满标回到认购中，buyLoanId=" + buyLoanDO.getBlId());
        }

        BaseResult modifyResult = buyLoanService.modifyBuyLoanInfo(toModify, traceRecord);
        if (!modifyResult.isSuccess()) {
            log.warn(modifyResult.getMessage());
            throw new BusinessException("撤销投资时收购标投资金额修改失败");
        }

        // 3 执行数据库修改
        // 修改认购人状态
        BaseResult subscribeResult = buyLoanSubscribeService.modifySubscribeState(investDO.getInBidId(),
                investDO.getInUserId(), BuyLoanSubscribeState.SUBSCRIBE_PASS, BuyLoanSubscribeState.WAITING);
        if (!subscribeResult.isSuccess()) {
            throw new BusinessException("认购人状态修改失败");
        }

        // 修改投资状态
        int count = investDao.updateInvestState(investDO.getInId(), InvestState.PAID.getState(),
                InvestState.WITHDRAWED.getState());
        if (count <= 0) {
            throw new BusinessException("投资状态修改失败");
        }

        return result;
    }

    /**
     * 撤资交易时回调：<br>
     * 1. 撤资成功<br>
     * 1.1 更新投资的状态<br>
     * 1.2 判断更新标的有效投资金额、如果以前标状态为满标则修改为招标中<br>
     * 2. 撤资失败<br>
     * 2.1 回滚标的投资金额<br>
     */
    public void invokeWithdraw(UserIdentity loanee, UserIdentity investor, BigDecimal amount, int businessId) {
        ListResult<Account> accountResult = accountInfoService.queryByUserIds(Arrays.asList(loanee, investor));
        if (!accountResult.isSuccess()) {
            log.warn(accountResult.getMessage());
            throw new BusinessException("用户账户查询失败");
        }

        List<Account> userAccountList = accountResult.getData();

        String loaneeAccountNo;
        String investorAccountNo;
        if (userAccountList.get(0).getAccountUserId().equals(loanee.getUserId())) {
            loaneeAccountNo = userAccountList.get(0).getAccountNo();
            investorAccountNo = userAccountList.get(1).getAccountNo();
        } else {
            loaneeAccountNo = userAccountList.get(1).getAccountNo();
            investorAccountNo = userAccountList.get(0).getAccountNo();
        }

        Deal deal = new Deal();

        /**
         * 撤投为解冻投资人投资金额操作
         */
        DealDetail dealDetail = new DealDetail();
        dealDetail.setMoneyAmount(amount);
        dealDetail.setPayAccountId(investorAccountNo);
        dealDetail.setReceiveAccountId(loaneeAccountNo);

        deal.setInnerSeqNo(InnerSeqNo.getInstance());
        deal.setBusinessType(DealType.WITHDRAWAL_INVESTER);
        deal.setOperator(investor.getUserId());
        deal.setDealDetail(Arrays.asList(dealDetail));
        deal.setBusinessId(businessId);

        // 添加资金撤投交易记录
        PlainResult<DealReturn> dealResult = dealRecordService.createBusinessRecord(deal, investWithdrawedCallback);
        if (!dealResult.isSuccess()) {
            log.warn(dealResult.getMessage());
            throw new BusinessException("交易创建失败");
        }

        // 执行撤投交易
        BaseResult invokeResult = dealRecordService.invokePayment(deal.getInnerSeqNo().getUniqueNo());
        if (!invokeResult.isSuccess()) {
            log.warn(dealResult.getMessage());
            throw new BusinessException("执行退款交易失败");
        }

    }

    @Override
    public BaseResult modifyInvestState(int bidId, InvestState oldState, InvestState newState) {
        BaseResult result = new BaseResult();

        if (bidId <= 0 || oldState == null || newState == null) {
            result.setError(CommonResultCode.ILLEGAL_PARAM);
            return result;
        }

        int count = investDao.updateInvestState(bidId, oldState.getState(), newState.getState());
        if (count <= 0) {
            return result.setError(CommonResultCode.BIZ_ERROR, "修改投资状态失败");
        }
        return result;
    }

    @Override
    public BaseResult batchModifyInvestState(int bidId, BidType bidType, InvestState oldState, InvestState newState) {
        BaseResult result = new BaseResult();
        int count = investDao
                .batchUpdateInvestState(bidId, bidType.getType(), oldState.getState(), newState.getState());
        if (count <= 0) {
            return result.setError(CommonResultCode.BIZ_ERROR, "批量修改投资状态失败");
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

    @Override
    public ListResult<TenderOverview> findMyTenderOverview(Integer userId) {
        ListResult<TenderOverview> list = new ListResult<TenderOverview>();
        List<TenderOverview> tenderOverviewList = investDao.findMyTenderOverview(userId);
        list.setData(tenderOverviewList);
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public /*synchronized*/ PlainResult<Integer> createInvest(Invest pojo, List<Integer> redsendIdList) {
        PlainResult<Integer> result = new PlainResult<Integer>();

        if (pojo == null) {
            result.setErrorMessage(CommonResultCode.ILLEGAL_PARAM_BLANK);
            return result;
        }
//        ListResult<RedsendDO> list = redsendService.queryByIdList(redsendIdList);
//        BigDecimal money = BigDecimal.ZERO;
//        for (RedsendDO redsendDO : list.getData()) {
//            money = money.add(BigDecimal.valueOf(redsendDO.getRsValidAmount()));
//        }
//        // 默认不使用红包的情况下，应付金额等于实际投资金额
//        pojo.setInvestPayMoney(pojo.getInvestMoney().subtract(money));
        
        pojo.setInvestPayMoney(pojo.getInvestMoney());//冻结投资人投资金额，然后再把红包给他
        try {
//        	semaphore.acquire();
        	while (atomicInteger.get()==1){//防止并发投资
				log.info(Thread.currentThread().getName()+"线程 please wait...");
				synchronized(this){
					this.wait();// 阻塞线程
				}
			}
        	atomicInteger.getAndIncrement();
			switch (pojo.getBidType()) {
			    case COMMON_LOAN:
			        return investCommonLoan(pojo, redsendIdList);
			    case TRANSFER_LOAN:
			        return investTransferLoan(pojo);
			    case BUY_LOAN:
			        return investBuyLoan(pojo);
			    default:
			        throw new BusinessException("未知的标类型");
			}
		} catch (Exception e) {
			log.error("投资出现异常：{}",e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally{
//			semaphore.release();
			atomicInteger.getAndDecrement();
			synchronized(this){
				this.notify();//通知其他线程
			}
		}
			
    }

    @Override
    public PlainResult<CollectedAndStill> findMyCollectedAndStill(Integer userId) {
        PlainResult<CollectedAndStill> planResult = new PlainResult<CollectedAndStill>();
        CollectedAndStill collectedAndStill = investDao.countCollectedAndStillNow(userId);
        planResult.setData(collectedAndStill);
        return planResult;
    }

    @Override
    public ListResult<Invest> findListByParam(int loanId) {
        ListResult<Invest> result = new ListResult<Invest>();
        InvestDO investDo = new InvestDO();
        investDo.setInOriginId(loanId);
        investDo.setInInvestState(InvestState.PAID.getState());
        List<InvestDO> investDoList = investDao.findListParam(investDo);
        List<Invest> investList = new ArrayList<Invest>();
        for (int i = 0; i < investDoList.size(); i++) {
            Invest invest = InvestConverter.toInvest(investDoList.get(i));
            investList.add(invest);
        }
        result.setData(investList);
        return result;
    }

    @Override
    public PlainResult<Map<String, BigDecimal>> findTotalIncomeAndPayMoneyByUserId(Integer userId) {
        PlainResult<Map<String, BigDecimal>> result = new PlainResult<Map<String, BigDecimal>>();

        Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
        BigDecimal incomeMoney = incomePlanDao.queryTotalIncomeMoneyByUserId(userId);
        BigDecimal incomeCapitalMoney = incomePlanDao.queryTotalIncomeCapitalMoneyByUserId(userId);
        BigDecimal payMoney = paymentPlanDao.queryTotalPayMoneyByUserId(userId);
        
        map.put("incomeMoney", incomeMoney);
        map.put("incomeCapitalMoney", incomeCapitalMoney);
        map.put("payMoney", payMoney);

        result.setData(map);
        return result;
    }

	@Override
	public ListResult<Invest> findListByParamEarning(int loanId) {
		ListResult<Invest> result = new ListResult<Invest>();
        InvestDO investDo = new InvestDO();
        investDo.setInOriginId(loanId);
        investDo.setInInvestState(InvestState.EARNING.getState());
        List<InvestDO> investDoList = investDao.findListParam(investDo);
        List<Invest> investList = new ArrayList<Invest>();
        for (int i = 0; i < investDoList.size(); i++) {
            Invest invest = InvestConverter.toInvest(investDoList.get(i));
            investList.add(invest);
        }
        result.setData(investList);
        return result;
	}
	
	@Override
	public int investNumOfOneLoan(Invest pojo)
	{
		InvestDO investDO = InvestConverter.toInvestDO(pojo);
		return investDao.investNumOfOneLoan(investDO);
	}

	@Override
	public BigDecimal tzze(Integer userId) {
		return investDao.tzze(userId);
	}

	@Override
	public BigDecimal mrzq(Integer userId) {
		return investDao.mrzq(userId);
	}
	
	@Override
	public BigDecimal zrzq(Integer userId){
		return investDao.zrzq(userId);
	}

	@Override
	public BigDecimal dstzze(Integer userId) {
		return investDao.dstzze(userId);
	}

	@Override
	public int batchUpdateInvestState(Integer bidId, int bidType, int oldState,
			int newState) {
		return investDao.batchUpdateInvestState(bidId,bidType,oldState,newState);
	}
}
