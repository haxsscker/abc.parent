package com.autoserve.abc.web.module.screen.review.json;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.dao.dataobject.TraceRecordDO;
import com.autoserve.abc.dao.intf.TraceRecordDao;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.web.util.VOUtil;
import com.autoserve.abc.web.vo.JsonListVO;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class ProjectTraceRecord {
	private static final Logger logger = LoggerFactory
			.getLogger(ProjectTraceRecord.class);

	@Autowired
	private EmployeeService employeeService;
	
	@Resource
	private TraceRecordDao traceRecordDao;

	public JsonListVO<TraceRecordDO> execute(@Param("applyId") int applyId ,@Param("page") int page, @Param("rows") int rows) {
		JsonPageVO<TraceRecordDO> result = new JsonPageVO<TraceRecordDO>();
		
		PageCondition pageCondition = new PageCondition(page,rows);
		List<TraceRecordDO> traceRecordList = traceRecordDao.findRecordListByLoanId(applyId,new PageCondition());
		
		// 为了在页面中展示出审核操作的操作人与下一操作的名字，
		// 这里需要根据所有员工ID查出他们的名字
		Set<Integer> allEmpIds = Sets.newHashSet();
		for (TraceRecordDO recordDO : traceRecordList) {
			//状态
			recordDO.setTrBidOldStateStr(recordDO.getTrBidOldState() == null?"":LoanState.valueOf(recordDO.getTrBidOldState()).prompt);
			recordDO.setTrBidNewStateStr(recordDO.getTrBidOldState() == null?"":LoanState.valueOf(recordDO.getTrBidNewState()).prompt);
			//处理时间
			recordDO.setTrCreatetimeStr(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(recordDO.getTrCreatetime()));
			
			allEmpIds.add(recordDO.getTrCreator());
		}
		ListResult<EmployeeDO> empListRes = employeeService.findByList(Lists
				.newArrayList(allEmpIds));
		if (!empListRes.isSuccess()
				|| CollectionUtils.isEmpty(empListRes.getData())) {
			logger.error("未找到相关审核操作人, empIds={}", allEmpIds.toString());
			return VOUtil.emptyPageVO("未找到相关审核操作历史");
		}
		Map<Integer, EmployeeDO> empIdMap = Maps.uniqueIndex(
				empListRes.getData(), new Function<EmployeeDO, Integer>() {
					@Override
					public Integer apply(EmployeeDO emp) {
						return emp.getEmpId();
					}
				});

		for (TraceRecordDO recordDO : traceRecordList) {
			EmployeeDO empDO = empIdMap.get(recordDO.getTrCreator());
			if (empDO != null) {
				recordDO.setTrCreatorStr(empDO.getEmpRealName());
			}else{
				recordDO.setTrCreatorStr("/");
			}
		}
		result.setRows(traceRecordList);
		result.setTotal(traceRecordList.size());
		return result;
	}
}
