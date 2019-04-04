package com.autoserve.abc.web.module.screen.account.mySetting.json;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.autoserve.abc.dao.dataobject.UserCompanyDO;
import com.autoserve.abc.dao.dataobject.UserContactDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserCompany;
import com.autoserve.abc.service.biz.entity.UserContact;
import com.autoserve.abc.service.biz.entity.UserEducation;
import com.autoserve.abc.service.biz.entity.UserHouse;
import com.autoserve.abc.service.biz.entity.UserOwner;
import com.autoserve.abc.service.biz.entity.UserSpouse;
import com.autoserve.abc.service.biz.enums.EducationLevel;
import com.autoserve.abc.service.biz.intf.upload.FileUploadInfoService;
import com.autoserve.abc.service.biz.intf.user.UserCompanyService;
import com.autoserve.abc.service.biz.intf.user.UserContactService;
import com.autoserve.abc.service.biz.intf.user.UserEducationService;
import com.autoserve.abc.service.biz.intf.user.UserHouseService;
import com.autoserve.abc.service.biz.intf.user.UserOwnerService;
import com.autoserve.abc.service.biz.intf.user.UserSpouseService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;

public class CustomerInfoAdd {
    @Autowired
    private HttpSession           session;
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

    public BaseResult execute(@Params UserContactDO contactdo, @Params UserHouse house,
                              @Params UserCompanyDO companydo, @Params UserOwner owner, @Params UserSpouse spouse,
                              @Params UserEducation education, Context context, ParameterParser params)
            throws ParseException {
        BaseResult message = new BaseResult();
        Integer param = params.getInt("param");
        User user = (User) session.getAttribute("user");
        //1、联系方式：
        if (param == 1) {
            message = usercontactservice.CreateUserContactByUserId(contactdo);
            PlainResult<UserContact> usercontact = usercontactservice.findUserContactByUserId(user.getUserId());
            if (usercontact.isSuccess()) {
                message.setCode(usercontact.getData().getUcId());
            }
        }
        //2、房產資料：
        if (param == 2) {
            String time = params.getString("houseDate");
            if (time != null && !"".equals(time)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = sdf.parse(time);
                house.setUhDate(date);
            }
            message = userhouseservice.createUserHouse(house);
            PlainResult<UserHouse> userhouse = userhouseservice.findUserHouseByUserId(user.getUserId());
            if (userhouse.isSuccess()) {
                message.setCode(userhouse.getData().getUhId());
            }
        }
        //3、單位資料：
        if (param == 3) {
        	String income = params.getString("userMonthIncome");
        	BigDecimal userMonthIncome = null;
        	if(income != null && !"".equals(income)) {
        		userMonthIncome = new BigDecimal(income);
        	}
            message = usercompanyservice.createUserHouse(companydo, userMonthIncome);
            PlainResult<UserCompany> usercompany = usercompanyservice.queryUserCompanyByUserId(user.getUserId());
            if (usercompany.isSuccess()) {
                message.setCode(usercompany.getData().getId());
            }
        }
        //4、私營業主資料：
        if (param == 4) {
        	String time = params.getString("uoDate");
            if (time != null && !"".equals(time)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = sdf.parse(time);
                owner.setUoDate(date);
            }
            message = userownerservice.createUserOwner(owner);
            PlainResult<UserOwner> userowner = userownerservice.findUserOwnerByUserId(user.getUserId());
            if (userowner.isSuccess()) {
                message.setCode(userowner.getData().getUoId());
            }
        }
        //5、配偶資料:
        if (param == 5) {
        	String marry = params.getString("userMarry");
        	Integer userMarry = null;
        	if(marry != null && !"".equals(marry)) {
        		userMarry = Integer.parseInt(marry);
        	}
            message = userspouseservice.createUserSpouse(spouse, userMarry);
            PlainResult<UserSpouse> userspouse = userspouseservice.findUserSpouseByUserId(user.getUserId());
            if (userspouse.isSuccess()) {
                message.setCode(userspouse.getData().getUsId());
            }
        }
        //6、教育背景：
        if (param == 6) {
        	String starttime = params.getString("ueStartTime");
            if (starttime != null && !"".equals(starttime)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date startdate = sdf.parse(starttime);
                education.setUeStartTime(startdate);
            }
            String endtime = params.getString("ueEndTime");
            if (endtime != null && !"".equals(endtime)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date enddate = sdf.parse(endtime);
                education.setUeEndTime(enddate);
            }
            education.setUeTopEducation(EducationLevel.valueOf(params.getInt("ueTopEducation")));
            message = usereducationservice.createUserEducation(education);
            PlainResult<UserEducation> usereducation = usereducationservice.findUserEducationByUserId(user.getUserId());
            if (usereducation.isSuccess()) {
                message.setCode(usereducation.getData().getUeId());
            }
        }
        //7、資料上傳：
        //fileuploadinfoservice.createFileUploadInfo(null);
        return message;

    }
}
