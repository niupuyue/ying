package com.paulniu.ying.activity;

import android.content.Context;
import android.content.Intent;

import com.paulniu.ying.base.BaseActivity;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-13
 * Time: 16:50
 * Desc: 切换主题
 * Version:
 */
public class SwitchThemeActivity extends BaseActivity {

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context,SwitchThemeActivity.class);
        return intent;
    }

}
