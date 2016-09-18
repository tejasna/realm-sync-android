package com.realm.sync.model;

import java.lang.String;import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class UserObject extends RealmObject {

    private String userName;
    private String authToken;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
