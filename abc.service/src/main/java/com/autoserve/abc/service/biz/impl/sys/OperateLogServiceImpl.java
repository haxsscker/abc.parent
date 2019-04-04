package com.autoserve.abc.service.biz.impl.sys;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.OperateLogDO;
import com.autoserve.abc.dao.dataobject.OperateLogJDO;
import com.autoserve.abc.dao.dataobject.search.OperateLogSearchDO;
import com.autoserve.abc.dao.intf.OperateLogDao;
import com.autoserve.abc.service.biz.enums.EntityState;
import com.autoserve.abc.service.biz.intf.sys.OperateLogService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author RJQ 2015/1/10 21:44.
 */
@Service
public class OperateLogServiceImpl implements OperateLogService {
    @Resource
    private OperateLogDao operateLogDao;

    @Override
    public PageResult<OperateLogJDO> queryList(OperateLogDO operateLogDO, PageCondition pageCondition) {
        PageResult<OperateLogJDO> result = new PageResult<OperateLogJDO>(pageCondition);
        int totalCount = operateLogDao.countListByParam(operateLogDO);
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            result.setData(operateLogDao.findListByParam(operateLogDO, pageCondition));
        }

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageResult<OperateLogJDO> searchList(OperateLogSearchDO searchDO, PageCondition pageCondition) {
        PageResult<OperateLogJDO> result = new PageResult<OperateLogJDO>(pageCondition);
        int totalCount = operateLogDao.countForSearch(searchDO);
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            result.setData(operateLogDao.search(searchDO, pageCondition));
        }

        return result;
    }

    @Override
    public BaseResult removeByIds(List<Integer> ids) {
        operateLogDao.deleteByIds(ids);
        return BaseResult.SUCCESS;
    }

    @Override
    public BaseResult removeAll() {
        operateLogDao.deleteByIds(null);
        return BaseResult.SUCCESS;
    }

    @Override
    public BaseResult createOperateLog(OperateLogDO operateLogDO) {
        operateLogDO.setOlState(EntityState.STATE_ENABLE.getState());
        operateLogDao.insert(operateLogDO);
        return BaseResult.SUCCESS;
    }
}
