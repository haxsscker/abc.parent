package com.autoserve.abc.service.biz.intf.banel;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.MonthReportDO;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

public interface MonthRptService {
	
	PageResult<MonthReportDO> queryListByPage(PageCondition pageCondition);
	
	BaseResult removeRpt(Integer id);
	
	BaseResult createRpt(MonthReportDO monthReportDO);
	
	PlainResult<MonthReportDO> queryById(int id);

	BaseResult modifyRpt(MonthReportDO monthReportDO);
	
	ListResult<MonthReportDO> queryListByYear(String year);
	
	/**
     * 根据条件查询
     * @param monthReportDO
     * @param pageCondition
     * @return
     */
    PageResult<MonthReportDO> queryListBySearchParam( MonthReportDO monthReportDO,
             PageCondition pageCondition);
	
}
