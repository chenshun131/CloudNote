package com.chenshun.test.other.validate;

import org.hibernate.validator.constraints.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * User: mew <p />
 * Time: 17/9/22 08:25  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class ValidateTest {

    @NotNull(message = "必须不为 null")
    @Size(min = 5, max = 10, message = "最大是 10，最小是 5，null元素被认定为有效")
    public List<String> orders;

    @Length(min = 5, max = 10, message = "检查字符串是否在 5 至 10之间，包括 5 和 10")
    public String orderId;

    @Null(message = "必须为 null")
    public String nullData;

    @NotBlank(message = "@NotBlank 检查约束字符串是不是Null还有被Trim的长度是否大于0,只对字符串,且会去掉前后空格")
    public String name;

    @NotEmpty(message = "@NotEmpty 检查约束元素是否为NULL或者是EMPTY")
    public String describ;

    @AssertTrue(message = "@AssertTrue 验证 Boolean 对象是否为 true")
    public boolean isEnable;

    @AssertFalse(message = "@AssertFalse 验证 Boolean 对象是否为 false")
    public boolean isNotEnable;

    @Max(value = 10, message = "len 不超过10")
    @Min(value = 5, message = "len 不低于5")
    public Number len;

    @NotNull(message = "adultTaxType不能为空")
    @Min(value = 0, message = "adultTaxType 的最小值为0")
    @Max(value = 1, message = "adultTaxType 的最大值为1")
    public Integer adultTaxType;

    /** 订单取消原因 */
    @NotNull(message = "reason信息不可以为空")
    @Pattern(regexp = "[1-7]{1}", message = "reason的类型值为1-7中的一个类型")
    public String reason;

    @DecimalMax(value = "100", message = "年龄不能超过100岁")
    @DecimalMin(value = "1", message = "年龄不能低于1岁")
    public int age;

    @Digits(integer = 4, fraction = 4, message = "整数部分最大4位，小数部分最大4位")
    public String ratio;

    @Range(min = 5, max = 10, message = "限制长度在 5～10 之间")
    public String song;

    @CreditCardNumber(message = "信用卡验证")
    public String creditCardNumber;

    @Email(message = "验证是否是邮件地址，如果为null,不进行验证，算通过验证")
    public String email;

    @URL
    public String url;

    /**
     * 验证注解的完整方法
     */
    public void validateParams() {
        // 调用JSR303验证工具，校验参数
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<ValidateTest>> violations = validator.validate(this);
        Iterator<ConstraintViolation<ValidateTest>> iter = violations.iterator();
        if (iter.hasNext()) {
            String errMessage = iter.next().getMessage();
            throw new ValidationException(errMessage);
        }
    }

    public static void main(String[] args) {
        ValidateTest validateTestClass = new ValidateTest();
        validateTestClass.orders = new ArrayList<>(4);

        validateTestClass.validateParams(); // 调用验证的方法
    }

}
