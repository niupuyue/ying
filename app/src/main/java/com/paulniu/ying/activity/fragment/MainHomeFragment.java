package com.paulniu.ying.activity.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.paulniu.ying.BaseFragment;
import com.paulniu.ying.R;


/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-13
 * Time: 16:59
 * Desc: 首页Fragment
 * Version:
 */
public class MainHomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public static MainHomeFragment getInstance() {
        MainHomeFragment fragment = new MainHomeFragment();

        return fragment;
    }

    private SwipeRefreshLayout srlNewsFragment;
    private RecyclerView rvNewsFragment;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news_tab;
    }

    @Override
    public void initDateExtra() {
    }

    @Override
    public void initViewFindViewById(View view) {
        srlNewsFragment = view.findViewById(R.id.srlNewsFragment);
        rvNewsFragment = view.findViewById(R.id.rvNewsFragment);
    }

    @Override
    public void initListener() {
        ListenerUtility.setOnRefreshListener(this,srlNewsFragment);
    }

    @Override
    public void initData() {

        srlNewsFragment.setRefreshing(true);
    }

    @Override
    public void onRefresh() {

    }
}
