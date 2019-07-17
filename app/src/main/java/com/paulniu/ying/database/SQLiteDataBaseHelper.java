package com.paulniu.ying.database;

import com.niupuyue.mylibrary.utils.TimeUtility;
import com.paulniu.ying.callback.IBaseRealmCallback;
import com.paulniu.ying.callback.IRealmQueryAffairCallback;
import com.paulniu.ying.model.AffairModel;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
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
            realm.executeTransactionAsync(new Realm.Transaction() {
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
     * 获取某一天的所有事务
     */
    public void queryTodayAffairAsync(final long time, final IRealmQueryAffairCallback callback) {
        if (realm != null) {
            long targetTime = 0;
            if (time <= 0) {
                targetTime = System.currentTimeMillis();
            } else {
                targetTime = time;
            }
            // 计算时间间距
            // 获取这一天的开始
            final long starTime = TimeUtility.getDayStartTime(TimeUtility.getCalendar(targetTime)).getTimeInMillis();
            final long endTime = TimeUtility.getDayEndTime(TimeUtility.getCalendar(targetTime)).getTimeInMillis();
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<AffairModel> affairModels = realm.where(AffairModel.class).between("affairTime", starTime, endTime).findAll();
                    List<AffairModel> results = realm.copyFromRealm(affairModels);
                    if (callback != null) {
                        callback.getResult(true, results);
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
        }
    }

    /**
     * 获取某一周所有的事务
     */
    public void queryWeekAffairAsync(final long time, final IRealmQueryAffairCallback callback) {
        if (realm != null) {
            long target = 0;
            if (time <= 0) {
                target = System.currentTimeMillis();
            } else {
                target = time;
            }
            // 计算一周的时间间距
            final long starTime = TimeUtility.getDayStarByWeek(TimeUtility.getCalendar(target)).getTimeInMillis();
            final long endTime = TimeUtility.getDayEndByWeek(TimeUtility.getCalendar(target)).getTimeInMillis();
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<AffairModel> affairModels = realm.where(AffairModel.class).between("affairTime", starTime, endTime).findAll();
                    List<AffairModel> results = realm.copyFromRealm(affairModels);
                    if (null != callback) {
                        callback.getResult(true, results);
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
        }
    }

    /**
     * 获取某个月的事务
     */
    public void queryMonthAffairAsync(final long time, final IRealmQueryAffairCallback callback) {
        if (realm != null) {
            long target = 0;
            if (time <= 0) {
                target = System.currentTimeMillis();
            } else {
                target = time;
            }
            // 计算一个月的开始和结束
            final long startTime = TimeUtility.getDayStarByMonth(TimeUtility.getCalendar(target)).getTimeInMillis();
            final long endTime = TimeUtility.getDayEndByMonth(TimeUtility.getCalendar(target)).getTimeInMillis();
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<AffairModel> affairModels = realm.where(AffairModel.class).between("affairTime", startTime, endTime).findAll();
                    List<AffairModel> results = realm.copyFromRealm(affairModels);
                    if (null != callback) {
                        callback.getResult(true, results);
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
        }
    }

    /**
     * 获取某一年的事务
     */
    public void queryYearAffairAsync(final long time,final IRealmQueryAffairCallback callback){
        if (realm == null){
            realm = Realm.getDefaultInstance();
        }
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                long target;
                if (time <=0){
                    target = System.currentTimeMillis();
                }else {
                    target = time;
                }
                // 获取一年的开始和结束
                long startTime = TimeUtility.getDayStarByYear(TimeUtility.getCalendar(target)).getTimeInMillis();
                long endTime = TimeUtility.getDayEndByYear(TimeUtility.getCalendar(target)).getTimeInMillis();
                RealmResults<AffairModel> affairModels = realm.where(AffairModel.class).between("affairTime",startTime,endTime).findAll();
                List<AffairModel> results = realm.copyFromRealm(affairModels);
                if (null != callback){
                    callback.getResult(true,results);
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (null != callback){
                    callback.onSuccess();
                }
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                if (null != callback){
                    callback.onError();
                }
            }
        });
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
