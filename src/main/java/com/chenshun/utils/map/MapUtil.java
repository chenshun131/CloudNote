package com.chenshun.utils.map;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import java.util.Comparator;

/**
 * User: mew <p />
 * Time: 17/8/1 19:07  <p />
 * Version: V1.0  <p />
 * Description: 地图测距常用方法 <p />
 */
public class MapUtil {

    /**
     * 判断地理位置上两点是否在限定距离之内（单位：公里）
     *
     * @param lat1
     *         第一个点纬度 （比如114.04961）
     * @param lng1
     *         第一个点经度 （比如22.54406）
     * @param lat2
     *         第二个点纬度
     * @param lng2
     *         第二个点经度
     * @param dis
     *         限定距离（单位：公里）
     * @return 在限定距离之内返回true, 否则返回false
     */
    public static boolean checkInCircle(double lat1, double lng1, double lat2, double lng2, double dis) {
        double radLng1 = lng1 * Math.PI / 180.0;
        double radLng2 = lng2 * Math.PI / 180.0;
        if (2 * Math.asin(Math.sqrt(Math.pow(Math.sin((radLng1 - radLng2) / 2), 2) + Math.cos(radLng1) * Math.cos
                (radLng2) * Math.pow(Math.sin(((lat1 - lat2) * Math.PI / 180.0) / 2), 2))) * 6378.137 <= dis) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 计算两点经纬度的距离 (参考:根据经纬度求地球表面两点间距离)
     *
     * @param startLatLon
     *         开始经纬度
     * @param endLatLon
     *         结束经纬度
     * @return 距离(单位：公里)
     */
    public static String calculateDistanceOfTwoPoints(String startLatLon, String endLatLon) {
        int index = startLatLon.indexOf(',');
        if (index == -1) {
            return null;
        }

        double lat1, lng1;
        try {
            lat1 = Double.parseDouble(startLatLon.substring(0, index));
            lng1 = Double.parseDouble(startLatLon.substring(index + 1));
        } catch (Exception e) {
            return null;
        }

        index = endLatLon.indexOf(',');
        if (index == -1) {
            return null;
        }

        double lat2, lng2;
        try {
            lat2 = Double.parseDouble(endLatLon.substring(0, index));
            lng2 = Double.parseDouble(endLatLon.substring(index + 1));
        } catch (Exception e) {
            return null;
        }

        double radLng1 = lng1 * Math.PI / 180.0;
        double radLng2 = lng2 * Math.PI / 180.0;
        return "" + 2 * Math.asin(Math.sqrt(Math.pow(Math.sin((radLng1 - radLng2) / 2), 2) + Math.cos(radLng1) *
                Math.cos(radLng2) * Math.pow(Math.sin(((lat1 - lat2) * Math.PI / 180.0) / 2), 2))) * 6378.137;
    }

    /**
     * 计算两点之间的距离
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return KM
     */
    public static double calculateDistanceOfTwoPoints(double lat1, double lng1, double lat2, double lng2) {
        double radLng1 = lng1 * Math.PI / 180.0;
        double radLng2 = lng2 * Math.PI / 180.0;
        return 2 * Math.asin(Math.sqrt(Math.pow(Math.sin((radLng1 - radLng2) / 2), 2) + Math.cos(radLng1) *
                Math.cos(radLng2) * Math.pow(Math.sin(((lat1 - lat2) * Math.PI / 180.0) / 2), 2))) * 6378.137;
    }

    /**
     * 计算两点之间真实距离(与上一个方法存在一些差距)
     *
     * @return 米
     */
    public static double getDistance(double longitude1, double latitude1, double longitude2, double latitude2) {
        // 维度
        double lat1 = (Math.PI / 180) * latitude1;
        double lat2 = (Math.PI / 180) * latitude2;
        // 经度
        double lon1 = (Math.PI / 180) * longitude1;
        double lon2 = (Math.PI / 180) * longitude2;
        // 地球半径
        double R = 6371;
        // 两点间距离 km，如果想要米的话，结果*1000就可以了
        double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1))
                * R;
        return d * 1000;
    }

    /**
     * 根据距离排序
     *
     * @author glf
     */
    public class DistanceComparatorAsc implements Comparator<Double> {

        public int compare(Double p1, Double p2) {
            if (p1 > p2) {
                return 1;
            } else if (p1 > p2) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    /**
     * 十進位值 = 度 + (分鐘/60) + (秒/3600) 东经”(E)，以西属于“西经”(W)。西经以负号表
     * 北纬[N]为正值，；若为负值，则为南纬[S]
     * <p>
     * <p>
     * 经度转换
     *
     * @param longitude
     *         经度 如：11357.0567
     * @return
     */
    public static String transformLongitude(String longitude, String longitudeType) {
        if (longitude == null || "".equals(longitude)) {
            return null;
        }
        if ("00.00".equals(longitude)) {
            return "0.0";
        }
        String lon = longitude;
        String subLon = lon.substring(0, lon.indexOf("."));
        if (subLon.length() < 5) {
        }

        String lonD = lon.substring(0, 3);
        String lonF = lon.substring(3, 5);
        // 11357.567
        String lonFM = lon.substring(lon.indexOf(".") + 1, lon.length());
        double b = Double.valueOf(lonF + "." + lonFM);
        b = b / 60;
        b = Double.valueOf(lonD) + b;
        lon = String.valueOf(b);
        if ("W".equals(longitudeType)) {
            lon = "-" + lon;
        }
        return lon;
    }

    /**
     * 十進位值 = 度 + (分鐘/60) + (秒/3600) 东经”(E)，以西属于“西经”(W)。西经以负号表
     * 北纬[N]为正值，；若为负值，则为南纬[S]
     * <p>
     * <p>
     * 纬度转换
     *
     * @param latitude
     *         纬度 如：2235.1212
     * @return
     */
    public static String transformLatitude(String latitude, String latitudeType) {
        if (latitude == null || "".equals(latitude)) {
            return null;
        }
        if ("00.00".equals(latitude)) {
            return "0.0";
        }
        String lat = latitude;
        String latD = "";
        String latF = "";
        String latPre = lat.substring(0, lat.indexOf("."));
        if (latPre.length() == 3) {
            latD = lat.substring(0, 2);
            latF = lat.substring(2, 3);
        } else if (latPre.length() == 4) {
            latD = lat.substring(0, 2);
            latF = lat.substring(2, 4);
        }
        String latFM = lat.substring(lat.indexOf(".") + 1, lat.length());
        double a = Double.valueOf(latF + "." + latFM);
        a = a / 60;
        a = Double.valueOf(latD) + a;
        lat = String.valueOf(a);
        if ("S".equals(latitudeType)) {
            lat = "-" + lat;
        }
        return lat;
    }

    /**
     * 求两点方位角
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static double getAzimuthAngle(double lat1, double lng1, double lat2, double lng2) {
        double d = 0;
        lat1 = lat1 * Math.PI / 180;
        lng1 = lng1 * Math.PI / 180;
        lat2 = lat2 * Math.PI / 180;
        lng2 = lng2 * Math.PI / 180;

        d = Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lng2 - lng1);
        d = Math.sqrt(1 - d * d);
        d = Math.cos(lat2) * Math.sin(lng2 - lng1) / d;
        d = Math.asin(d) * 180 / Math.PI;
        // d = Math.round(d*10000);
        return d;
    }

    /**
     * 根据经纬度取地址
     *
     * @param latlon
     *         纬度经度
     * @return
     * @throws Exception
     */
    public static String getAddress(String latlon) throws Exception {
        String url = "http://api.map.baidu.com/geocoder?output=json&location=" + latlon +
                "&key=VCS65EDKbKR4gVdQk94Vnc72IyFHzRTz";
        HttpClient httpClient = new HttpClient();
        HttpMethod method = new GetMethod(url);
        byte[] data = null;
        httpClient.executeMethod(method);
        data = method.getResponseBody();
        method.releaseConnection();
        String result = new String(data, "UTF-8");
        JSONObject jsonObject = JSONObject.parseObject(result.toString()).getJSONObject("result");
        return jsonObject.getString("formatted_address");
    }

    // 区域选择路况的长和宽

    /** 长为0.2公里 200米 */
    private static double l = 1.2;

    /** 宽为30米 */
    private static double w = 0.06;

    /** π */
    private static double pi = Math.PI;

    /**
     * 根据距离计算偏移后的经纬度
     *
     * @param lat
     * @param lon
     * @param a
     * @return
     */
    public double[] topLeftLL(double lat, double lon, double a) {
        double _a = a - 90;
        if (_a < 0) {
            _a = _a + 360;
        }
        double Lon = lon + w / 2 * Math.sin((_a) * pi / 180) / 111.7 * Math.cos(lat * pi / 180);
        double Lat = lat + w / 2 * Math.cos(_a * pi / 180) / 111.7;
        double[] llArr = new double[]{Lat, Lon};
        return llArr;
    }

    /**
     * 矩形右上经纬度
     *
     * @param lat
     * @param lon
     * @param a
     * @return
     */
    public double[] topRightLL(double lat, double lon, double a) {
        double _a = a + 90;
        if (_a > 360) {
            _a = _a - 360;
        }
        double Lon = lon + w / 2 * Math.sin((_a) * pi / 180) / 111.7 * Math.cos(lat * pi / 180);
        double Lat = lat + w / 2 * Math.cos(_a * pi / 180) / 111.7;
        double[] llArr = new double[]{Lat, Lon};
        return llArr;
    }

    /**
     * 矩形左下经纬度
     *
     * @param lat
     * @param lon
     * @param a
     * @return
     */
    public double[] bottomLeftLL(double lat, double lon, double a) {
        double _a = a - 90;
        if (_a < 0) {
            _a = _a + 360;
        }
        double Lon = lon + w / 2 * Math.sin((_a) * pi / 180) / 111.7 * Math.cos(lat * pi / 180);
        double Lat = lat + w / 2 * Math.cos(_a * pi / 180) / 111.7;
        double[] llArr = new double[]{Lat, Lon};
        return llArr;
    }

    /**
     * 矩形右下经纬度
     *
     * @param lat
     * @param lon
     * @param a
     * @return
     */
    public double[] bottomRightLL(double lat, double lon, double a) {
        double _a = a + 90;
        if (_a > 360) {
            _a = _a - 360;
        }
        double Lon = lon + w / 2 * Math.sin((_a) * pi / 180) / 111.7 * Math.cos(lat * pi / 180);
        double Lat = lat + w / 2 * Math.cos(_a * pi / 180) / 111.7;
        double[] llArr = new double[]{Lat, Lon};
        return llArr;
    }

    /**
     * 矩形上部中心原点经纬度
     *
     * @param lat
     * @param lon
     * @param a
     * @return
     */
    public double[] topCenterLL(double lat, double lon, double a) {
        double Lon = lon + l * Math.sin(a * pi / 180) / 111.7 * Math.cos(lat * pi / 180);
        double Lat = lat + l * Math.cos(a * pi / 180) / 111.7;

        double[] llArr = new double[]{Lat, Lon};
        return llArr;
    }

    public static void main(String[] args) {
        MapUtil test = new MapUtil();
        double lon = 114.054765;
        double lat = 22.566093333333335;
        double a = 120;

        // 矩形下部的左右经纬度为
        // 右
        double[] bottomRightArr = test.bottomRightLL(lat, lon, a);

        // 矩形上部的左右经纬度为
        // 左
        double[] topLeftArr = test.topLeftLL(lat, lon, a);

        System.out.println("矩形经纬度为 左上: " + topLeftArr[1] + "," + topLeftArr[0]
                + " 右下: " + bottomRightArr[1] + "," + bottomRightArr[0]);
    }

}

