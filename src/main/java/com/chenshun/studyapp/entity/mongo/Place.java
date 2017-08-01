package com.chenshun.studyapp.entity.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * User: mew <p />
 * Time: 17/8/1 11:26  <p />
 * Version: V1.0  <p />
 * Description: 地理位置信息 <br/>
 * 创建索引 : db.coll_places.createIndex({"coordinate":"2d"}) <p />
 */
@Document(collection = "coll_places")
public class Place implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    // 主键使用此注解
    @Id
    private String id;

    /** 位置名称 */
    @Field
    private String name;

    /** 地址 */
    @Field
    private String address;

    /** 城市 */
    @Field
    private String city;

    /** 经纬度信息 (创建一个地理空间索引) */
    @Indexed(name = "2d")
    @Field
    private Coordinate coordinate;

    /** 地理位置种类 School:学校 Company:公司 Dining:餐饮 */
    @Field
    private String category;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
