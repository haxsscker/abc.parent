/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.loan;

import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 项目编号模板规则
 * 
 * @author segen189 2015年1月1日 下午10:44:09
 */
public interface NumberRuleService {

    /**
     * 查询项目编号规则
     * 
     * @return PlainResult<String>
     */
    PlainResult<String> queryNumberRule();

    /**
     * 查询项目编号规则生成项目编号
     * 
     * @param originNumber 初始编号
     * @return PlainResult<String>
     */
    PlainResult<String> createNumberByNumberRule(int originNumber);

    /**
     * 修改项目编号规则
     * 
     * @param string 待更新的项目编号
     * @return BaseResult
     */
    BaseResult modifyNumberRule(String newNumberRule);

    /**
     * 按年份自增编号
     * 
     * @param string 待更新的项目编号
     * @return BaseResult
     */
    PlainResult<String> createNumberByYear();
}
