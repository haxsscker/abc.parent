package com.autoserve.abc.service.biz.intf.redenvelope;

import java.util.List;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RedDO;
import com.autoserve.abc.dao.dataobject.RedReportDO;
import com.autoserve.abc.dao.dataobject.search.RedSearchDO;
import com.autoserve.abc.service.biz.entity.Red;
import com.autoserve.abc.service.biz.entity.Redsend;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * service 层 红包接口
 * 
 * @author fangrui 2014年12月27日 下午3:13:25
 */
public interface RedService {
    /**
     * 新增红包
     * 
     * @param RedDO 红包信息
     * @return BaseResult
     */
    public BaseResult createRed(Red red);

    /**
     * 批量新增红包
     * 
     * @param listRed
     * @return PlainResult<Integer>
     */
    public PlainResult<Integer> batchCreateRed(List<Red> listRed);

    /**
     * 批量发放红包
     * 
     * @param sendList
     * @return PlainResult<Integer>
     */
    public PlainResult<Integer> batchSendRed(List<Redsend> sendList);

    /**
     * 删除红包
     * 
     * @param redId 红包id
     * @return BaseResult
     */
    public BaseResult removeRed(int redId);

    /**
     * 将红包状态改为无效
     * 
     * @param redId 用户ID
     * @return BaseResult
     */
    public BaseResult disableRed(int redId);

    /**
     * 将红包状态改为有效
     * 
     * @param redId 红包ID
     * @return BaseResult
     */
    public BaseResult enableRed(int redId);

    /**
     * 修改部分信息
     * 
     * @param RedDO
     * @return BaseResult
     */
    public BaseResult modifyRedByPrimaryKey(Red red);

    /**
     * 查询单个红包信息
     * 
     * @param redId 红包id
     * @return PlainResult<RedDO>
     */
    public PlainResult<RedDO> findById(int redId);

    /**
     * 查询单个红包信息
     * 
     * @param Red 查询条件
     * @return PlainResult<Red>
     */
    public PlainResult<Red> findByParam(Red param);

    /**
     * 查询单个红包实体信息
     * 
     * @param redId 红包id
     * @return PlainResult<Red>
     */
    public PlainResult<Red> findEntityById(int redId);

    /**
     * 查询红包列表
     * 
     * @param red 查询条件，可选
     * @param RedSearchDO 查询条件，可选
     * @param pageCondition 分页条件，必选
     * @return PageResult<RedDO>
     */
    public PageResult<Red> queryList(Red red, RedSearchDO redSearchDO, PageCondition pageCondition);

    /**
     * 查询红包列表
     * 
     * @param RedDO 查询条件，可选
     * @param pageCondition 分页条件
     * @return PageResult<RedDO>
     */
    public ListResult<Red> queryList(Red red, RedSearchDO redSearchDO);
    
    public PageResult<RedReportDO> redReport(RedReportDO redReport ,PageCondition pageCondition);
    
    public int redReportCount(RedReportDO redReport);

}
