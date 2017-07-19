package com.chenshun.utils;

import com.chenshun.component.exception.Exceptions;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringEscapeUtils;

/**
 * User: chenshun131 <p />
 * Time: 17/7/11 21:43  <p />
 * Version: V1.0  <p />
 * Description: 封装各种格式的编码解码工具类 <br/>
 * 1.Commons-Codec的 hex/base64 编码 <br/>
 * 2.自制的base62 编码 <br/>
 * 3.Commons-Lang的xml/html escape <br/>
 * 4.JDK提供的URLEncoder <p />
 */
public class EncodesUtil {

    private static final String DEFAULT_URL_ENCODING = "UTF-8";

    private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    public EncodesUtil() {
    }

    /**
     * Hex编码
     *
     * @param input
     * @return
     */
    public static String encodeHex(byte[] input) {
        return Hex.encodeHexString(input);
    }

    /**
     * Hex解码
     *
     * @param input
     * @return
     */
    public static byte[] decodeHex(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException var2) {
            throw Exceptions.unchecked(var2);
        }
    }

    /**
     * Base64编码
     *
     * @param input
     * @return
     */
    public static String encodeBase64(byte[] input) {
        return Base64.encodeBase64String(input);
    }

    /**
     * Base64编码, URL安全(将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548)
     *
     * @param input
     * @return
     */
    public static String encodeUrlSafeBase64(byte[] input) {
        return Base64.encodeBase64URLSafeString(input);
    }

    /**
     * Base64解码
     *
     * @param input
     * @return
     */
    public static byte[] decodeBase64(String input) {
        return Base64.decodeBase64(input);
    }

    /**
     * Base62编码
     *
     * @param input
     * @return
     */
    public static String encodeBase62(byte[] input) {
        char[] chars = new char[input.length];
        for (int i = 0; i < input.length; ++i) {
            chars[i] = BASE62[(input[i] & 255) % BASE62.length];
        }
        return new String(chars);
    }

    /**
     * Html 转码
     *
     * @param html
     * @return
     */
    public static String escapeHtml(String html) {
        return StringEscapeUtils.escapeHtml(html);
    }

    /**
     * Html 解码
     *
     * @param htmlEscaped
     * @return
     */
    public static String unescapeHtml(String htmlEscaped) {
        return StringEscapeUtils.unescapeHtml(htmlEscaped);
    }

    /**
     * xml 转码
     *
     * @param xml
     * @return
     */
    public static String escapeXml(String xml) {
        return StringEscapeUtils.escapeXml(xml);
    }

    /**
     * xml 解码
     *
     * @param xmlEscaped
     * @return
     */
    public static String unescapeXml(String xmlEscaped) {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }

    /**
     * URL 编码, Encode默认为UTF-8
     *
     * @param part
     * @return
     */
    public static String urlEncode(String part) {
        try {
            return URLEncoder.encode(part, DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException var2) {
            throw Exceptions.unchecked(var2);
        }
    }

    /**
     * URL 解码, Encode默认为UTF-8
     *
     * @param part
     * @return
     */
    public static String urlDecode(String part) {
        try {
            return URLDecoder.decode(part, DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException var2) {
            throw Exceptions.unchecked(var2);
        }
    }

    /**
     * Md5加密
     *
     * @param message
     * @return
     */
    public static String Md5Encoder(String message) {
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(message.getBytes(DEFAULT_URL_ENCODING));
            byte[] s = digest.digest();
            return encodeHex(s);
        } catch (NoSuchAlgorithmException var3) {
            var3.printStackTrace();
        } catch (UnsupportedEncodingException var4) {
            var4.printStackTrace();
        }
        return null;
    }

}
