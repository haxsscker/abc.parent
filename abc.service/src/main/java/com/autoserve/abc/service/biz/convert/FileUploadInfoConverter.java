package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.FileUploadInfoDO;
import com.autoserve.abc.service.biz.entity.FileUploadInfo;
import com.autoserve.abc.service.biz.enums.EntityState;
import com.autoserve.abc.service.biz.enums.FileUploadClassType;
import com.autoserve.abc.service.biz.enums.FileUploadSecondaryClass;
import com.autoserve.abc.service.exception.BusinessException;
import net.sf.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RJQ 2014/12/25 14:59.
 */
public class FileUploadInfoConverter {
    public static FileUploadInfo convertToEntity(FileUploadInfoDO fileUploadInfoDO) {
        FileUploadInfo fileUploadInfo = new FileUploadInfo();
        BeanCopier beanCopier = BeanCopier.create(FileUploadInfoDO.class, FileUploadInfo.class, false);
        beanCopier.copy(fileUploadInfoDO, fileUploadInfo, null);
        fileUploadInfo.setFuiClassType(FileUploadClassType.valueOf(fileUploadInfoDO.getFuiClassType()));
        fileUploadInfo.setFuiSecondaryClass(FileUploadSecondaryClass.valueOf(fileUploadInfoDO.getFuiSecondaryClass()));
        fileUploadInfo.setFuiState(EntityState.valueOf(fileUploadInfoDO.getFuiState()));
        fileUploadInfo.setLoanId(fileUploadInfoDO.getLoanId());
        return fileUploadInfo;
    }

    public static FileUploadInfoDO convertToDO(FileUploadInfo fileUploadInfo) {
        FileUploadInfoDO fileUploadInfoDO = new FileUploadInfoDO();
        BeanCopier beanCopier = BeanCopier.create(FileUploadInfo.class, FileUploadInfoDO.class, false);
        beanCopier.copy(fileUploadInfo, fileUploadInfoDO, null);
        fileUploadInfoDO.setFuiClassType(fileUploadInfo.getFuiClassType() == null ? null : fileUploadInfo.getFuiClassType().getType());
        fileUploadInfoDO.setFuiSecondaryClass(fileUploadInfo.getFuiSecondaryClass() == null ? null : fileUploadInfo.getFuiSecondaryClass().getType());
        fileUploadInfoDO.setFuiState(fileUploadInfo.getFuiState() == null ? null : fileUploadInfo.getFuiState().getState());
        fileUploadInfoDO.setLoanId(fileUploadInfo.getLoanId());

        return fileUploadInfoDO;
    }

    public static List<FileUploadInfo> convertToEntityList(List<FileUploadInfoDO> list) {
        if (list == null || list.isEmpty())
            throw new BusinessException("传入的list为空");
        List<FileUploadInfo> result = new ArrayList<FileUploadInfo>();
        for (FileUploadInfoDO infoDO : list) {
            result.add(convertToEntity(infoDO));
        }
        return result;
    }

    public static List<FileUploadInfoDO> convertToDOList(List<FileUploadInfo> list) {
        if (list == null || list.isEmpty())
            throw new BusinessException("传入的list为空");
        List<FileUploadInfoDO> result = new ArrayList<FileUploadInfoDO>();
        for (FileUploadInfo info : list) {
            result.add(convertToDO(info));
        }
        return result;
    }
}
