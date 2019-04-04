package com.autoserve.abc.service.biz.intf.government;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.GovPlainJDO;
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.dao.dataobject.GovernmentUpdateHistoryDO;
import com.autoserve.abc.dao.dataobject.search.GovReviewSearchDO;
import com.autoserve.abc.service.biz.entity.GovUpdateHistory;
import com.autoserve.abc.service.biz.entity.History;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 类的实现描述：机构操作的业务类
 *
 * @author RJQ 2014/11/17 19:13.
 */
public interface GovernmentService {

    /**
     * 新增机构
     *
     * @param govPlainJDO 新增机构信息
     * @return BaseResult
     */
    public BaseResult createGovernment(GovPlainJDO govPlainJDO);

    /**
     * 根据id查找机构
     *
     * @param id 机构id
     * @return PlainResult<GovernmentDO>
     */
    public PlainResult<GovernmentDO> findById(Integer id);

    /**
     * 删除某个机构，同时删除该机构的emp
     *
     * @param id 需删除机构id
     * @return BaseResult
     */
    public BaseResult removeGovernment(int id);

    /**
     * 初始化机构对应的唯一员工的密码
     *
     * @param govId 员工id
     * @return PlainResult<String>
     */
    public PlainResult<String> initPassword(int govId);

    /**
     * 修改机构信息（需要审核）
     *
     * @param govPlainJDO 待修改的机构信息
     * @param empId 操作员工id
     * @return BaseResult
     */
    public BaseResult modifyGovernment(GovPlainJDO govPlainJDO, Integer empId);

    /**
     * 更新钱多多账号
     *
     * @param govId 机构Id
     * @param outSeq 钱多多账号
     * @return BaseResult
     */
    public BaseResult updateGovernmentOutSeq(Integer govId, String outSeq);

    /**
     * 禁用某个机构，同时禁用该机构对应的emp
     *
     * @param id 机构ID
     * @return BaseResult
     */
    public BaseResult disableGovernment(int id);

    /**
     * 重新计算担保机构的可担保额度
     *
     * @param govId 担保机构id
     * @param candidateValue 待加减的值
     * @return BaseResult
     */
    public BaseResult computeSettGuarAmount(Integer govId, BigDecimal candidateValue);

    /**
     * 启用某个机构，同时启用该机构对应的emp
     *
     * @param id 机构ID
     * @return BaseResult
     */
    public BaseResult enableGovernment(int id);

    /**
     * 查询机构列表
     *
     * @param gov 查询条件，可选
     * @param pageCondition 分页条件
     * @param govUsername 机构对应员工用户名,可选查询条件
     * @return PageResult<GovernmentDO>
     */
    public PageResult<GovPlainJDO> queryList(GovernmentDO gov, String govUsername, PageCondition pageCondition);

    public PageResult<GovPlainJDO> queryList(GovReviewSearchDO searchDO, PageCondition pageCondition);

    /**
     * 查询机构列表，并将外键字段转成字符串 如：将地区编码xxx转成安徽省-合肥市 客户经理字段，empId转成员工的名字
     * 机构用户名字段，empId转成员工的名字 从机构与担保机构关联表中查询到此为此机构担保的机构名，组成字符串
     *
     * @param gov 查询条件，可选
     * @param govUsername 机构对应员工用户名,可选查询条件
     * @param pageCondition 分页条件，可选
     * @return PageResult<GovPlainJDO>
     */
    public PageResult<GovPlainJDO> queryListWithPlainInfo(GovernmentDO gov, String govUsername,
                                                          PageCondition pageCondition);

    /**
     * 查询机构列表
     *
     * @param govPlainJDO 查询条件，可选
     * @param maxLoanMoneyStart 查询条件，最大借款额度范围低值，可选
     * @param maxLoanMoneyEnd 查询条件，最大借款额度范围高值，可选
     * @param superAreaCode 父级地区代码（二级地区代码放在govPlainJDO中，如果二级代码为null，就根据父级代码查询）
     * @param pageCondition 分页条件，可选
     * @return PageResult<GovPlainJDO>
     */
    public PageResult<GovPlainJDO> queryListWithPlainInfo(GovPlainJDO govPlainJDO, BigDecimal maxLoanMoneyStart,
                                                          BigDecimal maxLoanMoneyEnd, String customerManagerName,
                                                          String superAreaCode, PageCondition pageCondition);

    public PageResult<GovPlainJDO> queryListWithPlainInfo(GovReviewSearchDO searchDO, PageCondition pageCondition);

    /**
     * 查询机构列表，包括queryListWithPlainInfo方法查询到的所有信息 另外加上了机构修改记录的最新审核状态
     *
     * @param gov 查询条件，可选
     * @param govUsername 机构对应员工用户名,可选查询条件
     * @param pageCondition 分页条件
     * @param historyDO 查询条件，可选
     * @return PageResult<GovPlainJDO>
     */
    public PageResult<GovPlainJDO> queryListWithReviewInfo(GovernmentDO gov, String govUsername,
                                                           GovernmentUpdateHistoryDO historyDO,
                                                           PageCondition pageCondition);

    public PageResult<GovPlainJDO> queryListWithReviewInfo(GovReviewSearchDO searchDO, PageCondition pageCondition);

    /**
     * 查询机构信息，并将外键字段转成字符串（包括客户经理，担保机构，所属地区外键转换）
     *
     * @param govId 机构id
     * @return PlainResult<GovPlainJDO>
     */
    public PlainResult<GovPlainJDO> findGovPlainWithAreaById(Integer govId);

    /**
     * 查询机构信息，并将外键字段转成字符串（包括客户经理，担保机构，外键转换）
     * 和findGovPlainWithAreaById方法的区别在于：这里存放地区代码
     *
     * @param govId 机构id
     * @return PlainResult<GovPlainJDO>
     */
    public PlainResult<GovPlainJDO> findGovPlainById(Integer govId);

    /**
     * 根据员工id查询机构信息（abc_employee表中有个所属机构字段）
     *
     * @param empId
     * @return
     */
    public PlainResult<GovPlainJDO> findGovPlainByEmpId(Integer empId);

    /**
     * 批量查询
     *
     * @param list 机构ID
     * @return PlainResult<GovernmentDO>
     */
    public ListResult<GovernmentDO> findByList(List<Integer> list);

    /**
     * 审核后，根据审核结果修改相关信息 审核通过：将修改字段的值设置为机构新值，且将审核状态更新为通过 审核未通过：只将审核状态更新为未通过
     *
     * @param guhId 机构修改记录ID，即abc_gov_update_history表主键
     * @param reviewResult 审核结果
     * @return BaseResult
     */
    public BaseResult updateGovAfterReview(int guhId, ReviewState reviewResult);

    /**
     * 更新机构部分信息
     *
     * @param map map中包含待更新的信息
     * @return int
     */
    public BaseResult modifyByMap(Map<String, Object> map);

    /**
     * 查找机构信息
     *
     * @param guhId 机构更新表主键
     * @return PlainResult<GovernmentDO>
     */
    public PlainResult<GovernmentDO> findByGovUpdateHistoryId(int guhId);

    /**
     * 根据机构主键查找最新修改批次的所有字段信息
     *
     * @param govId 机构修改记录主键
     * @return
     */
    public ListResult<History> findRecentListByGovId(Integer govId);

    /**
     * 根据机构修改记录主键查找最新修改批次的所有字段信息
     *
     * @param historyId 机构修改记录主键
     * @return ListResult<History>
     */
    public ListResult<History> findListByHistoryId(Integer historyId);

    /**
     * 根据员工id查询此员工所属的机构的审核情况
     *
     * @param empId 员工id
     * @param pageCondition 分页条件
     * @return PageResult<GovUpdateHistory>
     */
    public PageResult<GovUpdateHistory> queryReviewOpLogByEmpId(Integer empId, PageCondition pageCondition);

    /**
     * 根据机构id查询此员工所属的机构的审核情况
     *
     * @param govId 机构id
     * @param pageCondition 分页条件
     * @return PageResult<GovUpdateHistory>
     */
    public PageResult<GovUpdateHistory> queryReviewOpLogByGovId(Integer govId, PageCondition pageCondition);

    /**
     * 搜索机构的审核情况
     *
     * @param historyDO 搜索条件
     * @param updateStartDate 修改开始时间
     * @param updateEndDate 修改结束时间
     * @param updateEmpName 修改人名
     * @param pageCondition 分页条件
     * @return
     */
    public PageResult<GovUpdateHistory> searchReviewOpLog(GovernmentUpdateHistoryDO historyDO, Date updateStartDate,
                                                          Date updateEndDate, String updateEmpName,
                                                          PageCondition pageCondition);
    
    public int updateGovName(int id, String name);
}
