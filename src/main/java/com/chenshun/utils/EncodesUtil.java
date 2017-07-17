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
 * Description:  <p />
 */
public class EncodesUtil {

    private static final String DEFAULT_URL_ENCODING = "UTF-8";

    private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    public EncodesUtil() {
    }

    public static String encodeHex(byte[] input) {
        return Hex.encodeHexString(input);
    }

    public static byte[] decodeHex(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException var2) {
            throw Exceptions.unchecked(var2);
        }
    }

    public static String encodeBase64(byte[] input) {
        return Base64.encodeBase64String(input);
    }

    public static String encodeUrlSafeBase64(byte[] input) {
        return Base64.encodeBase64URLSafeString(input);
    }

    public static byte[] decodeBase64(String input) {
        return Base64.decodeBase64(input);
    }

    public static String encodeBase62(byte[] input) {
        char[] chars = new char[input.length];
        for (int i = 0; i < input.length; ++i) {
            chars[i] = BASE62[(input[i] & 255) % BASE62.length];
        }
        return new String(chars);
    }

    public static String escapeHtml(String html) {
        return StringEscapeUtils.escapeHtml(html);
    }

    public static String unescapeHtml(String htmlEscaped) {
        return StringEscapeUtils.unescapeHtml(htmlEscaped);
    }

    public static String escapeXml(String xml) {
        return StringEscapeUtils.escapeXml(xml);
    }

    public static String unescapeXml(String xmlEscaped) {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }

    public static String urlEncode(String part) {
        try {
            return URLEncoder.encode(part, "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            throw Exceptions.unchecked(var2);
        }
    }

    public static String urlDecode(String part) {
        try {
            return URLDecoder.decode(part, "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            throw Exceptions.unchecked(var2);
        }
    }

    public static String Md5Encoder(String message) {
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(message.getBytes("UTF-8"));
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
