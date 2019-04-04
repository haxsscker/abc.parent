package com.autoserve.abc.service.biz.intf.sys;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.OperateLogDO;
import com.autoserve.abc.dao.dataobject.OperateLogJDO;
import com.autoserve.abc.dao.dataobject.search.OperateLogSearchDO;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;

import java.util.List;

/**
 * 操作日志服务接口
 *
 * @author RJQ 2015/1/10 21:44.
 */
public interface OperateLogService {
    /**
     * 查询列表
     *
     * @param operateLogDO  查询条件，可选
     * @param pageCondition 分页条件
     * @return PageResult<CreditJDO>
     */
    public PageResult<OperateLogJDO> queryList(OperateLogDO operateLogDO, PageCondition pageCondition);

    /**
     * 搜索列表
     *
     * @param searchDO
     * @param pageCondition
     * @return
     */
    public PageResult<OperateLogJDO> searchList(OperateLogSearchDO searchDO, PageCondition pageCondition);

    /**
     * 删除ids列表中的登录日志
     *
     * @param ids 要删除的日志id集合
     * @return BaseResult
     */
    public BaseResult removeByIds(List<Integer> ids);

    /**
     * 删除全部登录日志
     *
     * @return BaseResult
     */
    public BaseResult removeAll();

    public BaseResult createOperateLog(OperateLogDO operateLogDO);
}
