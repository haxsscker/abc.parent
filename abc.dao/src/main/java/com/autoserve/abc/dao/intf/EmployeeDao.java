package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.dao.dataobject.UserDO;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeDao extends BaseDao<EmployeeDO, Integer> {
    /**
     * 修改员工的某些字段（以主键为准）
     *
     * @param employeeDO 员工信息，需包含主键
     * @return int
     */
    public int updateByPrimaryKeySelective(EmployeeDO employeeDO);

    /**
     * 修改员工的某些字段（以机构主键为准）
     *
     * @param employeeDO 员工信息，需包含机构主键
     * @return int
     */
    public int updateByGovIdSelective(EmployeeDO employeeDO);

    /**
     * 根据参数获取记录条数
     *
     * @param employeeDO
     * @return
     */
    public int countListByParam(EmployeeDO employeeDO);

    /**
     * 按条件查询分页结果
     *
     * @param employeeDO    查询条件，为空的话传new EmployeeDO()
     * @param pageCondition 分页和排序条件，可选
     * @return List<GovernmentDO>
     */
    List<EmployeeDO> findListByParam(@Param("emp") EmployeeDO employeeDO,
                                     @Param("pageCondition") PageCondition pageCondition);

    /**
     * 查询员工实体信息
     *
     * @param id 员工id
     * @return
     */
//    public EmployeeDO findEntityById(int id);

    /**
     * 根据员工用户名和密码查询员工信息
     *
     * @param empName     员工用户名
     * @param empPassword 密码
     * @return EmployeeDO
     */
    public EmployeeDO findEmpByNameAndPass(@Param("empName") String empName,
                                           @Param("empPassword") String empPassword);

    /**
     * 根据员工用户名查询员工信息
     *
     * @param empName 员工用户名
     * @return EmployeeDO
     */
    public EmployeeDO findEmpByName(@Param("empName") String empName,@Param("state") int state);

    /**
     * 批量查询
     *
     * @param ids 多个员工id
     * @return List<EmployeeDO>
     */
    public List<EmployeeDO> findByList(List<Integer> ids);
    
    public EmployeeDO findByGovId(int govId);
    
    
    public UserDO findEmpByAuthorizeSeqNo(EmployeeDO employeeDO);
    
	public void updateAuthorizeBySeqNo(EmployeeDO employeeDO);

	public void updateAuthorizeByEmpId(EmployeeDO employeeDO);
	//清除融资账户授权信息
	public void cleanLoanAuthorizeByEmpId(EmployeeDO employeeDO);
}
