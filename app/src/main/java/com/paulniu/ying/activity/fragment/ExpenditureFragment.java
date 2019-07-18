package com.paulniu.ying.activity.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.paulniu.ying.BaseFragment;
import com.paulniu.ying.R;
import com.paulniu.ying.adapter.AddTallyAdapter;

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

    private RecyclerView recyclerView;

    private AddTallyAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_add_expenditure;
    }

    @Override
    public void initDateExtra() {

    }

    @Override
    public void initViewFindViewById(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        adapter = new AddTallyAdapter(R.layout.item_add_tally,getContext());
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        recyclerView.setAdapter(adapter);

    }
}
