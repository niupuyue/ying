package com.paulniu.ying.util;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.niupuyue.mylibrary.utils.BaseUtility;
import com.paulniu.ying.App;
import com.paulniu.ying.model.AffairModel;
import com.paulniu.ying.model.FestivalModel;

import java.util.ArrayList;
import java.util.List;

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
     *
     * @return
     */
    public static boolean isNeedShowWelcomePage() {
        boolean value = true;
        try {
            String oldVersion = SharedPreferenceUtility.getLastSeeWelcomeVersion();
            if (BaseUtility.isEmpty(oldVersion) || BaseUtility.equals("0", oldVersion)) {
                // 没有查看过引导页
                value = true;
            } else {
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return value;
    }

    /**
     * 已经展示过了引导页
     */
    public static void showdWelcomePage() {
        try {
            final String versionName = App.getAppContext().getPackageManager().getPackageInfo(App.getAppContext().getPackageName(), 0).versionName;
            SharedPreferenceUtility.setLastSeeWelcomeVersion(versionName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 设置系统默认的节日列表
     * TODO
     */
    public static List<FestivalModel> getSysFestivals() {
        List<FestivalModel> festivalModels = new ArrayList<>();
        // 元旦
        FestivalModel newYearFestival = new FestivalModel();
        newYearFestival.setFestivalDate(1546272000000L);
        newYearFestival.setFestivalName("元旦");
        newYearFestival.setFestivalNote("他年妙笔开天路，万驾仙鸾纸上来");
        // 情人节
        FestivalModel valentineFestival = new FestivalModel();
        valentineFestival.setFestivalNote("愿得一人心，白首不分离");
        valentineFestival.setFestivalName("情人节");
        valentineFestival.setFestivalDate(1550073600000L);
        // 中国新年
        FestivalModel cnNewYearFestival = new FestivalModel();
        cnNewYearFestival.setFestivalDate(1549296000000L);
        cnNewYearFestival.setFestivalName("新年");
        cnNewYearFestival.setFestivalNote("欢天喜地中国年");
        // 元宵节
        FestivalModel lanterFestival = new FestivalModel();
        lanterFestival.setFestivalNote("元宵节快乐");
        lanterFestival.setFestivalName("元宵节");
        lanterFestival.setFestivalDate(1550505600000L);
        // 七夕
        FestivalModel qixiFestival = new FestivalModel();
        qixiFestival.setFestivalDate(1565107200000L);
        qixiFestival.setFestivalName("七夕");
        qixiFestival.setFestivalNote("");
        return festivalModels;
    }

    /**
     * 获取我的日常事宜 今天的
     * TODO
     */
    public static List<AffairModel> getAffairInfos() {
        List<AffairModel> affairModels = new ArrayList<>();

        return affairModels;
    }

    /**
     * 获取我的日常事宜中最重要的  今天的
     * TODO
     */
    public static AffairModel getImportAffair() {
        AffairModel model = null;
        try {
            if (!BaseUtility.isEmpty(getAffairInfos())) {
                for (AffairModel currModel : getAffairInfos()) {
                    if (null != currModel && currModel.getAffairType() == -1) {
                        model = currModel;
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return model;
    }

}
