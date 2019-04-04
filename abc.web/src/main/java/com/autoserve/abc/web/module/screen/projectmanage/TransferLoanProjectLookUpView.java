/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.projectmanage;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.FileUploadClassType;
import com.autoserve.abc.service.biz.enums.FileUploadSecondaryClass;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Resource;

/**
 * 项目发布－查看
 *
 * @author segen189 2014年12月24日 下午10:13:28
 */
public class TransferLoanProjectLookUpView {
    private static final Log log = LogFactory.getLog(TransferLoanProjectLookUpView.class);

    @Resource
    private TransferLoanService transferLoanService;

    @Resource
    private UserService userService;

    public void execute(@Param("transferId") int transferId, @Param("loanFileUrl") String loanFileUrl, Context context, ParameterParser params) {

        //        context.put("bidId", transferId);
        context.put("bidId", params.get("transferId"));

        context.put("bidType", BidType.TRANSFER_LOAN.getType());

        context.put("fileUploadClassType", FileUploadClassType.LOAN.getType());
        context.put("fileUploadSecondaryClass", FileUploadSecondaryClass.IMAGE_DATA.getType());
        context.put("dataId", loanFileUrl);

        PlainResult<TransferLoan> result = transferLoanService.queryById(transferId);
        if (result.isSuccess()) {
            TransferLoan tranferLoan = result.getData();
            context.put("transferLoan", tranferLoan);
            context.put("loanId", tranferLoan.getOriginId());

            PlainResult<UserDO> userResult = userService.findById(tranferLoan.getUserId());
            if (userResult.isSuccess()) {
                context.put("transferUserName", userResult.getData().getUserName());
            } else {
                log.warn(userResult.getMessage());
            }
        } else {
            log.warn(result.getMessage());
        }
    }

}
