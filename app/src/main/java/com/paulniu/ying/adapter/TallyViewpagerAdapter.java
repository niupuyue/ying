package com.paulniu.ying.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-14
 * Time: 14:39
 * Desc: 记录页面适配器
 * Version:
 */
public class TallyViewpagerAdapter extends FragmentPagerAdapter {
    private List<String> titles;
    private List<Fragment> fragments;

    public TallyViewpagerAdapter(FragmentManager fm, List<String> titleList, List<Fragment> list) {
        super(fm);
        this.titles = titleList;
        this.fragments = list;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
