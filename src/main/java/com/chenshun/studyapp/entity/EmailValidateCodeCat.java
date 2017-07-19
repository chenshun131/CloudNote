package com.chenshun.studyapp.entity;

/**
 * User: mew <p />
 * Time: 17/7/19 15:33  <p />
 * Version: V1.0  <p />
 * Description: 邮件类型枚举累类 <p />
 */
public enum EmailValidateCodeCat {

    GORGET_PASSWORD(0, "忘记密码", "forget_password");

    /** 索引值 */
    private int index;

    /** 描述信息 */
    private String desc;

    /** 前缀 */
    private String prefix;

    private EmailValidateCodeCat(int index, String desc, String prefix) {
        this.index = index;
        this.desc = desc;
        this.prefix = prefix;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public static EmailValidateCodeCat get(int index) {
        for (EmailValidateCodeCat type : EmailValidateCodeCat.values()) {
            if (type.index == index) {
                return type;
            }
        }
        return null;
    }

}
