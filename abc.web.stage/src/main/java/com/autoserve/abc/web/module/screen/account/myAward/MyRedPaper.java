package com.autoserve.abc.web.module.screen.account.myAward;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import com.autoserve.abc.service.biz.enums.RsState;
import com.autoserve.abc.service.biz.intf.redenvelope.RedsendService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.util.Pagebean;

/**
 * @author DS 2015下午3:13:08
 */
public class MyRedPaper {
    @Autowired
    private HttpSession    session;
    @Resource
    private RedsendService redSendService;

    public void execute(Context context, ParameterParser params) {
        User user = (User) session.getAttribute("user");
        int currentPage = params.getInt("currentPage");
        if (currentPage == 0)
            currentPage = 1;
        int pageSize = 10;
        PageCondition pageCondition = new PageCondition(currentPage, pageSize);
        RedSearchDO redSearchDO = new RedSearchDO();
        redSearchDO.setUserId(user.getUserId());
        redSearchDO.setOrder("FIELD(rs_state, 1, 2, 0), rs_sendtime DESC");//排序按未使用、已使用、已过期       红包过期时间升序
        PageResult<RedsendJ> RedsendJPageResult = redSendService.queryListJ(redSearchDO, pageCondition);
        List<RedsendJ> redsendJList = RedsendJPageResult.getData();
//        List<RedsendJ> failureList = new ArrayList<RedsendJ>();
        //判断红包是否过期
//        Iterator<RedsendJ> it = redsendJList.iterator();
//        while (it.hasNext()) {
//            RedsendJ redsendJ = it.next();
//            if (redsendJ.getRsClosetime().getTime() < new Date().getTime()
//                    && redsendJ.getRsState() == RsState.WITHOUT_USE) {
//                redsendJ.setRsState(RsState.FAILURE);
//                //把已过期的放到最后
//                it.remove();
//                failureList.add(redsendJ);
//            }
//        }
//        redsendJList.addAll(failureList);
        Pagebean<RedsendJ> pagebean = new Pagebean<RedsendJ>(currentPage, pageSize, redsendJList,
                RedsendJPageResult.getTotalCount());
        context.put("pagebean", pagebean);

        int unUsedRed = this.redSendService.countUnusedRed(user.getUserId());
        context.put("unusedRed", unUsedRed);
        
        BigDecimal unUsedRedAmount = this.redSendService.countUnusedRedAmount(user.getUserId());
        context.put("unUsedRedAmount", unUsedRedAmount);
    }
}
