package com.autoserve.abc.web.module.screen.charge.json;

import java.util.ArrayList;
import java.util.List;

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
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.ChargeRecord;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.intf.cash.ChargeRecordService;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.ChargeRecordVOConverter;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.charge.ChargeRecordVO;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

/**
 * 项目费用搜索
 *
 * @author liuwei 2014年12月17日 上午10:35:16
 */
public class SearchTollStatistic {
    @Resource
    private LoanQueryService    loanQueryService;
    @Resource
    private ChargeRecordService chargeRecordService;
    @Resource
    private GovernmentService   governmentService;
    @Resource
    private UserService         userService;

    private static Logger       logger = LoggerFactory.getLogger(SearchTollStatistic.class);

    public JsonPageVO<ChargeRecordVO> execute(ParameterParser params) {
        //分页
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");

        PageCondition pageCondition = new PageCondition(page, rows);
        //搜索
        String searchForm = params.getString("searchForm");
        //最低服务费用
        String service_fee_min = "";
        //最高服务费用
        String service_fee_max = "";
        //最低手续费
        String chargefee_min = "";
        //最高手续费
        String charge_fee_max = "";
        Loan searchloan = new Loan();
        if (StringUtils.isNotBlank(searchForm)) {
            try {
                JSONObject searchFormJson = JSON.parseObject(searchForm);
                JSONArray itemsArray = JSON.parseArray(String.valueOf(searchFormJson.get("Items")));

                for (Object item : itemsArray) {
                    JSONObject itemJson = JSON.parseObject(String.valueOf(item));
                    String field = String.valueOf(itemJson.get("Field"));
                    String value = String.valueOf(itemJson.get("Value"));

                    if ("project_number".equals(field)) {
                        searchloan.setLoanNo(value);
                    }
                    if ("type".equals(field)) {
                        searchloan.setLoanCategory(LoanCategory.valueOf(Integer.valueOf(value)));
                    }
                    if ("service_fee_min".equals(field)) {
                        service_fee_min = value;
                    }
                    if ("service_fee_max".equals(field)) {
                        service_fee_max = value;
                    }
                    if ("chargefee_min".equals(field)) {
                        chargefee_min = value;
                    }
                    if ("charge_fee_max".equals(field)) {
                        charge_fee_max = value;
                    }
                }
            } catch (Exception e) {
                logger.error("统计查询条件解析出错！", e);
            }
        }

        //查出所有项目
        PageResult<Loan> result = loanQueryService.querySearchLoanListByParam(searchloan, pageCondition,
                service_fee_min, service_fee_max, chargefee_min, charge_fee_max);
        List<Loan> queryResult = result.getData();
        List<Integer> loanIds = Lists.transform(queryResult, new Function<Loan, Integer>() {
            @Override
            public Integer apply(Loan input) {
                return input.getLoanId();
            }
        });
        ListResult<ChargeRecord> resultDO = new ListResult<ChargeRecord>();
        if (!CollectionUtils.isEmpty(loanIds)) {
            //查处所有费用
            resultDO = chargeRecordService.queryChargeRecordByLoanId(loanIds);
        }
        List<ChargeRecordVO> list = new ArrayList<ChargeRecordVO>();

        for (Loan loan : queryResult) {

            ChargeRecordVO vo = new ChargeRecordVO();
            vo = ChargeRecordVOConverter.toChargeRecordVO(loan, vo);
            //查出项目担保机构名称
            GovernmentDO governmentDO = governmentService.findById(loan.getLoanGuarGov()).getData();
            if (governmentDO != null) {
                vo.setGuarantee_institutions(governmentDO.getGovName());
            }
            //借款人名称
            UserDO userDO = userService.findById(loan.getLoanUserId()).getData();
            if (userDO != null) {
                vo.setBorrower(userDO.getUserName());
            }

            for (ChargeRecord chargeRecord : resultDO.getData()) {
                if (chargeRecord.getLoanId().equals(loan.getLoanId())) {
                    //计算费用
                    switch (chargeRecord.getFeeType()) {
                        case PLA_FEE:
                            vo.addChargefee(chargeRecord.getFee());
                            break;
                        case PLA_SERVE_FEE:
                            vo.addServicefee(chargeRecord.getFee());
                            break;
                        case INSURANCE_FEE:
                            vo.addServicefee(chargeRecord.getFee());
                            break;
                        case TRANSFER_FEE:
                            vo.addChargefee(chargeRecord.getFee());
                            break;
                        case PURCHASE_FEE:
                            vo.addChargefee(chargeRecord.getFee());
                            break;
                    }
                }
            }

            list.add(vo);
        }
        JsonPageVO<ChargeRecordVO> jsonPageVO = new JsonPageVO<ChargeRecordVO>();
        jsonPageVO.setRows(list);
        jsonPageVO.setTotal(result.getTotalCount());
        return jsonPageVO;
    }
}
