package com.paulniu.ying.model;

import java.io.Serializable;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-10
 * Time: 23:13
 * Desc: 日常事宜
 * Version:
 */
@RealmClass
public class AffairModel implements Serializable, RealmModel {

    @PrimaryKey
    private int id;

    private String affairNote;

    private long affairTime = 0L;

    private int affairType = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAffairNote() {
        return affairNote;
    }

    public void setAffairNote(String affairNote) {
        this.affairNote = affairNote;
    }

    public long getAffairTime() {
        return affairTime;
    }

    public void setAffairTime(long affairTime) {
        this.affairTime = affairTime;
    }

    public int getAffairType() {
        return affairType;
    }

    public void setAffairType(int affairType) {
        this.affairType = affairType;
    }
}
