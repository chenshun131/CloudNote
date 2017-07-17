package com.chenshun.utils.orm.hibernate;

import com.chenshun.utils.orm.Page;
import com.chenshun.utils.orm.PropertyFilter;
import com.chenshun.utils.reflection.ReflectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.impl.CriteriaImpl.OrderEntry;
import org.hibernate.transform.ResultTransformer;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * User: mew <p />
 * Time: 17/6/16 17:40  <p />
 * Version: V1.0  <p />
 * Description: 扩展Hibernat DAO泛型基类. <br/>
 * <p>
 * 扩展功能包括分页查询,按属性过滤条件列表查询. <br/>
 * 可在Service层直接使用,也可以扩展泛型DAO子类使用,见两个构造函数的注释. <p />
 *
 * @param <T>
 *         DAO操作的对象类型
 * @param <PK>
 *         主键类型
 */
public class BaseDao<T, PK extends Serializable> extends SimpleHibernateDao<T, PK> {

    private static final String CRITERIA_STRING = "criteria";

    private static final String CRITERIIONS_STRING = "criterions";

    /**
     * 用于Dao层子类的构造函数.
     * 通过子类的泛型定义取得对象类型Class.
     * eg.
     * public class UserDao extends HibernateDao<User, Long>{
     * }
     */
    public BaseDao() {
    }

    /**
     * 用于省略Dao层, Service层直接使用通用HibernateDao的构造函数.
     * 在构造函数中定义对象类型Class.
     * eg.
     * HibernateDao<User, Long> userDao = new HibernateDao<User, Long>(sessionFactory, User.class);
     */
    public BaseDao(SessionFactory sessionFactory, Class<T> entityClass) {
        super(sessionFactory, entityClass);
    }

    // -- 分页查询函数 --//

    /**
     * 分页获取全部对象.
     */
    public Page<T> getAll(Page<T> page) {
        return findPage(page, new Criterion[0]);
    }

    /**
     * 按HQL分页查询.
     *
     * @param page
     *         分页参数.
     * @param hql
     *         hql语句.
     * @param values
     *         数量可变的查询参数,按顺序绑定.
     * @return 分页查询结果, 附带结果列表及所有查询输入参数.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Page<T> findPage(Page<T> page, String hql, Object... values) {
        Assert.notNull(page, "page不能为空");

        Query q = createQuery(hql, values);

        if (page.isAutoCount()) {
            long totalCount = countHqlResult(hql, values);
            page.setTotalCount(totalCount);
        }

        setPageParameterToQuery(q, page);

        List result = q.list();
        page.setResult(result);
        return page;
    }

    /**
     * 按HQL分页查询.
     *
     * @param page
     *         分页参数. 注意不支持其中的orderBy参数.
     * @param hql
     *         hql语句.
     * @param values
     *         命名参数,按名称绑定.
     * @return 分页查询结果, 附带结果列表及所有查询输入参数.
     */
    @SuppressWarnings("unchecked")
    public Page<T> findPage(Page<T> page, String hql, Map<String, ?> values) {
        Assert.notNull(page, "page不能为空");

        Query q = createQuery(hql, values);

        if (page.isAutoCount()) {
            long totalCount = countHqlResult(hql, values);
            page.setTotalCount(totalCount);
        }

        setPageParameterToQuery(q, page);

        @SuppressWarnings("rawtypes")
        List result = q.list();
        page.setResult(result);
        return page;
    }

    /**
     * 按Criteria分页查询.
     *
     * @param page
     *         分页参数.
     * @param criterions
     *         数量可变的Criterion.
     * @return 分页查询结果.附带结果列表及所有查询输入参数.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Page<T> findPage(Page<T> page, Criterion... criterions) {
        Assert.notNull(page, "page不能为空");

        Criteria c = createCriteria(criterions);

        if (page.isAutoCount()) {
            long totalCount = countCriteriaResult(c);
            page.setTotalCount(totalCount);
        }

        setPageParameterToCriteria(c, page);

        List result = c.list();
        page.setResult(result);
        return page;
    }

    /**
     * 按属性过滤条件列表分页查找对象.
     */
    public Page<T> findPage(Page<T> page, List<PropertyFilter> filters) {
        Assert.notNull(page, "page不能为空");
        Criteria c = buildCriteriaByPropertyFilter(filters);
        return findPage(page, c);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Page<T> findPage(Page<T> page, Criteria c) {
        Assert.notNull(page, "page不能为空");
        if (page.isAutoCount()) {
            long totalCount = countCriteriaResult(c);
            page.setTotalCount(totalCount);
        }
        setPageParameterToCriteria(c, page);
        this.aliases = new HashSet<String>();
        List result = c.list();
        page.setResult(result);
        return page;
    }

    /**
     * 设置分页参数到Query对象,辅助函数.
     */

    private Query setPageParameterToQuery(Query q, Page<T> page) {
        Assert.isTrue(page.getPageSize() > 0, "Page Size must larger than zero");

        q.setFirstResult(page.getFirst() - 1);
        q.setMaxResults(page.getPageSize());
        return q;
    }

    /**
     * 设置分页参数到Criteria对象,辅助函数.
     */
    private Criteria setPageParameterToCriteria(Criteria c, Page<T> page) {
        Assert.isTrue(page.getPageSize() > 0, "Page Size must larger than zero");

        c.setFirstResult(page.getFirst() - 1);
        c.setMaxResults(page.getPageSize());

        if (page.isOrderBySetted()) {
            String[] orderByArray = StringUtils.split(page.getOrderField(), ',');
            String[] orderArray = StringUtils.split(page.getOrderType(), ',');

            Assert.isTrue(orderByArray.length == orderArray.length,
                    "分页多重排序参数中,排序字段与排序方向的个数不相等");

            for (int i = 0; i < orderByArray.length; i++) {
                if (orderByArray[i].contains(".")) {
                    String alias = StringUtils.substringBefore(orderByArray[i], ".");
                    if (!this.aliases.contains(alias)) {
                        c.createAlias(alias, alias);
                        this.aliases.add(alias);
                    }
                }
                if ("asc".equals(orderArray[i])) {
                    c.addOrder(Order.asc(orderByArray[i]));
                } else {
                    c.addOrder(Order.desc(orderByArray[i]));
                }
            }
        }
        return c;
    }

    /**
     * 执行count查询获得本次Hql查询所能获得的对象总数.
     * <p>
     * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
     */
    public long countHqlResult(String hql, Object... values) {
        String countHql = prepareCountHql(hql);
        try {
            Long count = (Long) findUnique(countHql, values);
            return count.longValue();
        } catch (Exception e) {
            throw new RuntimeException("hql can't be auto count, hql is:{}" + countHql, e);
        }
    }

    /**
     * 执行count查询获得本次Hql查询所能获得的对象总数.
     * <p>
     * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
     */

    public long countHqlResult(String hql, Map<String, ?> values) {
        String countHql = prepareCountHql(hql);
        try {
            Long count = (Long) findUnique(countHql, values);
            return count.longValue();
        } catch (Exception e) {
            throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
        }
    }

    public String prepareCountHql(String orgHql) {
        String fromHql = orgHql;

        fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
        fromHql = StringUtils.substringBefore(fromHql, "order by");

        String countHql = "select count(*) " + fromHql;
        return countHql;
    }

    /**
     * 执行count查询获得本次Criteria查询所能获得的对象总数.
     */

    @SuppressWarnings({"unchecked", "rawtypes"})
    public long countCriteriaResult(Criteria c) {
        CriteriaImpl impl = (CriteriaImpl) c;

        Projection projection = impl.getProjection();
        ResultTransformer transformer = impl.getResultTransformer();

        List<CriteriaImpl.OrderEntry> orderEntries = null;
        try {
            orderEntries = (List<OrderEntry>) ReflectionUtils.getFieldValue(impl, "orderEntries");

            ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList());
        } catch (Exception e) {
            this.logger.error("不可能抛出的异常:{}", e.getMessage());
        }

        Long totalCountObject = (Long) c.setProjection(Projections.rowCount()).uniqueResult();
        long totalCount = totalCountObject != null ? totalCountObject.longValue() : 0L;

        c.setProjection(projection);

        if (projection == null) {
            c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }
        if (transformer != null) {
            c.setResultTransformer(transformer);
        }
        try {
            ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
        } catch (Exception e) {
            this.logger.error("不可能抛出的异常:{}", e.getMessage());
        }

        return totalCount;
    }

    @SuppressWarnings("unchecked")
    /**
     * 按属性过滤条件列表查找对象列表.
     */
    public List<T> find(List<PropertyFilter> filters) {
        Criteria criteria = buildCriteriaByPropertyFilter(filters);
        return criteria.list();
    }

    /**
     * 按属性条件列表创建Criteria对象,辅助函数.
     */

    public Criteria buildCriteriaByPropertyFilter(List<PropertyFilter> filters) {
        return (Criteria) buildByPropertyFilter(filters).get(CRITERIA_STRING);
    }

    /**
     * 按属性条件列表创建Criterion数组,辅助函数.
     */

    public Criterion[] buildCriterionsByPropertyFilter(List<PropertyFilter> filters) {
        return (Criterion[]) buildByPropertyFilter(filters).get(CRITERIIONS_STRING);
    }

    private Map<String, Object> buildByPropertyFilter(List<PropertyFilter> filters) {
        Map<String, Object> map = new HashMap<>();
        Criteria criteria = getSession().createCriteria(this.entityClass);
        List<Criterion> criterionList = new ArrayList<>();

        for (PropertyFilter filter : filters) {
            if (!filter.hasMultiProperties()) {
                Criterion criterion = buildCriterion(filter.getPropertyName(), filter.getMatchValue(),
                        filter.getMatchType());
                criterionList.add(criterion);
                createAlias(criteria, filter.getPropertyName());
            } else {
                Disjunction disjunction = Restrictions.disjunction();
                for (String param : filter.getPropertyNames()) {
                    Criterion criterion = buildCriterion(param, filter.getMatchValue(), filter.getMatchType());
                    disjunction.add(criterion);
                    createAlias(criteria, param);
                }
                criterionList.add(disjunction);
            }
        }
        for (Criterion criterion : criterionList) {
            criteria.add(criterion);
        }
        // --放入criteria、criterions数组
        map.put(CRITERIA_STRING, criteria);
        map.put(CRITERIIONS_STRING, criterionList.toArray(new Criterion[criterionList.size()]));
        return map;
    }

}
