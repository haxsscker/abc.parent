package com.autoserve.abc.web.module.screen.autotransfer.json;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AutoTransferDO;
import com.autoserve.abc.service.biz.entity.AutoTransfer;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.AutoTransferService;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.AutoTransferVOConverter;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;
import com.autoserve.abc.web.vo.auto.AutoTransferVO;

public class ActionAutoTransfer {

    @Resource
    private AutoTransferService autoTransferService;
    @Resource
    private UserService         userService;
    @Resource
    private EmployeeService     employeeService;
    @Resource
    private AccountInfoService  accountInfoService;

    private Logger              logger = LoggerFactory.getLogger(getClass());

    public JsonBaseVO execute(@Params AutoTransferDO at, @Param("page") int page, @Param("rows") int rows,
                              ParameterParser param) {
        PageCondition pageCondition = new PageCondition(page, rows);
        if (at.getAtAuditState() != null && -1 == at.getAtAuditState()) {
            at.setAtAuditState(null);
        }
        String searchForm = param.getString("searchForm");
        if (StringUtils.isNotBlank(searchForm)) {
            try {
                JSONObject searchFormJson = JSON.parseObject(searchForm);
                JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));
                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                    String field = String.valueOf(itemJson.get("Field"));
                    String value = String.valueOf(itemJson.get("Value"));

                    if ("atPayAccotunt".equals(field)) {
                        at.setAtPayAccotunt(value);
                    }
                    if ("atReceibeAccotunt".equals(field)) {
                        at.setAtReceibeAccotunt(value);
                    }
                    if ("atAuditState".equals(field)) {
                        if (StringUtils.isNotBlank(value) && !"-1".equals(value)) {
                            at.setAtAuditState(Integer.parseInt(value));
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("统计查询条件解析出错！", e);
            }
        }
        PageResult<AutoTransfer> result = this.autoTransferService.queryList(at, pageCondition);

        PageResult<AutoTransferVO> listResult = new PageResult<AutoTransferVO>(pageCondition);
        if (result.getDataSize() > 0) {
            List<AutoTransferVO> list = AutoTransferVOConverter.toAutoTransferVOList(result.getData(), userService,
                    employeeService, accountInfoService);
            listResult.setData(list);
        }
        listResult.setTotalCount(result.getTotalCount());
        return ResultMapper.toPageVO(listResult);
    }
}
