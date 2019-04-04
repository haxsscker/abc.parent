package com.autoserve.abc.web.module.screen.invest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.FileUploadInfoDO;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.enums.FileUploadSecondaryClass;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.upload.FileUploadInfoService;
import com.autoserve.abc.service.biz.result.PlainResult;

public class InvestImgInfo {

    @Resource
    LoanQueryService              loanQueryService;

    @Resource
    TransferLoanService           transferLoanService;

    @Autowired
    private FileUploadInfoService fileUploadInfoService;

    public void execute(Context context, Navigator nav, ParameterParser params) {

        Integer loanId = params.getInt("loanId");
        Integer transferId = params.getInt("transferId");
        Integer flagLoan = params.getInt("flagLoan");

        PlainResult<Loan> resu = new PlainResult<Loan>();
        TransferLoan transferLoan = new TransferLoan();
        //以下为该项目的详细信息
        Loan loan = new Loan();
        String loanFileUrl = null;
        //普通标详情页
        if (loanId != null && loanId > 0 && flagLoan == 1) {
            loan.setLoanId(loanId);
            resu = this.loanQueryService.queryByParam(loan);
            loanFileUrl = resu.getData().getLoanFileUrl();
            resu.getData().getLoanFileUrl();

        }

        //转让标详情页
        PlainResult<TransferLoan> result = null;
        if (transferId != null && transferId > 0 && flagLoan == 2) {
            transferLoan.setId(transferId);
            loanId = this.transferLoanService.queryByParam(transferLoan).getData().getOriginId();
            loan.setLoanId(loanId);
            resu = this.loanQueryService.queryByParam(loan);
            loanFileUrl = resu.getData().getLoanFileUrl();
        }

        /**
         * IMAGE_DATA(1, "影像资料"), GUA_DATA(2, "担保机构"), QUA_DATA(3, "企业实地"),
         * SPOT_DATA(4, "企业资质"), OTHER_DATA(5, "其他"), SAFE_DATA(6, "风控资料");
         */

        //查询相关图片
        Map<String, List<FileUploadInfoDO>> picGroup = new HashMap<String, List<FileUploadInfoDO>>();
        picGroup.put("guas",
                fileUploadInfoService.findListByFileUrl(loanFileUrl, FileUploadSecondaryClass.GUA_DATA.getType()));
        picGroup.put("quas",
                fileUploadInfoService.findListByFileUrl(loanFileUrl, FileUploadSecondaryClass.QUA_DATA.getType()));
        picGroup.put("spots",
                fileUploadInfoService.findListByFileUrl(loanFileUrl, FileUploadSecondaryClass.SPOT_DATA.getType()));
        picGroup.put("others",
                fileUploadInfoService.findListByFileUrl(loanFileUrl, FileUploadSecondaryClass.IMAGE_DATA.getType()));
        picGroup.put("safes",
                fileUploadInfoService.findListByFileUrl(loanFileUrl, FileUploadSecondaryClass.SAFE_DATA.getType()));

        context.put("picGroup", picGroup);

        //参数回传
        context.put("loanId", loanId);
    }
}
