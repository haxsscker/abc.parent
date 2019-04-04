package com.autoserve.abc.service.util;

import com.autoserve.abc.service.biz.entity.History;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 记录属性变更历史的工具类
 * HistoryUtil util = new HistoryUtil();
 * util.record(xxxx.class, instance1, instance1, "1" ,"admin");
 *
 * @author RJQ 2014/12/12 14:09.
 */
public class HistoryUtil<T> {

    private static Logger logger = LoggerFactory.getLogger(HistoryUtil.class);

    /**
     * 比较两个对象哪些属性发生变化,将变化的属性保存为History对象.
     *
     * @param clazz             修改类
     * @param oldObj            老对象
     * @param newObj            新对象
     * @param excludeFieldNames 排除的字段名，不管修改前后值相不相等都跳过
     * @return List<History>
     */
    public List<History> record(Class<T> clazz, T oldObj, T newObj, String[] excludeFieldNames) {
        // 如果两个对象相同直接退出
        if (oldObj == newObj) {
            return null;
        }

        List<History> list = new ArrayList<History>();
        // 得到指定类的所有属性Field.
        Field[] allFields = clazz.getDeclaredFields();

        for (Field field : allFields) {
            // 设置类的私有字段属性可访问.
            field.setAccessible(true);
            try {
                if (checkExcludeFieldNames(excludeFieldNames, field.getName())) {//排除的字段名，跳过此次循环
                    continue;
                }
                logger.debug("Filed: " + field.getName() + ", old: " + field.get(oldObj) + ", new: " + field.get(newObj));
                if (field.get(newObj) == null || field.get(newObj).equals("")) {
                    if (field.get(oldObj) == null || field.get(oldObj).equals("")) {
                        continue;
                    } else {
                        History history = new History();
                        history.setGuhField(field.getName());
                        history.setGuhFieldOld(field.get(oldObj));
                        history.setGuhFiledNew(field.get(newObj));
                        list.add(history);
                    }
                } else {
                    if (field.get(oldObj) == null || field.get(oldObj).equals("")) {
                        History history = new History();
                        history.setGuhField(field.getName());
                        history.setGuhFieldOld(field.get(oldObj));
                        history.setGuhFiledNew(field.get(newObj));
                        list.add(history);
                    } else {
                        if (!field.get(oldObj).equals(field.get(newObj))) {
                            History history = new History();
                            history.setGuhField(field.getName());
                            history.setGuhFieldOld(field.get(oldObj));
                            history.setGuhFiledNew(field.get(newObj));
                            list.add(history);
                        }
                    }
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    /**
     * 判断当前字段是否是排除的字段
     *
     * @param excludeFieldNames 要排除的字段数组
     * @param fieldName         当前字段
     * @return
     */
    private boolean checkExcludeFieldNames(String[] excludeFieldNames, String fieldName) {
        if (excludeFieldNames != null && excludeFieldNames.length != 0) {
            for (String field : excludeFieldNames) {
                if (field.equals(fieldName)) {
                    return true;
                }
            }
        }
        return false;
    }


    /*public static void main(String[] args) {
        Student s = new Student();
        s.setId(1);
        s.setName("李坤");
        s.setSex("男");
        s.setClazz("五期提高班");

        // 可以使用commons-beanutils-xxx.jar中的下面这个方法来保留原对象.
        // BeanUtils.copyProperties(dest, src);

        Student s2 = new Student();
        s2.setId(1);
        s2.setName("李佳");
        s2.setSex("女");

        // HistoryUtil util = new HistoryUtil();
        // util.record(Student.class, s, s2,"1","admin");

        HistoryUtil<Student> util = new HistoryUtil<Student>();//方法内部有输出
        util.record(Student.class, s, s2, "1", "admin");

    }*/

    /*static class Student {
        private int id;
        private String name;
        private String sex;
        private String clazz;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getClazz() {
            return clazz;
        }

        public void setClazz(String clazz) {
            this.clazz = clazz;
        }

        @Override
        public String toString() {
            return "Student [clazz=" + clazz + ", id=" + id + ", name=" + name
                    + ", sex=" + sex + "]";
        }
    }*/
}



