package com.paulniu.ying.util.infomanager;

import com.niupuyue.mylibrary.utils.BaseUtility;
import com.paulniu.ying.model.ThemeInfoModel;

import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-13
 * Time: 18:12
 * Desc: 主题信息管理类
 * Version:
 */
public class ThemeInfoManager {

    private List<ThemeInfoModel> themeInfoes;

    private ThemeInfoModel themeInfoModel;

    private static class ThemeInfoManagerHelper {
        private static final ThemeInfoManager INSTANCE = new ThemeInfoManager();
    }

    public static ThemeInfoManager getInstance() {
        return ThemeInfoManagerHelper.INSTANCE;
    }

    public synchronized ThemeInfoModel getCurrentThemeInfo() {
        if (null == themeInfoModel) {
            themeInfoModel = SQLiteDataBaseHelperManager.getCurThemeInfo();
        }
        return themeInfoModel;
    }

    public synchronized List<ThemeInfoModel> getThemeInfoes() {
        if (BaseUtility.isEmpty(themeInfoes)) {
            themeInfoes = SQLiteDataBaseHelperManager.getSettedThemeInfoes();
        }
        return themeInfoes;
    }

}
