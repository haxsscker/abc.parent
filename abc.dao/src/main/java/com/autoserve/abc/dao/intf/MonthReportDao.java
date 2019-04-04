package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.MonthReportDO;

public interface MonthReportDao extends BaseDao<MonthReportDO, Integer> {
	
	List<MonthReportDO> findListByPage(PageCondition pageCondition);
	
	int countList();
	
	List<MonthReportDO> findListByYear( @Param("year") String year);
	
    List<MonthReportDO> findListBySearchParam(@Param("monthReportDO") MonthReportDO monthReportDO,
            @Param("pageCondition") PageCondition pageCondition);
    
    int countListBySearchParam(@Param("monthReportDO") MonthReportDO monthReportDO);
}
