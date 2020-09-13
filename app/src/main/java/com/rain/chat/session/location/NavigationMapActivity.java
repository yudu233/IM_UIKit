package com.rain.chat.session.location;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.MapView;
import com.rain.chat.R;


/**
 * @author : duyu
 * @date : 2019/1/24 11:47
 * @filename : NavigationMapActivity
 * @describe : 地图预览页面
 */
public class NavigationMapActivity extends AppCompatActivity {

    private MapView mMapView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nim_activity_map_preview);

        double latitude = getIntent().getExtras().getDouble("latitude");
        double longitude = getIntent().getExtras().getDouble("longitude");
        String address = getIntent().getExtras().getString("address");


        findViewById(R.id.ll_back).setOnClickListener(v -> finish());
        mMapView = findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        MapHelper.initMap(this, mMapView);
        //浏览地图
        MapHelper.moveMap(latitude, longitude);
        MapHelper.setMark(latitude, longitude);
        TextView mAddressName = findViewById(R.id.txv_address);
        mAddressName.setText(address);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
}
