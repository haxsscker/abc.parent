package com.autoserve.abc.web.convert;

import com.autoserve.abc.dao.dataobject.TocashRecordJDO;
import com.autoserve.abc.web.vo.cash.TocashRecordVO;

/**
 * 类TocashRecordVOConverter.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月23日 下午6:03:34
 */
public class TocashRecordVOConverter {

    public static TocashRecordVO toTocashRecordVO(TocashRecordJDO tocashRecordJDO) {
        TocashRecordVO vo = new TocashRecordVO();
        vo.setCust_fullname(tocashRecordJDO.getCustFullName());
        vo.setCust_name(tocashRecordJDO.getCustName());
        vo.setExtcash_bank(tocashRecordJDO.getExtcashBank());
        vo.setExtcash_date(tocashRecordJDO.getExtcashDate());
        vo.setExtcash_money(tocashRecordJDO.getExtcashMoney());
        vo.setExtcash_numberofbank(tocashRecordJDO.getExtcashNumberofBank());
        vo.setExtcash_state(tocashRecordJDO.getExtcashState());
        return vo;
    }

}
