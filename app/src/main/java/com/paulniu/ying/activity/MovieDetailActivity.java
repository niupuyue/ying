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
 * Time: 21:56
 * Desc: 电影详情页面
 * Version:
 */
public class MovieDetailActivity extends BaseActivity {

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, MovieDetailActivity.class);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
    }
}
