package com.autoserve.abc.web.convert;

import java.util.ArrayList;
import java.util.List;

import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.AutoTransfer;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.util.DateUtil;
import com.autoserve.abc.web.vo.auto.AutoTransferVO;

/**
 * 类AutoTransferVOConverter.java的实现描述：TODO 类实现描述
 * 
 * @author Tiuwer 2015年4月25日 下午2:32:38
 */
public class AutoTransferVOConverter {

    public static AutoTransferVO toAutoTransferVO(AutoTransfer autoTransfer) {
        AutoTransferVO vo = new AutoTransferVO();
        vo.setAtRemarks(autoTransfer.getAtRemarks());
        if (autoTransfer.getAuditState() != null)
            vo.setAuditState(autoTransfer.getAuditState().des);
        vo.setId(autoTransfer.getId());
        vo.setMoneyAmount(autoTransfer.getMoneyAmount());
        vo.setMoneyType(autoTransfer.getMoneyType());
        if (autoTransfer.getOperateDate() != null)
            vo.setOperateDate(DateUtil.formatDate(autoTransfer.getOperateDate(), DateUtil.DATE_FORMAT));
        if (autoTransfer.getOperator() != null)
            vo.setOperator(autoTransfer.getOperator().toString());
        vo.setOutSeqNo(autoTransfer.getOutSeqNo());
        vo.setPayAccotunt(autoTransfer.getPayAccotunt());
        vo.setReceibeAccotunt(autoTransfer.getReceibeAccotunt());
        if (autoTransfer.getReceibeUserId() != null)
            vo.setReceibeUser(autoTransfer.getReceibeUserId().toString());
        if (autoTransfer.getState() != null)
            vo.setState(autoTransfer.getState().des);
        return vo;
    }

	public static List<AutoTransferVO> toAutoTransferVOList(List<AutoTransfer> data, UserService userService,
			EmployeeService employeeService,AccountInfoService accountInfoService) {
		List<AutoTransferVO> list = new ArrayList<AutoTransferVO>();
		for (AutoTransfer autoTransfer : data) {
			AutoTransferVO vo = toAutoTransferVO(autoTransfer);
			if (autoTransfer.getReceibeUserId() != null) {
				UserDO user = userService.findById(
						autoTransfer.getReceibeUserId()).getData();
				vo.setReceibeUser(user.getUserRealName());
				vo.setReceiveUsername(user.getUserName());
			} else {
				vo.setReceibeUser("-");
			}
			String payAccount = autoTransfer.getPayAccotunt();
			AccountInfoDO accountInfo = accountInfoService
					.queryByAccountMark(payAccount);
			if (accountInfo != null) {
				vo.setPayRealName(accountInfo.getAccountLegalName());
				vo.setPayUserName(accountInfo.getAccountUserName());
			}
			PlainResult<UserDO> userDOResult = userService
					.findById(autoTransfer.getOperator());
			PlainResult<EmployeeDO> employeeDOResult = employeeService
					.findById(autoTransfer.getOperator());
			if (autoTransfer.getPayAccotunt().charAt(0) == 'p') {
				vo.setOperator(employeeDOResult.getData().getEmpName());
			} else {
				vo.setOperator(userDOResult.getData().getUserName());
			}
			list.add(vo);
		}
		return list;
	}
}
