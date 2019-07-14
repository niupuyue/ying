package com.paulniu.ying.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-14
 * Time: 10:51
 * Desc:
 * Version:
 */
public class Dog extends RealmObject {

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
