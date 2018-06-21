package com.hy.location.bean;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class LineBean implements RealmModel {
    private String id;
    private String routeName;  //路线名称
    private String beginLng;
    private String beginLat;
    private String endLng;
    private String endLat;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getBeginLng() {
        return beginLng;
    }

    public void setBeginLng(String beginLng) {
        this.beginLng = beginLng;
    }

    public String getBeginLat() {
        return beginLat;
    }

    public void setBeginLat(String beginLat) {
        this.beginLat = beginLat;
    }

    public String getEndLng() {
        return endLng;
    }

    public void setEndLng(String endLng) {
        this.endLng = endLng;
    }

    public String getEndLat() {
        return endLat;
    }

    public void setEndLat(String endLat) {
        this.endLat = endLat;
    }
}
