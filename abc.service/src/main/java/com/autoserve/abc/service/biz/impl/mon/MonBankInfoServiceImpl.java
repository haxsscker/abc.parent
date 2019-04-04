package com.autoserve.abc.service.biz.impl.mon;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.MonBankInfoDO;
import com.autoserve.abc.dao.intf.MonBankInfoDao;
import com.autoserve.abc.service.biz.convert.MonBankInfoConverter;
import com.autoserve.abc.service.biz.entity.MonBankInfo;
import com.autoserve.abc.service.biz.intf.mon.MonBankInfoService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类MonBankInfoServiceImpl.java的实现描述：TODO 类实现描述
 * 
 * @author dengjingyu 2014年12月23日 下午2:24:43
 */
@Service
public class MonBankInfoServiceImpl implements MonBankInfoService {
    @Resource
    MonBankInfoDao monBankInfoDao;

    @Override
    public BaseResult createMonBankInfo(MonBankInfo pojo) {
        BaseResult result = new BaseResult();
        int returnVal = monBankInfoDao.insert(MonBankInfoConverter.toMonBankInfoDO(pojo));
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "添加银行信息失败！");
        }
        result.setMessage("添加成功！");
        return result;
    }

    @Override
    public BaseResult removeMonBankInfo(int id) {
        BaseResult result = new BaseResult();
        int returnVal = monBankInfoDao.delete(id);
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "删除银行信息失败！");
        }
        result.setMessage("删除成功！");
        return result;
    }

    @Override
    public BaseResult modifyMonBankInfo(MonBankInfo pojo) {
        BaseResult result = new BaseResult();
        int returnVal = monBankInfoDao.update(MonBankInfoConverter.toMonBankInfoDO(pojo));
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "修改银行信息失败！");
        }
        result.setMessage("修改成功！");
        return result;
    }

    @Override
    public PlainResult<MonBankInfo> queryById(int id) {
        PlainResult<MonBankInfo> result = new PlainResult<MonBankInfo>();
        MonBankInfoDO bankInfoDO = monBankInfoDao.findById(id);
        if (bankInfoDO == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到对应银行信息");
            return result;
        }
        MonBankInfo monBankInfo = MonBankInfoConverter.toMonBankInfo(bankInfoDO);
        result.setData(monBankInfo);
        return result;
    }

    @Override
    public PageResult<MonBankInfoDO> queryList(MonBankInfoDO pojo, PageCondition pageCondition) {
        PageResult<MonBankInfoDO> result = new PageResult<MonBankInfoDO>(pageCondition);
        List<MonBankInfoDO> list = monBankInfoDao.findListByParam(pojo, pageCondition);
        int totalCount = list.size();
        result.setTotalCount(totalCount);
        if (totalCount > 0) {
            result.setData(list);
        }
        return result;
    }

}
