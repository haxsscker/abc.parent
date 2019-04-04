package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AutoTransferDO;

public interface AutoTransferDao {

    /**
     * deleteByPrimaryKey
     * 
     * @param Integer atId
     * @return int
     */
    int deleteByPrimaryKey(Integer atId);

    /**
     * insert
     * 
     * @param AutoTransferDO record
     * @return int
     */
    int insert(AutoTransferDO record);

    /**
     * insertSelective
     * 
     * @param AutoTransferDO record
     * @return int
     */
    int insertSelective(AutoTransferDO record);

    /**
     * selectByPrimaryKey
     * 
     * @param Integer atId
     * @return AutoTransferDO
     */
    AutoTransferDO selectByPrimaryKey(Integer atId);

    /**
     * updateByPrimaryKeySelective
     * 
     * @param AutoTransferDO record
     * @return int
     */
    int updateByPrimaryKeySelective(AutoTransferDO record);

    /**
     * updateByPrimaryKey
     * 
     * @param AutoTransferDO record
     * @return int
     */
    int updateByPrimaryKey(AutoTransferDO record);

    int countAll();

    List<AutoTransferDO> selectAll(@Param("pageCondition") PageCondition pageCondition);

    int count(AutoTransferDO at);

    List<AutoTransferDO> findList(@Param("at") AutoTransferDO at, @Param("pageCondition") PageCondition pageCondition);

}
