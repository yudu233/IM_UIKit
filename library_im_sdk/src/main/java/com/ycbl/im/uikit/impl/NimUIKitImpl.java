package com.ycbl.im.uikit.impl;

import com.ycbl.im.uikit.api.model.location.LocationProvider;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/4 14:42
 * @Version : 2.0
 * @Descroption : UIKit能力实现类
 */
public class NimUIKitImpl {

    // 地理位置信息提供者
    private static LocationProvider locationProvider;

    public static void setLocationProvider(LocationProvider locationProvider) {
        NimUIKitImpl.locationProvider = locationProvider;
    }

    public static LocationProvider getLocationProvider() {
        return locationProvider;
    }
}
