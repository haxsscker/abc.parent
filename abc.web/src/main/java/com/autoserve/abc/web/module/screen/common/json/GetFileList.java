package com.autoserve.abc.web.module.screen.common.json;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.entity.FileUploadInfo;
import com.autoserve.abc.service.biz.enums.EntityState;
import com.autoserve.abc.service.biz.enums.FileUploadClassType;
import com.autoserve.abc.service.biz.enums.FileUploadSecondaryClass;
import com.autoserve.abc.service.biz.intf.upload.FileUploadInfoService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.web.convert.FileUploadInfoVOConverter;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.file.FileUploadInfoVO;

/**
 * 查询影像资料列表
 *
 * @author RJQ 2014/12/28 17:54.
 */
public class GetFileList {
    @Autowired
    private FileUploadInfoService infoService;

    public JsonPageVO<FileUploadInfoVO> execute(ParameterParser params) {
        JsonPageVO<FileUploadInfoVO> vo = new JsonPageVO<FileUploadInfoVO>();
        FileUploadClassType classType;
        FileUploadSecondaryClass secondaryClassType = null;
        String dataId;
        if (0 != params.getInt("fileUploadClassType")) {
            classType = FileUploadClassType.valueOf(params.getInt("fileUploadClassType"));
            if (0 != params.getInt("fileUploadSecondaryClass")) {
                secondaryClassType = FileUploadSecondaryClass.valueOf(params.getInt("fileUploadSecondaryClass"));
            }
            dataId = params.getString("dataId");
            //loanId = params.getInt("loanId");

            FileUploadInfo uploadInfo = new FileUploadInfo();
            uploadInfo.setFuiClassType(classType);
            uploadInfo.setFuiSecondaryClass(secondaryClassType);
            uploadInfo.setFuiDataId(dataId);
            //uploadInfo.setLoanId(loanId);
            uploadInfo.setFuiState(EntityState.STATE_ENABLE);
            ListResult<FileUploadInfo> result = infoService.queryList(uploadInfo);
            if (result.getData() != null) {
                List<FileUploadInfoVO> infoVOs = FileUploadInfoVOConverter.convertToList(result.getData());
                vo.setTotal(infoVOs.size());
                vo.setRows(infoVOs);
            }
        }

        return vo;
    }
}
