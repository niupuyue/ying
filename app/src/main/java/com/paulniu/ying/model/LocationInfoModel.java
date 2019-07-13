package com.paulniu.ying.model;

import java.io.Serializable;

/**
 * Coder: niupuyue
 * Date: 2019/7/10
 * Time: 11:56
 * Desc: 定位对象
 * Version:
 */
public class LocationInfoModel implements Serializable {

    // 位置code
    private String adCode;
    // 城市名称
    private String cityName;
    // 海拔信息
    private double altitude;
    // 城市信息
    private String address;
    // 国家名称
    private String country;
    // 纬度
    private double latitude;
    // 经度
    private double longitude;
    // 省份
    private String province;
    // 街道
    private String street;
    // 门牌号
    private String streetNum;

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNum() {
        return streetNum;
    }

    public void setStreetNum(String streetNum) {
        this.streetNum = streetNum;
    }
}
