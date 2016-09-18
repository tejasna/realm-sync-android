
package com.realm.sync.model;


import io.realm.RealmObject;

public class Wind extends RealmObject {

    private Float speed;
    private Integer deg;

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public Integer getDeg() {
        return deg;
    }

    public void setDeg(Integer deg) {
        this.deg = deg;
    }
}
