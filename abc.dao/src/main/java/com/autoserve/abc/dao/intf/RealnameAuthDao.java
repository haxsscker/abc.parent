package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RealnameAuthDO;
import com.autoserve.abc.dao.dataobject.RealnameAuthJDO;

public interface RealnameAuthDao extends BaseDao<RealnameAuthDO, Integer> {
    /**
     * 根据参数获取记录条数
     * 
     * @param realnameAuthDO
     * @return int
     */
    public int countListByParam(RealnameAuthDO realnameAuthDO);

    /**
     * 按条件查询分页结果
     * 
     * @param realnameAuthDO 查询条件，可选
     * @param pageCondition 分页和排序条件，可选
     * @return List<RealnameAuthDO>
     */
    List<RealnameAuthDO> findListByParam(@Param("realName") RealnameAuthDO realnameAuthDO,
                                         @Param("pageCondition") PageCondition pageCondition);
    /**
     * 按条件查询分页结果总记录数
     * @param realnameAuthJDO
     * @return
     */
    int countRealnameListByParam(@Param("realName") RealnameAuthJDO realnameAuthJDO);

    /**
     * 按条件查询分页结果
     * 
     * @param realnameAuthJDO 查询条件，可选
     * @param pageCondition 分页和排序条件，可选
     * @return List<RealnameAuthDO>
     */
    List<RealnameAuthJDO> findRealnameListByParam(@Param("realName") RealnameAuthJDO realnameAuthJDO,
                                                  @Param("pageCondition") PageCondition pageCondition);

    RealnameAuthJDO findRealnameListByPOJO(@Param("realName") RealnameAuthJDO realnameAuthJDO);

    /**
     * 更新部分信息
     * 
     * @param realnameAuthDO 待更新的信息
     * @return int
     */
    public int updateByPrimaryKeySelective(RealnameAuthDO realnameAuthDO);

    /**
     * 更新部分信息 根据userId
     */
    public int updateByUserId(RealnameAuthDO realnameAuthDO);
}
