package com.autoserve.abc.service.biz.impl.fund;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.FundOrderApplyUserJDO;
import com.autoserve.abc.dao.dataobject.FundOrderDO;
import com.autoserve.abc.dao.intf.FundOrderDao;
import com.autoserve.abc.service.biz.convert.FundOrderConverter;
import com.autoserve.abc.service.biz.entity.FundOrder;
import com.autoserve.abc.service.biz.entity.FundOrderApplyUser;
import com.autoserve.abc.service.biz.intf.fund.FundOrderService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.util.DateUtil;

/**
 * 类FundOrderServiceImpl.java的实现描述：TODO 类实现描述
 *
 * @author wangyongheng 2014/12/08
 */
@Service
public class FundOrderServiceImpl implements FundOrderService {

    @Resource
    FundOrderDao fundOrderDao;

    @Override
    public BaseResult createFundOrder(FundOrder pojo) {
        BaseResult result = new BaseResult();
        int returnVal = fundOrderDao.insert(FundOrderConverter.toFundOrderDO(pojo));
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "添加合伙人预约失败！");
        }
        result.setMessage("添加成功！");
        return result;
    }

    @Override
    public BaseResult removeFundOrder(FundOrder pojo) {
        BaseResult result = new BaseResult();
        int returnVal = fundOrderDao.delete(pojo.getFoFundId());
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "删除合伙人预约失败！");
        }
        result.setMessage("删除成功！");
        return result;
    }

    @Override
    public BaseResult modifyFundOrder(FundOrder pojo) {
        BaseResult result = new BaseResult();
        int returnVal = fundOrderDao.update(FundOrderConverter.toFundOrderDO(pojo));
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "修改合伙人预约信息失败！");
        }
        result.setMessage("修改成功！");
        return result;
    }

    @Override
    public PlainResult<FundOrder> queryById(int id) {
        PlainResult<FundOrder> result = new PlainResult<FundOrder>();
        FundOrderDO orderDO = fundOrderDao.findById(id);
        if (orderDO == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到对应合伙人预约信息");
            return result;
        }
        FundOrder fundOrder = FundOrderConverter.toFundOrder(orderDO);
        result.setData(fundOrder);
        return result;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.autoserve.abc.service.biz.intf.fund.FundOrderService#queryList(com
     * .autoserve.abc.dao.dataobject.FundOrderDO,
     * com.autoserve.abc.dao.common.PageCondition)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageResult<FundOrderDO> queryList(FundOrderDO fa, PageCondition pageCondition) {
        PageResult<FundOrderDO> result = new PageResult<FundOrderDO>(pageCondition.getPage(),
                pageCondition.getPageSize());
        result.setData(fundOrderDao.findListByParam(fa, pageCondition));
        return result;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageResult<FundOrderApplyUserJDO> queryList(FundOrderApplyUserJDO fundOrderApplyUserJDO,
                                                       PageCondition pageCondition) {
        PageResult<FundOrderApplyUserJDO> result = new PageResult<FundOrderApplyUserJDO>(pageCondition.getPage(),
                pageCondition.getPageSize());
        result.setData(fundOrderDao.findListByJParam(fundOrderApplyUserJDO, pageCondition));
        return result;

    }

    /*
     * (non-Javadoc)
     * @see
     * com.autoserve.abc.service.biz.intf.fund.FundOrderService#queryByOrderId
     * (int)
     */
    @Override
    public PlainResult<FundOrderApplyUser> queryByOrderId(Integer id) {
        PlainResult<FundOrderApplyUser> result = new PlainResult<FundOrderApplyUser>();
        FundOrderApplyUserJDO fundOrderApplyUserJDO = fundOrderDao.findOrderById(id);
        if (fundOrderApplyUserJDO == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到对应合伙人预约信息");
            return result;
        }
        FundOrderApplyUser fundOrder = FundOrderConverter.toFundOrderApplyUser(fundOrderApplyUserJDO);
        result.setData(fundOrder);
        return result;
    }

    @Override
    public BaseResult passReview(int id, String reviewNote) {
        FundOrderApplyUserJDO fundOrderApplyUserJDO = new FundOrderApplyUserJDO();
        try {
            fundOrderApplyUserJDO.setFcOrderId(id);
            fundOrderApplyUserJDO.setFoOrderId(id);
            fundOrderApplyUserJDO.setFcCheckIdear(reviewNote);
            fundOrderApplyUserJDO.setFcCheckState(1);
            fundOrderApplyUserJDO.setFoOrderState(1);
            String ReviewDate = (new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_STYLE)).format(new Date());
            Date fcCheckDate = DateUtil.parseDate(ReviewDate);
            fundOrderApplyUserJDO.setFcCheckDate(fcCheckDate);
        } catch (ParseException e) {
            //log.error("error message", e);
            throw new BusinessException("时间转换异常");
        }
        BaseResult result = new BaseResult();
        int returnVal = fundOrderDao.updateByOrderd(fundOrderApplyUserJDO);
        System.out.println(fundOrderApplyUserJDO.getFcCheckIdear());
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "审核失败！");
        }
        return result;
    }

    @Override
    public BaseResult failedPassReview(int id, String reviewNote) {
        FundOrderApplyUserJDO fundOrderApplyUserJDO = new FundOrderApplyUserJDO();
        try {
            fundOrderApplyUserJDO.setFcOrderId(id);
            fundOrderApplyUserJDO.setFoOrderId(id);
            fundOrderApplyUserJDO.setFcCheckIdear(reviewNote);
            fundOrderApplyUserJDO.setFcCheckState(2);
            fundOrderApplyUserJDO.setFoOrderState(2);
            String ReviewDate = (new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_STYLE)).format(new Date());
            Date fcCheckDate = DateUtil.parseDate(ReviewDate);
            fundOrderApplyUserJDO.setFcCheckDate(fcCheckDate);
        } catch (ParseException e) {
            throw new BusinessException("时间转换异常");
        }
        BaseResult result = new BaseResult();
        int returnVal = fundOrderDao.updateByOrderd(fundOrderApplyUserJDO);
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "审核失败！");
        }
        return result;
    }

}
