package com.paulniu.ying.util.impl;

import com.paulniu.ying.callback.IRealmQueryCallback;
import com.paulniu.ying.model.AffairModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmChangeListener;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-14
 * Time: 11:50
 * Desc:
 * Version:
 */
public class SQLiteDataBaseImpl {

    private static class SQLiteDataBaseImplHelper {
        private static final SQLiteDataBaseImpl INSTANCE = new SQLiteDataBaseImpl();
    }

    public static SQLiteDataBaseImpl getInstance() {
        return SQLiteDataBaseImplHelper.INSTANCE;
    }

    private Realm getRealm() {
        return Realm.getDefaultInstance();
    }

    // 添加数据到数据库
    public RealmAsyncTask add(final RealmObject realmObject, final IRealmQueryCallback callback) {
        RealmAsyncTask task = null;
        try {
            getRealm().executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    if (null != realm) {
                        realm.copyToRealm(realmObject);
                    }
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    if (null != callback) {
                        callback.onSuccess();
                    }
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    if (null != callback) {
                        callback.onError();
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return task;
    }

    // 添加数据到数据库(集合)
    public RealmAsyncTask addAll(List<RealmObject> realmObjects, IRealmQueryCallback callback) {
        RealmAsyncTask task = null;
        try {

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return task;
    }

    // 删除数据库中的数据
    public RealmAsyncTask delete(final RealmObject realmObject, final IRealmQueryCallback callback) {
        RealmAsyncTask task = null;
        try {
            task = getRealm().executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realmObject.deleteFromRealm();
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    if (null != callback) {
                        callback.onSuccess();
                    }
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    if (null != callback) {
                        callback.onError();
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return task;
    }

    // 查询所有数据
    public void queryAll(final boolean isRefresh, Class clz, final IRealmQueryCallback callback) {
        final RealmResults<AffairModel> results = getRealm().where(clz).findAllAsync();
        results.addChangeListener(new RealmChangeListener<RealmResults<AffairModel>>() {
            @Override
            public void onChange(RealmResults<AffairModel> realmObjects) {
                if (null != callback) {
                    callback.getResult(isRefresh,getRealm().copyFromRealm(results));
                }
            }
        });
    }

}
