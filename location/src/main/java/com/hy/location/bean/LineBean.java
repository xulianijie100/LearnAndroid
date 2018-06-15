package com.hy.location.bean;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class LineBean implements RealmModel {
    private String id;
    private int RouteID;
    private String pointName;  //路段名称
    private double longitude;  //经度
    private double latitude;    //纬度

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRouteID() {
        return RouteID;
    }

    public void setRouteID(int routeID) {
        RouteID = routeID;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
