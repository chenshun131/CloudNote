package com.chenshun.studyapp.entity.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * User: mew <p />
 * Time: 17/7/31 13:47  <p />
 * Version: V1.0  <p />
 * Description: 用户实体类 <p />
 */
@Document(collection = "coll_user")
public class UserInfo implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    // 主键使用此注解
    @Id
    private String id;

    // 字段使用此注解
    @Field
    private String name;

    // 字段还可以用自定义名称
    @Field("myage")
    private int age;

    // 还可以生成索引
    @Indexed(name = "index_birth", direction = IndexDirection.DESCENDING)
    @Field
    private Date birth;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

}
