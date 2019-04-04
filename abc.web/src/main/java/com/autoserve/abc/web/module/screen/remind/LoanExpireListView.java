/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.remind;

import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanState;

/**
 * 项目过期提醒
 *
 */
public class LoanExpireListView {

    public void execute(Context context) {
        context.put("loanCategories", LoanCategory.values());
        context.put("loanStates", LoanState.values());
    }
}
