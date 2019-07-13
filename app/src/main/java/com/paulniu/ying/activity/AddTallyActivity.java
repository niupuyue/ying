package com.paulniu.ying.activity;

import android.content.Context;
import android.content.Intent;

import com.paulniu.ying.base.BaseActivity;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-13
 * Time: 21:59
 * Desc: 记账添加页面
 * Version:
 */
public class AddTallyActivity extends BaseActivity {

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context,AddTallyActivity.class);

        return intent;
    }

}
