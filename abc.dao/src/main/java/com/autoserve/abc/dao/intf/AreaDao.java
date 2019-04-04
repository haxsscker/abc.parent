package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.AreaDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author RJQ 2014/11/13 19:38.
 */
public interface AreaDao extends BaseDao<AreaDO, Integer> {

    /**
     * 找到所有的一级地区
     *
     * @return
     */
    public List<AreaDO> findAllSuperArea();

    /**
     * 通过一级地区代码查询二级地区信息
     *
     * @param superAreaCode
     * @return
     */
    public List<AreaDO> findAreaBySuperAreaCode(String superAreaCode);

    /**
     * 通过二级地区code获取AreaDO
     */
    public AreaDO findAreaByAreaCode(String areaCode);

    /**
     * 返回一个地区字符串
     *
     * @param areaCode
     * @return
     */
    public String findAreaStringByAreaCode(@Param("areaCode") String areaCode);

    /**
     * 查询地区信息list
     *
     * @param codeList 二级地区代码list
     * @return List<Map<String, String>>
     */
    public List<Map<String, String>> findAreaStringsByAreaCodes(List<String> codeList);

    /**
     * 通过areaCode批量查询地区信息
     * 
     * @param areaCodes
     * @return
     */
    public List<AreaDO> findAreasByAreaCodes(List<String> areaCodes);
    
    /**
     * 通查询所有的地区信息
     * 
     * @return
     */
    public List<AreaDO> queryAllArea();
}
