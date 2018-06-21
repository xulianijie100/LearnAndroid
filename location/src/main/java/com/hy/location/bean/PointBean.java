package com.hy.location.bean;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Created by Administrator on 2018/6/21.
 */
@RealmClass
public class PointBean implements RealmModel {

    private String id;
    private String routeID;  //路线id
    private String pointIndex;
    private String pointName;
    private float nextPointRange;
    private double longitude;
    private double latitude;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRouteID() {
        return routeID;
    }

    public void setRouteID(String routeID) {
        this.routeID = routeID;
    }

    public String getPointIndex() {
        return pointIndex;
    }

    public void setPointIndex(String pointIndex) {
        this.pointIndex = pointIndex;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public float getNextPointRange() {
        return nextPointRange;
    }

    public void setNextPointRange(float nextPointRange) {
        this.nextPointRange = nextPointRange;
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
