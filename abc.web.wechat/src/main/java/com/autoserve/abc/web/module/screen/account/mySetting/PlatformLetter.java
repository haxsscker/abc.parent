package com.autoserve.abc.web.module.screen.account.mySetting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.common.PageCondition.Order;
import com.autoserve.abc.dao.dataobject.SysMessageInfoDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.sys.SysMessageInfoService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.util.Pagebean;

/**
 * 类PlatformLetter.java的实现描述：TODO 查询用户的站内消息list
 * 
 * @author yehui 2015年5月21日 上午10:19:17
 */
public class PlatformLetter {
    private final static Logger   logger = LoggerFactory.getLogger(PlatformLetter.class); //加入日志

    @Autowired
    private HttpSession           session;
    @Resource
    private SysMessageInfoService messageInfoService;
    @Resource
    private UserService           userService;

    public void execute(Context context, Navigator nav, ParameterParser params) {

        logger.info("into PlatformLetter execute");

        User user = (User) session.getAttribute("user");
        Integer currentPage = params.getInt("currentPage");
        Integer pageSize = params.getInt("pageSize");
        if (currentPage == 0)
            currentPage = 1;//默认情况
        if (pageSize == 0)
            pageSize = 10;//默认情况
        //      List<SysMessageInfoDO> rechargeRecorDO = null;
        if (user != null) {
            PageCondition pageCondition = new PageCondition(currentPage, pageSize, "sysMessageDate", Order.DESC);
            PageResult<SysMessageInfoDO> pageResult = messageInfoService.queryByUserId(user.getUserId(), pageCondition);
            List<SysMessageInfoDO> messageInfoDOList = pageResult.getData();
            List<SysMessageInfoVO> messageInfoVOList = new ArrayList<SysMessageInfoVO>();
            List<SysMessageInfoVO> messageInfoVOList2 = new ArrayList<SysMessageInfoVO>();
            List<UserDO> userList = new ArrayList<UserDO>();
            for (SysMessageInfoDO sysMessageInfoDO : messageInfoDOList) {
                SysMessageInfoVO vo = new SysMessageInfoVO();
                vo.setSysDelSign(sysMessageInfoDO.getSysDelSign());
                vo.setSysMessageContent(sysMessageInfoDO.getSysMessageContent());//
                vo.setSysMessageDate(sysMessageInfoDO.getSysMessageDate());
                vo.setSysMessageId(sysMessageInfoDO.getSysMessageId());
                vo.setSysMessageState(sysMessageInfoDO.getSysMessageState());
                vo.setSysMessageTitle(sysMessageInfoDO.getSysMessageTitle());
                vo.setSysToUser(sysMessageInfoDO.getSysToUser());
                vo.setSysToUserType(sysMessageInfoDO.getSysToUserType());
                vo.setSysUserId(sysMessageInfoDO.getSysUserId());
                vo.setSysUserType(sysMessageInfoDO.getSysUserType());
                vo.setSysRead(sysMessageInfoDO.getSysRead());
                messageInfoVOList.add(vo);
                PlainResult<UserDO> plainResult = userService.findById(sysMessageInfoDO.getSysToUser());
                //           rechargeRecorDO = pageResult.getData();
                userList.add(plainResult.getData());

            }
            for (int i = 0; i < messageInfoDOList.size(); i++) {
                SysMessageInfoVO vo = messageInfoVOList.get(i);
                vo.setSysToUserName(userList.get(i).getUserName());
                messageInfoVOList2.add(vo);
            }
            //分页处理
            Pagebean<SysMessageInfoVO> pagebean = new Pagebean<SysMessageInfoVO>(currentPage, pageResult.getPageSize(),
                    messageInfoVOList2, pageResult.getTotalCount());
            context.put("pagebean", pagebean);

            /*
             * String html = ""; html = addHtml(rechargeRecorDO); List<String>
             * list = new ArrayList<String>(); list.add(html);
             * JsonListVO<String> jvo = new JsonListVO<String>();
             * jvo.setRows(list); return jvo;
             */

        } else {
            nav.forwardTo("/login/login").end();

        }

    }

}
