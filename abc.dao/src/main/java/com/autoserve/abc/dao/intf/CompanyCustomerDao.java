package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CompanyCustomerDO;
import com.autoserve.abc.dao.dataobject.CompanyUserJDO;

public interface CompanyCustomerDao extends BaseDao<CompanyCustomerDO, Integer> {
    /**
     * 根据参数获取记录条数
     * 
     * @param companyCustomerDO
     * @return
     */
    public int countListByParam(CompanyCustomerDO companyCustomerDO);
    /**
     * 检验组织机构代码唯一性
     * @param companyCustomerDO
     * @return
     */
    public int countNoByParam(CompanyCustomerDO companyCustomerDO);

    /**
     * 按条件查询分页结果
     * 
     * @param companyCustomerDO 查询条件，可为null
     * @param pageCondition 分页和排序条件，可选
     * @return List<CompanyCustomerDO>
     */
    List<CompanyCustomerDO> findListByParam(@Param("com") CompanyCustomerDO companyCustomerDO,
                                            @Param("pageCondition") PageCondition pageCondition);

    /**
     * 按条件查询分页结果
     * 
     * @param CompanyUserJDO 查询条件，可为null
     * @param pageCondition 分页和排序条件，可选
     * @return List<CompanyUserJDO>
     */
    List<CompanyUserJDO> findCompanyUserListByParam(@Param("com") CompanyUserJDO companyUserJDO,
                                                    @Param("pageCondition") PageCondition pageCondition);

    /**
     * 根据参数获取记录条数
     * 
     * @param CompanyUserJDO
     * @return
     */
    public int countListCompanyUserByParam(CompanyUserJDO companyUserJDO);

    /**
     * 根据用户ID获取企业信息
     */
    public CompanyCustomerDO findByUserId(int UserId);

    /**
     * 查询已开户的企业用户总数
     * 
     * @param companyUserJDO
     * @return
     */
    public int countOpenAccount(@Param("com") CompanyUserJDO companyUserJDO);

    /**
     * 查询已开户的企业用户
     * 
     * @param companyUserJDO
     * @param pageCondition
     * @return
     */
    public List<CompanyUserJDO> findOpenAccountListByParam(@Param("com") CompanyUserJDO companyUserJDO,
                                                           @Param("pageCondition") PageCondition pageCondition);
    
    /**
     * 根据用户ID获取企业信息
     */
    public CompanyCustomerDO findByCompanyId(@Param("companyId") int companyId);
}
