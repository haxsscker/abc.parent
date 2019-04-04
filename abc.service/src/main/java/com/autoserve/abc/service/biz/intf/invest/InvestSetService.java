package com.autoserve.abc.service.biz.intf.invest;

import com.autoserve.abc.service.biz.entity.InvestSet;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 类InvestSetService.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2015年3月10日 下午5:11:49
 */
public interface InvestSetService {

    /**
     * 添加自动投标
     */
    PlainResult<Integer> createInvest(InvestSet pojo);

    /**
     * 修改自动投标
     */
    PlainResult<Integer> modifyInvest(InvestSet pojo);
    /**
     * 删除自动投标
     */
    BaseResult removeInvestById(Integer id);

    /**
     * 查询自动投标设置
     * 
     * @param pojo
     * @return
     */
    ListResult<InvestSet> queryInvest(InvestSet pojo);

}
