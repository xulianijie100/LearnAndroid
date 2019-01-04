package com.hy.android.component;

import com.hy.android.news.DetailFragment;
import com.hy.android.news.NewsActivity;
import dagger.Component;

@Component(dependencies = ApplicationComponent.class)
public interface HttpComponent {


    void inject(DetailFragment detailFragment);

    void inject(NewsActivity newsActivity);

}
