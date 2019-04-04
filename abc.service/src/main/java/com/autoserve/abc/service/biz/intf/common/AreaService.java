/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.common;

import com.autoserve.abc.dao.dataobject.AreaDO;

import java.util.List;
import java.util.Map;
import com.autoserve.abc.service.biz.result.ListResult;

/**
 * 类AreaService.java的实现描述：TODO 类实现描述
 *
 * @author J.YL 2014年12月4日 下午3:28:07
 */
public interface AreaService {

    /**
     * 获取所有一级地区
     *
     * @return
     */
    public List<AreaDO> queryFirstArea();

    /**
     * 通过一级地区的地区代码获取二级地区
     *
     * @param superAreaCode
     * @return
     */
    public List<AreaDO> querySecondArea(String superAreaCode);

    /**
     * 通过地区编号获取父地区
     *
     * @param areaCode
     * @return
     */
    public String querySuperByAreaCode(String areaCode);

    /**
     * 通过地区二级代码查询完整地区信息以“-”连接
     *
     * @param areaCode
     * @return
     */
    public String queryByAreaCode(String areaCode);

    /**
     * 通过地区编号批量查询地区信息
     * 
     * @param areaCodes
     * @return
     */
    public ListResult<AreaDO> queryAreasByAreaCodes(List<String> areaCodes);

    /**
     * 通过多个地区二级代码查询多个完整地区信息
     *
     * @param codeList 二级地区代码list
     * @return Map<String, String>
     */
    public Map<String, String> queryMapByAreaCodes(List<String> codeList);
    
    /**
     * 获取所有地区
     *
     * @return
     */
    public List<AreaDO> queryAllArea();
}
