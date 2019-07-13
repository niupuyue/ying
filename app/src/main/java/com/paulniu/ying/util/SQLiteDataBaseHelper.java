package com.paulniu.ying.util;

import com.paulniu.ying.model.ThemeInfoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-13
 * Time: 18:22
 * Desc: 数据库操作接口
 * Version:
 */
public class SQLiteDataBaseHelper {

    // 获取数据库中所有已经设置过的主题样式
    public static List<ThemeInfoModel> getSettedThemeInfoes() {
        List<ThemeInfoModel> themeInfoModels = new ArrayList<>();

        return themeInfoModels;
    }

    // 获取当前正在设置的主题样式
    public static ThemeInfoModel getCurThemeInfo() {
        ThemeInfoModel model = new ThemeInfoModel();

        return model;
    }

}
