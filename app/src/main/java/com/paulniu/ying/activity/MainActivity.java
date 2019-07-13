package com.paulniu.ying.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.amap.api.location.AMapLocation;
import com.orhanobut.logger.Logger;
import com.paulniu.ying.R;
import com.paulniu.ying.base.BaseActivity;
import com.paulniu.ying.callback.ILocationCallback;
import com.paulniu.ying.util.LocationUtility;

public class MainActivity extends BaseActivity {

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

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
