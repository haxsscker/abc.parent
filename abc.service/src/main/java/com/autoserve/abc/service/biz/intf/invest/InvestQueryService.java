/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.invest;

import java.math.BigDecimal;

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
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 投资记录查询服务
 * 
 * @author segen189 2014年12月12日 下午10:10:32
 */
public interface InvestQueryService {

    /**
     * 查询投资
     * 
     * @param investId 投资id
     * @return PlainResult<Invest>
     */
    PlainResult<Invest> queryById(int investId);

    /**
     * 查询投资
     * 
     * @param invest 查询条件
     * @return PlainResult<Invest>
     */
    PlainResult<Invest> queryInvest(Invest invest);

    //    /**
    //     * 查询某个原始项目的状态为支付成功的投资记录信息集合
    //     * 
    //     * @param bidId 标id，必选
    //     * @param bidType 标类型，可选
    //     * @param pageCondition，必选
    //     * @return PageResult<Invest>
    //     */
    //    @Deprecated
    //    PageResult<Invest> queryInvestList(int bidId, BidType bidType, PageCondition pageCondition);

    /**
     * 查询某个原始项目的状态为支付成功的投资记录信息集合
     * 
     * @param bidId 标id，必选
     * @param bidType 标类型，可选
     * @return PageResult<Invest>
     */
    @Deprecated
    ListResult<Invest> queryInvestList(int bidId, BidType bidType);

    /**
     * 根据条件查询投资记录信息集合
     * 
     * @param searchJDO，必选
     * @param pageCondition，必选
     * @return PageResult<Invest>
     */
    PageResult<Invest> queryInvestList(InvestSearchDO searchDO, PageCondition pageCondition);

    /**
     * 根据条件查询投资记录信息集合
     * 
     * @param searchJDO，必选
     * @return ListResult<Invest>
     */
    ListResult<Invest> queryInvestList(InvestSearchDO searchDO);

    /**
     * 为投资人赚取的收益
     */
    PlainResult<BigDecimal> queryInvestTotal();

    /**
     * 投资金额最多的前几人
     */
    ListResult<InvestDO> queryMoneyByUser(PageCondition pageCondition);

    /**
     * 投资风云榜列表显示
     */
    PageResult<InvestDO> investmentList(PageCondition pageCondition);

    /**
     * 投资风云榜列表显示
     * 
     * @param time 本年：year,本月：month，本周:week
     * @param pageCondition
     * @return
     */
    PageResult<InvestDO> investmentList(String time, PageCondition pageCondition);

    /**
     * 我的投资回款中列表
     */
    PageResult<MyInvestReceived> queryMyInvestReceived(InvestSearchJDO searchDO, PageCondition pageCondition);

    /**
     * 我的投资投标中列表
     */
    PageResult<MyInvestTender> queryMyInvestTender(InvestSearchJDO searchDO, PageCondition pageCondition);

    /**
     * 我的投资已结清列表
     */
    PageResult<MyInvestClean> queryMyInvestClean(InvestSearchJDO searchDO, PageCondition pageCondition);

    /**
     * 我的债权回款中列表
     */
    PageResult<MyCreditReceived> queryMyCreditReceived(InvestSearchJDO searchDO, PageCondition pageCondition);

    /**
     * 我的债权投标中列表
     */
    PageResult<MyCreditTender> queryMyCreditTender(InvestSearchJDO searchDO, PageCondition pageCondition);

    /**
     * 我的债权已结清列表
     */
    PageResult<MyCreditClean> queryMyCreditClean(InvestSearchJDO searchDO, PageCondition pageCondition);

    /**
     * 投资统计回款中的标
     */
    PageResult<InvestStatistics> queryInvestStatistics(Integer userId, PageCondition pageCondition);

    /**
     * 项目投资人明细表
     * 
     * @param pageCondition
     * @return
     */
    PageResult<InvestorsReportDO> queryInvestorsReport(InvestorsReportDO investorsReportDO, PageCondition pageCondition);

    /**
     * 项目投资人明细表总数
     * 
     * @return
     */
    int queryCountInvestorsReport(InvestorsReportDO investorsReportDO);
    
    /**
     * 上个月交易笔数
     * @return
     */
    int findInvestLastMonth();

    /**
     * 转让项目投资人明细表
     * 
     * @param pageCondition
     * @return
     */
    PageResult<InvestorsReportDO> queryTransferInvestorsReport(InvestorsReportDO investorsReportDo,PageCondition pageCondition);

    /**
     * 转让项目投资人明细表总数
     * 
     * @return
     */
    int queryCountTransferInvestorsReport(InvestorsReportDO investorsReportDo);
    
    /**
     * 查询投资汇总
     * 
     * @param invest 查询条件
     * @return PlainResult<Invest>
     */
    PlainResult<Invest> queryByInvest(Invest invest);
    
    /**
     * 修改投资奖励
     * @param investId
     * @param prizeType
     */
    void updateInvestPrize(int investId, int prizeType);
}
