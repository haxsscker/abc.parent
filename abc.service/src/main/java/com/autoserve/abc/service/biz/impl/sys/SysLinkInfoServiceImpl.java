package com.autoserve.abc.service.biz.impl.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.SysLinkInfoDO;
import com.autoserve.abc.dao.intf.SysLinkInfoDao;
import com.autoserve.abc.service.biz.intf.sys.SysLinkInfoService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 类SysLinkInfoServiceImpl.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年11月28日 上午10:41:48
 */

@Service
public class SysLinkInfoServiceImpl implements SysLinkInfoService {

    @Autowired
    private SysLinkInfoDao sysLinkInfoDao;

    @Override
    public BaseResult createSyslinkInfo(SysLinkInfoDO pojo) {
        sysLinkInfoDao.insert(pojo);
        return new BaseResult();
    }

    @Override
    public BaseResult removeSyslinkInfo(int id) {
        sysLinkInfoDao.delete(id);
        return new BaseResult();
    }

    @Override
    public BaseResult modifySyslinkInfo(SysLinkInfoDO pojo) {
        sysLinkInfoDao.update(pojo);
        return new BaseResult();
    }

    @Override
    public PlainResult<SysLinkInfoDO> queryById(int id) {
        PlainResult<SysLinkInfoDO> result = new PlainResult<SysLinkInfoDO>();
        SysLinkInfoDO sysLinkInfo = sysLinkInfoDao.findById(id);
        if (sysLinkInfo == null) {
            return result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS, "SysLinkInfoDO");
        }
        result.setData(sysLinkInfo);
        return result;
    }

    @Override
    public PageResult<SysLinkInfoDO> queryListByParam(PageCondition pageCondition) {
        PageResult<SysLinkInfoDO> PageResult = new PageResult<SysLinkInfoDO>(pageCondition);
        List<SysLinkInfoDO> list = this.sysLinkInfoDao.findListByParam(null, pageCondition);
        PageResult.setData(list);
        return PageResult;

    }
    
    @Override
    public ListResult<SysLinkInfoDO> queryAllList() {
    	ListResult<SysLinkInfoDO> result = new ListResult<SysLinkInfoDO>();

		try {
			result.setData(this.sysLinkInfoDao.findListByParam(null, null));
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}

		return result;
    }
}
