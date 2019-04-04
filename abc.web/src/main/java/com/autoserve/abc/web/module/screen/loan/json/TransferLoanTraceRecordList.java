/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.loan.json;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.TransferLoanTraceRecord;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.TransferLoanTraceRecordVOConverter;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.loan.TransferLoanTraceRecordVO;

/**
 * 项目的跟踪列表
 * 
 * @author segen189 2014年12月25日 上午10:41:11
 */
public class TransferLoanTraceRecordList {

    @Resource
    private TransferLoanService transferLoanService;
    @Resource
    private EmployeeService     employeeService;
    @Resource
    private UserService         userService;

    public JsonPageVO<TransferLoanTraceRecordVO> execute(Context context, ParameterParser params,
                                                         @Param("bidId") int bidId, @Param("rows") int rows,
                                                         @Param("page") int page) {

        PageResult<TransferLoanTraceRecord> traceResult = transferLoanService.queryTraceRecordList(bidId,
                new PageCondition(page, rows));
        List<TransferLoanTraceRecordVO> list = new ArrayList<TransferLoanTraceRecordVO>();
        for (TransferLoanTraceRecord transferLoanTraceRecord : traceResult.getData()) {
            PlainResult<EmployeeDO> empResult = null;
            PlainResult<UserDO> useResult = null;
            if (transferLoanTraceRecord != null) {
                empResult = this.employeeService.findById(transferLoanTraceRecord.getCreator());
            }
            if (empResult.getData() == null) {
                useResult = userService.findById(transferLoanTraceRecord.getCreator());
            }
            TransferLoanTraceRecordVO vo = TransferLoanTraceRecordVOConverter
                    .toTransferLoanTraceRecordConvert(transferLoanTraceRecord);
            if (empResult.getData() != null) {
                vo.setCreatorName(empResult.getData().getEmpRealName());
            } else if (useResult.getData() != null) {
                vo.setCreatorName(useResult.getData().getUserRealName());
            }
            list.add(vo);
        }
        JsonPageVO<TransferLoanTraceRecordVO> result = new JsonPageVO<TransferLoanTraceRecordVO>();
        result.setMessage(traceResult.getMessage());
        result.setRows(list);
        result.setSuccess(traceResult.isSuccess());
        result.setTotal(traceResult.getTotalCount());
        return result;

    }
}
