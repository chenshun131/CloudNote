package com.chenshun.utils.mapper;

import org.dozer.DozerBeanMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: mew <p />
 * Time: 17/6/16 16:05  <p />
 * Version: V1.0  <p />
 * Description: 简单封装Dozer, 实现对象之间深度转换 Bean <-> Bean的Mapper实现: <br/>
 * 1. 持有Mapper的单例. <br/>
 * 2. 返回值类型转换. <br/>
 * 3. 批量转换Collection中的所有对象. <br/>
 * 4. 区分创建新的B对象与将对象A值复制到已存在的B对象两种函数.
 * https://github.com/DozerMapper/dozer <p />
 */
public class BeanMapper {

    /** 持有Dozer单例, 避免重复创建DozerMapper消耗资源. */
    private static final DozerBeanMapper dozer = new DozerBeanMapper();

    /**
     * 基于Dozer转换对象的类型.
     */
    public static <T> T map(Object source, Class<T> destinationClass) {
        return dozer.map(source, destinationClass);
    }

    /**
     * 基于Dozer转换Collection中对象的类型.
     */
    public static <T> List<T> mapList(Collection<T> sourceList, Class<T> destinationClass) {
        List<T> destinationList = new ArrayList<>();
        for (Object sourceObject : sourceList) {
            T destinationObject = dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;
    }

    /**
     * 基于Dozer将对象A的值拷贝到对象B中.
     */
    public static void copy(Object source, Object destinationObject) {
        dozer.map(source, destinationObject);
    }

}
