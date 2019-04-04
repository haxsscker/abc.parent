package com.autoserve.abc.service.biz.impl.wechat;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.UserDao;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.intf.wechat.WeChatUserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.service.util.RandomUserName;

@Service
public class WeChatUserServiceImpl implements WeChatUserService {

    @Resource
    private UserDao          userDao;

    @Resource
    private UserService      userService;

    @Autowired
    private SysConfigService sysConfigService;

    private String           password = "123456";

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String getUserNameToWeChat(String wechatid, String wechatname) {
        String username = "";
        //1.验证该关注用户是否已绑定平台账号
        int flag = userDao.queryUserByWeChatId(wechatid);
        if (flag <= 0) {
            UserDO userDO = new UserDO();
            boolean notUse = true; //首次进入表示需要获取随机用户名
            while (notUse) {
                //2.未绑定平台账号随机获取平台账号
                String temporaryUserName = RandomUserName.getRandomUserName();
                //3.验证随机获取的平台账号是否存在重复
                if (temporaryUserName != null && !("".equals(temporaryUserName))) {
                    userDO.setUserName(temporaryUserName);
                    PageResult<UserDO> pageResult = userService.queryList(userDO, new PageCondition());
                    if (pageResult.getData().size() != 0) {
                        //用户名已存在，不可用
                        notUse = true;
                    } else {
                        //用户名可用
                        notUse = false;
                    }
                }
            }
            //4.插入平台账号
            userDO.setUserPwd(CryptUtils.md5(password));
            userDO.setUserDealPwd(CryptUtils.md5(password));
            //是否绑定手机号
            userDO.setUserMobileIsbinded(0);
            //手机认证日期
            userDO.setUserMobileVerifyDate(null);
            //是否绑定邮箱
            userDO.setUserEmailIsbinded(0);
            //注册日期
            userDO.setUserRegisterDate(new Date());
            //启用账户
            userDO.setUserState(1);
            //用户类型 1.个人  微信关注目前标示为个人用户
            userDO.setUserType(UserType.PERSONAL.getType());
            //信用额度(后台设置)
            PlainResult<SysConfig> sysConfigInfo = sysConfigService.querySysConfig(SysConfigEntry.MIN_CREDIT_LIMIT);
            SysConfig sysConfig = sysConfigInfo.getData();
            if (sysConfig != null && sysConfig.getConfValue() != null && !"".equals(sysConfig.getConfValue())) {
                userDO.setUserLoanCredit(new BigDecimal(sysConfig.getConfValue()));
                userDO.setUserCreditSett(new BigDecimal(sysConfig.getConfValue()));
            }
            //微信相关信息存储
            userDO.setUserWeChatID(wechatid);
            userDO.setUserWeChatName(wechatname);
            userDO.setUserIsBound(1);//微信已绑定
            BaseResult result = userService.createUser(userDO);
            //用户数据添加成功返回用户名
            if (result.isSuccess()) {
                return userDO.getUserName();
            }
        }
        //5.返回平台账号
        return username;
    }
}
