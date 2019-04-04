package com.autoserve.abc.web.convert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.autoserve.abc.dao.dataobject.FundApplyDO;
import com.autoserve.abc.service.biz.entity.FundApply;
import com.autoserve.abc.service.biz.enums.FundPayType;
import com.autoserve.abc.service.biz.enums.FundState;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.util.NumberUtil;
import com.autoserve.abc.web.vo.fund.FundApplyVO;

public class FundApplyVOConvert {

    /*
     * 有限合伙人VO转entity
     */
    public static FundApply toFundApply(FundApplyVO fundApplyVO) {
        FundApply fundApply = new FundApply();
        fundApply.setFaFundId(fundApplyVO.getFaFundId());
        fundApply.setFaFundNo(fundApplyVO.getFaFundNo());
        fundApply.setFaFundName(fundApplyVO.getFaFundName());
        fundApply.setFaFundComp(fundApplyVO.getFaFundComp());
        fundApply.setFaFundMoney(fundApplyVO.getFaFundMoney() != null ? new BigDecimal(fundApplyVO.getFaFundMoney())
                : null);
        fundApply.setFaFundPeriod(fundApplyVO.getFaFundPeriod() != null ? new BigDecimal(fundApplyVO.getFaFundPeriod())
                : null);
        fundApply.setFaFundRate(fundApplyVO.getFaFundRate() != null ? new BigDecimal(fundApplyVO.getFaFundRate())
                : null);
        fundApply.setFaMinMoney(fundApplyVO.getFaMinMoney() != null ? new BigDecimal(fundApplyVO.getFaMinMoney())
                : null);
        fundApply.setFaFundIndustry(fundApplyVO.getFaFundIndustry());
        if (fundApplyVO.getFaPayType() != null) {
            fundApply.setFaPayType(FundPayType.valueOf(Integer.valueOf(fundApplyVO.getFaPayType())));
        }
        fundApply.setFaFundType(fundApplyVO.getFaFundType());
        fundApply.setFaMartgageRate(fundApplyVO.getFaMartgageRate() != null ? new BigDecimal(fundApplyVO
                .getFaMartgageRate()) : null);
        fundApply.setFaMartgageIntrol(fundApplyVO.getFaMartgageIntrol());
        fundApply.setFaFundIntrol(fundApplyVO.getFaFundIntrol());
        fundApply.setFaFundUse(fundApplyVO.getFaFundUse());
        fundApply.setFaFundRisk(fundApplyVO.getFaFundRisk());
        fundApply.setFaPayResource(fundApplyVO.getFaPayResource());
        fundApply.setFaProjectIntrol(fundApplyVO.getFaProjectIntrol());
        fundApply.setFaCompIntrol(fundApplyVO.getFaCompIntrol());
        fundApply.setFaFundState(FundState.valueOf(Integer.valueOf(fundApplyVO.getFaFundState() != null ? fundApplyVO
                .getFaFundState() : "0"))); // 发布状态为空时设为0未发布状态
        fundApply.setFaAddDate(new DateTime(fundApplyVO.getFaAddDate()).toDate());
        return fundApply;
    }

    /*
     * 有限合伙人VO转entity
     */
    public static FundApplyVO toFundApplyVO(FundApply fundApply) {
        FundApplyVO fundApplyVO = new FundApplyVO();
        fundApplyVO.setFaFundId(fundApply.getFaFundId());
        fundApplyVO.setFaFundNo(fundApply.getFaFundNo());
        fundApplyVO.setFaFundName(fundApply.getFaFundName());
        fundApplyVO.setFaFundComp(fundApply.getFaFundComp());
        fundApplyVO.setFaFundMoney(fundApply.getFaFundMoney() != null ? fundApply.getFaFundMoney().toString() : "");
        fundApplyVO.setFaFundPeriod(fundApply.getFaFundPeriod() != null ? fundApply.getFaFundPeriod().toString() : "");
        fundApplyVO.setFaFundRate(fundApply.getFaFundRate() != null ? fundApply.getFaFundRate().toString() : "");
        fundApplyVO.setFaMinMoney(fundApply.getFaMinMoney() != null ? fundApply.getFaMinMoney().toString() : "");
        fundApplyVO.setFaFundIndustry(fundApply.getFaFundIndustry());
        if (fundApply.getFaPayType() != null) {
            fundApplyVO.setFaPayType(String.valueOf(fundApply.getFaPayType().getType()));
        }
        fundApplyVO.setFaFundType(fundApply.getFaFundType());
        fundApplyVO.setFaMartgageRate(fundApply.getFaMartgageRate() != null ? fundApply.getFaMartgageRate().toString()
                : "");
        fundApplyVO.setFaMartgageIntrol(fundApply.getFaMartgageIntrol());
        fundApplyVO.setFaFundIntrol(fundApply.getFaFundIntrol());
        fundApplyVO.setFaFundUse(fundApply.getFaFundUse());
        fundApplyVO.setFaFundRisk(fundApply.getFaFundRisk());
        fundApplyVO.setFaPayResource(fundApply.getFaPayResource());
        fundApplyVO.setFaProjectIntrol(fundApply.getFaProjectIntrol());
        fundApplyVO.setFaCompIntrol(fundApply.getFaCompIntrol());
        fundApplyVO.setFaFundState(fundApply.getFaPayType() != null ? String
                .valueOf(fundApply.getFaPayType().getType()) : "0");
        fundApplyVO.setFaAddDate(new DateTime(fundApply.getFaAddDate()).toString(DateUtil.DATE_FORMAT));
        return fundApplyVO;
    }

    /*
     * 有限合伙人DO转VO
     */
    public static FundApplyVO toFundApplyVO(FundApplyDO fundApplyDO) {
        NumberUtil numberUtil = new NumberUtil();
        FundApplyVO fundApplyVO = new FundApplyVO();
        fundApplyVO.setFaFundId(fundApplyDO.getFaFundId());
        fundApplyVO.setFaFundNo(fundApplyDO.getFaFundNo());
        fundApplyVO.setFaFundName(fundApplyDO.getFaFundName());
        fundApplyVO.setFaFundComp(fundApplyDO.getFaFundComp());
        fundApplyVO.setFaFundMoney(numberUtil.currency(fundApplyDO.getFaFundMoney()));
        fundApplyVO.setFaFundPeriod(fundApplyDO.getFaFundPeriod() != null ? fundApplyDO.getFaFundPeriod().toString()
                : "");
        fundApplyVO.setFaFundRate(fundApplyDO.getFaFundRate().toString() + "%");
        fundApplyVO.setFaMinMoney(numberUtil.currency(fundApplyDO.getFaMinMoney()));
        fundApplyVO.setFaFundIndustry(fundApplyDO.getFaFundIndustry());
        fundApplyVO.setFaPayType(fundApplyDO.getFaPayType() != null ? FundPayType.valueOf(fundApplyDO.getFaPayType())
                .getDes() : "");
        fundApplyVO.setFaFundType(fundApplyDO.getFaFundType());
        fundApplyVO.setFaMartgageRate(fundApplyDO.getFaMartgageRate() != null ? fundApplyDO.getFaMartgageRate()
                .toString() : "");
        fundApplyVO.setFaMartgageIntrol(fundApplyDO.getFaMartgageIntrol());
        fundApplyVO.setFaFundIntrol(fundApplyDO.getFaFundIntrol());
        fundApplyVO.setFaFundUse(fundApplyDO.getFaFundUse());
        fundApplyVO.setFaFundRisk(fundApplyDO.getFaFundRisk());
        fundApplyVO.setFaPayResource(fundApplyDO.getFaPayResource());
        fundApplyVO.setFaProjectIntrol(fundApplyDO.getFaProjectIntrol());
        fundApplyVO.setFaCompIntrol(fundApplyDO.getFaCompIntrol());
        fundApplyVO.setFaFundState(fundApplyDO.getFaFundState() != null ? FundState.valueOf(
                fundApplyDO.getFaFundState()).getDes() : "");
        fundApplyVO.setFaAddDate(new DateTime(fundApplyDO.getFaAddDate()).toString(DateUtil.DATE_FORMAT));
        return fundApplyVO;
    }

    public static List<FundApplyVO> convertVoList(List<FundApplyDO> list) {
        List<FundApplyVO> result = new ArrayList<FundApplyVO>();
        for (FundApplyDO fado : list) {
            result.add(toFundApplyVO(fado));
        }
        return result;
    }
}
