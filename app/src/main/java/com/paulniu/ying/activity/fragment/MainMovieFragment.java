package com.paulniu.ying.activity.fragment;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.utils.CustomToastUtility;
import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.orhanobut.logger.Logger;
import com.paulniu.ying.ApiService;
import com.paulniu.ying.BaseFragment;
import com.paulniu.ying.R;
import com.paulniu.ying.RetorfitFactory;
import com.paulniu.ying.adapter.MovieAdapter;
import com.paulniu.ying.constant.AppConfig;
import com.paulniu.ying.model.data.MovieModel;
import com.paulniu.ying.util.infomanager.LocationInfoManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-13
 * Time: 17:09
 * Desc: 电影Fragment
 * Version:
 */
public class MainMovieFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public static MainMovieFragment getInstance() {
        MainMovieFragment fragment = new MainMovieFragment();

        return fragment;
    }

    private static final String TAG = MainMovieFragment.class.getSimpleName();

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private int index = ApiService.START_INDEX;
    private MovieAdapter adapter;
    private List<MovieModel.Subjects> subjects = new ArrayList<>();

    private View.OnClickListener footOnClicklistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CustomToastUtility.makeTextWarn(getString(R.string.app_no_more_data));
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie_tab;
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
        adapter = new MovieAdapter(R.layout.item_movie, subjects, getContext());
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                // 跳转到电影详情页面
                Logger.e(TAG, adapter.getData().get(position));
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                load(false);
            }
        });
        View footView = getLayoutInflater().inflate(R.layout.view_recycleview_foot, (ViewGroup) recyclerView.getParent(), false);
        ListenerUtility.setOnClickListener(footOnClicklistener, footView);
        adapter.addFooterView(footView);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(true);
        load(true);
    }

    private void load(final boolean isRefresh) {
        if (isRefresh) {
            index = ApiService.START_INDEX;
        } else {
            index += ApiService.LIMIT;
        }
        RetorfitFactory
                .getRetrofit()
                .create(ApiService.class)
                .getMovieList(LocationInfoManager.newInstance().getLocationInfo().getCityName(), AppConfig.DOUBAN_API_KEY, index, ApiService.LIMIT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<MovieModel>() {
                    @Override
                    public void onNext(MovieModel movieModel) {
                        // 获取到数据，刷新页面
                        if (movieModel != null && !BaseUtility.isEmpty(movieModel.getSubjects())) {
                            if (isRefresh) {
                                MainMovieFragment.this.subjects.clear();
                            }
                            MainMovieFragment.this.subjects.addAll(movieModel.getSubjects());
                            if (BaseUtility.size(movieModel.getSubjects()) < ApiService.LIMIT) {
                                // 数据加载完毕
                                adapter.loadMoreEnd(isRefresh);
                            } else {
                                // 数据未加载完毕
                                adapter.loadMoreComplete();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 刷新失败
                        if (adapter != null) {
                            adapter.loadMoreFail();
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (adapter != null) {
                            swipeRefreshLayout.setRefreshing(false);
                            adapter.loadMoreComplete();
                        }
                    }
                });
    }

    @Override
    public void onRefresh() {
        load(true);
    }
}
