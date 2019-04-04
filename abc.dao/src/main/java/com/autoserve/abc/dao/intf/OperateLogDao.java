package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.OperateLogDO;
import com.autoserve.abc.dao.dataobject.OperateLogJDO;
import com.autoserve.abc.dao.dataobject.search.OperateLogSearchDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OperateLogDao extends BaseDao<OperateLogDO, Integer> {
    /**
     * 根据参数获取记录条数
     *
     * @param operateLogDO
     * @return
     */
    public int countListByParam(@Param("log") OperateLogDO operateLogDO);

    /**
     * 按条件查询分页结果
     *
     * @param operateLogDO  查询条件，为空的话传new CreditJDO()
     * @param pageCondition 分页和排序条件，可选
     * @return List<CreditJDO>
     */
    public List<OperateLogJDO> findListByParam(@Param("log") OperateLogDO operateLogDO,
                                               @Param("pageCondition") PageCondition pageCondition);

    public int deleteByIds(@Param("ids") List<Integer> ids);

    public int countForSearch(@Param("searchDO") OperateLogSearchDO searchDO);

    public List<OperateLogJDO> search(@Param("searchDO") OperateLogSearchDO searchDO, @Param("pageCondition") PageCondition pageCondition);
}