package com.paulniu.ying.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.paulniu.ying.R;
import com.paulniu.ying.activity.fragment.ExpenditureFragment;
import com.paulniu.ying.activity.fragment.IncomeFragment;
import com.paulniu.ying.adapter.AddTallyViewpagerAdapter;
import com.paulniu.ying.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-13
 * Time: 21:59
 * Desc: 记账添加页面
 * Version:
 */
public class AddTallyActivity extends BaseActivity implements View.OnClickListener {

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, AddTallyActivity.class);

        return intent;
    }

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView tvAddTallyActivityCancel;

    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private AddTallyViewpagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtally);

        viewPager = findViewById(R.id.vpAddTallyActivityViewpager);
        tabLayout = findViewById(R.id.tlAddTallyActivityTabLayout);
        tvAddTallyActivityCancel = findViewById(R.id.tvAddTallyActivityCancel);

        ListenerUtility.setOnClickListener(this,tvAddTallyActivityCancel);

        titles.add("支出");
        titles.add("收入");

        fragments.add(ExpenditureFragment.getInstance());
        fragments.add(IncomeFragment.getInstance());

        adapter = new AddTallyViewpagerAdapter(getSupportFragmentManager(), titles, fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvAddTallyActivityCancel:
                finish();
                break;
        }
    }
}
