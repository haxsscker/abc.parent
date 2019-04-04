package com.autoserve.abc.web.module.screen.account.mySetting;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserCompany;
import com.autoserve.abc.service.biz.entity.UserContact;
import com.autoserve.abc.service.biz.entity.UserEducation;
import com.autoserve.abc.service.biz.entity.UserHouse;
import com.autoserve.abc.service.biz.entity.UserOwner;
import com.autoserve.abc.service.biz.entity.UserSpouse;
import com.autoserve.abc.service.biz.intf.upload.FileUploadInfoService;
import com.autoserve.abc.service.biz.intf.user.UserCompanyService;
import com.autoserve.abc.service.biz.intf.user.UserContactService;
import com.autoserve.abc.service.biz.intf.user.UserEducationService;
import com.autoserve.abc.service.biz.intf.user.UserHouseService;
import com.autoserve.abc.service.biz.intf.user.UserOwnerService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.intf.user.UserSpouseService;
import com.autoserve.abc.service.biz.result.PlainResult;

public class CustomerInfo {
    @Autowired
    private HttpSession           session;
    @Autowired
    private UserService userService;
    @Autowired
    private UserContactService    usercontactservice;
    @Autowired
    private UserHouseService      userhouseservice;
    @Autowired
    private UserCompanyService    usercompanyservice;
    @Autowired
    private UserOwnerService      userownerservice;
    @Autowired
    private UserSpouseService     userspouseservice;
    @Autowired
    private UserEducationService  usereducationservice;
    @Autowired
    private FileUploadInfoService fileuploadinfoservice;

    public void execute(Context context, ParameterParser params) {
        User user = (User) session.getAttribute("user");
        //0、基本资料
        PlainResult<UserDO> userinfo = userService.findById(user.getUserId());
        //1、联系方式：
        PlainResult<UserContact> usercontact = usercontactservice.findUserContactByUserId(user.getUserId());
        //2、房產資料：
        PlainResult<UserHouse> userhouse = userhouseservice.findUserHouseByUserId(user.getUserId());
        //3、單位資料：
        PlainResult<UserCompany> usercompany = usercompanyservice.queryUserCompanyByUserId(user.getUserId());
        //4、私營業主資料：
        PlainResult<UserOwner> userowner = userownerservice.findUserOwnerByUserId(user.getUserId());
        //5、配偶資料:
        PlainResult<UserSpouse> userspouse = userspouseservice.findUserSpouseByUserId(user.getUserId());
        //6、教育背景：
        PlainResult<UserEducation> usereducation = usereducationservice.findUserEducationByUserId(user.getUserId());
        //7、資料上傳：
        //fileuploadinfoservice.createFileUploadInfo(null);
        context.put("user", user);
        context.put("userinfo", userinfo);
        context.put("usercontact", usercontact);
        context.put("userhouse", userhouse);
        context.put("usercompany", usercompany);
        context.put("userowner", userowner);
        context.put("userspouse", userspouse);
        context.put("usereducation", usereducation);

    }
}
