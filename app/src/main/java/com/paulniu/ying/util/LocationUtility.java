package com.paulniu.ying.util;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.orhanobut.logger.Logger;
import com.paulniu.ying.App;
import com.paulniu.ying.callback.ILocationCallback;

/**
 * Coder: niupuyue
 * Date: 2019/7/10
 * Time: 10:50
 * Desc: 高德地图定位工具类
 * Version:
 */
public class LocationUtility implements AMapLocationListener {

    public static LocationUtility getInstance() {
        return LocationUtilityHelper.instance;
    }

    private static final class LocationUtilityHelper {
        private static LocationUtility instance = new LocationUtility();
    }

    private ILocationCallback callback;
    private AMapLocationClient client;

    public void setCallback(ILocationCallback callback) {
        this.callback = callback;
    }

    private LocationUtility() {
        if (client == null) {
            client = new AMapLocationClient(App.getAppContext());
            client.setLocationListener(this);// 设置定位监听
            AMapLocationClientOption option = new AMapLocationClientOption();
            // 设置需要获取详细地址
            option.setNeedAddress(true);
            // 设置定位模式
            option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            option.setHttpTimeOut(3 * 1000);
            //设置是否获取海拔高度
            option.setGpsFirst(true);
            option.setSensorEnable(true);
            //设置定位参数
            client.setLocationOption(option);
        }
    }

    /**
     * 开始定位
     */
    public void startLocation() {
        if (client != null) {
            client.startLocation();
        }
    }

    /**
     * 停止定位
     */
    public void stopLocation() {
        if (client == null) return;
        try {
            client.stopLocation();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void destory() {
        if (client == null) return;
        try {
            if (client.isStarted()) {
                client.stopLocation();
            }
            client.onDestroy();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 定位状态发生改变
     *
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        try {
            if (aMapLocation.getErrorCode() == AMapLocation.LOCATION_SUCCESS) {
                // 定位成功
                if (callback != null) {
                    callback.onSuccess(aMapLocation);
                }
                Logger.e(aMapLocation.getAddress());
                stopLocation();
            } else {
                // 定位失败
                stopLocation();
                Logger.e(aMapLocation.getErrorInfo());
                if (callback != null) {
                    callback.onFail(aMapLocation);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
