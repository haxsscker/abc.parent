package com.autoserve.abc.web.module.screen.account.myAward.json;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.RedSearchDO;
import com.autoserve.abc.service.biz.entity.RedsendJ;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.RedenvelopeType;
import com.autoserve.abc.service.biz.enums.RsState;
import com.autoserve.abc.service.biz.intf.redenvelope.RedsendService;
import com.autoserve.abc.service.biz.intf.score.ScoreConfigService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.web.util.Pagebean;

/**
 * 类QueryMyRed.java的实现描述：动态加载我的红包
 * 
 * @author WangMing 2015年5月14日 下午4:09:20
 */
public class QueryMyRed {

    @Autowired
    private HttpSession        session;
    @Resource
    private RedsendService     redSendService;
    @Resource
    private ScoreConfigService scoreConfigService;
    //日期格式化  
    private SimpleDateFormat   dateFormat;

    public String execute(Context context, ParameterParser params) {
        User user = (User) session.getAttribute("user");
        int pageSize = 10;
        int currentPage = params.getInt("currentPage") + 1;
        StringBuffer html = new StringBuffer();
        PageCondition pageCondition = new PageCondition(currentPage, pageSize);
        RedSearchDO redSearchDO = new RedSearchDO();
        redSearchDO.setUserId(user.getUserId());
        PageResult<RedsendJ> RedsendJPageResult = redSendService.queryListJ(redSearchDO, pageCondition);
        List<RedsendJ> redsendJList = RedsendJPageResult.getData();
        Pagebean<RedsendJ> pagebean = new Pagebean<RedsendJ>(currentPage, pageSize, redsendJList,
                RedsendJPageResult.getTotalCount());
        context.put("pagebean", pagebean);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (RedsendJ redsend : redsendJList) {
            //判断红包是否过期
            for (RedsendJ redsendJ : redsendJList) {
                if (redsendJ.getRsClosetime().getTime() < new Date().getTime()
                        && redsendJ.getRsState() == RsState.WITHOUT_USE) {
                    redsendJ.setRsState(RsState.FAILURE);
                }

            }
            html.append("<tr>");
            html.append("<td class='wdjf_td1  text-center'><span class='c-jyjl-money1'>"+dateFormat.format(redsend.getRsSendtime())+"</span></td>");
            html.append("<td class='wdjf_td1  text-center color_red'>"+redsend.getRsValidAmount()+"</td>");
            html.append("<td class='wdjf_td1  text-center '>"+redsend.getRsType().des+"</td>");
            
        	if(redsend.getRsState() == RsState.WITHOUT_USE){
        		html.append("<td class='text-center color_green'><span class='mr5'>未使用</span></td>");
        	}else if(redsend.getRsState() == RsState.USE){
        		html.append("<td class='text-center color_green'><span class='mr5'>已使用</span></td>");
        	}else{
        		html.append("<td class='text-center color_green'><span class='mr5'>已过期</span></td>");
        	}
            html.append("</tr>");
	    }
            return html.toString();

    }
}
