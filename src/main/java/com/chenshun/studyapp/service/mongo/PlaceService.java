package com.chenshun.studyapp.service.mongo;

import com.chenshun.studyapp.dao.mongo.IBaseDao;
import com.chenshun.studyapp.dao.mongo.IPlaceDao;
import com.chenshun.studyapp.entity.mongo.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: mew <p />
 * Time: 17/8/1 12:33  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
@Service
public class PlaceService extends BaseService<Place> {

    @Autowired
    private IPlaceDao placeDao;

    @Override
    protected IBaseDao<Place> getDao() {
        return placeDao;
    }

}
