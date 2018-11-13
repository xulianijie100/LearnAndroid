package com.hy.android.fragment;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hy.android.Base.BaseFragment;
import com.hy.android.R;
import com.hy.android.activity.*;
import com.hy.android.adapter.FuncAdapter;

import java.util.ArrayList;
import java.util.List;

public class TypeFragment extends BaseFragment  {

    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private FuncAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_type;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        List<String> list=new ArrayList<>();
        list.add("Handler+AsyncTask");
        list.add("cmake");
        list.add("自定义View");
        list.add("View 动画");
        list.add("Java 开发模式");
        list.add("Java 排序");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter=new FuncAdapter(R.layout.func_list_item,list);
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
                        startActivity(new Intent(getActivity(), SortingActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
