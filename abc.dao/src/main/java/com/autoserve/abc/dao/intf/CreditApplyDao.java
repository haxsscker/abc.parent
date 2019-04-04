package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CreditApplyDO;
import com.autoserve.abc.dao.dataobject.CreditJDO;
import com.autoserve.abc.dao.dataobject.search.CreditSearchDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CreditApplyDao extends BaseDao<CreditApplyDO, Integer> {

    public int updateByPrimaryKeySelective(CreditApplyDO creditApplyDO);

    /**
     * 根据参数获取记录条数
     *
     * @param creditJDO
     * @return
     */
    public int countListByParam(@Param("credit") CreditJDO creditJDO);

    /**
     * 按条件查询分页结果
     *
     * @param creditJDO     查询条件，为空的话传new CreditJDO()
     * @param pageCondition 分页和排序条件，可选
     * @return List<CreditJDO>
     */
    List<CreditJDO> findListByParam(@Param("credit") CreditJDO creditJDO,
                                    @Param("pageCondition") PageCondition pageCondition);

    /**
     * 查询信用额度信息（包括用户名等信息）
     *
     * @param creditId 信用额度申请表主键
     * @return
     */
    public CreditJDO findFullCreditInfoById(int creditId);

    public int countForSearch(@Param("searchDO") CreditSearchDO searchDO);

    public List<CreditJDO> search(@Param("searchDO") CreditSearchDO searchDO, @Param("pageCondition") PageCondition pageCondition);
}