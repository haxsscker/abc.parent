/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.cash;

import java.util.List;

import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.service.biz.entity.BankInfo;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 银行信息service
 * 
 * @author J.YL 2014年11月17日 下午4:02:26
 */
public interface BankInfoService {

	/**
	 * 创建用户银行账户
	 * 
	 * @param bankInfo
	 * @return BaseResult
	 */
	public BaseResult createBankInfo(BankInfo bankInfo);

	/**
	 * 根据用户ID&银行卡号删除银行卡 未实现
	 * 
	 * @param userID
	 * @param bankNo
	 * @return
	 */
	public BaseResult removeBankInfo(String userID, String bankNo);

	/**
	 * 未实现
	 * 
	 * @param userID
	 * @param bankNo
	 * @return
	 */
	public BaseResult modifyBankInfo(String userID, String bankNo);
	/**
	 * 只能查询前台用户
	 * @param userID
	 * @return
	 */
	@Deprecated
	public ListResult<BankInfoDO> queryListBankInfo(Integer userID);

	/**
	 * 根据ID查询银行
	 * 
	 * @param id
	 * @return
	 */
	public PlainResult<BankInfoDO> queryListBankInfoById(Integer id);

	/**
	 * 修改用户银行账户
	 * 
	 * @param bankInfo
	 * @return BaseResult
	 */
	public BaseResult modifyBankInfo(BankInfo bankInfo);

	/**
	 * 根据用户id 和银行卡号查询银行卡 -没有考虑用户类型
	 * 
	 * @param userID
	 * @param bankNo
	 * @return
	 */
	@Deprecated
	public PlainResult<BankInfoDO> queryBankInfo(String userID, String bankNo);

	/**
	 * 根据用户USERID修改 ps:没有考虑userType
	 * 
	 * @param bankInfo
	 * @return BaseResult
	 */
	public BaseResult modifyBankInfoByUserId(BankInfo bankInfo);

	/**
	 * 保存银行卡信息 根据userId,userType,bankNo查询，如果存在则更新，否则插入
	 * 
	 * @param bankInfo
	 * @return 返回刚保存的信息
	 */
	public PlainResult<BankInfoDO> saveBankInfo(BankInfo bankInfo);
	/**
	 * 查询银行卡列表
	 * @param bankInfo 必选：userId,userType 可选：cardNo, cardState
	 * @return
	 */
	List<BankInfoDO> findBankInfo(BankInfo bankInfo);

}
