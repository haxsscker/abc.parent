package com.autoserve.abc.service.biz.intf.banel;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.BanelDO;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

public interface BanelService {
	
	PageResult<BanelDO> queryListByPage(PageCondition pageCondition);
	
	BaseResult removeBanel(Integer id);
	
	BaseResult createBanel(BanelDO banel);
	
	PlainResult<BanelDO> queryById(int id);

	BaseResult modifyBanel(BanelDO banel);
	
	ListResult<BanelDO> queryListByGroup(String groupCode);
}
