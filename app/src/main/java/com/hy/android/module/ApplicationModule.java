package com.hy.android.module;

import android.content.Context;
import com.hy.android.base.BaseApplication;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Context mContext;

    public ApplicationModule(Context context) {
        this.mContext = context;
    }

    @Provides
    BaseApplication provideApplication() {
        return (BaseApplication) mContext.getApplicationContext();
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
