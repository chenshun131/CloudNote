package com.chenshun.studyapp.dao.mongo.impl;

import com.chenshun.studyapp.dao.mongo.IPlaceDao;
import com.chenshun.studyapp.entity.mongo.Place;
import org.springframework.stereotype.Repository;

/**
 * User: mew <p />
 * Time: 17/8/1 12:29  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
@Repository("placeDao")
public class PlaceDaoImpl extends BaseDaoImpl<Place> implements IPlaceDao {

    @Override
    protected Class<Place> getEntityClass() {
        return Place.class;
    }

}
