/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.user.json;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CashQuotaApplyDO;
import com.autoserve.abc.service.biz.intf.user.CashQuotaApplyService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.web.vo.JsonPageVO;

/**
 * 免费提现额度申请列表
 * 
 * @author zhangkang 2015年6月18日 上午9:34:16
 */
public class CashQuotaApplyList {

    @Autowired
    private CashQuotaApplyService cashQuotaApplyService;

    public JsonPageVO<CashQuotaApplyDO> execute(ParameterParser params) {
        JsonPageVO<CashQuotaApplyDO> pageVO = new JsonPageVO<CashQuotaApplyDO>();
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        CashQuotaApplyDO apply = new CashQuotaApplyDO();
        Integer state = params.getInt("cqaState");
        if (state != 0) {
            apply.setCqaState(state);
        }
        String username = params.getString("cqaUsername");
        apply.setCqaUsername(username);
        PageCondition pageCondition = new PageCondition(page, rows);
        ListResult<CashQuotaApplyDO> lResult = this.cashQuotaApplyService.queryList(apply, pageCondition);
        pageVO.setRows(lResult.getData());
        pageVO.setTotal(lResult.getCount());
        return pageVO;
    }
}
