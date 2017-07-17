package com.chenshun.component.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * User: mew <p />
 * Time: 17/7/17 12:27  <p />
 * Version: V1.0  <p />
 * Description: 日志拦截器，记录前端发起的请求，和返回的数据 <p />
 */
public class LoggerInterceptor extends HandlerInterceptorAdapter {

    private static final Logger log = LoggerFactory.getLogger(LoggerInterceptor.class);

    /** 重写initial method ， 解决相同线程进入多次报exception */
    private final ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<Long>() {
        @Override
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                             Object o) throws Exception {
        startTimeThreadLocal.set(System.currentTimeMillis());
        String accessUri = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length());
        log.info("call method:" + accessUri);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
        long endTime = System.currentTimeMillis();// 结束时间
        long beginTime = startTimeThreadLocal.get();// 得到线程绑定的局部变量（开始时间）
        startTimeThreadLocal.remove();
        String accessUri = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length());

        StringBuilder logs = new StringBuilder("\n");
        logs.append("**************** call method ****************");
        logs.append("\n").append("full path :" + httpServletRequest.getRequestURL());
        logs.append("\n").append("call method :" + accessUri + " , used time :" + (endTime - beginTime) + " ms.");
        Map<String, String[]> parameters = httpServletRequest.getParameterMap();

        for (String key : parameters.keySet()) {
            logs.append("\n").append(key + " = " + parameters.get(key)[0]);
        }
        if (e != null) {
            log.error(e.getMessage(), e);
        }
        logs.append("\n").append("**************** call method end ****************");
        log.info(logs.toString());
    }

}
