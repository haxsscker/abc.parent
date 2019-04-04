package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RedDO;
import com.autoserve.abc.dao.dataobject.RedReportDO;
import com.autoserve.abc.dao.dataobject.search.RedSearchDO;

/**
 * dao 层 红包接口
 * 
 * @author fangrui 2015年1月7日 下午1:52:41
 */
public interface RedDao extends BaseDao<RedDO, Integer> {

    /**
     * 按条件查询分页结果
     * 
     * @param RedDO 查询条件，为空的话传new RedDO()
     * @param pageCondition 分页和排序条件，可选
     * @return List<GovernmentDO>
     */
    List<RedDO> findListByParam(@Param("red") RedDO redDO, @Param("redSearchDO") RedSearchDO redSearchDO,
                                @Param("pageCondition") PageCondition pageCondition);

    /**
     * 查询满足条件的记录条数
     * 
     * @author fangrui
     * @param redsendJDO
     * @return int
     */
    int countListByParam(@Param("red") RedDO redDO, @Param("redSearchDO") RedSearchDO redSearchDO);
    
    /**
     * 红包明细统计报表
     * @param pageCondition
     * @return
     */
    List<RedReportDO> redReport(@Param("red") RedReportDO redReportDO ,@Param("pageCondition") PageCondition pageCondition);
    
    /**
     * 红包明细统计报表总条数
     * @return
     */
    int countRedReport(@Param("red") RedReportDO redReportDO);
}
