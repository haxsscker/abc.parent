package com.autoserve.abc.service.biz.enums;

/**
 * @author yuqing.zheng
 *         Created on 2014-12-08,15:09
 */
public enum UploadStatus {
    /**
     * 上传成功
     */
    SUCCESS(1),

    /**
     * 上传失败（重试次数已达最大限制）
     */
    FAILED(0),

    /**
     * 待重试（前次失败，但失败次数未达最大限制）
     */
    RETRYING(-1);

    public int status;

    private UploadStatus(int status) {
        this.status = status;
    }

    public static UploadStatus valueOf(Integer status) {
        for (UploadStatus uploadStatus : values()) {
            if (status != null && status == uploadStatus.status) {
                return uploadStatus;
            }
        }

        return null;
    }
}
