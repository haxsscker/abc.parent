package com.autoserve.abc.web.module.screen.redrewardmanage.json;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.InvestSearchDO;
import com.autoserve.abc.dao.dataobject.search.RedSearchDO;
import com.autoserve.abc.dao.intf.InvestDao;
import com.autoserve.abc.service.biz.entity.Red;
import com.autoserve.abc.service.biz.enums.RedenvelopeType;
import com.autoserve.abc.service.biz.intf.redenvelope.RedService;
import com.autoserve.abc.service.biz.intf.redenvelope.RedsendService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPageVO;

/**
 * web 层 项目奖励红包实现
 * 
 * @author lipeng 2015年1月5日 上午9:42:58
 */
public class ProjectRewardListView {
    private static Logger  logger = LoggerFactory.getLogger(ProjectRewardListView.class);

    @Resource
    private RedsendService redsendService;
    @Resource
    private InvestDao      investDao;
    @Resource
    private RedService     redService;

    public JsonPageVO<Red> execute(ParameterParser params) {

        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);

        RedSearchDO redSearchDO = new RedSearchDO();
        redSearchDO.setRedType(RedenvelopeType.PROJECT_RED.type);

        String searchForm = params.getString("searchForm");
        if (StringUtils.isNotBlank(searchForm)) {
            try {
                JSONObject searchFormJson = JSON.parseObject(searchForm);
                JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));

                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                    String field = String.valueOf(itemJson.get("Field"));
                    String value = String.valueOf(itemJson.get("Value"));

                    // 奖励主题
                    if ("redTheme".equals(field)) {
                        redSearchDO.setRedTheme(value);
                    }
                    // 发放开始时间
                    else if ("redSendtimeStart".equals(field)) {
                        redSearchDO.setRedSendtimeStart(value);
                    }
                    //发放结束时间
                    else if ("redSendtimeEnd".equals(field)) {
                        redSearchDO.setRedSendtimeEnd(value);
                    }
                    //到期日期开始时间
                    else if ("redClosetimeStart".equals(field)) {
                        redSearchDO.setRedClosetimeStart(value);
                    }
                    //到期日期结束时间
                    else if ("redClosetimeEnd".equals(field)) {
                        redSearchDO.setRedClosetimeEnd(value);
                    }
                }
            } catch (Exception e) {
                logger.error("红包信息－项目奖励红包－搜索查询 查询参数解析出错", e);
            }
        }
        PageResult<Red> pageResult = redService.queryList(null, redSearchDO, pageCondition);
        List<Red> redList = new ArrayList<Red>();

        for (Red red : pageResult.getData()) {
            InvestSearchDO searchDO = new InvestSearchDO();
            searchDO.setBidId(red.getRedBizid());
            int num = investDao.countListBySearchParam(searchDO);
            if (red.getRedAmt() != null) {
                red.setTotalAmount(red.getRedAmt() * num);
            } else {
                red.setTotalAmount(0.0);
            }
            redList.add(red);
        }
        pageResult.setData(redList);
        return ResultMapper.toPageVO(pageResult);
    }
}
