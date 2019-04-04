/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.intf;

import java.util.List;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CashBorrowerDO;

/**
 * 借款人资金对账表Dao
 * 
 * @deprecated
 * @author J.YL 2014年11月15日 下午6:30:04
 */

@Deprecated
public interface CashBorrowerDao extends BaseDao<CashBorrowerDO, Integer> {

    public List<CashBorrowerDO> queryListByUserId(Integer userId, PageCondition pageCondition);

    public CashBorrowerDO findBySeqNo(String seqNo);

    public CashBorrowerDO findLeastByByUserId(Integer userId);
}
