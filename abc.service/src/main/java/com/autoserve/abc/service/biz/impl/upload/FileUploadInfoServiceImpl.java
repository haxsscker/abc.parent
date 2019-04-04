package com.autoserve.abc.service.biz.impl.upload;

import com.autoserve.abc.dao.dataobject.FileUploadInfoDO;
import com.autoserve.abc.dao.intf.FileUploadInfoDao;
import com.autoserve.abc.service.biz.convert.FileUploadInfoConverter;
import com.autoserve.abc.service.biz.entity.FileUploadInfo;
import com.autoserve.abc.service.biz.enums.EntityState;
import com.autoserve.abc.service.biz.enums.FileUploadClassType;
import com.autoserve.abc.service.biz.enums.FileUploadSecondaryClass;
import com.autoserve.abc.service.biz.intf.upload.FileUploadInfoService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RJQ 2014/12/25 13:42.
 */
@Service
public class FileUploadInfoServiceImpl implements FileUploadInfoService {
    @Resource
    private FileUploadInfoDao fileUploadInfoDao;

    @Override
    public PlainResult<Integer> createFileUploadInfo(FileUploadClassType fileUploadClassType,
                                                     FileUploadSecondaryClass fileUploadSecondaryClass,
                                                     String dataId, String fileName, String filePath,int loanId) {
        PlainResult<Integer> result = new PlainResult<Integer>();
        FileUploadInfoDO uploadInfoDO = new FileUploadInfoDO();
        uploadInfoDO.setFuiClassType(fileUploadClassType == null ? null : fileUploadClassType.getType());
        uploadInfoDO.setFuiSecondaryClass(fileUploadSecondaryClass == null ? null : fileUploadSecondaryClass.getType());
        uploadInfoDO.setFuiDataId(dataId);
        uploadInfoDO.setFuiFileName(fileName);
        uploadInfoDO.setFuiFilePath(filePath);
        uploadInfoDO.setLoanId(loanId);
        uploadInfoDO.setFuiState(EntityState.STATE_DISABLE.getState());
        int val = fileUploadInfoDao.insert(uploadInfoDO);
        if (val <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "插入上传文件记录失败！");
            return result;
        }
        result.setData(uploadInfoDO.getFuiId());
        return result;
    }

    @Override
    public PlainResult<Integer> createFileUploadInfo(FileUploadInfo fileUploadInfo) {
        PlainResult<Integer> result = new PlainResult<Integer>();
        FileUploadInfoDO uploadInfoDO = FileUploadInfoConverter.convertToDO(fileUploadInfo);
        int val = fileUploadInfoDao.insert(uploadInfoDO);
        if (val <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "插入上传文件记录失败！");
            return result;
        }
        result.setData(uploadInfoDO.getFuiId());
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public ListResult<FileUploadInfo> queryList(FileUploadInfo fileUploadInfo) {
        ListResult<FileUploadInfo> listResult = new ListResult<FileUploadInfo>();
        int totalCount = fileUploadInfoDao.countListByParam(FileUploadInfoConverter.convertToDO(fileUploadInfo));
        listResult.setCount(totalCount);

        if (totalCount > 0) {
            List<FileUploadInfoDO> fileUploadInfoDOs = fileUploadInfoDao.findListByParam(FileUploadInfoConverter.convertToDO(fileUploadInfo), null);
            listResult.setData(FileUploadInfoConverter.convertToEntityList(fileUploadInfoDOs));
        }

        return listResult;
    }

    @Override
    public BaseResult enableFileUploadInfo(int fileUploadId) {
        BaseResult result = new BaseResult();
        FileUploadInfoDO uploadInfoDO = new FileUploadInfoDO();
        uploadInfoDO.setFuiId(fileUploadId);
        uploadInfoDO.setFuiState(EntityState.STATE_ENABLE.getState());

        int val = fileUploadInfoDao.update(uploadInfoDO);
        if (val <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "启用上传文件记录失败！");
        }
        return result;
    }


    @Override
    public BaseResult removeFileUploadInfoById(int id) {
        BaseResult result = new BaseResult();
        FileUploadInfoDO uploadInfoDO = new FileUploadInfoDO();
        uploadInfoDO.setFuiId(id);
        uploadInfoDO.setFuiState(EntityState.STATE_DELETED.getState());
        int val = fileUploadInfoDao.updateByPrimaryKeySelective(uploadInfoDO);
        if (val <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "删除上传文件记录失败！");
            return result;
        }
        result.setMessage("删除成功！");
        return result;
    }

    @Override
    public BaseResult removeFileUploadInfoByDO(FileUploadInfoDO fileUploadInfoDO){
        fileUploadInfoDO.setFuiState(EntityState.STATE_DELETED.getState());
        fileUploadInfoDao.updateByPrimaryKeySelective(fileUploadInfoDO);
        return BaseResult.SUCCESS;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult modifyById(FileUploadInfo fileUploadInfo) {
        BaseResult result = new BaseResult();

        int val = fileUploadInfoDao.updateByPrimaryKeySelective(FileUploadInfoConverter.convertToDO(fileUploadInfo));
        if (val <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "修改上传文件记录失败！");
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult modifyByDataId(FileUploadInfo fileUploadInfo) {
        BaseResult result = new BaseResult();

        int val = fileUploadInfoDao.updateByDataId(FileUploadInfoConverter.convertToDO(fileUploadInfo));
        if (val <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "修改上传文件记录失败！");
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult createFileUploadInfos(FileUploadClassType fileUploadClassType, FileUploadSecondaryClass fileUploadSecondaryClass, String dataId, String fileName, List<String> filePaths,int loanId) {
        BaseResult result = new BaseResult();
        FileUploadInfoDO infoDO = new FileUploadInfoDO();
        infoDO.setFuiClassType(fileUploadClassType.getType());
        infoDO.setFuiSecondaryClass(fileUploadSecondaryClass.getType());
        infoDO.setFuiDataId(dataId);
        infoDO.setFuiFileName(fileName);
        infoDO.setLoanId(loanId);
        removeFileUploadInfoByDO(infoDO);//将符合此类型的上传文件记录逻辑删除

        List<FileUploadInfoDO> infoDOs = new ArrayList<FileUploadInfoDO>();
        for(String path : filePaths){
            infoDO.setFuiFilePath(path);
            infoDO.setFuiState(EntityState.STATE_ENABLE.getState());
            infoDOs.add(infoDO);
        }

        int val = fileUploadInfoDao.batchInsert(infoDOs);
        if(val <= 0){
            result.setErrorMessage(CommonResultCode.BIZ_ERROR,"批量插入失败！");
        }
        return result;
    }
    
	@Override
	public List<FileUploadInfoDO> findListByFileUrl(String loanFileUrl, Integer fuiSecondaryClass) {
		if(loanFileUrl == null) {
			return new ArrayList<FileUploadInfoDO>();
		}
		return fileUploadInfoDao.findListByFileUrl(loanFileUrl, fuiSecondaryClass);
	}
	
	@Override
	public FileUploadInfoDO findFileUploadInfoById(int id)
	{
		return fileUploadInfoDao.findById(id);
	}
	
	@Override
	public List<String> findFuiIdUseLoanNo(String dataId)
	{
		List<String> list = fileUploadInfoDao.findFuiIdUseLoanNo(dataId);
		if (null == list || list.isEmpty())
		{
			return new ArrayList<String>();
		}
		else
		{
			return list;
		}
	}
	
	@Override
	public void deleteFuiIdUseLoanNo(String dataId, String filename)
	{
		fileUploadInfoDao.deleteFuiIdUseLoanNo(dataId, filename);
	}
}
