package com.autoserve.abc.dao.intf;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.LoanReportDO;
import com.autoserve.abc.dao.dataobject.search.InvestSearchJDO;
import com.autoserve.abc.dao.dataobject.search.LoanSearchDO;
import com.autoserve.abc.dao.dataobject.stage.myborrow.BorrowStatistics;
import com.autoserve.abc.dao.dataobject.stage.myborrow.MyBorrowClean;
import com.autoserve.abc.dao.dataobject.stage.myborrow.MyBorrowReceived;
import com.autoserve.abc.dao.dataobject.stage.myborrow.MyBorrowTender;
import com.autoserve.abc.dao.dataobject.summary.PostageSummaryDO;

public interface LoanDao extends BaseBidDao<LoanDO, Integer> {

    /**
     * 查询普通标，加行级独占锁
     * 
     * @param loanId
     * @return LoanDO
     */
    LoanDO findByLoanIdWithLock(int loanId);

    /**
     * 查询普通标，加行级独占锁
     * 
     * @param loanIntentId
     * @return LoanDO
     */
    LoanDO findByIntentIdWithLock(int loanIntentId);

    /**
     * 查询标,不加行级锁
     * 
     * @param loanIntentId
     * @return LoanDO
     */
    LoanDO findByLoanId(int loanId);

    /**
     * 更新普通标状态
     * 
     * @param loanId 标id，必选
     * @param oldState 旧状态，可选
     * @param newState 新状态，必选
     * @param modifier 修改人，可选
     * @return int，更新条数
     */
    int updateState(@Param("loanId") int loanId, @Param("oldState") Integer oldState, @Param("newState") int newState,
                    @Param("modifier") Integer modifier);

    /**
     * 区间查询分页结果
     * 
     * @param loan 查询条件，可选
     * @param pageCondition 分页和排序条件，可选
     * @return List<LoanDO>
     */
    List<LoanDO> searchListByParam(@Param("loan") LoanDO loan, @Param("pageCondition") PageCondition pageCondition,
                                   @Param("minSer") String minSer, @Param("maxSer") String maxSer,
                                   @Param("minCha") String minCha, @Param("maxCha") String maxCha);

    /**
     * 统计区间查询分页结果
     * 
     * @param loan 查询条件，可选
     * @param pageCondition 分页和排序条件，可选
     * @return List<LoanDO>
     */
    int coutListByParam(@Param("loan") LoanDO loan, @Param("minSer") String minSer, @Param("maxSer") String maxSer,
                        @Param("minCha") String minCha, @Param("maxCha") String maxCha);

    /**
     * 统计总数
     * 
     * @param searchDO 查询条件，可选
     * @param investSearchJDO
     * @return int
     */
    int countListBySearchParam(@Param("searchDO") LoanSearchDO searchDO);

    /**
     * 分页查询
     * 
     * @param searchDO 查询条件，可选
     * @param investSearchJDO
     * @param pageCondition 分页条件，可选
     * @return List<LoanDO>
     */
    List<LoanDO> findListBySearchParam(@Param("searchDO") LoanSearchDO searchDO,
                                       @Param("pageCondition") PageCondition pageCondition);

    /**
     * 列表查询
     * 
     * @param searchDO 查询条件，可选
     * @param investSearchJDO
     * @param pageCondition 分页条件，可选
     * @return List<LoanDO>
     */
    List<LoanDO> findListBySearch(@Param("searchDO") LoanSearchDO searchDO);

    /**
     * 根据意向申请id查询单条纪录
     * 
     * @param intentApplyId 意向申请id
     * @return LoanDO
     */
    LoanDO findByIntentApplyId(int intentApplyId);

    /**
     * 根据意向申请id列表查询多条记录
     * 
     * @param intentIds 意向申请id列表
     * @return List<LoanDO>
     */
    List<LoanDO> findByIntentIdList(@Param("intentIds") List<Integer> intentIds);

    /**
     * 按ID和搜索条件在列表里的所有记录
     * 
     * @param list 查询条件，必选
     * @return List<T>
     */
    List<LoanDO> findByListAndSrearch(@Param("list") List<Integer> list,
                                      @Param("searchJDO") InvestSearchJDO investSearchJDO);

    /**
     * 查询有效交易金额
     */
    BigDecimal findTotal();
    
    /**
     * 查询当年有效交易金额
     */
    BigDecimal findTotalNowYear();
    
    /**
     * 查询上个月成交额
     */
    BigDecimal findTotalLastMonth();
    
    //待收项目数
    int sumLoanToPay();
    
    //总项目额
    BigDecimal sumLoanTotal();
    
    //总成交项目数
    int sumLoanNum();

    /**
     * 我的借款列表（已结清）
     */
    List<MyBorrowClean> findMyBorrowClean(@Param("searchJDO") InvestSearchJDO searchDO,
                                          @Param("pageCondition") PageCondition pageCondition);

    /**
     * 我的借款列表（已结清）总数
     */
    int countMyBorrowClean(@Param("searchJDO") InvestSearchJDO searchDO);

    /**
     * 我的借款列表（还款中）
     */
    List<MyBorrowReceived> findMyBorrowReceived(@Param("searchJDO") InvestSearchJDO searchDO,
                                                @Param("pageCondition") PageCondition pageCondition);

    /**
     * 我的借款列表（还款中）总数
     */
    int countMyBorrowReceived(@Param("searchJDO") InvestSearchJDO searchDO);

    /**
     * 我的借款列表 （投标中）
     */
    List<MyBorrowTender> findMyBorrowTender(@Param("searchJDO") InvestSearchJDO searchDO,
                                            @Param("pageCondition") PageCondition pageCondition);

    /**
     * 我的借款列表 （投标中）总数
     */
    int countMyBorrowTender(@Param("searchJDO") InvestSearchJDO searchDO);

    /**
     * 我的借款统计明细
     */
    List<BorrowStatistics> findBorrowStatistics(@Param("userId") Integer userId,
                                                @Param("pageCondition") PageCondition pageCondition);

    /**
     * 我的借款统计明细总数
     */
    int countBorrowStatistics(@Param("userId") Integer userId);

    /**
     * 查询优质推荐
     * 
     * @return
     */
    List<LoanDO> findOptimization(@Param("topN") Integer topN);

    /**
     * 更新合同的物理路径
     * 
     * @param loanId
     * @param contractPath
     * @return
     */
    int updateContractPath(@Param("loanId") Integer loanId, @Param("contractPath") String contractPath);

    /**
     * 取得将要到期借款总数
     * 
     * @param expireDay
     * @return
     */
    int countLoanByExpire(@Param("expireDay") Integer expireDay);
    
    /**
     * 获取用户上次投标的类型
     * @param userId
     * @return
     */
    int queryLastLoanType(@Param("userId") Integer userId);
    
    /**
     * 项目发布统计表
     */
    List<LoanReportDO> findLoanReport(@Param("loan") LoanReportDO loanReportDO ,@Param("pageCondition") PageCondition pageCondition);

    /**
     * 项目发布统计表总数
     */
    int countLoanReport(@Param("loan") LoanReportDO loanReportDO);

    /**
     * 更新相关子标的贷后信息
     * @param loanId
     * @param postLoanInfo
     * @return
     */
    int postLoanInfoEdit(@Param("loanId")Integer loanId, @Param("postLoanInfo")String postLoanInfo);
    
    /**
     * 查询提前还款概况
     * @return
     */
    PostageSummaryDO queryAheadPay();
    /**
     * 根据流水号查询标的
     * @param merBillNo
     * @return
     */
	LoanDO findBySeqNo(String merBillNo);
	
	/**
     * 查询需要迁移的标的信息
     * @param merBillNo
     * @return
     */
	List<LoanDO> findByLoanByState();
	/**
	 * 更新借款合同存证编号
	 * @param loanId
	 * @param c_Evid
	 * @return
	 */
	int updateDepositNumber(@Param("loanId")Integer loanId, @Param("loanDepositNumber")String c_Evid);
	/**
	 * 检验项目编号的唯一性
	 * @param loanId
	 * @param loanNo
	 * @return
	 */
	int checkLoanNo(@Param("loanId")Integer loanId,@Param("loanNo") String loanNo);

}
