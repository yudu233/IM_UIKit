package com.ycbl.im.uikit.api;

import android.content.Context;

import com.ycbl.im.uikit.api.model.location.LocationProvider;
import com.ycbl.im.uikit.impl.NimUIKitImpl;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/4 14:40
 * @Version : 2.0
 * @Descroption : 云信UI组件接口/定制化入口
 */
public class NimUIKit {


    public static void init(Context context) {
//        NimUIKitImpl.init(context);
    }

    /**
     * 设置位置信息提供者
     *
     * @param locationProvider 位置信息提供者
     */
    public static void setLocationProvider(LocationProvider locationProvider) {
        NimUIKitImpl.setLocationProvider(locationProvider);
    }
}

