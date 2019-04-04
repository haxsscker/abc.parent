package com.autoserve.abc.web.module.screen.review.json;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.dao.dataobject.search.IntentReviewSearchDO;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.LoanIntentApply;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.loan.LoanIntentApplyService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.IntentCheckVOConverter;
import com.autoserve.abc.web.util.VOUtil;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.review.IntentCheckVO;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 退回审核页面数据加载与搜索
 *
 * @author yuqing.zheng Created on 2015-01-12,20:31
 */
public class LoanBackListSearch {
    private static final Logger     logger = LoggerFactory.getLogger(LoanBackListSearch.class);

    @Autowired
    private ReviewService           reviewService;

    @Autowired
    private LoanIntentApplyService intentApplyService;

    @Autowired
    private LoanQueryService        loanQueryService;

    @Autowired
    private GovernmentService       governmentService;

    public JsonPageVO<IntentCheckVO> execute(@Params IntentReviewSearchDO searchDO,
                                             @Param("page") int page,
                                             @Param("rows") int rows) {
        JsonPageVO<IntentCheckVO> result = new JsonPageVO<IntentCheckVO>();
        PageCondition pageCondition = new PageCondition(page, rows);

        PageResult<Review> reviewRes = reviewService.searchIntentReview(searchDO, pageCondition);
        if (!reviewRes.isSuccess() && CollectionUtils.isEmpty(reviewRes.getData())) {
            logger.info("未找到相关审核");
            return VOUtil.emptyPageVO("没有相关审核数据");
        }
        result.setTotal(reviewRes.getTotalCount());

        List<Review> reviews = reviewRes.getData();
        List<Integer> intentIds = Lists.transform(reviews, new Function<Review, Integer>() {
            @Override
            public Integer apply(Review review) {
                return review.getApplyId();
            }
        });

        ListResult<LoanIntentApply> intentRes = intentApplyService.queryByIntentIds(intentIds);
        if (!intentRes.isSuccess()) {
            logger.error("未找到相关意向退回申请, intentIds={}", intentIds);
            return VOUtil.emptyPageVO("没有相关退回审核数据");
        }
        List<LoanIntentApply> intentApplyList = intentRes.getData();

        ListResult<Loan> loanListRes = loanQueryService.queryByIntentIds(intentIds);
        List<Loan> loanList;
        if (!loanListRes.isSuccess()) {
            logger.warn("未找到相关项目信息");
            loanList = Lists.newArrayList();
        } else {
            loanList = loanListRes.getData();
        }

        List<Integer> govIdsWithNull = Lists.transform(loanList, new Function<Loan, Integer>() {
            @Override
            public Integer apply(Loan loan) {
                return loan.getLoanGuarGov();
            }
        });
        List<Integer> govIds = Lists.newArrayList();
        for (Integer govId : govIdsWithNull) {
            if (govId != null) {
                govIds.add(govId);
            }
        }

        List<GovernmentDO> governments = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(govIds)) {
            ListResult<GovernmentDO> govListRes = governmentService.findByList(govIds);
            if (!govListRes.isSuccess()) {
                logger.warn("查询项目相关担保机构失败，govIds={}", govIds.toString());
            } else {
                governments = govListRes.getData();
            }
        }
        Map<Integer, GovernmentDO> govIdMap = Maps.uniqueIndex(governments, new Function<GovernmentDO, Integer>() {
            @Override
            public Integer apply(GovernmentDO gov) {
                return gov.getGovId();
            }
        });

        Map<Integer, Loan> intentIdLoanMap = Maps.uniqueIndex(loanList, new Function<Loan, Integer>() {
            @Override
            public Integer apply(Loan loan) {
                return loan.getLoanIntentId();
            }
        });

        Map<Integer, Review> reviewMap = Maps.uniqueIndex(reviews, new Function<Review, Integer>() {
            @Override
            public Integer apply(Review review) {
                return review.getApplyId();
            }
        });

        List<IntentCheckVO> intentCheckVOList = Lists.newArrayList();
        for (LoanIntentApply intentApply : intentApplyList) {
            Review review = reviewMap.get(intentApply.getId());
            Loan loan = intentIdLoanMap.get(intentApply.getId());
            GovernmentDO gov = null;
            if (loan != null && loan.getLoanGuarGov() != null) {
                gov = govIdMap.get(loan.getLoanGuarGov());
            }

            intentCheckVOList.add(IntentCheckVOConverter.toIntentCheckVO(intentApply, review, loan, gov));
        }

        result.setRows(intentCheckVOList);
        return result;
    }
}
