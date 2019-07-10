package com.paulniu.ying.callback;

import com.amap.api.location.AMapLocation;

/**
 * Coder: niupuyue
 * Date: 2019/7/10
 * Time: 10:51
 * Desc: 定位结果监听回调
 * Version:
 */
public interface ILocationCallback {

    void onSuccess(AMapLocation aMapLocation);

    void onFail(AMapLocation aMapLocation);

}
