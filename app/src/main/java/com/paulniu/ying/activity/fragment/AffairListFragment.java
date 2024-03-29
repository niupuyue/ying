package com.paulniu.ying.activity.fragment;

import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.utils.CustomToastUtility;
import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.niupuyue.mylibrary.utils.ScreenUtility;
import com.orhanobut.logger.Logger;
import com.paulniu.ying.ApiService;
import com.paulniu.ying.BaseFragment;
import com.paulniu.ying.R;
import com.paulniu.ying.adapter.AffairListAdapter;
import com.paulniu.ying.callback.IBaseRealmCallback;
import com.paulniu.ying.callback.IRealmQueryAffairCallback;
import com.paulniu.ying.database.SQLiteDataBaseHelper;
import com.paulniu.ying.model.AffairModel;
import com.paulniu.ying.widget.IYingLoadMoreView;

import java.util.ArrayList;
import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-14
 * Time: 14:44
 * Desc: 事务列表Fragment
 * Version:
 */
public class AffairListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, IRealmQueryAffairCallback {

    private static final String TAG = AffairListFragment.class.getSimpleName();

    public static AffairListFragment getInstance() {
        AffairListFragment fragment = new AffairListFragment();

        return fragment;
    }

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private int index = 0;
    public boolean isNeedLoayLoad = true;
    private AffairListAdapter adapter;

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
        adapter = new AffairListAdapter(R.layout.item_affair, getContext());
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                // 显示事务详情

            }
        });
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final BaseQuickAdapter adapter, View view, int position) {
                // 长按显示弹窗
                new XPopup.Builder(getContext())
                        .atView(view)
                        .asAttachList(new String[]{getString(R.string.app_delete), getString(R.string.main_activity_share), getString(R.string.app_delete_all)}, null, new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                if (position == 0) {
                                    // 删除该对象
                                    SQLiteDataBaseHelper.getInstance().deleteAffairByTime(((AffairModel) adapter.getItem(position)).getAffairTime(), new IBaseRealmCallback() {
                                        @Override
                                        public void onSuccess() {
                                            CustomToastUtility.makeTextSucess(getString(R.string.app_delete_affair_success));
                                        }

                                        @Override
                                        public void onError() {
                                            CustomToastUtility.makeTextSucess(getString(R.string.app_delete_affair_error));
                                        }
                                    });
                                } else if (position == 1) {
                                    // 分享操作 TODO

                                } else if (position == 2) {
                                    // 删除全部事务
                                    SQLiteDataBaseHelper.getInstance().deleteAllAsync(AffairModel.class, new IBaseRealmCallback() {
                                        @Override
                                        public void onSuccess() {
                                            // 清除成功
                                            CustomToastUtility.makeTextSucess(getString(R.string.app_delete_affair_success));
                                            adapter.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onError() {
                                            CustomToastUtility.makeTextSucess(getString(R.string.app_delete_affair_error));
                                        }
                                    });
                                }
                            }
                        }).show();
                return false;
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
//                load(false);
            }
        });
        adapter.setLoadMoreView(new IYingLoadMoreView());
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
        SQLiteDataBaseHelper.getInstance().queryAllAffairAsync(this);
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
    }

    @Override
    public void getResult(final boolean isRefresh, final List<AffairModel> results) {
        // 记载数据成功
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!BaseUtility.isEmpty(results)) {
                    if (isRefresh) {
                        if (adapter != null) {
                            adapter.setNewData(results);
                        }
                    } else {
                        if (adapter != null) {
                            adapter.addData(results);
                        }
                    }
                    if (results.size() < ApiService.LIMIT) {
                        // 数据加载完成，后面没数据了
                        adapter.loadMoreEnd(isRefresh);
                    } else {
                        // 数据加载完成，后面还可能有数据
                        adapter.loadMoreComplete();
                    }
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
