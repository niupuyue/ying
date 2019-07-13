package com.paulniu.ying.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.paulniu.ying.R;
import com.paulniu.ying.adapter.WelcomeAdapter;
import com.paulniu.ying.base.BaseActivity;
import com.paulniu.ying.util.BusinessUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Coder: niupuyue
 * Date: 2019/7/10
 * Time: 9:55
 * Desc: 欢迎页
 * Version:
 */
public class WelcomeActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = WelcomeActivity.class.getSimpleName();

    private ViewPager vpWelcomeActivityViewpager;
    private View welcomeView1;
    private View welcomeView2;
    private View welcomeView3;

    private List<View> views = new ArrayList<>();
    private TextView tvToast;

    private WelcomeAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initViewByFindViewById();
        initListener();
        initDataAfterListener();

    }

    private void initViewByFindViewById() {
        vpWelcomeActivityViewpager = findViewById(R.id.vpWelcomeActivityViewpager);
        welcomeView1 = View.inflate(WelcomeActivity.this, R.layout.view_welcome_view1, null);
        welcomeView2 = View.inflate(WelcomeActivity.this, R.layout.view_welcome_view2, null);
        welcomeView3 = View.inflate(WelcomeActivity.this, R.layout.view_welcome_view3, null);

        tvToast = welcomeView3.findViewById(R.id.tvWelcomeActivityGotoHome);
    }

    private void initListener() {
        ListenerUtility.setOnClickListener(this, welcomeView1.findViewById(R.id.tvWelcomeActivityJump));
        ListenerUtility.setOnClickListener(this, welcomeView2.findViewById(R.id.tvWelcomeActivityJump));
        ListenerUtility.setOnClickListener(this, tvToast);
    }

    private void initDataAfterListener() {
        views.add(welcomeView1);
        views.add(welcomeView2);
        views.add(welcomeView3);
        adapter = new WelcomeAdapter(views);
        vpWelcomeActivityViewpager.setAdapter(adapter);
    }

    private void gotoHome() {
        // 设置已经看过引导页
        BusinessUtility.showdWelcomePage();
        Intent intent = MainActivity.getIntent(WelcomeActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseUtility.setBackgroundResource(welcomeView1, 0);
        BaseUtility.setBackgroundResource(welcomeView2, 0);
        BaseUtility.setBackgroundResource(welcomeView3, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvWelcomeActivityJump:
                gotoHome();
                break;
            case R.id.tvWelcomeActivityGotoHome:
                gotoHome();
                break;
        }
    }
}
