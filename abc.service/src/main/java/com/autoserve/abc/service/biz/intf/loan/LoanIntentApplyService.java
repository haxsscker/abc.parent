package com.autoserve.abc.service.biz.intf.loan;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.LoanCar;
import com.autoserve.abc.service.biz.entity.LoanHouse;
import com.autoserve.abc.service.biz.entity.LoanIntentApply;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 意向申请服务
 * 
 * @author wuqiang.du 2014年12月20日 下午9:48:09
 * @modifier segen189 2015年1月8日 下午17:12:09
 */
public interface LoanIntentApplyService {

    PlainResult<LoanIntentApply> queryById(int id);

    /**
     * 发起意向申请
     * 
     * @param pojo 待添加的意向申请，必选
     * @return BaseResult
     */
    BaseResult createLoanIntentApply(LoanIntentApply pojo);

    /**
     * 修改意向申请
     * 
     * @param pojo 待修改的普通标信息，必选
     * @return BaseResult
     */
    BaseResult modifyApplyLoanIntent(LoanIntentApply pojo);

    /**
     * 个人信用贷-意向申请资料补全<br>
     * 企业经营贷-意向申请资料补全
     * 
     * @param loanIntentId 意向申请id
     * @param loan 普通标信息
     * @return PlainResult<Integer> 借款id
     */
    PlainResult<Integer> infoSupplementForLoan(int loanIntentId, Loan loan);

    /**
     * 房屋抵押贷-意向申请资料补全
     * 
     * @param loanIntentId 意向申请id
     * @param loan 普通标信息
     * @param houseList 房屋抵押信息
     * @return PlainResult<Integer> 借款id
     */
    PlainResult<Integer> infoSupplementForHouseLoan(int loanIntentId, Loan loan, List<LoanHouse> houseList);

    /**
     * 汽车抵押贷-意向申请资料补全
     * 
     * @param loanIntentId 意向申请id
     * @param loan 普通标信息
     * @param carList 汽车抵押信息
     * @return PlainResult<Integer> 借款id
     */
    PlainResult<Integer> infoSupplementForCarLoan(int loanIntentId, Loan loan, List<LoanCar> carList);

    /**
     * 查询分页的意向申请列表
     * 
     * @param pojo 查询条件，可选
     * @param pageCondition 分页条件，必选
     * @return PageResult<Loan>
     */
    PageResult<LoanIntentApply> queryApplyLoanIntentListByParam(LoanIntentApply pojo, PageCondition pageCondition);

    /**
     * 查询分页的意向申请列表
     * 
     * @param pojo 查询条件，可选
     * @param pageCondition 分页条件，必选
     * @return PageResult<Loan>
     */
    PageResult<LoanIntentApply> queryApplyLoanIntentList(PageCondition pageCondition);

    /**
     * 查询id包含在ids列表里的意向申请信息集合
     * 
     * @param ids 查询条件，必选
     * @return ListResult<Loan> 如果结果不含任何数据会返回错误结果；否则返回成功数据
     */
    ListResult<LoanIntentApply> queryByIntentIds(List<Integer> ids);

    /**
     * 个体每日融资项目数
     * 
     * @return
     */
    PlainResult<Integer> countApplyLoanIntentforNow(Integer userid);

    /**
     * 意向审核资料补全
     * 
     * @param loanIntentId
     * @param loan
     * @param list
     * @return
     */
    BaseResult infoSupplementForCustomLoan(int loanIntentId, Loan loan, JSONArray list);
}
