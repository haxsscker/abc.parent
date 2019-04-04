/**
 * Author: wuqiang.du
 * Date: 2014年12月20日16:00:37
 * Description:
 */
package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.LoanIntentApplyDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoanIntentAppplyDao extends BaseDao<LoanIntentApplyDO, Integer> {

    int countListByParam(LoanIntentApplyDO param);

    List<LoanIntentApplyDO> findListByParam(@Param("param") LoanIntentApplyDO param,
                                            @Param("pageCondition") PageCondition pageCondition);

    /**
     * 查询所有
     * 
     * @param pageCondition
     * @return
     */
    List<LoanIntentApplyDO> findList(@Param("pageCondition") PageCondition pageCondition);

    /**
     * 根据多个id查询
     *
     * @param idList id的列表
     * @return 查询到的LoanIntentApplyDO列表
     */
    List<LoanIntentApplyDO> findByIdList(@Param("idList") List<Integer> idList);
    
    /**
     * 个体每日融资项目数
     * @return
     */
    Integer countByNow(@Param("userId") Integer userId);
}
