package com.autoserve.abc.web.convert;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import org.joda.time.DateTime;

import com.autoserve.abc.service.biz.entity.RedsendJ;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.redenvelope.RedsendJVO;

/**
 * 类RedsendJVOConvert.java的实现描述：红包 VO convert转换类
 * 
 * @author lipeng 2014年12月29日 上午10:56:32
 */
public class RedsendJVOConvert {
    /**
     * redsnedJ 到 redsendJVO 的转换
     * 
     * @param redsendJ
     * @return RedsendJVO
     */
    public static RedsendJVO toRedsendJVO(RedsendJ redsendJ) {

        RedsendJVO redsendJVO = new RedsendJVO();

        BeanCopier beanCopier = BeanCopier.create(RedsendJ.class, RedsendJVO.class, false);
        beanCopier.copy(redsendJ, redsendJVO, null);
        redsendJVO.setRsStarttime(new DateTime(redsendJ.getRsStarttime()).toString(DateUtil.DATE_FORMAT));
        redsendJVO.setRsSendtime(new DateTime(redsendJ.getRsSendtime()).toString(DateUtil.DATE_FORMAT));
        redsendJVO.setRsClosetime(new DateTime(redsendJ.getRsClosetime()).toString(DateUtil.DATE_FORMAT));
        redsendJVO.setRuUsetime(new DateTime(redsendJ.getRuUsetime()).toString(DateUtil.DATE_FORMAT));

        return redsendJVO;
    }

    /**
     * redsnedJ集合 到 redsendJVO集合 的转换
     * 
     * @param redsendJs
     * @return List<RedsendJVO>
     */
    public static List<RedsendJVO> convertToList(List<RedsendJ> redsendJs) {
        if (redsendJs == null || redsendJs.isEmpty())
            throw new BusinessException("传入的list为空");
        List<RedsendJVO> result = new ArrayList<RedsendJVO>();

        for (RedsendJ redsendJ : redsendJs) {
            result.add(toRedsendJVO(redsendJ));
        }
        return result;
    }

}
