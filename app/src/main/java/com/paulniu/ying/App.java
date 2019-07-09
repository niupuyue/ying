package com.paulniu.ying;

import android.app.Application;

import com.niupuyue.mylibrary.utils.LibraryConstants;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-09
 * Time: 21:59
 * Desc:
 * Version:
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LibraryConstants.setContext(this);
    }
}
