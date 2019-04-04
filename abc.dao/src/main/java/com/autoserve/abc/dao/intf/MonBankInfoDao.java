package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.MonBankInfoDO;

/**
 * Mon 银行信息Dao
 * 
 * @author dengjingyu 2014年12月22日 下午5:26:47
 */
public interface MonBankInfoDao extends BaseDao<MonBankInfoDO, Integer> {

    /**
     * 按条件查询分页条数
     * 
     * @param monBankInfoDO 分页和排序条件，可选
     * @param pageCondition
     * @return List<MonBankInfoDO>
     */
    List<MonBankInfoDO> countListByParam(@Param("mon") MonBankInfoDO monBankInfoDO, PageCondition pageCondition);

    /**
     * 按条件查询分页结果
     * 
     * @param monBankInfoDO 分页和排序条件，可选
     * @param pageCondition 分页和排序条件，可选
     * @return List<MonBankInfoDO>
     */
    List<MonBankInfoDO> findListByParam(@Param("mon") MonBankInfoDO monBankInfoDO,
                                        @Param("pageCondition") PageCondition pageCondition);
}
