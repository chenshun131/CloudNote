package com.chenshun.utils.orm.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * User: mew <p />
 * Time: 17/6/16 17:32  <p />
 * Version: V1.0  <p />
 * Description: 统一定义id的entity基类. <br/>
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略. 子类可重载getId()函数重定义id的列名映射和生成策略. <p />
 */
@MappedSuperclass
public abstract class BaseEntity {

    protected String id;

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @GeneratedValue(generator = "UIDGenerator")
    @GenericGenerator(name = "UIDGenerator", strategy = "org.cosmos.core.orm.entity.UIDGenerator")
    @Column(length = 64)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
