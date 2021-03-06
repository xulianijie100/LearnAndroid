package com.hy.android.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

public interface BaseContract {

    interface BasePresenter<T extends BaseView> {

        void attachView(T view);

        void detachView();
    }


    interface BaseView {

        //显示进度中
        void showLoading();

        //显示请求成功
        void showSuccess();

        //失败重试
        void showFaild();

        //显示当前网络不可用
        void showNoNet();

        /**
         * 绑定生命周期
         *
         * @param <T>
         * @return
         */
        <T> LifecycleTransformer<T> bindToLife();

    }
}
