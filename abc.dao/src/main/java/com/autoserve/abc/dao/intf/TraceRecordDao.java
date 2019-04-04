package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.TraceRecordDO;

public interface TraceRecordDao extends BaseDao<TraceRecordDO, Integer> {

    TraceRecordDO findOneByParam(TraceRecordDO param);

    List<TraceRecordDO> findList(@Param("bidId") int bidId, @Param("bidType") int bidType,
                                 @Param("pageCondition") PageCondition pageCondition);

    int countList(@Param("bidId") int bidId, @Param("bidType") int bidType);

    List<TraceRecordDO> findRecordListByLoanId(@Param("loanId") int loanId, 
            @Param("pageCondition") PageCondition pageCondition);
    
    int countListExisted(TraceRecordDO param);
}
