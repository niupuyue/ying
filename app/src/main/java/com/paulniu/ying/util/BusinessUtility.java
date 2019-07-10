package com.paulniu.ying.util;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.niupuyue.mylibrary.utils.BaseUtility;
import com.paulniu.ying.App;

/**
 * Coder: niupuyue
 * Date: 2019/7/10
 * Time: 19:24
 * Desc: 业务逻辑方法
 * Version:
 */
public class BusinessUtility {

    /**
     * 是否展示引导页
     * @return
     */
    public static boolean isNeedShowWelcomePage(){
        boolean value = true;
        try {
            String oldVersion = SharedPreferenceUtility.getLastSeeWelcomeVersion();
            if (BaseUtility.isEmpty(oldVersion) || BaseUtility.equals("0",oldVersion)){
                // 没有查看过引导页
                value = true;
            }else {
                // 之前查看过引导页
                final String versionName = App.getAppContext().getPackageManager().getPackageInfo(App.getAppContext().getPackageName(), 0).versionName;
                if (oldVersion.equalsIgnoreCase(versionName)) {
                    value = false;
                } else {
                    // 当前版本是否需要查看
                    ApplicationInfo appInfo = App.getAppContext().getPackageManager().getApplicationInfo(App.getAppContext().getPackageName(), PackageManager.GET_META_DATA);
                    value = appInfo.metaData.getBoolean("IsChangeSplash");
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return value;
    }

}
