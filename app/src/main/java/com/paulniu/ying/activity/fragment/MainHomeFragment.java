package com.paulniu.ying.activity.fragment;

import com.paulniu.ying.BaseFragment;
import com.paulniu.ying.R;


/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-13
 * Time: 16:59
 * Desc: 首页Fragment
 * Version:
 */
public class MainHomeFragment extends BaseFragment {

    public static MainHomeFragment getInstance() {
        MainHomeFragment fragment = new MainHomeFragment();

        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news_tab;
    }

    @Override
    public void initDateExtra() {

    }

    @Override
    public void initViewFindViewById() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
