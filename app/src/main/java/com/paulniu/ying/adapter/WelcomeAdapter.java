package com.paulniu.ying.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.niupuyue.mylibrary.utils.BaseUtility;

import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-12
 * Time: 23:41
 * Desc: 欢迎页面viewpager适配器
 * Version:
 */
public class WelcomeAdapter extends PagerAdapter {
    private List<View> views;

    public WelcomeAdapter(List<View> views) {
        this.views = views;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        viewPager.removeView(views.get(position));
    }

    @Override
    public int getCount() {
        return BaseUtility.isEmpty(views) ? 0 : BaseUtility.size(views);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(View container, int position) {
        ViewPager pViewPager = ((ViewPager) container);
        pViewPager.addView(views.get(position));
        return views.get(position);
    }
}
