package com.paulniu.ying.callback;

import java.util.List;

import io.realm.RealmObject;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-14
 * Time: 12:02
 * Desc: 数据库操作回调
 * Version:
 */
public interface ISqliteDataCallback {

    void onSuccess();

    void onError();

    void getResult(List<RealmObject> results);

}
