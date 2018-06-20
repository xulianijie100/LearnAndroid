package com.hy.location.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.services.route.DriveStep;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hy.location.R;
import com.hy.location.bean.LineBean;
import com.hy.location.utils.AMapUtil;
import com.hy.location.view.PointViewHolder;
import com.hy.location.view.RouteViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xulj on 2018/4/17.
 */

public class PointAdapter extends RecyclerView.Adapter<PointViewHolder> {

    private Context mContext;
    private List<DriveStep> mData = new ArrayList<>();


    public PointAdapter(Context context, List<DriveStep> datas) {
        mContext = context;
        if (datas != null) {
            mData = datas;
        }
    }

    @Override
    public PointViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_bus_segment, parent, false);
        return new PointViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PointViewHolder holder, int position) {
        final DriveStep item = mData.get(position);
        if (item != null) {
            if (position == 0) {
                holder.driveDirIcon.setImageResource(R.mipmap.dir_start);
                holder.driveLineName.setText("出发");
                holder.driveDirUp.setVisibility(View.GONE);
                holder.driveDirDown.setVisibility(View.VISIBLE);
                holder.splitLine.setVisibility(View.GONE);

            } else if (position == mData.size() - 1) {
                holder.driveDirIcon.setImageResource(R.mipmap.dir_end);
                holder.driveLineName.setText("到达终点");
                holder.driveDirUp.setVisibility(View.VISIBLE);
                holder.driveDirDown.setVisibility(View.GONE);
                holder.splitLine.setVisibility(View.VISIBLE);

            } else {
                String actionName = item.getAction();
                int resID = AMapUtil.getDriveActionID(actionName);
                holder.driveDirIcon.setImageResource(resID);
                holder.driveLineName.setText(item.getInstruction());
                holder.driveDirUp.setVisibility(View.VISIBLE);
                holder.driveDirDown.setVisibility(View.VISIBLE);
                holder.splitLine.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }


    public void add(List<DriveStep> datas) {
        mData.clear();
        mData.addAll(datas);
        notifyDataSetChanged();
    }

}
