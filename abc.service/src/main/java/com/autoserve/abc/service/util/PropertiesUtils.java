package com.autoserve.abc.service.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtils extends Properties {
	private static final long serialVersionUID = 5562231269642715636L;

	/**
	 * 返回含前缀的所有Properties值
	 * @param prefix
	 * @return
	 */
	public Map<String, Object> getProperties(String prefix) {
		return getProperties(prefix, false);
	}
	
	public Map<String, Object> getProperties() {
		return getProperties("");
	}
	
	/**
	 * 截断preifx后产生的properties数组
	 * @param prefix
	 * @return
	 */
	public Map<String, Object> getTruncatedProperties(String prefix) {
		return getProperties(prefix, true);
	}
	
	private Map<String, Object> getProperties(String prefix, Boolean isTruncated) {
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration<?> e = propertyNames();

		for (; e.hasMoreElements();) {
			String key = e.nextElement().toString();
			if (prefix.isEmpty() || key.startsWith(prefix)) {
				if (isTruncated) {
					map.put(key.substring(prefix.length()), getProperty(key));
				} else {
					map.put(key, getProperty(key));
				}
			}
		}
		return map;
	}
}
