package com.hy.android.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hy.android.R;

/**
 * Created by Administrator on 2018/4/17.
 */

public class BannerViewHolder extends BaseViewHolder {

    public TextView bannerTitle;
    public ImageView bannerImage;
    public BannerViewHolder(View view) {
        super(view);
        bannerTitle=view.findViewById(R.id.bannerTitle);
        bannerImage=view.findViewById(R.id.bannerImage);
    }
}
