package com.autoserve.abc.service.biz.impl.sys;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.dataobject.FeeSettingDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.search.FeeSettingSearchDO;
import com.autoserve.abc.dao.intf.FeeSettingDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.service.biz.convert.FeeSettingConverter;
import com.autoserve.abc.service.biz.entity.FeeSetting;
import com.autoserve.abc.service.biz.enums.ChargeType;
import com.autoserve.abc.service.biz.enums.FeeType;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.intf.sys.FeeSettingService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.DateUtil;

/**
 * @author yuqing.zheng Created on 2014-11-26,10:39
 */
@Service
public class FeeSettingServiceImpl implements FeeSettingService {
    private static Logger logger = LoggerFactory.getLogger(FeeSettingServiceImpl.class);

    @Autowired
    private FeeSettingDao feeSettingDao;

    @Resource
    private LoanDao       loanDao;

    @Override
    public BaseResult creatFeeSetting(FeeSetting feeSetting) {
        try {
            paramCheck(feeSetting);
        } catch (IllegalArgumentException e) {
            return new BaseResult(CommonResultCode.ILEEGAL_ARGUMENT);
        }

        feeSettingDao.insert(FeeSettingConverter.toFeeSettingDO(feeSetting));

        return BaseResult.SUCCESS;
    }

    @Override
    public BaseResult modifyFeeSetting(FeeSetting feeSetting) {
        try {
            paramCheck(feeSetting);
        } catch (IllegalArgumentException e) {
            return new BaseResult(CommonResultCode.ILEEGAL_ARGUMENT);
        }

        feeSettingDao.update(FeeSettingConverter.toFeeSettingDO(feeSetting));

        return BaseResult.SUCCESS;
    }

    @Override
    public ListResult<FeeSetting> queryByFeeType(FeeType feeType, FeeSettingSearchDO feeSettingSearchDO) {
        ListResult<FeeSetting> result = new ListResult<FeeSetting>();

        try {
            paramCheck(feeType);
        } catch (Exception e) {
            result.setErrorMessage(CommonResultCode.ILEEGAL_ARGUMENT);
            return result;
        }

        List<FeeSettingDO> feeSettingDOs = feeSettingDao.findByFeeType(feeType.type, feeSettingSearchDO);
        result.setData(FeeSettingConverter.toFeeSettingList(feeSettingDOs));

        return result;
    }

    @Override
    public PlainResult<FeeSetting> queryByFeeTypeLoanCategory(FeeType feeType, LoanCategory loanCategory,
                                                              BigDecimal loanMoney) {
        PlainResult<FeeSetting> result = new PlainResult<FeeSetting>();

        try {
            paramCheck(feeType, loanCategory);
        } catch (Exception e) {
            result.setErrorMessage(CommonResultCode.ILEEGAL_ARGUMENT);
            return result;
        }

        FeeSettingDO feeSettingDO = feeSettingDao.findByFeeTypeLoanCatogory(feeType.type, loanCategory.category,
                loanMoney);
        if (feeSettingDO == null) {
            result.setErrorMessage(CommonResultCode.ILEEGAL_ARGUMENT);
            result.setMessage("没有设置对应的费用");
            result.setSuccess(false);
            return result;
        }
        result.setData(FeeSettingConverter.toFeeSetting(feeSettingDO));

        return result;
    }

    @Override
    public BaseResult deleteFeeSetting(int feeSettingId) {
        if (feeSettingId <= 0) {
            logger.error("FeeSetting主键必需大于零");
            return new BaseResult(CommonResultCode.ILEEGAL_ARGUMENT);
        }

        feeSettingDao.delete(feeSettingId);

        return BaseResult.SUCCESS;
    }

    private void paramCheck(FeeSetting feeSetting) {
        if (feeSetting == null) {
            logger.error("FeeSetting不能为空");
            throw new IllegalArgumentException();
        }

        if (feeSetting.getFeeType() == null || feeSetting.getLoanCategory() == null
                || feeSetting.getChargeType() == null) {
            logger.error("FeeType, LoanCategory, ChargeType三个字段均不能为空");
            throw new IllegalArgumentException();
        }

        if (feeSetting.getChargeType() == ChargeType.BY_RATIO) {
            if (feeSetting.getMinAmount() == null || feeSetting.getMaxAmount() == null) {
                logger.error("收费类型为按比例收费时，必需设置最小与最大比例");
                throw new IllegalArgumentException();
            }
        }

        if (feeSetting.getChargeType() == ChargeType.BY_DEAL) {
            if (feeSetting.getAccurateAmount() == null) {
                logger.error("收费类型为按每笔收费时，必需设置确定的收费金额");
                throw new IllegalArgumentException();
            }
        }
    }

    private void paramCheck(FeeType feeType) {
        if (feeType == null) {
            logger.error("FeeType费用类型不能为空");
            throw new IllegalArgumentException();
        }
    }

    private void paramCheck(FeeType feeType, LoanCategory loanCategory) {
        paramCheck(feeType);

        if (loanCategory == null) {
            logger.error("LoanCategory项目类型不能为空");
            throw new IllegalArgumentException();
        }
    }

    @Override
    public PlainResult<BigDecimal> calcFee(Integer loanId, FeeType feeType) {
        PlainResult<BigDecimal> result = new PlainResult<BigDecimal>();
        LoanDO loan = loanDao.findById(loanId);
        Date expireDay = loan.getLoanExpireDate();
        int day = this.daysBetween(expireDay);
        BigDecimal base = loan.getLoanCurrentValidInvest();
        FeeSettingDO feeSetting = feeSettingDao.findByFeeTypeLoanCatogory(feeType.type, loan.getLoanCategory(), base);
        if (feeSetting == null) {
            result.setSuccess(false);
            result.setMessage("没有找到对应的计算费用规则，请联系管理员进行费用设置");
            return result;
        }
        BigDecimal fee =BigDecimal.ZERO;
        switch (feeSetting.getFsChargeType()) {
            case 1: {
                //按笔收费
                fee = feeSetting.getFsAccurateAmount();
                break;
            }
            case 2: {
                //按比例收费
                double temp = base.doubleValue() * feeSetting.getFsRate() / 100;
                fee = new BigDecimal(temp).setScale(2, BigDecimal.ROUND_HALF_UP);
                break;
            }
        }
        result.setData(fee);
        return result;
    }
    
    @Override
    public PlainResult<BigDecimal> calcFee(Integer loanId) {
        PlainResult<BigDecimal> result = new PlainResult<BigDecimal>();
        LoanDO loan = loanDao.findById(loanId);
        BigDecimal base = loan.getLoanCurrentValidInvest();
        Date loanExpireDate = loan.getLoanExpireDate();
		int days = DateUtil.diff(new Date(), loanExpireDate,TimeUnit.DAYS);//项目借款总天数,以当前划转日期作为开始时间
//        BigDecimal fee =BigDecimal.ZERO;
        BigDecimal fee =new BigDecimal(days*loan.getFsRate()).multiply(base).divide(new BigDecimal(36000),2, BigDecimal.ROUND_HALF_UP);
//        switch (loan.getFsChargeType()) {
//            case 1: {
//                //按笔收费
//                fee = loan.getFsAccurateAmount();
//                break;
//            }
//            case 2: {
//                //按比例收费
//                double temp = base.doubleValue() * loan.getFsRate() / 100;
//                fee = new BigDecimal(temp).setScale(2, BigDecimal.ROUND_HALF_UP);
//                break;
//            }
//        }
        result.setData(fee);
        return result;
    }
    /**
     * 计算项目到期日距离现在的天数
     * 
     * @param date1
     * @return
     */
    private int daysBetween(Date date1) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date());
        long time1 = cal1.getTimeInMillis();
        long time2 = cal2.getTimeInMillis();
        long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
        return Math.abs((int) betweenDays) + 1;
    }
    
//    public static void main(String[] args) {
//		DateTime date = new DateTime(2015,8, 19, 9, 25 ,0);
//		System.out.println(DateUtil.formatDate(date.toDate()));
//		System.out.println(DateUtil.formatDate(new Date()));
//		System.out.println(daysBetween(date.toDate()));
//	}

}
