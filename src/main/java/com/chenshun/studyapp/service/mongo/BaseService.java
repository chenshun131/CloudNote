package com.chenshun.studyapp.service.mongo;

import com.chenshun.studyapp.dao.mongo.IBaseDao;
import com.chenshun.studyapp.entity.mongo.PageModel;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * User: mew <p />
 * Time: 17/7/31 14:20  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public abstract class BaseService<T> {

    protected abstract IBaseDao<T> getDao();

    /**
     * 保存-实体
     *
     * @param entity
     */
    public void save(T entity) {
        getDao().save(entity);
    }

    /**
     * 修改实体
     *
     * @param entity
     */
    public void update(T entity) {
        getDao().update(entity);
    }

    /**
     * 删除实体[数组]
     *
     * @param ids
     */
    public void delete(Serializable... ids) {
        getDao().delete(ids);
    }

    /**
     * 根据ID查询
     *
     * @param id
     *         实体的主键ID
     */
    public T find(Serializable id) {
        return getDao().find(id);
    }

    /**
     * 查询所有记录<br>
     * [不分页]
     *
     * @return 结果集合
     */
    public List<T> findAll() {
        return getDao().findAll();
    }

    /**
     * 查询所有记录并排序<br>
     * [不分页]
     *
     * @return 结果集合
     */
    public List<T> findAll(String order) {
        return getDao().findAll(order);
    }

    /**
     * 根据单一参数查询记录<br>
     * [不分页]
     *
     * @param propName
     *         属性名称，对应实体类字段名称
     * @param propValue
     *         属性值
     * @return 结果列表 或 null
     */
    public List<T> findByProp(String propName, Object propValue) {
        return getDao().findByProp(propName, propValue);
    }

    /**
     * 根据单一参数查询记录并排序<br>
     * [不分页]
     *
     * @param propName
     *         属性名称，对应实体类字段名
     * @param propValue
     *         属性值
     * @param order
     *         排序字段（如：id 或 id desc）
     * @return 结果集合 或 null
     */
    public List<T> findByProp(String propName, Object propValue, String order) {
        return getDao().findByProp(propName, propValue, order);
    }

    /**
     * 根据多个参数查询记录<br>
     * [不分页]
     *
     * @param propName
     *         参数数组
     * @param propValue
     *         参数值数组
     * @return 结果集合 或 null
     */
    public List<T> findByProps(String[] propName, Object[] propValue) {
        return getDao().findByProps(propName, propValue);
    }

    /**
     * 根据多个参数查询记录 并排序<br>
     * [不分页]
     *
     * @param propName
     *         参数数组
     * @param propValue
     *         参数值数组
     * @param order
     *         排序字段
     * @return 结果集合 或 null
     */
    public List<T> findByProps(String[] propName, Object[] propValue, String order) {
        return getDao().findByProps(propName, propValue, order);
    }

    /**
     * 根据单一参数查询唯一结果<br>
     * [HQL]
     *
     * @param propName
     *         属性名称，对应实体类字段名
     * @param propValue
     *         属性值
     * @return 唯一结果 或 null
     */
    public T uniqueByProp(String propName, Object propValue) {
        return getDao().uniqueByProp(propName, propValue);
    }

    /**
     * 根据多个参数查询唯一结果<br>
     * [HQL]
     *
     * @param propName
     *         参数数组
     * @param propValue
     *         参数值数组
     * @return 唯一结果 或 null
     */
    public T uniqueByProps(String[] propName, Object[] propValue) {
        return getDao().uniqueByProps(propName, propValue);
    }

    /**
     * 分页查询所有结果集合<br>
     * [分页]
     *
     * @param pageNo
     *         当前页码
     * @param pageSize
     *         页容量
     * @return 分页模型对象（不会为null）
     */
    public PageModel<T> pageAll(int pageNo, int pageSize) {
        return getDao().pageAll(pageNo, pageSize);
    }

    /**
     * 分页查询所有结果集合 并排序<br>
     * [分页]
     *
     * @param pageNo
     *         当前页码
     * @param pageSize
     *         页容量
     * @param order
     *         排序字段
     * @return 分页模型对象（不会为null）
     */
    public PageModel<T> pageAll(int pageNo, int pageSize, String order) {
        return getDao().pageAll(pageNo, pageSize, order);
    }

    /**
     * 根据参数分页查询结果集合<br>
     * [分页]
     *
     * @param pageNo
     *         当前页码
     * @param pageSize
     *         页容量
     * @param param
     *         参数
     * @param value
     *         参数值
     * @return 分页模型对象（不会为null）
     */
    public PageModel<T> pageByProp(int pageNo, int pageSize, String param, Object value) {
        return getDao().pageByProp(pageNo, pageSize, param, value);
    }

    /**
     * 根据参数分页查询结果集合并排序<br>
     * [分页]
     *
     * @param pageNo
     *         当前页码
     * @param pageSize
     *         页容量
     * @param param
     *         参数
     * @param value
     *         参数值
     * @param order
     *         排序字段
     * @return 分页模型对象（不会为null）
     */
    public PageModel<T> pageByProp(int pageNo, int pageSize, String param, Object value, String order) {
        return getDao().pageByProp(pageNo, pageSize, param, value, order);
    }

    /**
     * 根据参数分页查询结果集合<br>
     * [分页]
     *
     * @param pageNo
     *         当前页码
     * @param pageSize
     *         页容量
     * @param params
     *         参数数组
     * @param values
     *         参数值数组
     * @return 分页模型对象（不会为null）
     */
    public PageModel<T> pageByProps(int pageNo, int pageSize, String[] params, Object[] values) {
        return getDao().pageByProps(pageNo, pageSize, params, values);
    }

    /**
     * 根据参数分页查询结果集合 并排序<br>
     * [分页]
     *
     * @param pageNo
     *         当前页码
     * @param pageSize
     *         页容量
     * @param params
     *         参数数组
     * @param values
     *         参数值数组
     * @param order
     *         排序字段
     * @return 分页模型对象（不会为null）
     */
    public PageModel<T> pageByProps(int pageNo, int pageSize, String[] params, Object[] values, String order) {
        return getDao().pageByProps(pageNo, pageSize, params, values, order);
    }

    /**
     * 根据条件查询总记录数
     *
     * @param params
     *         参数数组
     * @param values
     *         参数值数组
     * @return 总记录数
     */
    public int countByCondition(String[] params, Object[] values) {
        return getDao().countByCondition(params, values);
    }

    /**
     * 获取点附近的其点信息
     *
     * @param point
     * @param maxDistance
     *         kilometer unit
     * @param propName
     *         属性名称，对应实体类字段名称
     * @param propValue
     *         属性值
     * @return
     */
    public GeoResults<T> geoNear(@NotNull Point point, double maxDistance, String propName, Object propValue) {
        return getDao().geoNear(point, maxDistance, propName, propValue);
    }

    /**
     * 获取点附近的其点信息
     *
     * @param point
     * @param maxDistance
     *         kilometer unit
     * @param propName
     *         属性名称，对应实体类字段名称
     * @param propValue
     *         属性值
     * @param order
     *         排序字段，例如：id或id asc、或id asc,name desc<br>
     *         为空则不排序，不指定排序方式则默认升序排序
     * @return
     */
    public GeoResults<T> geoNear(@NotNull Point point, double maxDistance, String propName, Object propValue,
                                 String order) {
        return getDao().geoNear(point, maxDistance, propName, propValue, order);
    }

    /**
     * 获取点附近的其点信息
     *
     * @param point
     * @param maxDistance
     *         kilometer unit
     * @param propName
     *         参数数组
     * @param propValue
     *         参数值数组
     * @return
     */
    public GeoResults<T> geoNear(@NotNull Point point, double maxDistance, String[] propName, Object[] propValue) {
        return getDao().geoNear(point, maxDistance, propName, propValue);
    }

    /**
     * 获取点附近的其点信息
     *
     * @param point
     * @param maxDistance
     *         kilometer unit
     * @param propName
     *         参数数组
     * @param propValue
     *         参数值数组
     * @param order
     *         排序字段，例如：id或id asc、或id asc,name desc<br>
     *         为空则不排序，不指定排序方式则默认升序排序
     * @return
     */
    public GeoResults<T> geoNear(@NotNull Point point, double maxDistance, String[] propName, Object[] propValue,
                                 String order) {
        return getDao().geoNear(point, maxDistance, propName, propValue, order);
    }

    /**
     * 获取圆圈内的点
     *
     * @param key
     * @param circle
     * @return
     */
    public List<T> getCircleInnerPoint(String key, Circle circle) {
        return getDao().getCircleInnerPoint(key, circle);
    }

}
