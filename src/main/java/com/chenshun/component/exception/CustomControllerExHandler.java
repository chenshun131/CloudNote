package com.chenshun.component.exception;

import com.alibaba.fastjson.JSON;
import com.chenshun.utils.ServletUtil;
import com.chenshun.utils.web.rest.RestResultDTO;
import com.chenshun.utils.web.rest.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * User: chenshun131 <p />
 * Time: 17/7/11 21:31  <p />
 * Version: V1.0  <p />
 * Description: 异常统一处理 <p />
 */
public class CustomControllerExHandler implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(CustomControllerExHandler.class);

    public CustomControllerExHandler() {
    }

    
    
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
        RestResultDTO restResultDTO = new RestResultDTO(StatusCode.INTERNAL_SERVER_ERROR);
        if (ex instanceof UnloginException) {
            restResultDTO.setStatusCode("10002");
            restResultDTO.setMessage(ex.getMessage());
        } else if (!(ex instanceof ServiceException) && !(ex instanceof IllegalArgumentException)) {
            logger.error("程序执行过程中发生了未知的异常:" + ex.getMessage());
            ex.printStackTrace();
        } else {
            restResultDTO.setMessage(ex.getMessage());
        }

        try {
            String jsonString = JSON.toJSONString(restResultDTO);
            byte[] arr = jsonString.getBytes("UTF-8");
            response.setHeader("Content-Length", String.valueOf(arr.length));
            ServletUtil.renderJson(response, restResultDTO, new String[0]);
        } catch (UnsupportedEncodingException var8) {
            logger.error(var8.getMessage());
        }
        return null;
    }

}
