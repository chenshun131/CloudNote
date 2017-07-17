package com.chenshun.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * User: chenshun131 <p />
 * Time: 17/7/11 21:42  <p />
 * Version: V1.0  <p />
 * Description: HTTP与SERVLET工具类 <p />
 */
public class ServletUtil {

    // -- Content Type 定义 --//
    public static final String TEXT_TYPE = "text/plain";

    public static final String JSON_TYPE = "application/json";

    public static final String XML_TYPE = "text/xml";

    public static final String HTML_TYPE = "text/html";

    public static final String JS_TYPE = "text/javascript";

    public static final String EXCEL_TYPE = "application/vnd.ms-excel";

    public static final String AUTHENTICATION_HEADER = "Authorization";

    // -- 常用数值定义 --//
    public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;

    // -- header 常量定义 --//
    private static final String HEADER_ENCODING = "encoding";

    private static final String HEADER_NOCACHE = "no-cache";

    private static final String DEFAULT_ENCODING = "UTF-8";

    private static final boolean DEFAULT_NOCACHE = true;

    /**
     * 设置客户端缓存过期时间 的Header.
     */
    public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
        // Http 1.0 header
        response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000);
        // Http 1.1 header
        response.setHeader("Cache-Control", "private, max-age=" + expiresSeconds);
    }

    /**
     * 设置禁止客户端缓存的Header.
     */
    public static void setDisableCacheHeader(HttpServletResponse response) {
        // Set to expire far in the past.
        response.setDateHeader("Expires", 1L);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
    }

    /**
     * 设置LastModified Header.
     */
    public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate) {
        response.setDateHeader("Last-Modified", lastModifiedDate);
    }

    /**
     * 设置Etag Header.
     */
    public static void setEtag(HttpServletResponse response, String etag) {
        response.setHeader("ETag", etag);
    }

    /**
     * 根据浏览器If-Modified-Since Header, 计算文件是否已被修改.
     * <p/>
     * 如果无修改, checkIfModify返回false ,设置304 not modify status.
     *
     * @param lastModified
     *         内容的最后修改时间.
     */
    public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response, long
            lastModified) {
        long ifModifiedSince = request.getDateHeader("If-Modified-Since");
        if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return false;
        }
        return true;
    }

    /**
     * 根据浏览器 If-None-Match Header, 计算Etag是否已无效.
     * <p/>
     * 如果Etag有效, checkIfNoneMatch返回false, 设置304 not modify status.
     *
     * @param etag
     *         内容的ETag
     */
    public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
        String headerValue = request.getHeader("If-None-Match");
        if (headerValue != null) {
            boolean conditionSatisfied = false;
            if (!"*".equals(headerValue)) {
                StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");
                while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
                    String currentToken = commaTokenizer.nextToken();
                    if (currentToken.trim().equals(etag)) {
                        conditionSatisfied = true;
                    }
                }
            } else {
                conditionSatisfied = true;
            }
            if (conditionSatisfied) {
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                response.setHeader("ETag", etag);
                return false;
            }
        }
        return true;
    }

    /**
     * 设置让浏览器弹出下载对话框的Header.
     *
     * @param fileName
     *         下载后的文件名.
     */
    public static void setFileDownloadHeader(HttpServletResponse response, String fileName) {
        try {
            // 中文文件名支持
            String encodedfileName = new String(fileName.getBytes(), "ISO8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对Http Basic验证的 Header进行编码.
     */
    public static String encodeHttpBasic(String userName, String password) {
        String encode = userName + ":" + password;
        return "Basic " + EncodesUtil.encodeBase64(encode.getBytes());
    }

    /**
     * 取得带相同前缀的Request Parameters.
     * <p/>
     * 返回的结果的Parameter名已去除前缀.
     */

    @SuppressWarnings("rawtypes")
    public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
        Assert.notNull(request, "Request must not be null");
        Enumeration paramNames = request.getParameterNames();
        Map<String, Object> params = new TreeMap<>();
        if (prefix == null) {
            prefix = "";
        }
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            if ("".equals(prefix) || paramName.startsWith(prefix)) {
                String unprefixed = paramName.substring(prefix.length());
                String[] values = request.getParameterValues(paramName);
                if (values == null || values.length == 0) {
                    // Do nothing, no values found at all.
                } else if (values.length > 1) {
                    params.put(unprefixed, values);
                } else {
                    params.put(unprefixed, values[0]);
                }
            }
        }
        return params;
    }

    /**
     * 绝对路径
     */

    public static String getRealPath(HttpServletRequest request) {
        return request.getSession().getServletContext().getRealPath("/");
    }

    /**
     * 根路径
     */
    public static String getRootPath(HttpServletRequest request) {
        return request.getContextPath();
    }

    // -- 绕过jsp/freemaker直接输出文本的函数 --//

    /**
     * 直接输出内容的简便函数.
     * <p/>
     * eg.
     * render("text/plain", "hello", "encoding:GBK");
     * render("text/plain", "hello", "no-cache:false");
     * render("text/plain", "hello", "encoding:GBK", "no-cache:false");
     *
     * @param headers
     *         可变的header数组, 目前接受的值为"encoding:"或"no-cache:", 默认值分别为UTF-8和true.
     */
    public static void render(final HttpServletResponse response, final String contentType, final String content,
                              final String... headers) {
        HttpServletResponse resp = initResponseHeader(response, contentType, headers);
        try {
            resp.getWriter().write(content);
            resp.getWriter().flush();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 直接输出文本.
     */
    public static void renderText(final HttpServletResponse response, final String text, final String... headers) {
        render(response, ServletUtil.TEXT_TYPE, text, headers);
    }

    /**
     * 直接输出HTML.
     */
    public static void renderHtml(final HttpServletResponse response, final String html, final String... headers) {
        render(response, ServletUtil.HTML_TYPE, html, headers);
    }

    /**
     * 直接输出XML.
     */
    public static void renderXml(final HttpServletResponse response, final String xml, final String... headers) {
        render(response, ServletUtil.XML_TYPE, xml, headers);
    }

    /**
     * 直接输出JSON.
     *
     * @param jsonString
     *         json字符串.
     */
    public static void renderJson(final HttpServletResponse response, final String jsonString, final String...
            headers) {
        render(response, ServletUtil.JSON_TYPE, jsonString, headers);
    }

    /**
     * 直接输出JSON,使用fastJson转换Java对象.
     *
     * @param data
     *         可以是List<POJO>, POJO[], POJO, 也可以Map名值对.
     */
    public static void renderJson(final HttpServletResponse response, final Object data, final String... headers) {
        String jsonString = JSON.toJSONString(data);
        render(response, ServletUtil.JSON_TYPE, jsonString, headers);
    }

    /**
     * 直接输出支持跨域Mashup的JSONP.
     *
     * @param callbackName
     *         callback函数名.
     * @param object
     *         Java对象,可以是List<POJO>, POJO[], POJO ,也可以Map名值对, 将被转化为json字符串.
     */
    public static void renderJsonp(final HttpServletResponse response, final String callbackName, final Object
            object, final String... headers) {
        String jsonString = JSON.toJSONString(object);
        // --组装jsonP--//
        String result = new StringBuilder().append(callbackName).append("(").append(jsonString).append(");").toString();

        // 渲染Content-Type为javascript的返回内容,输出结果为javascript语句, 如callback197("{html:'Hello World!!!'}");
        render(response, ServletUtil.JS_TYPE, result, headers);
    }

    /**
     * 分析并设置contentType与headers.
     */
    public static HttpServletResponse initResponseHeader(HttpServletResponse response, final String contentType,
                                                         final String... headers) {
        // 分析headers参数
        String encoding = DEFAULT_ENCODING;
        boolean noCache = DEFAULT_NOCACHE;
        for (String header : headers) {
            String headerName = org.apache.commons.lang.StringUtils.substringBefore(header, ":");
            String headerValue = org.apache.commons.lang.StringUtils.substringAfter(header, ":");

            if (org.apache.commons.lang.StringUtils.equalsIgnoreCase(headerName, HEADER_ENCODING)) {
                encoding = headerValue;
            } else if (org.apache.commons.lang.StringUtils.equalsIgnoreCase(headerName, HEADER_NOCACHE)) {
                noCache = Boolean.parseBoolean(headerValue);
            } else {
                throw new IllegalArgumentException(headerName + "不是一个合法的header类型");
            }
        }

        // 设置headers参数
        String fullContentType = contentType + ";charset=" + encoding;
        response.setContentType(fullContentType);
        if (noCache) {
            ServletUtil.setDisableCacheHeader(response);
        }
        return response;
    }

}
