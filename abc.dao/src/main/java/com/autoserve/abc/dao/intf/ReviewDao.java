package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ReviewDO;
import com.autoserve.abc.dao.dataobject.search.BuyLoanReviewSearchDO;
import com.autoserve.abc.dao.dataobject.search.IntentReviewSearchDO;
import com.autoserve.abc.dao.dataobject.search.LoanGuarGovSearchDO;
import com.autoserve.abc.dao.dataobject.search.LoanReviewSearchDO;
import com.autoserve.abc.dao.dataobject.search.TransferReviewSearchDO;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReviewDao extends BaseDao<ReviewDO, Integer> {

    /**
     * 把所有查询条件都封装在ReviewDO对象中，根据ReviewDO对象查询
     */
    public List<ReviewDO> find(@Param("reviewDO") ReviewDO reviewDO, @Param("pageCondition") PageCondition pageCondition);

    public int updateVersion(@Param("reviewType") Integer reviewType, @Param("applyId") Integer applyId);

    public List<ReviewDO> findByTypeRoles(@Param("reviewType") Integer reviewType,
                                          @Param("roleList") List<Integer> roleList,
                                          @Param("pageCondition") PageCondition pageCondition);

    public int countByTypeRoles(@Param("reviewType") Integer reviewType, @Param("roleList") List<Integer> roleList);

    public List<ReviewDO> findByMultiTypesAndCurrRole(@Param("typeIdxList") List<Integer> typeIdxList,
                                                      @Param("currRole") Integer roleIdx,
                                                      @Param("empId") Integer employeeId,
                                                      @Param("pageCondition") PageCondition pageCondition);

    public int countByMultiTypesAndCurrRole(@Param("typeIdxList") List<Integer> typeIdxList,
                                            @Param("currRole") Integer roleIdx, @Param("empId") Integer employeeId);

    public ReviewDO findActiveByTypeApplyIdWithLock(@Param("reviewType") Integer reviewType,
                                                    @Param("applyId") Integer applyId);

    public List<ReviewDO> findByTypeApplyIdList(@Param("reviewType") Integer reviewType,
                                                @Param("applyIdList") List<Integer> applyIdList);

    public List<ReviewDO> searchLoanReview(@Param("searchDO") LoanReviewSearchDO searchDO,
                                           @Param("pageCondition") PageCondition pageCondition);

    public int countForLoanReviewSearch(@Param("searchDO") LoanReviewSearchDO searchDO);

    public List<ReviewDO> searchTransferReview(@Param("searchDO") TransferReviewSearchDO searchDO,
                                               @Param("pageCondition") PageCondition pageCondition);

    public int countForTransferReviewSearch(@Param("searchDO") TransferReviewSearchDO searchDO);

    public List<ReviewDO> searchBuyLoanReview(@Param("searchDO") BuyLoanReviewSearchDO searchDO,
                                              @Param("pageCondition") PageCondition pageCondition);

    public int countForBuyLoanReviewSearch(@Param("searchDO") BuyLoanReviewSearchDO searchDO);

    public List<ReviewDO> searchIntentReview(@Param("searchDO") IntentReviewSearchDO searchDO,
                                             @Param("pageCondition") PageCondition pageCondition);

    public int countForIntentReviewSearch(@Param("searchDO") IntentReviewSearchDO searchDO);

    public int countForLoanGuarReviewSearch(@Param("searchDO") LoanGuarGovSearchDO searchDO);

    public List<ReviewDO> searchLoanGuarReview(@Param("searchDO") LoanGuarGovSearchDO searchDO,
                                               @Param("pageCondition") PageCondition pageCondition);
    
    //add by xiatt 20160625 客户要求 还款后，未资金划转的债权不做流标处理,而是还原成初始状态
    public int deleteByApplyId(@Param("applyId") Integer applyId,@Param("reviewType") Integer reviewType);

	public int updateReviewState(@Param("newState")int newState, @Param("oldState")int oldState, @Param("applyId")Integer applyId);
}
