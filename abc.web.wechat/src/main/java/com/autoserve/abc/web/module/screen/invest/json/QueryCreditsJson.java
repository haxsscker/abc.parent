package com.autoserve.abc.web.module.screen.invest.json;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ScoreHistoryDO;
import com.autoserve.abc.dao.dataobject.ScoreHistoryWithValueDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.score.ScoreHistoryService;
import com.autoserve.abc.service.biz.result.PageResult;

/**
 * 类QueryCreditsJson.java的实现描述：翻页查询用户积分
 * 
 * @author WangMing 2015年5月14日 下午1:46:52
 */
public class QueryCreditsJson {

    @Resource
    private ScoreHistoryService scoreHistoryService;

    @Resource
    private HttpSession         session;
    //日期格式化
    private SimpleDateFormat    dateFormat;

    public String execute(Context context, ParameterParser params) {

        User user = (User) session.getAttribute("user");

        int pageSize = 30;
        int currentPage = params.getInt("currentPage") + 1;
        PageCondition pageCondition = new PageCondition(currentPage, pageSize);

        //获取积分记录 
        ScoreHistoryDO tmpDao = new ScoreHistoryDO();
        tmpDao.setShUserId(user.getUserId());
        PageResult<ScoreHistoryWithValueDO> result = scoreHistoryService.queryScoreListByUserId(tmpDao, pageCondition);
        StringBuffer html = new StringBuffer();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (ScoreHistoryWithValueDO swdo : result.getData()) {
            html.append("<tr>");
            html.append("<td class='ext-text-left'><span class='mr5'>" + dateFormat.format(swdo.getCreateTime())
                    + "</span></td>");
            if (swdo.getFlag() == 1) {
                html.append("<td class='color_red text-center'>+" + swdo.getScoreValue() + "分</td>");
            } else {
                html.append("<td class='color_red text-center'>-" + swdo.getScoreValue() + "分</td>");
            }
            html.append("<td class='text-right'><span class='mr5'>" + swdo.getScoreName() + "</span></td>");
            html.append("</tr>");
        }
        return html.toString();
    }
}
