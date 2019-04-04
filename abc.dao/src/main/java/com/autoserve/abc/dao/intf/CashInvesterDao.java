/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.intf;

import java.util.List;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CashInvesterDO;

/**
 *投资人资金对账表
 * @deprecated
 * @author J.YL 2014年11月15日 下午6:29:34
 */
@Deprecated
public interface CashInvesterDao extends BaseDao<CashInvesterDO, Integer> {

    public CashInvesterDO findBySeqNo(String seqNo);

    public List<CashInvesterDO> queryListByUserId(Integer userId, PageCondition pageCondition);

    public CashInvesterDO findByUserId(Integer userId);

    public List<CashInvesterDO> findByUserIds(List<Integer> userIds);
}
