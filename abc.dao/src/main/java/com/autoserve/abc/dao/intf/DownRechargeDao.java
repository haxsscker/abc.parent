package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.DownRechargeDO;
import com.autoserve.abc.dao.dataobject.DownRechargeJDO;
import com.autoserve.abc.dao.dataobject.search.DownRechargeSearchDO;

public interface DownRechargeDao extends BaseDao<DownRechargeDO, Integer> {
    /**
     * 根据线下充值DO进行查询所有线下充值审核数据
     */
    public List<DownRechargeJDO> queryByDownRechargeSelective(@Param("downRecharge") DownRechargeSearchDO downRechargeDO,
                                                              @Param("pageCondition") PageCondition pageCondition);

    /**
     * 根据线下充值DO进行统计数据条数
     */
    public int countByDownRechargeSelective(@Param("downRecharge") DownRechargeSearchDO downRechargeDO);

    /**
     * 选择性更新数据
     * 
     * @param downRechargeDO
     * @return
     */
    public int updateByPrimaryKeySelective(DownRechargeDO downRechargeDO);

    /**
     * 通过主键查询
     * 
     * @param downRechargeId
     * @return
     */
    public DownRechargeJDO findByPrimaryKey(int downRechargeId);
}
