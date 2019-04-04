package com.autoserve.abc.service.biz.intf.government;

import com.autoserve.abc.dao.dataobject.GovGuarDO;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;

import java.util.List;
import java.util.Map;

/**
 * @author RJQ 2014/12/9 14:57.
 */
public interface GovGuarService {

    /**
     * 批量查询为某些机构做担保的机构名，形如{govId，"govName1，govName2"}
     *
     * @param ids 机构id
     * @return Map<Integer,StringBuilder>
     */
    public Map<Integer, StringBuilder> findGovGuarByList(List<Integer> ids);

    /**
     * 查询为指定机构做担保的担保机构信息
     * 返回担保机构id和机构名
     *
     * @param govId 机构id
     * @return Map<Integer, String>
     */
    public Map<Integer, String> findGovGuarByGovId(Integer govId);

    /**
     * 查询为指定机构做担保的担保机构信息列表
     *
     * @param govGuarDO
     * @return
     */
    public ListResult<GovGuarDO> queryList(GovGuarDO govGuarDO);

    /**
     * 批量添加机构和担保机构关系记录
     *
     * @param guarIds 担保机构id
     * @param govId   被担保机构id
     * @return BaseResult
     */
    public BaseResult insertGovGuarByList(List<Integer> guarIds, Integer govId);

    /**
     * 根据被担保机构id删除指定机构的担保机构关联关系
     *
     * @param govId 被担保机构id
     * @return
     */
    public BaseResult removeAllGuarByGovId(Integer govId);

    /**
     * 批量添加机构和担保机构关系记录
     *
     * @param govGuarDOs 担保关系列表
     * @return
     */
    public BaseResult createByDOList(List<GovGuarDO> govGuarDOs);

    /**
     * 根据担保机构id删除指定机构的担保机构关联关系
     *
     * @param guarId 担保机构id
     * @return
     */
    public BaseResult removeByGuarId(Integer guarId);
}
