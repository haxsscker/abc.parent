package com.autoserve.abc.web.module.screen.account.myAward.json;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ScoreConfigDO;
import com.autoserve.abc.service.biz.intf.score.ScoreConfigService;
import com.autoserve.abc.service.biz.result.PageResult;

/**
 * 类QueryCreditGoodsJSON.java的实现描述：翻页查询积分兑换物品
 * 
 * @author WangMing 2015年5月14日 下午4:09:20
 */
public class QueryCreditGoodsJSON {

    @Resource
    private ScoreConfigService scoreConfigService;

    public String execute(Context context, ParameterParser params) {

        Integer pageSize = 5;
        Integer currentPage = params.getInt("currentPage") + 1;
        PageCondition pageCondition = new PageCondition(currentPage, pageSize);
        PageResult<ScoreConfigDO> pageResult = scoreConfigService.queryScoreConfigList(new ScoreConfigDO(),
                pageCondition);
        String html = addGoogsHtml(pageResult.getData());
        return html;
    }

    private String addGoogsHtml(List<ScoreConfigDO> scoreConfigDOs) {
        StringBuffer html = new StringBuffer();
        for (ScoreConfigDO scoreConfigDO : scoreConfigDOs) {
            html.append("<div class='row'>");
            html.append("<a class='sc_list clearfix  mt25px    col-xs-12 col-ms-12 col-md-6' href='/account/myAward/RedPacketInfo?packetId="
                    + scoreConfigDO.getScId() + "'>");
            html.append("<div class='row'>");
            html.append("<div class='col-xs-4 col-md-4 pull-left sc_img'>");
            html.append("<img src='" + scoreConfigDO.getScScorePic() + "'/>");
            html.append("</div>");
            html.append("<div class='col-xs-8 col-md-8 sc_arc pull-right'>");
            html.append("<h3>" + scoreConfigDO.getScScoreCash() + "元红包</h3>");
            html.append("<p><span>积分:</span><strong>" + scoreConfigDO.getScMaxScore() + "</strong></p>");
            html.append("</div>");
            html.append("</div>");
            html.append("</a>");
            html.append("</div>");
        }
        return html.toString();
    }
}
