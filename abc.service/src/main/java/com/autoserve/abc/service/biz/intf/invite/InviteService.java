package com.autoserve.abc.service.biz.intf.invite;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.InviteJDO;
import com.autoserve.abc.service.biz.entity.Invite;
import com.autoserve.abc.service.biz.enums.InviteUserType;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 邀请好友业务接口
 *
 * @author RJQ 2015/1/4 18:11.
 */
public interface InviteService {
    /**
     * 生成邀请好友链接(invitationId组成形式是userId和userType组成的字符串，且以逗号分隔，例如"27,2")
     * 解码方式：
     * String invitationId = new String(Base64.altBase64ToByteArray(invitationIdStr), "UTF-8");
     *
     * @param userId   用户id
     * @param userType 用户类型（前台用户或平台用户）
     * @return
     */
    public PlainResult<String> generateInviteUrl(Integer userId, InviteUserType userType);

    /**
     * 新增邀请
     *
     * @param invite
     * @return
     */
    public BaseResult createInvitation(Invite invite);

    /**
     * 删除邀请
     *
     * @param id
     * @return
     */
    public BaseResult removeInvitationById(int id);

    /**
     * 有选择的更新
     *
     * @param invite 需要更新的内容，主键必须包含
     * @return
     */
    public BaseResult modifySelective(Invite invite);

    /**
     * 查询邀请列表
     *
     * @param inviteJDO     查询条件，可选
     * @param pageCondition 分页条件
     * @return PageResult<InviteJDO>
     */
    public PageResult<InviteJDO> queryList(InviteJDO inviteJDO, PageCondition pageCondition);
}
