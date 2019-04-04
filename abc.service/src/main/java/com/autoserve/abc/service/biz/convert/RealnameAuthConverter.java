package com.autoserve.abc.service.biz.convert;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import com.autoserve.abc.dao.dataobject.RealnameAuthDO;
import com.autoserve.abc.service.biz.entity.RealnameAuth;
import com.autoserve.abc.service.biz.enums.DocType;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.enums.SexType;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * 类RealnameAuthConverter.java的实现描述：TODO 类实现描述
 * 
 * @author fangrui 2014年12月23日 下午6:14:39
 */
public class RealnameAuthConverter {
    public static RealnameAuth toRealnameAuth(RealnameAuthDO realnameAuthDO) {
        RealnameAuth realnameAuth = new RealnameAuth();
        realnameAuth.setRnpId(realnameAuthDO.getRnpId());
        realnameAuth.setRnpUserid(realnameAuthDO.getRnpUserid());
        realnameAuth.setRnpName(realnameAuthDO.getRnpName());
        realnameAuth.setRnpSex(SexType.valueOf(realnameAuthDO.getRnpSex()));
        realnameAuth.setRnpNation(realnameAuthDO.getRnpNation());
        realnameAuth.setRnpBirthday(realnameAuthDO.getRnpBirthday());
        realnameAuth.setRnpDocType(DocType.valueOfStrs(realnameAuthDO.getRnpDocType()));
        realnameAuth.setRnpDocNo(realnameAuthDO.getRnpDocNo());
        realnameAuth.setRnpNative(realnameAuthDO.getRnpNative());
        realnameAuth.setRnpIdcardFront(realnameAuthDO.getRnpIdcardFront());
        realnameAuth.setRnpIdcardBack(realnameAuthDO.getRnpIdcardBack());
        realnameAuth.setRnpApplyDate(realnameAuthDO.getRnpApplyDate());
        realnameAuth.setRnpReviewState(ReviewState.valueOf(realnameAuthDO.getRnpReviewState()));
        realnameAuth.setRnpReviewDate(realnameAuthDO.getRnpReviewDate());
        realnameAuth.setRnpReviewNote(realnameAuthDO.getRnpReviewNote());
        realnameAuth.setRnpReviewType(realnameAuthDO.getRnpReviewType());
        return realnameAuth;
    }

    public static RealnameAuth toRealnameAuthUsingBeanCopy(RealnameAuthDO realnameAuthDO) {
        RealnameAuth entity = new RealnameAuth();
        BeanCopier beanCopier = BeanCopier.create(RealnameAuthDO.class, RealnameAuth.class, false);
        beanCopier.copy(realnameAuthDO, entity, null);
        entity.setRnpSex(SexType.valueOf(realnameAuthDO.getRnpSex()));
        entity.setRnpReviewState(ReviewState.valueOf(realnameAuthDO.getRnpReviewState()));
        entity.setRnpDocType(DocType.valueOfStrs(realnameAuthDO.getRnpDocType()));
        return entity;
    }

    public static RealnameAuthDO toRealnameAuthDO(RealnameAuth realNameAuth) {
        RealnameAuthDO realnameAuthDO = new RealnameAuthDO();
        realnameAuthDO.setRnpId(realNameAuth.getRnpId());
        realnameAuthDO.setRnpUserid(realNameAuth.getRnpUserid());
        realnameAuthDO.setRnpName(realNameAuth.getRnpName());
        realnameAuthDO.setRnpSex(realNameAuth.getRnpSex().sex);
        realnameAuthDO.setRnpNation(realNameAuth.getRnpNation());
        realnameAuthDO.setRnpBirthday(realNameAuth.getRnpBirthday());
        realnameAuthDO.setRnpDocType(realNameAuth.getRnpDocType().type);
        realnameAuthDO.setRnpDocNo(realNameAuth.getRnpDocNo());
        realnameAuthDO.setRnpNative(realNameAuth.getRnpNative());
        realnameAuthDO.setRnpIdcardFront(realNameAuth.getRnpIdcardFront());
        realnameAuthDO.setRnpIdcardBack(realNameAuth.getRnpIdcardBack());
        realnameAuthDO.setRnpApplyDate(realNameAuth.getRnpApplyDate());
        realnameAuthDO.setRnpReviewState(realNameAuth.getRnpReviewState().state);
        realnameAuthDO.setRnpReviewDate(realNameAuth.getRnpReviewDate());
        realnameAuthDO.setRnpReviewNote(realNameAuth.getRnpReviewNote());
        realnameAuthDO.setRnpReviewType(realNameAuth.getRnpReviewType());
        return realnameAuthDO;
    }

    public static List<RealnameAuth> convertList(List<RealnameAuthDO> list) {
        if (list == null || list.isEmpty())
            throw new BusinessException("传入的list为空");
        List<RealnameAuth> result = new ArrayList<RealnameAuth>();
        for (RealnameAuthDO rnao : list) {
            result.add(toRealnameAuth(rnao));
        }
        return result;
    }
}
