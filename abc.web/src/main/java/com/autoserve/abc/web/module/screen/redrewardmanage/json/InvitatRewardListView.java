package com.autoserve.abc.web.module.screen.redrewardmanage.json;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.RedSearchDO;
import com.autoserve.abc.service.biz.entity.Red;
import com.autoserve.abc.service.biz.enums.RedenvelopeType;
import com.autoserve.abc.service.biz.intf.redenvelope.RedService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPageVO;

/**
 * web层 推荐邀请红包列表
 * 
 * @author fangrui 2015年1月5日 下午5:26:41
 */
public class InvitatRewardListView {
    private static Logger logger = LoggerFactory.getLogger(InvitatRewardListView.class);

    @Resource
    private RedService    redService;

    @Resource
    private UserService   userService;

    public JsonPageVO<Red> execute(ParameterParser params) {
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);

        RedSearchDO redSearchDO = new RedSearchDO();
        redSearchDO.setRedType(RedenvelopeType.INVIT_RED.type);

        String searchForm = params.getString("searchForm");
        if (StringUtils.isNotBlank(searchForm)) {
            try {
                JSONObject searchFormJson = JSON.parseObject(searchForm);
                JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));

                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                    String field = String.valueOf(itemJson.get("Field"));
                    String value = String.valueOf(itemJson.get("Value"));

                    // 客户名称
                    if ("userName".equals(field)) {
                        redSearchDO.setUserName(value);
                    }
                    // 状态
                    else if ("rsState".equals(field)) {
                        redSearchDO.setRsState(Integer.parseInt(value));
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
                logger.error("红包信息－推荐邀请送红包－搜索查询 查询参数解析出错", e);
            }
        }

        PageResult<Red> pageResult = redService.queryList(null, redSearchDO, pageCondition);
        return ResultMapper.toPageVO(pageResult);
    }

}
