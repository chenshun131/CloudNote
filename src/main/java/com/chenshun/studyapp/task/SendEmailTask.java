package com.chenshun.studyapp.task;

import com.chenshun.component.exception.ServiceException;
import com.chenshun.utils.spring.SpringContextHolder;
import freemarker.template.Template;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mew <p />
 * Time: 17/7/19 17:25  <p />
 * Version: V1.0  <p />
 * Description: 发送邮件 <p />
 */
public class SendEmailTask implements Runnable {

    private JavaMailSender mailSender = SpringContextHolder.getBean(JavaMailSender.class);

    private FreeMarkerConfigurer freeMarkerConfigurer = SpringContextHolder.getBean(FreeMarkerConfigurer.class);

    /** 邮件地址 */
    private String email;

    /** 验证码 */
    private String code;

    public SendEmailTask(String email, String code) {
        this.email = email;
        this.code = code;
    }

    @Override
    public void run() {
        sendTemplateMail(email, code);
    }

    /**
     * 通过邮件模版发送邮件
     *
     * @param email
     * @param verificationCode
     */
    private void sendTemplateMail(String email, String verificationCode) {
        MimeMessage msg = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(msg, false, "utf-8");
            helper.setFrom("chenshun131@163.com");
            helper.setTo(email);
            helper.setSubject("邮箱验证");
            String htmlText = getMailText(verificationCode);// 使用模板产生HTML 邮件体内容
            helper.setText(htmlText, true);
        } catch (MessagingException e) {
            throw new ServiceException("发送邮件失败！", e);
        }
        mailSender.send(msg);
    }

    /**
     * 通过模板构造邮件内容
     *
     * @param verificationCode
     * @return
     */
    private String getMailText(String verificationCode) {
        String htmlText = null;
        try {
            Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate("registerUser.ftl");// 通过指定模板名获取
            // Freemarker 模板实例
            Map map = new HashMap();// 通过 Map 传递动态数据
            map.put("verificationCode", verificationCode);// 注意动态数据的名字必须和模板标签中指定属性相匹配
            htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);// 解析模板并替换动态数据，产生最终的内容
        } catch (Exception e) {
            throw new ServiceException("构建邮件失败！", e);
        }
        return htmlText;
    }

}
