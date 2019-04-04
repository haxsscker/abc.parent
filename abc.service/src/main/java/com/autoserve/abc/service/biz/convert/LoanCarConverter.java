package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.LoanCarDO;
import com.autoserve.abc.service.biz.entity.LoanCar;
import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author yuqing.zheng Created on 2014-12-15,10:45
 */
public class LoanCarConverter {
    public static LoanCar toLoanCar(LoanCarDO loanCarDO) {
        LoanCar loanCar = new LoanCar();
        if (loanCarDO == null) {
            return loanCar;
        }

        loanCar.setId(loanCarDO.getLcId());
        loanCar.setLoanId(loanCarDO.getLcLoanId());
        loanCar.setBrand(loanCarDO.getLcCarBrand());
        loanCar.setSeries(loanCarDO.getLcCarSeries());
        loanCar.setOutput(loanCarDO.getLcCarOutput());
        loanCar.setColor(loanCarDO.getLcCarColor());
        loanCar.setBuyYear(loanCarDO.getLcBuyYear());
        loanCar.setTime(loanCarDO.getLcCarTime());
        loanCar.setRun(loanCarDO.getLcCarRun());
        loanCar.setAssessMoney(loanCarDO.getLcAssessMoney());
        loanCar.setCarAddress(loanCarDO.getLcCarAddress());
        loanCar.setCreatetime(loanCarDO.getLcCreatetime());
        loanCar.setModifytime(loanCarDO.getLcModifytime());
        loanCar.setIsDeleted(loanCarDO.getLcIsDeleted());

        return loanCar;
    }

    public static LoanCarDO toLoanCarDO(LoanCar loanCar) {
        LoanCarDO loanCarDO = new LoanCarDO();
        if (loanCar == null) {
            return loanCarDO;
        }

        loanCarDO.setLcId(loanCar.getId());
        loanCarDO.setLcLoanId(loanCar.getLoanId());
        loanCarDO.setLcCarBrand(loanCar.getBrand());
        loanCarDO.setLcCarSeries(loanCar.getSeries());
        loanCarDO.setLcCarOutput(loanCar.getOutput());
        loanCarDO.setLcCarColor(loanCar.getColor());
        loanCarDO.setLcBuyYear(loanCar.getBuyYear());
        loanCarDO.setLcCarTime(loanCar.getTime());
        loanCarDO.setLcCarRun(loanCar.getRun());
        loanCarDO.setLcAssessMoney(loanCar.getAssessMoney());
        loanCarDO.setLcCarAddress(loanCar.getCarAddress());
        loanCarDO.setLcCreatetime(loanCar.getCreatetime());
        loanCarDO.setLcModifytime(loanCar.getModifytime());
        loanCarDO.setLcIsDeleted(loanCar.getIsDeleted());

        return loanCarDO;
    }

    public static List<LoanCarDO> toLoanCarDOList(List<LoanCar> loanCarList) {
        if (CollectionUtils.isEmpty(loanCarList)) {
            return Collections.emptyList();
        }

        List<LoanCarDO> result = Lists.newArrayListWithCapacity(loanCarList.size());
        for (LoanCar loanCar : loanCarList) {
            result.add(toLoanCarDO(loanCar));
        }

        return result;
    }

    public static List<LoanCar> toLoanCarList(List<LoanCarDO> loanCarDOList) {
        if (CollectionUtils.isEmpty(loanCarDOList)) {
            return Collections.emptyList();
        }

        List<LoanCar> result = Lists.newArrayListWithCapacity(loanCarDOList.size());
        for (LoanCarDO loanCarDO : loanCarDOList) {
            result.add(toLoanCar(loanCarDO));
        }

        return result;
    }
}
