package com.chenshun.studyapp.entity.mongo;

import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * User: mew <p />
 * Time: 17/8/1 11:45  <p />
 * Version: V1.0  <p />
 * Description: 坐标 <p />
 */
public class Coordinate implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** 纬度 */
    @Field
    private double lat;

    /** 经度 */
    @Field
    private double lng;

    public Coordinate() {
    }

    public Coordinate(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

}
