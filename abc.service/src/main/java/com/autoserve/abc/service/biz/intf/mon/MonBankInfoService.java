package com.autoserve.abc.service.biz.intf.mon;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.MonBankInfoDO;
import com.autoserve.abc.service.biz.entity.MonBankInfo;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 类MonBankInfoService.java的实现描述：TODO 类实现描述
 * 
 * @author dengjingyu 2014年12月23日 上午10:43:40
 */
public interface MonBankInfoService {
    /**
     * 添加银行账户信息
     * 
     * @param pojo 待添加的银行账户信息, 必选
     * @return BaseResult
     */
    BaseResult createMonBankInfo(MonBankInfo pojo);

    /**
     * 删除银行账户信息
     * 
     * @param pojo 待删除的银行账户信息，必选
     * @return BaseResult
     */
    BaseResult removeMonBankInfo(int pojo);

    /**
     * 修改银行账户信息
     * 
     * @param pojo 待修改的银行账户信息，必选
     * @return BaseResult
     */
    BaseResult modifyMonBankInfo(MonBankInfo pojo);

    /**
     * 查询单条银行账户信息
     * 
     * @param pojo 查询条件，必选
     * @return PlainResult<MonBankInfo>
     */
    PlainResult<MonBankInfo> queryById(int id);

    /**
     * 查询银行账户信息列表
     * 
     * @param fo 查询条件，可选
     * @param pageCondition 分页条件
     * @return PageResult<MonBankInfoDO>
     */
    PageResult<MonBankInfoDO> queryList(MonBankInfoDO pojo, PageCondition pageCondition);

}
