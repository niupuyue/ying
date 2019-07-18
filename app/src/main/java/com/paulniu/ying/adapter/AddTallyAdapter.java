package com.paulniu.ying.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.paulniu.ying.model.TallyModel;

/**
 * Coder: niupuyue
 * Date: 2019/7/18
 * Time: 17:11
 * Desc:
 * Version:
 */
public class AddTallyAdapter extends BaseQuickAdapter<TallyModel, BaseViewHolder> {
    private Context mContext;

    public AddTallyAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, TallyModel item) {

    }
}
