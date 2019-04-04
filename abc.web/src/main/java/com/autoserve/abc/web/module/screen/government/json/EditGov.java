package com.autoserve.abc.web.module.screen.government.json;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.GovPlainJDO;
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.dao.dataobject.OperateLogDO;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.sys.OperateLogService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.GovernmentVOConverter;
import com.autoserve.abc.web.helper.LoginUserInfoHelper;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.IPUtil;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;
import com.autoserve.abc.web.vo.government.GovernmentVO;

/**
 * @author RJQ 2014/12/10 14:11.
 */
public class EditGov {
    @Resource
    private GovernmentService governmentService;

    @Resource
    private OperateLogService operateLogService;

    @Resource
    private HttpServletRequest request;

    private static Logger logger = LoggerFactory.getLogger(EditGov.class);

    public JsonBaseVO execute(@Param("org") String org, @Param("govId") Integer govId) {
        JsonBaseVO vo = new JsonBaseVO();
        logger.info("机构修改参数 org={}, govId={}, empId={}", org, govId, LoginUserUtil.getEmpId());
        if (org == null) {
            vo.setSuccess(false);
            vo.setMessage("参数不正确");
            logger.warn("机构修改参数不正确 org={}, govId={}, empId={}", org, govId, LoginUserUtil.getEmpId());
            return vo;
        }
        //用户在修改最大担保额度时需要同步更新可用担保额度
        PlainResult<GovernmentDO> government =  governmentService.findById(govId);
        //最大担保额度
        BigDecimal govMaxGuarAmount = government.getData().getGovMaxGuarAmount();
        //可用担保额度
        BigDecimal govSettGuarAmount = government.getData().getGovSettGuarAmount();
        if(govMaxGuarAmount == null){
        	govMaxGuarAmount = BigDecimal.ZERO;
        }
        if(govSettGuarAmount == null){
        	govSettGuarAmount = BigDecimal.ZERO;
        }
        
        GovernmentVO gov = JSON.parseObject(org, GovernmentVO.class);
        
        Integer govUpdateEmpId = LoginUserInfoHelper.getLoginUserInfo().getEmpId();
        GovPlainJDO govPlainJDO = GovernmentVOConverter.convertToGovPlainDO(gov);
        
        if(gov.getGovMaxGuarAmount() != null){
        	BigDecimal maxGuarAmountSub = gov.getGovMaxGuarAmount().subtract(govMaxGuarAmount);
        	//将原可用担保额度加上最大担保额度之差
        	BigDecimal settGuarAmountAdd = govSettGuarAmount.add(maxGuarAmountSub);
        	//需要保证可用担保额度不能为负值
        	if(settGuarAmountAdd.compareTo(BigDecimal.ZERO)>0){
        		govPlainJDO.setGovSettGuarAmount(settGuarAmountAdd);
        	}else{
        		govPlainJDO.setGovSettGuarAmount(BigDecimal.ZERO);
        	}
        }
        
        govPlainJDO.setGovId(govId);
        BaseResult result = governmentService.modifyGovernment(govPlainJDO, govUpdateEmpId);
        vo = ResultMapper.toBaseVO(result);

        OperateLogDO operateLogDO = new OperateLogDO();
        operateLogDO.setOlEmpId(LoginUserUtil.getEmpId());//操作人ID
        operateLogDO.setOlIp(IPUtil.getUserIpAddr(request));//操作人IP地址
        operateLogDO.setOlModule("机构管理");//操作模块
        operateLogDO.setOlOperateType("修改");//操作类型：添加/修改/删除
        operateLogDO.setOlContent("修改了ID为"+ govId +"的机构");//具体操作内容
        operateLogService.createOperateLog(operateLogDO);

        return vo;
    }
}
