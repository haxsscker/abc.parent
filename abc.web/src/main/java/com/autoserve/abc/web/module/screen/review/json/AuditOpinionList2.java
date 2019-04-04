package com.autoserve.abc.web.module.screen.review.json;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.service.biz.convert.EmployeeConverter;
import com.autoserve.abc.service.biz.entity.ReviewOp;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.review.ReviewOpLogService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.web.convert.AuditOpinionVOConverter;
import com.autoserve.abc.web.util.VOUtil;
import com.autoserve.abc.web.vo.JsonListVO;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.review.AuditOpinionVO;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class AuditOpinionList2 {
	private static final Logger logger = LoggerFactory
			.getLogger(AuditOpinionList2.class);

	@Autowired
	private ReviewOpLogService reviewOpLogService;

	@Autowired
	private EmployeeService employeeService;

	public JsonListVO<AuditOpinionVO> execute(@Param("applyId") int applyId) {
		JsonPageVO<AuditOpinionVO> result = new JsonPageVO<AuditOpinionVO>();

		ListResult<ReviewOp> opRes = reviewOpLogService.queryReviewOpHistory(
				 applyId);
		List<ReviewOp> opList = opRes.getData();

		// 为了在页面中展示出审核操作的操作人与下一操作的名字，
		// 这里需要根据所有员工ID查出他们的名字
		Set<Integer> allEmpIds = Sets.newHashSet();
		for (ReviewOp reviewOp : opList) {
			allEmpIds.add(reviewOp.getNextEmp().getEmpId());
			allEmpIds.add(reviewOp.getEmployee().getEmpId());
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

		for (ReviewOp reviewOp : opList) {
			EmployeeDO empDO = empIdMap.get(reviewOp.getEmployee().getEmpId());
			if (empDO != null) {
				reviewOp.setEmployee(EmployeeConverter.toEmployee(empDO));
			}

			EmployeeDO nextEmpDO = empIdMap.get(reviewOp.getNextEmp()
					.getEmpId());
			if (nextEmpDO != null) {
				reviewOp.setNextEmp(EmployeeConverter.toEmployee(nextEmpDO));
			}
		}

		result.setRows(AuditOpinionVOConverter.toVoList(opList));
		result.setTotal(opList.size());
		return result;
	}
}
