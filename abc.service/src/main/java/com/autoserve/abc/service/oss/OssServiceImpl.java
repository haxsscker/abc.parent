package com.autoserve.abc.service.oss;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.citrus.util.StringUtil;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.CannedAccessControlList;
import com.aliyun.openservices.oss.model.OSSObject;
import com.aliyun.openservices.oss.model.OSSObjectSummary;
import com.aliyun.openservices.oss.model.ObjectListing;
import com.aliyun.openservices.oss.model.ObjectMetadata;

/**
 * 附件上传到 oss
 */
@Service
public class OssServiceImpl implements OssService {
    private static final Log    log                     = LogFactory.getLog(OssServiceImpl.class);

    private String              accessId                = "ACSfWpoAv17ck1uX";
    private String              accessKey               = "gI1Vh0uh47";
    private String              ossEndpoint             = "oss.aliyuncs.com";
    private String              defaultBucket;

    private static final String BUCKET_NAME_PLACEHOLDER = "#bucketName#";
    private String              downloadHyperlink;

    private OSSClient           client;

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setOssEndpoint(String ossEndpoint) {
        this.ossEndpoint = ossEndpoint;
    }

    public void setDefaultBucket(String defaultBucket) {
        this.defaultBucket = defaultBucket;
    }

    public void setDownloadHyperlink(String downloadHyperlink) {
        this.downloadHyperlink = downloadHyperlink;
    }

    public void init() {
        if (StringUtils.isEmpty(accessId) || StringUtils.isEmpty(accessKey) || StringUtils.isEmpty(ossEndpoint)) {
            throw new RuntimeException("OssService init fail! parameters missing!");
        }

        client = new OSSClient(ossEndpoint, accessId, accessKey);
    }

    // 如果Bucket不存在，则创建它。

    public boolean ensureBucket(String bucket) {
        if (client.doesBucketExist(bucket)) {
            return true;
        }

        return createBucket(bucket);
    }

    public boolean storeString(String bucket, String key, String str, String contentType) {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            InputStream input = new ByteArrayInputStream(bytes);
            return storeStream(bucket, key, input, bytes.length, contentType);
        } catch (Exception e) {
            log.error("OssService storeString error! bucket:" + bucket + ", key:" + key, e);
            return false;
        }
    }

    public String getString(String bucket, String key) {
        try {
            OSSObject object = client.getObject(bucket, key);
            if (object == null || object.getObjectContent() == null) {
                return null;
            }
            InputStream is = object.getObjectContent();
            return IOUtils.toString(is, "UTF-8");
        } catch (Exception e) {
            log.error("OssService getString error! bucket:" + bucket + ", key:" + key, e);
            return null;
        }
    }

    public boolean delete(String bucket, String fileName) {
        try {
            client.deleteObject(bucket, fileName);
            return true;
        } catch (Exception e) {
            log.error("OssService deleteString error! bucket:" + bucket + ", key:" + fileName, e);
            return false;
        }
    }

    /**
     * 创建存储块
     *
     * @param bucketName 存储块名称
     * @return
     */
    public boolean createBucket(String bucketName) {
        try {
            client.createBucket(bucketName);
            client.setBucketAcl(bucketName, CannedAccessControlList.Private);
            return true;
        } catch (Exception e) {
            log.error("OssService create bucket:" + bucketName + " error!", e);
            return false;
        }
    }

    /**
     * 删除存储块
     *
     * @param bucketName存储块名
     * @return
     */
    public boolean deleteBucket(String bucketName) {
        try {
            client.deleteBucket(bucketName);
            return true;
        } catch (Exception e) {
            log.error("OssService delete bucket:" + bucketName + " error!", e);
            return false;
        }
    }

    /**
     * 文件流存储
     *
     * @param bucketName
     * @param key 文件名
     * @param input
     * @param length
     * @param contentType 文件类型MIME如text/plain
     * @return
     */
    public boolean storeStream(String bucketName, String key, InputStream input, long length, String contentType) {
        try {
            ObjectMetadata objectMeta = new ObjectMetadata();
            objectMeta.setContentLength(length);

            if (StringUtils.isBlank(contentType)) {
                objectMeta.setContentType("text/plain");
            } else {
                objectMeta.setContentType(contentType);
            }

            client.putObject(bucketName, key, input, objectMeta);
            return true;
        } catch (Exception e) {
            log.error("OssService storeSteram error! bucket:" + bucketName + ", key:" + key, e);
            return false;
        }
    }

    public InputStream getStream(String bucket, String key) {
        try {
            OSSObject object = client.getObject(bucket, key);
            if (object == null || object.getObjectContent() == null) {
                return null;
            }
            InputStream is = object.getObjectContent();
            return is;
        } catch (Exception e) {
            log.error("OssService getSteram error! bucket:" + bucket + ", key:" + key, e);
            return null;
        }
    }

    public List<String> getKeys(String bucket, String prefixKey) {
        List<String> keyList = new ArrayList<String>();
        try {
            ObjectListing list = client.listObjects(bucket, prefixKey);
            List<OSSObjectSummary> listSummary = list.getObjectSummaries();
            for (OSSObjectSummary summary : listSummary) {
                keyList.add(summary.getKey());
            }
        } catch (Exception e) {
            log.error("OssService getKeys error! bucket:" + bucket + ", prefixkey:" + prefixKey, e);
            return null;
        }
        if (keyList.size() > 0) {
            return keyList;
        }
        return null;
    }

    /**
     * 获取文件列表(获取下载链接)
     *
     * @param bucketName
     * @param prefixKey
     * @return
     */
    public List<String> getFileNames(String bucketName, String prefixKey) {
        List<String> keyList = new ArrayList<String>();
        try {
            ObjectListing list = client.listObjects(bucketName, prefixKey);
            List<OSSObjectSummary> listSummary = list.getObjectSummaries();
            for (OSSObjectSummary summary : listSummary) {
                keyList.add(summary.getKey());
            }
        } catch (Exception e) {
            log.error("OssService getKeys error! bucket:" + bucketName + ", prefixkey:" + prefixKey, e);
            return null;
        }
        if (keyList.size() > 0) {
            return keyList;
        }
        return null;
    }

    private String getDownloadLink(String bucket, String fileName) {
        return this.downloadHyperlink.replace(BUCKET_NAME_PLACEHOLDER, bucket).concat(fileName);
    }

    /**
     * 生成文件名字去重
     *
     * @param fileName
     * @return
     */
    private String generateFileName(String fileName) {
        return UUID.randomUUID().toString() + getFileType(fileName);
        //可以改进为：(文件名+ip+时间)
    }

    private String getFileType(String fileName) {
        if (StringUtils.isNotBlank(fileName)) {
            fileName = fileName.trim();

            int dotInd = fileName.lastIndexOf('.');
            if (dotInd >= 0) {
                String result = fileName.substring(dotInd);
                return result.replace('?', '_').replace('#', '_');
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取服务器IP
     *
     * @return
     */
    private String getHostIP() {
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
            if (addr == null) {
                return null;
            }
            return addr.getHostAddress();
        } catch (UnknownHostException e) {
            log.error("file upload get ip error:", e);
            return null;
        }

    }

    /**
     * 补充IP位数
     *
     * @param str
     * @param length
     * @return
     */
    private static String addZero(String str, int length) {
        StringBuffer s = new StringBuffer();
        s.append(str);
        while (s.length() < length) {
            s.insert(0, "0");
        }
        return s.toString();
    }

    /**
     * 上传文件
     *
     * @param file
     * @param bucketName 存储块名,如果为空则启用配置的默认存储区块
     * @param fileName 文件名，如果不输入，则默认取实体文件名
     * @return 下载文本的超链接
     * @throws FileNotFoundException
     */
    @Override
    public String uploadFile(File file, String bucketName, String destFileName) throws FileNotFoundException {

        if (StringUtils.isBlank(bucketName)) {
            bucketName = defaultBucket;
        }

        if (file == null) {
            return null;
        }
        if (StringUtils.isBlank(bucketName)) {
            return null;
        }

        if (StringUtils.isBlank(destFileName)) {
            destFileName = generateFileName(file.getName());
        }

        if (file.length() > 5368709120L) {
            throw new IllegalArgumentException("内容长度不能超过5G字节");
        }

        FileInputStream inputStream = new FileInputStream(file);
        String contentType = new MimetypesFileTypeMap().getContentType(file);
        boolean isSuccess = storeStream(bucketName, destFileName, inputStream, file.length(), contentType);

        if (isSuccess) {
            return getDownloadLink(bucketName, destFileName);
        }

        return null;
    }

    @Override
    public String uploadFile(FileItem fileItem, String bucketName, String destFileName) {
        if (StringUtil.isBlank(bucketName)) {
            bucketName = defaultBucket;
        }

        if (StringUtil.isBlank(destFileName)) {
            destFileName = generateFileName(fileItem.getName());
        }

        try {
            boolean success = storeStream(bucketName, destFileName, fileItem.getInputStream(), fileItem.getSize(),
                    fileItem.getContentType());
            if (success) {
                return getDownloadLink(bucketName, destFileName);
            }
        } catch (IOException e) {
            log.error("failed to getInputStream from FileItem, error is " + e.getMessage());
        }

        return null;
    }

}
