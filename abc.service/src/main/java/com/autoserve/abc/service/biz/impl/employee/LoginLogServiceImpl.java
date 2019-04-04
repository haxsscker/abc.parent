/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.employee;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.LoginLogJDO;
import com.autoserve.abc.dao.dataobject.search.LoginLogSearchDO;
import com.autoserve.abc.dao.intf.LoginLogDao;
import com.autoserve.abc.service.biz.intf.employee.LoginLogService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户登录日志服务
 *
 * @author segen189 2015年1月5日 下午12:09:24
 */
@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Resource
    private LoginLogDao loginLogDao;

    @Override
    public PageResult<LoginLogJDO> queryPageList(LoginLogSearchDO searchDO, PageCondition pageCondition) {
        PageResult<LoginLogJDO> result = new PageResult<LoginLogJDO>(pageCondition);

        int count = loginLogDao.countBySearchDO(searchDO);
        result.setTotalCount(count);
        if (count > 0) {
            result.setData(loginLogDao.findListBySearchDO(searchDO, pageCondition));
        }

        return result;
    }

    @Override
    public BaseResult removeByIds(List<Integer> ids) {
        loginLogDao.deleteByIds(ids);
        return BaseResult.SUCCESS;
    }

    @Override
    public BaseResult removeAll() {
        loginLogDao.deleteByIds(null);
        return BaseResult.SUCCESS;
    }

}
