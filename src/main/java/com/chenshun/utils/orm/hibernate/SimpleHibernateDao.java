package com.chenshun.utils.orm.hibernate;

import com.chenshun.utils.orm.MatchType;
import com.chenshun.utils.reflection.ReflectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * User: mew <p />
 * Time: 17/6/16 17:41  <p />
 * Version: V1.0  <p />
 * Description: 封装Hibernate原生API的DAO泛型基类. <br/>
 * 可在Service层直接使用, 也可以扩展泛型DAO子类使用, 见两个构造函数的注释. <br/>
 * 参考Spring2.5自带的Petlinc例子, 取消了HibernateTemplate, 直接使用Hibernate原生API. <p />
 *
 * @param <T>
 *         DAO操作的对象类型
 * @param <PK>
 *         主键类型
 */
@SuppressWarnings("unchecked")
public class SimpleHibernateDao<T, PK extends Serializable> {

    public final Logger logger = LoggerFactory.getLogger(getClass());

    public SessionFactory sessionFactory;

    public final Class<T> entityClass;

    public Set<String> aliases = new HashSet<>();

    /**
     * 用于Dao层子类使用的构造函数.
     * 通过子类的泛型定义取得对象类型Class.
     * eg.
     * public class UserDao extends SimpleHibernateDao<User, Long>
     */
    public SimpleHibernateDao() {
        this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
    }

    /**
     * 用于用于省略Dao层, 在Service层直接使用通用SimpleHibernateDao的构造函数.
     * 在构造函数中定义对象类型Class.
     * eg.
     * SimpleHibernateDao<User, Long> userDao = new SimpleHibernateDao<User, Long>(sessionFactory, User.class);
     */
    public SimpleHibernateDao(SessionFactory sessionFactory, Class<T> entityClass) {
        this.sessionFactory = sessionFactory;
        this.entityClass = entityClass;
    }

    /**
     * 取得sessionFactory.
     */
    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    /**
     * 保存新增或修改的对象.
     */

    public void save(T entity) {
        Assert.notNull(entity, "entity不能为空");
        getSession().saveOrUpdate(entity);
        this.logger.debug("save entity: {}", entity);
    }

    public void saveAll(Collection<T> entities) {
        Assert.notNull(entities, "entities不能为空");
        Assert.notEmpty(entities, "entities不能是空集合");
        for (T entity : entities)
            this.save(entity);
    }

    /**
     * 删除对象.
     *
     * @param entity
     *         对象必须是session中的对象或含id属性的transient对象.
     */
    public void delete(T entity) {
        Assert.notNull(entity, "entity不能为空");
        getSession().delete(entity);
        this.logger.debug("delete entity: {}", entity);
    }

    /**
     * 按id删除对象.
     */

    public void delete(PK id) {
        Assert.notNull(id, "id不能为空");
        delete(get(id));
        this.logger.debug("delete entity {},id is {}", this.entityClass.getSimpleName(), id);
    }

    public void deleteAll(Collection<T> entities) {
        Assert.notNull(entities, "entities不能为空");
        Assert.notEmpty(entities, "entities不能是空集合");
        for (T entity : entities)
            delete(entity);
    }

    public void deleteAll(PK[] ids) {
        Assert.notNull(ids, "ids不能为空");
        Assert.notEmpty(ids, "ids不能是空数组");
        for (PK id : ids)
            delete(id);
    }

    /**
     * 执行HQL进行批量修改/删除操作.
     *
     * @param values
     *         数量可变的参数,按顺序绑定.
     * @return 更新记录数.
     */

    public int batchExecute(String hql, Object... values) {
        return createQuery(hql, values).executeUpdate();
    }

    /**
     * 执行HQL进行批量修改/删除操作.
     *
     * @param values
     *         命名参数,按名称绑定.
     * @return 更新记录数.
     */
    public int batchExecute(String hql, Map<String, ?> values) {
        return createQuery(hql, values).executeUpdate();
    }

    /**
     * 按id获取对象.
     */
    public T get(PK id) {
        Assert.notNull(id, "id不能为空");
        return (T) getSession().load(this.entityClass, id);
    }

    /**
     * 按id列表获取对象列表.
     */
    public List<T> get(Collection<PK> ids) {
        return createCriteria(new Criterion[]{Restrictions.in(getIdName(), ids)}).list();
    }

    /**
     * 获取全部对象.
     */
    public List<T> getAll() {
        return createCriteria(new Criterion[0]).list();
    }

    /**
     * 获取全部对象, 支持按属性行序.
     */
    public List<T> getAll(String orderByProperty, boolean isAsc) {
        Criteria c = createCriteria(new Criterion[0]);
        if (isAsc)
            c.addOrder(Order.asc(orderByProperty));
        else {
            c.addOrder(Order.desc(orderByProperty));
        }
        return c.list();
    }

    /**
     * 按属性查找对象列表, 匹配方式为自选方式
     */
    public List<T> findByProperty(String propertyName, Object value, MatchType matchType) {
        Criterion criterion = buildCriterion(propertyName, value, matchType);

        Criteria criteria = createCriteria(new Criterion[]{criterion});

        createAlias(criteria, propertyName);

        return criteria.list();
    }

    /**
     * 按属性查找对象列表, 匹配方式为相等.
     */

    public List<T> findByProperty(String propertyName, Object value) {
        return findByProperty(propertyName, value, MatchType.EQ);
    }

    /**
     * 按属性查找唯一对象, 匹配方式为相等.
     */
    public T findUniqueByProperty(String propertyName, Object value) {
        Criterion criterion = buildCriterion(propertyName, value, MatchType.EQ);
        Criteria criteria = createCriteria(criterion);
        createAlias(criteria, propertyName);
        return (T) criteria.uniqueResult();
    }

    /**
     * 按HQL查询对象列表.
     *
     * @param values
     *         数量可变的参数,按顺序绑定.
     */
    public <X> List<X> find(String hql, Object... values) {
        return createQuery(hql, values).list();
    }

    /**
     * 按HQL查询对象列表.
     *
     * @param values
     *         命名参数,按名称绑定.
     */
    public <X> List<X> find(String hql, Map<String, ?> values) {
        return createQuery(hql, values).list();
    }

    /**
     * 按Criteria查询对象列表.
     *
     * @param criterions
     *         数量可变的Criterion.
     */
    public List<T> find(final Criterion... criterions) {
        return createCriteria(criterions).list();
    }

    /**
     * 按Criteria查询唯一对象.
     *
     * @param criterions
     *         数量可变的Criterion.
     */
    public T findUnique(final Criterion... criterions) {
        return (T) createCriteria(criterions).uniqueResult();
    }

    /**
     * 按HQL查询唯一对象.
     *
     * @param values
     *         数量可变的参数,按顺序绑定.
     */

    public <X> X findUnique(String hql, Object... values) {
        return (X) createQuery(hql, values).uniqueResult();
    }

    /**
     * 按HQL查询唯一对象.
     *
     * @param values
     *         命名参数,按名称绑定.
     */

    public <X> X findUnique(String hql, Map<String, ?> values) {
        return (X) createQuery(hql, values).uniqueResult();
    }

    /**
     * 根据查询HQL与参数列表创建Query对象.
     * 与find()函数可进行更加灵活的操作.
     *
     * @param values
     *         数量可变的参数,按顺序绑定.
     */

    public Query createQuery(String queryString, Object... values) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    /**
     * 根据查询HQL与参数列表创建Query对象.
     * 与find()函数可进行更加灵活的操作.
     *
     * @param values
     *         命名参数,按名称绑定.
     */
    public Query createQuery(String queryString, Map<String, ?> values) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            query.setProperties(values);
        }
        return query;
    }

    /**
     * 根据Criterion条件创建Criteria.
     * 与find()函数可进行更加灵活的操作.
     *
     * @param criterions
     *         数量可变的Criterion.
     */
    public Criteria createCriteria(Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(this.entityClass);
        return createCriteria(criteria, criterions);
    }

    public Criteria createCriteria(Criteria criteria, Criterion... criterions) {
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        return criteria;
    }

    public void initProxyObject(Object proxy) {
        Hibernate.initialize(proxy);
    }

    /**
     * Flush当前Session.
     */
    public void flush() {
        getSession().flush();
    }

    /**
     * 为Query添加distinct transformer.
     * 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
     */
    public Query distinct(Query query) {
        query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return query;
    }

    /**
     * 为Criteria添加distinct transformer.
     * 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
     */
    public Criteria distinct(Criteria criteria) {
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria;
    }

    /**
     * 取得对象的主键名.
     */
    public String getIdName() {
        ClassMetadata meta = getSessionFactory().getClassMetadata(this.entityClass);
        return meta.getIdentifierPropertyName();
    }

    /**
     * 判断对象的属性值在数据库内是否唯一.
     * <p>
     * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
     */
    public boolean isPropertyUnique(String propertyName, Object newValue, Object oldValue) {
        if ((newValue == null) || (newValue.equals(oldValue))) {
            return true;
        }
        Object object = findUniqueByProperty(propertyName, newValue);
        return object == null;
    }

    /**
     * 创建别名
     * hiberante 4 JoinType：
     * JoinType.LEFT_OUTER_JOIN
     * JoinType.RIGHT_OUTER_JOIN
     * JoinType.INNER_JOIN
     * JoinType.FULL_JOIN
     * hiberate 3 JoinType:
     * CriteriaSpecification.LEFT_JOIN
     * CriteriaSpecification.INNER_JOIN
     * CriteriaSpecification.FULL_JOIN
     */
    public Criteria createAlias(Criteria criteria, String propertyName, int joinType) {
        if (propertyName.contains(".")) {
            String alias = StringUtils.substringBefore(propertyName, ".");
            if (!this.aliases.contains(alias)) {
                criteria.createAlias(alias, alias, joinType);
                this.aliases.add(alias);
            }
        }
        return criteria;
    }

    /**
     * 以默认方式创建别名
     * 默认为左外连接
     */
    public Criteria createAlias(Criteria criteria, String propertyName) {
        return createAlias(criteria, propertyName, CriteriaSpecification.LEFT_JOIN);
    }

    public Criterion buildCriterion(String propertyName, Object propertyValue, MatchType matchType) {
        Assert.hasText(propertyName, "propertyName不能为空");
        Criterion criterion = null;
        switch (matchType) {
            case EQ:
                criterion = Restrictions.eq(propertyName, propertyValue);
                break;
            case LIKE:
                if (propertyValue instanceof String) {
                    criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.ANYWHERE);
                } else {
                    logger.warn("属性{}使用LIKE查询，为非字符型，实际类型为：{}，自动使用EQ查询", propertyName, propertyValue.getClass().getName());
                    criterion = Restrictions.eq(propertyName, propertyValue);
                }
                break;
            case LIKEE:
                if (propertyValue instanceof String) {
                    criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.END);
                } else {
                    logger.warn("属性{}使用LIKEE查询，为非字符型，实际类型为：{}，自动使用EQ查询", propertyName, propertyValue.getClass().getName());
                    criterion = Restrictions.eq(propertyName, propertyValue);
                }
                break;
            case LIKES:
                if (propertyValue instanceof String) {
                    criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.START);
                } else {
                    logger.warn("属性{}使用LIKES查询，为非字符型，实际类型为：{}，自动使用EQ查询", propertyName, propertyValue.getClass().getName());
                    criterion = Restrictions.eq(propertyName, propertyValue);
                }
                break;
            case NE:
                criterion = Restrictions.ne(propertyName, propertyValue);
                break;
            case LE:
                criterion = Restrictions.le(propertyName, propertyValue);
                break;
            case LT:
                criterion = Restrictions.lt(propertyName, propertyValue);
                break;
            case GE:
                criterion = Restrictions.ge(propertyName, propertyValue);
                break;
            case GT:
                criterion = Restrictions.gt(propertyName, propertyValue);
                break;
            case ISNULL:
                criterion = Restrictions.isNull(propertyName);
                break;
            case NOTNULL:
                criterion = Restrictions.isNotNull(propertyName);
                break;
            case IN:
                break;
            default:
                break;
        }
        return criterion;
    }

}
