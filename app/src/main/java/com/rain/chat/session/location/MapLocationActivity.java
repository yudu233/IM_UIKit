package com.rain.chat.session.location;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amap.api.location.AMapLocation;
import com.amap.api.maps.MapView;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rain.chat.R;
import com.rain.chat.base.BaseActivity;
import com.ycbl.im.uikit.api.model.location.LocationProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.Nullable;

/**
 * @author : duyu
 * @CreateDate : 2019/1/24 10:20
 * @Version : 1.0
 * @ChnageDate : 2020/9/1 14:42
 * @Version : 2.0
 * @filename : MapLocationActivity
 * @describe : 地图位置选择
 */
public class MapLocationActivity extends BaseActivity implements LocationUtils.LocationCallBack {

    private AppCompatEditText mSearchView;
    private RecyclerView mRecyclerView;
    private AppCompatTextView mNoDataView;
    private MapLocationAdapter mapLocationAdapter;
    private static LocationProvider.Callback callback;
    private MapView mMapView;
    private LocationUtils locationUtils;
    private LocationInfo locationInfo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nim_activity_maplocation);
        mSearchView = findViewById(R.id.edt_search);
        mNoDataView = findViewById(R.id.noDataView);
        mMapView = findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        setToolbarTitle(getString(R.string.nim_title_location), true);

        initSearchView();
        initLocation();
        initList();
    }

    private void initSearchView() {
        mSearchView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
                if (isGPSEnabled()) {
                    getPoiList(locationInfo, null, mSearchView.getText().toString().trim());
                }
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_common, menu);
        menu.findItem(R.id.menu_title).setTitle(getString(R.string.text_send));
        menu.findItem(R.id.menu_title).setOnMenuItemClickListener(item -> {
            sendLocationMessage();
            return false;
        });
        return true;
    }

    private void sendLocationMessage() {
        if (locationInfo == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(NimLocationProvider.LocationExtras.LATITUDE, locationInfo.getLat());
        intent.putExtra(NimLocationProvider.LocationExtras.LONGITUDE, locationInfo.getLng());
        String addressInfo = TextUtils.isEmpty(locationInfo.getAddress()) ?
                getResources().getString(R.string.nim_no_location_info) :
                String.format(getResources().getString(R.string.str_current_loc),
                        locationInfo.getName(), locationInfo.getAddress());
        intent.putExtra(NimLocationProvider.LocationExtras.ADDRESS, addressInfo);
        if (callback != null) {
            callback.onSuccess(locationInfo.getLng(), locationInfo.getLat(), addressInfo);
            finish();
        }
    }

    private void initLocation() {
        locationUtils = new LocationUtils(MapLocationActivity.this);
        locationUtils.setCallBack(this);
        locationUtils.startLoc();
        MapHelper.initMap(this, mMapView);
    }

    private void initList() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mapLocationAdapter = new MapLocationAdapter();
        mRecyclerView.setAdapter(mapLocationAdapter);
        mapLocationAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (MapHelper.getMarker() != null) {
                    MapHelper.getMarker().remove();
                }
                locationInfo = (LocationInfo) adapter.getData().get(position);
                mapLocationAdapter.setSelected(position);
                mapLocationAdapter.notifyDataSetChanged();
                MapHelper.moveMap(locationInfo.getLat(), locationInfo.getLng());
            }
        });
    }

    public boolean isGPSEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            new MaterialDialog.Builder(this)
                    .title(getResources().getString(R.string.dialog_title))
                    .content(getResources().getString(R.string.nim_location_desc))
                    .positiveText(getResources().getString(R.string.dialog_text_setting))
                    .negativeText(getResources().getString(R.string.dialog_text_cancel))
                    .onPositive((dialog, which) -> {
                        // 转到手机设置界面，用户设置GPS
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, 0);
                    }).onNegative((dialog, which) -> dialog.dismiss()).show();
            return false;
        } else {
            return true;
        }
    }

    private void getPoiList(LocationInfo loc, AMapLocation location, String address) {
        PoiSearch.Query query;
        //周边检索POI
        query = new PoiSearch.Query(address, "", loc.getCityCode());
        //keyWord表示搜索字符串，
        //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);//设置查询页码
        PoiSearch poiSearch = new PoiSearch(this, query);
        if (location != null) {
            LatLonPoint searchLatLonPoint = new LatLonPoint(location.getLatitude(), location.getLongitude());
            poiSearch.setBound(new PoiSearch.SearchBound(searchLatLonPoint, 500, true));
        }
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int rCode) {
                List<LocationInfo> locationInfoList = new ArrayList<>();
                for (int j = 0; j < poiResult.getPois().size(); j++) {
                    PoiItem poiItem = poiResult.getPois().get(j);
                    LocationInfo locationInfo = new LocationInfo();
                    locationInfo.setName(poiItem.getCityName() + poiItem.getTitle());
                    locationInfo.setAddress(poiItem.getSnippet());
                    locationInfo.setLat(poiItem.getLatLonPoint().getLatitude());
                    locationInfo.setLng(poiItem.getLatLonPoint().getLongitude());
                    locationInfo.setCityCode(poiItem.getCityCode());
                    locationInfoList.add(locationInfo);
                }
                //移动到搜索的第一个位置
                if (locationInfoList.size() != 0) {
                    mapLocationAdapter.setSelected(0);
                    mRecyclerView.scrollToPosition(0);
                    locationInfo = locationInfoList.get(0);
                    MapHelper.moveMap(locationInfo.getLat(), locationInfo.getLng());
                }
                if (locationInfoList.size() == 0) {
                    mNoDataView.setVisibility(View.VISIBLE);
                    mapLocationAdapter.setNewData(locationInfoList);
                    mapLocationAdapter.notifyDataSetChanged();
                    return;
                }
                mNoDataView.setVisibility(View.GONE);
                mapLocationAdapter.setNewData(locationInfoList);
                mapLocationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.searchPOIAsyn();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == 0) {
            locationUtils.startLoc();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
        isGPSEnabled();
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
        locationUtils.onDestory();
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationUtils.stopLoc();//修复停止定位
    }

    public static void start(Context context, LocationProvider.Callback callback) {
        MapLocationActivity.callback = callback;
        context.startActivity(new Intent(context, MapLocationActivity.class));
    }

    @Override
    public void locationStart() {

    }

    @Override
    public void locationStop() {

    }

    @Override
    public void locationSuccess(AMapLocation loc) {
        locationInfo = new LocationInfo();
        locationInfo.setAddress(loc.getAddress());
        locationInfo.setLat(loc.getLatitude());
        locationInfo.setLng(loc.getLongitude());
        locationInfo.setCityCode(loc.getCityCode());
        getPoiList(locationInfo, loc, loc.getPoiName());
    }

    @Override
    public void locationFailed() {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
}
