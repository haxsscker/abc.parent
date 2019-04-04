package com.autoserve.abc.dao.intf;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.InvestDO;
import com.autoserve.abc.dao.dataobject.InvestorsReportDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchJDO;
import com.autoserve.abc.dao.dataobject.stage.myinvest.InvestStatistics;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyCreditClean;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyCreditReceived;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyCreditTender;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyInvestClean;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyInvestReceived;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyInvestTender;
import com.autoserve.abc.dao.dataobject.stage.statistics.CollectedAndStill;
import com.autoserve.abc.dao.dataobject.stage.statistics.TenderOverview;

public interface InvestDao extends BaseDao<InvestDO, Integer> {
	
    /**
     * 按条件查询单条记录
     * 
     * @param pojo 查询条件，必选
     * @return InvestDO
     */
    InvestDO findByParam(InvestDO pojo);
    
    /**
     * 查询单标投资次数
     */
    int investNumOfOneLoan(InvestDO invest);
    
    /**
     * 普通标投资次数
     * @param userId
     * @return
     */
    int totalInvest(@Param("userId") Integer userId);

    /**
     * 按条件查询分页结果
     * 
     * @param invest 查询条件，可选
     * @param pageCondition 分页和排序条件，可选
     * @return List<InvestDO>
     */
    List<InvestDO> findListByParam(@Param("invest") InvestDO invest, @Param("pageCondition") PageCondition pageCondition);

    /**
     * 按条件查询结果
     * 
     * @param invest 查询条件，可选
     * @return List<InvestDO>
     */
    List<InvestDO> findListParam(@Param("invest") InvestDO invest);

    /**
     * 根据投资交易流水号修改有效投资金额
     * 
     * @param updateSize 有效投资金额要更新的幅度，必选
     * @param innerSeqNo 内部投资交易流水号，作为查询条件，必选
     * @param oldState 旧的状态，作为查询条件，可选
     * @param newState 新的状态，可选
     * @return int
     */
    int updateValidInvestMoneyByInnerSeqNo(@Param("updateSize") BigDecimal updateSize,
                                           @Param("innerSeqNo") String innerSeqNo, @Param("oldState") Integer oldState,
                                           @Param("newState") Integer newState);

    /**
     * 根据撤投交易流水号修改有效投资金额
     * 
     * @param updateSize 有效投资金额要更新的幅度，必选
     * @param withdrawSeqNo 内部撤投交易流水号，作为查询条件，必选
     * @param oldState 旧的状态，作为查询条件，可选
     * @param newState 新的状态，可选
     * @return int
     */
    int updateValidInvestMoneyByWithdrawSeqNo(@Param("updateSize") BigDecimal updateSize,
                                              @Param("withdrawSeqNo") String withdrawSeqNo,
                                              @Param("oldState") Integer oldState, @Param("newState") Integer newState);

    /**
     * 根据撤投交易流水号修改投资金额
     * 
     * @param updateSize 投资金额要更新的幅度，必选
     * @param withdrawSeqNo 撤投交易流水号，作为查询条件，必选
     * @param oldState 旧的状态，作为查询条件，可选
     * @param newState 新的状态，可选
     * @return int
     */
    int updateInvestMoney(@Param("updateSize") BigDecimal updateSize, @Param("withdrawSeqNo") String withdrawSeqNo,
                          @Param("oldState") Integer oldState, @Param("newState") Integer newState);

    /**
     * 更改投资的状态
     * 
     * @param investId 投资记录id，必选
     * @param oldState 原来的状态，必选
     * @param newState 要更改为的新状态，必选
     * @return int 更改的条数
     */
    int updateInvestState(@Param("investId") int investId, @Param("oldState") int oldState,
                          @Param("newState") int newState);
    
    /**
     * 更改投资奖励情况
     * @return
     */
    int updateInvestPrize(@Param("investId") int investId, @Param("prizeType") int prizeType);

    /**
     * 更改投资的状态
     * 
     * @param bidId 待修改的投资标id，必选
     * @param bidType 待修改的投资标类型，必选
     * @param oldState 原来的状态，必选
     * @param newState 要更改为的新状态，必选
     * @return int 更改的条数
     */
    int batchUpdateInvestState(@Param("bidId") int bidId, @Param("bidType") int bidType,
                               @Param("oldState") int oldState, @Param("newState") int newState);

    /**
     * 统计总数
     * 
     * @param searchDO 查询条件，可选
     * @return int
     */
    int countListBySearchParam(@Param("searchDO") InvestSearchDO searchDO);

    /**
     * 分页查询
     * 
     * @param searchDO 查询条件，可选
     * @param pageCondition 分页条件，可选
     * @return List<InvestDO>
     */
    List<InvestDO> findListBySearchParam(@Param("searchDO") InvestSearchDO searchJDO,
                                         @Param("pageCondition") PageCondition pageCondition);
    /**
     * 查询投资序号
     * @param searchJDO
     * @param investId
     * @return
     */
    int findXhBySearchParam(@Param("searchDO") InvestSearchDO searchJDO, @Param("investId") int investId);
    /**
     * 设置投资的撤投流水号，如果已经设置过了，则不再做设置
     * 
     * @param investId 投资记录id
     * @param withdrawSeqNo 撤投流水号
     * @return int
     */
    int setWithdrawSeqNo(@Param("investId") int investId, @Param("withdrawSeqNo") String withdrawSeqNo);

    /**
     * 还原已经设置的投资记录的撤投流水号字段为空
     * 
     * @param investId 投资记录id
     * @return int
     */
    int resetWithdrawSeqNo(@Param("investId") int investId);

    /**
     * 取得投资人收益总额
     */
    BigDecimal findTotal();

    /**
     * 投资风云：按分页取前几名投资最多用户
     */
    List<InvestDO> findMoneyByUser(@Param("pageCondition") PageCondition pageCondition);

    /**
     * 投资风云：按分页取前几名投资最多用户
     * 
     * @param time 年：year，月:month，周:week
     * @param pageCondition
     * @return
     */
    List<InvestDO> findMoneyByUserParam(@Param("time") String time, @Param("pageCondition") PageCondition pageCondition);

    /**
     * 投资风云：总数
     */
    int countfindMoneyByUser();

    /**
     * 投资风云：总数
     * 
     * @param time 年：year，月:month，周:week
     * @return
     */
    int countfindMoneyByUserParam(@Param("time") String time);

    /**
     * 我的投资回款中列表
     */
    List<MyInvestReceived> findMyInvestReceived(@Param("searchJDO") InvestSearchJDO searchDO,
                                                @Param("pageCondition") PageCondition pageCondition);

    /**
     * 我的投资回款中列表总数
     */
    int countMyInvestReceived(@Param("searchJDO") InvestSearchJDO searchDO);

    /**
     * 我的投资投标中列表
     */
    List<MyInvestTender> findMyInvestTender(@Param("searchJDO") InvestSearchJDO searchDO,
                                            @Param("pageCondition") PageCondition pageCondition);

    /**
     * 我的投资投标中列表总数
     */
    int countMyInvestTender(@Param("searchJDO") InvestSearchJDO searchDO);

    /**
     * 我的投资已结清列表
     */
    List<MyInvestClean> findMyInvestClean(@Param("searchJDO") InvestSearchJDO searchDO,
                                          @Param("pageCondition") PageCondition pageCondition);

    /**
     * 我的投资已结清列表总数
     */
    int countMyInvestClean(@Param("searchJDO") InvestSearchJDO searchDO);

    /**
     * 我的债权回款中列表
     */
    List<MyCreditReceived> findMyCreditReceived(@Param("searchJDO") InvestSearchJDO searchDO,
                                                @Param("pageCondition") PageCondition pageCondition);

    /**
     * 我的债权回款中列表总数
     */
    int countMyCreditReceived(@Param("searchJDO") InvestSearchJDO searchDO);

    /**
     * 我的债权投标中列表
     */
    List<MyCreditTender> findMyCreditTender(@Param("searchJDO") InvestSearchJDO searchDO,
                                            @Param("pageCondition") PageCondition pageCondition);

    /**
     * 我的债权投标中列表总数
     */
    int countMyCreditTender(@Param("searchJDO") InvestSearchJDO searchDO);

    /**
     * 我的债权已结清列表
     */
    List<MyCreditClean> findMyCreditClean(@Param("searchJDO") InvestSearchJDO searchDO,
                                          @Param("pageCondition") PageCondition pageCondition);

    /**
     * 我的债权已结清列表总数
     */
    int countMyCreditClean(@Param("searchJDO") InvestSearchJDO searchDO);

    /**
     * 投资统计回款中的标
     */
    List<InvestStatistics> findInvestStatistics(@Param("userId") Integer userId,
                                                @Param("pageCondition") PageCondition pageCondition);

    /**
     * 投资统计回款中的标
     */
    int countInvestStatistics(@Param("userId") Integer userId);

    /**
     * 投标概况
     */
    List<TenderOverview> findMyTenderOverview(@Param("userId") Integer userId);

    /**
     * 今日待收、今日待还
     */
    CollectedAndStill countCollectedAndStillNow(@Param("userId") Integer userId);

    /**
     * 投资人明细表
     * 
     * @param pageCondition
     * @return
     */
    List<InvestorsReportDO> investorsReport(@Param("investorsReportDO") InvestorsReportDO investorsReportDO,@Param("pageCondition") PageCondition pageCondition);

    /**
     * 投资人明细表总数
     * 
     * @param pageCondition
     * @return
     */
    int countInvestorsReport(@Param("investorsReportDO") InvestorsReportDO investorsReportDO);
    
    int findInvestLastMonth();

    /**
     * 转让投资人明细表
     * 
     * @param pageCondition
     * @return
     */
    List<InvestorsReportDO> transferInvestorsReport(@Param("investorsReportDO") InvestorsReportDO investorsReportDo,@Param("pageCondition") PageCondition pageCondition);

    /**
     * 转让投资人明细表总数
     * 
     * @param pageCondition
     * @return
     */
    int countTransferInvestorsReport(@Param("investorsReportDO") InvestorsReportDO investorsReportDo);
    /**
     * 统计用户投资总额
     * @param userId
     * @return
     */
    BigDecimal tzze(Integer userId);
    /**
     * 统计用户买入债权
     * @param userId
     * @return
     */
    BigDecimal mrzq(Integer userId);
    /**
     * 转让债权
     * @param userId
     * @return
     */
    BigDecimal zrzq(Integer userId);
    /**
     * 转让手续费
     * @param userId
     * @return
     */
    BigDecimal zrsxf(Integer userId);
    
    /**
     * 待收投资总额
     * @param userId
     * @return
     */
    BigDecimal dstzze(Integer userId);
    
    /**
     * 按条件查询单条记录
     * 
     * @param pojo 查询条件，必选
     * @return InvestDO
     */
    InvestDO findSumByParam(InvestDO pojo);

    int updateInInnerSeqNo(InvestDO investDO);

    
    /**
     * 更新普通合同的物理路径
     * 
     * @param investId
     * @param inCommContract
     * @return
     */
    int updateCommContractPath(@Param("investId") Integer investId, @Param("inCommContract") String inCommContract);
    
    
    /**
     * 更新转让合同的物理路径
     * 
     * @param investId
     * @param inTransContract
     * @return
     */
    int updateTransContractPath(@Param("investId") Integer investId, @Param("inTransContract") String inTransContract);
	/**
	 * 更新普通合同的存证编号
	 * @param investId
	 * @param c_Evid
	 * @return
	 */
	int updateCommDepositNumber(@Param("investId") Integer investId, @Param("commDepositNumber") String c_Evid);
	/**
	 * 更新转让合同的存证编号
	 * @param investId
	 * @param c_Evid
	 * @return
	 */
	int updateTransDepositNumber(@Param("investId") Integer investId, @Param("TransDepositNumber") String c_Evid);
}
