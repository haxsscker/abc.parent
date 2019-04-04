package com.autoserve.abc.web.module.screen.review;

import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.LoginUserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 信贷审核页面
 * 需要注入当前登录的小贷机构的govId
 *
 * @author yuqing.zheng
 *         Created on 2015-01-06,21:25
 */
public class LoanGovListCheckView {
    private static final Logger logger = LoggerFactory.getLogger(LoanGovListCheckView.class);

    @Autowired
    private EmployeeService employeeService;

    public void execute(Context context) {
        Integer empId = LoginUserUtil.getEmpId();
        PlainResult<EmployeeDO> empRes = employeeService.findById(empId);
        if (!empRes.isSuccess()) {
            logger.error("未找到相关小贷机构的govId, empId={}", empId);
            return;
        }

        Integer govId = empRes.getData().getEmpOrgId();
        context.put("govId", govId);
    }
}
