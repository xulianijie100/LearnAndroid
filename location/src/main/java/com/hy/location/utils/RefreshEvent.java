package com.hy.location.utils;

import com.hy.location.bean.LocalBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/24.
 */

public class RefreshEvent {

    public ArrayList<LocalBean> list;

    public RefreshEvent(ArrayList<LocalBean> list) {
        this.list = list;
    }
}
