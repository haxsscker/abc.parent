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

    private String[]          imageType = { "png", "jpg", "jpeg", "bmp" };

    public JsonPlainVO<String> execute(ParameterParser params) {

        PlainResult<String> uploadResult = new PlainResult<String>();

        FileItem fileItem = params.getFileItem("fileField");

        if (fileItem != null) {
            //获取文件后缀名称
            String filename = fileItem.getName();
            String filetype = filename.substring(filename.lastIndexOf(".") + 1);
            boolean flag = true;
            for (String type : imageType) {
                if (type.equals(filetype)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                uploadResult.setSuccess(false);
                uploadResult.setMessage("上传失败,文件格式限定：png/jpg/jpeg/bmp");
                return ResultMapper.toPlainVO(uploadResult);
            }

            //获取文件大小
            long size = fileItem.getSize();
            if (size > 5000000) {
                uploadResult.setSuccess(false);
                uploadResult.setMessage("上传失败,文件大小不能超过5M.");
                return ResultMapper.toPlainVO(uploadResult);
            }
        }
        //验证通过符合文件长传标准
        uploadResult = fileUploadService.uploadFile(fileItem);
        return ResultMapper.toPlainVO(uploadResult);
    }
}
