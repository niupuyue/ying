package com.paulniu.ying.activity.fragment;

import android.view.View;

import com.paulniu.ying.BaseFragment;
import com.paulniu.ying.R;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-14
 * Time: 14:44
 * Desc: 记账列表fragment
 * Version:
 */
public class TallyListFragment extends BaseFragment {
    public static TallyListFragment getInstance() {
        TallyListFragment fragment = new TallyListFragment();

        return fragment;
    }

    public boolean isNeedLoayLoad = true;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tally_list;
    }

    @Override
    public void initDateExtra() {

    }

    @Override
    public void initViewFindViewById(View view) {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
