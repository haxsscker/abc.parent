/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CashQuotaApplyDO;

/**
 * 免费提现额度申请dao
 * 
 * @author zhangkang 2015年6月17日 下午1:01:15
 */
public interface CashQuotaApplyDao extends BaseDao<CashQuotaApplyDO, Integer> {

    /**
     * 分页查询
     * 
     * @param c
     * @param page
     * @return
     */
    List<CashQuotaApplyDO> findList(@Param("apply") CashQuotaApplyDO c, @Param("page") PageCondition page);
}
