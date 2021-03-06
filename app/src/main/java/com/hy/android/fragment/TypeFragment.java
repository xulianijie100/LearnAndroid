package com.hy.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hy.android.R;
import com.hy.android.activity.AnimationActivity;
import com.hy.android.activity.AsyncActivity;
import com.hy.android.activity.TestFunActivity;
import com.hy.android.activity.TestJniActivity;
import com.hy.android.activity.VideoActivity;
import com.hy.android.activity.ViewActivity;
import com.hy.android.adapter.FuncAdapter;
import com.hy.android.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TypeFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private FuncAdapter mAdapter;

    @Override
    public int getContentLayout() {
        return R.layout.fragment_type;
    }

    @Override
    public void initData() {
    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        List<String> list = new ArrayList<>();
        list.add("Handler+AsyncTask");
        list.add("Serializable");
        list.add("自定义View");
        list.add("View 动画");
        list.add("Java 开发模式");
        list.add("jni test");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new FuncAdapter(R.layout.func_list_item, list);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getActivity(), AsyncActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), VideoActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), ViewActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(), AnimationActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(getActivity(), TestFunActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(getActivity(), TestJniActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
