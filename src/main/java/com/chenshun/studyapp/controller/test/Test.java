package com.chenshun.studyapp.controller.test;

import com.chenshun.utils.date.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * User: mew <p />
 * Time: 17/8/6 11:20  <p />
 * Version: V1.0  <p />
 * Description: 测试访问地址 <p />
 */
@Controller
@RequestMapping("/test")
public class Test {

    @RequestMapping(value = "/webSSE")
    @ResponseBody
    public void webSSE(HttpServletResponse response) {
        response.addHeader("Cache-Control", "no-cache");
        response.setHeader("Content-Type", "text/event-stream");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(String.format("data: The server time is : %s", DateUtil.getTime()));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
