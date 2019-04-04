package com.autoserve.abc.service.biz.impl.score;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ScoreConfigDO;
import com.autoserve.abc.dao.dataobject.ScoreDO;
import com.autoserve.abc.dao.dataobject.ScoreUsageRecordDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.ScoreConfigDao;
import com.autoserve.abc.dao.intf.ScoreDao;
import com.autoserve.abc.dao.intf.UserDao;
import com.autoserve.abc.service.biz.entity.Red;
import com.autoserve.abc.service.biz.entity.Redsend;
import com.autoserve.abc.service.biz.enums.EntityState;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.RedState;
import com.autoserve.abc.service.biz.enums.RedenvelopeType;
import com.autoserve.abc.service.biz.enums.RsState;
import com.autoserve.abc.service.biz.enums.ScoreType;
import com.autoserve.abc.service.biz.intf.redenvelope.RedService;
import com.autoserve.abc.service.biz.intf.score.ScoreHistoryService;
import com.autoserve.abc.service.biz.intf.score.ScoreService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * 积分服务实现类
 *
 * @author RJQ 2014/11/22 14:42.
 */
@Service
public class ScoreServiceImpl implements ScoreService {
    @Resource
    private ScoreDao            scoreDao;

    @Resource
    private UserDao             userDao;

    @Resource
    private ScoreHistoryService scoreHistoryService;

    @Resource
    private RedService          redService;
    
    @Resource
    private ScoreConfigDao      scoreConfigDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageResult<ScoreDO> queryScoreList(ScoreDO scoreDO, PageCondition pageCondition) {
        PageResult<ScoreDO> result = new PageResult<ScoreDO>(pageCondition.getPage(), pageCondition.getPageSize());
        int totalCount = scoreDao.countListByParam(scoreDO);
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            result.setData(scoreDao.findListByParam(scoreDO, pageCondition));
        }

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult createScore(ScoreDO scoreDO) {
        BaseResult result = new BaseResult();
        /**
         * 先判断新增的积分类型代码字段是否已存在，已存在的话不允许插入
         * 只需要和启用和禁用状态的积分类型代码比较，已删除的积分类型代码字段相同可以插入
         */
        ScoreDO tmpScore = scoreDao.findByScoreCode(scoreDO.getScoreCode());
        if (tmpScore != null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "添加失败，积分类型代码已存在！");
            return result;
        }

        scoreDO.setScoreState(EntityState.STATE_ENABLE.getState());
        int returnVal = scoreDao.insert(scoreDO);
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "添加积分失败！");
            return result;
        }
        result.setMessage("添加成功！");
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult modifyScore(ScoreDO scoreDO) {
        BaseResult result = new BaseResult();
        /**
         * 先将待修改的积分类型记录状态位置为已删除
         */
        BaseResult removeResult = this.removeScore(scoreDO.getScoreId());
        if (!removeResult.isSuccess()) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "修改积分失败!");
            return result;
        }
        /**
         * 查询积分代码是否已存在，findByScoreCode方法只找状态位启用和禁用的积分类型
         */
        ScoreDO tmpScore = scoreDao.findByScoreCode(scoreDO.getScoreCode());
        if (tmpScore != null && !tmpScore.getScoreId().equals(scoreDO.getScoreId())) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "积分类型代码已存在！");
            return result;
        }
        /**
         * 插入一条新的积分类型记录
         */
        scoreDO.setScoreState(EntityState.STATE_ENABLE.getState());
        int returnVal = scoreDao.insert(scoreDO);
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "修改积分失败！");
            return result;
        }
        result.setMessage("修改成功！");
        return result;
    }

    @Override
    public BaseResult enableScore(int id) {
        BaseResult result = new BaseResult();
        ScoreDO scoreDO = new ScoreDO();
        scoreDO.setScoreId(id);
        scoreDO.setScoreState(EntityState.STATE_ENABLE.getState());
        int returnVal = scoreDao.updateByPrimaryKeySelective(scoreDO);
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "启用失败！");
        }
        return result;
    }

    @Override
    public BaseResult disableScore(int id) {
        BaseResult result = new BaseResult();
        ScoreDO scoreDO = new ScoreDO();
        scoreDO.setScoreId(id);
        scoreDO.setScoreState(EntityState.STATE_DISABLE.getState());
        int returnVal = scoreDao.updateByPrimaryKeySelective(scoreDO);
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "禁用失败！");
        }
        return result;
    }

    @Override
    public BaseResult removeScore(int id) {
        BaseResult result = new BaseResult();
        ScoreDO scoreDO;
        PlainResult<ScoreDO> plainResult = findById(id);
        if (!plainResult.isSuccess()) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到指定积分类型！");
            return result;
        }
        scoreDO = plainResult.getData();
        //查看此积分类型是否允许删除
//        boolean canBeRemoved = checkRemovable(scoreDO);
//        if (!canBeRemoved) {
//            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "此积分类型不可删除！");
//            return result;
//        }

        scoreDO.setScoreState(EntityState.STATE_DELETED.getState());
        int returnVal = scoreDao.updateByPrimaryKeySelective(scoreDO);
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "删除失败！");
            return result;
        }
        result.setMessage("删除成功！");
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult convertRedEnvelope(int userId, Integer score) {
        BaseResult result = new BaseResult();
        int baseConvNum = 10000;

        UserDO userDO = userDao.findByIdWithLock(userId);
        if (userDO == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "用户不存在");
        }

        if (score != null) {
            if (score <= 0 || score > userDO.getUserScore()) {
                return result.setError(CommonResultCode.BIZ_ERROR, "用来兑换的积分不合法");
            }

            result = sendInviteRed(userId, score / baseConvNum);
            if (!result.isSuccess()) {
                return result;
            }

            //修改user表中用户的积分值和修改时间
            userDO.setUserScore(userDO.getUserScore() - score / baseConvNum * baseConvNum);

            int val = userDao.updateByPrimaryKeySelective(userDO);
            if (val <= 0) {
                throw new BusinessException("修改用户积分失败！");
            }
        }
        //        else {
        //            result = sendInviteRed(userId, userDO.getUserScore() / baseConvNum);
        //            if (!result.isSuccess()) {
        //                return result;
        //            }
        //
        //            //修改user表中用户的积分值和修改时间
        //            userDO.setUserScore(userDO.getUserScore() - userDO.getUserScore() / baseConvNum * baseConvNum);
        //
        //            int val = userDao.updateByPrimaryKeySelective(userDO);
        //            if (val <= 0) {
        //                throw new BusinessException("修改用户积分失败！");
        //            }
        //        }

        return result;
    }

    /**
     * 发送积分兑换红包<br>
     * userId:要发给的用户Id<br>
     * factor: 10000积分兑换定额红包，系数
     */
    private BaseResult sendInviteRed(int userId, int factor) {
        BaseResult baseResult = new BaseResult();

        if (factor <= 0) {
            return baseResult;
        }

        Red redParam = new Red();
        redParam.setRedType(RedenvelopeType.SCORE_RED);

        // 查询积分兑换红包
        //        PageResult<Red> redResult = redService.queryList(redParam, null, null);
        ListResult<Red> redResult = redService.queryList(redParam, null);
        if (!redResult.isSuccess()) {
            baseResult.setError(CommonResultCode.BIZ_ERROR, "查询积分税换红包失败！");
            return baseResult;
        }

        // 不存在积分兑换红包直接返回
        List<Red> redList = redResult.getData();
        if (CollectionUtils.isEmpty(redList)) {
            baseResult.setError(CommonResultCode.BIZ_ERROR, "没有积分税换红包规则！");
            return baseResult;
        }

        // 对用户发放积分兑换红包
        List<Redsend> sendList = new ArrayList<Redsend>();
        DateTime fullDaytime = new DateTime();
        for (Red red : redList) {
            if (RedState.EFFECTIVE.equals(red.getRedState())) {
                for (int i = 0; i < factor; i++) {
                    Redsend redsend = new Redsend();
                    redsend.setRsTheme(red.getRedTheme());
                    redsend.setRsUserid(userId);
                    redsend.setRsRedId(red.getRedId());
                    redsend.setRsUseScope(red.getRedUseScope());
                    redsend.setRsState(RsState.WITHOUT_USE);
                    redsend.setRsClosetime(fullDaytime.plusDays(red.getRedClosetime()).toDate());
                    redsend.setRsStarttime(new Date());
                    redsend.setRsSender(red.getRedCreator());
                    redsend.setRsType(RedenvelopeType.SCORE_RED);
                    redsend.setRsAmt(red.getRedAmt());
                    redsend.setRsValidAmount(red.getRedAmount());
                    sendList.add(redsend);
                }
            }
        }

        if (CollectionUtils.isEmpty(sendList)) {
            return baseResult;
        }

        baseResult = redService.batchSendRed(sendList);
        return baseResult;
    }

    /**
     * 判断此积分类型是否和系统预先设定的积分枚举相同，相同的话设置标志位不可删除
     *
     * @param scoreDO 积分类型
     * @return
     */
    private boolean checkRemovable(ScoreDO scoreDO) {
        ScoreType[] scoreTypes = ScoreType.values();
        boolean canBeRemoved = true;
        for (ScoreType st : scoreTypes) {
            if (scoreDO.getScoreCode() != null && scoreDO.getScoreCode().equals(st.getCode())) {
                canBeRemoved = false;
                break;
            }
        }
        return canBeRemoved;
    }

    @Override
    public PlainResult<ScoreDO> findById(int id) {
        PlainResult<ScoreDO> result = new PlainResult<ScoreDO>();
        ScoreDO scoreDO = scoreDao.findById(id);
        if (null == scoreDO) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到指定积分类型！");
        }
        result.setData(scoreDO);
        return result;
    }

    @Override
    public ListResult<ScoreDO> findAllList() {
        ListResult<ScoreDO> result = new ListResult<ScoreDO>();
        ScoreDO scoreDO=new ScoreDO();
        scoreDO.setScoreState(1);   //启用
        List<ScoreDO> scoreDOs = scoreDao.findListByParam(scoreDO, null);
        if (scoreDOs == null || scoreDOs.size() == 0) {
            return result;
        }
        result.setData(scoreDOs);
        return result;
    }

    @Override
    public PlainResult<ScoreDO> findByScoreCode(String code) {
        PlainResult<ScoreDO> result = new PlainResult<ScoreDO>();
        ScoreDO scoreDO = scoreDao.findByScoreCode(code);
        if (scoreDO == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到指定积分类型");
            return result;
        }
        result.setData(scoreDO);
        return result;
    }
    

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult creditConvertRed(int userId, Integer creditTypeId) {

        BaseResult result = new BaseResult();

        //验证用户
        UserDO userDO = userDao.findByIdWithLock(userId);
        if (userDO == null) {
            return result.setError(CommonResultCode.BIZ_ERROR, "用户不存在");
        }

        //获取红包兑换类型
        ScoreConfigDO scoreInfo = new ScoreConfigDO();
        try {
            scoreInfo = scoreConfigDao.findScoreKindsById(creditTypeId);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("积分兑换卷信息查询错误");
            return result;
        }

        //验证用户积分是否满足兑换条件
        if (scoreInfo.getScMaxScore().intValue() > userDO.getUserScore()) {
            result.setMessage("兑换失败：积分余额不足!");
        }

        //积分兑换
        result = sendInviteRed(userId, scoreInfo);

        //插入积分兑换记录
        ScoreUsageRecordDO scoreUsageRecordDO = new ScoreUsageRecordDO();
        scoreUsageRecordDO.setSurUserId(userDO.getUserId());
        scoreUsageRecordDO.setSurExchangeScore(scoreInfo.getScMaxScore().intValue());

        scoreUsageRecordDO.setSurExchangeCash(scoreInfo.getScScoreCash().longValue());
        scoreUsageRecordDO.setSurReviewState(0);//未审核
        int i = scoreHistoryService.addScoreConvertRecord(scoreUsageRecordDO);
        if (i <= 0) {
            result.setMessage("积分兑换记录更新错误！");
            return result;
        }

        //用户积分余额更新
        userDO.setUserScore(userDO.getUserScore() - scoreInfo.getScMaxScore().intValue());
        int val = userDao.updateByPrimaryKeySelective(userDO);
        if (val < 0) {
            result.setMessage("用户积分修改失败！");
            return result;
        }

        return result;
    }

    /**
     * @param userId
     * @return
     */
    private BaseResult sendInviteRed(int userId, ScoreConfigDO scoreInfo) {

        BaseResult baseResult = new BaseResult();

        // 对用户发放积分兑换红包
        List<Redsend> sendList = new ArrayList<Redsend>();
        DateTime fullDaytime = new DateTime();
        Redsend redsend = new Redsend();
        redsend.setRsTheme("积分兑换红包");
        redsend.setRsUserid(userId);
        redsend.setRsUseScope(scoreInfo.getUseScope().replace("|", ",").replace("1", LoanCategory.LOAN_PERSON.prompt)
                .replace("2", LoanCategory.LOAN_CAR.prompt).replace("3", LoanCategory.LOAN_HOUSE.prompt)
                .replace("4", LoanCategory.LOAN_CUST.prompt));
        redsend.setRsState(RsState.WITHOUT_USE);
        redsend.setRsClosetime(fullDaytime.plusDays(scoreInfo.getRedCloseTime()).toDate());
        redsend.setRsStarttime(new Date());
        //redsend.setRsSender(red.getRedCreator());
        redsend.setRsType(RedenvelopeType.SCORE_RED);
        redsend.setRsAmt(scoreInfo.getScScoreCash().doubleValue());
        redsend.setRsValidAmount(scoreInfo.getScScoreCash().doubleValue());
        sendList.add(redsend);

        if (CollectionUtils.isEmpty(sendList)) {
            baseResult.setMessage("积分兑换红包失败！");
            return baseResult;
        }

        baseResult = redService.batchSendRed(sendList);

        return baseResult;
    }
}
