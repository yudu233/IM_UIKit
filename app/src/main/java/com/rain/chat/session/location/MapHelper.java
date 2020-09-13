package com.rain.chat.session.location;

import android.content.Context;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;


/**
 * @author : duyu
 * @date : 2018/8/30 15:15
 * @filename : MapHelper
 * @describe : 地图工具类
 */
public class MapHelper {

    private static AMap aMap;
    private static Marker marker;

    private static MapView mMapView;

    /**
     * 初始化地图
     */
    public static void initMap(Context context, MapView mapView) {
        mMapView = mapView;
        //获取TencentMap实例
        aMap = mapView.getMap();
        //设置缩放级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(12));
        //获取UiSettings实例
        UiSettings uiSettings = aMap.getUiSettings();
        //启用缩放手势
        uiSettings.setZoomGesturesEnabled(true);
        //v显示或隐藏比例尺
        uiSettings.setScaleControlsEnabled(false);
        //设置是否允许Scroll
        uiSettings.setScrollGesturesEnabled(true);
        //设置缩放按钮是否可见
        uiSettings.setZoomControlsEnabled(false);

    }

    public static void initMessageMap(Context context, MapView mapView) {
        mMapView = mapView;
        aMap = mapView.getMap();
        //设置缩放级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(12));

        //获取UiSettings实例
        UiSettings uiSettings = aMap.getUiSettings();
        //设置缩放按钮是否可见
        uiSettings.setZoomControlsEnabled(false);
        //启用缩放手势
        uiSettings.setZoomGesturesEnabled(false);
        //v显示或隐藏比例尺
        uiSettings.setScaleControlsEnabled(false);
        //设置是否允许Scroll
        uiSettings.setScrollGesturesEnabled(false);
    }



    public static void setMark(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
         marker = aMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker()).draggable(false));
        setMarker(marker);

    }

    public static void moveMap(double latitude, double longitude) {
        moveMap(latitude, longitude, 17);
    }

    public static void moveMap(double latitude, double longitude, int zoom) {
        if(marker != null){
            marker.remove();
        }
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(new LatLng(latitude, longitude), //新的中心点坐标
                zoom));
        //移动地图
        aMap.stopAnimation();
        aMap.moveCamera(cameraUpdate);
        setMark(latitude, longitude);
    }




    public static AMap getTencentMap() {
        return aMap;
    }

    public static Marker getMarker() {
        return marker;
    }

    public static void setMarker(Marker marker) {
        MapHelper.marker = marker;
    }

    public static MapView getmMapView() {
        return mMapView;
    }
}
