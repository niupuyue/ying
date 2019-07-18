package com.paulniu.ying.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Coder: niupuyue
 * Date: 2019/7/18
 * Time: 14:43
 * Desc: 添加记账页面的viewpager的adapter
 * Version:
 */
public class AddTallyViewpagerAdapter extends FragmentPagerAdapter {
    private List<String> titles;
    private List<Fragment> fragments;

    public AddTallyViewpagerAdapter(FragmentManager fm, List<String> titles, List<Fragment> fragments) {
        super(fm);
        this.titles = titles;
        this.fragments = fragments;
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
