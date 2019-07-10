package com.paulniu.ying.util.infomanager;

import com.paulniu.ying.model.FestivalModel;

import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-10
 * Time: 22:55
 * Desc: 用户信息管理类
 * Version:
 */
public class UserInfoManager {

    private UserInfoManager() {

    }

    private static class UserInfoManagerHelper {
        public static final UserInfoManager INSTANCE = new UserInfoManager();
    }

    public static UserInfoManager getInstance() {
        return UserInfoManagerHelper.INSTANCE;
    }

    /**
     * 获取用户记录的节日信息
     * todo
     */
    public synchronized List<FestivalModel> getFestivals() {
        return null;
    }

    /**
     * 用户添加节日信息
     * TODO
     * @param model
     */
    public synchronized void setFestival(FestivalModel model) {

    }

}
