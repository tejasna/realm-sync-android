
package com.realm.sync.model;


import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class Coord extends RealmObject{

    private Float lon;
    private Float lat;

    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }
}
