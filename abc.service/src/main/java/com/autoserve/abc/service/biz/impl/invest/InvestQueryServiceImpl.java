/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.invest;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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
import com.autoserve.abc.dao.intf.InvestDao;
import com.autoserve.abc.service.biz.convert.InvestConverter;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.Arith;

/**
 * 投资记录查询服务
 * 
 * @author segen189 2014年12月12日 下午10:03:20
 */
@Service
public class InvestQueryServiceImpl implements InvestQueryService {

    @Resource
    private InvestDao investDao;

    @Override
    public PlainResult<Invest> queryById(int investId) {
        PlainResult<Invest> result = new PlainResult<Invest>();

        InvestDO investDO = investDao.findById(investId);
        if (investDO == null) {
            return result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS, "Invest");
        }

        result.setData(InvestConverter.toInvest(investDO));
        return result;
    }

    @Override
    public PlainResult<Invest> queryInvest(Invest invest) {
        PlainResult<Invest> result = new PlainResult<Invest>();
        result.setData(InvestConverter.toInvest(investDao.findByParam(InvestConverter.toInvestDO(invest))));
        return result;
    }

    @Override
    public PageResult<Invest> queryInvestList(InvestSearchDO searchDO, PageCondition pageCondition) {
        PageResult<Invest> result = new PageResult<Invest>(pageCondition);

        int count = investDao.countListBySearchParam(searchDO);
        if (count > 0) {
            result.setData(InvestConverter.toInvestList(investDao.findListBySearchParam(searchDO, pageCondition)));
            result.setTotalCount(count);
        }

        return result;
    }

    @Override
    public ListResult<Invest> queryInvestList(int bidId, BidType bidType) {
        ListResult<Invest> result = new ListResult<Invest>();
        if (bidType == null) {
            return result.setError(CommonResultCode.ILLEGAL_PARAM_BLANK, "BidType");
        }

        InvestDO param = new InvestDO();
        param.setInBidId(bidId);
        param.setInBidType(bidType.getType());
        param.setInInvestState(InvestState.PAID.getState());

        List<Invest> investList = InvestConverter.toInvestList(investDao.findListByParam(param, null));
        result.setData(investList);
        return result;
    }

    @Override
    public ListResult<Invest> queryInvestList(InvestSearchDO searchJDO) {
        ListResult<Invest> result = new ListResult<Invest>();
        result.setData(InvestConverter.toInvestList(investDao.findListBySearchParam(searchJDO, null)));
        return result;
    }

    @Override
    public PlainResult<BigDecimal> queryInvestTotal() {
        PlainResult<BigDecimal> result = new PlainResult<BigDecimal>();
        result.setData(this.investDao.findTotal());
        return result;
    }

    @Override
    public ListResult<InvestDO> queryMoneyByUser(PageCondition pageCondition) {
        ListResult<InvestDO> result = new ListResult<InvestDO>();
        List<InvestDO> list = this.investDao.findMoneyByUser(pageCondition);
        result.setData(list);
        return result;
    }

    @Override
    public PageResult<InvestDO> investmentList(PageCondition pageCondition) {
        PageResult<InvestDO> result = new PageResult<InvestDO>(pageCondition);
        int count = this.investDao.countfindMoneyByUser();
        if (count > 0) {
            List<InvestDO> list = this.investDao.findMoneyByUser(pageCondition);
            result.setData(list);
        }
        result.setTotalCount(count);
        return result;
    }

    @Override
    public PageResult<InvestDO> investmentList(String time, PageCondition pageCondition) {
        PageResult<InvestDO> result = new PageResult<InvestDO>(pageCondition);
        int count = this.investDao.countfindMoneyByUserParam(time);
        if (count > 0) {
            List<InvestDO> list = this.investDao.findMoneyByUserParam(time, pageCondition);
            result.setData(list);
            result.setTotalCount(count);
        }
        return result;
    }

    @Override
    public PageResult<MyInvestReceived> queryMyInvestReceived(InvestSearchJDO searchDO, PageCondition pageCondition) {
        PageResult<MyInvestReceived> result = new PageResult<MyInvestReceived>(pageCondition);
        int count = this.investDao.countMyInvestReceived(searchDO);
        if (count > 0) {
            List<MyInvestReceived> list = this.investDao.findMyInvestReceived(searchDO, pageCondition);
            result.setData(list);
            result.setTotalCount(count);
        }
        return result;
    }

    @Override
    public PageResult<MyInvestTender> queryMyInvestTender(InvestSearchJDO searchDO, PageCondition pageCondition) {
        PageResult<MyInvestTender> result = new PageResult<MyInvestTender>(pageCondition);
        int count = this.investDao.countMyInvestTender(searchDO);
        if (count > 0) {
            List<MyInvestTender> list = this.investDao.findMyInvestTender(searchDO, pageCondition);
            result.setData(list);
            result.setTotalCount(count);
        }
        return result;
    }

    @Override
    public PageResult<MyInvestClean> queryMyInvestClean(InvestSearchJDO searchDO, PageCondition pageCondition) {
        PageResult<MyInvestClean> result = new PageResult<MyInvestClean>(pageCondition);
        int count = this.investDao.countMyInvestClean(searchDO);
        if (count > 0) {
            List<MyInvestClean> list = this.investDao.findMyInvestClean(searchDO, pageCondition);
            result.setData(list);
            result.setTotalCount(count);
        }
        return result;
    }

    @Override
    public PageResult<MyCreditReceived> queryMyCreditReceived(InvestSearchJDO searchDO, PageCondition pageCondition) {
        PageResult<MyCreditReceived> result = new PageResult<MyCreditReceived>(pageCondition);
        int count = this.investDao.countMyCreditReceived(searchDO);
        if (count > 0) {
            List<MyCreditReceived> list = this.investDao.findMyCreditReceived(searchDO, pageCondition);
            result.setData(list);
            result.setTotalCount(count);
        }
        return result;
    }

    @Override
    public PageResult<MyCreditTender> queryMyCreditTender(InvestSearchJDO searchDO, PageCondition pageCondition) {
        PageResult<MyCreditTender> result = new PageResult<MyCreditTender>(pageCondition);
        int count = this.investDao.countMyCreditTender(searchDO);
        if (count > 0) {
            List<MyCreditTender> list = this.investDao.findMyCreditTender(searchDO, pageCondition);
            for(MyCreditTender t:list){
            	BigDecimal percent = Arith.calcPercent(t.getCurrentMoney(), t.getTransferLoanMoney());
            	t.setPercent(percent);
            }
            result.setData(list);
            result.setTotalCount(count);
        }
        return result;
    }

    @Override
    public PageResult<MyCreditClean> queryMyCreditClean(InvestSearchJDO searchDO, PageCondition pageCondition) {
        PageResult<MyCreditClean> result = new PageResult<MyCreditClean>(pageCondition);
        int count = this.investDao.countMyCreditClean(searchDO);
        if (count > 0) {
            List<MyCreditClean> list = this.investDao.findMyCreditClean(searchDO, pageCondition);
            result.setData(list);
            result.setTotalCount(count);
        }
        return result;
    }

    @Override
    public PageResult<InvestStatistics> queryInvestStatistics(Integer userId, PageCondition pageCondition) {
        PageResult<InvestStatistics> result = new PageResult<InvestStatistics>(pageCondition);
        int count = this.investDao.countInvestStatistics(userId);
        if (count > 0) {
            result.setData(this.investDao.findInvestStatistics(userId, pageCondition));
            result.setTotalCount(count);
        }
        return result;
    }

    @Override
    public PageResult<InvestorsReportDO> queryInvestorsReport(InvestorsReportDO investorsReportDO,PageCondition pageCondition) {
        PageResult<InvestorsReportDO> pageResult = new PageResult<InvestorsReportDO>(pageCondition.getPage(),
                pageCondition.getPageSize());
        int totalCount = investDao.countInvestorsReport(investorsReportDO);
        pageResult.setTotalCount(totalCount);

        if (totalCount > 0) {
            List<InvestorsReportDO> list = investDao.investorsReport(investorsReportDO, pageCondition);
            pageResult.setData(list);
        }

        return pageResult;
    }

    @Override
    public int queryCountInvestorsReport(InvestorsReportDO investorsReportDO) {
        int totalCount = investDao.countInvestorsReport(investorsReportDO);
        return totalCount;
    }
    
    @Override
    public int findInvestLastMonth() {
    	return investDao.findInvestLastMonth();
    }

    @Override
    public PageResult<InvestorsReportDO> queryTransferInvestorsReport(InvestorsReportDO investorsReportDo,PageCondition pageCondition) {
        PageResult<InvestorsReportDO> pageResult = new PageResult<InvestorsReportDO>(pageCondition.getPage(),
                pageCondition.getPageSize());
        int totalCount = investDao.countTransferInvestorsReport(investorsReportDo);
        pageResult.setTotalCount(totalCount);

        if (totalCount > 0) {
            List<InvestorsReportDO> list = investDao.transferInvestorsReport(investorsReportDo,pageCondition);
            pageResult.setData(list);
        }

        return pageResult;
    }

    @Override
    public int queryCountTransferInvestorsReport(InvestorsReportDO investorsReportDo) {
        int totalCount = investDao.countTransferInvestorsReport(investorsReportDo);
        return totalCount;
    }
    
    @Override
    public  PlainResult<Invest> queryByInvest(Invest invest) {
        PlainResult<Invest> result = new PlainResult<Invest>();
        result.setData(InvestConverter.toInvest(investDao.findSumByParam(InvestConverter.toInvestDO(invest))));
        return result;
    }
    
    @Override
    public void updateInvestPrize(int investId, int prizeType)
    {
    	investDao.updateInvestPrize(investId, prizeType);
    }
}
