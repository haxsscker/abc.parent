package com.autoserve.abc.service.biz.impl.upload.oss;

import java.io.File;
import java.io.FileNotFoundException;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autoserve.abc.service.biz.intf.upload.FileUploadService;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.oss.OssService;

/**
 * 文件上传到oss
 * 
 * @author segen189 2015年2月26日 下午3:01:35
 */
public class OssUploadServiceImpl implements FileUploadService {
    private static final Logger log = LoggerFactory.getLogger(OssUploadServiceImpl.class);

    @Resource
    private OssService          ossService;

    public PlainResult<String> uploadFile(File file) {
        PlainResult<String> result = new PlainResult<String>();

        try {
            result.setData(ossService.uploadFile(file, null, null));
        } catch (FileNotFoundException e) {
            log.error("文件{}不存在", file.getName());
            result.setError(CommonResultCode.BIZ_ERROR, "文件不存在");
        }

        return result;
    }

    @Override
    public PlainResult<String> uploadFile(FileItem fileItem) {
        PlainResult<String> result = new PlainResult<String>();
        result.setData(ossService.uploadFile(fileItem, null, null));
        return result;
    }

    @Override
    public PlainResult<String> uploadFile(FileItem fileItem, String destFileName) {
        PlainResult<String> result = new PlainResult<String>();
        result.setData(ossService.uploadFile(fileItem, null, destFileName));
        return result;
    }

	@Override
	public String getBasePath() {
		throw new UnsupportedOperationException("没有实现");
//		return null;
	}

}
