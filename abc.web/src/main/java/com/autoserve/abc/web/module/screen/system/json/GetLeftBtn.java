package com.autoserve.abc.web.module.screen.system.json;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.ButtonDO;
import com.autoserve.abc.service.biz.intf.authority.ButtonService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.ButtonVOConverter;
import com.autoserve.abc.web.vo.button.AllocatBtnVO;

public class GetLeftBtn {

    @Resource
    private ButtonService buttonService;

    private static Logger logger = LoggerFactory.getLogger(GetLeftBtn.class);

    public List<AllocatBtnVO> execute() {
        PlainResult<List<ButtonDO>> result = buttonService.queryAllButton();
        List<AllocatBtnVO> list = ButtonVOConverter.convertList(result.getData());
        logger.info(JSON.toJSONString(list));
        return list;
    }
}
