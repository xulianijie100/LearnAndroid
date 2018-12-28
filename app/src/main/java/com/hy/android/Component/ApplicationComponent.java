package com.hy.android.Component;

import android.content.Context;
import com.hy.android.base.BaseApplication;
import com.hy.android.module.ApplicationModule;
import com.hy.android.module.HttpModule;
import dagger.Component;

@Component(modules = {ApplicationModule.class, HttpModule.class})
public interface ApplicationComponent {

    BaseApplication getApplication();

    Context getContext();
}
