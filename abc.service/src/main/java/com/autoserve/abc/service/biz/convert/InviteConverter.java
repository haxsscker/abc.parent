package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.InviteDO;
import com.autoserve.abc.service.biz.entity.Invite;
import com.autoserve.abc.service.biz.enums.InviteUserType;
import com.autoserve.abc.service.biz.enums.RewardState;
import com.autoserve.abc.service.biz.enums.ValidState;
import com.autoserve.abc.service.exception.BusinessException;
import net.sf.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RJQ 2015/1/5 11:02.
 */
public class InviteConverter {
    public static Invite toInvite(InviteDO inviteDO) {
        Invite invite = new Invite();
        BeanCopier beanCopier = BeanCopier.create(InviteDO.class, Invite.class, false);
        beanCopier.copy(inviteDO, invite, null);
        invite.setInviteIsValid(ValidState.valueOf(inviteDO.getInviteIsValid()));
        invite.setInviteRewardState(RewardState.valueOf(inviteDO.getInviteRewardState()));
        invite.setInviteUserType(InviteUserType.valueOf(inviteDO.getInviteUserType()));

        return invite;
    }

    public static InviteDO toInviteDO(Invite invite) {
        InviteDO inviteDO = new InviteDO();
        BeanCopier beanCopier = BeanCopier.create(Invite.class, InviteDO.class, false);
        beanCopier.copy(invite, inviteDO, null);
        inviteDO.setInviteIsValid(invite.getInviteIsValid().getState());
        inviteDO.setInviteRewardState(invite.getInviteRewardState().getState());
        inviteDO.setInviteUserType(invite.getInviteUserType().getType());

        return inviteDO;
    }

    public static List<Invite> convertToEntityList(List<InviteDO> list) {
        if (list == null || list.isEmpty())
            throw new BusinessException("传入的list为空");
        List<Invite> result = new ArrayList<Invite>();
        for (InviteDO inviteDO : list) {
            result.add(toInvite(inviteDO));
        }
        return result;
    }
}
