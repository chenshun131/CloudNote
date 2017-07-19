package com.chenshun.studyapp.controller;

import com.alibaba.fastjson.JSON;
import com.chenshun.component.exception.ServiceException;
import com.chenshun.utils.EncodesUtil;
import com.chenshun.utils.web.rest.RestResultDTO;
import com.chenshun.utils.web.rest.StatusCode;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mew <p />
 * Time: 17/7/19 15:02  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class SupportAction {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String MEMBER_CACHE_KEY_PREFIX = "memberpin_";

    /** 通用的异常提示信息 */
    public static final String ERROR_MSG = "亲,出错了.攻城师们正在加紧处理...";

    protected void exceptionDeal(RestResultDTO restResultDTO, Exception ex, String apiName, String exTipPrefix) {
        restResultDTO.initStatus(StatusCode.INTERNAL_SERVER_ERROR);
        if (ex instanceof IllegalArgumentException | ex instanceof ServiceException) {
            restResultDTO.setMessage(ex.getMessage());
            logger.error(apiName + " -> " + exTipPrefix + ":" + ex.getMessage());
        } else {
            restResultDTO.setMessage(ERROR_MSG);
            logger.error(apiName + " -> " + exTipPrefix, ex);
        }
    }

    /**
     * 根据请求参数名获取指定的请求参数
     *
     * @param request
     * @param name
     * @return
     */
    protected String getParameterByName(HttpServletRequest request, String name) {
        if (StringUtils.isNotEmpty(name)) {
            return request.getParameter(name);
        }
        return null;
    }

    protected void writeInfo(HttpServletResponse response, String info) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.getWriter().write(info);
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写Cookie
     *
     * @param response
     * @param name
     * @param value
     * @param days
     */
    protected void writeCookie(HttpServletResponse response, String name, String value, int days) {
        int day = 24 * 60 * 60;
        value = EncodesUtil.urlEncode(value);
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(days * day);
        cookie.setPath("/");
        cookie.setDomain("jhjhome.com");
        response.addCookie(cookie);
    }

    /**
     * 清空Cookie
     *
     * @param request
     * @param response
     */
    protected void clearCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }

    protected void deleteWebCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if (cookie.getName().startsWith("web")) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                cookie.setValue("");
                cookie.setDomain("jhjhome.com");
                response.addCookie(cookie);
            }
        }
    }

    protected static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = readCookies2Map(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }

    protected static String getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = getCookieByName(request, name);
        if (cookie == null) {
            return "";
        }
        return EncodesUtil.urlDecode(cookie.getValue());
    }

    /**
     * 将Cookie封装到Map容器
     *
     * @param request
     * @return
     */
    private static Map<String, Cookie> readCookies2Map(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    protected void showBox(HttpServletResponse response, String context) {
        try {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script>alert('" + context + "');history.go(-1);</script>");
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
