package com.hy.android.ui.news.contract;

import com.hy.android.base.BaseContract;
import com.hy.android.bean.Channel;

import java.util.List;

public interface NewsContract {

    interface View extends BaseContract.BaseView{

        void loadData(List<Channel> channels, List<Channel> otherChannels);

    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        /**
         * 初始化频道
         */
        void getChannel();

    }

}
