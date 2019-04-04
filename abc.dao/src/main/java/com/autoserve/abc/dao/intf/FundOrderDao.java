/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Tue Dec 02 13:24:46 CST 2014
 * Description:
 */
package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.FundApplyDO;
import com.autoserve.abc.dao.dataobject.FundOrderApplyUserJDO;
import com.autoserve.abc.dao.dataobject.FundOrderDO;

/**
 * 类FundOrderDao.java的实现描述：TODO 类实现描述
 * 
 * @author dongxuejiao 2014年12月23日 下午4:35:32
 */
public interface FundOrderDao extends BaseDao<FundOrderDO, Integer> {
    /**
     * 查询所有数据
     * 
     * @param fundOrderDO
     * @param pageCondition
     * @return
     */

    List<FundOrderDO> findListByParam(@Param("fa") FundOrderDO fundOrderDO,
                                      @Param("pageCondition") PageCondition pageCondition);

    /**
     * 查询所有数据（FundOrderApplyUserJDO）包含分页
     * 
     * @param fundOrderDO
     * @param pageCondition
     * @return
     */

    List<FundOrderApplyUserJDO> findListByJParam(@Param("fa") FundOrderApplyUserJDO fundOrderApplyUserJDO,
                                                 @Param("pageCondition") PageCondition pageCondition);

    /**
     * 查询所有数据（FundOrderApplyUserJDO）
     * 
     * @param fundOrderDO
     * @param pageCondition
     * @return
     */

    List<FundOrderApplyUserJDO> findListByJParam(FundOrderApplyUserJDO fundOrderApplyUserJDO);

    /**
     * 计算查询总条数
     * 
     * @param fundOrderDO
     * @param pageCondition
     * @return
     */
    public int countListByParam(@Param("fa") FundApplyDO fundApplyDO);

    public int countListByJParam(FundOrderApplyUserJDO fundOrderApplyUserJDO);

    public FundOrderApplyUserJDO findOrderById(@Param("id") Integer id);

    /**
     * @param fundOrderApplyUserJDO
     * @return
     */
    public int updateByOrderd(FundOrderApplyUserJDO fundOrderApplyUserJDO);

}
