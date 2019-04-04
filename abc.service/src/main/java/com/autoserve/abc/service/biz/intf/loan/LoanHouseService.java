package com.autoserve.abc.service.biz.intf.loan;

import java.util.List;

import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.LoanHouse;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 房屋抵押贷服务
 *
 * @author yuqing.zheng Created on 2014-12-15,11:18
 */
public interface LoanHouseService {

    /**
     * 创建房屋抵押贷
     *
     * @param pojo 待创建的借款信息
     * @param houseList 待创建的房屋抵押贷
     * @return PlainResult<Integer> loanId
     */
    PlainResult<Integer> createHouseLoan(Loan pojo, List<LoanHouse> houseList);

    /**
     * 修改房屋抵押贷
     *
     * @param pojo 普通标信息
     * @param houseList 房屋抵押集合
     * @return BaseResult
     */
    BaseResult modifyHouseLoan(Loan pojo, List<LoanHouse> houseList);

    /**
     * 融资申请房屋抵押贷资料补全
     *
     * @param pojo 普通标信息
     * @param houseList 房屋抵押集合
     * @return BaseResult
     */
    BaseResult infoSupplementForHouseLoan(Loan pojo, List<LoanHouse> houseList);

    /**
     * 根据loanId查找房屋抵押贷信息
     *
     * @param loanId 贷款id
     * @return ListResult<LoanHouse>
     */
    ListResult<LoanHouse> queryByLoanId(int loanId);

}
