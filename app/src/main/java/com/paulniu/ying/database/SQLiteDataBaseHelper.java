package com.paulniu.ying.database;

import com.niupuyue.mylibrary.utils.TimeUtility;
import com.paulniu.ying.callback.IBaseRealmCallback;
import com.paulniu.ying.callback.IRealmQueryAffairCallback;
import com.paulniu.ying.callback.IRealmQueryFestivalCallback;
import com.paulniu.ying.callback.IRealmQueryTallyCallback;
import com.paulniu.ying.model.AffairModel;
import com.paulniu.ying.model.FestivalModel;
import com.paulniu.ying.model.TallyModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmChangeListener;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

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

    private Realm realm = Realm.getDefaultInstance();

    // 异步插入数据，有primarykey
    public RealmAsyncTask addAsyncWithKey(final RealmObject obj, final IBaseRealmCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        RealmAsyncTask task = null;
        try {
            task = realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(obj);
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

    // 异步删除所有数据
    public RealmAsyncTask deleteAllAsync(final Class clz, final IBaseRealmCallback callback) {
        RealmAsyncTask task = null;
        try {
            task = realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults results = realm.where(clz).findAll();
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

    /**
     * 异步加载所有的事务
     */
    public void queryAllAffairAsync(final IRealmQueryAffairCallback callback) {
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(final Realm realm) {
                    final RealmResults<AffairModel> affairModels = realm.where(AffairModel.class).sort("affairTime", Sort.DESCENDING).findAll();
                    if (null != callback) {
                        List<AffairModel> results = realm.copyFromRealm(affairModels);
                        callback.getResult(true, results);
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 删除某一个事务
     */
    public void deleteAffairByTime(final long time) {
        try {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<AffairModel> affairModels = realm.where(AffairModel.class).equalTo("affairTime", time).findAll();
                    if (affairModels.size() > 0) {
                        affairModels.deleteAllFromRealm();
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
