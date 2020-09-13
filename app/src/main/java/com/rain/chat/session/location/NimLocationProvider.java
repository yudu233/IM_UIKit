package com.rain.chat.session.location;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ycbl.im.uikit.api.model.location.LocationProvider;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/3 18:50
 * @Version : 1.0
 * @Descroption : 地图信息提供者
 */
public class NimLocationProvider implements LocationProvider {
    @Override
    public void requestLocation(final Context context, Callback callback) {

        MapLocationActivity.start(context, callback);
    }

    @Override
    public void openMap(Context context, double longitude, double latitude, String address) {
        Intent intent = new Intent(context, NavigationMapActivity.class);
        Bundle bundle = new Bundle();
        bundle.putDouble(LocationExtras.LONGITUDE, longitude);
        bundle.putDouble(LocationExtras.LATITUDE, latitude);
        bundle.putString(LocationExtras.ADDRESS, address);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static class LocationExtras {
        public static int DEFAULT_ZOOM_LEVEL = 15;
        public static String LATITUDE = "latitude";
        public static String LONGITUDE = "longitude";
        public static String ADDRESS = "address";
    }
}
