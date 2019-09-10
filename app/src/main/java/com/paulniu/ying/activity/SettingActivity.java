package com.paulniu.ying.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.niupuyue.mylibrary.widgets.CircleImageView;
import com.paulniu.ying.R;
import com.paulniu.ying.base.BaseActivity;
import com.paulniu.ying.constant.AppConfig;
import com.paulniu.ying.widget.popup.AddAffairOrTallyPop;

import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.licenses.BSD3ClauseLicense;
import de.psdev.licensesdialog.licenses.MITLicense;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-13
 * Time: 16:51
 * Desc: 设置页面
 * Version:
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        return intent;
    }

    private Toolbar toolbar;
    private LinearLayout llSettingActivitySwitchTheme;
    private LinearLayout llSettingActivityChangeTextSize;
    private LinearLayout llSettingActivityChangeIcon;
    private LinearLayout llSettingActivityChangeColor;
    private LinearLayout llSettingActivityVersion;
    private LinearLayout llSettingActivityChangeLog;
    private LinearLayout llSettingActivityOpen;
    private LinearLayout llSettingActivitySource;
    private LinearLayout llSettingActivityLicense;
    private TextView tvSettingActivitySource;
    private TextView tvSettingActivityLog;
    private TextView tvSettingActivityVersion;
    private CircleImageView civSettingActivityColor;
    private CircleImageView ivSettingActivityChangeIcon;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initViewByFindViewById();
        initListener();
        initDataAfterListener();

    }

    private void initViewByFindViewById() {
        toolbar = findViewById(R.id.toolbar);

        tvSettingActivitySource = findViewById(R.id.tvSettingActivitySource);
        tvSettingActivityLog = findViewById(R.id.tvSettingActivityLog);
        tvSettingActivityVersion = findViewById(R.id.tvSettingActivityVersion);

        civSettingActivityColor = findViewById(R.id.civSettingActivityColor);
        ivSettingActivityChangeIcon = findViewById(R.id.ivSettingActivityChangeIcon);

        llSettingActivitySwitchTheme = findViewById(R.id.llSettingActivitySwitchTheme);
        llSettingActivityChangeTextSize = findViewById(R.id.llSettingActivityChangeTextSize);
        llSettingActivityChangeIcon = findViewById(R.id.llSettingActivityChangeIcon);
        llSettingActivityChangeColor = findViewById(R.id.llSettingActivityChangeColor);
        llSettingActivityVersion = findViewById(R.id.llSettingActivityVersion);
        llSettingActivityChangeLog = findViewById(R.id.llSettingActivityChangeLog);
        llSettingActivityOpen = findViewById(R.id.llSettingActivityOpen);
        llSettingActivitySource = findViewById(R.id.llSettingActivitySource);
        llSettingActivityLicense = findViewById(R.id.llSettingActivityLicense);
    }

    private void initListener() {
        ListenerUtility.setOnClickListener(this, llSettingActivitySwitchTheme, llSettingActivityChangeTextSize, llSettingActivityChangeIcon, llSettingActivityChangeColor, llSettingActivityVersion, llSettingActivityChangeLog, llSettingActivityOpen, llSettingActivitySource, llSettingActivityLicense);
    }

    private void initDataAfterListener() {
        BaseUtility.setText(tvSettingActivitySource,AppConfig.APP_SOURCE_ADDRESS);

    }

    /**
     * 显示开源依赖路径
     */
    private void showOpenLicense(){
        Notices notices = new Notices();
        notices.addNotice(new Notice("PhotoView", "https://github.com/chrisbanes/PhotoView", "Copyright 2017 Chris Banes", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("OkHttp", "https://github.com/square/okhttp", "Copyright 2016 Square, Inc.", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("Gson", "https://github.com/google/gson", "Copyright 2008 Google Inc.", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("Glide", "https://github.com/bumptech/glide", "Sam Judd - @sjudd on GitHub, @samajudd on Twitter", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("Stetho", "https://github.com/facebook/stetho", "Copyright (c) 2015, Facebook, Inc. All rights reserved.", new BSD3ClauseLicense()));
        notices.addNotice(new Notice("PersistentCookieJar", "https://github.com/franmontiel/PersistentCookieJar", "Copyright 2016 Francisco José Montiel Navarro", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("jsoup", "https://jsoup.org", "Copyright © 2009 - 2016 Jonathan Hedley (jonathan@hedley.net)", new MITLicense()));

        new LicensesDialog.Builder(this)
                .setNotices(notices)
                .setIncludeOwnLicense(true)
                .build()
                .show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llSettingActivitySwitchTheme:
                // 切换主题
                break;
            case R.id.llSettingActivityChangeTextSize:
                // 改变字体大小
                break;
            case R.id.llSettingActivityChangeIcon:
                // 改变图标大小
                break;
            case R.id.llSettingActivityChangeColor:
                // 设置主题颜色
                break;
            case R.id.llSettingActivityVersion:
                // 检查版本
                break;
            case R.id.llSettingActivityChangeLog:
                // 更新日志
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(AppConfig.APP_SOURCE_LOG)));
                break;
            case R.id.llSettingActivityOpen:
                // 开源许可
                showOpenLicense();
                break;
            case R.id.llSettingActivitySource:
                // 源代码
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(AppConfig.APP_SOURCE_ADDRESS)));
                break;
            case R.id.llSettingActivityLicense:
                // 版权申明
                BasePopupView popupView = new XPopup.Builder(this).asConfirm(getString(R.string.setting_activity_license), getString(R.string.setting_activity_license_content), null).show();
                break;
        }
    }
}
