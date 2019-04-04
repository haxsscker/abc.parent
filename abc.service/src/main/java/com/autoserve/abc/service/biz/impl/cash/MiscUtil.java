package com.autoserve.abc.service.biz.impl.cash;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class MiscUtil {
	private static Logger logger = LoggerFactory.getLogger(MiscUtil.class);

	@SuppressWarnings("unchecked")
	public static HashMap<String, String> parseJSON(String json) {
		try {
			if (json == null) {
				return null;
			}
			HashMap<String, String> myMap = (HashMap<String, String>) JSON
					.parseObject(json, Map.class);
			return myMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
