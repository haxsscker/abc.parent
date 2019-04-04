/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.intf;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.FullTransferRecordDO;
import com.autoserve.abc.dao.dataobject.FullTransferRecordJDO;
import com.autoserve.abc.dao.dataobject.search.BidIdentitySearchDO;
import com.autoserve.abc.dao.dataobject.search.FullTransferFundsSearchDO;
import com.autoserve.abc.dao.dataobject.search.FullTransferRecordSearchDO;
import com.autoserve.abc.dao.dataobject.summary.FullTransferSummaryDO;

/**
 * 满标资金划转
 * 
 * @author J.YL 2014年11月15日 下午6:30:29
 */
public interface FullTransferRecordDao extends BaseDao<FullTransferRecordDO, Integer> {

    FullTransferRecordDO findByInnerSeqNo(String innerSeqNo);

    int updateByInnerSeqNo(FullTransferRecordDO toModify);

    List<FullTransferRecordJDO> getMoneyTransferList(@Param("fullTransferRecordSearchDO") FullTransferRecordSearchDO fullTransferRecordSearchDO,
                                                     @Param("pageCondition") PageCondition pageCondition);

    List<FullTransferRecordJDO> getAttFulScaTsfListView(@Param("fullTransferRecordSearchDO") FullTransferRecordSearchDO fullTransferRecordSearchDO,
                                                        @Param("pageCondition") PageCondition pageCondition);

    List<FullTransferRecordJDO> getBuyFullTransferListView(@Param("fullTransferRecordSearchDO") FullTransferRecordSearchDO fullTransferRecordSearchDO,
                                                           @Param("pageCondition") PageCondition pageCondition);

    int countMoneyTransferList(@Param("fullTransferRecordSearchDO") FullTransferRecordSearchDO fullTransferRecordSearchDO,
                               @Param("pageCondition") PageCondition pageCondition);

    int countAttFulScaTsfListView(@Param("fullTransferRecordSearchDO") FullTransferRecordSearchDO fullTransferRecordSearchDO,
                                  @Param("pageCondition") PageCondition pageCondition);

    int countBuyFullTransferListView(@Param("fullTransferRecordSearchDO") FullTransferRecordSearchDO fullTransferRecordSearchDO,
                                     @Param("pageCondition") PageCondition pageCondition);

    /**
     * 查询资金划转统计总年数
     * 
     * @return
     * @author J.YL
     */
    int countFullTransferSummaryYears();

    /**
     * 查询资金划转统计的年份列表
     * 
     * @return
     * @author J.YL
     */
    List<Integer> findSummaryYears(@Param("pageCondition") PageCondition pageCondition);

    /**
     * 查询指定年份的资金划转统计的详情
     * 
     * @param years
     * @return
     */
    List<FullTransferSummaryDO> findTransferSummaryInfo(List<Integer> years);

    /**
     * 查询指定年月的资金划转详情
     * 
     * @param date
     * @param pageCondition
     * @return
     */
    List<FullTransferRecordDO> findDetailInfoByDate(@Param("beginDate") Date date,
                                                    @Param("pageCondition") PageCondition pageCondition);

    /**
     * 统计指定年月的资金划转总数
     * 
     * @param date
     * @return
     */
    int countDetailInfoByDate(@Param("beginDate") Date date);

    /**
     * 通过项目编号和项目类型查询项目id
     * 
     * @param loanNo
     * @param loanCategory
     * @return
     */
    List<Integer> findLoanIds(@Param("loanNo") String loanNo, @Param("loanCategory") Integer loanCategory,
                              @Param("date") Date date);

    /**
     * 通过项目id查询转让标的id
     * 
     * @param loanIds
     * @param date
     * @return
     */
    List<Integer> findTransferIds(@Param("loanNo") String loanNo, @Param("loanCategory") Integer loanCategory,
                                  @Param("date") Date date);

    /**
     * 通过项目id查询收购标id
     * 
     * @param loanIds
     * @param date
     * @return
     */
    List<Integer> findBuyIds(@Param("loanNo") String loanNo, @Param("loanCategory") Integer loanCategory,
                             @Param("date") Date date);

    /**
     * 资金划转搜索
     * 
     * @param search
     * @param date
     * @param bidIdentities
     * @param pageCondition
     * @return
     */
    List<FullTransferRecordDO> findBySearchDO(@Param("search") FullTransferFundsSearchDO search,
                                              @Param("date") Date date,
                                              @Param("bidIdentities") List<BidIdentitySearchDO> bidIdentities,
                                              @Param("pageCondition") PageCondition pageCondition);

    int countBySearchDO(@Param("search") FullTransferFundsSearchDO search, @Param("date") Date date,
                        @Param("bidIdentities") List<BidIdentitySearchDO> bidIdentities);

	FullTransferRecordDO findByBidId(@Param("ftrBidId")Integer ftrBidId);

	int updateByBidId(FullTransferRecordDO fullTransRecord);
}
