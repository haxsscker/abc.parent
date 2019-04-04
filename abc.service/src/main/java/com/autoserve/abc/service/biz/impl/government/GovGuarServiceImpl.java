package com.autoserve.abc.service.biz.impl.government;

import com.autoserve.abc.dao.dataobject.GovGuarDO;
import com.autoserve.abc.dao.dataobject.GovGuarWithNameDO;
import com.autoserve.abc.dao.intf.GovGuarDao;
import com.autoserve.abc.service.biz.intf.government.GovGuarService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author RJQ 2014/12/9 14:57.
 */
@Service
public class GovGuarServiceImpl implements GovGuarService {
    @Resource
    private GovGuarDao govGuarDao;

    @Override
    public Map<Integer, StringBuilder> findGovGuarByList(List<Integer> ids) {
        Map<Integer, StringBuilder> result = new HashMap<Integer, StringBuilder>();
        List<GovGuarWithNameDO> govGuarDOs = govGuarDao.findByList(ids);
        if (govGuarDOs != null && govGuarDOs.size() != 0) {
            for (int i = 0, j = govGuarDOs.size(); i < j; ++i) {
                GovGuarWithNameDO tmpDo = govGuarDOs.get(i);
                if (result.get(tmpDo.getGgGovId()) != null) {
                    result.get(tmpDo.getGgGovId()).append(",").append(tmpDo.getGuarName());
                } else {
                    result.put(tmpDo.getGgGovId(), new StringBuilder(tmpDo.getGuarName()));
                }
            }
        }
        return result;
    }

    @Override
    public Map<Integer, String> findGovGuarByGovId(Integer govId) {
        Map<Integer, String> result = new HashMap<Integer, String>();
        List<GovGuarWithNameDO> govGuarDOs = govGuarDao.findWithGuarNameByGovId(govId);
        if (govGuarDOs != null && govGuarDOs.size() != 0) {
            for (int i = 0, j = govGuarDOs.size(); i < j; ++i) {
                GovGuarWithNameDO tmpDo = govGuarDOs.get(i);
                result.put(tmpDo.getGgGuarId(), tmpDo.getGuarName());
            }
        }
        return result;
    }

    @Override
    public ListResult<GovGuarDO> queryList(GovGuarDO govGuarDO) {
        ListResult<GovGuarDO> result = new ListResult<GovGuarDO>();
        result.setData(govGuarDao.findListByParam(govGuarDO));
        return result;
    }

    @Override
    public BaseResult insertGovGuarByList(List<Integer> guarIds, Integer govId) {
        BaseResult result = new BaseResult();
        List<GovGuarDO> govGuarDOs = new ArrayList<GovGuarDO>();
        if (guarIds == null || guarIds.size() == 0 || govId == null) {
            //根据业务，参数为空，不做插入操作
            result.setMessage("参数不正确，不做插入操作！");
            return result;
        }

        for (int i = 0, j = guarIds.size(); i < j; ++i) {
            GovGuarDO govGuarDO = new GovGuarDO();
            govGuarDO.setGgGovId(govId);
            govGuarDO.setGgGuarId(guarIds.get(i));
            govGuarDOs.add(govGuarDO);
        }
        int returnVal = govGuarDao.insertGovGuarByList(govGuarDOs);
        if (returnVal <= 0) {
            result.setErrorMessage(CommonResultCode.BIZ_ERROR, "插入机构与担保机构信息失败！");
        }
        return result;
    }

    @Override
    public BaseResult removeAllGuarByGovId(Integer govId) {
        govGuarDao.deleteByGovId(govId);
        return BaseResult.SUCCESS;
    }

    @Override
    public BaseResult createByDOList(List<GovGuarDO> govGuarDOs) {
        if (govGuarDOs != null && !govGuarDOs.isEmpty()) {
            govGuarDao.batchInsert(govGuarDOs);
        }
        return BaseResult.SUCCESS;
    }

    @Override
    public BaseResult removeByGuarId(Integer guarId) {
        govGuarDao.deleteByGuarId(guarId);
        return BaseResult.SUCCESS;
    }
}
