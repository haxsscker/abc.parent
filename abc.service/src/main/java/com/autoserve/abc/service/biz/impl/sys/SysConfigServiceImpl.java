package com.autoserve.abc.service.biz.impl.sys;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.SmsNotifyCfg;
import com.autoserve.abc.dao.dataobject.SysConfigDO;
import com.autoserve.abc.dao.intf.SysConfigDao;
import com.autoserve.abc.service.biz.convert.SysConfigConverter;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.google.common.collect.Lists;

/**
 * @author yuqing.zheng Created on 2014-11-27,14:08
 */
@Service
public class SysConfigServiceImpl implements SysConfigService {
	private static final Logger logger = LoggerFactory
			.getLogger(SysConfigServiceImpl.class);

	@Autowired
	private SysConfigDao configDao;

	@Override
	public PlainResult<SysConfig> querySysConfig(SysConfigEntry configEntry) {
		PlainResult<SysConfig> result = new PlainResult<SysConfig>();

		try {
			paramCheck(configEntry);
		} catch (IllegalArgumentException e) {
			result.setError(CommonResultCode.ILEEGAL_ARGUMENT);
			return result;
		}

		SysConfigDO configDO = configDao.findByConfigKey(configEntry.name());
		if (configDO == null) {
			logger.warn("未找到key为{}的系统配置", configEntry.name());
			result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS, "系统配置");
			return result;
		}

		result.setData(SysConfigConverter.toSysConfig(configDO));
		return result;
	}

	@Override
	public BaseResult modifySysConfig(SysConfigEntry configEntry,
			String configValue) {
		try {
			paramCheck(configEntry, configValue);
		} catch (IllegalArgumentException e) {
			return new BaseResult(CommonResultCode.ILEEGAL_ARGUMENT);
		}

		// 验证改系统参数是否存在，存在则跟新，不存在则插入
		SysConfigDO configDO = new SysConfigDO();
		configDO.setConfKey(configEntry.name());
		configDO.setConfValue(configValue);
		if (null == configDao.findByConfigKey(configEntry.name())) {
			configDao.insert(configDO);
		} else {
			configDao.updateByConfigKey(configDO);
		}

		return BaseResult.SUCCESS;
	}

	@Override
	public BaseResult batchCreateSysConfig(List<SysConfig> list) {
		List<SysConfigDO> listDO = Lists.newArrayList();
		for (SysConfig sysConfig : list) {
			listDO.add(SysConfigConverter.toSysConfigDO(sysConfig));
		}

		if (list.size() > 0) {
			configDao.batchInsert(listDO);
		} else {
			logger.error("修改条数为空");
		}

		return BaseResult.SUCCESS;
	}

	@Override
	public ListResult<SysConfig> queryListByParam(List<String> lists) {
		ListResult<SysConfig> listResult = new ListResult<SysConfig>();

		List<SysConfigDO> listDO = configDao.findListByList(lists);
		List<SysConfig> list = Lists.newArrayList();
		for (SysConfigDO sysConfigDO : listDO) {
			list.add(SysConfigConverter.toSysConfig(sysConfigDO));
		}

		listResult.setData(list);
		return listResult;
	}

	@Override
	public PlainResult<SmsNotifyCfg> querySmsNotifyConfig(SysConfigEntry sysConfigEntry) {
		PlainResult<SmsNotifyCfg> result = new PlainResult<SmsNotifyCfg>();
		SysConfigDO sysConfigDO = configDao.findByConfigKey(sysConfigEntry.name());
		if(sysConfigDO==null){
			logger.warn("未找到key为{}的系统配置", sysConfigEntry.name());
			result.setError(CommonResultCode.ERROR_DATA_NOT_EXISTS, "系统配置");
			return result;
		}
		SmsNotifyCfg smsNotifyCfg = null;
		try{
			smsNotifyCfg = JSON.parseObject(sysConfigDO.getConfValue(), SmsNotifyCfg.class);
		}catch(Exception e){
			logger.warn("短信模板错误，{}", sysConfigDO.getConfValue());
			result.setSuccess(false);
			result.setMessage("短信模版错误");
			return result;
		}
		result.setData(smsNotifyCfg);
		return result;
	}

	private void paramCheck(SysConfigEntry configEntry) {
		if (configEntry == null) {
			logger.error("SysConfigEntry不能为空");
			throw new IllegalArgumentException();
		}
	}

	private void paramCheck(SysConfigEntry configEntry, String configValue) {
		paramCheck(configEntry);

		if (StringUtils.isBlank(configValue)) {
			logger.error("系统配置值不能为空白");
			throw new IllegalArgumentException();
		}
	}

}
