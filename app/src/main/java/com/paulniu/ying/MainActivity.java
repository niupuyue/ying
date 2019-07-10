package com.paulniu.ying;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amap.api.location.AMapLocation;
import com.orhanobut.logger.Logger;
import com.paulniu.ying.callback.ILocationCallback;
import com.paulniu.ying.util.LocationUtility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 设置获取定位
        LocationUtility.getInstance().setCallback(new ILocationCallback() {
            @Override
            public void onSuccess(AMapLocation aMapLocation) {
                Logger.e("");
            }

            @Override
            public void onFail(AMapLocation aMapLocation) {

            }
        });
        LocationUtility.getInstance().startLocation();
    }

}
