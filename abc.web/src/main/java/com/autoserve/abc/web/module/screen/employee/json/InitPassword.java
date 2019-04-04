package com.autoserve.abc.web.module.screen.employee.json;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.OperateLogDO;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.sys.OperateLogService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.IPUtil;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author RJQ 2014/12/4 10:06.
 */
public class InitPassword {
    @Resource
    private EmployeeService employeeService;

    @Autowired
    private HttpServletRequest request;

    @Resource
    private OperateLogService operateLogService;

    private static Logger logger = LoggerFactory.getLogger(InitPassword.class);

    public JsonBaseVO execute(@Param("empId") int empId) {
        logger.info("初始化用户密码 empId={}, updateEmpId={}", empId, LoginUserUtil.getEmpId());

        OperateLogDO operateLogDO = new OperateLogDO();
        operateLogDO.setOlEmpId(LoginUserUtil.getEmpId());//操作人ID
        operateLogDO.setOlIp(IPUtil.getUserIpAddr(request));//操作人IP地址
        operateLogDO.setOlModule("管理员管理");//操作模块
        operateLogDO.setOlOperateType("修改");//操作类型：添加/修改/删除
        operateLogDO.setOlContent("初始化ID为" + empId + "的管理员密码");//具体操作内容
        operateLogService.createOperateLog(operateLogDO);

        PlainResult<String> result = employeeService.initPwdByEmpId(empId);
        return ResultMapper.toPlainVO(result);
    }
}
