package com.autoserve.abc.service.biz.impl.employee;

import com.autoserve.abc.dao.dataobject.EmployeeRoleDO;
import com.autoserve.abc.dao.dataobject.RoleDO;
import com.autoserve.abc.dao.intf.EmployeeRoleDao;
import com.autoserve.abc.dao.intf.RoleDao;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.intf.employee.EmployeeRoleService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class EmployeeRoleServiceImpl implements EmployeeRoleService {

    @Resource
    private EmployeeRoleDao employeeRoleDao;

    @Resource
    private RoleDao roleDao;

    @Override
    public ListResult<RoleDO> queryAllRole() {
        List<RoleDO> list = roleDao.findAll();
        ListResult<RoleDO> result = new ListResult<RoleDO>();
        if (list == null || list.isEmpty()) {
            result.setData(new ArrayList<RoleDO>());
        } else {
            result.setData(list);
        }
        return result;
    }

    @Override
    public ListResult<RoleDO> queryRoleBySingleEmp(Integer empId) {
        List<EmployeeRoleDO> list = employeeRoleDao.findByEmpId(empId);
        ListResult<RoleDO> result = new ListResult<RoleDO>();
        if (list == null || list.isEmpty()) {
            result.setData(new ArrayList<RoleDO>());
        } else {
            List<RoleDO> rList = new ArrayList<RoleDO>();
            for (EmployeeRoleDO edo : list) {
                rList.add(roleDao.findById(edo.getRoleId()));
            }
            result.setData(rList);
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PlainResult<Map<Integer, List<RoleDO>>> queryRoleByEmps(List<Integer> emps) {
        PlainResult<Map<Integer, List<RoleDO>>> result = new PlainResult<Map<Integer, List<RoleDO>>>();
        if (emps == null || emps.isEmpty()) {
            result.setData(new HashMap<Integer, List<RoleDO>>());
        } else {
            Map<Integer, List<RoleDO>> map = new HashMap<Integer, List<RoleDO>>();
            for (Integer empId : emps) {
                List<EmployeeRoleDO> list = employeeRoleDao.findByEmpId(empId);
                if (list == null || list.isEmpty()) {
                    map.put(empId, new ArrayList<RoleDO>());
                } else {
                    List<RoleDO> roles = new ArrayList<RoleDO>();
                    for (EmployeeRoleDO edo : list) {
                        roles.add(roleDao.findById(edo.getRoleId()));
                    }
                    map.put(empId, roles);
                }
            }
            result.setData(map);
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult allocatRoleForEmps(Integer empId, List<Integer> roles) {
        BaseResult result = new BaseResult();
        for (Integer roleId : roles) {
            EmployeeRoleDO erdo = new EmployeeRoleDO();
            erdo.setRoleId(roleId);
            erdo.setEmpId(empId);
            employeeRoleDao.insert(erdo);
        }
        return result;
    }

    @Override
    public BaseResult allocatRoleForEmp(Integer empId, BaseRoleType roleType) {
        BaseResult result = new BaseResult();
        RoleDO roleDO = roleDao.findByRoleName(roleType.roleName);
        if(roleDO != null){
            EmployeeRoleDO erdo = new EmployeeRoleDO();
            erdo.setEmpId(empId);
            erdo.setRoleId(roleDO.getRoleId());
            employeeRoleDao.insert(erdo);
        }
        return result;
    }

    @Override
    public BaseResult removeAllRoleForEmp(Integer empId) {
        employeeRoleDao.deleteByEmpId(empId);
        return BaseResult.SUCCESS;
    }
}
