package com.autoserve.abc.service.biz.intf.upload;

import java.util.List;

import com.autoserve.abc.dao.dataobject.FileUploadInfoDO;
import com.autoserve.abc.service.biz.entity.FileUploadInfo;
import com.autoserve.abc.service.biz.enums.FileUploadClassType;
import com.autoserve.abc.service.biz.enums.FileUploadSecondaryClass;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author RJQ 2014/12/25 11:21.
 */
public interface FileUploadInfoService {

    /**
     * 新增上传文件信息
     *
     * @param fileUploadClassType      上传文件的模块类型
     * @param fileUploadSecondaryClass 上传文件的二级模块类型，可为空
     * @param dataId                   数据记录
     * @param fileName                 文件名
     * @param filePath                 文件路径
     * @return
     */
    public PlainResult<Integer> createFileUploadInfo(FileUploadClassType fileUploadClassType,
                                                     FileUploadSecondaryClass fileUploadSecondaryClass,
                                                     String dataId, String fileName, String filePath,int loanId);

    public PlainResult<Integer> createFileUploadInfo(FileUploadInfo fileUploadInfo);

    /**
     * 查询上传文件列表
     *
     * @param fileUploadInfo 查询条件，条件为空时传new FileUploadInfo()
     * @return
     */
    public ListResult<FileUploadInfo> queryList(FileUploadInfo fileUploadInfo);

    /**
     * 启用文件上传记录
     *
     * @param fileUploadId 主键
     * @return
     */
    public BaseResult enableFileUploadInfo(int fileUploadId);

    /**
     * 修改上传文件记录
     *
     * @param fileUploadInfo 待修改
     * @return
     */
    public BaseResult modifyById(FileUploadInfo fileUploadInfo);

    /**
     * 修改上传文件记录（根据dataID修改）
     *
     * @param fileUploadInfo 待修改
     * @return
     */
    public BaseResult modifyByDataId(FileUploadInfo fileUploadInfo);

    /**
     * 删除上传文件
     *
     * @param id 主键
     * @return
     */
    public BaseResult removeFileUploadInfoById(int id);

    public BaseResult removeFileUploadInfoByDO(FileUploadInfoDO fileUploadInfoDO);

    /**
     * 批量插入(插入之前将同类型的旧记录删除)
     *
     * @param fileUploadInfos
     * @return
     */
    public BaseResult createFileUploadInfos(FileUploadClassType fileUploadClassType,
                                            FileUploadSecondaryClass fileUploadSecondaryClass,
                                            String dataId, String fileName, List<String> filePaths,int loanId);

    
    /**
     * 根据loanId查询
     * @param loanId
     * @param fuiSecondaryClass
     * @return
     */
    public List<FileUploadInfoDO> findListByFileUrl(String loanFileUrl, Integer fuiSecondaryClass);
    
    /**
     * 根据ID查找某个附件
     * @param id
     * @return
     */
    public FileUploadInfoDO findFileUploadInfoById(int id);
    
    /**
     * 查找某一类项目文件编号
     * @param dataId
     * @return
     */
    public List<String> findFuiIdUseLoanNo(String dataId);
    
    /**
     * 删除相关子项目某张图
     * @param dataId
     * @param filename
     */
    public void deleteFuiIdUseLoanNo(String dataId, String filename);
}
