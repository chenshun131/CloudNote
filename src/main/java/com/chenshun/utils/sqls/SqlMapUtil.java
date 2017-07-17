package com.chenshun.utils.sqls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * User: mew <p />
 * Time: 17/6/19 09:02  <p />
 * Version: V1.0  <p />
 * Description: SQL解析工厂类 <p />
 */
public class SqlMapUtil {

    private static final Logger logger = LoggerFactory.getLogger(SqlMapUtil.class);

    private static final SqlMapUtil _instance = new SqlMapUtil();

    private SqlMap _sqlMap;

    private SqlMapUtil() {
        try {
            _sqlMap = new SqlMap();
        } catch (Exception e) {
            logger.error("初始化Sql映射模板出错", e);
        }
    }

    public static String appendCriteria(String sql, String criteria) {
        return _instance._sqlMap.appendCriteria(sql, criteria);
    }

    public static String get(String id) {
        return _instance._sqlMap.get(id);
    }

    public static String get(String id, Map<String, Object> models) {
        return _instance._sqlMap.get(id, models);
    }

}
