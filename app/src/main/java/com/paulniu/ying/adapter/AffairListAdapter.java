package com.paulniu.ying.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.utils.TimeUtility;
import com.paulniu.ying.R;
import com.paulniu.ying.model.AffairModel;

import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-14
 * Time: 15:04
 * Desc: 事务列表adapter
 * Version:
 */
public class AffairListAdapter extends BaseQuickAdapter<AffairModel, BaseViewHolder> {
    private Context mContext;

    public AffairListAdapter(int layoutResId, @Nullable List<AffairModel> data, Context context) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void convert(BaseViewHolder helper, AffairModel item) {
        try {
            helper.setText(R.id.tvItemAffairNote, item.getAffairNote());
            helper.setText(R.id.tvItemAffairTime, TimeUtility.convertToString(item.getAffairTime(), TimeUtility.DATE_FORMAT));
            if (item.getAffairType() == AffairModel.AFFAIR_TYPE_LOW) {
                BaseUtility.setBackground(helper.getView(R.id.llItemAffairAvator),mContext.getDrawable(R.drawable.shape_circle_low));
                helper.setText(R.id.tvItemAffairAvator, mContext.getString(R.string.item_affair_low));
            } else if (item.getAffairType() == AffairModel.AFFAIR_TYPE_MIDDLE) {
                BaseUtility.setBackground(helper.getView(R.id.llItemAffairAvator),mContext.getDrawable(R.drawable.shape_circle_middle));
                helper.setText(R.id.tvItemAffairAvator, mContext.getString(R.string.item_affair_middle));
            } else if (item.getAffairType() == AffairModel.AFFAIR_TYPE_MAX) {
                BaseUtility.setBackground(helper.getView(R.id.llItemAffairAvator),mContext.getDrawable(R.drawable.shape_circle_max));
                helper.setText(R.id.tvItemAffairAvator, mContext.getString(R.string.item_affair_max));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
