package com.autoserve.abc.web.module.screen.link.json;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.intf.sys.SysLinkInfoService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * 类AddFriendLink.java的实现描述：TODO 类实现描述
 *
 * @author liuwei 2014年12月3日 下午1:21:02
 */
public class DelFriendLink {

    @Resource
    private SysLinkInfoService sysLinkInfoService;

    private static Logger logger = LoggerFactory.getLogger(DelFriendLink.class);

    public JsonBaseVO execute(ParameterParser params) {
        JsonBaseVO vo = new JsonBaseVO();
        String slId = params.getString("sys_link_id");
        if (slId == null) {
            logger.warn("DelFriendLink参数slId出错");
            vo.setMessage("参数出错");
            vo.setSuccess(false);
            return vo;
        }

        BaseResult result = this.sysLinkInfoService.removeSyslinkInfo(Integer.parseInt(slId));
        return ResultMapper.toBaseVO(result);
    }
}
