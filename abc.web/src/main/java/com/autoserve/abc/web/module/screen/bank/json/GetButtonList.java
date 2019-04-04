package com.autoserve.abc.web.module.screen.bank.json;

import java.util.List;

import javax.annotation.Resource;

import com.autoserve.abc.dao.dataobject.ButtonDO;
import com.autoserve.abc.service.biz.intf.authority.ButtonService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.ButtonVOConverter;
import com.autoserve.abc.web.vo.button.ButtonListVO;

/**
 * 类GetButtonList.java的实现描述： 
 * 
 * @author dengjingyu 2014年12月23日 下午4:22:40
 */
public class GetButtonList {

    @Resource
    private ButtonService buttonService;

    public List<ButtonListVO> execute() {
        PlainResult<List<ButtonDO>> result = buttonService.queryAllButton();
        return ButtonVOConverter.convertListToListVO(result.getData());
    }
}
