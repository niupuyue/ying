package com.paulniu.ying;

import android.app.Application;
import android.content.Context;

import com.niupuyue.mylibrary.utils.LibraryConstants;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-09
 * Time: 21:59
 * Desc:
 * Version:
 */
public class App extends Application {

    private static Application application = null;
    private static Context context = null;

    public static Context getAppContext() {
        return context;
    }

    public static Application getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        application = this;
        init();
    }

    private void init() {
        Logger.addLogAdapter(new AndroidLogAdapter());

        LibraryConstants.setContext(this);
    }
}
