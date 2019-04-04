package com.autoserve.abc.web.module.screen.common.json;

import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.intf.upload.FileUploadService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPlainVO;

/**
 * 上传文件(头像，照片)
 *
 * @author RJQ 2014/12/25 17:07.
 */
public class UploadFile {

    @Autowired
    private FileUploadService fileUploadService;

    public JsonPlainVO<String> execute(ParameterParser params) {
        FileItem fileItem = params.getFileItem("Filedata");
        PlainResult<String> uploadResult = fileUploadService.uploadFile(fileItem);
        return ResultMapper.toPlainVO(uploadResult);
    }
}
