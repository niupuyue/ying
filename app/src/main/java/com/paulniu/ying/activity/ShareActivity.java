package com.paulniu.ying.activity;

import android.content.Context;
import android.content.Intent;

import com.paulniu.ying.base.BaseActivity;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-13
 * Time: 16:51
 * Desc: 分享页面
 * Version:
 */
public class ShareActivity extends BaseActivity {

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context,ShareActivity.class);
        return intent;
    }

}
