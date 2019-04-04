package com.autoserve.abc.web.module.screen.review.json;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.service.biz.convert.EmployeeConverter;
import com.autoserve.abc.service.biz.entity.ReviewOp;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.review.ReviewOpLogService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.AuditOpinionVOConverter;
import com.autoserve.abc.web.util.VOUtil;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.review.AuditOpinionVO;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author yuqing.zheng
 *         Created on 2014-12-29,12:27
 */
public class AuditOpinionList {
    private static final Logger logger = LoggerFactory.getLogger(AuditOpinionList.class);

    @Autowired
    private ReviewOpLogService reviewOpLogService;

    @Autowired
    private EmployeeService employeeService;

    public JsonPageVO execute(@Param("reviewType") int reviewTypeIdx, @Param("applyId") int applyId,
                               @Param("page") int page, @Param("rows") int rows) {
        JsonPageVO<AuditOpinionVO> result = new JsonPageVO<AuditOpinionVO>();
        PageCondition pageCondition = new PageCondition(page, rows);

        ReviewType reviewType = ReviewType.valueOf(reviewTypeIdx);
        PageResult<ReviewOp> opRes = reviewOpLogService.queryReviewOpHistory(reviewType, applyId, pageCondition);
        if (!opRes.isSuccess()) {
            logger.warn("未找到相关审核操作历史");
            return VOUtil.emptyPageVO("未找到相关审核操作历史");
        }

        result.setTotal(opRes.getTotalCount());
        List<ReviewOp> opList = opRes.getData();

        // 为了在页面中展示出审核操作的操作人与下一操作的名字，
        // 这里需要根据所有员工ID查出他们的名字
        Set<Integer> allEmpIds = Sets.newHashSet();
        for (ReviewOp reviewOp : opList) {
            allEmpIds.add(reviewOp.getNextEmp().getEmpId());
            allEmpIds.add(reviewOp.getEmployee().getEmpId());
        }
        ListResult<EmployeeDO> empListRes = employeeService.findByList(Lists.newArrayList(allEmpIds));
        if (!empListRes.isSuccess() || CollectionUtils.isEmpty(empListRes.getData())) {
            logger.error("未找到相关审核操作人, empIds={}", allEmpIds.toString());
            return VOUtil.emptyPageVO("未找到相关审核操作历史");
        }

        Map<Integer, EmployeeDO> empIdMap = Maps.uniqueIndex(empListRes.getData(), new Function<EmployeeDO, Integer>() {
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

            EmployeeDO nextEmpDO = empIdMap.get(reviewOp.getNextEmp().getEmpId());
            if (nextEmpDO != null) {
                reviewOp.setNextEmp(EmployeeConverter.toEmployee(nextEmpDO));
            }
        }

        result.setRows(AuditOpinionVOConverter.toVoList(opList));

        return result;
    }
}
