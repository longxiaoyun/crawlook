package io.github.longxiaoyun.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * @author longxiaoyun
 * @date 2024-08-21
 */
public class BeanMapUtils {

    private BeanMapUtils() {}

    /**
     * 将Bean对象转为Map
     * @param bean Bean对象
     * @return Map
     */
    public static Map<String,Object> beanToMap(Object bean) {
        try {
            Map<String,Object> map = new HashMap<>();
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass(), Object.class);
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                String name = pd.getName();
                Object value = pd.getReadMethod().invoke(bean);
                map.put(name,value);
            }
            return map;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static <T> T mapToBean(Map<String, Object> map, Class<T> beanClass){
        try {
            T bean = beanClass.newInstance();
            BeanInfo beanInfo = Introspector.getBeanInfo(beanClass, Object.class);
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                // 从Map中获取属性同名的key值
                Object value = map.get(pd.getName());
                // 调用setter方法设置属性值
                pd.getWriteMethod().invoke(bean,value);
            }
            return bean;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
