package com.autoserve.abc.web.module.screen.sysset.json;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.LogFileRecordDO;
import com.autoserve.abc.service.biz.intf.sys.LogFileService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.LogFileRecordVOConverter;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.sysset.LogFileRecordVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * 获取系统错误日志列表
 *
 * @author RJQ 2015/1/10 21:45.
 */
public class GetSystemErrorLogList {
    @Autowired
    private LogFileService logFileService;

    public JsonPageVO<LogFileRecordVO> execute(ParameterParser params) {
        JsonPageVO<LogFileRecordVO> vo = new JsonPageVO<LogFileRecordVO>();
        PageCondition pageCondition = new PageCondition(params.getInt("page"), params.getInt("rows"));

        PageResult<LogFileRecordDO> result = logFileService.queryList(new LogFileRecordDO(), pageCondition);
        if(!CollectionUtils.isEmpty(result.getData())){
            vo.setTotal(result.getTotalCount());
            vo.setRows(LogFileRecordVOConverter.convertToVOList(result.getData()));
            return vo;
        }

        vo.setTotal(0);
        vo.setRows(new ArrayList<LogFileRecordVO>());
        return vo;
    }
}
