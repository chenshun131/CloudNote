package com.chenshun.utils.reflection;

import com.chenshun.utils.date.DateFormatType;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * User: mew <p />
 * Time: 17/6/16 17:22  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class ConvertUtils {

    static {
        registerDateConverter();
    }

    /**
     * 提取集合中的对象的两个属性(通过Getter函数), 组合成Map.
     *
     * @param collection
     *         来源集合.
     * @param keyPropertyName
     *         要提取为Map中的Key值的属性名.
     * @param valuePropertyName
     *         要提取为Map中的Value值的属性名.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map convertElementPropertyToMap(final Collection collection, final String keyPropertyName, final String valuePropertyName) {
        Map map = new HashMap(collection.size());
        try {
            for (Object obj : collection) {
                map.put(PropertyUtils.getProperty(obj, keyPropertyName), PropertyUtils.getProperty(obj, valuePropertyName));
            }
        } catch (Exception e) {
            throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
        }
        return map;
    }

    /**
     * 提取集合中的对象的属性(通过getter函数), 组合成List.
     *
     * @param collection
     *         来源集合.
     * @param propertyName
     *         要提取的属性名.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List convertElementPropertyToList(final Collection collection, final String propertyName) {
        List list = new ArrayList();
        try {
            for (Object obj : collection) {
                list.add(PropertyUtils.getProperty(obj, propertyName));
            }
        } catch (Exception e) {
            throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
        }
        return list;
    }

    /**
     * 提取集合中的对象的属性(通过getter函数), 组合成由分割符分隔的字符串.
     *
     * @param collection
     *         来源集合.
     * @param propertyName
     *         要提取的属性名.
     * @param separator
     *         分隔符.
     */
    @SuppressWarnings({"rawtypes"})
    public static String convertElementPropertyToString(final Collection collection, final String propertyName, final String separator) {
        List list = convertElementPropertyToList(collection, propertyName);
        return StringUtils.join(list, separator);
    }

    /**
     * 转换Collection所有元素(通过toString())为String, 中间以 separator分隔。
     */
    @SuppressWarnings({"rawtypes"})
    public static String convertToString(final Collection collection, final String separator) {
        return StringUtils.join(collection, separator);
    }

    /**
     * 将对象的属性提取出来组成由属性为key的map
     *
     * @param bean
     * @return Map
     * @throws IntrospectionException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */

    public static <T> Map<?, ?> convertBeanPropertyToMap(T bean) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Class<?> type = bean.getClass();
        Map<String, Object> returnMap = new HashMap<>();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[]{});
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }

    /**
     * 将Map对应的key-value值转化成对应的对象
     *
     * @param map
     * @param bean
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> T convertMapElementToBean(Map<?, ?> map, Class<T> bean) throws IntrospectionException, InstantiationException, IllegalAccessException {
        BeanInfo beanInfo = Introspector.getBeanInfo(bean);
        T obj = bean.newInstance();
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            Method method = descriptor.getWriteMethod();
            String name = descriptor.getName();
            if (map.containsKey(name)) {
                Class<?>[] types = method.getParameterTypes();
                for (Class<?> type : types) { // 类型转换
                    Object value = map.get(name);
                    try {
                        method.invoke(obj, new Object[]{value});
                    } catch (Exception e) {
                        System.out.println("type:" + type.getSimpleName() + "|name:" + name + "|value:" + value);
                        e.printStackTrace();
                    }
                }
            }
        }
        return obj;
    }

    /**
     * 转换字符串到相应类型.
     *
     * @param value
     *         待转换的字符串.
     * @param toType
     *         转换目标类型.
     */
    public static Object convertStringToObject(String value, Class<?> toType) {
        try {
            return org.apache.commons.beanutils.ConvertUtils.convert(value, toType);
        } catch (Exception e) {
            throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * 定义日期Converter的格式
     * 具体见DateUtil中DateFormatType所定义的日期格式化类型
     */
    private static void registerDateConverter() {
        DateConverter dc = new DateConverter();
        dc.setUseLocaleFormat(true);
        dc.setPatterns(DateFormatType.getAllFormatTypes());
        org.apache.commons.beanutils.ConvertUtils.register(dc, Date.class);
    }

}
