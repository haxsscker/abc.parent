package com.autoserve.abc.service.biz.impl.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RealnameAuthDO;
import com.autoserve.abc.dao.dataobject.RealnameAuthJDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.RealnameAuthDao;
import com.autoserve.abc.dao.intf.UserDao;
import com.autoserve.abc.service.biz.entity.RealnameAuth;
import com.autoserve.abc.service.biz.enums.DocType;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.enums.ScoreType;
import com.autoserve.abc.service.biz.impl.cash.Common;
import com.autoserve.abc.service.biz.intf.user.RealnameAuthService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.message.identity.IdentityJsonList;
import com.autoserve.abc.service.message.identity.SendIdentityService;
import com.autoserve.abc.service.util.DateUtil;

/**
 * @author RJQ 2014/12/26 21:57.
 */
@Service
public class RealnameAuthServiceImpl implements RealnameAuthService {

    @Resource
    private RealnameAuthDao     realnameAuthDao;
    @Resource
    private UserDao             userDao;
    @Resource
    private SendIdentityService sendIdentityService;
    @Resource
    private UserService userService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageResult<RealnameAuthDO> queryList(RealnameAuthDO realnameAuthDO, PageCondition pageCondition) {
        PageResult<RealnameAuthDO> result = new PageResult<RealnameAuthDO>(pageCondition.getPage(),
                pageCondition.getPageSize());
        int totalCount = realnameAuthDao.countListByParam(realnameAuthDO);
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            result.setData(realnameAuthDao.findListByParam(realnameAuthDO, pageCondition));
        }

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageResult<RealnameAuthJDO> queryRealnameAuthJDOList(RealnameAuthJDO realnameAuthJDO,
                                                                PageCondition pageCondition) {
        PageResult<RealnameAuthJDO> result = new PageResult<RealnameAuthJDO>(pageCondition.getPage(),
                pageCondition.getPageSize());
        realnameAuthJDO.setRnpReviewType(3);
        int total = realnameAuthDao.countRealnameListByParam(realnameAuthJDO);
        if (total > 0) {
        	List<RealnameAuthJDO> list = realnameAuthDao.findRealnameListByParam(realnameAuthJDO, pageCondition);
            result.setData(list);
        }
        result.setTotalCount(total);
        return result;
    }

    @Override
    public PlainResult<RealnameAuthDO> findRealNameAuthById(int id) {
        RealnameAuthDO realnameAuthDO = realnameAuthDao.findById(id);
        PlainResult<RealnameAuthDO> result = new PlainResult<RealnameAuthDO>();
        if (null == realnameAuthDO) {
            result.setError(CommonResultCode.BIZ_ERROR, "未找到指定信息！");
        }
        return result;
    }

    @Override
    public PlainResult<RealnameAuthJDO> findRealNameAuthJDOById(RealnameAuthJDO realnameAuthJDO) {
        RealnameAuthJDO rJDO = realnameAuthDao.findRealnameListByPOJO(realnameAuthJDO);
        PlainResult<RealnameAuthJDO> result = new PlainResult<RealnameAuthJDO>();
        if (null == rJDO) {
            result.setError(CommonResultCode.BIZ_ERROR, "未找到指定信息！");
        } else {
            result.setData(rJDO);
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult realNamePassReview(int id, String reviewNote) {
    	RealnameAuthDO realnameAuthDO = new RealnameAuthDO();
        try {
            realnameAuthDO.setRnpId(id);
            realnameAuthDO.setRnpReviewNote(reviewNote);
            realnameAuthDO.setRnpReviewState(ReviewState.PASS_REVIEW.getState());
            String ReviewDate = (new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_STYLE)).format(new Date());
            Date rnpReviewDate = DateUtil.parseDate(ReviewDate);
            realnameAuthDO.setRnpReviewDate(rnpReviewDate);
        } catch (ParseException e) {
            throw new BusinessException("时间转换异常");
        }
        BaseResult result = new BaseResult();
        int returnVal = realnameAuthDao.updateByPrimaryKeySelective(realnameAuthDO);
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "审核失败！");
        }
        RealnameAuthDO VRealnameAuth = realnameAuthDao.findById(id);
		UserDO updateUser = new UserDO();
        updateUser.setUserId(VRealnameAuth.getRnpUserid());
        updateUser.setUserRealnameIsproven(1);
        int flag = userDao.updateByPrimaryKeySelective(updateUser);
        if(flag<=0){
        	result.setErrorMessage(CommonResultCode.BIZ_ERROR, "更新实名认证失败！");
        }
        //实名认证送积分
            if (!userService.modifyUserScore(VRealnameAuth.getRnpUserid(), ScoreType.REALNAME_SCORE, null).isSuccess()) {
                result.setErrorMessage(CommonResultCode.BIZ_ERROR, "添加积分失败！");
            }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult realNameFailedPassReview(int id, String reviewNote) {
        RealnameAuthDO realnameAuthDO = new RealnameAuthDO();
        try {
            realnameAuthDO.setRnpId(id);
            realnameAuthDO.setRnpReviewNote(reviewNote);
            realnameAuthDO.setRnpReviewState(ReviewState.FAILED_PASS_REVIEW.getState());
            String ReviewDate = (new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_STYLE)).format(new Date());
            Date rnpReviewDate = DateUtil.parseDate(ReviewDate);
            realnameAuthDO.setRnpReviewDate(rnpReviewDate);
        } catch (ParseException e) {
            throw new BusinessException("时间转换异常");
        }
        BaseResult result = new BaseResult();
        int returnVal = realnameAuthDao.updateByPrimaryKeySelective(realnameAuthDO);
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "审核失败！");
        }
//        UserDO updateUser = new UserDO();
//        updateUser.setUserId(id);
//        updateUser.setUserRealnameIsproven(1);
//        userDao.updateByPrimaryKeySelective(updateUser);
        return result;
    }

    /**
     * 发送实名认证
     * 
     * @parmas realnameAuthJDO 实名认证信息
     * @return BaseResult 返回信息
     */
    @Override
    public BaseResult transmitRealNameAudit(int id) {
        /*
         * RealnameAuth realnameAuth = new RealnameAuth(); UserDO updateUser =
         * new UserDO(); RealnameAuthDO VRealnameAuth =
         * realnameAuthDao.findById(id); UserDO userDo = userDao.findById(id);
         * try { realnameAuth.setRnpId(id); realnameAuth.setRnpUserid(id);
         * realnameAuth.setRnpName(userDo.getUserRealName());
         * realnameAuth.setRnpDocNo(userDo.getUserDocNo());
         * realnameAuth.setRnpDocType(DocType.ID_CARD);
         * realnameAuth.setRnpNative(userDo.getUserNative());
         * realnameAuth.setRnpNation(userDo.getUserNation());
         * realnameAuth.setRnpSex(SexType.valueOf(userDo.getUserSex()));
         * realnameAuth.setRnpBirthday(userDo.getUserBirthday()); String
         * ApplyDate = (new
         * SimpleDateFormat(DateUtil.DEFAULT_FORMAT_STYLE)).format(new Date());
         * Date rnpApplyDate = DateUtil.parseDate(ApplyDate);
         * realnameAuth.setRnpApplyDate(rnpApplyDate); } catch (ParseException
         * e) { throw new BusinessException("时间转换异常"); } BaseResult result = new
         * BaseResult(); RealnameAuthDO realnameAuthDO = new RealnameAuthDO();
         * int flag = 0; Integer userRealnameIsproven = 0; if (VRealnameAuth ==
         * null) { realnameAuth.setRnpReviewType(1); String status =
         * sendIdentityService.sendIdentity(userDo.getUserRealName(),
         * userDo.getUserDocNo()); if ("0".equals(status)) {
         * realnameAuth.setRnpReviewState(ReviewState.PASS_REVIEW);
         * userRealnameIsproven = 1; } else {
         * realnameAuth.setRnpReviewState(ReviewState.FAILED_PASS_REVIEW); }
         * realnameAuthDO =
         * RealnameAuthConverter.toRealnameAuthDO(realnameAuth); flag =
         * realnameAuthDao.insert(realnameAuthDO); } else { Integer reviewType =
         * VRealnameAuth.getRnpReviewType(); if (reviewType == 1) { String
         * status = sendIdentityService.sendIdentity(userDo.getUserRealName(),
         * userDo.getUserDocNo()); realnameAuth.setRnpReviewType(2); if
         * ("0".equals(status)) {
         * realnameAuth.setRnpReviewState(ReviewState.PASS_REVIEW);
         * userRealnameIsproven = 1; } else {
         * realnameAuth.setRnpReviewState(ReviewState.FAILED_PASS_REVIEW); }
         * realnameAuthDO =
         * RealnameAuthConverter.toRealnameAuthDO(realnameAuth); } else {
         * realnameAuth.setRnpReviewType(3); realnameAuthDO =
         * RealnameAuthConverter.toRealnameAuthDO(realnameAuth); } flag =
         * realnameAuthDao.updateByPrimaryKeySelective(realnameAuthDO); }
         * updateUser.setUserId(id);
         * updateUser.setUserRealnameIsproven(userRealnameIsproven);
         * userDao.updateByPrimaryKeySelective(updateUser); if (flag > 0) {
         * result.setSuccess(true); result.setMessage("发送实名认证成功"); } else {
         * result.setSuccess(false); result.setMessage("发送实名认证失败"); }
         */

        return null;
    }

    @Override
    public BaseResult updateRealNameAudit(RealnameAuth realNameAuth) {
        BaseResult result = new BaseResult();
        RealnameAuthDO realnameAuthDO = new RealnameAuthDO();
        realnameAuthDO.setRnpId(realNameAuth.getRnpId());
        realnameAuthDO.setRnpDocNo(realNameAuth.getRnpDocNo());
        int returnVal = realnameAuthDao.updateByPrimaryKeySelective(realnameAuthDO);
        if (returnVal > 0) {
            result.setSuccess(true);
            result.setMessage("发送实名认证成功");
        } else {
            result.setSuccess(false);
            result.setMessage("发送实名认证失败");
        }
        return result;
    }

    /**
     * 1.判断是否成功进行实名认证
     * 2.如果失败或没有，判断是否进行过实名认证
     * 2.1.进行过，判断第几次实名认证，如果超过3次，发送平台审核
     * 2.2.没进行过，进行实名认证
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BaseResult realNameAudit(int userId) {
        BaseResult result = new BaseResult();
        UserDO userDo = userDao.findById(userId);
        //判断是否进行实名认证(成功)
        if(userDo.getUserRealnameIsproven()==null || userDo.getUserRealnameIsproven() == 0){
	        //判断是否进行过实名认证
	        RealnameAuthJDO realnameAuthJDO =new RealnameAuthJDO();
	        realnameAuthJDO.setRnpUserid(userId);
	        RealnameAuthJDO VRealnameAuth = realnameAuthDao.findRealnameListByPOJO(realnameAuthJDO);
	        
	        //实名认证表
	        RealnameAuthDO realnameAuthDO = new RealnameAuthDO();
	        realnameAuthDO.setRnpUserid(userId);
            realnameAuthDO.setRnpName(userDo.getUserRealName());
            realnameAuthDO.setRnpDocNo(userDo.getUserDocNo());
            realnameAuthDO.setRnpDocType(DocType.ID_CARD.getType());
            String ApplyDate = (new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_STYLE)).format(new Date());
            Date rnpApplyDate=null;
            try {
				rnpApplyDate = DateUtil.parseDate(ApplyDate);				
			} catch (ParseException e1) {
				throw new BusinessException("时间转换异常");
			}
            realnameAuthDO.setRnpApplyDate(rnpApplyDate);  //申请时间
            realnameAuthDO.setRnpReviewState(0); //审核状态：待审核
            
	        if(VRealnameAuth == null || (VRealnameAuth!=null && VRealnameAuth.getRnpReviewType()==null)){
	        	realnameAuthDO.setRnpReviewType(0);
	        	return this.callAuthenticationInterface(userDo,realnameAuthDO);	        	
	        }else{
	        	 realnameAuthDO.setRnpId(VRealnameAuth.getRnpId());
	        	 Integer reviewType = VRealnameAuth.getRnpReviewType();	        	 
	        	 if(reviewType!=null && reviewType<2){
	        		 realnameAuthDO.setRnpReviewType(VRealnameAuth.getRnpReviewType()+1);
	        		 return this.callAuthenticationInterface(userDo,realnameAuthDO);
	        	 }else{
	        		 realnameAuthDO.setRnpReviewType(3);
	        		 //发送平台审核
	        		 int flag = realnameAuthDao.updateByUserId(realnameAuthDO);
	        		 if(flag<0){
	        			 return result.setError(CommonResultCode.BIZ_ERROR, "更新实名认证信息失败");
	        		 }else{
	        			 return result.setError(CommonResultCode.BIZ_ERROR, "调用三次实名认证接口没有审核通过，您的信息已经提交到本地平台，请耐心等待，审核通过后可进行开户操作！");
	        		 }
	        			
	        	 }
	        }
        }
        return result;
    }
    
    /*
     * 调用实名认证接口
     */
    private BaseResult callAuthenticationInterface(UserDO userDo,RealnameAuthDO realnameAuthDO){
    	BaseResult result=new BaseResult();
    	 Map<String, String> resultMap = sendIdentityService.sendIdentity(userDo.getUserRealName(), userDo.getUserDocNo());
//		    String status = resultMap.get("status");    //调用实名认证接口是否成功 0、成功   1、失败
//	        String compStatus=resultMap.get("compStatus");   //实名认证返回的结果 0、比对一致   非0、比对不一致
	        
	        //双钱认证接口
    	 	String ResultCode = resultMap.get("ResultCode").toString();
    	 	String IdentityJsonList = resultMap.get("IdentityJsonList");
    	 	List<IdentityJsonList> list = JSON.parseArray(Common.UrlDecoder(IdentityJsonList, "utf-8"), IdentityJsonList.class);
	        
	        //发送实名认证是否成功
//	        if ("0".equals(status) && "0".equals(compStatus)) {
	        if ("88".equals(ResultCode) && null != list && !list.isEmpty() && 1 == list.get(0).getState()) {
	        	//1.更新实名认证标志
	        	UserDO updateUser = new UserDO();
	            updateUser.setUserId(userDo.getUserId());
	            updateUser.setUserRealnameIsproven(1);
	            int flag = userDao.updateByPrimaryKeySelective(updateUser);
	            if (flag > 0) {
	                int sex = 0;
	                if("男".equals(resultMap.get("sex"))){
	                	sex = 1;
	                }
	                realnameAuthDO.setRnpNative(resultMap.get("policeadd"));
	                realnameAuthDO.setRnpSex(sex);
	                //审核状态
	                realnameAuthDO.setRnpReviewState(1); //审核状态：审核已通过
	                realnameAuthDO.setRnpReviewDate(new Date()); //审核时间
//	                if(realnameAuthDO.getRnpReviewType()==0){     //第一次、插入操作
//	                	flag = realnameAuthDao.insert(realnameAuthDO);
//	                }else{    //更新操作
	                	 flag = realnameAuthDao.updateByUserId(realnameAuthDO);
//	                }
	                 if (flag < 0) {
	                      return result.setError(CommonResultCode.BIZ_ERROR, "更新实名认证信息失败");
	                  }
	                //实名认证送积分
	                if (!userService.modifyUserScore(realnameAuthDO.getRnpUserid(), ScoreType.REALNAME_SCORE, null).isSuccess()) {
	                     result.setErrorMessage(CommonResultCode.BIZ_ERROR, "添加积分失败！");
	                 }
	            } else {
	                return result.setError(CommonResultCode.BIZ_ERROR, "更新实名认证信息失败");
	            }
	        } else {
	        	//调用实名认证接口失败、或者认证不一致
	        	 int flag;
//	        	   if(realnameAuthDO.getRnpReviewType()==0){     //第一次、插入操作
//	                	flag = realnameAuthDao.insert(realnameAuthDO);
//	                }else{    //更新操作
	                	 flag = realnameAuthDao.updateByUserId(realnameAuthDO);
//	                }
	                 if (flag < 0) {
	                      return result.setError(CommonResultCode.BIZ_ERROR, "更新实名认证信息失败");
	                  }
//	            return result.setError(CommonResultCode.BIZ_ERROR, "真实姓名和身份证号"+resultMap.get("compResult")+",请检查您录入的信息是否正确！");
	            return result.setError(CommonResultCode.BIZ_ERROR, "真实姓名和身份证号认证失败，请检查您录入的信息是否正确！");
	        }
    	return result;
    }
    
}
