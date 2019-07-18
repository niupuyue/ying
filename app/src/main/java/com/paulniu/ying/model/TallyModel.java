package com.paulniu.ying.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-15
 * Time: 22:07
 * Desc: 账单对象
 * Version:
 */
public class TallyModel extends RealmObject {

    @PrimaryKey
    private long time;

    private String note;// 用途

    private double amount;

    private int type;// 类型  两种，收入和支出

    private String usage;// 用途

    private int avator;// 图标

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
