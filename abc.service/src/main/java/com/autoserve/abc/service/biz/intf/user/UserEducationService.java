package com.autoserve.abc.service.biz.intf.user;

import com.autoserve.abc.service.biz.entity.UserEducation;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author RJQ 2014/12/26 21:52.
 */
public interface UserEducationService {
    /**
     * 查询用户教育背景
     *
     * @param id 用户id
     * @return PlainResult<UserEducation>
     */
    public PlainResult<UserEducation> findUserEducationByUserId(int id);
    
    /**
     * 修改用户教育背景
     *
     * @param UserEducation
     * @return
     */
    public BaseResult modifyUserEducation(UserEducation userEducation);
    /**
     * 创建用户教育背景
     *
     * @param UserEducation
     * @return
     */
    public BaseResult createUserEducation(UserEducation userEducation);
}
