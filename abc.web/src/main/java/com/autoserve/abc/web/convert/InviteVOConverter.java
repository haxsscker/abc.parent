package com.autoserve.abc.web.convert;

import com.autoserve.abc.dao.dataobject.InviteJDO;
import com.autoserve.abc.service.biz.enums.*;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.invite.InviteVO;
import net.sf.cglib.beans.BeanCopier;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RJQ 2015/1/5 15:19.
 */
public class InviteVOConverter {
    public static InviteVO convertToVO(InviteJDO inviteJDO) {
        InviteVO vo = new InviteVO();
        BeanCopier beanCopier = BeanCopier.create(InviteJDO.class, InviteVO.class, false);
        beanCopier.copy(inviteJDO, vo, null);
        vo.setCst_user_name(inviteJDO.getInviteeName());
        vo.setCst_add_date(inviteJDO.getInviteCreateTime() == null ? "" : new DateTime(inviteJDO.getInviteCreateTime()).toString(DateUtil.DATE_FORMAT));
        vo.setCst_realname_prove(inviteJDO.getUserRealnameIsproven() == null ? "" : RealnameState.valueOf(inviteJDO.getUserRealnameIsproven()).getDes());
        vo.setCst_binding_mobile(inviteJDO.getUserMobileIsbinded() == null ? "" : BindingState.valueOf(inviteJDO.getUserMobileIsbinded()).getDes());
        vo.setCst_binding_email(inviteJDO.getUserEmailIsbinded() == null ? "" : BindingState.valueOf(inviteJDO.getUserEmailIsbinded()).getDes());
        vo.setCst_binding_bankcard(inviteJDO.getUserBankcardIsbinded() == null ? "" : BindingState.valueOf(inviteJDO.getUserBankcardIsbinded()).getDes());
        vo.setOpen_account_state(inviteJDO.getUserBusinessState() == null ? null : inviteJDO.getUserBusinessState());
        vo.setRecharge_state(inviteJDO.getUserBusinessState() == null ? null : inviteJDO.getUserBusinessState());
        vo.setInvest_state(inviteJDO.getUserBusinessState() == null ? null : inviteJDO.getUserBusinessState());
        vo.setInviteUserType(inviteJDO.getInviteUserType() == null ? null : InviteUserType.valueOf(inviteJDO.getInviteUserType()).getDes());
        vo.setInviteIsValid(inviteJDO.getInviteIsValid() == null ? null : ValidState.valueOf(inviteJDO.getInviteIsValid()).getDes());
        vo.setInviteStartDate(inviteJDO.getInviteStartDate() == null ? "" : new DateTime(inviteJDO.getInviteStartDate()).toString(DateUtil.DATE_FORMAT));
        vo.setInviteEndDate(inviteJDO.getInviteEndDate() == null ? "" : new DateTime(inviteJDO.getInviteEndDate()).toString(DateUtil.DATE_FORMAT));
        vo.setInviteRewardState(inviteJDO.getInviteRewardState() == null ? null : RewardState.valueOf(inviteJDO.getInviteRewardState()).getDes());
        vo.setInviteCreateTime(inviteJDO.getInviteCreateTime() == null ? "" : new DateTime(inviteJDO.getInviteCreateTime()).toString(DateUtil.DATE_FORMAT));
        vo.setInviteLastModifyTime(inviteJDO.getInviteLastModifyTime() == null ? "" : new DateTime(inviteJDO.getInviteLastModifyTime()).toString(DateUtil.DATE_FORMAT));

        return vo;
    }

    public static List<InviteVO> convertToVOList(List<InviteJDO> inviteJDOs) {
        List<InviteVO> result = new ArrayList<InviteVO>();
        if (inviteJDOs == null || inviteJDOs.isEmpty()) {
            return result;
        }
        for (InviteJDO inviteJDO : inviteJDOs) {
            result.add(convertToVO(inviteJDO));
        }
        return result;
    }

}
