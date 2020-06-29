package com.rain.messagelist.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @Author : Rain
 * @CreateDate : 2020/6/29 16:43
 * @Version : 1.0
 * @Descroption : 屏幕相关工具类
 */
public class ScreenUtils {
    private static final String TAG = "ScreenUtils";

    public static float density;
    public static float scaleDensity;

    public static int screenWidth;
    public static int screenHeight;

    public static void init(Context context) {
        DisplayMetrics displayMetrics = context.getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        density = displayMetrics.density;
        scaleDensity = displayMetrics.scaledDensity;
    }

    public static int dip2px(float dipValue) {
        return (int) (dipValue * density + 0.5f);
    }

    public static int px2dip(float pxValue) {
        return (int) (pxValue / density + 0.5f);
    }

    public static int sp2px(float spValue) {
        return (int) (spValue * scaleDensity + 0.5f);
    }


}
