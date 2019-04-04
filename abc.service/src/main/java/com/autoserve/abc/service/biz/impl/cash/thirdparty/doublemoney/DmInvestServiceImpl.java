package com.autoserve.abc.service.biz.impl.cash.thirdparty.doublemoney;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.dataobject.BuyLoanDO;
import com.autoserve.abc.dao.dataobject.BuyLoanSubscribeDO;
import com.autoserve.abc.dao.dataobject.InvestDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.PaymentPlanDO;
import com.autoserve.abc.dao.dataobject.RedUseDO;
import com.autoserve.abc.dao.dataobject.RedsendDO;
import com.autoserve.abc.dao.dataobject.TransferLoanDO;
import com.autoserve.abc.dao.dataobject.stage.statistics.CollectedAndStill;
import com.autoserve.abc.dao.dataobject.stage.statistics.TenderOverview;
import com.autoserve.abc.dao.intf.BuyLoanDao;
import com.autoserve.abc.dao.intf.BuyLoanSubscribeDao;
import com.autoserve.abc.dao.intf.IncomePlanDao;
import com.autoserve.abc.dao.intf.InvestDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.PaymentPlanDao;
import com.autoserve.abc.dao.intf.RedUseDao;
import com.autoserve.abc.dao.intf.RedsendDao;
import com.autoserve.abc.dao.intf.TransferLoanDao;
import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.convert.InvestConverter;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.ActivityRecord;
import com.autoserve.abc.service.biz.entity.BuyLoan;
import com.autoserve.abc.service.biz.entity.BuyLoanSubscribe;
import com.autoserve.abc.service.biz.entity.BuyLoanTraceRecord;
import com.autoserve.abc.service.biz.entity.Deal;
import com.autoserve.abc.service.biz.entity.DealDetail;
import com.autoserve.abc.service.biz.entity.DealNotify;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.InvestOrder;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.ActivityType;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.BuyLoanState;
import com.autoserve.abc.service.biz.enums.BuyLoanSubscribeState;
import com.autoserve.abc.service.biz.enums.BuyLoanTraceOperation;
import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.IncomePlanState;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.OrderState;
import com.autoserve.abc.service.biz.enums.RedState;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.enums.RsState;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.service.biz.enums.UserBusinessState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.invest.ActivityRecordService;
import com.autoserve.abc.service.biz.intf.invest.InvestOrderService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.loan.BuyLoanService;
import com.autoserve.abc.service.biz.intf.loan.BuyLoanSubscribeService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.intf.redenvelope.RedsendService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.util.Arith;
import com.autoserve.abc.service.util.DateUtil;

/**
 * 投资服务
 * 
 * @author segen189 2014年11月19日 下午2:26:50
 */
@Service
public class DmInvestServiceImpl implements InvestService {
    private static final Logger     log = LoggerFactory.getLogger(DmInvestServiceImpl.class);

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

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public PlainResult<Integer> createInvest(Invest pojo) {
        PlainResult<Integer> result = new PlainResult<Integer>();

        if (pojo == null) {
            result.setErrorMessage(CommonResultCode.ILLEGAL_PARAM_BLANK);
            return result;
        }

        // 默认不使用红包的情况下，应付金额等于实际投资金额
        pojo.setInvestPayMoney(pojo.getInvestMoney());

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

        // 5. 添加订单记录
        InvestOrder order = new InvestOrder();
        order.setOrderMoney(pojo.getInvestMoney());
        order.setOriginId(pojo.getOriginId());
        order.setOrderState(OrderState.UNPAID);
        order.setBidType(pojo.getBidType());
        order.setBidId(pojo.getBidId());
        order.setUserId(pojo.getUserId());
        order.setInnerSeqNo(innerSeqNo.toString() + 0);//没有使用红包加个0?

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
        userList.add(investor);

        // 借款人
        UserIdentity loanee = new UserIdentity();
        loanee.setUserId(loanDO.getLoanUserId());
        loanee.setUserType(queryUserTypeByUserId(loanee.getUserId()));
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
            payUserNo = userAccountList.get(0).getAccountMark();
            receiveUserNo = userAccountList.get(1).getAccountMark();
        } else {
            investorAccountNo = userAccountList.get(1).getAccountNo();
            loaneeAccountNo = userAccountList.get(0).getAccountNo();

            payUserNo = userAccountList.get(1).getAccountMark();
            receiveUserNo = userAccountList.get(0).getAccountMark();
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
            dealDetail.setReceiveAccountId(loaneeAccountNo);
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

            DealDetail redDetail = new DealDetail();
            redDetail.setData(params);
            redDetail.setMoneyAmount(redAmount);
            redDetail.setPayAccountId(platformAccountResult.getData().getConfValue());
            //redDetail.setReceiveAccountId(loaneeAccountNo);
            redDetail.setReceiveAccountId(investorAccountNo);//红包给投资人，dealRecord表记录
            redDetail.setDealDetailType(DealDetailType.INVESTE_MONEY);
            detailList.add(redDetail);

            // 5. 添加订单记录
            InvestOrder redSendOrder = new InvestOrder();
            redSendOrder.setOrderMoney(redAmount);
            redSendOrder.setOriginId(pojo.getOriginId());
            redSendOrder.setOrderState(OrderState.UNPAID);
            redSendOrder.setBidType(pojo.getBidType());
            redSendOrder.setBidId(pojo.getBidId());
            redSendOrder.setUserId(pojo.getUserId());
            redSendOrder.setInnerSeqNo(innerSeqNo.toString() + 1);

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
        pojo.setInvestState(InvestState.UNPAID);
        invest.setInvestState(InvestState.UNPAID);
        invest.setBidType(pojo.getBidType());
        invest.setBidId(pojo.getBidId());
        invest.setInnerSeqNo(innerSeqNo.toString() + 0);

        // 5. 添加订单记录
        InvestOrder order = new InvestOrder();
        order.setOrderMoney(pojo.getInvestMoney());
        order.setOriginId(transferLoanDO.getTlOriginId());
        order.setOrderState(OrderState.UNPAID);
        order.setBidType(pojo.getBidType());
        order.setBidId(pojo.getBidId());
        order.setUserId(pojo.getUserId());
        order.setInnerSeqNo(innerSeqNo.toString() + 0);

        // 6. 添加资金支付交易记录
        List<UserIdentity> userList = new ArrayList<UserIdentity>(2);

        // 投资人
        UserIdentity investor = new UserIdentity();
        investor.setUserId(invest.getUserId());
        investor.setUserType(queryUserTypeByUserId(investor.getUserId()));
        userList.add(investor);

        // 借款人
        UserIdentity loanee = new UserIdentity();
        loanee.setUserId(transferLoanDO.getTlUserId());
        loanee.setUserType(queryUserTypeByUserId(loanee.getUserId()));
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
            payUserNo = userAccountList.get(0).getAccountMark();
            receiveUserNo = userAccountList.get(1).getAccountMark();
        } else {
            investorAccountNo = userAccountList.get(1).getAccountNo();
            loaneeAccountNo = userAccountList.get(0).getAccountNo();

            payUserNo = userAccountList.get(1).getAccountMark();
            receiveUserNo = userAccountList.get(0).getAccountMark();
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
        dealDetail.setReceiveAccountId(loaneeAccountNo);
        dealDetail.setDealDetailType(DealDetailType.INVESTE_MONEY);
        deal.setInnerSeqNo(innerSeqNo);
        deal.setBusinessType(DealType.INVESTER);
        deal.setOperator(pojo.getUserId());
        deal.setDealDetail(Arrays.asList(dealDetail));
        deal.setBusinessId(transferLoanDO.getTlOriginId());

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

        // 添加业务活动记录
        activity.setForeignId(investDO.getInId());
        BaseResult activityResult = activityRecordService.createActivityRecord(activity);
        if (!activityResult.isSuccess()) {
            log.warn(activityResult.getMessage());
            throw new BusinessException("业务活动记录创建失败");
        }

        // 9. 发起资金支付
        BaseResult invokeResult = dealRecordService.invokePayment(deal.getInnerSeqNo().getUniqueNo());
        if (!invokeResult.isSuccess()) {
            log.warn(invokeResult.getMessage());
            throw new BusinessException("发起资金支付失败");
        }
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

        // 投资人
        UserIdentity investor = new UserIdentity();
        investor.setUserId(investDO.getInUserId());
        investor.setUserType(queryUserTypeByUserId(investor.getUserId()));

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

        // 投资人
        UserIdentity investor = new UserIdentity();
        investor.setUserId(investDO.getInUserId());
        investor.setUserType(queryUserTypeByUserId(investor.getUserId()));

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
    public PlainResult<Integer> createInvest(Invest pojo, List<Integer> redsendIdList) {
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
	public int batchUpdateInvestState(Integer loanId, int type, int state,
			int state2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<String, String> checkInvestConditions(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}
}
