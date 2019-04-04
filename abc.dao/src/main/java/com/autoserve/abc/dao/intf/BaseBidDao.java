/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;

/**
 * 普通标、转让标、收购标的公共接口
 * 
 * @author segen189 2014年11月23日 下午11:55:46
 */
public interface BaseBidDao<T, K> extends BaseDao<T, K> {
    /**
     * 按条件查询单条记录
     * 
     * @param pojo 查询条件，必选
     * @return T
     */
    T findByParam(T pojo);

    /**
     * 按ID在列表里的所有记录
     * 
     * @param list 查询条件，必选
     * @return List<T>
     */
    List<T> findByList(@Param("list") List<Integer> list);

    /**
     * 统计按条件查询的分页结果总数
     * 
     * @param loan 查询条件，可选
     * @param investSearchJDO
     * @return int
     */
    int countListByParam(@Param("loan") T loan);

    /**
     * 按条件查询分页结果
     * 
     * @param loan 查询条件，可选
     * @param investSearchJDO
     * @param pageCondition 分页和排序条件，可选
     * @return List<T>
     */
    List<T> findListByParam(@Param("loan") T loan, @Param("pageCondition") PageCondition pageCondition);

}
