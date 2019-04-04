package com.autoserve.abc.web.convert;

import com.autoserve.abc.service.biz.entity.FileUploadInfo;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.file.FileUploadInfoVO;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RJQ 2014/12/28 18:23.
 */
public class FileUploadInfoVOConverter {
    public static FileUploadInfoVO convertToVO(FileUploadInfo info) {
        FileUploadInfoVO vo = new FileUploadInfoVO();
        vo.setFile_Id(info.getFuiId());
        vo.setFuiClassType(info.getFuiClassType().getType());
        vo.setFuiSecondaryClass(info.getFuiSecondaryClass().getType());
        vo.setFile_Name(info.getFuiFileName());
        vo.setFile_Path(info.getFuiFilePath());
        vo.setFile_DataId(info.getFuiDataId());
        vo.setFile_DataId(info.getFuiDataId());
        vo.setFile_CreateTime(new DateTime(info.getFuiCreateTime()).toString(DateUtil.DATE_FORMAT));
        vo.setFuiState(info.getFuiState());
        vo.setLoanId(info.getLoanId());
        return vo;
    }

    public static List<FileUploadInfoVO> convertToList(List<FileUploadInfo> infos) {
        List<FileUploadInfoVO> result = new ArrayList<FileUploadInfoVO>();
        if (infos == null || infos.isEmpty()) {
            return result;
        }
        for (FileUploadInfo info : infos) {
            result.add(convertToVO(info));
        }
        return result;
    }
}
