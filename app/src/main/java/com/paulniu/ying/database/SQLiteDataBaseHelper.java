package com.paulniu.ying.database;

import com.paulniu.ying.callback.IBaseRealmCallback;
import com.paulniu.ying.callback.IRealmQueryCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Coder: niupuyue
 * Date: 2019/7/15
 * Time: 18:46
 * Desc: 数据库操作类
 * Version:
 */
public class SQLiteDataBaseHelper {

    private static class SQLiteDataBaseHelperImpl {
        private static final SQLiteDataBaseHelper INSTANCE = new SQLiteDataBaseHelper();
    }

    public static SQLiteDataBaseHelper getInstance() {
        return SQLiteDataBaseHelperImpl.INSTANCE;
    }

    private Realm mRealm = null;

    public synchronized Realm getRealm() {
        if (null == mRealm) {
            mRealm = Realm.getDefaultInstance();
        }
        return mRealm;
    }

    // 同步插入数据 没有primarykey
    public void add(final RealmObject obj) {
        try {
            getRealm().executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    getRealm().copyToRealm(obj);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 同步插入数据，有primarykey
    public void addWithKey(final RealmObject obj) {
        try {
            getRealm().executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    getRealm().insertOrUpdate(obj);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 异步插入数据 没有primarykey
    public RealmAsyncTask addAsync(final RealmObject obj, final IBaseRealmCallback callback) {
        RealmAsyncTask task = null;
        try {
            task = getRealm().executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    getRealm().copyToRealm(obj);
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

    // 异步插入数据，有primarykey
    public RealmAsyncTask addAsyncWithKey(final RealmObject obj, final IBaseRealmCallback callback) {
        RealmAsyncTask task = null;
        try {
            task = getRealm().executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    getRealm().insertOrUpdate(obj);
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

    // 查找所有数据
    public List<Object> getAll(final Class clz, IRealmQueryCallback callback){
        List<Object> results = new ArrayList<>();
        try {
            getRealm().executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults rr = getRealm().where(clz).findAll();
                    results = Arrays.asList(rr.toArray());
                }
            });
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    // 同步删除所有数据
    public void deleteAll(final Class clz) {
        try {
            getRealm().executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    // 先遍历所有数据，然后再删除
                    RealmResults results = getRealm().where(clz).findAll();
                    if (results.size() > 0) {
                        results.deleteAllFromRealm();
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 异步删除所有数据
    public RealmAsyncTask deleteAllAsync(final Class clz, final IBaseRealmCallback callback) {
        RealmAsyncTask task = null;
        try {
            task = getRealm().executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults results = getRealm().where(clz).findAll();
                    if (results.size() > 0) {
                        results.deleteAllFromRealm();
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


}
