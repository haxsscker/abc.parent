package com.autoserve.abc.service.biz.impl.government;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.dao.dataobject.GovGuarDO;
import com.autoserve.abc.dao.dataobject.GovPlainJDO;
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.dao.dataobject.GovernmentUpdateDetailDO;
import com.autoserve.abc.dao.dataobject.GovernmentUpdateHistoryDO;
import com.autoserve.abc.dao.dataobject.RoleDO;
import com.autoserve.abc.dao.dataobject.search.GovReviewSearchDO;
import com.autoserve.abc.dao.intf.GovernmentDao;
import com.autoserve.abc.service.biz.callback.Callback;
import com.autoserve.abc.service.biz.callback.center.ReviewCallbackCenter;
import com.autoserve.abc.service.biz.convert.GovernmentConverter;
import com.autoserve.abc.service.biz.convert.HistoryConverter;
import com.autoserve.abc.service.biz.entity.GovUpdateHistory;
import com.autoserve.abc.service.biz.entity.Government;
import com.autoserve.abc.service.biz.entity.History;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.ReviewOp;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.EntityState;
import com.autoserve.abc.service.biz.enums.GovProvideGuarState;
import com.autoserve.abc.service.biz.enums.ReviewOpType;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.intf.common.AreaService;
import com.autoserve.abc.service.biz.intf.employee.EmployeeRoleService;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.government.GovGuarService;
import com.autoserve.abc.service.biz.intf.government.GovUpdateDetailService;
import com.autoserve.abc.service.biz.intf.government.GovUpdateHistoryService;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.review.ReviewOpLogService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.MapResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.service.util.HistoryUtil;

/**
 * 机构服务类
 *
 * @author RJQ 2014/11/18 17:05.
 */
//@Service
public class GovernmentServiceImpl implements GovernmentService {

    @Resource
    private GovernmentDao            governmentDao;

    @Resource
    private GovUpdateDetailService   detailService;

    @Resource
    private ReviewService            reviewService;

    @Resource
    private GovUpdateHistoryService  updateHistoryService;

    @Resource
    private AreaService              areaService;

    @Resource
    private EmployeeService          employeeService;

    @Resource
    private GovGuarService           govGuarService;

    @Resource
    private EmployeeRoleService      employeeRoleService;

    @Resource
    private ReviewOpLogService       reviewOpLogService;

    @Resource
    private LoanQueryService         loanService;

    /**
     * 机构信息修改时发起审核回调方法
     */
    private final Callback<ReviewOp> reviewCallback = new Callback<ReviewOp>() {

                                                        @Override
                                                        public BaseResult doCallback(ReviewOp data) {
                                                            int guhId = data.getReview().getApplyId();
                                                            ReviewOpType reviewOpType = data.getOpType();
                                                            BaseResult result = null;

                                                            switch (reviewOpType) {
                                                                case PASS:
                                                                    result = updateGovAfterReview(guhId,
                                                                            ReviewState.PASS_REVIEW);
                                                                    return result;
                                                                case REJECT:
                                                                    result = updateGovAfterReview(guhId,
                                                                            ReviewState.FAILED_PASS_REVIEW);
                                                                    return result;
                                                            }
                                                            return result;
                                                        }
                                                    };

    @PostConstruct
    private void registerCallBack() {
        ReviewCallbackCenter.registerCallback(ReviewType.GOVERNMENT_INFO_MODIFY_REVIEW, reviewCallback);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult createGovernment(GovPlainJDO govPlainJDO) {
        BaseResult result = new BaseResult();
        /**
         * govPlainDO包含多个担保机构id字符串，需要解析出来，判断选择担保机构是否满足最大借款额度
         */
        List<GovernmentDO> guarGovDOs = new ArrayList<GovernmentDO>();//担保公司列表
        if (GovProvideGuarState.PROVIDE.getState() != (govPlainJDO.getGovIsOfferGuar())) {//如果是小贷公司，需要判断最大借款额度和担保机构可用额度之间的可行性关系
            String guarIds = govPlainJDO.getGovGuarId();
            List<Integer> ids = getGuarIdList(guarIds);
            if (!ids.isEmpty()) {
                guarGovDOs = governmentDao.findByList(ids);
            }
            BigDecimal govMaxLoanAmount = govPlainJDO.getGovMaxLoanAmount();//最大借款额度
            if (govMaxLoanAmount == null || govMaxLoanAmount.compareTo(BigDecimal.ZERO) < 0) {
                result.setErrorMessage(CommonResultCode.BIZ_ERROR, "小贷公司最大借款额度参数有误");
                return result;
            }
            //最大担保额度和担保机构可用额度之间的可行性判断
            if (!judgeGuarantee(govMaxLoanAmount, guarGovDOs, null)) {//所选担保机构不足以提供最大借款做担保，报错，添加机构失败
                result.setErrorMessage(CommonResultCode.BIZ_ERROR, "所选担保机构不足以提供最大借款做担保");
                return result;
            }
        } else {//担保机构，将可用担保额度置为最大担保额度
            govPlainJDO.setGovSettGuarAmount(govPlainJDO.getGovMaxGuarAmount());
        }

        /**
         * 检查用户名是否存在，已存在的话直接返回错误提示
         */
        String govEmpUserName = govPlainJDO.getGovUserName();
        BaseResult baseResult = employeeService.checkEmpNameExist(govEmpUserName);
        if (!baseResult.isSuccess()) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, baseResult.getMessage());
            return result;
        }

        /**
         * 将机构的基本信息插入表abc_government
         */
        GovernmentDO governmentDO = GovernmentConverter.convertToGovernmentDO(govPlainJDO);
        governmentDO.setGovIsEnable(EntityState.STATE_ENABLE.getState());
        int returnVal = governmentDao.insert(governmentDO);
        if (returnVal <= 0) {
            throw new BusinessException("添加机构失败!");
        }
        int govId = governmentDO.getGovId();

        List<GovGuarDO> govGuarDOs = distributeLoanAmount(govId, govPlainJDO.getGovMaxLoanAmount(), guarGovDOs);//分配每个担保机构的担保额度

        /**
         * 向机构信息与担保机构关联表abc_gov_guar中批量插入每条记录
         */
        BaseResult govGuarResult = govGuarService.createByDOList(govGuarDOs);
        if (!govGuarResult.isSuccess()) {
            throw new BusinessException(govGuarResult.getMessage());
        }
        /**
         * 更新各担保公司的可用担保额度(无批量更新接口，循环更新)
         */
        if (!guarGovDOs.isEmpty()) {
            for (GovernmentDO gdo : guarGovDOs) {
                governmentDao.updateByPrimaryKeySelective(gdo);
            }
        }

        /**
         * 取出govPlainDO中包含的机构对应的员工用户名， 并在abc_employee表中插入记录，同时初始化密码，记录此用户所属机构id
         */
        EmployeeDO employeeDO = new EmployeeDO();
        employeeDO.setEmpName(govEmpUserName);
        employeeDO.setEmpOrgId(govId);
        employeeDO.setEmpRealName(govEmpUserName);
        employeeDO.setEmpState(EntityState.STATE_ENABLE.getState());
        BaseResult empResult = employeeService.createEmployee(employeeDO);
        if (!empResult.isSuccess()) {
            throw new BusinessException(empResult.getMessage());
        }

        /**
         * 为员工添加角色，如果该机构的“能否提供担保”字段值为0,即不能提供担保，就是小贷公司员工角色，否则就是担保公司员工角色
         */
        BaseRoleType roleName;
        if (govPlainJDO.getGovIsOfferGuar().equals(GovProvideGuarState.PROVIDE.getState())) {//提供担保
            roleName = BaseRoleType.INSURANCE_GOVERNMENT;
        } else {
            roleName = BaseRoleType.LOAN_GOVERNMENT;
        }
        result = employeeRoleService.allocatRoleForEmp(employeeDO.getEmpId(), roleName);
        if (!result.isSuccess()) {
            throw new BusinessException("为机构账户分配角色失败！");
        }

        result.setMessage("添加机构成功！");
        return result;
    }

    /**
     * 分配担保额度
     *
     * @param govId 借款机构id
     * @param govMaxLoanAmount 最大借款额度
     * @param guarGovDOs 担保机构列表
     * @return
     */
    private List<GovGuarDO> distributeLoanAmount(final int govId, final BigDecimal govMaxLoanAmount,
                                                 List<GovernmentDO> guarGovDOs) {
        List<GovGuarDO> govGuarDOs = new ArrayList<GovGuarDO>();
        if (govMaxLoanAmount == null) {//最大借款额度为空，说明是担保公司，由于担保公司也可以选担保公司，这时直接添加他们之间的关联关系，没有涉及将借款和担保额度
            for (int i = 0, j = guarGovDOs.size(); i < j; ++i) {
                GovGuarDO govGuarDO = new GovGuarDO();
                govGuarDO.setGgGovId(govId);
                govGuarDO.setGgGuarId(guarGovDOs.get(i).getGovId());
                govGuarDO.setGgGuarAmount(BigDecimal.ZERO);
                govGuarDOs.add(govGuarDO);
            }
        } else {
            BigDecimal tmpLoanAmount = govMaxLoanAmount;
            for (int i = 0, j = guarGovDOs.size(); i < j; ++i) {
                GovGuarDO govGuarDO = new GovGuarDO();
                govGuarDO.setGgGovId(govId);
                govGuarDO.setGgGuarId(guarGovDOs.get(i).getGovId());
                if (!tmpLoanAmount.equals(BigDecimal.ZERO)) {
                    BigDecimal tmp = guarGovDOs.get(i).getGovSettGuarAmount().subtract(tmpLoanAmount);
                    if (tmp.compareTo(BigDecimal.ZERO) >= 0) {//当前担保机构的可担保额度足够担保
                        govGuarDO.setGgGuarAmount(tmpLoanAmount);
                        guarGovDOs.get(i).setGovSettGuarAmount(
                                guarGovDOs.get(i).getGovSettGuarAmount().subtract(tmpLoanAmount));
                        tmpLoanAmount = BigDecimal.ZERO;
                        govGuarDOs.add(govGuarDO);
                    } else {
                        govGuarDO.setGgGuarAmount(guarGovDOs.get(i).getGovSettGuarAmount());
                        tmpLoanAmount = tmpLoanAmount.subtract(guarGovDOs.get(i).getGovSettGuarAmount());
                        guarGovDOs.get(i).setGovSettGuarAmount(BigDecimal.ZERO);
                        govGuarDOs.add(govGuarDO);
                    }
                } else {
                    govGuarDO.setGgGuarAmount(BigDecimal.ZERO);
                    govGuarDOs.add(govGuarDO);
                }

            }
        }
        return govGuarDOs;
    }

    /**
     * 判断最大借款额度是否大于所选择的担保机构的可担保额度之和，如果大于，返回false
     *
     * @param govMaxLoanAmount 最大借款额度
     * @param guarGovDOs 担保机构列表
     * @param govGuarDOs 如果是新增机构，调用此方法时传入null；如果是修改机构时，此参数代表修改前为此机构做担保的担保机构关联列表
     * @return boolean 最大借款额度小于选择的担保机构可提供的额度之和，返回true;否则返回false
     */
    private boolean judgeGuarantee(final BigDecimal govMaxLoanAmount, final List<GovernmentDO> guarGovDOs,
                                   final List<GovGuarDO> govGuarDOs) {
        //为选择担保机构，且最大借款额度大于0时，返回false
        if ((guarGovDOs == null || guarGovDOs.isEmpty())) {
            return (govMaxLoanAmount.compareTo(BigDecimal.ZERO) <= 0);
        }

        BigDecimal tmpGuarAmount = BigDecimal.ZERO;
        if (govGuarDOs != null) {//传入参数不为空，修改机构操作
            for (int i = 0, j = guarGovDOs.size(); i < j; ++i) {
                for (int k = 0, l = govGuarDOs.size(); k < l; ++k) {
                    //如果修改时选择的担保机构和修改前存在重复，就先将此担保机构的可用担保额度暂时加上已担保的额度，在计算和最大担保额度之间的关系
                    if (guarGovDOs.get(i).getGovId().equals(govGuarDOs.get(k).getGgGuarId())) {
                        guarGovDOs.get(i).setGovSettGuarAmount(
                                guarGovDOs.get(i).getGovSettGuarAmount().add(govGuarDOs.get(k).getGgGuarAmount()));
                    }
                }
            }
        }

        //将选择的担保机构的可用担保额度相加，再和最大借款额度相比较
        for (GovernmentDO gdo : guarGovDOs) {
            tmpGuarAmount = tmpGuarAmount.add(gdo.getGovSettGuarAmount());
        }
        return (govMaxLoanAmount.compareTo(tmpGuarAmount) <= 0);
    }

    /**
     * 形如“1,2,3,4,5”的字符串转成List
     *
     * @param idStr
     * @return
     */
    private List<Integer> getGuarIdList(String idStr) {
        List<Integer> result = new ArrayList<Integer>();
        if (null != idStr && 0 != idStr.length()) {
            String[] ids = idStr.split(",");
            for (String id : ids) {
                result.add(Integer.parseInt(id));
            }
        }
        return result;
    }

    @Override
    public PlainResult<GovernmentDO> findById(Integer id) {
        PlainResult<GovernmentDO> result = new PlainResult<GovernmentDO>();
        GovernmentDO governmentDO = governmentDao.findById(id);
        if (governmentDO == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到指定机构！");
            return result;
        }
        result.setData(governmentDO);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult removeGovernment(int id) {
        BaseResult result = new BaseResult();
        PlainResult<GovernmentDO> plainResult = findById(id);
        if (!plainResult.isSuccess()) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到指定机构！");
            return result;
        }
        GovernmentDO governmentDO = plainResult.getData();

        /**
         * 判断机构是否是启用状态，启用状态不可删除
         */
        if (EntityState.STATE_ENABLE.getState() == governmentDO.getGovIsEnable()) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "已启用的机构不可删除！");
            return result;
        }

        /**
         * 判断机构是否和项目相关，如果相关，不可删除
         */
        GovProvideGuarState govType = governmentDO.getGovIsOfferGuar() == GovProvideGuarState.PROVIDE.getState() ? GovProvideGuarState.PROVIDE
                : GovProvideGuarState.NOT_PROVIDE;
        Boolean existFlag = loanService.existProjectRelateGovment(id, govType).getData();
        if (existFlag != null && existFlag) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "业务进行中，不可删除！");
            return result;
        }

        /**
         * 删除机构之前，判断此机构是小贷还是担保机构，分别做处理
         */
        if (govType == GovProvideGuarState.PROVIDE) {//担保机构
            if (checkGuarGovIsInGuarantee(governmentDO)) {//是否在担保中
                result.setErrorMessage(CommonResultCode.BIZ_ERROR, "该机构正在担保中，不能删除！");
                return result;
            }
            //删除机构关联表中，以此机构作为被担保的记录
            govGuarService.removeAllGuarByGovId(id);
        } else {//小贷机构
            result = removeGuaranteeRelationShip(id);
            if (!result.isSuccess()) {
                throw new BusinessException("删除担保记录失败！");
            }
        }

        //逻辑删除此机构
        int returnVal = updateGovState(id, EntityState.STATE_DELETED);
        if (returnVal <= 0) {
            throw new BusinessException("删除失败！");
        }

        //删除此机构对应的员工，即abc_employee表中数据
        result = employeeService.removeEmpByGovId(id);
        if (!result.isSuccess()) {
            throw new BusinessException("删除失败！");
        }
        result.setMessage("删除成功！");
        return result;
    }

    /**
     * 删除机构关联表中，被担保的记录
     *
     * @param id 被担保机构id
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    private BaseResult removeGuaranteeRelationShip(int id) {
        BaseResult result;
        //查询机构关联表中，以此机构作为被担保的记录，查出已担保记录，然后将各担保机构的可担保额度加上
        GovGuarDO govGuarDO = new GovGuarDO();
        govGuarDO.setGgGovId(id);
        List<GovGuarDO> govGuarDOs = govGuarService.queryList(govGuarDO).getData();
        if (govGuarDOs != null && !govGuarDOs.isEmpty()) {
            for (GovGuarDO guarDO : govGuarDOs) {
                GovernmentDO gov = governmentDao.findById(guarDO.getGgGuarId());
                gov.setGovId(guarDO.getGgGuarId());
                gov.setGovSettGuarAmount(gov.getGovSettGuarAmount().add(guarDO.getGgGuarAmount()));
                governmentDao.updateByPrimaryKeySelective(gov);
            }
        }
        //删除机构关联表中，以此机构作为被担保的记录
        result = govGuarService.removeAllGuarByGovId(id);
        return result;
    }

    /**
     * 检查担保机构是否在担保中:查看该机构的最大担保额度和可用担保额度是否相同，相同的话表示现在没做担保可以删除，否则不能删除
     *
     * @param gov
     * @return
     */
    private boolean checkGuarGovIsInGuarantee(GovernmentDO gov) {
        BigDecimal maxGuarAmount = gov.getGovMaxGuarAmount();//最大担保额度
        BigDecimal settGuarAmount = gov.getGovSettGuarAmount();//可用担保额度
        if (maxGuarAmount != null && settGuarAmount != null) {
            if (!maxGuarAmount.equals(settGuarAmount)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 更新机构状态
     *
     * @param id 机构ID
     * @param state 待更新成的状态
     * @return int
     */
    private int updateGovState(int id, EntityState state) {
        GovernmentDO governmentDO = new GovernmentDO();
        governmentDO.setGovId(id);
        governmentDO.setGovIsEnable(state.getState());

        return governmentDao.updateByPrimaryKeySelective(governmentDO);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult modifyGovernment(GovPlainJDO newGovPlainJDO, Integer empId) {
        BaseResult result = new BaseResult();
        /**
         * TODO 如果修改字段包括是否提供担保（即更改担保和小贷）以及担保额度，借款额度的话，需要判断是否存在业务活动， TODO
         * 而且相关的担保额度，借款额度都系要修改，目前不做实现，客服不要去修改这样的字段，如果小贷或担保自己去修改的话，客服在审核时一定要注意！！！
         */
        /**
         * 判断机构是否和业务活动相关，如果相关，暂时做不可修改的处理
         */
        Integer govId = newGovPlainJDO.getGovId();
        PlainResult<GovPlainJDO> plainResult = this.findGovPlainById(govId);
        if (!plainResult.isSuccess()) {
            return plainResult;
        }
        GovPlainJDO oldGovPlainJDO = plainResult.getData();
        GovProvideGuarState govType = oldGovPlainJDO.getGovIsOfferGuar() == GovProvideGuarState.PROVIDE.getState() ? GovProvideGuarState.PROVIDE
                : GovProvideGuarState.NOT_PROVIDE;
        Boolean existFlag = loanService.existProjectRelateGovment(govId, govType).getData();
        if (existFlag != null && existFlag) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "业务进行中，不可修改！");
            return result;
        }

        /**
         * 如果是小贷公司，需要判断最大借款额度和担保机构可用额度之间的关系
         * 注意：修改小贷机构的担保公司时，不能直接看所选的几家担保公司剩下可用担保额度之和
         * ，如果这家小贷以前选了某个担保，现在还包括这家担保时需要注意考虑进去
         */
        List<GovernmentDO> guarGovDOs = new ArrayList<GovernmentDO>();//担保公司信息
        if (GovProvideGuarState.PROVIDE.getState() != (newGovPlainJDO.getGovIsOfferGuar())) {
            BigDecimal govMaxLoanAmount = newGovPlainJDO.getGovMaxLoanAmount();//最大借款额度
            if (govMaxLoanAmount == null || govMaxLoanAmount.compareTo(BigDecimal.ZERO) < 0) {
                result.setErrorMessage(CommonResultCode.BIZ_ERROR, "小贷公司最大借款额度参数有误");
                return result;
            }
            String guarIds = newGovPlainJDO.getGovGuarId();
            List<Integer> ids = getGuarIdList(guarIds);
            List<GovGuarDO> govGuarDOs = null;
            if (!ids.isEmpty()) {
                guarGovDOs = governmentDao.findByList(ids);
                GovGuarDO queryGuarDO = new GovGuarDO();
                queryGuarDO.setGgGovId(govId);
                govGuarDOs = govGuarService.queryList(queryGuarDO).getData();
            }
            //最大担保额度和担保机构可用额度之间的可行性判断
            if (!judgeGuarantee(govMaxLoanAmount, guarGovDOs, govGuarDOs)) {//所选担保机构不足以提供最大借款做担保，报错，添加机构失败
                result.setErrorMessage(CommonResultCode.BIZ_ERROR, "所选担保机构不足以提供最大借款做担保");
                return result;
            }
        }

        /**
         * 查询当前修改人的角色，如果是客服，跳过审核过程直接修改，如果是其他角色（针对机构用户），发起审核流程
         */
        boolean IS_PLATFORM_SERVICE_ROLE = checkIsPlatformServiceRole(empId);
        if (IS_PLATFORM_SERVICE_ROLE) {//平台客服
            result = modifyGovByPlatformService(newGovPlainJDO, guarGovDOs);
        } else {
            result = modifyGovByOtherRole(newGovPlainJDO, oldGovPlainJDO, empId);
        }
        return result;
    }

    /**
     * 平台客服以外的角色修改机构信息
     *
     * @param newGovPlainJDO 新修改的机构信息
     * @param empId 修改员工ID
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    private BaseResult modifyGovByOtherRole(GovPlainJDO newGovPlainJDO, GovPlainJDO oldGovPlainJDO, Integer empId) {
        BaseResult result = new BaseResult();
        /**
         * 与修改前机构信息，进行比较，如果有变化记下修改前后的值
         */
        List<History> histories = getModifyFieldsAndValue(oldGovPlainJDO, newGovPlainJDO);
        if (null == histories || histories.size() == 0) {
            result.setMessage("未作修改！");
            return result;
        }
        Integer govId = oldGovPlainJDO.getGovId();
        /**
         * 查询修改批次，如果没有修改记录默认为1，否则机构修改批次加1作为新的批次 如果最近一次机构信息修改仍在待审核中，则不可再次提交修改请求
         */
        GovernmentUpdateHistoryDO historyDO = updateHistoryService.findLastUpdateHistory(govId).getData();
        int updateNum = 1;
        if (null != historyDO) {
            boolean reviewPending = (historyDO.getGuhAuditState() == ReviewState.PENDING_REVIEW.getState());
            //如果最近一次机构信息修改仍在待审核中，则不可再次提交修改请求
            if (reviewPending) {
                result.setErrorMessage(CommonResultCode.BIZ_ERROR, "上次修改仍在审核中，无法再次修改！");
                return result;
            }
            //机构修改批次加1作为新的批次
            updateNum = historyDO.getGuhUpdateNumber() + 1;
        }

        /**
         * 在表abc_gov_update_history中添加一条记录， 包括员工id，机构id，修改日期，批次，审核状态记录为未审核，
         */
        int updateHistoryId = createGovUpdateHistory(govId, empId, updateNum);
        if (updateHistoryId <= 0) {
            throw new BusinessException("添加修改记录失败！");
        }

        /**
         * 将修改字段记录在abc_gov_update_detail表中，一个字段一条记录
         */
        int affectLines = createGovUpdateDetail(histories, updateHistoryId);
        if (affectLines != histories.size()) {
            throw new BusinessException("记录字段详细修改信息失败！");
        }

        /**
         * 发起更新审核
         */
        BaseResult reviewResult = startReviewProcess(updateHistoryId);
        if (!reviewResult.isSuccess()) {
            throw new BusinessException("发起审核失败！");
        }

        result.setMessage("修改请求已提交，等待审核中...");
        return result;
    }

    /**
     * 平台客服修改机构信息
     *
     * @param newGovPlainJDO 新提交的机构信息
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    private BaseResult modifyGovByPlatformService(GovPlainJDO newGovPlainJDO, List<GovernmentDO> guarGovDOs) {
        BaseResult result = new BaseResult();
        int val = governmentDao.updateByPrimaryKeySelective(newGovPlainJDO);
        if (val <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "修改失败！");
            return result;
        }
        Integer govId = newGovPlainJDO.getGovId();

        removeGuaranteeRelationShip(newGovPlainJDO.getGovId());

        List<GovGuarDO> govGuarDOs = distributeLoanAmount(govId, newGovPlainJDO.getGovMaxLoanAmount(), guarGovDOs);//分配每个担保机构的担保额度
        //向机构信息与担保机构关联表abc_gov_guar中批量插入每条记录
        BaseResult govGuarResult = govGuarService.createByDOList(govGuarDOs);
        if (!govGuarResult.isSuccess()) {
            throw new BusinessException(govGuarResult.getMessage());
        }
        //更新各担保公司的可用担保额度(无批量更新接口，循环更新)
        if (!guarGovDOs.isEmpty()) {
            for (GovernmentDO gdo : guarGovDOs) {
                governmentDao.updateByPrimaryKeySelective(gdo);
            }
        }

        result.setMessage("修改成功！");
        return result;
    }

    /**
     * 查询员工是否具有平台客服角色
     *
     * @param empId 员工id
     * @return
     */
    private boolean checkIsPlatformServiceRole(Integer empId) {
        boolean IS_PLATFORM_SERVICE__ROLE = false;
        List<RoleDO> roleDOs = employeeRoleService.queryRoleBySingleEmp(empId).getData();
        if (roleDOs == null || roleDOs.size() == 0) {
            throw new BusinessException("查询修改人员角色失败！");
        }
        for (int i = 0, j = roleDOs.size(); i < j; ++i) {
            if (roleDOs.get(i).getRoleName().equals(BaseRoleType.PLATFORM_SERVICE.roleName)) {
                IS_PLATFORM_SERVICE__ROLE = true;
                break;
            }
        }

        return IS_PLATFORM_SERVICE__ROLE;
    }

    /**
     * 找出修改的字段和修改前后的值
     *
     * @param oldGovPlainJDO 修改前的机构信息
     * @param newGovPlainJDO 修改后的机构信息
     * @return List<History>
     */
    private List<History> getModifyFieldsAndValue(GovPlainJDO oldGovPlainJDO, GovPlainJDO newGovPlainJDO) {
        Government oldGov = GovernmentConverter.convertToGovernment(oldGovPlainJDO);
        Government newGov = GovernmentConverter.convertToGovernment(newGovPlainJDO);
        HistoryUtil<Government> historyUtil = new HistoryUtil<Government>();
        //修改页面无机构的是否可用以及可用担保额度字段的修改项，将这些字段排除，注意：字符串要和属性名称一致
        String[] excludeFiledNames = { "govIsEnable", "govSettGuarAmount" };
        return historyUtil.record(Government.class, oldGov, newGov, excludeFiledNames);
    }

    /**
     * 发起审核流程
     *
     * @param applyId 申请修改记录的id
     * @return BaseResult
     */
    private BaseResult startReviewProcess(int applyId) {
        Review review = new Review();
        review.setApplyId(applyId);
        review.setType(ReviewType.GOVERNMENT_INFO_MODIFY_REVIEW);
        review.setCurrRole(BaseRoleType.PLATFORM_SERVICE);
        return reviewService.initiateReview(review);
    }

    /**
     * 机构信息中每更改一个字段，就在更改明细表中插入字段名，修改前后的值等
     *
     * @param histories 修改的字段，修改前值和修改后值
     * @param updateHistoryId 关联的修改记录表ID
     * @return int
     */
    private int createGovUpdateDetail(List<History> histories, int updateHistoryId) {
        List<GovernmentUpdateDetailDO> list = new ArrayList<GovernmentUpdateDetailDO>();
        for (int i = 0, j = histories.size(); i < j; ++i) {
            //机构状态标记字段排除掉，因为页面修改机构信息时没有修改该字段的地方，传到后台该字段一定为null，再和它修改前的值比较时一定不一样，因此需要去掉
            if (histories.get(i).getGuhField().equals("govIsEnable")) {
                continue;
            }
            GovernmentUpdateDetailDO updateDetailDO = HistoryConverter.convertToDO(histories.get(i));
            updateDetailDO.setGuhUpdateHistoryId(updateHistoryId);
            list.add(updateDetailDO);
        }
        PlainResult<Integer> result = detailService.batchInsert(list);
        if (!result.isSuccess()) {
            return 0;
        }
        return result.getData();
    }

    /**
     * 新增机构修改记录
     *
     * @param govId 机构ID
     * @param empId 修改员工ID
     * @param updateNum 修改批次
     * @return int
     */
    private int createGovUpdateHistory(int govId, int empId, int updateNum) {
        GovernmentUpdateHistoryDO updateHistoryDO = new GovernmentUpdateHistoryDO();
        updateHistoryDO.setGuhGovid(govId);
        updateHistoryDO.setGuhUpdateEmp(empId);
        updateHistoryDO.setGuhUpdateNumber(updateNum);
        updateHistoryDO.setGuhAuditState(ReviewState.PENDING_REVIEW.getState());
        updateHistoryService.createHistory(updateHistoryDO);
        return updateHistoryDO.getGuhId();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult disableGovernment(int id) {
        BaseResult result = new BaseResult();
        int resultVal = updateGovState(id, EntityState.STATE_DISABLE);
        if (resultVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "禁用失败！");
            return result;
        }
        EmployeeDO employeeDO = new EmployeeDO();
        employeeDO.setEmpState(EntityState.STATE_DISABLE.getState());
        result = employeeService.modifyByGovId(id, employeeDO);
        if (!result.isSuccess()) {
            throw new BusinessException("禁用失败！");
        }
        result.setMessage("禁用成功！");
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult enableGovernment(int id) {
        BaseResult result = new BaseResult();
        int resultVal = updateGovState(id, EntityState.STATE_ENABLE);
        if (resultVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "启用失败！");
            return result;
        }
        EmployeeDO employeeDO = new EmployeeDO();
        employeeDO.setEmpState(EntityState.STATE_ENABLE.getState());
        result = employeeService.modifyByGovId(id, employeeDO);
        if (!result.isSuccess()) {
            throw new BusinessException("启用失败！");
        }
        result.setMessage("启用成功！");
        return result;
    }

    @Override
    public PlainResult<String> initPassword(int govId) {
        return employeeService.initPwdByGovId(govId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageResult<GovPlainJDO> queryList(GovernmentDO gov, String govUsername, PageCondition pageCondition) {
        PageResult<GovPlainJDO> result = new PageResult<GovPlainJDO>(pageCondition.getPage(),
                pageCondition.getPageSize());
        GovPlainJDO govPlainDO = GovernmentConverter.convertToGovPlainJDO(gov);
        if (govUsername != null) {
            govPlainDO.setGovUserName(govUsername);
        }
        int totalCount = governmentDao.countListByParam(govPlainDO);
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            result.setData(governmentDao.findListByParam(govPlainDO, pageCondition));
        }

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageResult<GovPlainJDO> queryList(GovReviewSearchDO searchDO, PageCondition pageCondition) {
        PageResult<GovPlainJDO> result = new PageResult<GovPlainJDO>(pageCondition.getPage(),
                pageCondition.getPageSize());
        int totalCount = governmentDao.countForSearch(searchDO);
        result.setTotalCount(totalCount);

        if (totalCount > 0) {
            result.setData(governmentDao.search(searchDO, pageCondition));
        }

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageResult<GovPlainJDO> queryListWithPlainInfo(GovernmentDO gov, String govUsername,
                                                          PageCondition pageCondition) {
        //查询出机构列表，sql已将机构用户名id查询出对应的名字
        PageResult<GovPlainJDO> pageResult = queryList(gov, govUsername, pageCondition);
        if (pageResult.getData() != null) {
            List<GovPlainJDO> govList = pageResult.getData();
            //取出机构list中包含的所有地区编码，批量查询出对应的字符串，例如：将编码1210转成河北省-石家庄
            List<String> codeList = getAreaCodeList(govList);
            Map<String, String> areaMap = areaService.queryMapByAreaCodes(codeList);
            decodeAreaInfo(areaMap, govList);

            //取出机构list中包含的所有客户经理id，批量查询出客户经理名称
            List<Integer> empIds = getCustomerManagerIdList(govList);
            if (null != empIds && 0 != empIds.size()) {
                Map<Integer, String> empNamesMap = employeeService.findEmpNamesByIds(empIds);
                decodeEmpNameInfo(empNamesMap, govList);
            }

            //取出机构list中包含的所有机构id，批量查询出为此机构做担保的机构名字符串，以“，”分隔，如”机构1，机构2，机构3”
            List<Integer> govIds = getGovIdList(govList);
            if (null != govIds && 0 != govIds.size()) {
                Map<Integer, StringBuilder> govGuarNames = govGuarService.findGovGuarByList(govIds);
                decodeGovGuarNameInfo(govGuarNames, govList);
            }

            pageResult.setData(govList);
            pageResult.setTotalCount(govList.size());
        }

        return pageResult;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageResult<GovPlainJDO> queryListWithPlainInfo(GovPlainJDO govPlainJDO, BigDecimal maxLoanMoneyStart,
                                                          BigDecimal maxLoanMoneyEnd, String customerManagerName,
                                                          String superAreaCode, PageCondition pageCondition) {
        //查询出机构列表，sql已将机构用户名id查询出对应的名字
        PageResult<GovPlainJDO> pageResult = new PageResult<GovPlainJDO>(pageCondition.getPage(),
                pageCondition.getPageSize());
        int totalCount = governmentDao.countListByMap(govPlainJDO, maxLoanMoneyStart, maxLoanMoneyEnd,
                customerManagerName, superAreaCode);
        pageResult.setTotalCount(totalCount);
        if (totalCount <= 0) {
            return pageResult;
        }

        List<GovPlainJDO> govList = governmentDao.findListByMap(govPlainJDO, maxLoanMoneyStart, maxLoanMoneyEnd,
                customerManagerName, superAreaCode, pageCondition);
        //取出机构list中包含的所有地区编码，批量查询出对应的字符串，例如：将编码1210转成河北省-石家庄
        List<String> codeList = getAreaCodeList(govList);
        Map<String, String> areaMap = areaService.queryMapByAreaCodes(codeList);
        decodeAreaInfo(areaMap, govList);

        //取出机构list中包含的所有客户经理id，批量查询出客户经理名称
        List<Integer> empIds = getCustomerManagerIdList(govList);
        if (null != empIds && 0 != empIds.size()) {
            Map<Integer, String> empNamesMap = employeeService.findEmpNamesByIds(empIds);
            decodeEmpNameInfo(empNamesMap, govList);
        }

        //取出机构list中包含的所有机构id，批量查询出为此机构做担保的机构名字符串，以“，”分隔，如”机构1，机构2，机构3”
        List<Integer> govIds = getGovIdList(govList);
        if (null != govIds && 0 != govIds.size()) {
            Map<Integer, StringBuilder> govGuarNames = govGuarService.findGovGuarByList(govIds);
            decodeGovGuarNameInfo(govGuarNames, govList);
        }

        pageResult.setData(govList);
        return pageResult;
    }

    @Override
    public PageResult<GovPlainJDO> queryListWithPlainInfo(GovReviewSearchDO searchDO, PageCondition pageCondition) {
        //查询出机构列表，sql已将机构用户名id查询出对应的名字
        PageResult<GovPlainJDO> pageResult = queryList(searchDO, pageCondition);
        if (pageResult.getData() != null) {
            List<GovPlainJDO> govList = pageResult.getData();

            //取出机构list中包含的所有机构id，批量查询出为此机构做担保的机构名字符串，以“，”分隔，如”机构1，机构2，机构3”
            List<Integer> govIds = getGovIdList(govList);
            if (null != govIds && 0 != govIds.size()) {
                Map<Integer, StringBuilder> govGuarNames = govGuarService.findGovGuarByList(govIds);
                decodeGovGuarNameInfo(govGuarNames, govList);
            }

            pageResult.setData(govList);
            pageResult.setTotalCount(pageResult.getTotalCount());
        }

        return pageResult;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PlainResult<GovPlainJDO> findGovPlainById(Integer govId) {
        PlainResult<GovPlainJDO> result = new PlainResult<GovPlainJDO>();
        GovPlainJDO govPlainJDO = governmentDao.findGovPlainById(govId);
        if (govPlainJDO == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到指定机构！");
            return result;
        }

        /**
         * 取出机构中客户经理id，查询出客户经理名称
         */
        Integer customerManagerId = govPlainJDO.getGovCustomerManager();
        if (null != customerManagerId) {
            PlainResult<EmployeeDO> plainResult = employeeService.findById(customerManagerId);
            if (plainResult.isSuccess()) {
                String customerManagerName = plainResult.getData().getEmpName();
                govPlainJDO.setGovCustomerManagerName(customerManagerName);
            }
        }

        /**
         * 根据机构id，查询出为此机构做担保的机构名字符串，以“，”分隔，如”机构1，机构2，机构3”
         */
        Map<Integer, String> govGuarNames = govGuarService.findGovGuarByGovId(govId);
        if (null != govGuarNames && govGuarNames.size() != 0) {
            StringBuilder govGuarId = new StringBuilder();
            StringBuilder govGuarName = new StringBuilder();
            for (Map.Entry<Integer, String> entry : govGuarNames.entrySet()) {
                govGuarId.append(entry.getKey()).append(",");
                govGuarName.append(entry.getValue()).append(",");
            }
            //            govGuarId.deleteCharAt(govGuarId.length() - 1);
            //            govGuarName.deleteCharAt(govGuarName.length() - 1);
            govPlainJDO.setGovGuarId(govGuarId.toString());
            govPlainJDO.setGovGuarName(govGuarName.toString());
        }

        result.setData(govPlainJDO);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PlainResult<GovPlainJDO> findGovPlainByEmpId(Integer empId) {
        PlainResult<GovPlainJDO> result = new PlainResult<GovPlainJDO>();
        PlainResult<EmployeeDO> plainResult = employeeService.findById(empId);
        if (!plainResult.isSuccess()) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "查询员工信息失败！");
            return result;
        }
        EmployeeDO employeeDO = plainResult.getData();
        Integer govId = employeeDO.getEmpOrgId();
        result = findGovPlainById(govId);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PlainResult<GovPlainJDO> findGovPlainWithAreaById(Integer govId) {
        PlainResult<GovPlainJDO> result = new PlainResult<GovPlainJDO>();
        GovPlainJDO govPlainJDO = governmentDao.findGovPlainWithAreaById(govId);
        if (govPlainJDO == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到指定机构！");
            return result;
        }

        /**
         * 取出机构中客户经理id，查询出客户经理名称
         */
        Integer customerManagerId = govPlainJDO.getGovCustomerManager();
        if (null != customerManagerId) {
            PlainResult<EmployeeDO> plainResult = employeeService.findById(customerManagerId);
            if (plainResult.isSuccess()) {
                String customerManagerName = plainResult.getData().getEmpName();
                govPlainJDO.setGovCustomerManagerName(customerManagerName);
            }
        }

        /**
         * 根据机构id，查询出为此机构做担保的机构名字符串，以“，”分隔，如”机构1，机构2，机构3”
         */
        Map<Integer, String> govGuarNames = govGuarService.findGovGuarByGovId(govId);
        if (null != govGuarNames && govGuarNames.size() != 0) {
            StringBuilder govGuarId = new StringBuilder();
            StringBuilder govGuarName = new StringBuilder();
            for (Map.Entry<Integer, String> entry : govGuarNames.entrySet()) {
                govGuarId.append(entry.getKey()).append(",");
                govGuarName.append(entry.getValue()).append(",");
            }
            //            govGuarId.deleteCharAt(govGuarId.length() - 1);
            //            govGuarName.deleteCharAt(govGuarName.length() - 1);
            govPlainJDO.setGovGuarId(govGuarId.toString());
            govPlainJDO.setGovGuarName(govGuarName.toString());
        }

        result.setData(govPlainJDO);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageResult<GovPlainJDO> queryListWithReviewInfo(GovernmentDO gov, String govUsername,
                                                           GovernmentUpdateHistoryDO historyDO,
                                                           PageCondition pageCondition) {
        PageResult<GovPlainJDO> result = new PageResult<GovPlainJDO>(pageCondition);
        PageResult<GovPlainJDO> pageResult = queryListWithPlainInfo(gov, govUsername, pageCondition);
        if (!pageResult.isSuccess()) {
            pageResult.setErrorMessage(CommonResultCode.BIZ_ERROR, "查询列表失败！");
            return pageResult;
        }
        List<GovPlainJDO> govPlainDOs = fillReviewInfoForGovList(pageResult.getData());

        result.setData(govPlainDOs);
        return result;
    }

    @Override
    public PageResult<GovPlainJDO> queryListWithReviewInfo(GovReviewSearchDO searchDO, PageCondition pageCondition) {
        PageResult<GovPlainJDO> result = new PageResult<GovPlainJDO>(pageCondition);
        PageResult<GovPlainJDO> pageResult = queryListWithPlainInfo(searchDO, pageCondition);
        if (!pageResult.isSuccess()) {
            pageResult.setErrorMessage(CommonResultCode.BIZ_ERROR, "查询列表失败！");
            return pageResult;
        }
        //        List<GovPlainJDO> govPlainDOs = fillReviewInfoForGovList(pageResult.getData());

        result.setData(pageResult.getData());
        result.setTotalCount(pageResult.getTotalCount());
        return result;
    }

    /**
     * 将机构最新修改时间和审核信息取出
     *
     * @param govPlainDOs 机构列表
     */
    private List<GovPlainJDO> fillReviewInfoForGovList(List<GovPlainJDO> govPlainDOs) {
        List<GovPlainJDO> result = new ArrayList<GovPlainJDO>();
        List<Integer> govIds = new ArrayList<Integer>();
        if (govPlainDOs != null && govPlainDOs.size() > 0) {
            for (GovPlainJDO g : govPlainDOs) {
                govIds.add(g.getGovId());
            }
            List<GovernmentUpdateHistoryDO> updateHistoryDOs = updateHistoryService.findLastUpdateHistoryList(govIds)
                    .getData();
            for (GovPlainJDO g : govPlainDOs) {
                for (GovernmentUpdateHistoryDO h : updateHistoryDOs) {
                    if (g.getGovId().equals(h.getGuhGovid())) {
                        g.setGovModifyDate(new DateTime(h.getGuhUpdateDate()).toString(DateUtil.DEFAULT_DAY_STYLE));
                        g.setGovState(h.getGuhAuditState());
                        result.add(g);
                        break;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 将机构的地区字段编码转成实际地区字符串
     *
     * @param areaMap areaMap的形式为{"1210", "河北省-石家庄"}
     * @param govList govList中每个机构的area字段原为1210，从map中取出对应的地区字符串
     */
    private void decodeAreaInfo(Map<String, String> areaMap, List<GovPlainJDO> govList) {
        for (int i = 0, j = govList.size(); i < j; ++i) {
            if (govList.get(i).getGovArea() != null) {
                String areaInfo = areaMap.get(govList.get(i).getGovArea());
                govList.get(i).setGovArea(areaInfo);
            }
        }
    }

    /**
     * 将机构的客户经理字段的id转成名字字符串
     *
     * @param nameMap 名字
     * @param govList 每个机构的客户经理字段原为员工id
     */
    private void decodeEmpNameInfo(Map<Integer, String> nameMap, List<GovPlainJDO> govList) {
        for (int i = 0, j = govList.size(); i < j; ++i) {
            if (govList.get(i).getGovArea() != null) {
                String name = nameMap.get(govList.get(i).getGovCustomerManager());
                govList.get(i).setGovCustomerManagerName(name);
            }
        }
    }

    /**
     * 设置机构的担保机构信息
     *
     * @param nameMap {govId, 担保机构名称组成的StringBuilder}
     * @param govList 需要设置的机构列表
     */
    private void decodeGovGuarNameInfo(Map<Integer, StringBuilder> nameMap, List<GovPlainJDO> govList) {
        for (int i = 0, j = govList.size(); i < j; ++i) {
            String name = null;
            if (nameMap.get(govList.get(i).getGovId()) != null) {
                name = nameMap.get(govList.get(i).getGovId()).toString();
            }
            govList.get(i).setGovGuarName(name);
        }
    }

    private List<String> getAreaCodeList(List<GovPlainJDO> list) {
        List<String> result = new ArrayList<String>();
        Set<String> tmp = new HashSet<String>();
        for (GovPlainJDO g : list) {
            if (null != g.getGovArea()) {
                tmp.add(g.getGovArea());
            }
        }
        result.addAll(tmp);
        return result;
    }

    private List<Integer> getCustomerManagerIdList(List<GovPlainJDO> list) {
        List<Integer> result = new ArrayList<Integer>();
        Set<Integer> tmp = new HashSet<Integer>();
        if (null == list || list.size() == 0) {
            return result;
        }
        for (GovPlainJDO g : list) {
            if (null != g.getGovCustomerManager()) {
                tmp.add(g.getGovCustomerManager());
            }
        }
        result.addAll(tmp);
        return result;
    }

    private List<Integer> getGovIdList(List<GovPlainJDO> list) {
        List<Integer> result = new ArrayList<Integer>();
        for (GovPlainJDO g : list) {
            if (null != g.getGovId()) {
                result.add(g.getGovId());
            }
        }
        return result;
    }

    @Override
    public ListResult<GovernmentDO> findByList(List<Integer> list) {
        ListResult<GovernmentDO> result = new ListResult<GovernmentDO>();
        if (list == null) {
            throw new BusinessException("参数不能为null");
        }
        List<GovernmentDO> govList = governmentDao.findByList(list);
        result.setData(govList);
        return result;
    }

    @Override
    public BaseResult modifyByMap(Map<String, Object> map) {
        int returnVal = governmentDao.updateByMap(map);
        BaseResult result = new BaseResult();
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "更新失败！");
        }
        return result;
    }

    @Override
    public PlainResult<GovernmentDO> findByGovUpdateHistoryId(int guhId) {
        PlainResult<GovernmentDO> result = new PlainResult<GovernmentDO>();
        GovernmentDO governmentDO = governmentDao.findByGovUpdateHistoryId(guhId);
        if (null == governmentDO) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "查询失败！");
            return result;
        }
        result.setData(governmentDO);
        return result;
    }

    @Override
    public BaseResult updateGovAfterReview(int guhId, ReviewState reviewResult) {
        BaseResult result = new BaseResult();
        PlainResult<GovernmentUpdateHistoryDO> plainResult = updateHistoryService.findById(guhId);
        if (!plainResult.isSuccess()) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, plainResult.getMessage());
            return result;
        }
        GovernmentUpdateHistoryDO historyDO = plainResult.getData();
        int govId = historyDO.getGuhGovid();

        //将审核结果保存到机构修改记录中，即abc_government_history中修改记录审核状态
        BaseResult updateStateResult = updateHistoryService.updateGovHistoryState(guhId, reviewResult);
        if (!updateStateResult.isSuccess()) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, updateStateResult.getMessage());
            return result;
        }

        //如果审核通过，需要将更新机构信息
        //即根据abc_gov_update_detail表中记录的待修改的信息对abc_government表中机构信息进行更新
        if (reviewResult == ReviewState.PASS_REVIEW) {
            BaseResult updateResult = updateGovInfoIfPassReview(guhId, govId);
            if (!updateResult.isSuccess()) {
                result.setErrorMessage(CommonResultCode.BIZ_ERROR, "修改机构信息失败！");
                return result;
            }
        }

        result.setMessage("修改机构信息成功！");
        return result;
    }

    /**
     * 审核通过以后，更新机构信息
     *
     * @param guhId 机构跟新记录表ID
     * @param govId 机构ID
     * @return int
     */
    private BaseResult updateGovInfoIfPassReview(int guhId, int govId) {
        BaseResult result;
        GovernmentUpdateDetailDO detailDO = new GovernmentUpdateDetailDO();
        detailDO.setGuhUpdateHistoryId(guhId);
        ListResult<GovernmentUpdateDetailDO> listResult = detailService.findListByGovUpdateHistoryId(guhId);
        if (!listResult.isSuccess()) {
            throw new BusinessException(listResult.getMessage());
        }
        List<GovernmentUpdateDetailDO> list = listResult.getData();
        Map<String, Object> map = new HashMap<String, Object>();
        String guarIds = null;
        for (GovernmentUpdateDetailDO aDo : list) {
            if (aDo.getGuhField().equals("govGuarId")) {//担保机构单独拿出来处理
                guarIds = aDo.getGuhFiledNew();
            } else {
                map.put(aDo.getGuhField(), aDo.getGuhFiledNew());
            }
        }
        map.put("govId", govId);
        result = modifyByMap(map);
        if (!result.isSuccess()) {
            throw new BusinessException("修改机构信息出错!");
        }

        if (guarIds != null) {//担保机构不为空，更新担保关系
            List<Integer> ids = getGuarIdList(guarIds);
            List<GovernmentDO> guarGovDOs = new ArrayList<GovernmentDO>();//担保公司列表
            if (!ids.isEmpty()) {
                guarGovDOs = governmentDao.findByList(ids);
            }

            removeGuaranteeRelationShip(govId);

            GovernmentDO governmentDO = governmentDao.findById(govId);
            if (governmentDO == null) {
                throw new BusinessException("查询机构信息失败");
            }
            List<GovGuarDO> govGuarDOs = distributeLoanAmount(govId, governmentDO.getGovMaxLoanAmount(), guarGovDOs);//分配每个担保机构的担保额度
            //向机构信息与担保机构关联表abc_gov_guar中批量插入每条记录
            BaseResult govGuarResult = govGuarService.createByDOList(govGuarDOs);
            if (!govGuarResult.isSuccess()) {
                throw new BusinessException(govGuarResult.getMessage());
            }
            //更新各担保公司的可用担保额度(无批量更新接口，循环更新)
            if (!guarGovDOs.isEmpty()) {
                for (GovernmentDO gdo : guarGovDOs) {
                    governmentDao.updateByPrimaryKeySelective(gdo);
                }
            }
        }

        //        result = govGuarService.removeAllGuarByGovId(govId);
        //        if (!result.isSuccess()) {
        //            throw new BusinessException("修改失败！");
        //        }
        //        String guarIds = (String) map.get("govGuarId");
        //        List<Integer> ids = getGuarIdList(guarIds);
        //        if (ids != null && ids.size() != 0) {
        //            result = govGuarService.insertGovGuarByList(ids, govId);
        //            if (!result.isSuccess()) {
        //                throw new BusinessException(result.getMessage());
        //            }
        //        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public ListResult<History> findRecentListByGovId(Integer govId) {
        ListResult<History> result = new ListResult<History>();
        PlainResult<GovernmentUpdateHistoryDO> historyResult = updateHistoryService.findLastUpdateHistory(govId);
        GovernmentUpdateHistoryDO historyDO = historyResult.getData();
        if (historyDO == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到该机构最新的修改记录！");
            return result;
        }
        ListResult<GovernmentUpdateDetailDO> detailResult = detailService.findListByGovUpdateHistoryId(historyDO
                .getGuhId());
        if (!detailResult.isSuccess()) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到该机构最新的修改字段详细信息！");
            return result;
        }
        List<History> histories = HistoryConverter.convertToHistoryList(detailResult.getData());
        result.setData(histories);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public ListResult<History> findListByHistoryId(Integer historyId) {
        ListResult<History> result = new ListResult<History>();
        ListResult<GovernmentUpdateDetailDO> detailResult = detailService.findListByGovUpdateHistoryId(historyId);
        if (!detailResult.isSuccess()) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到该机构最新的修改字段详细信息！");
            return result;
        }
        List<History> histories = HistoryConverter.convertToHistoryList(detailResult.getData());
        result.setData(histories);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageResult<GovUpdateHistory> queryReviewOpLogByEmpId(Integer empId, PageCondition pageCondition) {
        PageResult<GovUpdateHistory> result = new PageResult<GovUpdateHistory>(pageCondition);
        //查询员工信息
        EmployeeDO employeeDO = employeeService.findById(empId).getData();
        if (employeeDO == null) {
            return result;
        }
        //拿到此员工所属的机构id
        Integer govId = employeeDO.getEmpOrgId();
        if (govId == null) {
            return result;
        }

        GovernmentUpdateHistoryDO historyDO = new GovernmentUpdateHistoryDO();
        historyDO.setGuhGovid(govId);
        //查询所属机构所有的修改记录
        List<GovernmentUpdateHistoryDO> historyDOs = updateHistoryService.queryList(historyDO, pageCondition).getData();
        if (historyDOs == null || historyDOs.size() == 0) {
            return result;
        }
        //要返回的维护记录列表
        List<GovUpdateHistory> histories = getGovUpdateHistoryList(historyDOs);
        result.setData(histories);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageResult<GovUpdateHistory> queryReviewOpLogByGovId(Integer govId, PageCondition pageCondition) {
        PageResult<GovUpdateHistory> result = new PageResult<GovUpdateHistory>(pageCondition);
        GovernmentUpdateHistoryDO historyDO = new GovernmentUpdateHistoryDO();
        historyDO.setGuhGovid(govId);
        //查询所属机构所有的修改记录
        List<GovernmentUpdateHistoryDO> historyDOs = updateHistoryService.queryList(historyDO, pageCondition).getData();
        if (historyDOs == null || historyDOs.size() == 0) {
            return result;
        }

        //要返回的维护记录列表
        List<GovUpdateHistory> histories = getGovUpdateHistoryList(historyDOs);
        result.setData(histories);
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    private List<GovUpdateHistory> getGovUpdateHistoryList(List<GovernmentUpdateHistoryDO> historyDOs) {
        //从修改记录中拿到修改人的id
        List<Integer> empIds = new ArrayList<Integer>();
        //从修改记录中拿到修改记录的主键
        List<Integer> reviewApplyIds = new ArrayList<Integer>();
        for (GovernmentUpdateHistoryDO his : historyDOs) {
            empIds.add(his.getGuhUpdateEmp());
            reviewApplyIds.add(his.getGuhId());
        }
        //查询对应每次记录的审核信息
        MapResult<Integer, List<ReviewOp>> mapResult = reviewOpLogService.queryMultiReviewOpHistory(
                ReviewType.GOVERNMENT_INFO_MODIFY_REVIEW, reviewApplyIds);
        getReviewEmpIds(mapResult.getData(), empIds);
        //查询对应的员工名称
        Map<Integer, String> empNames = employeeService.findEmpNamesByIds(empIds);

        //要返回的维护记录列表
        List<GovUpdateHistory> histories = new ArrayList<GovUpdateHistory>();
        for (int i = 0, j = historyDOs.size(); i < j; ++i) {
            GovUpdateHistory his = new GovUpdateHistory();
            GovernmentUpdateHistoryDO hdo = historyDOs.get(i);

            his.setGovId(hdo.getGuhGovid());
            his.setUpdateHistoryId(hdo.getGuhId());
            his.setReviewState(ReviewState.valueOf(hdo.getGuhAuditState()));//审核状态

            his.setUpdateDate(hdo.getGuhUpdateDate());//修改时间
            his.setUpdateEmpId(hdo.getGuhUpdateEmp());//修改人id
            his.setUpdateEmpName(empNames.get(hdo.getGuhUpdateEmp()));//修改人用户名

            //每个修改记录对应的审核信息，这里机构修改审核只需要一步，list长度最多为1，也可能过为0，因为客服修改机构信息不需要审核。
            List<ReviewOp> reviewOps = mapResult.getData().get(hdo.getGuhId());
            if (reviewOps != null && reviewOps.size() != 0) {
                ReviewOp op = reviewOps.get(0);
                his.setReviewEmpId(op.getEmployee().getEmpId());//审核人id
                his.setReviewEmpName(empNames.get(op.getEmployee().getEmpId()));//审核人用户名
                his.setReviewDate(op.getDate());//审核日期
                his.setReviewMessage(op.getMessage());//审核意见
            }

            histories.add(his);
        }

        return histories;
    }

    @Override
    public PageResult<GovUpdateHistory> searchReviewOpLog(GovernmentUpdateHistoryDO historyDO, Date updateStartDate,
                                                          Date updateEndDate, String updateEmpName,
                                                          PageCondition pageCondition) {
        PageResult<GovUpdateHistory> result = new PageResult<GovUpdateHistory>(pageCondition);
        //查询所属机构所有的修改记录
        List<GovernmentUpdateHistoryDO> historyDOs = updateHistoryService.searchList(historyDO, updateStartDate,
                updateEndDate, updateEmpName, pageCondition).getData();
        if (historyDOs == null || historyDOs.size() == 0) {
            return result;
        }
        //要返回的维护记录列表
        List<GovUpdateHistory> histories = getGovUpdateHistoryList(historyDOs);
        result.setData(histories);
        return result;
    }

    /**
     * 从审核信息列表中取出审核人id列表
     *
     * @param map 审核信息
     * @param empIds 审核人id集合
     */
    private void getReviewEmpIds(Map<Integer, List<ReviewOp>> map, List<Integer> empIds) {
        for (Map.Entry<Integer, List<ReviewOp>> entry : map.entrySet()) {
            List<ReviewOp> reviewOps = entry.getValue();
            if (reviewOps != null && reviewOps.size() != 0) {
                empIds.add(reviewOps.get(0).getEmployee().getEmpId());
            }
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult computeSettGuarAmount(Integer govId, BigDecimal candidateValue) {
        BaseResult result = new BaseResult();
        if (candidateValue.compareTo(BigDecimal.ZERO) < 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "担保额度参数不能为负");
            return result;
        } else if (candidateValue.compareTo(BigDecimal.ZERO) == 0) {
            return result;
        }

        GovernmentDO governmentDO = governmentDao.findByIdWithLock(govId);
        if (governmentDO == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到指定机构");
            return result;
        }
        BigDecimal maxGuarAmount = governmentDO.getGovMaxGuarAmount();//最大担保额度
        BigDecimal settGuarAmount = governmentDO.getGovSettGuarAmount();//可用担保额度
        BigDecimal tmpCandidateValue = candidateValue.add(settGuarAmount);
        //判断可用担保额度加上传过来的值是否大于最大担保额度，如果大于，可能是机构最大担保额度做了修改，这时修改参数，将可用担保额度设置为最大担保额度的值
        if (tmpCandidateValue.compareTo(maxGuarAmount) > 0) {
            tmpCandidateValue = maxGuarAmount.subtract(settGuarAmount);
        } else {
            tmpCandidateValue = candidateValue;
        }
        int val = governmentDao.computeSettGuarAmount(govId, tmpCandidateValue);
        if (val <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "更新可担保额度出错");
        }
        return result;
    }

    @Override
    public BaseResult updateGovernmentOutSeq(Integer govId, String outSeq) {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public int updateGovName(int id, String name) {
		GovernmentDO governmentDO = new GovernmentDO();
        governmentDO.setGovId(id);
        governmentDO.setGovName(name);
        return governmentDao.updateByPrimaryKeySelective(governmentDO);
	}
}
