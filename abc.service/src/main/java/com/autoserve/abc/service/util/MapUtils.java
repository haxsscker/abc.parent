package com.autoserve.abc.service.util;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapUtils {
	// private static final Logger logger =
	// LoggerFactory.getLogger(MapUtil.class);
	/**
	 * 分割纬度一样的二维数组成为多维数组，不能出现短路径和路径一致的
	 * 
	 * @param map
	 *            需要被分割的二维数组
	 * @param regex
	 *            分割的字符，和字符串的split一样
	 * @author zhihua.zhangzh
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> split(Map<String, Object> map, String regex) {
		String regexString = null;
		Map<String, Object> result = new HashMap<String, Object>();
		List<String> a = new ArrayList<String>();

		Iterator iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object okey = entry.getKey();
			Object oval = entry.getValue();
			String key = okey.toString();
			String[] keys = key.split(regex);

			switch (keys.length) {
			case 0:
				continue;
			case 1:
				result.put(key, oval);
				break;
			default:
				if (regexString == null) {
					Pattern pattern = Pattern.compile(regex);
					Matcher matcher = pattern.matcher(key);
					if (matcher.find()) {
						regexString = matcher.group();
					}
				}

				String k = keys[0];

				Map<String, Object> m;
				if (result.containsKey(k)) {
					m = (Map<String, Object>) result.get(k);
				} else {
					a.add(k);
					m = new HashMap<String, Object>();
				}
				StringBuffer k1 = new StringBuffer();
				for (int i = 1, j = keys.length - 1; i < j; i++) {
					k1.append(keys[i]);
					k1.append(regexString);
				}
				k1.append(keys[keys.length - 1]);
				m.put(k1.toString(), oval);
				result.put(k, m);
			}
		}

		for (String n : a) {
			result.put(n, split((Map<String, Object>) result.get(n), regex));
		}
		return result;
	}

	/**
	 * 默认直接采用 \W （等价于 '[^A-Za-z0-9_]'）来进行分割
	 * 
	 * @see split(Map<String, Object> map, String regex)
	 * @param map
	 * @return
	 */
	public static Map<String, Object> split(Map<String, Object> map) {
		return split(map, "\\W+");
	}

	/**
	 * Array type to List type
	 * 
	 * @param arr
	 * @return
	 */
	public static <T> List<T> array2List(T[] arr) {
		List<T> a = new ArrayList<T>();
		if (arr.length > 0) {
			for (int i = 0; i < arr.length; i++) {
				a.add(arr[i]);
			}
		}
		return a;
	}

	/**
	 * 交集：在original和selected都同时存在的部分
	 * 
	 * @param original
	 * @param selected
	 * @return
	 */
	public static <T> List<T> intersection(List<T> original, List<T> selected) {
		List<T> result = new ArrayList<T>();
		if (original == null || selected == null)
			return result;
		result.addAll(original);
		result.retainAll(selected);
		return result;
	}

	/**
	 * 并集：将original和selected合并的部分
	 * 
	 * @param original
	 * @param selected
	 * @return
	 */
	public static <T> List<T> union(List<T> original, List<T> selected) {
		List<T> result = new ArrayList<T>();
		if (original != null) {
			result.addAll(original);
		}
		if (selected != null) {
			result.addAll(selected);
		}
		return result;
	}

	/**
	 * 差集:在selected中有，在original中没有的部分
	 * 
	 * @param original
	 * @param selected
	 * @return
	 */
	public static <T> List<T> diff(List<T> original, List<T> selected) {
		List<T> result = new ArrayList<T>();
		if (selected == null) {
			return result;
		} else {
			result.addAll(selected);
		}

		if (original == null) {
			return result;
		} else {
			result.removeAll(original);
		}

		return result;
	}
}
