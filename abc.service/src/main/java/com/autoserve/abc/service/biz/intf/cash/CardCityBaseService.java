package com.autoserve.abc.service.biz.intf.cash;

import com.autoserve.abc.dao.dataobject.CardCityBaseDO;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 双乾 绑卡城市管理
 * 
 * @author liuwei 2015年1月28日 下午10:43:56
 */
public interface CardCityBaseService {
    /**
     * 根据编码查询卡
     * 
     * @param code
     * @return
     */
    public PlainResult<CardCityBaseDO> queryCardByCode(Integer code);
    
    /**
     * 根据provCode查询
     * 
     * @param provCode
     * @return
     */
    public ListResult<CardCityBaseDO> queryCardByprovCode(Integer provCode);
    
    /**
     * 查询所有的city数据
     * 
     * @return
     */
    public ListResult<CardCityBaseDO> queryAllCity();
}
