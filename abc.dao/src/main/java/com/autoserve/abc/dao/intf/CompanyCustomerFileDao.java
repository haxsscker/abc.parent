package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.CompanyCustomerFileDO;

public interface CompanyCustomerFileDao extends BaseDao<CompanyCustomerFileDO, Integer> {

    /**
     * 查询公司附件
     * @param id 公司用户id
     * @return CompanyCustomerFileDO
     */
    public CompanyCustomerFileDO findByCompanyCustomerId(int id);
}
