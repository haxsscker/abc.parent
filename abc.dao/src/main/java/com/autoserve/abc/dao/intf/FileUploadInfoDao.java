package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.FileUploadInfoDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileUploadInfoDao extends BaseDao<FileUploadInfoDO, Integer> {
    /**
     * 根据参数获取记录条数
     *
     * @param fileUploadInfoDO
     * @return
     */
    public int countListByParam(@Param("file") FileUploadInfoDO fileUploadInfoDO);

    /**
     * 按条件查询分页结果
     *
     * @param fileUploadInfoDO 查询条件，为空的话传new FileUploadInfoDO()
     * @param pageCondition    分页和排序条件，可选
     * @return List<GovernmentDO>
     */
    public List<FileUploadInfoDO> findListByParam(@Param("file") FileUploadInfoDO fileUploadInfoDO,
                                                  @Param("pageCondition") PageCondition pageCondition);

    /**
     * 选择修改
     *
     * @param fileUploadInfoDO
     * @return
     */
    public int updateByPrimaryKeySelective(FileUploadInfoDO fileUploadInfoDO);

    public int updateByDataId(FileUploadInfoDO fileUploadInfoDO);

    /**
     * 批量插入
     *
     * @param fileUploadInfoDOs
     * @return
     */
    @Override
	public int batchInsert(List<FileUploadInfoDO> fileUploadInfoDOs);

    /**
     * 根据条件删除
     *
     * @param fileUploadInfoDO
     * @return
     */
    public int deleteByDO(FileUploadInfoDO fileUploadInfoDO);
    
    /**
     * 根据loanId查询
     * @param loanId
     * @param fuiSecondaryClass
     * @return
     */
    public List<FileUploadInfoDO> findListByFileUrl(@Param("loanFileUrl") String loanFileUrl, @Param("fuiSecondaryClass") Integer fuiSecondaryClass);
    
    /**
     * 查找一类项目文件编号
     * @param dataId
     * @return
     */
    public List<String> findFuiIdUseLoanNo(@Param("dataId") String dataId);
    
    /**
     * 根据fui_data_id 删除其他管理子项目图片
     * @param fuiId
     */
    void deleteFuiIdUseLoanNo(@Param("dataId") String dataId, @Param("filename") String filename);
}
