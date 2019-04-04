package com.autoserve.abc.web.module.screen.employee;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.EmployeeConverter;
import com.autoserve.abc.web.vo.employee.EmployeeVO;

import javax.annotation.Resource;

/**
 * @author RJQ 2014/12/4 19:59.
 */
public class EmployeeAddOrEditView {
    @Resource
    private EmployeeService employeeService;

    public void execute(Context context, @Param("empId") int empId, @Param("flag") String flag) {
        if (null == flag) {//添加页面
            return;
        }

        //查询用户信息
        PlainResult<EmployeeDO> empResult = employeeService.findById(empId);
        EmployeeVO employeeVO = EmployeeConverter.convert(empResult.getData());
        context.put("emp", employeeVO);
    }
}
