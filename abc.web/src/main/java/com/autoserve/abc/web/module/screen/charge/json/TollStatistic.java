package com.autoserve.abc.web.module.screen.charge.json;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.ChargeRecord;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.intf.cash.ChargeRecordService;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.ChargeRecordVOConverter;
import com.autoserve.abc.web.module.screen.selfprove.json.CompanyCustomersListView;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.charge.ChargeRecordVO;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

/**
 * 费用统计中收费统计
 *
 * @author liuwei 2014年12月17日 上午10:35:16
 */
public class TollStatistic {
    @Resource
    private LoanQueryService    loanQueryService;
    @Resource
    private ChargeRecordService chargeRecordService;
    @Resource
    private GovernmentService   governmentService;
    @Resource
    private UserService         userService;

    private static Logger       logger = LoggerFactory.getLogger(CompanyCustomersListView.class);

    public JsonPageVO<ChargeRecordVO> execute(ParameterParser params) {
        //分页
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);

        //查出所有项目
        PageResult<Loan> result = loanQueryService.queryLoanListByParam(null, pageCondition);
        List<Loan> resultLoan = result.getData();
        List<Integer> loanIds = Lists.transform(resultLoan, new Function<Loan, Integer>() {
            @Override
            public Integer apply(Loan input) {
                return input.getLoanId();
            }

        });
        ListResult<ChargeRecord> resultData = new ListResult<ChargeRecord>();
        if (!CollectionUtils.isEmpty(loanIds)) {
            //查询项目的收费记录
            resultData = chargeRecordService.queryChargeRecordByLoanId(loanIds);
        }
        List<ChargeRecordVO> list = new ArrayList<ChargeRecordVO>();

        for (Loan loan : resultLoan) {

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

            for (ChargeRecord chargeRecord : resultData.getData()) {
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
