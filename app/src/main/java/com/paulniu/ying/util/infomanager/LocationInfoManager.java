package com.paulniu.ying.util.infomanager;

import com.paulniu.ying.model.LocationInfoModel;
import com.paulniu.ying.util.SharedPreferenceUtility;

/**
 * Coder: niupuyue
 * Date: 2019/7/10
 * Time: 11:55
 * Desc: 定位信息管理类
 * Version:
 */
public class LocationInfoManager {

    private LocationInfoModel locationInfo;

    private LocationInfoManager() {

    }

    private static class LocationManagerHolder {
        private static LocationInfoManager INSTANCE = new LocationInfoManager();
    }

    public static LocationInfoManager newInstance() {
        return LocationInfoManager.LocationManagerHolder.INSTANCE;
    }

    public synchronized LocationInfoModel getLocationInfo() {
        if (null == locationInfo) {
            locationInfo = SharedPreferenceUtility.getLocation();
        }
        return locationInfo;
    }

    public synchronized void cacheLocationInfo(LocationInfoModel locationInfoModel) {
        if (null != locationInfoModel) {
            SharedPreferenceUtility.setLocation(locationInfoModel);
        }
    }

}
