package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CashBorrowerViewDO;

public interface CashBorrowerViewDao extends BaseDao<CashBorrowerViewDO, Integer> {

    public int getBorrowerNum(@Param("borrowerName") String borrowerName);

    /**
     * 查询借款人资金对账信息
     * 
     * @return
     */
    public List<CashBorrowerViewDO> queryBorrowerDetail(@Param("borrowerName") String borrowerName,
                                                        @Param("pageCondition") PageCondition pageCondition);

    /**
     * 查询指定借款人由平台代付的还款总和
     */
    public List<CashBorrowerViewDO> queryPlatformPay(List<Integer> loanees);

    /**
     * 查询指定借款人由平台代付的还款总和
     */
    public List<CashBorrowerViewDO> queryBorrowerDetailByUserId(@Param("userId") int userId);
}
