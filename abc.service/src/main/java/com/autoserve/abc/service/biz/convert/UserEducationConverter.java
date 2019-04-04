package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.UserEducationDO;
import com.autoserve.abc.service.biz.entity.UserEducation;
import com.autoserve.abc.service.biz.enums.EducationLevel;
import net.sf.cglib.beans.BeanCopier;

/**
 * @author RJQ 2014/12/13 13:53.
 */
public class UserEducationConverter {
    public static UserEducation toUserEducation(UserEducationDO userEducationDO) {
        UserEducation userEducation = new UserEducation();
        BeanCopier beanCopier = BeanCopier.create(UserEducationDO.class, UserEducation.class, false);
        beanCopier.copy(userEducationDO, userEducation, null);
        userEducation.setUeTopEducation(EducationLevel.valueOf(userEducationDO.getUeTopEducation()));

        return userEducation;
    }

	public static UserEducationDO toUserEducationDO(UserEducation userEducation) {
		UserEducationDO userEducationDO = new UserEducationDO();
        BeanCopier beanCopier = BeanCopier.create(UserEducation.class, UserEducationDO.class, false);
        beanCopier.copy(userEducation, userEducationDO, null);
        if(userEducation.getUeTopEducation()!=null){
        userEducationDO.setUeTopEducation(userEducation.getUeTopEducation().level);
        }
        return userEducationDO;
	}
}
