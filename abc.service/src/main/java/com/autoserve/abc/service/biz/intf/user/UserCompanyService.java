package com.autoserve.abc.service.biz.intf.user;

import java.math.BigDecimal;
import java.util.List;

import com.autoserve.abc.dao.dataobject.UserCompanyDO;
import com.autoserve.abc.service.biz.entity.UserCompany;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author RJQ 2014/12/26 9:59.
 */
public interface UserCompanyService {

    /**
     * 修改用户单位信息
     *
     * @param userCompanyDO
     * @param userMonthIncome 
     * @return
     */
    public BaseResult modifyUserCompany(UserCompanyDO userCompanyDO, BigDecimal userMonthIncome);

    /**
     * 查询用户单位信息
     *
     * @param id 用户id
     * @return PlainResult<UserCompany>
     */
    public PlainResult<UserCompany> queryUserCompanyByUserId(int id);

    /**
     * 批量查询用户单位信息
     *
     * @param ids 用户ids
     * @return
     */
    public ListResult<UserCompany> queryUserCompaniesByUserIds(List<Integer> ids);
    
    /**
     * 创建用户单位信息
     *
     * @param UserCompanyDO
     * @param userMonthIncome 
     * @return
     */
    public BaseResult createUserHouse(UserCompanyDO userCompanyDO, BigDecimal userMonthIncome);
}
