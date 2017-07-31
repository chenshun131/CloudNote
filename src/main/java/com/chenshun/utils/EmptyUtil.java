package com.chenshun.utils;

import java.util.List;
import java.util.Map;

/**
 * User: mew <p />
 * Time: 17/7/31 14:11  <p />
 * Version: V1.0  <p />
 * Description: 判断是否[不]为空的工具类 <p />
 * 有多种类型的重载
 */
public class EmptyUtil {

    /**
     * 私有构造
     */
    private EmptyUtil() {
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return true or false
     */
    public static boolean isEmpty(String str) {
        return str == null || str == "" || str.trim() == "" || str.length() <= 0;
    }

    /**
     * 判断List是否为空
     *
     * @param list
     * @return true or false
     */
    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    /**
     * 判断Map是否为空
     *
     * @param map
     * @return true or false
     */
    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断数组是否为空
     *
     * @param array
     * @return true or false
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断字符串是否不为空
     *
     * @param str
     * @return true or false
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断List是否不为空
     *
     * @param list
     * @return true or false
     */
    public static <T> boolean isNotEmpty(List<T> list) {
        return !isEmpty(list);
    }

    /**
     * 判断Map是否不为空
     *
     * @param map
     * @return true or false
     */
    public static <K, V> boolean isNotEmpty(Map<K, V> map) {
        return !isEmpty(map);
    }

    /**
     * 判断数组是否不为空
     *
     * @param array
     * @return true or false
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return !isEmpty(array);
    }

}
