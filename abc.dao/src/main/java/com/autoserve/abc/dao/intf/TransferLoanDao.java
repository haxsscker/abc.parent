package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AccordTenderDO;
import com.autoserve.abc.dao.dataobject.TransferLoanDO;
import com.autoserve.abc.dao.dataobject.TransferLoanJDO;
import com.autoserve.abc.dao.dataobject.search.TransferLoanSearchDO;

public interface TransferLoanDao extends BaseBidDao<TransferLoanDO, Integer> {

    /**
     * 查询转让标，加行级独占锁
     *
     * @param transferLoanId 转让标id
     * @return TransferLoanDO 转让标
     */
    TransferLoanDO findByTransferLoanIdWithLock(int transferLoanId);
    
    TransferLoanDO findByTransferLoanId(int transferLoanId);
    
    TransferLoanDO findByInvestId(int investId);

    /**
     * 更新转让标状态
     *
     * @param transferLoanId 转让标id，必选
     * @param oldState 旧状态，必选
     * @param newState 新状态，必选
     * @return int，更新条数
     */
    int updateState(@Param("transferLoanId") int transferLoanId, @Param("oldState") int oldState,
                    @Param("newState") int newState);

    /**
     * 统计按条件查询的分页结果总数
     *
     * @param pojo 查询条件，可选
     * @return 纪录总数
     */
    int countListBySearchParam(@Param("pojo") TransferLoanSearchDO pojo);

    /**
     * 按条件查询分页结果
     *
     * @param pojo 查询条件，可选
     * @param pageCondition 分页条件，可选
     * @return List<TransferLoanJDO>
     */
    List<TransferLoanJDO> findListBySearchParam(@Param("pojo") TransferLoanSearchDO pojo,
                                                @Param("pageCondition") PageCondition pageCondition);

    /**
     * 查询出需要流标的数据
     * 
     * @return
     */
    List<AccordTenderDO> findAccordTenderList(List<Integer> list);

    /**
     * 更新合同的物理路径
     * @param id
     * @param contractPath
     * @return
     */
    int updateContractPath(@Param("id") Integer id, @Param("contractPath") String contractPath);
    
    /**
     * 查询优质推荐(债券)
     * add by xiatt 20160630
     * @return
     */
    List<TransferLoanJDO> findTrOptimization(@Param("topN") Integer topN);
    /**
     * 更新合同存证编号
     * @param id
     * @param c_Evid
     * @return
     */
	int updateContractDepositNumber(@Param("id") Integer id, @Param("contractDepositNumber")String c_Evid);
}
