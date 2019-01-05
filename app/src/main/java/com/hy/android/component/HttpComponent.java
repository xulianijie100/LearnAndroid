package com.hy.android.component;

import com.hy.android.ui.news.DetailFragment;
import com.hy.android.ui.news.NewsFragment;

import dagger.Component;

@Component(dependencies = ApplicationComponent.class)
public interface HttpComponent {

    void inject(NewsFragment newsActivity);

    void inject(DetailFragment detailFragment);

}
