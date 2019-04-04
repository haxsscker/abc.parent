package com.autoserve.abc.service.biz.impl.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.dao.dataobject.LoginLogDO;
import com.autoserve.abc.dao.dataobject.RoleDO;
import com.autoserve.abc.dao.intf.EmployeeDao;
import com.autoserve.abc.dao.intf.LoginLogDao;
import com.autoserve.abc.service.biz.convert.EmployeeConverter;
import com.autoserve.abc.service.biz.entity.Employee;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.EmployeeType;
import com.autoserve.abc.service.biz.enums.EntityState;
import com.autoserve.abc.service.biz.enums.OnlineState;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.employee.EmployeeRoleService;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.util.CryptUtils;

/**
 * 员工操作的业务实现类
 * 
 * @author RJQ 2014/11/17 17:35.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Resource
    private EmployeeDao         employeeDao;

    @Resource
    private LoginLogDao         loginLogDao;

    @Resource
    private SysConfigService    sysConfigService;

    @Resource
    private EmployeeRoleService roleService;

    /**
     * 新增员工
     * 
     * @param employee
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult createEmployee(EmployeeDO employee) {
        BaseResult result = new BaseResult();
        //从系统配置表获取默认密码
        SysConfig sysConfig = sysConfigService.querySysConfig(SysConfigEntry.EMPLOYEE_DEFAULT_PASSWORD).getData();
        if (sysConfig == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "查询新员工默认密码失败！");
            return result;
        }
        String defaultPass = CryptUtils.md5(sysConfig.getConfValue());
        employee.setEmpPassword(defaultPass);

        //验证用户名是否已存在
        BaseResult baseResult = checkEmpNameExist(employee.getEmpName());
        if (!baseResult.isSuccess()) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, baseResult.getMessage());
            return result;
        }

        //插入新员工信息
        int returnVal = employeeDao.insert(employee);
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "添加失败！");
            return result;
        }

        List<Integer> list = new ArrayList<Integer>();
        list.add(BaseRoleType.FINANCIAL_MANAGER.index);
        list.add(BaseRoleType.INSURANCE_GOVERNMENT.index);
        list.add(BaseRoleType.LOAN_GOVERNMENT.index);
        list.add(BaseRoleType.PLATFORM_FINANCIAL.index);
        list.add(BaseRoleType.PLATFORM_SERVICE.index);
        list.add(BaseRoleType.SYS_ADMIN.index);
        roleService.allocatRoleForEmps(employee.getEmpId(), list);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult createEmployeeWithRoles(EmployeeDO employeeDO, List<Integer> list) {
        //插入员工信息
        BaseResult result = this.createEmployee(employeeDO);
        if (result.isSuccess()) {
            //批量插入员工角色
            BaseResult baseResult = roleService.allocatRoleForEmps(employeeDO.getEmpId(), list);
            if (!baseResult.isSuccess()) {
                result.setErrorMessage(CommonResultCode.BIZ_ERROR, "记录员工角色失败！");
                return result;
            }
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult modifyEmployeeWithRoles(EmployeeDO employeeDO, List<Integer> roleIdList) {
        BaseResult result = this.modifyEmployee(employeeDO);
        if (!result.isSuccess()) {
            throw new BusinessException(result.getMessage());
        }
        Integer empId = employeeDO.getEmpId();
        result = roleService.removeAllRoleForEmp(empId);
        if (!result.isSuccess()) {
            throw new BusinessException(result.getMessage());
        }
        result = roleService.allocatRoleForEmps(empId, roleIdList);
        if (!result.isSuccess()) {
            throw new BusinessException(result.getMessage());
        }

        result.setMessage("修改成功！");
        return result;
    }

    /**
     * 删除指定员工，逻辑删除，将state字段修改
     * 
     * @param id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult removeEmployee(int id) {
        BaseResult result = new BaseResult();
        //小贷或担保用户不允许删除，因为一个机构只对应这一个用户账户，提醒想要删除的话，删除对应的机构便可同时将此用户删除
        PlainResult<EmployeeDO> plainResult = findById(id);
        if (!plainResult.isSuccess()) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到指定用户！");
            return result;
        }
        EmployeeDO employeeDO = plainResult.getData();
        /**
         * 判断用户是否是启用状态，启用状态不可删除
         */
        if (EntityState.STATE_ENABLE.getState() == employeeDO.getEmpState()) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "已启用的用户不可删除！");
            return result;
        }

        Integer empType = employeeDO.getEmpType();
        if (empType != null && empType.equals(EmployeeType.LOAN_GUAR_GOVERNMENT_EMP.getType())) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "小贷/担保机构用户请先删除机构！");
            return result;
        }
        int returnVal = this.updateUserState(id, EntityState.STATE_DELETED);
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "删除失败！");
            return result;
        }
        result.setMessage("删除成功！");
        return result;
    }

    @Override
    public BaseResult removeEmpByGovId(Integer govId) {
        BaseResult result = new BaseResult();
        EmployeeDO emp = new EmployeeDO();
        emp.setEmpOrgId(govId);
        emp.setEmpState(EntityState.STATE_DELETED.getState());
        int returnVal = employeeDao.updateByGovIdSelective(emp);
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "删除失败！");
            return result;
        }
        result.setMessage("删除成功！");
        return result;
    }

    /**
     * 修改员工信息
     * 
     * @param employee
     * @return
     */
    @Override
    public BaseResult modifyEmployee(EmployeeDO employee) {
        BaseResult result = new BaseResult();
        int returnVal = employeeDao.updateByPrimaryKeySelective(employee);
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "修改失败！");
            return result;
        }
        return result;
    }

    /**
     * 根据id查找员工
     * 
     * @param id
     * @return
     */
    @Override
    //    @Cacheable(cacheName = "employeeCache")
    public PlainResult<EmployeeDO> findById(int id) {
        PlainResult<EmployeeDO> result = new PlainResult<EmployeeDO>();
        EmployeeDO emp = employeeDao.findById(id);
        if (emp == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "查询失败！");
            return result;
        }
        result.setData(emp);
        return result;
    }

    @Override
    public PlainResult<Employee> findEntityById(int id) {
        EmployeeDO employeeDO = employeeDao.findById(id);
        PlainResult<Employee> result = new PlainResult<Employee>();
        if (employeeDO == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "查询失败！");
            return result;
        }
        Employee entity = EmployeeConverter.toEmployeeUsingBeanCopy(employeeDO);
        result.setData(entity);
        return result;
    }

    /**
     * 禁用某个员工
     * 
     * @param id
     * @return
     */
    @Override
    public BaseResult disableEmployee(int id) {
        BaseResult result = new BaseResult();
        int returnVal = this.updateUserState(id, EntityState.STATE_DISABLE);
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "禁用失败！");
            return result;
        }
        return result;
    }

    /**
     * 启用某个员工
     * 
     * @param id
     * @return
     */
    @Override
    public BaseResult enableEmployee(int id) {
        BaseResult result = new BaseResult();
        int returnVal = this.updateUserState(id, EntityState.STATE_ENABLE);
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "启用失败！");
            return result;
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageResult<EmployeeDO> queryList(EmployeeDO employeeDO, PageCondition pageCondition) {

        PageResult<EmployeeDO> result = new PageResult<EmployeeDO>(pageCondition.getPage(), pageCondition.getPageSize());
        int totalCount = employeeDao.countListByParam(employeeDO);
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            result.setData(employeeDao.findListByParam(employeeDO, pageCondition));
        }

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult updatePassword(int empId, String oldPwd, String newPwd) {
        BaseResult result = new BaseResult();

        //校验旧密码是否正确
        EmployeeDO employeeDO = employeeDao.findById(empId);
        if (employeeDO == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到指定员工！");
            return result;
        }
        String oldPwdMd5 = CryptUtils.md5(oldPwd);
        if (!oldPwdMd5.equals(employeeDO.getEmpPassword())) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "旧密码输入不正确！");
            return result;
        }

        //修改新密码
        String newPwdMd5 = CryptUtils.md5(newPwd);
        employeeDO.setEmpPassword(newPwdMd5);
        int returnVal = employeeDao.update(employeeDO);
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "密码修改失败！");
            return result;
        }
        return result;
    }

    /**
     * 更新员工状态
     * 
     * @param id
     * @param state 0：停用 1：启用 2:已删除
     * @return
     */
    private int updateUserState(int id, EntityState state) {
        EmployeeDO employee = new EmployeeDO();
        employee.setEmpId(id);
        employee.setEmpState(state.getState());
        return employeeDao.updateByPrimaryKeySelective(employee);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public PlainResult<Employee> login(String empName, String password, String loginIp) {
        PlainResult<Employee> result = new PlainResult<Employee>();

        // 1.校验用户名密码，进行登录
        EmployeeDO employeeDO = employeeDao.findEmpByNameAndPass(empName, password);
        if (employeeDO == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "用户名或密码不正确！");
        } else if (employeeDO.getEmpState() != EntityState.STATE_ENABLE.getState()) {//账号非启用状态
        	if(EntityState.STATE_DELETED.getState()==employeeDO.getEmpState()){
        		result.setErrorMessage(CommonResultCode.BIZ_ERROR, "用户已被删除！");
        	}else{
        		result.setErrorMessage(CommonResultCode.BIZ_ERROR, "用户不在启用状态请联系管理员！");
        	}
        } else {
            //更新登录次数，最后登录时间和在线状态
            Integer loginCount = employeeDO.getEmpLoginCount();
            if (null == loginCount) {
                loginCount = 0;
            }
            employeeDO.setEmpLoginCount(++loginCount);
            employeeDO.setEmpLastlogintime(new Date());
            employeeDO.setEmpIsonline(OnlineState.STATE_ONLINE.getState());
            int returnVal = employeeDao.update(employeeDO);
            if (returnVal <= 0) {
                result.setErrorMessage(CommonResultCode.BIZ_ERROR, "更新登录相关字段失败！");
                return result;
            }

            //成功，返回员工信息
            Employee entity = EmployeeConverter.toEmployeeUsingBeanCopy(employeeDO);
            result.setData(entity);
        }

        // 2.添加登录日志
        LoginLogDO logLogDO = new LoginLogDO();
        logLogDO.setLlEmpId(result.isSuccess() ? employeeDO.getEmpId() : null);
        logLogDO.setLlIp(loginIp);
        logLogDO.setLlLoginState(result.isSuccess() ? 1 : 0); // 登录状态 -1:已删除 0：用户登录失败 1：用户登录成功 2：用户主动登出
        logLogDO.setLlLoginTime(new Date());
        int count = loginLogDao.insert(logLogDO);
        if (count <= 0) {
            throw new BusinessException("登录失败");
        }

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult logout(int empId, String logoutIp) {
        BaseResult result = new BaseResult();

        // 登出日志
        LoginLogDO logLogDO = new LoginLogDO();

        logLogDO.setLlEmpId(empId);
        logLogDO.setLlIp(logoutIp);
        logLogDO.setLlLoginState(2); // 登录状态 -1:已删除 0：用户登录失败 1：用户登录成功 2：用户主动登出
        logLogDO.setLlLogoutTime(new Date());
        int updateCount = loginLogDao.updateOneLoginLogState(logLogDO);
        if (updateCount != 1) {
            throw new BusinessException("更新离线状态失败");
        }

        EmployeeDO employeeDO = new EmployeeDO();
        employeeDO.setEmpId(empId);
        employeeDO.setEmpIsonline(OnlineState.STATE_NOT_ONLINE.getState());
        int returnVal = employeeDao.updateByPrimaryKeySelective(employeeDO);
        if (returnVal <= 0) {
            throw new BusinessException("更新离线状态失败");
        }

        return result;
    }

    @Override
    public PageResult<Employee> queryEmpWithRolesList(EmployeeDO employeeDO, PageCondition pageCondition) {
        PageResult<Employee> result = new PageResult<Employee>(pageCondition);

        PageResult<EmployeeDO> pageEmpDO = this.queryList(employeeDO, pageCondition);
        List<EmployeeDO> list = pageEmpDO.getData();
        if (null != list) {
            //将员工ID组成list
            List<Integer> idList = new ArrayList<Integer>();
            for (EmployeeDO edo : list) {
                idList.add(edo.getEmpId());
            }

            //调用role的service获取角色
            Map<Integer, List<RoleDO>> roles = roleService.queryRoleByEmps(idList).getData();
            List<Employee> empList = null;
            if (roles != null) {
                //将EmployeeDO转成Employee
                empList = EmployeeConverter.convertList(list);
                for (int i = 0, j = empList.size(); i < j; ++i) {
                    //将每个员工的角色组织成String，再加到Employee中
                    List<RoleDO> role = roles.get(empList.get(i).getEmpId());
                    StringBuffer strRole = new StringBuffer();
                    if (role != null) {
                        for (RoleDO r : role) {
                            if (r != null && r.getRoleName() != null) {
                                strRole.append(r.getRoleName()).append(",");
                            }
                        }
                    }
                    int len = strRole.length();
                    if (len > 0) {
                        strRole.delete(len - 1, len);
                    }
                    empList.get(i).setEmpRole(strRole.toString());
                }
            }
            result.setData(empList);
            result.setTotalCount(pageEmpDO.getTotalCount());
        }
        return result;
    }

    @Override
    public Map<Integer, String> findEmpNamesByIds(List<Integer> ids) {
        Map<Integer, String> result = new HashMap<Integer, String>();
        List<EmployeeDO> emps = employeeDao.findByList(ids);
        if (null != emps && emps.size() != 0) {
            for (int i = 0, j = emps.size(); i < j; ++i) {
                EmployeeDO emp = emps.get(i);
                result.put(emp.getEmpId(), emp.getEmpName());
            }
        }
        return result;
    }

    @Override
    public ListResult<EmployeeDO> findByList(List<Integer> ids) {
        ListResult<EmployeeDO> result = new ListResult<EmployeeDO>();
        if(ids==null || ids.size()==0){
        	return result;
        }
        List<EmployeeDO> emps = employeeDao.findByList(ids);
        if (null == emps) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "没有数据");
            return result;
        }
        result.setData(emps);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public PlainResult<String> initPwdByGovId(int govId) {
        PlainResult<String> result = new PlainResult<String>();
        EmployeeDO employeeDO = new EmployeeDO();
        employeeDO.setEmpOrgId(govId);
        PlainResult<SysConfig> plainResult = sysConfigService.querySysConfig(SysConfigEntry.EMPLOYEE_DEFAULT_PASSWORD);
        if (!plainResult.isSuccess()) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "查询默认密码失败！");
            return result;
        }
        String defaultPass = plainResult.getData().getConfValue();
        String defaultPassMD5 = CryptUtils.md5(defaultPass);
        employeeDO.setEmpPassword(defaultPassMD5);
        int returnVal = employeeDao.updateByGovIdSelective(employeeDO);
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "初始化密码失败！");
            return result;
        }
        result.setData(defaultPass);
        result.setMessage("初始化密码成功！");
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public PlainResult<String> initPwdByEmpId(int empId) {
        PlainResult<String> result = new PlainResult<String>();
        EmployeeDO employeeDO = new EmployeeDO();
        employeeDO.setEmpId(empId);
        PlainResult<SysConfig> plainResult = sysConfigService.querySysConfig(SysConfigEntry.EMPLOYEE_DEFAULT_PASSWORD);
        if (!plainResult.isSuccess()) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "查询默认密码失败！");
            return result;
        }
        String defaultPass = plainResult.getData().getConfValue();
        String defaultPassMD5 = CryptUtils.md5(defaultPass);
        employeeDO.setEmpPassword(defaultPassMD5);
        int returnVal = employeeDao.updateByPrimaryKeySelective(employeeDO);
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "初始化密码失败！");
            return result;
        }
        result.setData(defaultPass);
        result.setMessage("初始化密码成功！");
        return result;
    }

    @Override
    public BaseResult modifyByGovId(int govId, EmployeeDO employeeDO) {
        BaseResult result = new BaseResult();
        employeeDO.setEmpOrgId(govId);
        int val = employeeDao.updateByGovIdSelective(employeeDO);
        if (val <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "修改失败！");
        }
        return result;
    }
   
    /**
     * 在状态为未删除的范围内验证用户名是否重复
     */
    @Override
    public BaseResult checkEmpNameExist(String empName) {
        BaseResult result = new BaseResult();
        int state = EntityState.STATE_DELETED.getState();
        EmployeeDO employeeDO = employeeDao.findEmpByName(empName,state);
        if (employeeDO != null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "用户名已存在");
            return result;
        }
        return result;
    }

    @Override
    public PlainResult<Employee> findByGovId(int govId) {
        PlainResult<Employee> result = new PlainResult<Employee>();
        EmployeeDO employeeDO = employeeDao.findByGovId(govId);
        if (employeeDO == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到指定信息");
            return result;
        }
        Employee employee = EmployeeConverter.toEmployee(employeeDO);
        result.setData(employee);
        return result;
    }
}
