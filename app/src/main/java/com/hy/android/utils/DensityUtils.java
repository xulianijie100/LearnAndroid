package com.hy.android.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by Administrator on 2018/6/21.
 */

public class DensityUtils {

    public static int dp2px(Context context, float dpVal) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,

                dpVal, context.getResources().getDisplayMetrics());

    }

    /**
     * sp转px
     *
     * @param context
     * @param
     * @return
     */

    public static int sp2px(Context context, float spVal) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,

                spVal, context.getResources().getDisplayMetrics());

    }


    /**
     * px转dp
     *
     * @param context
     * @param
     * @return
     */

    public static int px2dp(Context context, float pxValue) {

        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (pxValue / scale + 0.5f);

    }


    /**
     * px转sp
     *
     * @param
     * @param pxVal
     * @return
     */

    public static float px2sp(Context context, float pxVal) {

        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);

    }


}
