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

    /**
     * 异步加载所有的事务
     */
    public void queryAllAffairAsync(final IRealmQueryAffairCallback callback) {
        try {
            getRealm().executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    final RealmResults<AffairModel> results = getRealm().where(AffairModel.class).findAllAsync();
                    results.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<AffairModel>>() {
                        @Override
                        public void onChange(RealmResults<AffairModel> affairModels, OrderedCollectionChangeSet changeSet) {
                            if (callback != null) {
                                List<AffairModel> result = getRealm().copyFromRealm(affairModels);
                                callback.getResult(true, results);
                            }
                        }
                    });
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
                    if (callback != null) {
                        callback.onError();
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 异步加载某一天所有事务
     */
    public void queryAffairByDayAsync(final boolean isRefresh, final long time, final IRealmQueryAffairCallback callback) {
        try {
            getRealm().executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    long targetTime;
                    if (time < 0) {
                        targetTime = System.currentTimeMillis();
                    } else {
                        targetTime = time;
                    }
                    RealmResults<AffairModel> affairModels = getRealm().where(AffairModel.class).findAllAsync();
                    affairModels.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<AffairModel>>() {
                        @Override
                        public void onChange(RealmResults<AffairModel> affairModels, OrderedCollectionChangeSet changeSet) {
                            if (affairModels.size() > 0) {
                                List<AffairModel> temp = getRealm().copyFromRealm(affairModels);
                                List<AffairModel> result = new ArrayList<>();
                                for (AffairModel model : temp) {
                                    if (TimeUtility.isToday(new Date(model.getAffairTime()))) {
                                        result.add(model);
                                    }
                                }
                                if (null != callback) {
                                    callback.getResult(isRefresh, result);
                                }
                            }
                        }
                    });
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
    }

    /**
     * 异步加载某一个月的所有事务
     */
    public void queryAffairByMonthAsync(final boolean isRefresh, final long time, final IRealmQueryAffairCallback callback) {
        try {
            getRealm().executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    long targetTime;
                    if (time < 0) {
                        targetTime = System.currentTimeMillis();
                    } else {
                        targetTime = time;
                    }
                    RealmResults<AffairModel> affairModels = getRealm().where(AffairModel.class).findAllAsync();
                    affairModels.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<AffairModel>>() {
                        @Override
                        public void onChange(RealmResults<AffairModel> affairModels, OrderedCollectionChangeSet changeSet) {
                            if (affairModels.size() > 0) {
                                List<AffairModel> temp = getRealm().copyFromRealm(affairModels);
                                List<AffairModel> results = new ArrayList<>();
                                for (AffairModel model : temp) {
                                    if (TimeUtility.isThisMonth(new Date(model.getAffairTime()))) {
                                        results.add(model);
                                    }
                                }
                                if (null != callback) {
                                    callback.getResult(isRefresh, results);
                                }
                            }
                        }
                    });
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
    }

    /**
     * 异步加载某一个星期的所有事务
     */
    public void queryAffairByWeekAsync(final boolean isRefresh, final long time, final IRealmQueryAffairCallback callback) {
        try {
            getRealm().executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    long targetTime;
                    if (time < 0) {
                        targetTime = System.currentTimeMillis();
                    } else {
                        targetTime = time;
                    }
                    RealmResults<AffairModel> affairModels = getRealm().where(AffairModel.class).findAllAsync();
                    affairModels.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<AffairModel>>() {
                        @Override
                        public void onChange(RealmResults<AffairModel> affairModels, OrderedCollectionChangeSet changeSet) {
                            if (affairModels.size() > 0) {
                                List<AffairModel> temp = getRealm().copyFromRealm(affairModels);
                                List<AffairModel> results = new ArrayList<>();
                                for (AffairModel model : temp) {
                                    if (TimeUtility.isThisWeek(new Date(model.getAffairTime()))) {
                                        results.add(model);
                                    }
                                }
                                if (null != callback) {
                                    callback.getResult(isRefresh, results);
                                }
                            }
                        }
                    });
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
    }

    /**
     * 删除指定的事务
     */
    public void deleteAffairByTime(final long time, final IBaseRealmCallback callback) {
        try {
            getRealm().executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<AffairModel> results = getRealm().where(AffairModel.class).equalTo("affairTime", time).findAllAsync();
                    results.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<AffairModel>>() {
                        @Override
                        public void onChange(RealmResults<AffairModel> affairModels, OrderedCollectionChangeSet changeSet) {
                            if (affairModels.size() > 0) {
                                affairModels.deleteAllFromRealm();
                            }
                            if (null != callback) {
                                callback.onSuccess();
                            }
                        }
                    });
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
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
    }

    /**
     * 查询所有的节日
     */
    public void queryAllFestivalAsync(final boolean isRefresh, final IRealmQueryFestivalCallback callback) {
        try {
            getRealm().executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<FestivalModel> festivalModels = getRealm().where(FestivalModel.class).findAllAsync();
                    festivalModels.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<FestivalModel>>() {
                        @Override
                        public void onChange(RealmResults<FestivalModel> festivalModels, OrderedCollectionChangeSet changeSet) {
                            if (festivalModels.size() > 0) {
                                List<FestivalModel> results = getRealm().copyFromRealm(festivalModels);
                                if (null != callback) {
                                    callback.getResult(isRefresh, results);
                                }
                            }
                        }
                    });
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
    }

    /**
     * 判断当天是否是节日
     */
    public void queryTadayFestivalAsync(final IRealmQueryFestivalCallback callback) {
        try {
            getRealm().executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<FestivalModel> festivalModels = getRealm().where(FestivalModel.class).findAllAsync();
                    festivalModels.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<FestivalModel>>() {
                        @Override
                        public void onChange(RealmResults<FestivalModel> festivalModels, OrderedCollectionChangeSet changeSet) {
                            if (festivalModels.size() > 0) {
                                List<FestivalModel> temp = getRealm().copyFromRealm(festivalModels);
                                FestivalModel model = null;
                                for (FestivalModel festivalModel : temp) {
                                    if (TimeUtility.isToday(new Date(festivalModel.getFestivalDate()))) {
                                        model = festivalModel;
                                        break;
                                    }
                                }
                                if (null != callback) {
                                    callback.getFestival(model);
                                }
                            }
                        }
                    });
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
    }

    /**
     * 获取全部账单
     */
    public void queryAllTallyAsync(final boolean isRefresh, final IRealmQueryTallyCallback callback) {
        try {
            getRealm().executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<TallyModel> tallyModels = getRealm().where(TallyModel.class).findAllAsync();
                    tallyModels.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<TallyModel>>() {
                        @Override
                        public void onChange(RealmResults<TallyModel> tallyModels, OrderedCollectionChangeSet changeSet) {
                            if (tallyModels.size() > 0) {
                                List<TallyModel> results = getRealm().copyFromRealm(tallyModels);
                                if (null != callback) {
                                    callback.getResults(isRefresh, results);
                                }
                            }
                        }
                    });
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
    }

    /**
     * 获取月份的账单
     */
    public void queryTallyByMonthAsync(final boolean isRefresh, final long time, final IRealmQueryTallyCallback callback) {
        try {
            getRealm().executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    long targetTime;
                    if (time < 0) {
                        targetTime = System.currentTimeMillis();
                    } else {
                        targetTime = time;
                    }
                    RealmResults<TallyModel> tallyModels = getRealm().where(TallyModel.class).findAllAsync();
                    tallyModels.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<TallyModel>>() {
                        @Override
                        public void onChange(RealmResults<TallyModel> tallyModels, OrderedCollectionChangeSet changeSet) {
                            if (tallyModels.size() > 0) {
                                List<TallyModel> temp = getRealm().copyFromRealm(tallyModels);
                                List<TallyModel> results = new ArrayList<>();
                                for (TallyModel model : temp) {
                                    if (TimeUtility.isThisMonth(new Date(model.getTime()))) {
                                        results.add(model);
                                    }
                                }
                                if (null != callback) {
                                    callback.getResults(isRefresh, results);
                                }
                            }
                        }
                    });
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
    }

}
