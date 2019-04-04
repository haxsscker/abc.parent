package com.autoserve.abc.service.util;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 针对class和method的一些公共操作方法类
 */
public class ClassUtils {
	private static final Logger logger = LoggerFactory.getLogger(ClassUtils.class);
	/**
	 * beanMap,用来存放所有根据xml配置文件实例化的对象
	 */
	private Map<String, Object> beanMap = new HashMap<String, Object>();

	/**
	 * 初始化。在class路径下查找指定的xml文件，并初始化bean工厂
	 * 
	 * @param xmlUri
	 *            xml文件在classpath的相对路径。例如：/com/jisong/ioc/zjsioc.xml
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void init(String xmlUri) throws Exception {
		try {

			// 1.根据根据xml的相对路径，读取xml文件，并获得xml文件的根元素
			// 1.1根据xml的相对路径，获得一个输入流
			SAXReader reader = new SAXReader();// 创建一个解析器对象
			// ClassLoader classLoader = Thread.currentThread()
			// .getContextClassLoader();//获得当前线程的类加载器
			// InputStream in =
			// classLoader.getResourceAsStream(xml);//通过类加载器，获得指定相对路径下文件的输入流

			InputStream in = this.getClass().getResourceAsStream(xmlUri);// 通过当前Class对象，获得指定相对路径下文件的输入流

			// 1.2.从输入流获得document对象
			Document doc = reader.read(in);// 通过解析器对象，读取输入流并转换为一个Document对象

			// 1.3.从document对象，获得xml文件的根元素
			Element root = doc.getRootElement();// 获得根元素
			Element elBean = null;// 定义bean元素变量

			// 2.遍历bean元素，实例化所有bean并初始化其属性值，然后保存在beanMap中
			for (Iterator iteBean = root.elementIterator("bean"); iteBean.hasNext();) {
				elBean = (Element) iteBean.next();// 获得bean元素

				// 2.1获得bean的属性id和class
				Attribute atrId = elBean.attribute("id");
				Attribute atrClass = elBean.attribute("class");

				// 2.2通过Java反射机制，通过class的名称获取Class对象
				Class clsBean = Class.forName(atrClass.getText());

				// 2.3获得指定class的所有属性描述，以初始化其所有属性

				// 获取其属性描述数组
				Field pds[] = clsBean.getDeclaredFields();

				// 将属性描述数组转换为HashMap,这样下一步在设置属性的值的时候，速度更快
				Map<String, Field> mapProp = new HashMap<String, Field>();
				for (Field pd : pds) {
					mapProp.put(pd.getName(), pd);
				}

				// 2.4创建指定class的实例obj
				Object obj = clsBean.newInstance();
				Method mSet = null;// 定义set方法变量

				// 2.5遍历该bean的property属性，并通过Java反射调用其set方法，设置obj的所有属性的值
				for (Iterator iteProp = elBean.elementIterator("property"); iteProp.hasNext();) {
					// 2.5.1 获得属性元素
					Element elProp = (Element) iteProp.next();

					// 2.5.2获取该property的name属性
					Attribute atrName = elProp.attribute("name");
					Attribute atrType = elProp.attribute("type");

					Class clsType = Class.forName(atrType.getText());

					// 2.5.3获取该property的子元素value的值
					String strValue = null;
					// 正常情况下，name元素只能有一个value属性
					for (Iterator iteValue = elProp.elementIterator("value"); iteValue.hasNext();) {
						Element elValue = (Element) iteValue.next();
						strValue = elValue.getText();
						break;
					}

					// 2.5.4调用对象属性名的 set方法，该指定的属性赋值
					Field tmpPd = mapProp.get(atrName.getValue());
					if (tmpPd != null) {
						String fieldName = tmpPd.getName();
						String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
						mSet = clsBean.getMethod(methodName, clsType);// 取得指定的set方法
						mSet.invoke(obj, strValue);// 通过Java反射，调用指定对象的方法并传参（即调用指定的set方法）
					}
				}
				// 将对象放入beanMap中，其中key为id值，value为obj对象
				beanMap.put(atrId.getText(), obj);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 通过id获取bean的对象.
	 * 
	 * @param beanId
	 *            xml文件中bean元素的id属性值
	 * @return 返回对应对象
	 */
	public Object getBean(String beanId) {
		Object obj = beanMap.get(beanId);
		return obj;
	}

	/**
	 * 利用递归找一个类的指定方法，如果找不到，去父类里面找直到最上层Object对象为止。
	 * 
	 * @param clazz
	 *            目标类
	 * @param methodName
	 *            方法名
	 * @param classes
	 *            方法参数类型数组
	 * @return 方法对象
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Method getMethod(Class clazz, String methodName, final Class[] classes) throws Exception {
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(methodName, classes);
		} catch (NoSuchMethodException e) {
			try {
				method = clazz.getMethod(methodName, classes);
			} catch (NoSuchMethodException ex) {
				if (clazz.getSuperclass() == null) {
					return method;
				} else {
					method = getMethod(clazz.getSuperclass(), methodName, classes);
				}
			}
		}
		return method;
	}

	/**
	 * 调用方法
	 * 
	 * @param obj
	 *            调整方法的对象
	 * @param methodName
	 *            方法名
	 * @param classes
	 *            参数类型数组
	 * @param objects
	 *            参数数组
	 * @return 方法的返回值
	 */
	@SuppressWarnings("rawtypes")
	public static Object invoke(final Object obj, final String methodName, final Class[] classes, final Object[] objects) {
		try {
			Method method = getMethod(obj.getClass(), methodName, classes);
			method.setAccessible(true);// 调用private方法的关键一句话
			return method.invoke(obj, objects);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 调用方法
	 * 
	 * @param obj
	 * @param methodName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object invoke(final Object obj, final String methodName, final Class[] classes) {
		return invoke(obj, methodName, classes, new Object[] {});
	}

	/**
	 * 调用方法
	 * 
	 * @param obj
	 * @param methodName
	 * @return
	 */
	public static Object invoke(final Object obj, final String methodName) {
		return invoke(obj, methodName, new Class[] {}, new Object[] {});
	}

	/**
	 * 自动setter，会通过反射的方式将string类型的值自动转换
	 * 
	 * @param obj
	 * @param config
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Boolean setter(Object obj, Map<String, Object> config) {
		if (config == null) {
			throw new IllegalArgumentException("ClassUtils.setter.config is null");
		}

		Class<? extends Object> clazz = obj.getClass();
		Iterator<Entry<String, Object>> iter = config.entrySet().iterator();

		while (iter.hasNext()) {
			Map.Entry entry = iter.next();
			String fieldName = entry.getKey().toString();
			String methodName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			String setMethodName = "set" + methodName;
			String getMethodName = "get" + methodName;
			// 不能单单使用字段来处理，因为有一些保留字，也可能被做了setter和getter的，比如dubbo里面用了interface
			try {
				Method method = getMethod(clazz, getMethodName, null);
				Object value = autoTypeConversion(method.getReturnType().getSimpleName(), entry.getValue());
				clazz.getMethod(setMethodName, method.getReturnType()).invoke(obj, value);
			} catch (Exception e) {
				// 忽略有异常的字段
				logger.warn("fieldName:[" + fieldName + "] Setter error: ", e);
			}
		}

		return true;
	}

	/**
	 * 获取某个类中的所有getX方法的值
	 * 
	 * @param o
	 *            目标对象
	 * @param prefix
	 *            给每个值都加一个前缀
	 * @return
	 */
	public static Map<String, Object> getter(Object o, String prefix) {
		Map<String, Object> map = new HashMap<String, Object>();
		Class<? extends Object> clazz = o.getClass();
		Method[] methods = clazz.getDeclaredMethods();

		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().startsWith("get")) {// getXX()
				// methods[i].setAccessible(true);// 允许private被访问
				String name = methods[i].getName().substring(3);
				String fieldName = name.substring(0, 1).toLowerCase() + name.substring(1);
				try {
					Object object = methods[i].invoke(o);
					map.put(prefix + fieldName, object);
				} catch (Exception e) {
					// 忽略有异常的字段
					logger.warn("fieldName:[" + fieldName + "] Getter error: ", e);
				}
			}
		}

		return map;
	}

	/**
	 * 获取某个类中的所有getX方法的值
	 * 
	 * @param o
	 *            目标对象
	 * @return
	 */
	public static Map<String, Object> getter(Object o) {
		return getter(o, "");
	}

	/**
	 * 将字符串自动根据指定的类型转换类型
	 * 
	 * @param type
	 * @param value
	 * @return
	 */
	public static Object autoTypeConversionIsEmptyToNull(String type, Object value, Boolean isEmptyToNull) {
		if (value.getClass().getSimpleName().equalsIgnoreCase("string")) {
			String val = value.toString();
			if (isEmptyToNull && val.isEmpty()) {
				return null;
			}
			if (type.equalsIgnoreCase("boolean")) {
				return Boolean.valueOf(val);
			} else if (type.equalsIgnoreCase("byte")) {
				return Byte.valueOf(val);
			} else if (type.equalsIgnoreCase("char") || type.equalsIgnoreCase("Character")) {
				return val.charAt(0);
			} else if (type.equalsIgnoreCase("short")) {
				return Short.valueOf(val);
			} else if (type.equalsIgnoreCase("int") || type.equalsIgnoreCase("Integer")) {
				return Integer.valueOf(val);
			} else if (type.equalsIgnoreCase("long")) {
				return Long.valueOf(val);
			} else if (type.equalsIgnoreCase("float")) {
				return Float.valueOf(val);
			} else if (type.equalsIgnoreCase("double")) {
				return Double.valueOf(val);
			} else {// Not in the Java the eight basic type inside
				return value;
			}
		}
		return value;
	}
	
	public static Object autoTypeConversion(String type, Object value) {
		return autoTypeConversionIsEmptyToNull(type, value, false);
	}

	/**
	 * 获取传入值的类型，如果不是java.lang.*中的类型，那么会去找第一个interface做为类型，否则用自己本身的类做为类型
	 * 
	 * @param object
	 * @return
	 */
	public static <T> String getTypeName(T object) {
		String typeName;
		if (object.getClass().getName().startsWith("java.lang.")) {
			typeName = object.getClass().getName();
		} else if (object.getClass().getInterfaces().length > 0) {
			typeName = object.getClass().getInterfaces()[0].getName();
		} else {
			typeName = object.getClass().getName();
		}
		return typeName;
	}
}
