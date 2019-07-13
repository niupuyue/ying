package com.paulniu.ying.activity;

import android.content.Context;
import android.content.Intent;

import com.paulniu.ying.base.BaseActivity;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-13
 * Time: 22:01
 * Desc: 添加事务
 * Version:
 */
public class AddAffairActivity extends BaseActivity {

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, AddAffairActivity.class);

        return intent;
    }

}
