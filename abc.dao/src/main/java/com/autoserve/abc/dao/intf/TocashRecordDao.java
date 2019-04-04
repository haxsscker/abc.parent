package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.TocashRecordDO;
import com.autoserve.abc.dao.dataobject.TocashRecordJDO;

/**
 * 提现记录
 * 
 * @author J.YL 2015年1月11日 下午9:04:44
 */
public interface TocashRecordDao extends BaseDao<TocashRecordDO, Integer> {
    public Integer updateBySeqNoSelective(TocashRecordDO toCashDo);

    /**
     * 通过内部交易流水查询提现记录
     * @param SeqNo
     * @return
     */
    public TocashRecordDO queryBySeqNo(String SeqNo);
    public List<TocashRecordJDO> userInvestorExtr(@Param("tocashRecordJDO") TocashRecordJDO tocashRecordJDO,
                                                  @Param("pageCondition") PageCondition pageCondition,
                                                  @Param("startDate") String startDate, @Param("endDate") String endDate);

    public Integer countUserInvestorExtr(@Param("tocashRecordJDO") TocashRecordJDO tocashRecordJDO,
                                         @Param("pageCondition") PageCondition pageCondition,
                                         @Param("startDate") String startDate, @Param("endDate") String endDate);

    public List<TocashRecordJDO> empInvestorExtr(@Param("tocashRecordJDO") TocashRecordJDO tocashRecordJDO,
                                                 @Param("pageCondition") PageCondition pageCondition,
                                                 @Param("startDate") String startDate, @Param("endDate") String endDate);

    public Integer countEmpInvestorExtr(@Param("tocashRecordJDO") TocashRecordJDO tocashRecordJDO,
                                        @Param("pageCondition") PageCondition pageCondition,
                                        @Param("startDate") String startDate, @Param("endDate") String endDate);
    
    public List<TocashRecordDO> queryListByUserId(@Param("tocashRecordDO") TocashRecordDO tocashRecordDO,
							            @Param("pageCondition") PageCondition pageCondition,
							            @Param("startDate") String startDate, @Param("endDate") String endDate);

	public Integer countQueryListByUserId(@Param("tocashRecordDO") TocashRecordDO tocashRecordDO,
                                        @Param("pageCondition") PageCondition pageCondition,
                                        @Param("startDate") String startDate, @Param("endDate") String endDate);
	
	public Integer countDealByParams(@Param("tocashRecordDO") TocashRecordDO tocashRecordDO,
            @Param("pageCondition") PageCondition pageCondition,
            @Param("startDate") String startDate, @Param("endDate") String endDate);
	
	public List<TocashRecordDO> queryDealByParams(@Param("tocashRecordDO") TocashRecordDO tocashRecordJDO,
            @Param("pageCondition") PageCondition pageCondition,
            @Param("startDate") String startDate, @Param("endDate") String endDate);

	/**
	 * 查询本月成功提现的次数
	 * @param userId
	 * @return
	 */
	public Integer countTocashCurrentMonth(@Param("userId")Integer userId);

	public void updateCash(TocashRecordDO toCashDo);
	
	public List<TocashRecordDO> queryPendingRestoCashRecord();
}
