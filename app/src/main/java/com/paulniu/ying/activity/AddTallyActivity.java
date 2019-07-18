package com.paulniu.ying.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.paulniu.ying.R;
import com.paulniu.ying.base.BaseActivity;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-13
 * Time: 21:59
 * Desc: 记账添加页面
 * Version:
 */
public class AddTallyActivity extends BaseActivity {

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, AddTallyActivity.class);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 设置当前页面打开的方式
//        overridePendingTransition(R.anim.anim_bottom_top, R.anim.anim_top_bottom);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtally);



    }
}
