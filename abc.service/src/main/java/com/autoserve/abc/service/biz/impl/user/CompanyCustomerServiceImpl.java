package com.autoserve.abc.service.biz.impl.user;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CompanyCustomerDO;
import com.autoserve.abc.dao.dataobject.CompanyCustomerFileDO;
import com.autoserve.abc.dao.dataobject.CompanyUserJDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.CompanyCustomerDao;
import com.autoserve.abc.dao.intf.CompanyCustomerFileDao;
import com.autoserve.abc.dao.intf.UserDao;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.impl.cash.StringUtil;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.CryptUtils;

/**
 * @author RJQ 2014/11/20 20:18.
 */
@Service
public class CompanyCustomerServiceImpl implements CompanyCustomerService {

    @Resource
    private CompanyCustomerDao     companyCustomerDao;

    @Resource
    private CompanyCustomerFileDao companyCustomerFileDao;

    @Resource
    private UserService            userService;

    @Resource
    private UserDao                userDao;

    @Autowired
    private SysConfigService       sysConfigService;

    @Override
    public PlainResult<CompanyCustomerDO> findById(int id) {
        CompanyCustomerDO companyCustomerDO = companyCustomerDao.findById(id);
        PlainResult<CompanyCustomerDO> result = new PlainResult<CompanyCustomerDO>();
        if (companyCustomerDO == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到指定信息！");
        }
        result.setData(companyCustomerDO);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageResult<CompanyCustomerDO> queryList(CompanyCustomerDO companyCustomerDO, PageCondition pageCondition) {
        PageResult<CompanyCustomerDO> result = new PageResult<CompanyCustomerDO>(pageCondition.getPage(),
                pageCondition.getPageSize());
        int totalCount = companyCustomerDao.countListByParam(companyCustomerDO);
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            result.setData(companyCustomerDao.findListByParam(companyCustomerDO, pageCondition));
        }

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult removeCompanyCustomer(int id) {
        int companyUserId = companyCustomerDao.findById(id).getCcId();
        return userService.removeUser(companyUserId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult disableCompanyCustomer(int id) {
        int companyUserId = companyCustomerDao.findById(id).getCcId();
        return userService.disableUser(companyUserId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult enableCompanyCustomer(int id) {
        int companyUserId = companyCustomerDao.findById(id).getCcId();
        return userService.enableUSer(companyUserId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult createCompanyCustomer(CompanyCustomerDO companyCustomerDO) {
    	BaseResult baseResult = new BaseResult();  
        //校验证件号码唯一性 xiatt 20160625 
//        if(!StringUtil.isBlank(companyCustomerDO.getCcDocNo())){
//        	UserDO queryUser = new UserDO();
//        	queryUser.setUserDocNo(companyCustomerDO.getCcDocNo());
//        	queryUser.setUserType(2);
//            int docNoCount = userDao.countDocNoByParam(queryUser);
//            if(docNoCount>0){
//            	baseResult.setErrorMessage(CommonResultCode.BIZ_ERROR, "证件号码已存在");
//                return baseResult;
//            }
//        }
    	 //校验组织机构代码唯一性   zhm   20161116
        if(!StringUtil.isBlank(companyCustomerDO.getCcNo())){
        	int totalCount = companyCustomerDao.countNoByParam(companyCustomerDO);
            if(totalCount>0){
            	baseResult.setErrorMessage(CommonResultCode.BIZ_ERROR, "组织机构代码已存在");
                return baseResult;
            }
        }
        UserDO userDO = new UserDO();
        //登录账号
        userDO.setUserName(companyCustomerDO.getCcUserName());
        userDO.setUserUuid(InnerSeqNo.getInstance().getUniqueNo());
        /**
         * 企业用户初始密码:123456
         */
        userDO.setUserPwd(CryptUtils.md5("123456"));
        //交易密码(初始值和登录密码一样)
        userDO.setUserDealPwd(CryptUtils.md5("123456"));
        //类型
        userDO.setUserType(2);
        //保存为公司名字
        userDO.setUserRealName(companyCustomerDO.getCcCompanyName());
        /**
         * 根据客户需求，法人也能单独注册，成为投资人，所以企业的法人只能存在abc_company_customer表
         */
        //法定代表人
                //userDO.setUserRealName(companyCustomerDO.getCcCorporate());
        //edit by 夏同同  20160516
        //证件类型
                userDO.setUserDocType(companyCustomerDO.getCcDocType());
        //证件号码
                userDO.setUserDocNo(companyCustomerDO.getCcDocNo());
        //电子邮箱
                userDO.setUserEmail(companyCustomerDO.getCcContactEmail());
        //法人手机号码
                userDO.setUserPhone(companyCustomerDO.getCcContactPhone());
        //绑定手机号
        userDO.setUserMobileIsbinded(1);
        //绑定邮箱
        userDO.setUserEmailIsbinded(1);
        //信用额度(后台设置)
        PlainResult<SysConfig> sysConfigInfo = sysConfigService.querySysConfig(SysConfigEntry.MIN_CREDIT_LIMIT);
        SysConfig sysConfig = sysConfigInfo.getData();
        if (sysConfig != null && sysConfig.getConfValue() != null && !"".equals(sysConfig.getConfValue())) {
            userDO.setUserLoanCredit(new BigDecimal(sysConfig.getConfValue()));
            userDO.setUserCreditSett(new BigDecimal(sysConfig.getConfValue()));
        }
        //启用
        userDO.setUserState(1);
        //注册日期
        userDO.setUserRegisterDate(new Date());
        int val = userDao.insert(userDO);
        if (val <= 0) {
            baseResult.setErrorMessage(CommonResultCode.BIZ_ERROR, "新增用户失败");
            return baseResult;
        } else {
            companyCustomerDO.setCcUserid(userDO.getUserId());
            companyCustomerDO.setCcRegisterDate(new Date());
            val = companyCustomerDao.insert(companyCustomerDO);
            if (val <= 0) {
                baseResult.setErrorMessage(CommonResultCode.BIZ_ERROR, "新增公司客户失败");
                return baseResult;
            }
        }
        return baseResult;
    }

    @Override
    public BaseResult editCompanyCustomer(CompanyCustomerDO companyCustomerDO) {
        BaseResult baseResult = new BaseResult();
        //更新user表
        UserDO userDO = new UserDO();
        userDO.setUserId(companyCustomerDO.getCcUserid());
        userDO.setUserName(companyCustomerDO.getCcUserName());
        //充值提现时显示公司名字
        userDO.setUserRealName(companyCustomerDO.getCcCompanyName());
        //证件类型
        userDO.setUserDocType(companyCustomerDO.getCcDocType());
        //证件号码
        userDO.setUserDocNo(companyCustomerDO.getCcDocNo());
        //电子邮箱
        userDO.setUserEmail(companyCustomerDO.getCcUserEmail());
        //法人手机号码
        userDO.setUserPhone(companyCustomerDO.getCcUserPhone());
        int val = userDao.updateByPrimaryKeySelective(userDO);
        if (val <= 0) {
            baseResult.setErrorMessage(CommonResultCode.BIZ_ERROR, "更新用户信息失败");
            return baseResult;
        } else {
            val = companyCustomerDao.update(companyCustomerDO);
            if (val <= 0) {
                baseResult.setErrorMessage(CommonResultCode.BIZ_ERROR, "编辑公司客户失败");
            }
        }
        return baseResult;
    }

    @Override
    public PlainResult<CompanyCustomerFileDO> findByCompanyCustomerId(int id) {
        PlainResult<CompanyCustomerFileDO> result = new PlainResult<CompanyCustomerFileDO>();
        CompanyCustomerFileDO fileDO = companyCustomerFileDao.findByCompanyCustomerId(id);
        if (fileDO == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到指定信息！");
        }
        return result;
    }

    @Override
    public PageResult<CompanyUserJDO> queryList(CompanyUserJDO companyUserJDO, PageCondition pageCondition) {
        PageResult<CompanyUserJDO> result = new PageResult<CompanyUserJDO>(pageCondition.getPage(),
                pageCondition.getPageSize());
        int totalCount = companyCustomerDao.countListCompanyUserByParam(companyUserJDO);
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            result.setData(companyCustomerDao.findCompanyUserListByParam(companyUserJDO, pageCondition));
        }

        return result;
    }

    @Override
    public PageResult<CompanyUserJDO> queryOpenAccountList(CompanyUserJDO companyUserJDO, PageCondition pageCondition) {
        PageResult<CompanyUserJDO> result = new PageResult<CompanyUserJDO>(pageCondition.getPage(),
                pageCondition.getPageSize());
        int totalCount = companyCustomerDao.countOpenAccount(companyUserJDO);
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            result.setData(companyCustomerDao.findOpenAccountListByParam(companyUserJDO, pageCondition));
        }

        return result;
    }

    @Override
    public PlainResult<CompanyCustomerDO> findByUserId(int userId) {
        CompanyCustomerDO companyCustomerDO = companyCustomerDao.findByUserId(userId);
        PlainResult<CompanyCustomerDO> result = new PlainResult<CompanyCustomerDO>();
        if (companyCustomerDO == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到指定信息！");
        }
        result.setData(companyCustomerDO);
        return result;
    }
    
    @Override
    public PlainResult<CompanyCustomerDO> findByCompanyId(int userId) {
        CompanyCustomerDO companyCustomerDO = companyCustomerDao.findByCompanyId(userId);
        PlainResult<CompanyCustomerDO> result = new PlainResult<CompanyCustomerDO>();
        if (companyCustomerDO == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到指定信息！");
        }
        result.setData(companyCustomerDO);
        return result;
    }
}
