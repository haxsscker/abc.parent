package com.autoserve.abc.service.biz.impl.redenvelope;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RedDO;
import com.autoserve.abc.dao.dataobject.RedReportDO;
import com.autoserve.abc.dao.dataobject.search.RedSearchDO;
import com.autoserve.abc.dao.intf.RedDao;
import com.autoserve.abc.dao.intf.RedsendDao;
import com.autoserve.abc.service.biz.convert.RedConverter;
import com.autoserve.abc.service.biz.convert.RedsendConverter;
import com.autoserve.abc.service.biz.entity.Red;
import com.autoserve.abc.service.biz.entity.Redsend;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.RedState;
import com.autoserve.abc.service.biz.intf.redenvelope.RedService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * sercice 层 红包实现类
 * 
 * @author fangrui 2014年12月27日 下午3:32:47
 */
@Service
public class RedServiceImpl implements RedService {

    @Resource
    private RedDao     redDao;

    @Resource
    private RedsendDao redsendDao;

    @Override
    public BaseResult createRed(Red red) {
        RedDO redDO = RedConverter.toRedDO(red);
        BaseResult baseResult = new BaseResult();
        int result = redDao.insert(redDO);
        if (result <= 0) {
            baseResult.setErrorMessage(CommonResultCode.BIZ_ERROR, "添加红包失败！");
        }
        return baseResult;
    }

    @Override
    public PlainResult<Integer> batchCreateRed(List<Red> listRed) {
        PlainResult<Integer> result = new PlainResult<Integer>();

        if (CollectionUtils.isEmpty(listRed)) {
            return result.setError(CommonResultCode.ILLEGAL_PARAM_BLANK, "listRed");
        }
        for(Red red:listRed){
        	red.setRedState(RedState.EFFECTIVE);
        }
        int count = redDao.batchInsert(RedConverter.convertToDOList(listRed));
        result.setData(count);
        return result;
    }

    @Override
    public PlainResult<Integer> batchSendRed(List<Redsend> sendList) {
        PlainResult<Integer> result = new PlainResult<Integer>();

        if (CollectionUtils.isEmpty(sendList)) {
            return result.setError(CommonResultCode.ILLEGAL_PARAM_BLANK, "sendList");
        }
        int count = redsendDao.batchInsert(RedsendConverter.toRedsendDOList(sendList));

        result.setData(count);
        return result;
    }

    @Override
    public BaseResult removeRed(int redId) {
        BaseResult baseResult = new BaseResult();
        int result = updateRedState(redId, RedState.DELETE);
        if (result <= 0) {
            baseResult.setErrorMessage(CommonResultCode.BIZ_ERROR, "删除红包失败！");
        }
        return baseResult;
    }

    @Override
    public BaseResult disableRed(int redId) {
        BaseResult baseResult = new BaseResult();
        int result = updateRedState(redId, RedState.FAILURE);
        if (result <= 0) {
            baseResult.setErrorMessage(CommonResultCode.BIZ_ERROR, "修改红包状态为无效状态失败！");
        }
        return baseResult;
    }

    @Override
    public BaseResult enableRed(int redId) {
        BaseResult baseResult = new BaseResult();
        int result = updateRedState(redId, RedState.EFFECTIVE);
        if (result <= 0) {
            baseResult.setErrorMessage(CommonResultCode.BIZ_ERROR, "修改红包状态为有效状态失败！");
        }
        return baseResult;
    }

    @Override
    public BaseResult modifyRedByPrimaryKey(Red red) {
        RedDO redDO = RedConverter.toRedDO(red);
        BaseResult baseResult = new BaseResult();
        int result = redDao.update(redDO);
        if (result <= 0) {
            baseResult.setErrorMessage(CommonResultCode.BIZ_ERROR, "更新红包信息失败！");
        }
        return baseResult;
    }

    @Override
    public PlainResult<RedDO> findById(int redId) {
        PlainResult<RedDO> plainResult = new PlainResult<RedDO>();
        RedDO redDO = redDao.findById(redId);
        if (redDO == null) {
            plainResult.setErrorMessage(CommonResultCode.BIZ_ERROR, "根据红包id查询红包信息失败!");
        }
        plainResult.setData(redDO);
        return plainResult;
    }

    @Override
    public PlainResult<Red> findByParam(Red param) {
        PlainResult<Red> plainResult = new PlainResult<Red>();
        List<RedDO> redDOList = redDao.findListByParam(RedConverter.toRedDO(param), null, null);
        if (CollectionUtils.isEmpty(redDOList)) {
            return plainResult.setErrorMessage(CommonResultCode.BIZ_ERROR, "查询红包信息不存在!");
        }

        plainResult.setData(RedConverter.toRed(redDOList.get(0)));
        return plainResult;
    }

    @Override
    public PlainResult<Red> findEntityById(int redId) {
        PlainResult<Red> plainResult = new PlainResult<Red>();
        RedDO redDO = redDao.findById(redId);
        if (redDO == null) {
            plainResult.setErrorMessage(CommonResultCode.BIZ_ERROR, "根据红包id查询红包信息失败!");
            return plainResult;
        }
        plainResult.setData(RedConverter.toRed(redDO));

        return plainResult;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageResult<Red> queryList(Red red, RedSearchDO redSearchDO, PageCondition pageCondition) {
        PageResult<Red> pageResult = new PageResult<Red>(pageCondition.getPage(), pageCondition.getPageSize());
        RedDO redDO = RedConverter.toRedDO(red);
        int totalCount = redDao.countListByParam(redDO, redSearchDO);
        pageResult.setTotalCount(totalCount);

        if (totalCount > 0) {
            List<RedDO> list = redDao.findListByParam(redDO, redSearchDO, pageCondition);
            for (RedDO rd : list) {
                rd.setRedUseScope(formatString(rd.getRedUseScope(), "|"));
            }
            pageResult.setData(RedConverter.convertList(list));
        }

        return pageResult;
    }

    /**
     * 更新红包状态
     * 
     * @param id 红包id
     * @param state 状态
     * @return int
     */
    private int updateRedState(int id, RedState state) {
        RedDO redDO = new RedDO();
        redDO.setRedId(id);
        redDO.setRedState(state.getState());

        return redDao.update(redDO);
    }

    /**
     * 格式化使用范围
     * 
     * @param value
     * @param pattern
     * @return String
     */
    private String formatString(String value, String pattern) {

        String[] scopes = StringUtils.split(value, pattern);
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < scopes.length; i++) {
            if (i != (scopes.length - 1)) {
                str.append(LoanCategory.valueOf(Integer.parseInt(scopes[i])).getPrompt() + ",");
            } else {
                str.append(LoanCategory.valueOf(Integer.parseInt(scopes[i])).getPrompt());
            }
        }
        return str.toString();
    }

    @Override
    public ListResult<Red> queryList(Red red, RedSearchDO redSearchDO) {
        ListResult<Red> result = new ListResult<Red>();

        RedDO redDO = RedConverter.toRedDO(red);
        List<RedDO> list = redDao.findListByParam(redDO, redSearchDO, null);
        for (RedDO rd : list) {
            rd.setRedUseScope(formatString(rd.getRedUseScope(), "|"));
        }

        result.setData(RedConverter.convertList(list));
        return result;
    }
    
    @Override
    public PageResult<RedReportDO> redReport(RedReportDO redReportDO,PageCondition pageCondition) {
        PageResult<RedReportDO> pageResult = new PageResult<RedReportDO>(pageCondition.getPage(), pageCondition.getPageSize());
        int totalCount = redDao.countRedReport(redReportDO);
        pageResult.setTotalCount(totalCount);

        if (totalCount > 0) {
            List<RedReportDO> list = redDao.redReport(redReportDO,pageCondition);
            pageResult.setData(list);
        }

        return pageResult;
    }
    
    @Override
    public int redReportCount(RedReportDO redReportDO) {
        int totalCount = redDao.countRedReport(redReportDO);
        return totalCount;
    }

}
