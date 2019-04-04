package com.autoserve.abc.service.biz.impl.redenvelope;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RedsendDO;
import com.autoserve.abc.dao.dataobject.RedsendJDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.search.RedSearchDO;
import com.autoserve.abc.dao.intf.RedsendDao;
import com.autoserve.abc.service.biz.convert.RedsendConverter;
import com.autoserve.abc.service.biz.convert.RedsendJConvert;
import com.autoserve.abc.service.biz.entity.Redsend;
import com.autoserve.abc.service.biz.entity.RedsendJ;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.RsState;
import com.autoserve.abc.service.biz.intf.redenvelope.RedsendService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 红包发放service层实现类
 * 
 * @author lipeng 2014年12月27日 下午2:51:44
 */
@Service
public class RedsendServiceImpl implements RedsendService {

    @Resource
    private RedsendDao  redsendDao;

    @Resource
    private UserService userService;

    @Override
    public BaseResult createRedsend(Redsend redsend) {
        BaseResult result = new BaseResult();
        int returnVal = redsendDao.insert(RedsendConverter.toRedsendDO(redsend));
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "红包发放失败");
        }

        result.setMessage("发放成功!");
        return result;
    }

    @Override
    public BaseResult removeRedsend(Redsend redsend) {
        BaseResult result = new BaseResult();
        int returnVal = redsendDao.delete(redsend.getRsId());
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "红包发放信息删除失败！");
        }
        result.setMessage("红包发放信息删除成功");
        return result;

    }

    @Override
    public BaseResult modifyRedsend(Redsend redsend) {
        BaseResult result = new BaseResult();
        int returnVal = redsendDao.update(RedsendConverter.toRedsendDO(redsend));
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "红包发放信息修改失败！");
        }
        result.setMessage("红包修改信息成功");
        return result;
    }

    @Override
    public PlainResult<Redsend> quaryById(int id) {
        PlainResult<Redsend> result = new PlainResult<Redsend>();
        RedsendDO redsendDO = redsendDao.findById(id);
        if (redsendDO == null) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "未找到对应的信息");
            return result;
        }
        Redsend redsend = RedsendConverter.toRedsend(redsendDO);
        result.setData(redsend);
        return result;
    }

    @Override
    public PageResult<RedsendDO> queryList(Redsend redSend, PageCondition pageCondition) {
        RedsendDO pojo = RedsendConverter.toRedsendDO(redSend);
        PageResult<RedsendDO> result = new PageResult<RedsendDO>(pageCondition.getPage(), pageCondition.getPageSize());
        result.setData(redsendDao.findListByParam(pojo, pageCondition));
        return result;
    }

    @Override
    public PageResult<RedsendJ> queryListJ(RedSearchDO redSearchDO, PageCondition pageCondition) {
    	redOverdue(new Date());//失效红包处理
        PageResult<RedsendJ> result = new PageResult<RedsendJ>(pageCondition.getPage(), pageCondition.getPageSize());
        int totalCount = redsendDao.countListByParam(redSearchDO);
        result.setTotalCount(totalCount);
        if (totalCount > 0) {
            result.setData(RedsendJConvert.toRedsendList(redsendDao.findListByJParam(redSearchDO, pageCondition)));
        }
        return result;
    }

    @Override
    public PageResult<RedsendJDO> queryListJDO(RedSearchDO redSearchDO, PageCondition pageCondition) {
        PageResult<RedsendJDO> result = new PageResult<RedsendJDO>(pageCondition.getPage(), pageCondition.getPageSize());
        int totalCount = redsendDao.countListByParam(redSearchDO);

        result.setTotalCount(totalCount);
        List<RedsendJDO> listResult = new ArrayList<RedsendJDO>();
        if (totalCount > 0) {
            List<RedsendJDO> list = redsendDao.findListByJParam(redSearchDO, pageCondition);
            for (RedsendJDO rsJDO : list) {
                rsJDO.setRedUseScope(formatString(rsJDO.getRedUseScope(), "|"));
                listResult.add(rsJDO);
            }
            result.setData(listResult);
        }
        return result;
    }

    @Override
    public int countListByParam(RedSearchDO redSearchDO) {
        int num = redsendDao.countListByParam(redSearchDO);
        return num;
    }

    @Override
    public PageResult<RedsendJDO> queryInviteList(RedSearchDO redSearchDO, PageCondition pageCondition) {
        PageResult<RedsendJDO> result = new PageResult<RedsendJDO>(pageCondition.getPage(), pageCondition.getPageSize());
        int totalCount = redsendDao.countInviteList(redSearchDO);
        result.setTotalCount(totalCount);
        List<RedsendJDO> RedsendJDOs = new ArrayList<RedsendJDO>();
        if (totalCount > 0) {
            List<RedsendJDO> list = redsendDao.findInviteList(redSearchDO, pageCondition);
            for (RedsendJDO redsendJDO : list) {
                UserDO InviteeUser = userService.findById(redsendJDO.getInviteInviteeId()).getData();
                if (InviteeUser != null) {
                    redsendJDO.setInviteeName(InviteeUser.getUserName());
                    redsendJDO.setUserBusinessState(InviteeUser.getUserBusinessState());
                } else {
                    redsendJDO.setInviteeName(null);
                    redsendJDO.setUserBusinessState(null);
                }
                RedsendJDOs.add(redsendJDO);
            }
            result.setData(RedsendJDOs);
        }
        return result;
    }

    @Override
    public int countInviteList(RedSearchDO redSearchDO) {
        int num = redsendDao.countInviteList(redSearchDO);
        return num;
    }

    /**
     * 格式化使用范围
     * 
     * @param value
     * @param pattern
     * @return String
     */
    private String formatString(String value, String pattern) {

        String[] scopes = value.split(pattern);
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < scopes.length; i++) {
            if (i != (scopes.length - 1)) {
                str.append(LoanCategory.valueOf(Integer.parseInt(scopes[i])).getPrompt() + ", ");
            } else {
                str.append(LoanCategory.valueOf(Integer.parseInt(scopes[i])).getPrompt());
            }
        }
        return str.toString();
    }

    @Override
    public ListResult<RedsendDO> queryByIdList(List<Integer> sendidList) {
        ListResult<RedsendDO> result = new ListResult<RedsendDO>();
        if (CollectionUtils.isEmpty(sendidList)) {
            return result.setError(CommonResultCode.ILLEGAL_PARAM);
        }

        List<RedsendDO> data = redsendDao.findListByIds(sendidList);
        result.setData(data);
        return result;
    }
    

    @Override
    public BaseResult batchModifyState(List<Integer> sendIdList, RsState oldState, RsState newState) {
        BaseResult result = new BaseResult();
        if (CollectionUtils.isEmpty(sendIdList) || oldState == null || newState == null) {
            return result.setError(CommonResultCode.ILLEGAL_PARAM);
        }

        redsendDao.batchModifyState(sendIdList, oldState.getState(), newState.getState());
        return result;
    }

    /**
     * 红包过期失效处理
     */
    @Override
    public int redOverdue(Date date) {
        int result = redsendDao.redOverdue(date);
        return result;
    }

    @Override
    public int countUnusedRed(Integer userId) {
        return redsendDao.countUnusedRed(userId);
    }
    
    @Override
    public BigDecimal countUnusedRedAmount(Integer userId)
    {
    	return redsendDao.countUnusedRedAmount(userId);
    }

    @Override
    public int queryRedCount(RedSearchDO redSearchDO) {

        int result = redsendDao.findIsHaveRed(redSearchDO);
        return result;

    }

    @Override
    public int queryRedAmount(RedSearchDO redSearchDO) {

        int result = redsendDao.findRedAmount(redSearchDO);
        return result;

    }
}
