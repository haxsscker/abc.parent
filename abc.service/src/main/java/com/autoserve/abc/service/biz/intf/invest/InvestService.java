/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.invest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.autoserve.abc.dao.dataobject.stage.statistics.CollectedAndStill;
import com.autoserve.abc.dao.dataobject.stage.statistics.TenderOverview;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 投资管理服务<br>
 * 创建投资的流程：<br>
 * 1) 前台用户对普通项目进行投资时<br>
 * 2) 前台用户对转让项目进行投资时<br>
 * 3) 前台用户对收购项目进行认购时
 * 
 * @author segen189 2014年11月19日 下午12:05:04
 */
public interface InvestService {

    /**
     * 添加投资
     * 
     * @param invest 待添加的投资信息
     * @return PlainResult<Integer>
     */
    @Deprecated
    PlainResult<Integer> createInvest(Invest pojo);

    /**
     * 添加投资
     * 
     * @param invest 待添加的投资信息
     * @param redsendIdList 红包发放纪录id列表
     * @return PlainResult<Integer>
     */
    PlainResult<Integer> createInvest(Invest pojo, List<Integer> redsendIdList);
    
    /**
     * 单标投资次数
     * @param pojo
     * @return
     */
    int investNumOfOneLoan(Invest pojo);

    /**
     * 撤回投资
     * 
     * @param investId 待撤资的投资信息id
     * @param userId 投资人
     * @return BaseResult
     */
    BaseResult withdrawInvest(int investId, int userId);

    /**
     * 更新投资状态
     * 
     * @param bidid 待修改的bidid
     * @param oldState 旧的投资状态
     * @param newState 新的投资状态
     * @return BaseResult
     */
    BaseResult modifyInvestState(int bidid, InvestState oldState, InvestState newState);

    /**
     * 批量更新投资状态
     * 
     * @param bidId 待修改的投资标id，必选
     * @param bidType 待修改的投资标类型，必选
     * @param oldState 旧的投资状态，必选
     * @param newState 新的投资状态，必选
     * @return BaseResult
     */
    BaseResult batchModifyInvestState(int bidId, BidType bidType, InvestState oldState, InvestState newState);

    /**
     * 投标概况
     */
    ListResult<TenderOverview> findMyTenderOverview(Integer userId);

    /**
     * 今日待收、今日待还
     */
    PlainResult<CollectedAndStill> findMyCollectedAndStill(Integer userId);

    /**
     * 根据标号 查询投资记录(支付成功)
     */
    ListResult<Invest> findListByParam(int loanId);

    /**
     * 总的待收金额和待还金额
     * @param userId
     * @return
     */
	PlainResult<Map<String, BigDecimal>> findTotalIncomeAndPayMoneyByUserId(Integer userId);
	 /**
     * 根据标号 查询投资记录(待收益)
     */
    ListResult<Invest> findListByParamEarning(int loanId);
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
	 * 统计用户转让债权
	 * @param userId
	 * @return
	 */
	BigDecimal zrzq(Integer userId);
	
	/**
     * 统计用户待收投资总额
     * @param userId
     * @return
     */
	BigDecimal dstzze(Integer userId);

	int batchUpdateInvestState(Integer loanId, int type, int state, int state2);
	/**
	 * 检查投资条件是否满足
	 * @param userId
	 * @return
	 */
	Map<String, String> checkInvestConditions(Integer userId);
}
