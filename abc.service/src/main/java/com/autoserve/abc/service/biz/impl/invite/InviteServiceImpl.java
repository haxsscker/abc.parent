package com.autoserve.abc.service.biz.impl.invite;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.druid.util.Base64;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.InviteDO;
import com.autoserve.abc.dao.dataobject.InviteJDO;
import com.autoserve.abc.dao.dataobject.ScoreDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.InviteDao;
import com.autoserve.abc.service.biz.convert.InviteConverter;
import com.autoserve.abc.service.biz.entity.Invite;
import com.autoserve.abc.service.biz.entity.Red;
import com.autoserve.abc.service.biz.entity.Redsend;
import com.autoserve.abc.service.biz.enums.InviteUserType;
import com.autoserve.abc.service.biz.enums.RedState;
import com.autoserve.abc.service.biz.enums.RedenvelopeType;
import com.autoserve.abc.service.biz.enums.RsState;
import com.autoserve.abc.service.biz.enums.ScoreType;
import com.autoserve.abc.service.biz.intf.invite.InviteService;
import com.autoserve.abc.service.biz.intf.redenvelope.RedService;
import com.autoserve.abc.service.biz.intf.score.ScoreService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.util.Encryption;

/**
 * @author RJQ 2015/1/4 18:12.
 */
public class InviteServiceImpl implements InviteService {
    private static final Logger log = LoggerFactory.getLogger(InviteServiceImpl.class);

    @Resource
    private InviteDao           inviteDao;

    private String              registerUrl;

    @Resource
    private UserService         userService;

    @Resource
    private RedService          redService;
    
    @Resource
    private ScoreService        scoreService;
    
    public static void main(String[] args) {
        String str = "1,3";
        System.out.println(Base64.byteArrayToAltBase64(str.getBytes()));
    }

    @Override
    public PlainResult<String> generateInviteUrl(Integer userId, InviteUserType userType) {
        PlainResult<String> result = new PlainResult<String>();
        if (userId == null || userType == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "参数不正确");
            return result;
        }
        String invitationId = userId + "," + userType.getType();
        //        result.setData(registerUrl + Base64.byteArrayToAltBase64(invitationId.getBytes()));
        try {
            result.setData(registerUrl + URLEncoder.encode(Encryption.encrypt(invitationId), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult createInvitation(Invite invite) {
        BaseResult result = new BaseResult();
        InviteDO inviteDO = InviteConverter.toInviteDO(invite);
        int val = inviteDao.insert(inviteDO); //返回主键
        if (val <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "新增失败！");
            return result;
        }

        if (InviteUserType.PERSONAL.equals(invite.getInviteUserType())) {//邀请人是前台用户，需加上相应积分
            if (!userService.modifyUserScore(invite.getInviteUserId(), ScoreType.RECOMMEND_SCORE, null).isSuccess()) {
                throw new BusinessException("添加积分失败");
            }
            //更新abc_invite表
            PlainResult<ScoreDO> scoreResult = scoreService.findByScoreCode(ScoreType.RECOMMEND_SCORE.getCode());
            if (!scoreResult.isSuccess()) {
                throw new BusinessException("未找到指定积分类型！");
            }
            InviteDO inviteDOx = new InviteDO();
            inviteDOx.setInviteId(inviteDO.getInviteId());
            inviteDOx.setInviteRewardScore(scoreResult.getData().getScoreValue());
            int valx = inviteDao.updateByPrimaryKeySelective(inviteDOx);
            if (valx <= 0) {
                result.setError(CommonResultCode.BIZ_ERROR, "更新推荐表失败！");
                return result;
            }
            //邀请人发送红包
            UserDO userDO = new UserDO();
            userDO.setUserId(invite.getInviteUserId());
            sendInviteRed(userDO, inviteDO.getInviteId());
        }
        return result;
    }

    /**
     * 推荐成功后发送红包
     */
    private BaseResult sendInviteRed(UserDO userDO, int investId) {
        BaseResult baseResult = new BaseResult();

        Red redParam = new Red();
        redParam.setRedType(RedenvelopeType.INVIT_RED);

        // 查询推荐红包
        ListResult<Red> redResult = redService.queryList(redParam, null);
        if (!redResult.isSuccess()) {
            baseResult.setError(CommonResultCode.BIZ_ERROR, redResult.getMessage());
            return baseResult;
        }

        // 不存在推荐红包直接返回
        List<Red> redList = redResult.getData();
        if (CollectionUtils.isEmpty(redList)) {
            return baseResult;
        }

        // 对用户发放推荐红包
        List<Redsend> sendList = new ArrayList<Redsend>();
        //推荐红包的总金额
        BigDecimal RedAmounts = new BigDecimal(0);
        DateTime fullDaytime = new DateTime();
        
        for (Red red : redList) {
            if (RedState.EFFECTIVE.equals(red.getRedState())) {
                Redsend redsend = new Redsend();
                redsend.setRsTheme(red.getRedTheme());
                redsend.setRsUserid(userDO.getUserId());
                redsend.setRsRedId(red.getRedId());
                redsend.setRsUseScope(red.getRedUseScope());
                redsend.setRsState(RsState.WITHOUT_USE);
                redsend.setRsClosetime(fullDaytime.plusDays(red.getRedClosetime()).toDate());
                redsend.setRsStarttime(new Date());
                redsend.setRsSender(red.getRedCreator());
                redsend.setRsType(RedenvelopeType.INVIT_RED);
                redsend.setRsAmt(red.getRedAmt());
                redsend.setRsValidAmount(red.getRedAmount());
                sendList.add(redsend);
                RedAmounts = RedAmounts.add(new BigDecimal(red.getRedAmount()));
            }
        }

        if (CollectionUtils.isEmpty(sendList)) {
            return baseResult;
        }

        baseResult = redService.batchSendRed(sendList);
        //更新abc_invite表
        InviteDO inviteDO = new InviteDO();
        inviteDO.setInviteId(investId);
        inviteDO.setInviteRewardMoney(RedAmounts);
        int val = inviteDao.updateByPrimaryKeySelective(inviteDO);
        if (val <= 0) {
            baseResult.setError(CommonResultCode.BIZ_ERROR, "更新推荐表失败！");
            return baseResult;
        }
        
        return baseResult;
    }

    @Override
    public BaseResult removeInvitationById(int id) {
        BaseResult result = new BaseResult();
        int val = inviteDao.delete(id);
        if (val <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "删除失败！");
        }
        return result;
    }

    @Override
    public BaseResult modifySelective(Invite invite) {
        BaseResult result = new BaseResult();
        int val = inviteDao.updateByPrimaryKeySelective(InviteConverter.toInviteDO(invite));
        if (val <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "更新失败！");
        }
        return result;
    }

    @Override
    public PageResult<InviteJDO> queryList(InviteJDO inviteJDO, PageCondition pageCondition) {
        PageResult<InviteJDO> result = new PageResult<InviteJDO>(pageCondition);
        int totalCount = inviteDao.countListByParam(inviteJDO);
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            result.setData(inviteDao.findListByParam(inviteJDO, pageCondition));
        }

        return result;
    }

    public void setRegisterUrl(String registerUrl) {
        this.registerUrl = registerUrl;
    }
}
