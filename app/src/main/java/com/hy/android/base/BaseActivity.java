package com.hy.android.base;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.hy.android.R;
import com.hy.android.utils.AppManager;
import com.hy.android.utils.DialogHelper;
import com.hy.android.widget.SimpleMultiStateView;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.lang.ref.WeakReference;

public abstract class BaseActivity<T1 extends BaseContract.BasePresenter> extends SupportActivity implements IBase, BaseContract.BaseView {
    private Unbinder unbinder;
    public Handler mHandler;
    protected View mRootView;
    protected Dialog mLoadingDialog = null;

    protected T1 mPresenter;

    @Nullable
    @BindView(R.id.SimpleMultiStateView)
    SimpleMultiStateView mSimpleMultiStateView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = createView(null, null, savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(mRootView);
        attachView();
        bindView(mRootView, savedInstanceState);
        initData();
        mLoadingDialog = DialogHelper.getLoadingDialog(this);
        this.mHandler = new ActivityHandler(this);
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(getContentLayout(), container);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public View getView() {
        return mRootView;
    }

    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    protected void showLoadingDialog() {
        if (mLoadingDialog != null)
            mLoadingDialog.show();
    }

    protected void showLoadingDialog(String str) {
        if (mLoadingDialog != null) {
            TextView tv = (TextView) mLoadingDialog.findViewById(R.id.tv_load_dialog);
            tv.setText(str);
            mLoadingDialog.show();
        }
    }

    protected void hideLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }


    @Override
    public void showLoading() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showLoadingView();
        }
    }

    @Override
    public void showSuccess() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showContent();
        }
    }

    @Override
    public void showFaild() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showErrorView();
        }
    }

    @Override
    public void showNoNet() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showNoNetView();
        }
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        unbinder.unbind();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    public void handleMessage(Message msg) {
    }

    private static class ActivityHandler extends Handler {
        WeakReference<BaseActivity> mRefActivity;

        private ActivityHandler(BaseActivity activity) {
            this.mRefActivity = new WeakReference(activity);
        }

        public void handleMessage(Message msg) {
            if (this.mRefActivity != null) {
                BaseActivity activity = (BaseActivity) this.mRefActivity.get();
                if (activity != null && !activity.isFinishing()) {
                    activity.handleMessage(msg);
                }
            }
        }
    }
}
