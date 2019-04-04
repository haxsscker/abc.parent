package com.autoserve.abc.service.biz.intf.user;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CompanyCustomerDO;
import com.autoserve.abc.dao.dataobject.CompanyCustomerFileDO;
import com.autoserve.abc.dao.dataobject.CompanyUserJDO;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author RJQ 2014/11/20 20:12.
 */
public interface CompanyCustomerService {

    /**
     * 查询公司用户信息
     * 
     * @param id 公司id
     * @return BaseResult
     */
    public PlainResult<CompanyCustomerDO> findById(int id);

    /**
     * 根据用户查询公司信息
     * 
     * @param userId 用户ID
     * @return BaseResult
     */
    public PlainResult<CompanyCustomerDO> findByUserId(int userId);
    
    /**
     * 根据用户查询公司信息
     * 
     * @param userId 用户ID
     * @return BaseResult
     */
    public PlainResult<CompanyCustomerDO> findByCompanyId(int userId);

    /**
     * 查询公司列表
     * 
     * @param companyCustomerDO 查询条件，可选
     * @param pageCondition 分页条件
     * @return PageResult<CompanyCustomerDO>
     */
    public PageResult<CompanyCustomerDO> queryList(CompanyCustomerDO companyCustomerDO, PageCondition pageCondition);

    /**
     * 删除公司用户
     * 
     * @param id 公司用户id
     * @return BaseResult
     */
    public BaseResult removeCompanyCustomer(int id);

    /**
     * 禁用公司用户
     * 
     * @param id 用户ID
     * @return BaseResult
     */
    public BaseResult disableCompanyCustomer(int id);

    /**
     * 启用公司用户
     * 
     * @param id 用户ID
     * @return BaseResult
     */
    public BaseResult enableCompanyCustomer(int id);

    /**
     * 新增公司用户
     * 
     * @param companyCustomerDO 信息
     * @return BaseResult
     */
    public BaseResult createCompanyCustomer(CompanyCustomerDO companyCustomerDO);

    /**
     * 编辑公司用户
     * 
     * @param companyCustomerDO
     * @return
     */
    public BaseResult editCompanyCustomer(CompanyCustomerDO companyCustomerDO);

    /**
     * 查询公司附件
     * 
     * @param id 公司用户id
     * @return PlainResult<CompanyCustomerFileDO>
     */
    public PlainResult<CompanyCustomerFileDO> findByCompanyCustomerId(int id);

    /**
     * 查询公司列表
     * 
     * @param companyCustomerDO 查询条件，可选
     * @param pageCondition 分页条件
     * @return PageResult<CompanyCustomerDO>
     */
    public PageResult<CompanyUserJDO> queryList(CompanyUserJDO companyUserJDO, PageCondition pageCondition);

    /**
     * 查询已开户公司列表
     * 
     * @param companyUserJDO
     * @param pageCondition
     * @return
     */
    PageResult<CompanyUserJDO> queryOpenAccountList(CompanyUserJDO companyUserJDO, PageCondition pageCondition);
}
