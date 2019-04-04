package com.autoserve.abc.service.oss;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.fileupload.FileItem;

/**
 * 附件上传到 oss<br>
 * 参考文档：http://www.aliyun.com/product/oss/
 */
public interface OssService {

    String uploadFile(File file, String bucketName, String destFileName) throws FileNotFoundException;

    String uploadFile(FileItem fileItem, String bucketName, String destFileName);

}
