package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.dataobject.PlatformReport;
/**
 * 平台运营总报表
 * @author zhangkang
 *
 */
public interface PlatformReportDao {
	/**
	 * 统计某个时间段内平台运营报表
	 * @param report 必传字段 beginDate,endDate
	 * @return
	 */
	public Integer report(PlatformReport report);
}
