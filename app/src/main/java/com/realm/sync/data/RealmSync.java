package com.realm.sync.data;

import android.content.Context;

import com.realm.sync.constant.APIConstants;

import org.json.JSONObject;

import io.realm.Realm;

public class RealmSync {

    private JSONObject jsonObject;
    private Realm realm;
    private String url;
    private Class realmClass;

    public RealmSync(JSONObject jsonObject, String url, Context context) {

        this.jsonObject = jsonObject;
        this.url = url;
        RealmManager.getInstance(context);
        realm = RealmManager.getRealm();

        saveRealmObject();

    }

    public void saveRealmObject() {

        Class realmClass = getSyncedClass();
        String json = "";
        if (!jsonObject.toString().startsWith("[")) {
            json = "[" + jsonObject.toString() + "]";
        }

        realm.beginTransaction();
        realm.where(realmClass).findAll().clear();
        realm.createAllFromJson(realmClass, json);
        realm.commitTransaction();

    }

    public Class<?> getSyncedClass() {

        for (Object key : APIConstants.realmMapper.keySet()) {

            if (url.contains(key.toString())) {

                realmClass = APIConstants.realmMapper.get(key);
            }
        }
        return realmClass;
    }

}
