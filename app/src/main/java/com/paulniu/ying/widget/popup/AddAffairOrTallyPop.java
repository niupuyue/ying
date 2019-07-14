package com.paulniu.ying.widget.popup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.paulniu.ying.R;

import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-14
 * Time: 12:26
 * Desc: 添加记账或者事务弹窗
 * Version:
 */
public class AddAffairOrTallyPop extends PopupWindow implements View.OnClickListener {

    public interface AddAffairOrTallyListener {
        void gotoAffair();

        void gotoTally();
    }

    private AddAffairOrTallyListener listener;

    public AddAffairOrTallyPop(Context context, AddAffairOrTallyListener listener) {
        super(context);
        this.listener = listener;
        // 设置背景view
        View mRoot = View.inflate(context, R.layout.view_add_affair_tally_pop, null);
        TextView tvAffair = mRoot.findViewById(R.id.tvAddPopAffair);
        TextView tvTally = mRoot.findViewById(R.id.tvAddPopTall);
        // 设置高度
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置宽度
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置外部是否可以点击
        setOutsideTouchable(true);
        // 设置获取焦点
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ListenerUtility.setOnClickListener(this, tvAffair);
        ListenerUtility.setOnClickListener(this, tvTally);
        setContentView(mRoot);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvAddPopAffair:
                if (null != listener) {
                    listener.gotoAffair();
                }
                dismiss();
                break;
            case R.id.tvAddPopTall:
                if (null != listener) {
                    listener.gotoTally();
                }
                dismiss();
                break;
        }
    }
}
