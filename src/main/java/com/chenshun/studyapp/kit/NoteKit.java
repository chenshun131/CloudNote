package com.chenshun.studyapp.kit;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * User: mew <p />
 * Time: 17/7/12 10:35  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class NoteKit {

    public static String createId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * 生成一个令牌号
     *
     * @return
     */
    public static String createToken() {
        return createId().replaceAll("-", "");// 去掉"-"
    }

    public static String md5(String msg) {
        // 基于摘要算法
        try {
            // 将msg消息进行md5处理
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] input = msg.getBytes();
            byte[] output = md.digest(input);
            // 用base64算法将output加密后的字节数组转成字符串
            return Base64.encodeBase64String(output);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(md5("1234"));
        System.out.println(md5("13241234123341234"));
    }

}
