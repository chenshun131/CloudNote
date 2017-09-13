package com.chenshun.test.mongodb;

import com.chenshun.studyapp.entity.mongo.Coordinate;
import com.chenshun.studyapp.entity.mongo.Place;
import com.chenshun.studyapp.service.mongo.PlaceService;
import com.chenshun.utils.map.MapUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * User: mew <p />
 * Time: 17/8/1 12:35  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 * <pre>
 * *************************************************************
 *                                                             *
 *   .=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-.        *
 *    |                     ______                     |       *
 *    |                  .-"      "-.                  |       *
 *    |                 /            \                 |       *
 *    |     _          |              |          _     |       *
 *    |    ( \         |,  .-.  .-.  ,|         / )    |       *
 *    |     > "=._     | )(__/  \__)( |     _.=" <     |       *
 *    |    (_/"=._"=._ |/     /\     \| _.="_.="\_)    |       *
 *    |           "=._"(_     ^^     _)"_.="           |       *
 *    |               "=\__|IIIIII|__/="               |       *
 *    |              _.="| \IIIIII/ |"=._              |       *
 *    |    _     _.="_.="\          /"=._"=._     _    |       *
 *    |   ( \_.="_.="     `--------`     "=._"=._/ )   |       *
 *    |    > _.="                            "=._ <    |       *
 *    |   (_/                                    \_)   |       *
 *    |                                                |       *
 *    '-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-='       *
 *                                                             *
 *           LASCIATE OGNI SPERANZA, VOI CH'ENTRATE            *
 * *************************************************************
 * </pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext.xml", "/spring/spring-mvc.xml"})
public class TestPlace {

    @Autowired
    private PlaceService placeService;

    @Test
    public void save() {
        Place place1 = new Place();
        place1.setName("四川大学");
        place1.setAddress("四川成都市人民南路三段17号");
        place1.setCity("成都");
        place1.setCoordinate(new Coordinate(30.647093, 104.072946));
        place1.setCategory("School");
        placeService.save(place1);

        Place place2 = new Place();
        place2.setName("成都天之声科技有限公司");
        place2.setAddress("人民南路三段24号成都师范学院A栋403");
        place2.setCity("成都");
        place2.setCoordinate(new Coordinate(30.643481, 104.07163));
        place2.setCategory("Company");
        placeService.save(place2);

        Place place3 = new Place();
        place3.setName("家常菜豆汤饭");
        place3.setAddress("人民南路三段24号附60号(近公行道)");
        place3.setCity("成都");
        place3.setCoordinate(new Coordinate(30.643885, 104.070449));
        place3.setCategory("Dining");
        placeService.save(place3);

        Place place4 = new Place();
        place4.setName("北京果木烤鸭");
        place4.setAddress("中学路22号附1号");
        place4.setCity("成都");
        place4.setCoordinate(new Coordinate(30.643178, 104.069631));
        place4.setCategory("Dining");
        placeService.save(place4);

        Place place5 = new Place();
        place5.setName("味缘雅风花园餐厅");
        place5.setAddress("成都武侯区中学路22号(近四川教育学院)");
        place5.setCity("成都");
        place5.setCoordinate(new Coordinate(30.643209, 104.069186));
        place5.setCategory("Dining");
        placeService.save(place5);

        Place place6 = new Place();
        place6.setName("一串飘红串串香");
        place6.setAddress("九如村33号附4~7号");
        place6.setCity("成都");
        place6.setCoordinate(new Coordinate(30.641524, 104.068113));
        place6.setCategory("Dining");
        placeService.save(place6);

        Place place7 = new Place();
        place7.setName("蒙山茶府");
        place7.setAddress("人民南路三段17号华西美庐A栋3楼");
        place7.setCity("成都");
        place7.setCoordinate(new Coordinate(30.644926, 104.073229));
        place7.setCategory("Dining");
        placeService.save(place7);

        Place place8 = new Place();
        place8.setName("滇味过桥米线(新南路)");
        place8.setAddress("武侯区新南路88附14号(近徐老八怪味面)");
        place8.setCity("成都");
        place8.setCoordinate(new Coordinate(30.642949, 104.082261));
        place8.setCategory("Dining");
        placeService.save(place8);

        Place place9 = new Place();
        place9.setName("鲜香店");
        place9.setAddress("新南路88号附1号");
        place9.setCity("成都");
        place9.setCoordinate(new Coordinate(30.643384, 104.082216));
        place9.setCategory("Dining");
        placeService.save(place9);

        Place place10 = new Place();
        place10.setName("万州烤鱼");
        place10.setAddress("林荫中街8号附5");
        place10.setCity("成都");
        place10.setCoordinate(new Coordinate(30.643722, 104.079818));
        place10.setCategory("Dining");
        placeService.save(place10);

        Place place11 = new Place();
        place11.setName("久御香酒楼茶坊");
        place11.setAddress("武侯区林荫中街8号(成都7中)");
        place11.setCity("成都");
        place11.setCoordinate(new Coordinate(30.6434, 104.079773));
        place11.setCategory("Dining");
        placeService.save(place11);

        Place place12 = new Place();
        place12.setName("零三酒店");
        place12.setAddress("人民南路三段22号(近四川大学华西第二医院)");
        place12.setCity("成都");
        place12.setCoordinate(new Coordinate(30.643819, 104.072726));
        place12.setCategory("Dining");
        placeService.save(place12);
    }

    @Test
    public void findAll() {
        List<Place> places = placeService.findByProp("category", "Dining");
        for (Place place : places) {
            System.out.println(place.getCategory() + " : " + place.getName());
        }
    }

    /**
     * 查找点 由近及远的其它点
     */
    @Test
    public void findLocalPoint() {
        GeoResults<Place> results = placeService.geoNear(new Point(30.644242, 104.073143), 1000000,
                "category", "Dining");
        for (GeoResult<Place> geoResult : results) {
            System.out.println(geoResult.getContent().getName() + ":" +
                    MapUtil.getDistance(104.073143, 30.644242,
                            geoResult.getContent().getCoordinate().getLng(),
                            geoResult.getContent().getCoordinate().getLat()) +
                    "  || " + MapUtil.calculateDistanceOfTwoPoints(30.644242, 104.073143,
                    geoResult.getContent().getCoordinate().getLat(),
                    geoResult.getContent().getCoordinate().getLng()));
        }
    }

    /**
     * 查找
     */
    @Test
    public void getCircleInnerPoint() {
        List<Place> places = placeService.getCircleInnerPoint("coordinate", new Circle(30.644242, 104.073143, 0.1));
        for (Place place : places) {
            System.out.println(place.getCategory() + " : " + place.getName() + " : " +
                    MapUtil.getDistance(104.073143, 30.644242,
                            place.getCoordinate().getLng(), place.getCoordinate().getLat()));
        }
    }

}
