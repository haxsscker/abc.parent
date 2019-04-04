/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.employee;

import java.util.List;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.LoginLogJDO;
import com.autoserve.abc.dao.dataobject.search.LoginLogSearchDO;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;

/**
 * 用户登录日志服务
 *
 * @author segen189 2015年1月5日 下午12:03:27
 */
public interface LoginLogService {

    /**
     * 分页查询日志
     *
     * @param searchDO 查询条件，可选
     * @param pageCondition 分页条件，必选
     * @return PageResult<LoginLogDO>
     */
    PageResult<LoginLogJDO> queryPageList(LoginLogSearchDO searchDO, PageCondition pageCondition);

    /**
     * 删除ids列表中的登录日志
     *
     * @param ids 要删除的日志id集合
     * @return BaseResult
     */
    BaseResult removeByIds(List<Integer> ids);

    /**
     * 删除全部登录日志
     *
     * @return BaseResult
     */
    BaseResult removeAll();

}
