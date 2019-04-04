/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.projectmanage;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.enums.FileUploadClassType;
import com.autoserve.abc.service.biz.enums.FileUploadSecondaryClass;

/**
 * 项目发布－查看
 *
 * @author segen189 2014年12月24日 下午10:13:28
 */
public class LoanProjectLookUpView {

    public void execute(@Param("loanId") int loanId, @Param("loanFileUrl") String loanFileUrl, Context context) {
        context.put("loanId", loanId);
        context.put("fileUploadClassType", FileUploadClassType.LOAN.getType());
        context.put("fileUploadSecondaryClass", FileUploadSecondaryClass.IMAGE_DATA.getType());
        context.put("dataId", loanFileUrl);
    }

}
