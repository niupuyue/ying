package com.paulniu.ying.activity.fragment;

import android.content.Context;

import com.paulniu.ying.BaseFragment;
import com.paulniu.ying.R;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-13
 * Time: 17:09
 * Desc:
 * Version:
 */
public class MainMovieFragment extends BaseFragment {

    public static MainMovieFragment getInstance(){
        MainMovieFragment fragment = new MainMovieFragment();

        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie_tab;
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
