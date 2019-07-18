package com.paulniu.ying.callback;

import com.paulniu.ying.model.TallyModel;

import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-15
 * Time: 22:12
 * Desc: 获取账单列表接口回调
 * Version:
 */
public interface IRealmQueryTallyCallback extends IBaseRealmCallback {

    void getResults(boolean isRefresh, List<TallyModel> results);

    void getTallyResult(TallyModel model);

}
