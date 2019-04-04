package com.autoserve.abc.service.biz.intf.cashsq;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CashSqDO;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;

 
public interface CashSqService {
	
	
 

    /**
     * 添加增加免费提现额度申请
     * 
     * @param 
     * @return BaseResult
     */
    public BaseResult insetcashsq(CashSqDO cashsqDO);

 
    /**
     * 查询
     * @param pageCondition 分页条件
     * @return PageResult<UserDO>
     */
    public  PageResult<CashSqDO> queryListCashSq(CashSqDO cashSqDO, PageCondition pageConditio);


    /**
     * 修改额度记录
     * 
     * @param creditApply
     * @return BaseResult
     */
    public BaseResult modifyCreditApply(CashSqDO cashSqDO);

}
