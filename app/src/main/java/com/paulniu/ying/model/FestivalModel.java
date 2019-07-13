package com.paulniu.ying.model;

import java.io.Serializable;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-10
 * Time: 22:50
 * Desc: 节日信息
 * Version:
 */
@RealmClass
public class FestivalModel implements Serializable, RealmModel {

    @PrimaryKey
    private int id;

    private String festivalName;

    private String festivalNote;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFestivalNote() {
        return festivalNote;
    }

    public void setFestivalNote(String festivalNote) {
        this.festivalNote = festivalNote;
    }

    private long festivalDate = 0L;

    public String getFestivalName() {
        return festivalName;
    }

    public void setFestivalName(String festivalName) {
        this.festivalName = festivalName;
    }

    public long getFestivalDate() {
        return festivalDate;
    }

    public void setFestivalDate(long festivalDate) {
        this.festivalDate = festivalDate;
    }
}
