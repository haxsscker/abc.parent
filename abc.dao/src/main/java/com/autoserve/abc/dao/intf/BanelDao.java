package com.autoserve.abc.dao.intf;

import java.util.List;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.BanelDO;

public interface BanelDao extends BaseDao<BanelDO, Integer> {
	
	List<BanelDO> findListByPage(PageCondition pageCondition);
	
	int countList();
	
	List<BanelDO> findListByGroupCode(String groupCode);
}
