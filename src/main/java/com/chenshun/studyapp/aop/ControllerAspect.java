package com.chenshun.studyapp.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * User: mew <p />
 * Time: 17/7/15 15:40  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
@Component // 扫描到Spring容器
@Aspect // 将该组件指定为方面
public class ControllerAspect {

    private static final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    @Before("within(com.chenshun.studyapp.controller..*)")
    public void mybefore() {
//		System.out.println("进入Controller处理");
        logger.debug("进入Controller处理");
    }

}
