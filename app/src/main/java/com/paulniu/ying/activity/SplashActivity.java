package com.paulniu.ying.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.niupuyue.mylibrary.callbacks.ISimpleDialogButtonClickCallback;
import com.niupuyue.mylibrary.widgets.SimpleDialog;
import com.paulniu.ying.R;
import com.paulniu.ying.base.BaseActivity;
import com.paulniu.ying.callback.ILocationCallback;
import com.paulniu.ying.model.LocationInfoModel;
import com.paulniu.ying.util.BusinessUtility;
import com.paulniu.ying.util.LocationUtility;
import com.paulniu.ying.util.infomanager.LocationInfoManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.ref.WeakReference;

import io.reactivex.functions.Consumer;

/**
 * Coder: niupuyue
 * Date: 2019/7/10
 * Time: 9:51
 * Desc: 闪屏页
 * Version:
 */
public class SplashActivity extends BaseActivity implements ILocationCallback {

    // 动态请求权限
    final RxPermissions rxPermissions = new RxPermissions(this);

    class SplashActivityHandler extends Handler{
        WeakReference<Activity> mWeakReference;
        public SplashActivityHandler(Activity activity) {
            mWeakReference = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            if (mWeakReference == null || mWeakReference.get() == null || mWeakReference.get().isFinishing())
                return;

//            if (msg.what == Handler_LoadData) {
//                //重新加载数据
//                loadData(true, false);
//            }
        }
    }

    // 是否展示欢迎语或者提醒事务
    private boolean isShowedFestivals = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initViewByFindViewById();
        initListener();
        initLocation();

    }

    private void initViewByFindViewById() {

    }

    private void initListener() {
        LocationUtility.getInstance().setCallback(this);
    }

    private void initLocation() {
        // 获取定位信息之前判断权限是否获取成功
        rxPermissions
                .request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            // 获取权限
                            LocationUtility.getInstance().startLocation();

                        } else {
                            // 弹窗提示信息
                            SimpleDialog.showSimpleDialog(SplashActivity.this, getString(R.string.permission_ask_fail_content), new ISimpleDialogButtonClickCallback() {
                                @Override
                                public void onLeftButtonClick() {
                                    // 点击取消按钮，进入应用系统设置页面
                                }

                                @Override
                                public void onRightButtonClick() {
                                    // 重新请求权限
                                    initLocation();
                                }

                                @Override
                                public void onCancel() {

                                }
                            });
                        }
                    }
                });
    }

    private void toNextView(){
        if (BusinessUtility.isNeedShowWelcomePage()){
            // 进入欢迎页面
            startActivity(new Intent(SplashActivity.this,WelcomeActivity.class));
            finish();
        }else if (isShowedFestivals){
            showFestivals();
        }
    }

    private void showFestivals(){

    }

    @Override
    public void onSuccess(AMapLocation aMapLocation) {
        // 定位成功
        LocationInfoModel model = LocationInfoManager.newInstance().getLocationInfo();
        model.setAdCode(aMapLocation.getAdCode());
        model.setAddress(aMapLocation.getAddress());
        model.setAltitude(aMapLocation.getAltitude());
        model.setCityName(aMapLocation.getCity());
        model.setCountry(aMapLocation.getCountry());
        model.setLatitude(aMapLocation.getLatitude());
        model.setLongitude(aMapLocation.getLongitude());
        model.setProvince(aMapLocation.getProvince());
        model.setStreet(aMapLocation.getStreet());
        model.setStreetNum(aMapLocation.getStreetNum());
        LocationInfoManager.newInstance().cacheLocationInfo(model);
    }

    @Override
    public void onFail(AMapLocation aMapLocation) {
        // 定位失败
    }
}
