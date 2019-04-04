package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.SysLinkInfoDO;

public interface SysLinkInfoDao extends BaseDao<SysLinkInfoDO, Integer> {

    /**
     * 按条件查询分页结果
     *
     * @param articleInfo 查询条件，可选
     * @param pageCondition 分页和排序条件，可选
     * @return List<SysLinkInfoDO>
     */
    List<SysLinkInfoDO> findListByParam(@Param("sysLinkInfo") SysLinkInfoDO sysLinkInfo,
            @Param("pageCondition") PageCondition pageCondition);

}
