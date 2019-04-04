package com.autoserve.abc.web.module.screen.statistic.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RefundRecordDO;
import com.autoserve.abc.dao.dataobject.search.RefundRecordSearchDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.refund.RefundRecordService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.RefundRecordVOConverter;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.refund.RefundRecordVO;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 退费统计页面
 * 
 * @author liuwei 2014年12月18日 下午2:12:58
 */
public class ReturnStatisticView {
    @Resource
    private RefundRecordService refundRecordService;
    @Resource
    private AccountInfoService  accountInfoService;

    private static Logger       logger = LoggerFactory.getLogger(ReturnStatisticView.class);

    public JsonPageVO<RefundRecordVO> execute(ParameterParser params) {

        //搜索
        String searchForm = params.getString("searchForm");

        RefundRecordSearchDO refundRecordSearchDO = new RefundRecordSearchDO();

        if (StringUtils.isNotBlank(searchForm)) {
            try {
                JSONObject searchFormJson = JSON.parseObject(searchForm);
                JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));

                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                    String field = String.valueOf(itemJson.get("Field"));
                    String value = String.valueOf(itemJson.get("Value"));
                    if ("returnsObject".equals(field)) {
                        refundRecordSearchDO.setReturnsObject(value);
                    }
                    if ("refundAccount".equals(field)) {
                        refundRecordSearchDO.setRefundAccount(value);
                    }
                    if ("costsMin".equals(field)) {
                        refundRecordSearchDO.setCostsMin(value);
                    }
                    if ("costsMax".equals(field)) {
                        refundRecordSearchDO.setCostsMax(value);
                    }
                    if ("phoneNumber".equals(field)) {
                        refundRecordSearchDO.setPhoneNumber(value);
                    }
                }
            } catch (Exception e) {
                logger.error("统计查询条件解析出错！", e);
            }
        }

        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);
        PageResult<RefundRecordDO> result = refundRecordService.queryList(refundRecordSearchDO, pageCondition);
        List<RefundRecordDO> data = result.getData();
        List<Account> account = new ArrayList<Account>();
        List<String> accountNos = Lists.transform(data, new Function<RefundRecordDO, String>() {
            @Override
            public String apply(RefundRecordDO input) {
                return input.getRefundAccountNo();
            }
        });
        if (!CollectionUtils.isEmpty(accountNos)) {
            ListResult<Account> accountRes = accountInfoService.queryByAccountNos(accountNos);

            account = accountRes.getData();
        }
        Map<String, Account> accountMap = Maps.uniqueIndex(account, new Function<Account, String>() {
            @Override
            public String apply(Account input) {
                return input.getAccountNo();
            }
        });
        List<RefundRecordVO> list = new ArrayList<RefundRecordVO>();
        for (RefundRecordDO refundRecordDO : data) {
            RefundRecordVO vo = RefundRecordVOConverter.toRefundRecord(refundRecordDO);
            Account temp = accountMap.get(refundRecordDO.getRefundAccountNo());
            if (temp != null) {
                vo.setReturns_object(temp.getAccountUserName());
            }
            list.add(vo);
        }
        JsonPageVO<RefundRecordVO> jsonPageVO = new JsonPageVO<RefundRecordVO>();
        jsonPageVO.setRows(list);
        jsonPageVO.setTotal(result.getTotalCount());
        return jsonPageVO;
    }
}
