package com.paulniu.ying.callback;

import com.paulniu.ying.model.AffairModel;
import com.paulniu.ying.model.FestivalModel;

import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-15
 * Time: 21:52
 * Desc: 获取节日信息列表回调
 * Version:
 */
public interface IRealmQueryFestivalCallback extends IBaseRealmCallback {
    void getResult(boolean isRefresh, List<FestivalModel> results);
    void getFestival(FestivalModel model);
}
