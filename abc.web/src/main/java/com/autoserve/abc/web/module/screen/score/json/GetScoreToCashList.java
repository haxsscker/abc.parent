package com.autoserve.abc.web.module.screen.score.json;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.service.biz.entity.ScoreUsageRecord;
import com.autoserve.abc.service.biz.intf.score.ScoreUsageRecordService;
import com.autoserve.abc.web.convert.ScoreUsageRecordVOConverter;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.score.ScoreUsageRecordVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author RJQ 2014/12/16 21:43.
 */
public class GetScoreToCashList {
    @Resource
    private ScoreUsageRecordService scoreService;

    private static Logger logger = LoggerFactory.getLogger(GetScoreToCashList.class);

    public JsonPageVO<ScoreUsageRecordVO> execute(ParameterParser params) {
        JsonPageVO<ScoreUsageRecordVO> pageVO = new JsonPageVO<ScoreUsageRecordVO>();
        List<ScoreUsageRecord> scoreUsageRecords;

        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);

        ScoreUsageRecordVO record = new ScoreUsageRecordVO();

//        {
//            "Items":[{
//            "Field":"cst_user_name", "Value":"asd", "Method":"Contains"
//        },{
//            "Field":"sco_to_score", "Value":"123", "Method":"GreaterThanOrEqual"
//        },{
//            "Field":"sco_to_score", "Value":"131", "Method":"LessThanOrEqual"
//        },{
//            "Field":"sco_to_cash_date", "Value":"2014-12-08", "Method":"GreaterThanOrEqual"
//        },{
//            "Field":"sco_to_cash_date", "Value":"2014-12-16", "Method":"LessThanOrEqual"
//        },{
//            "Field":"sco_check_state", "Value":"0", "Method":"Equal"
//        }]}
        String searchForm = params.getString("searchForm");
        if (StringUtils.isNotBlank(searchForm)) {//搜索
            String userName = null;
            Integer scoreStart = null;
            Integer scoreEnd = null;
            Date toCashStartDate = null;
            Date toCashEndDate = null;

            try {
                JSONObject searchFormJson = JSON.parseObject(searchForm);
                JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));

                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                    String field = String.valueOf(itemJson.get("Field"));
                    String value = String.valueOf(itemJson.get("Value"));
                    String method = String.valueOf(itemJson.get("Method"));

                    if ("cst_user_name".equals(field)) {
                        userName = value;
                    } else if ("sco_to_score".equals(field)) {
                        if ("GreaterThanOrEqual".equals(method)) {
                            scoreStart = Integer.valueOf(value);
                        } else if ("LessThanOrEqual".equals(method)) {
                            scoreEnd = Integer.valueOf(value);
                        }
                    } else if ("sco_to_cash_date".equals(field)) {
                        if ("GreaterThanOrEqual".equals(method)) {
                            toCashStartDate = DateUtil.parseDate(value, DateUtil.DATE_FORMAT);
                        } else if ("LessThanOrEqual".equals(method)) {
                            toCashEndDate = DateUtil.parseDate(value, DateUtil.DATE_FORMAT);
                        }
                    } else if ("sco_check_state".equals(field)) {
                        record.setSco_check_state(value);
                    }
                }
            } catch (Exception e) {
                logger.error("积分兑现审核－搜索查询 查询参数解析出错", e);
            }
            scoreUsageRecords = scoreService.queryReviewOpLogBySurId(ScoreUsageRecordVOConverter.convertVOToEntity(record), userName,
                    scoreStart, scoreEnd, toCashStartDate, toCashEndDate, pageCondition).getData();

        } else {
            scoreUsageRecords = scoreService.queryReviewOpLogBySurId(new ScoreUsageRecord(), pageCondition).getData();
        }

        List<ScoreUsageRecordVO> list = ScoreUsageRecordVOConverter.convertToVOList(scoreUsageRecords);
        pageVO.setRows(list);
        pageVO.setTotal(list.size());
        return pageVO;
    }
}
