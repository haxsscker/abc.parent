package com.autoserve.abc.service.biz.intf.loan;

import java.util.List;

import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.LoanCar;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 汽车抵押贷款服务
 *
 * @author yuqing.zheng Created on 2014-12-15,11:17
 */
public interface LoanCarService {

    /**
     * 创建汽车抵押贷
     *
     * @param pojo 待创建的借款信息
     * @param carList 待创建的汽车抵押贷集合
     * @return PlainResult<Integer> loanId
     */
    PlainResult<Integer> createCarLoan(Loan pojo, List<LoanCar> carList);

    /**
     * 修改汽车抵押贷
     *
     * @param pojo 普通标信息
     * @param carList 汽车抵押集合
     * @return BaseResult
     */
    BaseResult modifyCarLoan(Loan pojo, List<LoanCar> carList);

    /**
     * 融资申请汽车抵押贷资料补全
     *
     * @param pojo 普通标信息
     * @param carList 汽车抵押集合
     * @return BaseResult
     */
    BaseResult infoSupplementForCarLoan(Loan pojo, List<LoanCar> carList);

    /**
     * 查询汽车抵押贷款
     *
     * @param loanId 借款id
     * @return ListResult<LoanCar>
     */
    ListResult<LoanCar> queryByLoanId(int loanId);

}
