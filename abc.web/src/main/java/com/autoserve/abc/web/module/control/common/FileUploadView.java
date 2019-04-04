/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.control.common;

import com.alibaba.citrus.turbine.Context;

/**
 * 影像资料文件上传
 *
 * @author segen189 2014年12月28日 下午5:49:45
 */
public class FileUploadView {

    public void execute(Context context) {
        context.put("isUpload", context.get("isUpload") == null ? "True" : context.get("isUpload").toString());
        context.put("fileUploadClassType", Integer.valueOf(context.get("fileUploadClassType").toString()));
        context.put("fileUploadSecondaryClass", Integer.valueOf(context.get("fileUploadSecondaryClass").toString()));
        context.put("dataId", context.get("dataId").toString());
    }

}
