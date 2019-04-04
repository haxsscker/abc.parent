package com.autoserve.abc.web.module.screen.mobileIos;

import java.util.HashMap;
/**
 * 重写hashMap,防止put(key,null)转换成json时，没有对应的key
 * @author zhangkang
 *
 * @param <K>
 * @param <V>
 */
public class MobileMap<K,V> extends HashMap<K,V>{
	
	 /**
	  * put null时，改为put ""，
	  */
	 public V put(K key, V value) {
		 if(value==null){
			 return super.put(key, (V) "");
		 }else{
			return super.put(key, value);
		 }
	 }
}
