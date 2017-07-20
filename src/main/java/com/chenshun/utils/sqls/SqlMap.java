package com.chenshun.utils.sqls;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mew <p />
 * Time: 17/6/19 09:02  <p />
 * Version: V1.0  <p />
 * Description: SQL语句映射工具类 <p />
 */
public class SqlMap {

    private static final Logger logger = LoggerFactory.getLogger(SqlMap.class);

    private final Map<String, SQLBean> _sqlPool;

    private static final String GROUP_BY_CLAUSE = " GROUP BY ";

    private static final String ORDER_BY_CLAUSE = " ORDER BY ";

    private static final String STRING_SPACE = " ";

    private static final String DEFAULT_SQLS_CONFIG = "sqls/default.xml";

    private final SAXReader saxReader = new SAXReader();

    private Configuration configuration = null;

    private StringTemplateLoader stringTemplateLoader = null;

    /**
     * @description SQL模板
     */
    public enum TemplateType {
        /** simple */
        SIMPLE("simple"),
        /** freemaker */
        FREEMAKER("freemaker");

        private final String type;

        TemplateType(String type) {
            this.type = type;
        }

        public String getValue() {
            return type;
        }

    }

    public SqlMap() throws SQLException {
        _sqlPool = new HashMap<>();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            configuration = new Configuration(Configuration.VERSION_2_3_0);
            stringTemplateLoader = new StringTemplateLoader();
            configuration.setDefaultEncoding("UTF-8");

            String[] configs = getConfigs();
            for (String _config : configs) {
                read(classLoader, _config);
            }
            configuration.setTemplateLoader(stringTemplateLoader);
        } catch (Exception e) {
            logger.error("", e);
        }

    }

    protected String[] getConfigs() {
        return new String[]{DEFAULT_SQLS_CONFIG};
    }

    /**
     * eg.
     * appendCriteria("select * from user order by name desc","where id>1")
     * result:select * from user where id>1 order by name desc
     *
     * @param sql
     * @param criteria
     * @return String
     * @description 在SQL语句上附加条件(where 语句), 一般情况下Simple模板使用
     */
    public String appendCriteria(String sql, String criteria) {
        if (StringUtils.isBlank(criteria)) {
            return sql;
        }
        if (!criteria.startsWith(STRING_SPACE)) {
            criteria = STRING_SPACE.concat(criteria);
        }
        if (!criteria.endsWith(STRING_SPACE)) {
            criteria = criteria.concat(STRING_SPACE);
        }

        int pos = StringUtils.indexOfIgnoreCase(sql, GROUP_BY_CLAUSE);
        if (pos != -1) {
            return sql.substring(0, pos + 1).concat(criteria).concat(sql.substring(pos + 1));
        }
        pos = StringUtils.indexOfIgnoreCase(sql, ORDER_BY_CLAUSE);
        if (pos != -1) {
            return sql.substring(0, pos + 1).concat(criteria).concat(sql.substring(pos + 1));
        }
        return sql.concat(criteria);
    }

    public String get(String id) {
        SQLBean bean = _sqlPool.get(id);
        if (TemplateType.SIMPLE.getValue().equals(bean.getTempateType())) {
            String sql = _sqlPool.get(id).getContent();
            logger.info("After parse of SQL is:{}", sql);
            return sql;
        } else {
            throw new RuntimeException("SQL 模板类型不正确，只可以是simple类型");
        }
    }

    public String get(String id,
                      Map<String, Object> models) {
        try {
            Template template = configuration.getTemplate(id);
            StringWriter writer = new StringWriter();
            template.process(models, writer);
            String sql = writer.toString();
            logger.info("After parse of SQL is:{}", sql);
            return sql;
        } catch (TemplateException | IOException e) {
            throw new RuntimeException("Parse sql failed", e);
        }
    }

    protected void read(ClassLoader classLoader, String source) throws Exception {
        InputStream is = classLoader.getResourceAsStream(source);
        if (is == null) {
            return;
        }
        logger.info("Loading:{}", source);
        Document document = saxReader.read(is);
        Element rootElement = document.getRootElement();
        if (rootElement == null) {
            return;
        }
        for (Object sqlObj : rootElement.elements("sql")) {
            Element sqlElement = (Element) sqlObj;
            String file = sqlElement.attributeValue("file");

            if (StringUtils.isNotBlank(file)) {
                read(classLoader, file);
            } else {
                String id = sqlElement.attributeValue("id");
                String sqlType = sqlElement.attributeValue("sqlType");
                String tempateType = sqlElement.attributeValue("tempateType");

                if (TemplateType.SIMPLE.getValue().equals(tempateType) || TemplateType.FREEMAKER.getValue().equals
                        (tempateType)) {
                    String content = transform(sqlElement.getText());

                    SQLBean bean = new SQLBean();
                    bean.setTempateType(tempateType);
                    bean.setSqlType(sqlType);
                    bean.setContent(content);

                    if (TemplateType.FREEMAKER.getValue().equals(tempateType)) {
                        stringTemplateLoader.putTemplate(id, content);
                    }

                    _sqlPool.put(id, bean);
                } else {
                    logger.warn("{} 对应 tempateType 值 {} 不正确，可选值为：simple和freeMarker", id, sqlType);
                }
            }
        }
    }

    protected String transform(String sql) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new StringReader(sql));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line.trim());
                sb.append(STRING_SPACE);
            }
            bufferedReader.close();
        } catch (IOException ioe) {
            return sql;
        }
        return sb.toString();
    }

    public static class SQLBean {

        /**
         * 两种可选类型：simple和freeMarker
         */
        private String tempateType = TemplateType.SIMPLE.getValue(); // 默认为simple

        /**
         * 两种可选类型：SQL和HQL
         */
        private String sqlType = "SQL";

        private String content = "";

        public String getTempateType() {
            return tempateType;
        }

        public void setTempateType(String tempateType) {
            this.tempateType = tempateType;
        }

        public String getSqlType() {
            return sqlType;
        }

        public void setSqlType(String sqlType) {
            this.sqlType = sqlType;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

}

