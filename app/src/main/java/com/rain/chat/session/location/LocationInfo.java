package com.rain.chat.session.location;

/**
 * @author : duyu
 * @date : 2019/1/24 11:28
 * @filename : LocationInfo
 * @describe : 地图位置信息
 */
public class LocationInfo {

    private String Name;
    private String Address;
    private double lat;
    private double lng;
    private String cityCode;

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "Name='" + Name + '\'' +
                ", Address='" + Address + '\'' +
                '}';
    }
}
