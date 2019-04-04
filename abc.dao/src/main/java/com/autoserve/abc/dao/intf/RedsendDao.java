package com.autoserve.abc.dao.intf;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RedsendDO;
import com.autoserve.abc.dao.dataobject.RedsendJDO;
import com.autoserve.abc.dao.dataobject.search.RedSearchDO;

/**
 * 红包发放dao层接口
 * 
 * @author lipeng 2014年12月26日 下午8:05:45
 */
public interface RedsendDao extends BaseDao<RedsendDO, Integer> {
    /**
     * 查询所有数据
     * 
     * @param redsendDO
     * @param pageCondition
     * @return
     */

    List<RedsendDO> findListByParam(@Param("rs") RedsendDO redsendDO,
                                    @Param("pageCondition") PageCondition pageCondition);

    /**
     * 查询所有数据
     * 
     * @param redsendJDO
     * @param pageCondition
     * @return List<RedsendJDO>
     */

    List<RedsendJDO> findListByJParam(@Param("redSearchDO") RedSearchDO redSearchDO,
                                      @Param("pageCondition") PageCondition pageCondition);

    /**
     * 查询满足条件的记录条数
     * 
     * @author fangrui
     * @param redsendJDO
     * @return int
     */
    int countListByParam(@Param("redSearchDO") RedSearchDO redSearchDO);

    /**
     * 查询邀请红包列表
     * 
     * @param redSearchDO
     * @param pageCondition
     * @return List<RedsendJDO>
     */
    List<RedsendJDO> findInviteList(@Param("redSearchDO") RedSearchDO redSearchDO,
                                    @Param("pageCondition") PageCondition pageCondition);

    /**
     * 查询邀请红包数
     * 
     * @param redSearchDO
     * @return int
     */
    int countInviteList(@Param("redSearchDO") RedSearchDO redSearchDO);

    /**
     * 查询id在ids里的所有记录
     * 
     * @param ids
     * @return List<RedsendDO>
     */
    List<RedsendDO> findListByIds(@Param("ids") List<Integer> ids);
    
    /**
     * 查询id在ids里的所有记录-加锁
     * 
     * @param ids
     * @return List<RedsendDO>
     */
    List<RedsendDO> findListByIdsLock(@Param("ids") List<Integer> ids);

    /**
     * 批量修改状态
     * 
     * @param sendIdList
     * @param oldState
     * @param newState
     */
    int batchModifyState(@Param("ids") List<Integer> sendIdList, @Param("oldState") int oldState,
                         @Param("newState") int newState);

    /**
     * 红包过期可用状态修改
     * 
     * @param date 部署服务器时间
     * @return
     */
    int redOverdue(@Param("date") Date date);

    /**
     * 统计用户未使用红包
     * 
     * @param userId
     * @return
     */
    int countUnusedRed(Integer userId);
    
    /**
     * 统计用户未使用红包金额
     * 
     * @param userId
     * @return
     */
    BigDecimal countUnusedRedAmount(Integer userId);
    
    /**
     * 查询可用红包的数量
     * 
     * @param redSearchDO
     * @return
     */
    int findIsHaveRed(@Param("redSearchDO") RedSearchDO redSearchDO);

    /**
     * 查询红包的有效金额
     * 
     * @param redSearchDO
     * @return
     */
    int findRedAmount(@Param("redSearchDO") RedSearchDO redSearchDO);
}
