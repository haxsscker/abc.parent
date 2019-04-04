package com.autoserve.abc.service.biz.intf.upload;

import org.apache.commons.fileupload.FileItem;

import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 文件上传服务
 */
public interface FileUploadService {

    PlainResult<String> uploadFile(FileItem fileItem);

    PlainResult<String> uploadFile(FileItem fileItem, String destFileName);
    /**
     * 去掉上传路径尾部的prefix
     * @return
     */
	String getBasePath();
}
