package com.paulniu.ying.activity.fragment;

import android.view.View;

import com.paulniu.ying.BaseFragment;
import com.paulniu.ying.R;

/**
 * Coder: niupuyue
 * Date: 2019/7/18
 * Time: 14:44
 * Desc: 支出fragment
 * Version:
 */
public class ExpenditureFragment extends BaseFragment {

    public static ExpenditureFragment getInstance() {
        ExpenditureFragment fragment = new ExpenditureFragment();

        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_add_expenditure;
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
