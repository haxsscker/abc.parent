package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.service.biz.entity.Employee;
import com.autoserve.abc.service.biz.enums.UserAuthorizeFlag;
import com.autoserve.abc.service.exception.BusinessException;

import net.sf.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RJQ 2014/12/3 11:24.
 */
public class EmployeeConverter {
    public static Employee toEmployee(EmployeeDO employeeDO) {
        Employee emp = new Employee();
        emp.setEmpNo(employeeDO.getEmpNo());
        emp.setEmpName(employeeDO.getEmpName());
        emp.setEmpPhoto(employeeDO.getEmpPhoto());
        emp.setEmpLoginCount(employeeDO.getEmpLoginCount());
        emp.setEmpHeadImg(employeeDO.getEmpHeadImg());
        emp.setEmpLastlogintime(employeeDO.getEmpLastlogintime());
        emp.setEmpEmail(employeeDO.getEmpEmail());
        emp.setEmpMobile(employeeDO.getEmpMobile());
        emp.setEmpId(employeeDO.getEmpId());
        emp.setEmpRealName(employeeDO.getEmpRealName());
        emp.setEmpState(employeeDO.getEmpState());

        emp.setUserAuthorizeFlag(UserAuthorizeFlag.valueOf(employeeDO.getUserAuthorizeFlag()));
        emp.setAuthorizeSeqNo(employeeDO.getAuthorizeSeqNo());
        emp.setAuthorizeRepayType(employeeDO.getAuthorizeRepayType());
        emp.setAuthorizeRepayStartDate(employeeDO.getAuthorizeRepayStartDate());
        emp.setAuthorizeRepayEndDate(employeeDO.getAuthorizeRepayEndDate());
        emp.setAuthorizeRepayAmount(employeeDO.getAuthorizeRepayAmount());
        emp.setAuthorizeFeeType(employeeDO.getAuthorizeFeeType());
        emp.setAuthorizeFeeStartDate(employeeDO.getAuthorizeFeeStartDate());
        emp.setAuthorizeFeeEndDate(employeeDO.getAuthorizeFeeEndDate());
        emp.setAuthorizeFeeAmount(employeeDO.getAuthorizeFeeAmount());
        return emp;
    }

    public static Employee toEmployeeUsingBeanCopy(EmployeeDO employeeDO){
        Employee entity = new Employee();
        BeanCopier beanCopier = BeanCopier.create(EmployeeDO.class, Employee.class, false);
        beanCopier.copy(employeeDO, entity, null);
        return entity;
    }

    public static EmployeeDO toEmployeeDO(Employee employee) {
        EmployeeDO empDO = new EmployeeDO();
        empDO.setEmpNo(employee.getEmpNo());
        empDO.setEmpName(employee.getEmpName());
        empDO.setEmpPhoto(employee.getEmpPhoto());
        empDO.setEmpLoginCount(employee.getEmpLoginCount());
        empDO.setEmpHeadImg(employee.getEmpHeadImg());
        empDO.setEmpLastlogintime(employee.getEmpLastlogintime());
        empDO.setEmpEmail(employee.getEmpEmail());
        empDO.setEmpMobile(employee.getEmpMobile());
        empDO.setEmpId(employee.getEmpId());
        empDO.setEmpRealName(employee.getEmpRealName());
        empDO.setEmpState(employee.getEmpState());

        empDO.setUserAuthorizeFlag(employee.getUserAuthorizeFlag().getState());
        empDO.setAuthorizeSeqNo(employee.getAuthorizeSeqNo());
        empDO.setAuthorizeRepayType(employee.getAuthorizeRepayType());
        empDO.setAuthorizeRepayStartDate(employee.getAuthorizeRepayStartDate());
        empDO.setAuthorizeRepayEndDate(employee.getAuthorizeRepayEndDate());
        empDO.setAuthorizeRepayAmount(employee.getAuthorizeRepayAmount());
        empDO.setAuthorizeFeeType(employee.getAuthorizeFeeType());
        empDO.setAuthorizeFeeStartDate(employee.getAuthorizeFeeStartDate());
        empDO.setAuthorizeFeeEndDate(employee.getAuthorizeFeeEndDate());
        empDO.setAuthorizeFeeAmount(employee.getAuthorizeFeeAmount());
        return empDO;
    }

    public static List<Employee> convertList(List<EmployeeDO> list) {
        if (list == null || list.isEmpty())
            throw new BusinessException("传入的list为空");
        List<Employee> result = new ArrayList<Employee>();
        for (EmployeeDO edo : list) {
            result.add(toEmployee(edo));
        }
        return result;
    }
}
