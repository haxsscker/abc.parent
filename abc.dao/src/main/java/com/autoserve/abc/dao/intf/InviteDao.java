package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.InviteDO;
import com.autoserve.abc.dao.dataobject.InviteJDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InviteDao extends BaseDao<InviteDO, Integer> {

    public int updateByPrimaryKeySelective(InviteDO inviteDO);

    /**
     * 根据参数获取记录条数
     *
     * @param inviteJDO
     * @return
     */
    public int countListByParam(@Param("invite") InviteJDO inviteJDO);

    /**
     * 按条件查询分页结果
     *
     * @param inviteJDO     查询条件，为空的话传new InviteJDO()
     * @param pageCondition 分页和排序条件，可选
     * @return List<InviteJDO>
     */
    List<InviteJDO> findListByParam(@Param("invite") InviteJDO inviteJDO,
                                    @Param("pageCondition") PageCondition pageCondition);

}