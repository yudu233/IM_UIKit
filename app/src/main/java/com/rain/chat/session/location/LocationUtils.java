package com.rain.chat.session.location;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;

/**
 * 辅助工具类
 *
 * @author xiaobo
 * @创建时间： 2015年11月24日 上午11:46:50
 * @项目名称： AMapLocationDemo2.x
 * @文件名称: Utils.java
 * @类型名称: Utils
 */
public class LocationUtils implements AMapLocationListener {


    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption;

    private LocationCallBack callBack;

    public void setCallBack(LocationCallBack callBack) {
        this.callBack = callBack;
    }

    public LocationUtils(Context context) {
        locationClient = new AMapLocationClient(context);
        locationOption = new AMapLocationClientOption();
        locationOption.setOnceLocation(true);
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationMode.Battery_Saving);
        locationOption.setOnceLocationLatest(false);
//        locationOption.setInterval(60*1000);
        // 设置定位监听
        locationClient.setLocationListener(this);

        locationClient.setLocationOption(locationOption);
        locationOption.setNeedAddress(true);

    }


    @Override
    public void onLocationChanged(AMapLocation loc) {
        if (null != loc) {
            if (callBack != null) {
                callBack.locationSuccess(loc);
            }
        } else {
            if (callBack != null) {
                callBack.locationFailed();
            }
        }
    }

    public void startLoc() {
        // 启动定位
        locationClient.startLocation();
        if (callBack != null) {
            callBack.locationStart();
        }
    }

    public void stopLoc() {
        // 启动定位
        locationClient.stopLocation();
        if (callBack != null) {
            callBack.locationStop();
        }
    }

    public void onDestory() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.stopLocation();
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }


    public interface LocationCallBack {
         void locationStart();

         void locationStop();

         void locationSuccess(AMapLocation loc);

         void locationFailed();

    }
}
