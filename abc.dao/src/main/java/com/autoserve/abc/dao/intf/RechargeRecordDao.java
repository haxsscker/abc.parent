package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RechargeRecordDO;
import com.autoserve.abc.dao.dataobject.TocashRecordJDO;
import com.autoserve.abc.dao.dataobject.UserIdentityDO;

public interface RechargeRecordDao extends BaseDao<RechargeRecordDO, Integer> {

    public UserIdentityDO queryUserIdentityBySeqNo(String seqNo);

    public Integer updateBySeqNoSelective(RechargeRecordDO recordDo);

    public List<TocashRecordJDO> userListByParam(@Param("tocashRecordJDO") TocashRecordJDO tocashRecordJDO,
                                                 @Param("pageCondition") PageCondition pageCondition,
                                                 @Param("startDate") String startDate, @Param("endDate") String endDate);

    public int countUserListByParam(@Param("tocashRecordJDO") TocashRecordJDO tocashRecordJDO,
                                    @Param("pageCondition") PageCondition pageCondition,
                                    @Param("startDate") String startDate, @Param("endDate") String endDate);

    public List<TocashRecordJDO> empListByParam(@Param("tocashRecordJDO") TocashRecordJDO tocashRecordJDO,
                                                @Param("pageCondition") PageCondition pageCondition,
                                                @Param("startDate") String startDate, @Param("endDate") String endDate);

    public int countEmpListByParam(@Param("tocashRecordJDO") TocashRecordJDO tocashRecordJDO,
                                   @Param("pageCondition") PageCondition pageCondition,
                                   @Param("startDate") String startDate, @Param("endDate") String endDate);
    
    public List<RechargeRecordDO> rechargeRecordByparam(@Param("rechargeRecordDO") RechargeRecordDO rechargeRecordDO,
            @Param("pageCondition") PageCondition pageCondition,
            @Param("startDate") String startDate, @Param("endDate") String endDate);
    
    public int countRechargeRecordByparam(@Param("rechargeRecordDO") RechargeRecordDO rechargeRecordDO,
            @Param("startDate") String startDate, @Param("endDate") String endDate);

	public void updateChargeStatus(RechargeRecordDO rechargeDo);
}
