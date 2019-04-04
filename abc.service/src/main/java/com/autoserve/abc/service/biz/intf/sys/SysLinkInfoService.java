package com.autoserve.abc.service.biz.intf.sys;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.SysLinkInfoDO;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 类SysLinkInfo.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年11月27日 下午5:03:20
 */
public interface SysLinkInfoService {
    /**
     * 添加友情链接
     * 
     * @param pojo
     * @return BaseResult
     */
    BaseResult createSyslinkInfo(SysLinkInfoDO pojo);

    /**
     * 删除友情链接
     * 
     * @param id
     * @return
     */
    BaseResult removeSyslinkInfo(int id);

    /**
     * 修改友情链接
     * 
     * @param pojo
     * @return BaseResult
     */
    BaseResult modifySyslinkInfo(SysLinkInfoDO pojo);

    /**
     * 查询单条友情链接
     * 
     * @param id
     * @return
     */
    PlainResult<SysLinkInfoDO> queryById(int id);

    /**
     * 分页查询友情链接
     * 
     * @param id
     * @return
     */
    PageResult<SysLinkInfoDO> queryListByParam(PageCondition pageCondition);

    /**
     * 查询所有的友情链接
     * @return
     */
    ListResult<SysLinkInfoDO> queryAllList();
    
}
