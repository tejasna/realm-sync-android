
package com.realm.sync.model;


import io.realm.RealmObject;

public class Main extends RealmObject {

    private Float temp;
    private Integer pressure;
    private Float temp_min;
    private Float temp_max;
    private Integer humidity;

    public Float getTemp() {
        return temp;
    }

    public void setTemp(Float temp) {
        this.temp = temp;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Float getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(Float temp_min) {
        this.temp_min = temp_min;
    }

    public Float getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(Float temp_max) {
        this.temp_max = temp_max;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }
}
