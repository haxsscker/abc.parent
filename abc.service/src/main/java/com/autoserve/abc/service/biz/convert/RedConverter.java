package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.RedDO;
import com.autoserve.abc.service.biz.entity.Red;
import com.autoserve.abc.service.biz.enums.RedState;
import com.autoserve.abc.service.biz.enums.RedenvelopeType;
import com.autoserve.abc.service.exception.BusinessException;
import net.sf.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;

/**
 * service 层 红包实体类转换器,RedDO.java ----> Red.java; Red.java ----> RedDO.java
 * 
 * @author fangrui 2014年12月27日 下午2:29:53
 */
public class RedConverter {
    public static Red toRed(RedDO redDO) {
        Red red = new Red();
        BeanCopier beanCopier = BeanCopier.create(RedDO.class, Red.class, false);
        beanCopier.copy(redDO, red, null);
        red.setRedState(RedState.valueOf(redDO.getRedState()));
        red.setRedType(RedenvelopeType.valueOf(redDO.getRedType()));
        return red;
    }

    public static RedDO toRedDO(Red red) {
        if (red == null) {
            return null;
        }
        RedDO redDO = new RedDO();
        BeanCopier beanCopier = BeanCopier.create(Red.class, RedDO.class, false);
        beanCopier.copy(red, redDO, null);
        if (red.getRedState() == null) {
            redDO.setRedState(null);
        } else {
            redDO.setRedState(red.getRedState().state);
        }
        if (red.getRedType() == null) {
            redDO.setRedType(null);
        } else {
            redDO.setRedType(red.getRedType().type);
        }
        return redDO;

    }

    public static List<Red> convertList(List<RedDO> list) {
//        if (list == null || list.isEmpty())
//            throw new BusinessException("传入的list为空");
        List<Red> result = new ArrayList<Red>();
        for (RedDO redDO : list) {
            result.add(toRed(redDO));
        }
        return result;
    }

    public static List<RedDO> convertToDOList(List<Red> list) {
        if (list == null || list.isEmpty())
            throw new BusinessException("传入的list为空");
        List<RedDO> result = new ArrayList<RedDO>();
        for (Red red : list) {
            result.add(toRedDO(red));
        }
        return result;
    }
}
