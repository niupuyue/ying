package com.paulniu.ying.callback;

import com.paulniu.ying.model.AffairModel;

import java.util.List;

import io.realm.RealmObject;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-14
 * Time: 12:02
 * Desc: 数据库操作回调
 * Version:
 */
public interface IRealmQueryCallback extends IBaseRealmCallback {

    void getResult(boolean isRefresh, List<AffairModel> results);

}
