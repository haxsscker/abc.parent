package com.autoserve.abc.service.biz.intf.cash;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AutoTransferDO;
import com.autoserve.abc.service.biz.entity.AutoTransfer;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 类AutoTransferService.java的实现描述：TODO 类实现描述
 * 
 * @author Tiuwer 2015年4月23日 上午11:14:43
 */
public interface AutoTransferService {

    /**
     * 创建自主转账申请
     * 
     * @return
     */
    public PlainResult<AutoTransfer> createAutoTransfer(AutoTransfer autoTransfer);

    /**
     * 删除自主转账申请 如果审核以通过 无法删除
     * 
     * @return
     */
    public BaseResult removeAutoTransfer(int id);

    /**
     * 删除自主转账申请 如果审核以通过 无法删除
     * 
     * @return
     */
    public PlainResult<AutoTransfer> queryAutoTransfer(int id);

    /**
     * 删除自主转账申请 如果审核以通过 无法删除
     * 
     * @return
     */
    public PlainResult<AutoTransfer> modifyAutoTransfer(AutoTransfer autoTransfer);

    /**
     * 自主转账申请列表
     */
    public PageResult<AutoTransfer> showList(PageCondition pageCondition);

    /**
     * 发送转账 审核
     * 
     * @param creditId
     * @return
     */
    BaseResult initReview(int creditId);

    /**
     * 查询自动转账申请列表
     * 
     * @param at
     * @param pageCondition
     * @return
     */
    PageResult<AutoTransfer> queryList(AutoTransferDO at, PageCondition pageCondition);

}
