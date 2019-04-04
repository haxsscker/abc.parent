package com.autoserve.abc.service.biz.convert;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import com.autoserve.abc.dao.dataobject.RealnameAuthJDO;
import com.autoserve.abc.service.biz.entity.RealnameAuthJ;
import com.autoserve.abc.service.biz.enums.DocType;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.enums.SexType;
import com.autoserve.abc.service.biz.enums.UserRealnameIsproven;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * 类RealnameAuthJConverter.java的实现描述：TODO 类实现描述
 * 
 * @author fangrui 2014年12月23日 下午1:12:25
 */
public class RealnameAuthJConverter {

    public static RealnameAuthJ toRealnameAuthJ(RealnameAuthJDO realnameAuthJDO) {
        RealnameAuthJ realnameAuthJ = new RealnameAuthJ();
        realnameAuthJ.setRnpId(realnameAuthJDO.getRnpId());
        realnameAuthJ.setRnpUserid(realnameAuthJDO.getRnpUserid());
        realnameAuthJ.setRnpName(realnameAuthJDO.getRnpName());
        realnameAuthJ.setRnpSex(SexType.valueOf(realnameAuthJDO.getRnpSex()));
        realnameAuthJ.setRnpNation(realnameAuthJDO.getRnpNation());
        realnameAuthJ.setRnpBirthday(realnameAuthJDO.getRnpBirthday());
        realnameAuthJ.setRnpDocType(DocType.valueOfStrs(realnameAuthJDO.getRnpDocType()));
        realnameAuthJ.setRnpDocNo(realnameAuthJDO.getRnpDocNo());
        realnameAuthJ.setRnpNative(realnameAuthJDO.getRnpNative());
        realnameAuthJ.setRnpIdcardFront(realnameAuthJDO.getRnpIdcardFront());
        realnameAuthJ.setRnpIdcardBack(realnameAuthJDO.getRnpIdcardBack());
        realnameAuthJ.setRnpApplyDate(realnameAuthJDO.getRnpApplyDate());
        realnameAuthJ.setRnpReviewState(ReviewState.valueOf(realnameAuthJDO.getRnpReviewState()));
        realnameAuthJ.setRnpReviewDate(realnameAuthJDO.getRnpReviewDate());
        realnameAuthJ.setRnpReviewNote(realnameAuthJDO.getRnpReviewNote());
        realnameAuthJ.setUserRealnameIsproven(UserRealnameIsproven.valueOf(realnameAuthJDO.getUserRealnameIsproven()));
        realnameAuthJ.setUserName(realnameAuthJDO.getUserName());
        realnameAuthJ.setUserScore(realnameAuthJDO.getUserScore());
        return realnameAuthJ;
    }

    public static RealnameAuthJ toRealnameAuthJUsingBeanCopy(RealnameAuthJDO realnameAuthJDO) {
        RealnameAuthJ entity = new RealnameAuthJ();
        BeanCopier beanCopier = BeanCopier.create(RealnameAuthJDO.class, RealnameAuthJ.class, false);
        beanCopier.copy(realnameAuthJDO, entity, null);
        entity.setRnpSex(SexType.valueOf(realnameAuthJDO.getRnpSex()));
        entity.setRnpReviewState(ReviewState.valueOf(realnameAuthJDO.getRnpReviewState()));
        entity.setUserRealnameIsproven(UserRealnameIsproven.valueOf(realnameAuthJDO.getUserRealnameIsproven()));
        entity.setRnpDocType(DocType.valueOfStrs(realnameAuthJDO.getRnpDocType()));
        return entity;
    }

    public static RealnameAuthJDO toRealnameAuthJDO(RealnameAuthJ realNameAuthJ) {
        RealnameAuthJDO realnameAuthJDO = new RealnameAuthJDO();
        realnameAuthJDO.setRnpId(realNameAuthJ.getRnpId());
        realnameAuthJDO.setRnpUserid(realNameAuthJ.getRnpUserid());
        realnameAuthJDO.setRnpName(realNameAuthJ.getRnpName());
        realnameAuthJDO.setRnpSex(realNameAuthJ.getRnpSex().sex);
        realnameAuthJDO.setRnpNation(realNameAuthJ.getRnpNation());
        realnameAuthJDO.setRnpBirthday(realNameAuthJ.getRnpBirthday());
        realnameAuthJDO.setRnpDocType(realNameAuthJ.getRnpDocType().type);
        realnameAuthJDO.setRnpDocNo(realNameAuthJ.getRnpDocNo());
        realnameAuthJDO.setRnpNative(realNameAuthJ.getRnpNative());
        realnameAuthJDO.setRnpIdcardFront(realNameAuthJ.getRnpIdcardFront());
        realnameAuthJDO.setRnpIdcardBack(realNameAuthJ.getRnpIdcardBack());
        realnameAuthJDO.setRnpApplyDate(realNameAuthJ.getRnpApplyDate());
        realnameAuthJDO.setRnpReviewState(realNameAuthJ.getRnpReviewState().state);
        realnameAuthJDO.setRnpReviewDate(realNameAuthJ.getRnpReviewDate());
        realnameAuthJDO.setRnpReviewNote(realNameAuthJ.getRnpReviewNote());
        realnameAuthJDO.setUserName(realNameAuthJ.getUserName());
        realnameAuthJDO.setUserRealnameIsproven(realNameAuthJ.getUserRealnameIsproven().state);
        realnameAuthJDO.setUserScore(realNameAuthJ.getUserScore());
        return realnameAuthJDO;
    }

    public static List<RealnameAuthJ> convertList(List<RealnameAuthJDO> list) {
        if (list == null || list.isEmpty())
            throw new BusinessException("传入的list为空");
        List<RealnameAuthJ> result = new ArrayList<RealnameAuthJ>();
        for (RealnameAuthJDO rnao : list) {
            result.add(toRealnameAuthJ(rnao));
        }
        return result;
    }

}
