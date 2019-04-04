package com.autoserve.abc.web.module.screen.system.json;

import java.util.List;

import javax.annotation.Resource;

import com.autoserve.abc.dao.dataobject.ButtonDO;
import com.autoserve.abc.service.biz.intf.authority.ButtonService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.ButtonVOConverter;
import com.autoserve.abc.web.vo.button.ButtonListVO;

public class GetButtonList {

    @Resource
    private ButtonService buttonService;

    public List<ButtonListVO> execute() {
        PlainResult<List<ButtonDO>> result = buttonService.queryAllButton();
        return ButtonVOConverter.convertListToListVO(result.getData());
    }
}
