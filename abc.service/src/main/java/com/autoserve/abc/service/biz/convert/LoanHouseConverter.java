package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.LoanHouseDO;
import com.autoserve.abc.service.biz.entity.LoanHouse;
import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author yuqing.zheng Created on 2014-12-15,10:46
 */
public class LoanHouseConverter {
    public static LoanHouse toLoanHouse(LoanHouseDO loanHouseDO) {
        LoanHouse loanHouse = new LoanHouse();
        if (loanHouseDO == null) {
            return loanHouse;
        }

        loanHouse.setId(loanHouseDO.getLhId());
        loanHouse.setLoanId(loanHouseDO.getLhLoanId());
        loanHouse.setHouseMeasure(loanHouseDO.getLhHouseMeasure());
        loanHouse.setCoverMeasure(loanHouseDO.getLhCoverMeasure());
        loanHouse.setHouseNo(loanHouseDO.getLhHouseNo());
        loanHouse.setHouseArea(loanHouseDO.getLhHouseArea());
        loanHouse.setHouseAge(loanHouseDO.getLhHouseAge());
        loanHouse.setIsMortgage(loanHouseDO.getLhIsMortgage());
        loanHouse.setAssessMoney(loanHouseDO.getLhAssessMoney());
        loanHouse.setCreatetime(loanHouseDO.getLhCreatetime());
        loanHouse.setModifytime(loanHouseDO.getLhModifytime());
        loanHouse.setIsDeleted(loanHouseDO.getLhIsDeleted());

        return loanHouse;
    }

    public static LoanHouseDO toLoanHouseDO(LoanHouse loanHouse) {
        LoanHouseDO loanHouseDO = new LoanHouseDO();
        if (loanHouse == null) {
            return loanHouseDO;
        }

        loanHouseDO.setLhId(loanHouse.getId());
        loanHouseDO.setLhLoanId(loanHouse.getLoanId());
        loanHouseDO.setLhHouseMeasure(loanHouse.getHouseMeasure());
        loanHouseDO.setLhCoverMeasure(loanHouse.getCoverMeasure());
        loanHouseDO.setLhHouseNo(loanHouse.getHouseNo());
        loanHouseDO.setLhHouseArea(loanHouse.getHouseArea());
        loanHouseDO.setLhHouseAge(loanHouse.getHouseAge());
        loanHouseDO.setLhIsMortgage(loanHouse.isMortgage());
        loanHouseDO.setLhAssessMoney(loanHouse.getAssessMoney());
        loanHouseDO.setLhCreatetime(loanHouse.getCreatetime());
        loanHouseDO.setLhModifytime(loanHouse.getModifytime());
        loanHouseDO.setLhIsDeleted(loanHouse.getIsDeleted());

        return loanHouseDO;
    }

    public static List<LoanHouseDO> toLoanHouseDOList(List<LoanHouse> loanHouseList) {
        if (CollectionUtils.isEmpty(loanHouseList)) {
            return Collections.emptyList();
        }

        List<LoanHouseDO> result = Lists.newArrayListWithCapacity(loanHouseList.size());
        for (LoanHouse loanHouse : loanHouseList) {
            result.add(toLoanHouseDO(loanHouse));
        }

        return result;
    }

    public static List<LoanHouse> toLoanHouseList(List<LoanHouseDO> loanHouseDOList) {
        if (CollectionUtils.isEmpty(loanHouseDOList)) {
            return Collections.emptyList();
        }

        List<LoanHouse> result = Lists.newArrayListWithCapacity(loanHouseDOList.size());
        for (LoanHouseDO loanHouseDO : loanHouseDOList) {
            result.add(toLoanHouse(loanHouseDO));
        }

        return result;
    }
}
