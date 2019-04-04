package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.RedUseDO;
import com.autoserve.abc.service.biz.entity.RedUse;
import com.autoserve.abc.service.exception.BusinessException;
import net.sf.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;

/**
 * 类RedUseConverter.java的实现描述：service 层 红包使用实体类转换器 RedUse.java ----->
 * RedUseDO.java; RedUseDO.java -----> RedUse.java
 * 
 * @author fangrui 2014年12月27日 下午3:04:20
 */
public class RedUseConverter {
    public static RedUse toRedUse(RedUseDO redUseDO) {
        RedUse redUse = new RedUse();
        BeanCopier beanCopier = BeanCopier.create(RedUseDO.class, RedUse.class, false);
        beanCopier.copy(redUseDO, redUse, null);
        return redUse;
    }

    public static RedUseDO toRedUseDO(RedUse redUse) {
        RedUseDO redUseDO = new RedUseDO();
        BeanCopier beanCopier = BeanCopier.create(RedUse.class, RedUseDO.class, false);
        beanCopier.copy(redUse, redUseDO, null);
        return redUseDO;
    }

    public static List<RedUse> convertList(List<RedUseDO> list) {
        if (list == null || list.isEmpty())
            throw new BusinessException("传入的list为空");
        List<RedUse> result = new ArrayList<RedUse>();
        for (RedUseDO redUseDO : list) {
            result.add(toRedUse(redUseDO));
        }
        return result;
    }

    public static List<RedUseDO> convertToDOList(List<RedUse> list) {
        if (list == null || list.isEmpty())
            throw new BusinessException("传入的list为空");
        List<RedUseDO> result = new ArrayList<RedUseDO>();
        for (RedUse redUse : list) {
            result.add(toRedUseDO(redUse));
        }
        return result;
    }
}
