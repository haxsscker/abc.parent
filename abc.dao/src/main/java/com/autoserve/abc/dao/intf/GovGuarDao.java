package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.GovGuarDO;
import com.autoserve.abc.dao.dataobject.GovGuarWithNameDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GovGuarDao extends BaseDao<GovGuarDO, Integer> {

    /**
     * 批量查询
     *
     * @param ids 机构id
     * @return List<GovGuarDO>
     */
    public List<GovGuarWithNameDO> findByList(List<Integer> ids);

    /**
     * 批量添加机构和担保机构关系记录
     *
     * @param guarIds 担保机构id
     * @param govId   被担保机构id
     * @return int
     */
    public int insertGovGuarByList(@Param("list") List<GovGuarDO> govGuarDOs);

    /**
     * 查询为某机构做担保的担保机构信息
     *
     * @param govId 机构id
     * @return List<GovGuarWithNameDO>
     */
    public List<GovGuarWithNameDO> findWithGuarNameByGovId(Integer govId);

    /**
     * 根据被担保机构id删除指定机构的担保机构关联关系
     *
     * @param govId
     * @return
     */
    public int deleteByGovId(Integer govId);

    /**
     * 批量添加机构和担保机构关系记录
     *
     * @param govGuarDOs
     * @return
     */
    @Override
	public int batchInsert(List<GovGuarDO> govGuarDOs);

    /**
     * 根据担保机构id删除指定机构的担保机构关联关系
     *
     * @param guarId
     * @return
     */
    public int deleteByGuarId(Integer guarId);

    /**
     * 查询为指定机构做担保的担保机构信息列表
     *
     * @param govGuarDO
     * @return
     */
    public List<GovGuarDO> findListByParam(GovGuarDO govGuarDO);
}
