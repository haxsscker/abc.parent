package com.autoserve.abc.web.module.screen.employee.json;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.OperateLogDO;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.sys.OperateLogService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.IPUtil;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author RJQ 2014/12/3 20:40.
 */
public class RemoveEmployeeItem {
    @Resource
    private EmployeeService employeeService;

    @Resource
    private OperateLogService operateLogService;

    @Resource
    private HttpServletRequest request;

    public JsonBaseVO execute(@Param("empId") int empId) {
        BaseResult result = employeeService.removeEmployee(empId);

        OperateLogDO operateLogDO = new OperateLogDO();
        operateLogDO.setOlEmpId(LoginUserUtil.getEmpId());//操作人ID
        operateLogDO.setOlIp(IPUtil.getUserIpAddr(request));//操作人IP地址
        operateLogDO.setOlModule("管理员管理");//操作模块
        operateLogDO.setOlOperateType("删除");//操作类型：添加/修改/删除
        operateLogDO.setOlContent("删除了ID为" + empId + "的管理员");//具体操作内容
        operateLogService.createOperateLog(operateLogDO);

        return ResultMapper.toBaseVO(result);
    }
}
