package com.paulniu.ying.activity.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.utils.CustomToastUtility;
import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.orhanobut.logger.Logger;
import com.paulniu.ying.ApiService;
import com.paulniu.ying.BaseFragment;
import com.paulniu.ying.R;
import com.paulniu.ying.adapter.AffairListAdapter;
import com.paulniu.ying.callback.IRealmQueryCallback;
import com.paulniu.ying.model.AffairModel;
import com.paulniu.ying.util.impl.SQLiteDataBaseImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-14
 * Time: 14:44
 * Desc: 事务列表Fragment
 * Version:
 */
public class AffairListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, IRealmQueryCallback {

    private static final String TAG = AffairListFragment.class.getSimpleName();

    public static AffairListFragment getInstance() {
        AffairListFragment fragment = new AffairListFragment();

        return fragment;
    }

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private int index = 0;
    private AffairListAdapter adapter;
    private List<AffairModel> affairModels = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_affair_list;
    }

    @Override
    public void initDateExtra() {

    }

    @Override
    public void initViewFindViewById(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    public void initListener() {
        ListenerUtility.setOnRefreshListener(this, swipeRefreshLayout);
    }

    @Override
    public void initData() {
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color._feb501), getResources().getColor(R.color.toolbar_bg));
        adapter = new AffairListAdapter(R.layout.item_affair, affairModels, getContext());
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                // 显示事务详情
                Logger.e(TAG, adapter.getItem(position).toString());
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                load(false);
            }
        });
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(true);
        load(true);
    }

    private void load(boolean isRefresh) {
        if (isRefresh) {
            index = ApiService.START_INDEX;
        } else {
            index += ApiService.LIMIT;
        }
        SQLiteDataBaseImpl.getInstance().queryAll(isRefresh, AffairModel.class, this);
    }

    @Override
    public void onRefresh() {
        load(true);
    }

    @Override
    public void onSuccess() {
    }

    @Override
    public void onError() {
        // 加载数据失败
        CustomToastUtility.makeTextError(getString(R.string.app_load_data_error));
        if (adapter != null) {
            adapter.loadMoreFail();
        }
    }

    @Override
    public void getResult(boolean isRefresh, List<AffairModel> results) {
        Logger.e(TAG, results.toString());
        // 记载数据成功
        if (!BaseUtility.isEmpty(results)) {
            if (isRefresh) {
                affairModels.clear();
            }
            affairModels.addAll(results);
            if (BaseUtility.size(results) < ApiService.LIMIT) {
                // 数据加载完毕
                adapter.loadMoreEnd(isRefresh);
            } else {
                // 数据未加载完毕
                adapter.loadMoreComplete();
            }
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }
}
