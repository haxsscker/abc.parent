package com.autoserve.abc.web.module.screen.account.myAward;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ScoreConfigDO;
import com.autoserve.abc.dao.dataobject.ScoreHistoryDO;
import com.autoserve.abc.dao.dataobject.ScoreHistoryWithValueDO;
import com.autoserve.abc.dao.dataobject.search.RedSearchDO;
import com.autoserve.abc.service.biz.entity.RedsendJ;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.RsState;
import com.autoserve.abc.service.biz.intf.credit.CreditService;
import com.autoserve.abc.service.biz.intf.redenvelope.RedsendService;
import com.autoserve.abc.service.biz.intf.score.ScoreConfigService;
import com.autoserve.abc.service.biz.intf.score.ScoreHistoryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.Pagebean;

/**
 * 类RedPacketInfo.java的实现描述：单个红包信息展示
 * 
 * @author WangMing 2015年5月15日 上午10:50:02
 */
public class RedPacketInfo {

    @Resource
    private UserService         userService;
    @Autowired
    private HttpSession         session;
    @Resource
    private ScoreHistoryService scoreHistoryService;
    @Resource
    private HttpServletRequest  request;
    @Autowired
    private DeployConfigService deployConfigService;
    @Resource
    private ScoreConfigService  scoreConfigService;
    @Resource
    private CreditService       creditService;
    @Resource
    private  RedsendService redSendService;

    public void execute(Context context, ParameterParser params, Navigator nav) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            nav.redirectToLocation(deployConfigService.getLoginUrl(request));
            return;
        }
        int currentPage = params.getInt("currentPage");
        if (currentPage == 0)
            currentPage = 1;
        int pageSize = 20;
        PageCondition pageCondition = new PageCondition(currentPage, pageSize);

        PlainResult<User> userResult = userService.findEntityById(user.getUserId());
        
        //获取积分记录
        ScoreHistoryDO tmpDao = new ScoreHistoryDO();
        tmpDao.setShUserId(user.getUserId());
        PageResult<ScoreHistoryWithValueDO> result = scoreHistoryService.queryScoreHistoryList(tmpDao, pageCondition);
        context.put("userPojo", userResult.getData());
        context.put("scoreHistory", result);
        
      //积分信息汇总
        Integer allCredits = 0;
        Integer isHaveCredits = 0;
        Integer isUseCredits = 0;
        allCredits = creditService.queryCountScore(user.getUserId()).getData();
        if (userResult.getData().getUserScore() != null) {
            isHaveCredits = userResult.getData().getUserScore();
        }
        isUseCredits = allCredits - isHaveCredits;
        context.put("allCredits", allCredits);
        context.put("isHaveCredits", isHaveCredits);
        context.put("isUseCredits", isUseCredits);

        //查询积分兑换物品
        PageResult<ScoreConfigDO> pageResult = scoreConfigService.queryScoreConfigList(new ScoreConfigDO(),
                new PageCondition());
        context.put("creditTypes", pageResult.getData());
        
        
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        Integer rsState = 11;
        String sss = params.getString("rsState");
        if("null".equals(sss)||sss == null){
        	rsState = 10;
        }else{
        	rsState = params.getInt("rsState");
        }

        RedSearchDO redSearchDO = new RedSearchDO();
        redSearchDO.setUserId(user.getUserId());
        //全部状态定义10,查询时rsState字段为空
        if (rsState != 10) {
            redSearchDO.setRsState(rsState);
        }
        PageResult<RedsendJ> RedsendJPageResult = redSendService.queryListJ(redSearchDO, pageCondition);
        List<RedsendJ> redsendJList = RedsendJPageResult.getData();
        //判断红包是否过期
        context.put("pagebean", RedsendJPageResult);
        
    }
}
