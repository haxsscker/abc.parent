/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.common.json;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.dataobject.AreaDO;
import com.autoserve.abc.service.biz.intf.common.AreaService;

/**
 * @author J.YL 2014年12月4日 上午11:12:11
 */
public class GetAreaInfoData {
    private final Logger        Logger = LoggerFactory.getLogger(GetAreaInfoData.class);
    @Autowired
    private HttpServletResponse resp;

    @Resource
    private AreaService         areaService;

    public void execute(ParameterParser params) {

        String modelAction = params.getString("modelAction");
        String modelActionB = null;
        List<AreaDO> result = null;
        if ("0".equals(modelAction)) {
            result = areaService.queryFirstArea();
        } else if ("1".equals(modelAction)) {
            modelActionB = params.getString("modelActionB");
            result = areaService.querySecondArea(modelActionB);
        }
        StringBuffer resultString = new StringBuffer();
        for (AreaDO area : result) {
            resultString.append(area.getAreaAreaCode());
            resultString.append(",");
            resultString.append(area.getAreaName());
            resultString.append("|");
        }

        try {
            resp.getWriter().write(resultString.toString());
            //            resp.getWriter()
            //                    .write("110,北京市|120,天津市|130,河北省|340,安徽省|350,福建省|620,甘肃省|440,广东省|450,广西壮族自治区|520,贵州省|460,海南省|500,重庆市|510,四川省|410,河南省|230,黑龙江省|420,湖北省|430,湖南省|220,吉林省|360,江西省|210,辽宁省|150,内蒙古自治区|640,宁夏回族自治区|630,青海省|370,山东省|140,山西省|610,陕西省|310,上海市|320,江苏省|540,西藏自治区|650,新疆维吾尔自治区|530,云南省|330,浙江省|");
        } catch (IOException e) {
            Logger.error("[GetAreaInfoData]", e);
        }
    }
}
