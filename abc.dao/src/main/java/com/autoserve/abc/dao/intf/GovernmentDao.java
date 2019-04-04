package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.GovPlainJDO;
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.dao.dataobject.search.GovReviewSearchDO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface GovernmentDao extends BaseDao<GovernmentDO, Integer> {

    /**
     * 修改机构的某些字段（以主键为准）
     *
     * @param governmentDO 机构信息，需包含主键
     * @return int
     */
    public int updateByPrimaryKeySelective(GovernmentDO governmentDO);

    /**
     * 修改机构的某些字段（以主键为准）
     *
     * @param govPlainJDO 机构信息，需包含主键
     * @return int
     */
    public int updateByPrimaryKeySelective(GovPlainJDO govPlainJDO);

    public int computeSettGuarAmount(@Param("govId") Integer govId, @Param("val") BigDecimal candidateValue);

    /**
     * 根据参数获取记录条数
     *
     * @param govPlainJDO
     * @return
     */
    public int countListByParam(@Param("gov") GovPlainJDO govPlainJDO);

    /**
     * 按条件查询分页结果
     *
     * @param govPlainJDO   查询条件，为空的话传new GovPlainDO()
     * @param pageCondition 分页和排序条件，可选
     * @return List<GovernmentDO>
     */
    List<GovPlainJDO> findListByParam(@Param("gov") GovPlainJDO govPlainJDO,
                                      @Param("pageCondition") PageCondition pageCondition);

    public GovernmentDO findByIdWithLock(int id);

    /**
     * 根据参数获取记录条数
     *
     * @param govPlainJDO         查询条件，为空的话传new GovPlainDO()
     * @param maxLoanMoneyStart   最大借款额度范围低值，可选
     * @param maxLoanMoneyEnd     最大借款额度范围高值，可选
     * @param customerManagerName 客户经理名称
     * @param superAreaCode       父级地区代码
     * @return
     */
    public int countListByMap(@Param("gov") GovPlainJDO govPlainJDO,
                              @Param("maxLoanMoneyStart") BigDecimal maxLoanMoneyStart,
                              @Param("maxLoanMoneyEnd") BigDecimal maxLoanMoneyEnd,
                              @Param("customerManagerName") String customerManagerName,
                              @Param("superAreaCode") String superAreaCode);

    /**
     * 按条件查询分页结果
     *
     * @param govPlainJDO         查询条件，为空的话传new GovPlainDO()
     * @param maxLoanMoneyStart   最大借款额度范围低值，可选
     * @param maxLoanMoneyEnd     最大借款额度范围高值，可选
     * @param customerManagerName 客户经理名称
     * @param superAreaCode       父级地区代码
     * @return
     */
    List<GovPlainJDO> findListByMap(@Param("gov") GovPlainJDO govPlainJDO,
                                    @Param("maxLoanMoneyStart") BigDecimal maxLoanMoneyStart,
                                    @Param("maxLoanMoneyEnd") BigDecimal maxLoanMoneyEnd,
                                    @Param("customerManagerName") String customerManagerName,
                                    @Param("superAreaCode") String superAreaCode,
                                    @Param("pageCondition") PageCondition pageCondition);

    public int countForSearch(@Param("searchDO") GovReviewSearchDO searchDO);

    public List<GovPlainJDO> search(@Param("searchDO") GovReviewSearchDO searchDO, @Param("pageCondition") PageCondition pageCondition);

    /**
     * 查找机构信息
     *
     * @param guhId 机构更新表主键
     * @return GovernmentDO
     */
    public GovernmentDO findByGovUpdateHistoryId(int guhId);

    /**
     * 更新机构部分信息
     *
     * @param map map中包含待更新的信息
     * @return int
     */
    public int updateByMap(@Param("map") Map<String, Object> map);

    /**
     * 批量查询
     *
     * @param list 机构ID
     * @return List<GovernmentDO>
     */
    public List<GovernmentDO> findByList(List<Integer> list);

    public GovPlainJDO findGovPlainWithAreaById(int govId);

    public GovPlainJDO findGovPlainById(int govId);

}
