package com.autoserve.abc.service.biz.intf.exportpdf;

import java.io.OutputStream;
import java.util.List;

import com.autoserve.abc.dao.dataobject.pdfBean.InvestInformationDO;
import com.autoserve.abc.service.biz.entity.AgreementMessage;
import com.autoserve.abc.service.biz.entity.ContractBean;
import com.autoserve.abc.service.biz.result.BaseResult;

public interface ExportPdf {

    /**
     * 借款合同导出
     * 
     * @params int id 标号
     * @params OutputStream out 文件输出地址
     * @return BaseResult
     */
    public BaseResult investBorrowMoney(List<InvestInformationDO> dataset, AgreementMessage agreementMessage,
                                        OutputStream out);

    /**
     * 债券合同导出
     *
     * @params int id 标号
     * @params OutputStream out 文件输出地址
     * @return
     */
    public BaseResult exportObligatoryRight(List<InvestInformationDO> dataset, ContractBean contractBean,
                                            OutputStream out);

}
