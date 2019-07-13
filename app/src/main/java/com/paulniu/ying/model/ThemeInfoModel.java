package com.paulniu.ying.model;

import java.io.Serializable;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-13
 * Time: 18:13
 * Desc: 设置主题信息model
 * Version:
 */
public class ThemeInfoModel implements Serializable {

    private long themeTime;

    private int themeColor;

    private String themePhoto;

    private boolean isSetting;

    public boolean isSetting() {
        return isSetting;
    }

    public void setSetting(boolean setting) {
        isSetting = setting;
    }

    public long getThemeTime() {
        return themeTime;
    }

    public void setThemeTime(long themeTime) {
        this.themeTime = themeTime;
    }

    public int getThemeColor() {
        return themeColor;
    }

    public void setThemeColor(int themeColor) {
        this.themeColor = themeColor;
    }

    public String getThemePhoto() {
        return themePhoto;
    }

    public void setThemePhoto(String themePhoto) {
        this.themePhoto = themePhoto;
    }
}
