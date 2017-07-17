package com.chenshun.utils.mapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * User: mew <p />
 * Time: 17/6/16 16:21  <p />
 * Version: V1.0  <p />
 * Description: 简单封装fastJSON,完成对象与JSON之间序列化和反序列化的Mapper实现: <br/>
 * 1. 普通序列化 <br/>
 * 2. 带属性过滤序列化 <br/>
 * 3. 带日期格式化 <br/>
 * 4. 通过制定序列化的特性进行定制序列化 <br/>
 * 5. 通过注解方式,自定义序列化 <br/>
 * 6. 普通反序列化 <br/>
 * 7. 指定参数类型反序列化 <br/>
 * 8. MVC集成 <br/>
 * 集成SpringMVC替换默认的jackson,在配置文件中加入： <br/>
 * <pre>
 * <xmp>
 * <mvc:annotation-driven>
 *     <mvc:message-converters register-defaults="true">
 *         <bean class="com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter">
 *             <property name="supportedMediaTypes" value="application/json" />
 *             <property name="features">
 *                 <array>
 *                     <value>WriteMapNullValue</value>
 *                     <value>QuoteFieldNames</value>
 *                     <value>WriteDateUseDateFormat</value>
 *                     <value>PrettyFormat</value>
 *                 </array>
 *             </property>
 *         </bean>
 *     </mvc:message-converters>
 * </mvc:annotation-driven>
 * </xmp>
 * <pre/>
 *
 * 具体使用如:
 * <pre>
 * &#64;ResponseBody
 * public Resource editForm(@RequestParam String id,HttpServletResponse response) {
 *     return resourceService.getResource(id);
 * }
 * <pre/>
 * 其次:在SERVLET中或者其他MVC框架中集成使用fastJSON,可以通过调用cosmos底层框架工具类如下 :
 * 自动序列化(一般情况):ServletUtils.renderJson(response,Object)
 * 手动序列化(需要特殊处理):ServletUtils.renderJson(response,JsonString)
 * 通过以上两种方式都可以完成JSON序列化和视图的渲染
 *
 * other 如不满足应用需求,请调用fastJSON原生API进行自定义扩展,详细参见官方文档 http://code.alibabatech.com/wiki/display/FastJSON/Tutorial
 */
public class JsonMapper {

    private static final Logger logger = LoggerFactory.getLogger(JsonMapper.class);

    private static String jsonString = StringUtils.EMPTY;

    private Set<SerializerFeature> features;

    public JsonMapper() {
        this(new SerializerFeature[0]);
    }

    public JsonMapper(SerializerFeature... serializerFeature) {
        if (ArrayUtils.isNotEmpty(serializerFeature)) {
            features = Sets.newHashSet();
            CollectionUtils.addAll(features, serializerFeature);
        }
    }

    /**
     * 默认的序列化特性
     */
    public static JsonMapper defaultMapper() {
        //SerializerFeature.PrettyFormat 使用美化特性会导致属性过滤失效
        return new JsonMapper(SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.QuoteFieldNames);
    }

    public static JsonMapper customMapper(SerializerFeature... serializerFeature) {
        return new JsonMapper(serializerFeature);
    }

    /**
     * 序列化
     */
    public String toJson(Object object) {
        return toJson(object, null, true);
    }

    public String toJson(Object object, String dateFormat, boolean isInclude, String... propertyName) {
        SerializeWriter out = new SerializeWriter();
        try {
            JSONSerializer serializer = new JSONSerializer(out);
            if (features != null && !features.isEmpty()) {
                for (SerializerFeature feature : features) {
                    serializer.config(feature, true);
                }
            }
            if (StringUtils.isNotBlank(dateFormat)) {
                serializer.setDateFormat(dateFormat);
            }
            if (ArrayUtils.isNotEmpty(propertyName)) {
                SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
                if (isInclude) {
                    CollectionUtils.addAll(filter.getIncludes(), propertyName);
                } else {
                    CollectionUtils.addAll(filter.getExcludes(), propertyName);
                }
                serializer.getPropertyPreFilters().add(filter);
            }
            serializer.write(object);
            jsonString = out.toString();
            logger.info(" serialize object {} to jsonString:{}", object, jsonString);
        } finally {
            out.close();
        }
        return jsonString;
    }

    public String toJson(Object object, String dateFormat) {
        return toJson(object, dateFormat, true);
    }

    /**
     * 反序列化
     */
    public Object parse(String jsonString) {
        return JSON.parse(jsonString);
    }

    public <T> T parse(String jsonString, Class<T> clazz) {
        return JSON.parseObject(jsonString, clazz);
    }

    public <T> List<T> parseArray(String jsonString, Class<T> clazz) {
        return JSON.parseArray(jsonString, clazz);
    }

}
