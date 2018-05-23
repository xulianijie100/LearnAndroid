package com.hy.location.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hy.location.R;
import com.hy.location.bean.LocalBean;
import com.hy.location.view.LocalViewHolder;

import java.util.List;

/**
 * Created by xulj on 2018/4/17.
 */

public class LocalAdapter extends BaseQuickAdapter<LocalBean, LocalViewHolder> {
    public LocalAdapter(int layoutResId, @Nullable List<LocalBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(LocalViewHolder helper, LocalBean item) {
        if (!TextUtils.isEmpty(item.name)) {
            helper.setText(R.id.tv_name, item.name);

        }

        if(item.localArray!=null && item.localArray.size()>0){

            String local="";
            for(String str:item.localArray){
                local+=str+" --> ";
            }
            helper.setText(R.id.tv_local, local.substring(0,local.length()-4));

        }
    }
}
