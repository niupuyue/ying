package com.paulniu.ying;

import android.app.Application;
import android.content.Context;

import com.niupuyue.mylibrary.utils.LibraryConstants;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.paulniu.ying.constant.AppConfig;
import com.paulniu.ying.database.YingMigration;
import com.squareup.leakcanary.LeakCanary;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.rx.RealmObservableFactory;

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
        // 初始化Logger
        Logger.addLogAdapter(new AndroidLogAdapter());
        // 初始化自己的依赖库
        LibraryConstants.setContext(this);
        // 初始化realm数据库
        initRealmSQLite();
        // 初始化leakCanary
        initLeakCanary(this);
    }

    private void initRealmSQLite() {
        Realm.init(this);
        // 设置Realm配置信息
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(AppConfig.REALM_SQLITE_NAME)
                .rxFactory(new RealmObservableFactory())
                .schemaVersion(AppConfig.REALM_SQLITE_VERSION)
                .migration(new YingMigration())
                .build();
        Realm.setDefaultConfiguration(config);
    }

    public void initLeakCanary(Application app) {
        if (LeakCanary.isInAnalyzerProcess(app)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(app);
    }
}
