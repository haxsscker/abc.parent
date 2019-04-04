package com.autoserve.abc.service.biz.intf.employee;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.service.biz.entity.Employee;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

import java.util.List;
import java.util.Map;

/**
 * 类的实现描述：员工操作的业务类
 *
 * @author RJQ 2014/11/17 16:10.
 */
public interface EmployeeService {

    /**
     * 查询用户名是否存在
     *
     * @param empName 用户名
     * @return BaseResult
     */
    public BaseResult checkEmpNameExist(String empName);

    /**
     * 根据员工id查询员工姓名，形如{1, "姓名"}
     *
     * @param ids 员工id
     * @return String
     */
    public Map<Integer, String> findEmpNamesByIds(List<Integer> ids);

    public ListResult<EmployeeDO> findByList(List<Integer> ids);

    /**
     * 新增员工
     *
     * @param employee 新员工信息
     * @return BaseResult
     */
    public BaseResult createEmployee(EmployeeDO employee);

    /**
     * 新增员工(带角色信息)
     *
     * @param employeeDO 员工信息
     * @param list       角色id
     * @return BaseResult
     */
    public BaseResult createEmployeeWithRoles(EmployeeDO employeeDO, List<Integer> list);

    /**
     * 根据用户名和密码登录 登录成功后需更新登录次数，最后登录时间和在线状态
     *
     * @param empName  员工用户名
     * @param password 密码
     * @param loginIp  登录ip
     * @return PlainResult<Employee>
     */
    public PlainResult<Employee> login(String empName, String password, String loginIp);

    /**
     * 退出时更新在线状态
     *
     * @param empId    员工id
     * @param logoutIp 登出ip
     * @return
     */
    public BaseResult logout(int empId, String logoutIp);

    /**
     * 删除指定员工，逻辑删除，将state字段修改
     *
     * @param id 员工ID
     * @return BaseResult
     */
    public BaseResult removeEmployee(int id);

    /**
     * 根据机构id删除emp
     *
     * @param govId 机构id
     * @return BaseResult
     */
    public BaseResult removeEmpByGovId(Integer govId);

    /**
     * 修改员工信息
     *
     * @param employee 待修改的员工信息
     * @return PlainResult<Integer>
     */
    public BaseResult modifyEmployee(EmployeeDO employee);

    /**
     * 修改带角色的员工信息
     *
     * @param employeeDO 员工信息
     * @param roleIdList 角色ID
     * @return BaseResult
     */
    public BaseResult modifyEmployeeWithRoles(EmployeeDO employeeDO, List<Integer> roleIdList);

    /**
     * 根据id查找员工
     *
     * @param id 待查询的员工ID
     * @return PlainResult<EmployeeDO>
     */
    public PlainResult<EmployeeDO> findById(int id);

    /**
     * 查找员工Entity信息
     *
     * @param id 员工id
     * @return
     */
    public PlainResult<Employee> findEntityById(int id);

    /**
     * 禁用某个员工
     *
     * @param id 员工ID
     * @return BaseResult
     */
    public BaseResult disableEmployee(int id);

    /**
     * 启用某个员工
     *
     * @param id 员工ID
     * @return BaseResult
     */
    public BaseResult enableEmployee(int id);

    /**
     * 查询员工列表
     *
     * @param employeeDO    查询条件，可选
     * @param pageCondition 分页条件
     * @return PageResult<EmployeeDO>
     */
    public PageResult<EmployeeDO> queryList(EmployeeDO employeeDO, PageCondition pageCondition);

    /**
     * 查询带角色信息的员工列表
     *
     * @param employeeDO    查询条件，可选
     * @param pageCondition 分页条件
     * @return
     */
    public PageResult<Employee> queryEmpWithRolesList(EmployeeDO employeeDO, PageCondition pageCondition);

    /**
     * 修改密码
     *
     * @param empId  员工ID
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return BaseResult
     */
    public BaseResult updatePassword(int empId, String oldPwd, String newPwd);

    /**
     * 初始化机构对应员工密码
     *
     * @param govId 机构id
     * @return PlainResult<String> 返回初始密码
     */
    public PlainResult<String> initPwdByGovId(int govId);

    /**
     * 初始化员工密码
     *
     * @param empId 员工id
     * @return PlainResult<String> 返回初始密码
     */
    public PlainResult<String> initPwdByEmpId(int empId);

    /**
     * 根据机构id修改机构所属员工信息
     *
     * @param govId      机构id
     * @param employeeDO 待修改的信息
     * @return
     */
    public BaseResult modifyByGovId(int govId, EmployeeDO employeeDO);

    /**
     * 根据机构id查询机构所属员工信息
     *
     * @param govId 机构id
     * @return
     */
    public PlainResult<Employee> findByGovId(int govId);
}
