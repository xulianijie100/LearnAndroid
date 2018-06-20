package com.hy.location.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hy.location.R;

/**
 * Created by Administrator on 2018/4/17.
 */

public class PointViewHolder extends BaseViewHolder {

    public TextView driveLineName;
    public ImageView driveDirIcon;
    public ImageView driveDirUp;
    public ImageView driveDirDown;
    public ImageView splitLine;
    public PointViewHolder(View view) {
        super(view);
        driveLineName= (TextView) view.findViewById(R.id.bus_line_name);
        driveDirIcon= (ImageView) view.findViewById(R.id.bus_dir_icon);
        driveDirUp= (ImageView) view.findViewById(R.id.bus_dir_icon_up);
        driveDirDown= (ImageView) view.findViewById(R.id.bus_dir_icon_down);
        splitLine= (ImageView) view.findViewById(R.id.bus_seg_split_line);
    }
}
