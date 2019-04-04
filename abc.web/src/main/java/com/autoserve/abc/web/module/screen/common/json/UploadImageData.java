package com.autoserve.abc.web.module.screen.common.json;

import java.util.Date;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.entity.FileUploadInfo;
import com.autoserve.abc.service.biz.enums.EntityState;
import com.autoserve.abc.service.biz.enums.FileUploadClassType;
import com.autoserve.abc.service.biz.enums.FileUploadSecondaryClass;
import com.autoserve.abc.service.biz.intf.upload.FileUploadInfoService;
import com.autoserve.abc.service.biz.intf.upload.FileUploadService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.FileUploadInfoVOConverter;
import com.autoserve.abc.web.vo.JsonPlainVO;
import com.autoserve.abc.web.vo.file.FileUploadInfoVO;

/**
 * 上传影像资料
 *
 * @author RJQ 2014/12/25 17:07.
 */
public class UploadImageData {

    @Autowired
    private FileUploadService     fileUploadService;

    @Autowired
    private FileUploadInfoService infoService;

    public JsonPlainVO<FileUploadInfoVO> execute(ParameterParser params,
                                                 @Param("fileUploadClassType") Integer classType,
                                                 @Param("fileUploadSecondaryClass") Integer secondaryClassType,
                                                 @Param("dataId") String dataId,
                                                 @Param("loanId") int loanId) {
        JsonPlainVO<FileUploadInfoVO> vo = new JsonPlainVO<FileUploadInfoVO>();

        FileItem fileItem = params.getFileItem("Filedata");
        PlainResult<String> uploadResult = fileUploadService.uploadFile(fileItem);

        if (uploadResult.isSuccess() && 0 != classType) {
            FileUploadInfo uploadInfo = new FileUploadInfo();
            Date d = new Date(); 
            
            uploadInfo.setFuiClassType(FileUploadClassType.valueOf(classType));
            if (0 != secondaryClassType) {
                uploadInfo.setFuiSecondaryClass(FileUploadSecondaryClass.valueOf(secondaryClassType));
            }
            uploadInfo.setFuiDataId(dataId);
            uploadInfo.setFuiState(EntityState.STATE_ENABLE);
            uploadInfo.setFuiFileName(fileItem.getName());
            uploadInfo.setFuiFilePath(uploadResult.getData());
            uploadInfo.setFuiCreateTime(d);
            uploadInfo.setLoanId(loanId);
            PlainResult<Integer> plainResult = infoService.createFileUploadInfo(uploadInfo);
            if (plainResult.isSuccess()) {
                vo.setData(FileUploadInfoVOConverter.convertToVO(uploadInfo));
            }
            
            //如果是贷后资料，每个子项目都插入一份图片信息
            if (1 == classType && 6 == secondaryClassType)
            {
            	List<String> list = infoService.findFuiIdUseLoanNo(dataId);
                for (String li : list)
                {
                	if (!li.equals(dataId))
                	{
                		uploadInfo = new FileUploadInfo();
                		
                		uploadInfo.setFuiClassType(FileUploadClassType.valueOf(classType));
                        if (0 != secondaryClassType) {
                            uploadInfo.setFuiSecondaryClass(FileUploadSecondaryClass.valueOf(secondaryClassType));
                        }
                		uploadInfo.setFuiDataId(li);
                        uploadInfo.setFuiState(EntityState.STATE_ENABLE);
                        uploadInfo.setFuiFileName(fileItem.getName());
                        uploadInfo.setFuiFilePath(uploadResult.getData());
                        uploadInfo.setFuiCreateTime(d);
                        uploadInfo.setLoanId(loanId);
                        
                        infoService.createFileUploadInfo(uploadInfo);
                	}
                }
            }
        }

        return vo;
    }
}
