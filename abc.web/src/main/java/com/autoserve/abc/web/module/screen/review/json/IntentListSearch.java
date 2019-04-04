package com.autoserve.abc.web.module.screen.review.json;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.dao.dataobject.search.IntentReviewSearchDO;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.LoanIntentApply;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.ReviewSendStatus;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.loan.LoanIntentApplyService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.review.ReviewSendStatusService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.IntentCheckVOConverter;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.util.VOUtil;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.review.IntentCheckVO;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * @author yuqing.zheng Created on 2015-01-02,21:29
 */
public class IntentListSearch {
	private static final Logger logger = LoggerFactory
			.getLogger(IntentListSearch.class);

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private LoanIntentApplyService intentService;

	@Autowired
	private ReviewSendStatusService sendStatusService;

	@Autowired
	private LoanQueryService loanQueryService;
	@Resource
	protected GovernmentService governmentService;

	public JsonPageVO<IntentCheckVO> execute(
			@Params IntentReviewSearchDO searchDO, @Param("page") int page,
			@Param("rows") int rows, ParameterParser params, Context context) {
		JsonPageVO<IntentCheckVO> result = new JsonPageVO<IntentCheckVO>();
		PageCondition pageCondition = new PageCondition(page, rows);

		searchDO.setIntentTimeTo(params.getDate("intentTimeTo",
				new SimpleDateFormat(DateUtil.DATE_FORMAT)));
		searchDO.setIntentTimeFrom(params.getDate("intentTimeFrom",
				new SimpleDateFormat(DateUtil.DATE_FORMAT)));
		PageResult<Review> reviewRes = reviewService.searchIntentReview(
				searchDO, pageCondition);
		if (!reviewRes.isSuccess()
				&& CollectionUtils.isEmpty(reviewRes.getData())) {
			logger.info("未找到相关审核");
			return VOUtil.emptyPageVO("没有相关审核数据");
		}

		result.setTotal(reviewRes.getTotalCount());
		List<Review> reviews = reviewRes.getData();
		List<Integer> intentIds = Lists.transform(reviews,
				new Function<Review, Integer>() {
					@Override
					public Integer apply(Review review) {
						return review.getApplyId();
					}
				});

		ListResult<LoanIntentApply> intentRes = intentService
				.queryByIntentIds(intentIds);
		if (!intentRes.isSuccess()) {
			logger.error("未找到任何相关意向信息, intentIds={}", intentIds.toString());
			return VOUtil.emptyPageVO();
		}
		List<LoanIntentApply> intents = intentRes.getData();

		List<Loan> loanList = Lists.newArrayList();
		ListResult<Loan> loanRes = loanQueryService.queryByIntentIds(intentIds);
		if (loanRes.isSuccess()) {
			loanList = loanRes.getData();
		}
		// 查询担保信息
		Set<Integer> govIds = Sets.newHashSet();
		for (Loan loan : loanList) {
			govIds.add(loan.getLoanGuarGov());
		}
		ListResult<GovernmentDO> govResult = governmentService
				.findByList(new ArrayList<Integer>(govIds));
		Map<Integer, String> govNameMap = Maps.newHashMap();
		if (govResult.isSuccess()) {
			for (GovernmentDO govDO : govResult.getData()) {
				govNameMap.put(govDO.getGovId(), govDO.getGovName());
			}
		}

		Map<Integer, Loan> intentIdLoanMap = Maps.uniqueIndex(loanList,
				new Function<Loan, Integer>() {
					@Override
					public Integer apply(Loan loan) {
						return loan.getLoanIntentId();
					}
				});

		List<Integer> reviewIdList = Lists.transform(reviews,
				new Function<Review, Integer>() {
					@Override
					public Integer apply(Review review) {
						return review.getReviewId();
					}
				});
		//查询发送状态
		ListResult<ReviewSendStatus> sendStatusRes = sendStatusService
				.queryByReviewIdList(reviewIdList);
		List<ReviewSendStatus> sendStatusList;
		if (sendStatusRes.isSuccess()) {
			sendStatusList = sendStatusRes.getData();
		} else {
			sendStatusList = Lists.newArrayList();
		}
		Map<Integer, ReviewSendStatus> sendStatusMap = Maps.uniqueIndex(
				sendStatusList, new Function<ReviewSendStatus, Integer>() {
					@Override
					public Integer apply(ReviewSendStatus sendStatus) {
						return sendStatus.getReview().getReviewId();
					}
				});

		Map<Integer, Review> reviewMap = Maps.uniqueIndex(reviews,
				new Function<Review, Integer>() {
					@Override
					public Integer apply(Review review) {
						return review.getApplyId();
					}
				});
		

		List<IntentCheckVO> voList = Lists.newArrayList();
		for (LoanIntentApply intentApply : intents) {
			Review review = reviewMap.get(intentApply.getId());
			ReviewSendStatus sendStatus = sendStatusMap.get(review
					.getReviewId());
			Loan loan = intentIdLoanMap.get(intentApply.getId());

			IntentCheckVO intentCheckVO;
			if (loan == null) {
				intentCheckVO = IntentCheckVOConverter.toIntentCheckVO(
						intentApply, review, sendStatus);
			} else {
				intentCheckVO = IntentCheckVOConverter.toIntentCheckVO(
						intentApply, loan, review, sendStatus, govNameMap);
			}
			voList.add(intentCheckVO);
		}

		result.setRows(voList);
		return result;
	}
}
