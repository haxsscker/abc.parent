package com.autoserve.abc.dao.intf;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.FeeSettingDO;
import com.autoserve.abc.dao.dataobject.search.FeeSettingSearchDO;

/**
 * @author yuqing.zheng Created on 2014-11-25,16:37
 */
public interface FeeSettingDao extends BaseDao<FeeSettingDO, Integer> {
    public List<FeeSettingDO> findByFeeType(@Param("feeType") int feeType,
                                            @Param("feeSettingSearchDO") FeeSettingSearchDO feeSettingSearchDO);

    public FeeSettingDO findByFeeTypeLoanCatogory(@Param("feeType") int feeType, @Param("loanCategory") int loanCategory,@Param("loanMoney")BigDecimal loanMoney);
}
