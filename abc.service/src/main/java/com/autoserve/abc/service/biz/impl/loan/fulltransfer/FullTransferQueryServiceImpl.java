/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.loan.fulltransfer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.dao.dataobject.FullTransferRecordDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.search.BidIdentitySearchDO;
import com.autoserve.abc.dao.dataobject.search.FullTransferFundsSearchDO;
import com.autoserve.abc.dao.dataobject.summary.FullTransferSummaryDO;
import com.autoserve.abc.dao.intf.FullTransferRecordDao;
import com.autoserve.abc.service.biz.entity.BuyLoan;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.TransferFundsDetailInfo;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoaneeType;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.loan.BuyLoanService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.loan.fulltransfer.FullTransferQueryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.DateUtil;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 类FullTransferQueryServiceImpl.java的实现描述：资金划转统计
 *
 * @author J.YL 2014年12月31日 下午5:49:11
 */
@Service
public class FullTransferQueryServiceImpl implements FullTransferQueryService {
    private static final Logger   logger = LoggerFactory.getLogger(FullTransferQueryServiceImpl.class);
    @Resource
    private FullTransferRecordDao fullTransferRecordDao;
    @Resource
    private TransferLoanService   transferLoanService;
    @Resource
    private BuyLoanService        buyLoanService;
    @Resource
    private LoanQueryService      loanQueryService;
    @Resource
    private UserService           userService;
    @Resource
    private EmployeeService       employeeService;

    @Override
    public ListResult<FullTransferSummaryDO> queryTransferSummaryInfo(List<Integer> year) {
        List<FullTransferSummaryDO> resultList = fullTransferRecordDao.findTransferSummaryInfo(year);
        ListResult<FullTransferSummaryDO> result = new ListResult<FullTransferSummaryDO>();
        result.setData(resultList);
        return result;
    }

    @Override
    public PageResult<Integer> queryYears(PageCondition pageCondition) {
        int count = fullTransferRecordDao.countFullTransferSummaryYears();
        List<Integer> years = fullTransferRecordDao.findSummaryYears(pageCondition);
        PageResult<Integer> result = new PageResult<Integer>(pageCondition);
        result.setTotalCount(count);
        result.setData(years);
        return result;
    }

    @Override
    public PageResult<TransferFundsDetailInfo> queryDetail(FullTransferFundsSearchDO searchDO, Date date,
            PageCondition pageCondition) {
        List<FullTransferRecordDO> queryRes = null;
        int count = 0;
        DateTime dateTime = new DateTime(date);
        Date endMounth = new Date(dateTime.plusMonths(1).dayOfMonth().withMinimumValue().getMillis());  //下个月第一天的开始，如：2015-9-1 00：00:00.0
        List<Integer> loanIds = new ArrayList<Integer>();
        List<Integer> transferIds = new ArrayList<Integer>();
        List<Integer> buyIds = new ArrayList<Integer>();
        List<BidIdentitySearchDO> bidIdentities = new ArrayList<BidIdentitySearchDO>();
        if (searchDO == null) {
            queryRes = fullTransferRecordDao.findDetailInfoByDate(date, pageCondition);
            count = fullTransferRecordDao.countDetailInfoByDate(date);
        } else {
            //设置放款时间
            if (searchDO.getLendDateTo() == null || searchDO.getLendDateTo().after(endMounth)) {
                searchDO.setLendDateTo(endMounth);
            }
            if (searchDO.getLendDateFrom() == null || searchDO.getLendDateFrom().before(date)) {
                searchDO.setLendDateFrom(date);
            }
            if (searchDO.getLoanNo() != null || searchDO.getLoanCategory() != null) {
                Integer category = searchDO.getLoanCategory();
                if (LoanCategory.valueOf(category) == null) {
                    category = null;
                }
                loanIds = fullTransferRecordDao.findLoanIds(searchDO.getLoanNo(), category, date);
                transferIds = fullTransferRecordDao.findTransferIds(searchDO.getLoanNo(), category, date);
                buyIds = fullTransferRecordDao.findBuyIds(searchDO.getLoanNo(), category, date);
                //根据项目编号和项目类型查询三种标均无符合记录则结束
                if (CollectionUtils.isEmpty(buyIds) && CollectionUtils.isEmpty(loanIds)
                        && CollectionUtils.isEmpty(transferIds)) {
                    return new PageResult<TransferFundsDetailInfo>(pageCondition);
                }
            }
            for (Integer i : loanIds) {
                BidIdentitySearchDO bsd = new BidIdentitySearchDO();
                bsd.setBidId(i);
                bsd.setBidType(BidType.COMMON_LOAN.getType());
                bidIdentities.add(bsd);
            }
            for (Integer i : transferIds) {
                BidIdentitySearchDO bsd = new BidIdentitySearchDO();
                bsd.setBidId(i);
                bsd.setBidType(BidType.TRANSFER_LOAN.getType());
                bidIdentities.add(bsd);
            }
            for (Integer i : buyIds) {
                BidIdentitySearchDO bsd = new BidIdentitySearchDO();
                bsd.setBidId(i);
                bsd.setBidType(BidType.BUY_LOAN.getType());
                bidIdentities.add(bsd);
            }
            if (CollectionUtils.isEmpty(bidIdentities)) {
                bidIdentities = null;
            }
            queryRes = fullTransferRecordDao.findBySearchDO(searchDO, date, bidIdentities, pageCondition);
            count = fullTransferRecordDao.countBySearchDO(searchDO, date, bidIdentities);
        }
        if (queryRes == null) {
            queryRes = new ArrayList<FullTransferRecordDO>();
        }
        return process(queryRes, date, count, pageCondition);
    }

    private PageResult<TransferFundsDetailInfo> process(List<FullTransferRecordDO> queryRes, Date date, int count,
            PageCondition pageCondition) {
        List<Integer> commonBid = new ArrayList<Integer>();
        List<Integer> transferBid = new ArrayList<Integer>();
        List<Integer> buyBid = new ArrayList<Integer>();
        for (FullTransferRecordDO ftd : queryRes) {
            BidType type = BidType.valueOf(ftd.getFtrBidType());
            switch (type) {
                case COMMON_LOAN:
                    commonBid.add(ftd.getFtrBidId());
                    break;
                case TRANSFER_LOAN:
                    transferBid.add(ftd.getFtrBidId());
                    break;
                case BUY_LOAN:
                    buyBid.add(ftd.getFtrBidId());
                    break;
                default:
                    logger.warn("满标资金划转记录id：{} 标类型错误 标类型为：{}", ftd.getFtrId(), ftd.getFtrBidType());
                    break;
            }
        }
        List<BuyLoan> buyLoanList = new ArrayList<BuyLoan>();
        List<TransferLoan> transferList = new ArrayList<TransferLoan>();
        if (!CollectionUtils.isEmpty(buyBid)) {
            buyLoanList = buyLoanService.queryByIds(buyBid).getData();
        }
        if (!CollectionUtils.isEmpty(transferBid)) {
            transferList = transferLoanService.queryByIds(transferBid).getData();
        }
        Map<Integer, BuyLoan> buyLoanMap = Maps.uniqueIndex(buyLoanList, new Function<BuyLoan, Integer>() {
            @Override
            public Integer apply(BuyLoan input) {
                return input.getId();
            }
        });
        Map<Integer, TransferLoan> transferMap = Maps.uniqueIndex(transferList, new Function<TransferLoan, Integer>() {
            @Override
            public Integer apply(TransferLoan input) {
                return input.getId();
            }
        });
        //收购标的原始标id
        List<Integer> buyLoanOri = Lists.transform(buyLoanList, new Function<BuyLoan, Integer>() {
            @Override
            public Integer apply(BuyLoan input) {
                return input.getOriginId();
            }
        });
        //转让标的原始标id
        List<Integer> transferLoanOri = Lists.transform(transferList, new Function<TransferLoan, Integer>() {

            @Override
            public Integer apply(TransferLoan input) {
                return input.getOriginId();
            }
        });
        //将原始标信息id
        commonBid.addAll(transferLoanOri);
        commonBid.addAll(buyLoanOri);
        //去重
        commonBid = new ArrayList<Integer>(new HashSet<Integer>(commonBid));
        //查出所有的原始标信息
        List<Loan> loanList = loanQueryService.queryByIds(commonBid).getData();
        Map<Integer, Loan> loanMap = Maps.uniqueIndex(loanList, new Function<Loan, Integer>() {
            @Override
            public Integer apply(Loan input) {
                return input.getLoanId();
            }
        });

        List<Integer> front = new ArrayList<Integer>();
        List<Integer> back = new ArrayList<Integer>();
        //划分出前后台用户
        for (Loan loan : loanList) {
            LoaneeType userType = loan.getLoaneeType();
            Integer userId = loan.getLoanUserId();
            switch (userType) {
                case COMPANY:
                    front.add(userId);
                    break;
                case GOV:
                    back.add(userId);
                    break;
                case PERSON:
                    front.add(userId);
                    break;
                case PLATFORM:
                    back.add(userId);
                    break;
                default:
                    break;
            }
        }
        ListResult<UserDO> user = null;
        Map<Integer, UserDO> userMap = new HashMap<Integer, UserDO>();
        ListResult<EmployeeDO> emp = null;
        Map<Integer, EmployeeDO> empMap = new HashMap<Integer, EmployeeDO>();
        if (!CollectionUtils.isEmpty(front)) {
            user = userService.findByList(front);
            userMap = Maps.uniqueIndex(user.getData(), new Function<UserDO, Integer>() {
                @Override
                public Integer apply(UserDO input) {
                    return input.getUserId();
                }
            });
        }
        if (!CollectionUtils.isEmpty(back)) {
            emp = employeeService.findByList(back);
            empMap = Maps.uniqueIndex(emp.getData(), new Function<EmployeeDO, Integer>() {
                @Override
                public Integer apply(EmployeeDO input) {
                    return input.getEmpId();
                }
            });
        }

        List<TransferFundsDetailInfo> resultList = new ArrayList<TransferFundsDetailInfo>();
        for (FullTransferRecordDO ftd : queryRes) {
            TransferFundsDetailInfo temp = new TransferFundsDetailInfo();
            resultList.add(temp);
            temp.setCollectFee(ftd.getFtrRealPayFee());
            temp.setCollectGuarFee(ftd.getFtrRealGuarFee());
            temp.setLendDate(DateUtil.formatDate(ftd.getFtrTransferDate(), DateUtil.DEFAULT_FORMAT_STYLE));
            temp.setLendMoney(ftd.getFtrTransferMoney());
            temp.setLoanType(ftd.getFtrBidType());
            BidType bidType = BidType.valueOf(ftd.getFtrBidType());
            Loan ori = null;
            switch (bidType) {
                case BUY_LOAN: {
                    BuyLoan bl = buyLoanMap.get(ftd.getFtrBidId());
                    ori = loanMap.get(bl.getOriginId());
                    temp.setLoanNo(ori.getLoanNo());
                    temp.setLoanRate(ori.getLoanRate());
                    temp.setProductName(ori.getLoanCategory().getPrompt());
                    temp.setLoanMoney(ori.getLoanMoney());
                    break;
                }
                case COMMON_LOAN: {
                    ori = loanMap.get(ftd.getFtrBidId());
                    temp.setLoanNo(ori.getLoanNo());
                    temp.setLoanRate(ori.getLoanRate());
                    temp.setProductName(ori.getLoanCategory().getPrompt());
                    temp.setLoanMoney(ori.getLoanMoney());
                    break;
                }
                case TRANSFER_LOAN: {
                    TransferLoan tl = transferMap.get(ftd.getFtrBidId());
                    ori = loanMap.get(tl.getOriginId());
                    temp.setLoanNo(ori.getLoanNo());
                    temp.setLoanRate(tl.getTransferRate());
                    temp.setProductName(ori.getLoanCategory().getPrompt());
                    temp.setLoanMoney(ori.getLoanMoney());
                    break;
                }
                default:
                    logger.warn("[FullTransferQueryServiceImpl][process] 标类型出错 标类型：{} ", bidType.getType());
                    break;
            }
            switch (ori.getLoaneeType()) {
                case COMPANY: {
                    UserDO ud = userMap.get(ori.getLoanUserId());
                    temp.setRealName(ud.getUserRealName());
                    break;
                }
                case GOV: {
                    EmployeeDO em = empMap.get(ori.getLoanUserId());
                    temp.setRealName(em.getEmpRealName());
                    break;
                }
                case PERSON: {
                    UserDO ud = userMap.get(ori.getLoanUserId());
                    temp.setRealName(ud.getUserRealName());
                    break;
                }
                case PLATFORM: {
                    EmployeeDO em = empMap.get(ori.getLoanUserId());
                    temp.setRealName(em.getEmpRealName());
                    break;
                }
                default:
                    logger.warn("[FullTransferQueryServiceImpl][process] 借款人类型出错，借款人类型：{}", ori.getLoaneeType()
                            .getType());
                    break;
            }
        }
        PageResult<TransferFundsDetailInfo> result = new PageResult<TransferFundsDetailInfo>(pageCondition);
        result.setTotalCount(count);
        result.setData(resultList);
        return result;
    }

}
