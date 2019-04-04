package com.autoserve.abc.service.biz.impl.score;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ScoreUsageRecordDO;
import com.autoserve.abc.dao.dataobject.ScoreUsageRecordWithNameDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.ScoreUsageRecordDao;
import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.callback.center.ReviewCallbackCenter;
import com.autoserve.abc.service.biz.convert.ScoreUsageRecordConverter;
import com.autoserve.abc.service.biz.entity.ReviewOp;
import com.autoserve.abc.service.biz.entity.ScoreUsageRecord;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.enums.*;
import com.autoserve.abc.service.biz.intf.review.ReviewOpLogService;
import com.autoserve.abc.service.biz.intf.score.ScoreUsageRecordService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.*;
import com.autoserve.abc.service.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author RJQ 2014/12/21 22:20.
 */
@Service
public class ScoreUsageRecordServiceImpl implements ScoreUsageRecordService {
    @Resource
    private ScoreUsageRecordDao scoreUsageRecordDao;

    @Resource
    private ReviewOpLogService reviewOpLogService;

    @Resource
    private UserService userService;

    @Resource
    private SysConfigService sysConfigService;

    private static Logger logger = LoggerFactory.getLogger(ScoreUsageRecordServiceImpl.class);

    /**
     * 兑换积分审核回调方法
     */
    private final Callback<ReviewOp> reviewCallback = new Callback<ReviewOp>() {

        @Override
        public BaseResult doCallback(ReviewOp data) {
            int surId = data.getReview().getApplyId();
            ReviewOpType reviewOpType = data.getOpType();
            BaseResult result = null;

            switch (reviewOpType) {
                case PASS:
                    result = modifyScoreUsageRecordAfterReview(surId,
                            ReviewState.PASS_REVIEW);
                    return result;
                case REJECT:
                    result = modifyScoreUsageRecordAfterReview(surId,
                            ReviewState.FAILED_PASS_REVIEW);
                    return result;
            }
            return result;
        }
    };

    @PostConstruct
    private void registerCallBack() {
        ReviewCallbackCenter.registerCallback(ReviewType.SCORE_REDEEM_REVIEW, reviewCallback);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult modifyScoreUsageRecordAfterReview(int surId, ReviewState reviewState) {
        BaseResult result = new BaseResult();
        ScoreUsageRecordDO scoreUsageRecordDO = new ScoreUsageRecordDO();
        if(reviewState == ReviewState.PASS_REVIEW){//通过审核
            //修改用户积分和积分最后修改时间
            scoreUsageRecordDO = scoreUsageRecordDao.findById(surId);
            PlainResult<UserDO> userDOPlainResult= userService.findByIdWithLock(scoreUsageRecordDO.getSurUserId());
            if(!userDOPlainResult.isSuccess()){
                result.setErrorMessage(CommonResultCode.BIZ_ERROR,"查询用户信息失败！");
                return result;
            }
            UserDO userDO = userDOPlainResult.getData();
            userDO.setUserScore(userDO.getUserScore() - scoreUsageRecordDO.getSurExchangeScore());
            userDO.setUserScoreLastmodifytime(new Date());
            BaseResult userUpdateResult =  userService.modifyUserSelective(userDO);
            if(!userUpdateResult.isSuccess()){
                logger.error("积分兑现通过审核，修改用户积分失败！ surId={} userId={}", surId, userDO.getUserId());
                result.setErrorMessage(CommonResultCode.BIZ_ERROR,"更新用户积分失败！");
                return result;
            }

            //获取积分使用期限的默认值
            PlainResult<SysConfig> configPlainResult = sysConfigService.querySysConfig(SysConfigEntry.SCORE_USAGE_VALID_PERIOD);
            if(!configPlainResult.isSuccess()){
                logger.error("积分兑现通过审核，修改积分兑现记录失败！ surId={} userId={}", surId, userDO.getUserId());
                throw new BusinessException("修改积分兑现记录失败!");
            }
            SysConfig sysConfig = configPlainResult.getData();
            Integer scoreUsageValidPeriod = Integer.valueOf(sysConfig.getConfValue());
            Date startDate = new Date();//积分有效起始日期
            Calendar calStart = Calendar.getInstance();
            calStart.setTime(startDate);
            calStart.add(Calendar.MONTH, scoreUsageValidPeriod);
            Date endDate = calStart.getTime();//积分有效截止日期

            scoreUsageRecordDO.setSurStartDate(startDate);
            scoreUsageRecordDO.setSurEndDate(endDate);
            scoreUsageRecordDO.setSurUseMoney(new BigDecimal(0));
            scoreUsageRecordDO.setSurScoreState(ScoreUsageState.NOT_USE.getState());
        }
        scoreUsageRecordDO.setSurReviewState(reviewState.getState());
        int val =  scoreUsageRecordDao.updateByPrimaryKeySelective(scoreUsageRecordDO);
        if (val <= 0) {
            throw new BusinessException("积分兑现审核回调失败！");
        }
        return result;
    }

    @Override
    public PageResult<ScoreUsageRecord> queryReviewOpLogBySurId(ScoreUsageRecord record, PageCondition pageCondition) {
        PageResult<ScoreUsageRecord> result = new PageResult<ScoreUsageRecord>(pageCondition);

        ScoreUsageRecordDO scoreUsageRecordDO = ScoreUsageRecordConverter.convertScoreUsageRecordToDO(record);
        int totalCount = scoreUsageRecordDao.countListByParam(scoreUsageRecordDO);
        result.setTotalCount(totalCount);
        if (totalCount > 0) {
            List<ScoreUsageRecordWithNameDO> usageRecordDOs = scoreUsageRecordDao.findListByParam(scoreUsageRecordDO, pageCondition);
            List<Integer> reviewApplyIds = new ArrayList<Integer>();//存储积分兑现审核记录主键
            if (usageRecordDOs == null || usageRecordDOs.size() == 0) {
                return result;
            }
            for (ScoreUsageRecordWithNameDO recordDO : usageRecordDOs) {
                reviewApplyIds.add(recordDO.getSurId());
            }
            //根据积分兑现审核记录主键和审核类型查询到审核时间，审核意见等详情
            MapResult<Integer, List<ReviewOp>> mapResult = reviewOpLogService.queryMultiReviewOpHistory(
                    ReviewType.SCORE_REDEEM_REVIEW, reviewApplyIds);

            List<ScoreUsageRecord> scoreUsageRecords = ScoreUsageRecordConverter.convertNameList(usageRecordDOs);
            for (int i = 0, j = scoreUsageRecords.size(); i < j; ++i) {
                List<ReviewOp> reviewOps = mapResult.getData().get(scoreUsageRecords.get(i).getSurId());
                if (reviewOps != null && reviewOps.size() != 0) {
                    scoreUsageRecords.get(i).setReviewDate(reviewOps.get(0).getDate());//审核时间
                    scoreUsageRecords.get(i).setReviewNote(reviewOps.get(0).getMessage());//审核意见
                }
            }

            result.setData(scoreUsageRecords);
            return result;
        }
        return result;
    }

    @Override
    public PageResult<ScoreUsageRecord> queryReviewOpLogBySurId(ScoreUsageRecord record, String userName, Integer scoreStart,
                                                                Integer scoreEnd, Date toCashStartDate, Date toCashEndDate,
                                                                PageCondition pageCondition) {
        PageResult<ScoreUsageRecord> result = new PageResult<ScoreUsageRecord>(pageCondition);

        ScoreUsageRecordDO scoreUsageRecordDO = ScoreUsageRecordConverter.convertScoreUsageRecordToDO(record);
        int totalCount = scoreUsageRecordDao.countListByMap(userName, scoreUsageRecordDO, scoreStart, scoreEnd, toCashStartDate, toCashEndDate);
        result.setTotalCount(totalCount);
        if (totalCount > 0) {
            List<ScoreUsageRecordWithNameDO> usageRecordDOs = scoreUsageRecordDao.findListByMap(userName, scoreUsageRecordDO, scoreStart,
                    scoreEnd, toCashStartDate, toCashEndDate, pageCondition);
            List<Integer> reviewApplyIds = new ArrayList<Integer>();//存储积分兑现审核记录主键
            if (usageRecordDOs == null || usageRecordDOs.size() == 0) {
                return result;
            }
            for (ScoreUsageRecordWithNameDO recordDO : usageRecordDOs) {
                reviewApplyIds.add(recordDO.getSurId());
            }
            //根据积分兑现审核记录主键和审核类型查询到审核时间，审核意见等详情
            MapResult<Integer, List<ReviewOp>> mapResult = reviewOpLogService.queryMultiReviewOpHistory(
                    ReviewType.SCORE_REDEEM_REVIEW, reviewApplyIds);

            List<ScoreUsageRecord> scoreUsageRecords = ScoreUsageRecordConverter.convertNameList(usageRecordDOs);
            for (int i = 0, j = scoreUsageRecords.size(); i < j; ++i) {
                List<ReviewOp> reviewOps = mapResult.getData().get(scoreUsageRecords.get(i).getSurId());
                if (reviewOps != null && reviewOps.size() != 0) {
                    scoreUsageRecords.get(i).setReviewDate(reviewOps.get(0).getDate());//审核时间
                    scoreUsageRecords.get(i).setReviewNote(reviewOps.get(0).getMessage());//审核意见
                }
            }

            result.setData(scoreUsageRecords);
            return result;
        }
        return result;
    }

    @Override
    public PlainResult<ScoreUsageRecordWithNameDO> queryScoreUsageRecordById(Integer id) {
        PlainResult<ScoreUsageRecordWithNameDO> result = new PlainResult<ScoreUsageRecordWithNameDO>();
        ScoreUsageRecordWithNameDO recordWithNameDO = scoreUsageRecordDao.findWithNameById(id);
        if (recordWithNameDO == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "查询失败！");
            return result;
        }
        result.setData(recordWithNameDO);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageResult<ScoreUsageRecordWithNameDO> queryScoreUsageRecordList(ScoreUsageRecordDO scoreUsageRecordDO, PageCondition pageCondition) {
        PageResult<ScoreUsageRecordWithNameDO> result = new PageResult<ScoreUsageRecordWithNameDO>(pageCondition.getPage(),
                pageCondition.getPageSize());
        int totalCount = scoreUsageRecordDao.countListByParam(scoreUsageRecordDO);
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            List<ScoreUsageRecordWithNameDO> list = scoreUsageRecordDao.findListByParam(scoreUsageRecordDO, pageCondition);
            result.setData(list);
        }

        return result;
    }

    @Override
    public PageResult<ScoreUsageRecordWithNameDO> searchScoreUsageRecordList(String userName, ScoreUsageRecordDO record, Integer scoreStart,
                                                                             Integer scoreEnd, Date toCashStartDate,
                                                                             Date toCashEndDate, PageCondition pageCondition) {
        PageResult<ScoreUsageRecordWithNameDO> result = new PageResult<ScoreUsageRecordWithNameDO>(pageCondition.getPage(),
                pageCondition.getPageSize());
        int totalCount = scoreUsageRecordDao.countListByMap(userName, record, scoreStart, scoreEnd, toCashStartDate, toCashEndDate);
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            List<ScoreUsageRecordWithNameDO> list = scoreUsageRecordDao.findListByMap(userName, record, scoreStart,
                    scoreEnd, toCashStartDate, toCashEndDate, pageCondition);
            result.setData(list);
        }
        return result;
    }
}
