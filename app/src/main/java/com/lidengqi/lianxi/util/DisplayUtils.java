package com.lidengqi.lianxi.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 在此添加[类的功能描述]
 *
 * @author dengqi.li
 * @date 2020/05/08
 */
public class DisplayUtils {

    public static int px2dp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5F);
    }

    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5F);
    }

    public static int px2sp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(pxValue / scale + 0.5F);
    }

    public static int sp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(dpValue * scale + 0.5F);
    }

    public static int getScreenWidthPixels(Activity context) {
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    public static int getScreenHeightPixels(Activity context) {
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }
}
