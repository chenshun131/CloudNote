package com.chenshun.utils.validate;

import com.chenshun.utils.date.DateOperator;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: mew <p />
 * Time: 17/7/11 12:50  <p />
 * Version: V1.0  <p />
 * Description: 通用的验证工具类 <p />
 */
public final class CommonValidateUtil {

    private CommonValidateUtil() {
    }

    /** 行政区域字符串 */
    private static final String DISTRICT_STRINGS = "京津沪渝黑吉辽冀豫鲁晋湘鄂粤桂云贵川陕内宁甘新青藏皖赣苏浙闽琼";

    /** 数字正则表达式,不包含小数 */
    private static final String DIGITAL_REG_EXP = "\\d";

    /** 登录密码的正则表达式 */
    public static final String LOGIN_PASSWORD_REG_EXP = "^[A-Za-z0-9_]{6,16}$";

    /** 交易密码的正则表达式 */
    public static final String TRADE_PASSWORD_REG_EXP = "[0-9]{6}$";

    /** 手机号码正则表达式 */
    private static final String MOBILE_REG_EXP = "^0?(13|15|18|14|17)[0-9]{9}$";

    /** 座机号码正则表达式 */
    private static final String TEL_REG_EXP = "^(0\\d{2,3}-?)?\\d{7,8}$";

    /** 电子邮箱正则表达式 */
    private static final String EMAIL_REG_EXP =
            "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";

    /** 车牌号码正则表达式 */
    private static final String CAR_PLATE_NO_REG_EXP = "^[" + DISTRICT_STRINGS + "]{1}[A-Za-z]{1}[A-Za-z0-9]{5}$";

    /** 证件有效期正则表达式 */
    private static final String PAPER_VALI_PERIOD = "^\\d{4}-\\d{2}-\\d{2}至\\d{4}-\\d{2}-\\d{2}$";

    /**
     * 验证字符串非空
     *
     * @param string
     * @param message
     * @throws IllegalArgumentException
     */
    public static void notEmpty(String string, String message) {
        if (string == null || string.length() == 0) {
            throw new IllegalArgumentException(message);
        }
        if ("null".equalsIgnoreCase(string.trim()) || "".equals(string.trim())) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 验证字符串是否为空
     *
     * @param string
     * @return
     */
    public static boolean isEmpty(String string) {
        if (string == null || string.length() == 0) {
            return true;
        }
        if ("null".equalsIgnoreCase(string.trim()) || "".equals(string.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 验证定长字符串
     * <br>
     * length minimum value : 1
     *
     * @param str
     * @param len
     * @return
     */
    public static boolean validateFixedLengthStr(String str, int len) {
        if (isEmpty(str)) {
            return false;
        }
        if (len < 1) {
            len = 1;
        }
        if (len != str.length()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 验证变长字符串
     *
     * @param str
     * @param minLength
     *         minimum value : 1
     * @param maxLength
     *         minimum value : minLength
     * @return
     */
    public static boolean validateRangeLengthStr(String str, int minLength, int maxLength) {
        if (isEmpty(str)) {
            return false;
        }
        if (minLength < 1) {
            minLength = 1;
        }
        if (maxLength < minLength) {
            maxLength = minLength;
        }
        int strLen = str.length();
        if (minLength > strLen || maxLength < strLen) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 验证字符串是否由数字组成，不包含小数
     *
     * @param digital
     * @return
     */
    public static boolean validateDigital(String digital) {
        if (isEmpty(digital)) {
            return false;
        }
        String patternStr = DIGITAL_REG_EXP + "+";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(digital);
        return matcher.matches();
    }

    /**
     * validate fixed length digital
     * <p>
     * <br />
     * <p>
     * when length equal or less than zero, just only validate target string is a digital
     *
     * @param digital
     * @param length
     * @return true if validate success, otherwise will return false
     */
    public static boolean validateFixedLengthDigital(String digital, int length) {
        if (length <= 0) {
            return validateDigital(digital);
        } else {
            if (isEmpty(digital)) {
                return false;
            }
            String patternStr = DIGITAL_REG_EXP + "{" + length + "}";
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(digital);
            return matcher.matches();
        }
    }

    /**
     * validate range length digital
     * <p>
     * <br />
     *
     * @param digital
     * @param minLength
     * @param maxLength
     * @return true if validate success, otherwise will return false
     * @throws IllegalArgumentException
     *         1.when minimum length equal or less than zero 2.maximum length less than minimum length
     */
    public static boolean validateRangeLengthDigital(String digital, int minLength, int maxLength) {
        if (minLength <= 0) {
            throw new IllegalArgumentException("min length must great than zero");
        }

        if (minLength > maxLength) {
            throw new IllegalArgumentException("max length must great than min length");
        }

        if (isEmpty(digital)) {
            return false;
        }

        String patternStr = DIGITAL_REG_EXP + "{" + minLength + "," + maxLength + "}";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(digital);
        return matcher.matches();
    }

    public static boolean validateLoginPass(String originalLoginPass) {
        if (isEmpty(originalLoginPass)) {
            return false;
        }
        Pattern pattern = Pattern.compile(LOGIN_PASSWORD_REG_EXP);
        Matcher matcher = pattern.matcher(originalLoginPass);
        return matcher.matches();

    }

    public static boolean validateTradePass(String tradePass) {
        if (isEmpty(tradePass)) {
            return false;
        }
        Pattern pattern = Pattern.compile(TRADE_PASSWORD_REG_EXP);
        Matcher matcher = pattern.matcher(tradePass);
        return matcher.matches();

    }

    public static boolean validateMobile(String mobile) {
        if (isEmpty(mobile)) {
            return false;
        }
        Pattern pattern = Pattern.compile(MOBILE_REG_EXP);
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }

    public static boolean validateTel(String tel) {
        if (isEmpty(tel)) {
            return false;
        }

        Pattern pattern = Pattern.compile(TEL_REG_EXP);
        Matcher matcher = pattern.matcher(tel);
        return matcher.matches();
    }

    public static boolean validateEmail(String email) {
        if (isEmpty(email)) {
            return false;
        }
        Pattern pattern = Pattern.compile(EMAIL_REG_EXP);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validateZipcode(String zipcode) {
        return validateFixedLengthDigital(zipcode, 6);
    }

    public static boolean validateCarPlateNo(String carPlateNo) {
        if (isEmpty(carPlateNo)) {
            return false;
        }

        Pattern pattern = Pattern.compile(CAR_PLATE_NO_REG_EXP);
        Matcher matcher = pattern.matcher(carPlateNo);
        return matcher.matches();
    }

    /**
     * 验证身份证号码
     *
     * @param identityCardNo
     * @return
     */
    public static boolean validateIdentityCardNum(String identityCardNo) {
        if (isEmpty(identityCardNo)) {
            return false;
        }
        // 不再支持15位的旧版身份证
        if (identityCardNo.length() == 15) {
            return false;
        }
        return IdentityCardUtil.getInstance().verify(identityCardNo);
    }

    public static boolean validatePaperValiPeriod(String valiPeriod) {
        if (isEmpty(valiPeriod)) {
            return false;
        }

        Pattern pattern = Pattern.compile(PAPER_VALI_PERIOD);
        Matcher matcher = pattern.matcher(valiPeriod);
        if (matcher.matches()) {

            String[] dateArr = valiPeriod.split("至");

            Date start = DateOperator.parse(dateArr[0], "yyyy-MM-dd");
            Date end = DateOperator.parse(dateArr[1], "yyyy-MM-dd");

            if (start.compareTo(end) >= 0) {
                return false;
            } else {
                return true;
            }

        } else {
            return false;
        }
    }

    public static boolean validateBankCardNum(String bankCardNum) {
        if (!CommonValidateUtil.validateRangeLengthDigital(bankCardNum, 16, 19)) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {

        System.out.println(validateDigital("100"));

        System.out.println(DISTRICT_STRINGS.length());
        System.out.println(validateCarPlateNo("川A3EQ22"));
        System.out.println(validateCarPlateNo("川A3EQ22"));
        System.out.println(validateTradePass("112345"));
    }

}

