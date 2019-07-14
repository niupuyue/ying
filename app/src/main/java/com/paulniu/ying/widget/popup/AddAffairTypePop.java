package com.paulniu.ying.widget.popup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.paulniu.ying.R;
import com.paulniu.ying.model.AffairModel;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-14
 * Time: 20:47
 * Desc: 添加事务类型选择弹窗
 * Version:
 */
public class AddAffairTypePop extends PopupWindow implements View.OnClickListener {

    public interface AddAffairTypeListener {
        void affairType(int type);
    }

    private AddAffairTypeListener listener;

    public AddAffairTypePop(Context context, AddAffairTypeListener listener) {
        super(context);
        this.listener = listener;
        // 设置背景View
        View view = View.inflate(context, R.layout.view_add_affair_type_pop, null);
        TextView tvAddAffairTypePopHigh = view.findViewById(R.id.tvAddAffairTypePopHigh);
        TextView tvAddAffairTypePopMiddle = view.findViewById(R.id.tvAddAffairTypePopMiddle);
        TextView tvAddAffairTypePopLow = view.findViewById(R.id.tvAddAffairTypePopLow);
        ListenerUtility.setOnClickListener(this, tvAddAffairTypePopHigh, tvAddAffairTypePopMiddle, tvAddAffairTypePopLow);
        // 设置高度
        setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置宽度
        setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置外部是否可以点击
        setOutsideTouchable(true);
        // 设置获取焦点
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(view);
    }

    @Override
    public void onClick(View v) {
        if (listener == null) return;
        switch (v.getId()) {
            case R.id.tvAddAffairTypePopHigh:
                listener.affairType(AffairModel.AFFAIR_TYPE_MAX);
                break;
            case R.id.tvAddAffairTypePopMiddle:
                listener.affairType(AffairModel.AFFAIR_TYPE_MIDDLE);
                break;
            case R.id.tvAddAffairTypePopLow:
                listener.affairType(AffairModel.AFFAIR_TYPE_LOW);
                break;
        }
        dismiss();
    }

}
