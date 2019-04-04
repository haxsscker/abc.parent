/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.citrus.util.StringUtil;
import com.autoserve.abc.dao.dataobject.AreaDO;
import com.autoserve.abc.dao.intf.AreaDao;
import com.autoserve.abc.service.biz.intf.common.AreaService;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;

/**
 * 类AreaServiceImpl.java的实现描述：TODO 类实现描述
 *
 * @author J.YL 2014年12月4日 下午3:56:05
 */
@Service
public class AreaServiceImpl implements AreaService {

    @Resource
    private AreaDao areaDao;

    @Override
    public List<AreaDO> queryFirstArea() {
        return areaDao.findAllSuperArea();
    }

    @Override
    public List<AreaDO> querySecondArea(String superAreaCode) {
        if (StringUtil.isBlank(superAreaCode)) {
            return new ArrayList<AreaDO>();
        }
        return areaDao.findAreaBySuperAreaCode(superAreaCode);
    }

    @Override
    public String querySuperByAreaCode(String areaCode) {
        if (StringUtil.isBlank(areaCode)) {
            return null;
        }
        String superCode = areaDao.findAreaByAreaCode(areaCode).getAreaSuperArea();
        return superCode;
    }

    @Override
    public String queryByAreaCode(String areaCode) {
        if (StringUtil.isBlank(areaCode)) {
            return null;
        }
        return areaDao.findAreaStringByAreaCode(areaCode);
    }

    @Override
    public Map<String, String> queryMapByAreaCodes(List<String> codeList) {
        Map<String, String> map = new HashMap<String, String>();
        List<Map<String, String>> list = areaDao.findAreaStringsByAreaCodes(codeList);
        for (Map<String, String> m : list) {
            map.put(m.get("area_area_code"), m.get("area_info"));
        }
        return map;
    }

    @Override
    public ListResult<AreaDO> queryAreasByAreaCodes(List<String> areaCodes) {
        ListResult<AreaDO> result = new ListResult<AreaDO>();
        List<AreaDO> areaDOs = areaDao.findAreasByAreaCodes(areaCodes);
        if (areaDOs.size() != areaCodes.size()) {
            result.setSuccess(false);
            result.setErrorMessage(CommonResultCode.ERROR_DATA_NOT_EXISTS, "查询数据和结果数据的数据不一致");
        }
        result.setData(areaDOs);
        return result;
    }

	@Override
	public List<AreaDO> queryAllArea() {
		 return areaDao.queryAllArea();
	}
}
