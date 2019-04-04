package com.autoserve.abc.web.module.screen.link.json;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.SysLinkInfoDO;
import com.autoserve.abc.service.biz.intf.sys.SysLinkInfoService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.SysLinkInfoVOConverter;
import com.autoserve.abc.web.vo.syslinkinfo.SysLinkInfoItemVO;
import com.autoserve.abc.web.vo.syslinkinfo.SysLinkInfoVO;

/**
 * 类ActionFriendLinkListView.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月18日 下午3:11:11
 */
public class ActionFriendLinkListView {

    @Resource
    private SysLinkInfoService sysLinkInfoService;

    public SysLinkInfoItemVO execute(ParameterParser params) {
        Integer rows = params.getInt("rows");
        Integer page = params.getInt("page");
        PageCondition pageCondition = new PageCondition(page, rows);
        PageResult<SysLinkInfoDO> result = sysLinkInfoService.queryListByParam(pageCondition);

        List<SysLinkInfoDO> listDo = result.getData();
        SysLinkInfoItemVO vo = new SysLinkInfoItemVO();
        List<SysLinkInfoVO> listVo = new ArrayList<SysLinkInfoVO>();
        for (SysLinkInfoDO sysLinkInfoDO : listDo) {
            listVo.add(SysLinkInfoVOConverter.toSysLinkInfoVO(sysLinkInfoDO));
        }
        vo.setTotal(listVo.size());
        vo.setRows(listVo);
        return vo;
    }
}
