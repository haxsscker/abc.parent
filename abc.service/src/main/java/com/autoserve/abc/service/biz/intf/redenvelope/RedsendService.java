package com.autoserve.abc.service.biz.intf.redenvelope;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RedsendDO;
import com.autoserve.abc.dao.dataobject.RedsendJDO;
import com.autoserve.abc.dao.dataobject.search.RedSearchDO;
import com.autoserve.abc.service.biz.entity.Redsend;
import com.autoserve.abc.service.biz.entity.RedsendJ;
import com.autoserve.abc.service.biz.enums.RsState;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 红包表service层接口
 * 
 * @author lipeng 2014年12月27日 下午2:35:41
 */
public interface RedsendService {
    /**
     * 添加红包发放信息
     * 
     * @param redsendJ
     * @return
     */
    BaseResult createRedsend(Redsend redsend);

    /**
     * 删除红包发放信息
     * 
     * @param redsendJ
     * @return
     */
    BaseResult removeRedsend(Redsend redsend);

    /**
     * 修改红包发放信息
     * 
     * @param redsendJ
     * @return
     */
    BaseResult modifyRedsend(Redsend redsend);

    /**
     * 查询单条红包发放信息
     * 
     * @param id
     * @return
     */
    PlainResult<Redsend> quaryById(int id);

    /**
     * 红包发放信息多条查询
     * 
     * @param pojo
     * @param pageCondition
     * @return
     */
    public PageResult<RedsendDO> queryList(Redsend pojo, PageCondition pageCondition);

    /**
     * 红包发放信息多条查询JDO
     * 
     * @param pojo
     * @param pageCondition
     * @return
     */
    public PageResult<RedsendJ> queryListJ(RedSearchDO redSearchDO, PageCondition pageCondition);

    /**
     * 红包发放信息多条查询JDO
     * 
     * @param pojo
     * @param pageCondition
     * @return
     */
    public PageResult<RedsendJDO> queryListJDO(RedSearchDO redSearchDO, PageCondition pageCondition);

    /**
     * 查询每种类型红包记录条数，或者所有类型红包记录条数
     * 
     * @author fangrui
     * @param redsendJDO
     * @return int
     */
    public int countListByParam(RedSearchDO redSearchDO);

    /**
     * 可用于查询同一红包标题的列表
     * 
     * @param pojo
     * @param pageCondition
     * @return PageResult<RedsendJDO>
     */
    public PageResult<RedsendJDO> queryInviteList(RedSearchDO redSearchDO, PageCondition pageCondition);

    /**
     * 可用于查询同一红包标题的红包数
     * 
     * @param pojo
     * @param pageCondition
     * @return int
     */
    public int countInviteList(RedSearchDO redSearchDO);

    /**
     * 根据sendIdList列表查询红包发送记录
     * 
     * @param sendIdList id列表，必选
     * @return ListResult<RedsendDO>
     */
    public ListResult<RedsendDO> queryByIdList(List<Integer> sendIdList);

    /**
     * 批量修改id列表中的红包的状态
     * 
     * @param sendIdList id列表，必选
     * @param oldState 旧的状态，必选
     * @param newState 新的状态，必选
     * @return BaseResult
     */
    public BaseResult batchModifyState(List<Integer> sendIdList, RsState oldState, RsState newState);

    /**
     * 红包过期失效处理
     * 
     * @param date 传入当前部署服务器时间，防止数据库和服务部署服务器时间不一产生的问题
     * @return
     */
    public int redOverdue(Date date);

    /**
     * 统计未使用红包数
     * 
     * @param userId
     * @return
     */
    int countUnusedRed(Integer userId);
    
    /**
     * 查询用户未使用红包金额
     * @param userId
     * @return
     */
    BigDecimal countUnusedRedAmount(Integer userId);

    /**
     * 查询红包的数目
     * 
     * @param redSearchDO
     * @return
     */
    public int queryRedCount(RedSearchDO redSearchDO);
    
    /**
     * 查询红包的有效金额
     * 
     * @param redSearchDO
     * @return
     */
    public int queryRedAmount(RedSearchDO redSearchDO);

}
