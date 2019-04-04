package com.autoserve.abc.service.util;

import java.lang.reflect.Method;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Ordering;

/**
 * 提供gava工具
 * @author Tiuwer
 *
 */
public class GuavaUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(GuavaUtil.class);
	/**
	 * 针对对象进行排序
	 * @param <T>
	 * @param obj
	 * @param name 对象需要排序的字段名
	 * @return
	 */
	public static <T> List<T> OrderByParamInteger(final String name,List<T> list){
		Ordering<Object> ordering = new Ordering<Object>(){
			@Override
			public int compare(Object left, Object right) {
			
				try {
					left = this.findName(name,left);
					right = this.findName(name,right);
						
							 if((Integer)left > (Integer)right) return 1;
						else if((Integer)left == (Integer)right) return 0;
						else if((Integer)left < (Integer)right) return -1;
							 
				} catch (Exception e) {
					logger.error("类型获取异常");
					e.printStackTrace();
				}
				return 0;
			}

			private Object findName(String name,Object obj) throws Exception{
				String firstLetter = name.substring(0, 1).toUpperCase();    
		        String getter = "get" + firstLetter + name.substring(1);    
		        Method method = obj.getClass().getMethod(getter, new Class[] {});    
		        Object value = method.invoke(obj, new Object[] {});
				return value;    
			}
		};
		
		List<T> returnList = ordering.sortedCopy(list);
		return returnList;
	}
	/**
	 * 对于对象时间进行排序
	 * @param name 对象需要排序的字段名
	 * @param list
	 * @return
	 */
	public static <T> List<T> OrderByParamDate(final String name,List<T> list){
		Ordering<Object> ordering = new Ordering<Object>(){
			@Override
			public int compare(Object left, Object right) {
			
				try {
					left = this.findName(name,left);
					right = this.findName(name,right);

					if(DateUtil.parseDate(left.toString(),DateUtil.DEFAULT_DAY_STYLE).getTime() < DateUtil.parseDate(right.toString(),DateUtil.DEFAULT_DAY_STYLE).getTime()) return 1;
					else if(DateUtil.parseDate(left.toString(),DateUtil.DEFAULT_DAY_STYLE).getTime() == DateUtil.parseDate(right.toString(),DateUtil.DEFAULT_DAY_STYLE).getTime()) return 0;
					else if(DateUtil.parseDate(left.toString(),DateUtil.DEFAULT_DAY_STYLE).getTime() > DateUtil.parseDate(right.toString(),DateUtil.DEFAULT_DAY_STYLE).getTime()) return -1;
					
				} catch (Exception e) {
					logger.error("类型获取异常");
					e.printStackTrace();
				}
				return 0;
			}

			private Object findName(String name,Object obj) throws Exception{
				String firstLetter = name.substring(0, 1).toUpperCase();    
		        String getter = "get" + firstLetter + name.substring(1);    
		        Method method = obj.getClass().getMethod(getter, new Class[] {});    
		        Object value = method.invoke(obj, new Object[] {});
				return value;    
			}
		};
		
		List<T> returnList = ordering.sortedCopy(list);
		return returnList;
	}

}
