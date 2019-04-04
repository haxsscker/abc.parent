package com.autoserve.abc.service.biz.intf.exportpdf;

import java.io.OutputStream;

import com.autoserve.abc.service.biz.result.BaseResult;

public interface ExportPdfService {

    /**
     * 借款合同导出
     * 
     * @params int id 标号
     * @params OutputStream out 文件输出地址
     * @return BaseResult
     */
    public BaseResult exportBorrowMoney(int id,int investUserId,int investId, OutputStream out,int type);

    /**
     * 债券合同导出
     *
     * @params int id 标号
     * @params OutputStream out 文件输出地址
     * @return
     */
    public BaseResult exportObligatoryRight(int id,int investUserId,int investId, OutputStream out,int type);

}
