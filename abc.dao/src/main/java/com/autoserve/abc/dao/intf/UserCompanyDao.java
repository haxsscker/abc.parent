package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.UserCompanyDO;

import java.util.List;

public interface UserCompanyDao extends BaseDao<UserCompanyDO, Integer> {

    /**
     * 查询用户单位信息
     *
     * @param id 用户id
     * @return UserCompanyDO
     */
    public UserCompanyDO findByUserId(int id);

    /**
     * 批量查询用户单位信息
     *
     * @param ids 用户id列表
     * @return List<UserCompanyDO>
     */
    public List<UserCompanyDO> findListByIds(List<Integer> ids);

    /**
     * 选择更新用户单位信息
     *
     * @param userCompanyDO
     * @return
     */
    public int updateByPrimaryKeySelective(UserCompanyDO userCompanyDO);
}
