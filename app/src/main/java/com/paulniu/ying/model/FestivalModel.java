package com.paulniu.ying.model;

import java.io.Serializable;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-10
 * Time: 22:50
 * Desc: 节日信息
 * Version:
 */
public class FestivalModel implements Serializable {

    private String festivalName;

    private String festivalNote;

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
