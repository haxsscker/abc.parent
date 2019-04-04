package com.autoserve.abc.dao.intf;

import java.util.List;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.UserAccountDO;

public interface UserAccountDao extends BaseDao<UserAccountDO, Integer> {

    /**
     * 将findByAccountNos拆分为两个部分：findIdsByAccountNos 和 findByIds 其中
     * findIdsByAccountNos 将根据用户账户号对用户资金记录加行锁,并返回用户资金信息的id<br>
     * findByIds 通过根据UserAccount表的id查询用户资金详情
     * 
     * @deprecated
     * @param userAccounts
     * @return
     */

    @Deprecated
    public List<UserAccountDO> findByAccountNos(List<String> userAccounts);

    /**
     * 批量通过账户号查询用户资金记录id
     * 
     * @param userAccounts
     * @return
     */
    public List<Integer> findIdsByAccountNos(List<String> userAccounts);

    /**
     * 批量通过用户资金记录id查询用户资金记录
     * 
     * @param ids
     * @return
     */
    public List<UserAccountDO> findByIds(List<Integer> ids);

    /**
     * 通过账户查询账户财务信息 只返回最新的资金状态
     * 
     * @param accountNo
     * @return
     */
    public UserAccountDO findByAccountNo(String accountNo);
}
