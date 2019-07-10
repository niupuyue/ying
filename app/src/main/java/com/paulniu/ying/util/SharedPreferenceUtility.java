package com.paulniu.ying.util;

import android.app.Activity;
import android.content.SharedPreferences;

import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.utils.SpanStringUtility;
import com.paulniu.ying.App;
import com.paulniu.ying.constant.SharedConfig;
import com.paulniu.ying.model.LocationInfoModel;

/**
 * Coder: niupuyue
 * Date: 2019/7/10
 * Time: 13:58
 * Desc: 本机的Shared存储
 * Version:
 */
public class SharedPreferenceUtility {

    private static SharedPreferences mySharedPreferences = null;

    public static SharedPreferences getSharedPreferencesInstance() {
        if (mySharedPreferences == null && null != App.getAppContext()) {
            mySharedPreferences = App.getAppContext().getSharedPreferences(SharedConfig.SHARED_SVAE_LOCAL_NAME, Activity.MODE_PRIVATE);
        }
        return mySharedPreferences;
    }

    /**
     * 获取本地存储的位置信息对象
     */
    public static LocationInfoModel getLocation() {
        LocationInfoModel model = new LocationInfoModel();
        String locationInfoString = getSharedPreferencesInstance().getString(SharedConfig.SHARED_SAVE_LOCATION, "");
        try {
            if (!BaseUtility.isEmpty(locationInfoString)) {
                model = (LocationInfoModel) SpanStringUtility.deSerializationToObject(locationInfoString);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (model == null) {
            model = new LocationInfoModel();
        }
        return model;
    }

    /**
     * 将定位信息存储到本地
     */
    public static void setLocation(LocationInfoModel model) {
        if (null == model) return;
        try {
            SharedPreferences.Editor editor = getSharedPreferencesInstance().edit();
            editor.putString(SharedConfig.SHARED_SAVE_LOCATION, SpanStringUtility.serializeToString(model));
            editor.apply();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 获取最后一次查看引导页的版本
     */
    public static String getLastSeeWelcomeVersion() {
        String versionName = "";
        try {
            versionName = getSharedPreferencesInstance().getString(SharedConfig.SHARED_SAVE_LAST_SEE_WELCOME_VERSION, "0");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return versionName;
    }

    /**
     * 设置最后一次查看引导页的版本
     * @param versionName
     */
    public static void setLastSeeWelcomeVersion(String versionName) {
        if (BaseUtility.isEmpty(versionName)) return;
        try {
            SharedPreferences.Editor editor = getSharedPreferencesInstance().edit();
            editor.putString(SharedConfig.SHARED_SAVE_LAST_SEE_WELCOME_VERSION, versionName);
            editor.apply();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
