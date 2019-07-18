package com.paulniu.ying.database;

import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.utils.TimeUtility;
import com.paulniu.ying.callback.IBaseRealmCallback;
import com.paulniu.ying.callback.IRealmQueryAffairCallback;
import com.paulniu.ying.callback.IRealmQueryTallyCallback;
import com.paulniu.ying.model.AffairModel;
import com.paulniu.ying.model.TallyModel;

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
    public void queryYearAffairAsync(final long time, final IRealmQueryAffairCallback callback) {
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                long target;
                if (time <= 0) {
                    target = System.currentTimeMillis();
                } else {
                    target = time;
                }
                // 获取一年的开始和结束
                long startTime = TimeUtility.getDayStarByYear(TimeUtility.getCalendar(target)).getTimeInMillis();
                long endTime = TimeUtility.getDayEndByYear(TimeUtility.getCalendar(target)).getTimeInMillis();
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

    /**
     * 修改某一个事务
     */
    public void updateAffairAsync(final AffairModel model, final IRealmQueryAffairCallback callback) {
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
        if (null == callback || model == null) return;
        if (model.getAffairTime() <= 0) {
            callback.onError();
            return;
        }
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                AffairModel affairModel = realm.where(AffairModel.class).equalTo("affairTime", model.getAffairTime()).findFirst();
                if (affairModel != null) {
                    affairModel.setAffairType(model.getAffairType());
                    affairModel.setAffairNote(model.getAffairNote());
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

    /**
     * 批量删除多个事务
     */
    public void deleteAffairs(List<AffairModel> models, final IRealmQueryAffairCallback callback) {
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
        if (BaseUtility.isEmpty(models)) return;
        final Long[] tims = new Long[BaseUtility.size(models)];
        for (int i = 0; i < tims.length; i++) {
            tims[i] = models.get(i).getAffairTime();
        }
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<AffairModel> affairModels = realm.where(AffairModel.class).in("affairTime", tims).findAll();
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

    /**
     * 删除某一个事务
     */
    public void deleteAffairByTime(final long time, final IBaseRealmCallback callback) {
        if (null == callback) return;
        if (time <= 0) {
            callback.onError();
        }
        try {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<AffairModel> affairModels = realm.where(AffairModel.class).equalTo("affairTime", time).findAll();
                    if (affairModels.size() > 0) {
                        affairModels.deleteAllFromRealm();
                        if (callback != null) {
                            callback.onSuccess();
                        }
                    }
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
     * 获取全部的记账
     */
    public void queryAllTallyAsync(final IRealmQueryTallyCallback callback) {
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<TallyModel> tallyModels = realm.where(TallyModel.class).findAll();
                List<TallyModel> results = realm.copyFromRealm(tallyModels);
                if (null != callback) {
                    callback.getResults(true, results);
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

    /**
     * 获取单独的某一个记账
     */
    public void querySingleTallyAsync(final long time, final IRealmQueryTallyCallback callback) {
        if (null == realm) {
            realm = Realm.getDefaultInstance();
        }
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<TallyModel> tallyModels = realm.where(TallyModel.class).equalTo("time", time).findAll();
                List<TallyModel> results = realm.copyFromRealm(tallyModels);
                if (!BaseUtility.isEmpty(results)) {
                    if (null != callback) {
                        callback.getTallyResult(results.get(0));
                    }
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

    /**
     * 获取某一天的记账
     */
    public void queryTodayAffairAsync(long time, final IRealmQueryTallyCallback callback) {
        if (null == realm) {
            realm = Realm.getDefaultInstance();
        }
        long target = 0;
        if (time <= 0) {
            target = System.currentTimeMillis();
        } else {
            target = time;
        }
        final long startTime = TimeUtility.getDayStartTime(TimeUtility.getCalendar(target)).getTimeInMillis();
        final long endTime = TimeUtility.getDayEndTime(TimeUtility.getCalendar(target)).getTimeInMillis();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<TallyModel> tallyModels = realm.where(TallyModel.class).between("time", startTime, endTime).findAll();
                List<TallyModel> results = realm.copyFromRealm(tallyModels);
                if (null != callback) {
                    callback.getResults(true, results);
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

    /**
     * 获取某一周的记账
     */
    public void queryWeekAffairAsync(final long time, final IRealmQueryTallyCallback callback) {
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
        long target = 0;
        if (time <= 0) {
            target = System.currentTimeMillis();
        } else {
            target = time;
        }
        final long startTime = TimeUtility.getDayStarByWeek(TimeUtility.getCalendar(target)).getTimeInMillis();
        final long endTime = TimeUtility.getDayEndByWeek(TimeUtility.getCalendar(target)).getTimeInMillis();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<TallyModel> tallyModels = realm.where(TallyModel.class).between("time", startTime, endTime).findAll();
                List<TallyModel> results = realm.copyFromRealm(tallyModels);
                if (callback != null) {
                    callback.getResults(true, results);
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

    /**
     * 获取一个月的记账
     */
    public void queryMonthTallyAsync(long time, final IRealmQueryTallyCallback callback) {
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
        long target = 0;
        if (time <= 0) {
            target = System.currentTimeMillis();
        } else {
            target = time;
        }
        final long startTime = TimeUtility.getDayStarByMonth(TimeUtility.getCalendar(target)).getTimeInMillis();
        final long endTime = TimeUtility.getDayEndByMonth(TimeUtility.getCalendar(target)).getTimeInMillis();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<TallyModel> tallyModels = realm.where(TallyModel.class).between("time", startTime, endTime).findAll();
                List<TallyModel> results = realm.copyFromRealm(tallyModels);
                if (null != callback) {
                    callback.getResults(true, results);
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

    /**
     * 获取一年的记账
     */
    public void queryYearTallyAsync(long time, final IRealmQueryTallyCallback callback) {
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
        long target = 0;
        if (time <= 0) {
            target = System.currentTimeMillis();
        } else {
            target = time;
        }
        final long startTime = TimeUtility.getDayStarByYear(TimeUtility.getCalendar(target)).getTimeInMillis();
        final long endTime = TimeUtility.getDayEndByYear(TimeUtility.getCalendar(target)).getTimeInMillis();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<TallyModel> tallyModels = realm.where(TallyModel.class).between("time", startTime, endTime).findAll();
                List<TallyModel> results = realm.copyFromRealm(tallyModels);
                if (callback != null) {
                    callback.getResults(true, results);
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

    /**
     * 删除某一个记账
     */
    public void deleteTallyByTime(final long time, final IBaseRealmCallback callback) {
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
        if (callback == null) return;
        if (time <= 0) {
            callback.onError();
            return;
        }
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<TallyModel> tallyModels = realm.where(TallyModel.class).equalTo("time", time).findAll();
                if (tallyModels.size() > 0) {
                    tallyModels.deleteAllFromRealm();
                    if (callback != null) {
                        callback.onSuccess();
                    }
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

    /**
     * 批量删除记账
     */
    public void deleteTallys(List<TallyModel> tallyModels, final IBaseRealmCallback callback) {
        if (callback == null) return;
        if (BaseUtility.isEmpty(tallyModels)) {
            callback.onError();
            return;
        }
        final Long[] times = new Long[BaseUtility.size(tallyModels)];
        for (int i = 0; i < times.length; i++) {
            times[i] = tallyModels.get(i).getTime();
        }
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<TallyModel> models = realm.where(TallyModel.class).in("time", times).findAll();
                if (models.size() > 0) {
                    models.deleteAllFromRealm();
                    if (callback != null) {
                        callback.onSuccess();
                    }
                }
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
    }
}
