package com.paulniu.ying.activity.fragment;

import android.content.Intent;
import android.view.View;

import com.paulniu.ying.BaseFragment;
import com.paulniu.ying.R;

/**
 * Coder: niupuyue
 * Date: 2019/7/18
 * Time: 14:45
 * Desc: 收入fragment
 * Version:
 */
public class IncomeFragment extends BaseFragment {

    public static IncomeFragment getInstance() {
        IncomeFragment fragment = new IncomeFragment();

        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_add_income;
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
