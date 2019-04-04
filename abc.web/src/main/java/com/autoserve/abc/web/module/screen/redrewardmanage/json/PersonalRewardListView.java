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
import com.autoserve.abc.dao.dataobject.search.RedSearchDO;
import com.autoserve.abc.service.biz.entity.RedsendJ;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.RedenvelopeType;
import com.autoserve.abc.service.biz.intf.redenvelope.RedsendService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPageVO;

/**
 * 活动派送红包
 * 
 * @author fangrui 2015年1月4日 上午10:05:23
 */
public class PersonalRewardListView {
    private static Logger  logger = LoggerFactory.getLogger(PersonalRewardListView.class);

    @Resource
    private RedsendService redsendService;

    public JsonPageVO<RedsendJ> execute(ParameterParser params) {
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);

        RedSearchDO redSearchDO = new RedSearchDO();
        redSearchDO.setRedType(RedenvelopeType.PERSON_RED.getType());

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
                logger.error("红包信息－活动派送红包－搜索查询 查询参数解析出错", e);
            }
        }
        redSearchDO.setRedType(RedenvelopeType.PERSON_RED.type);

        PageResult<RedsendJ> pageResult = redsendService.queryListJ(redSearchDO, pageCondition);
        List<RedsendJ> list = new ArrayList<RedsendJ>();

        for (RedsendJ red : pageResult.getData()) {
            if (red.getRedAmount() != null && red.getRedRewardNumber() != null) {
                red.setTotalAmount(red.getRedAmount() * red.getRedRewardNumber());
            } else {
                red.setTotalAmount(0.00);
            }

            // 使用范围
            String scopes = red.getRsUseScope();
            if (StringUtils.isNotBlank(scopes)) {
                String[] scopeArr = StringUtils.split(scopes, "|");
                for (int i = 0; i < scopeArr.length; i++) {
                    for (LoanCategory cate : LoanCategory.values()) {
                        if (String.valueOf(cate.getCategory()).equals(scopeArr[i])) {
                            scopeArr[i] = cate.getPrompt();
                            break;
                        }
                    }
                }

                scopes = StringUtils.join(scopeArr, "|");
                red.setRsUseScope(scopes);
            }

            list.add(red);
        }
        pageResult.setData(list);
        return ResultMapper.toPageVO(pageResult);
    }
}
