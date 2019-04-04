package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RedUseDO;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Dao 层 红包使用接口
 * 
 * @author fangrui 2015年1月7日 下午1:43:11
 */
public interface RedUseDao extends BaseDao<RedUseDO, Integer> {
    /**
     * 按条件查出记录数
     * 
     * @param redUseDO
     * @return int
     */
    public int countListByParam(@Param("redUse") RedUseDO redUseDO);

    /**
     * 按条件查询分页结果
     * 
     * @param RedUseDO 查询条件，为空的话传new RedUseDO()
     * @param pageCondition 分页和排序条件，可选
     * @return List<GovernmentDO>
     */
    List<RedUseDO> findListByParam(@Param("redUse") RedUseDO redUseDO,
                                   @Param("pageCondition") PageCondition pageCondition);
    /**
     * 通过投资记录的id查询红包使用记录
     * @param investIdList
     * @return
     */
	public List<RedUseDO> findListByInvestIds(List<Integer> investIdList);
}
