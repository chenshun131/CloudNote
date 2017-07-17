package com.chenshun.utils.orm;

/**
 * User: mew <p />
 * Time: 17/6/16 17:20  <p />
 * Version: V1.0  <p />
 * Description: 属性比较类型枚举 <p />
 */
public enum MatchType {

    EQ(1, "等于"),
    LT(2, "小于"),
    GT(3, "大于"),
    LE(4, "小于等于"),
    GE(5, "大于等于"),
    NE(6, "不等于"),
    LIKE(7, "模糊匹配,任意位置"),
    LIKES(8, "模糊匹配,仅在头部"),
    LIKEE(9, "模糊匹配,仅在尾部"),
    ISNULL(10, "空"),
    NOTNULL(11, "不为空"),
    IN(12, "IN"),
    NOTIN(13, "NOT IN");

    private int value;

    private String desc;

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    private MatchType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}
