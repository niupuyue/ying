package com.paulniu.ying.widget;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.paulniu.ying.R;

/**
 * Coder: niupuyue
 * Date: 2019/7/16
 * Time: 11:20
 * Desc: 加载更多数据
 * Version:
 */
public class IYingLoadMoreView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.view_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
