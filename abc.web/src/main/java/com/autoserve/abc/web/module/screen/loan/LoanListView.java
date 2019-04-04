/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.loan;

import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanState;

/**
 * 融资管理-融资维护
 *
 * @author segen189 2014年12月28日 上午11:22:39
 */
public class LoanListView {

    public void execute(Context context) {
        context.put("loanCategories", LoanCategory.values());
        context.put("loanStates", LoanState.values());
    }
}
