/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.intf;

import java.util.List;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.SessionRecordDO;

/**
 * 银行编码Dao
 * 
 * @author LZ
 */
public interface SessionRecordDao extends BaseDao<SessionRecordDO, Integer> {

    public List<SessionRecordDO> queryAllSessionInfo();    
    
    public SessionRecordDO findSessionByUserName(String name);
 
    public void deleteByUserName(String name);

	public SessionRecordDO findSessionBySessionId(String id);
}
