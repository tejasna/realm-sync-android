package com.realm.sync.data;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmManager {

    public static RealmManager realmManager;
    public static RealmConfiguration realmConfig;
    public static Realm realm;
    public static Context appContext;

    public static RealmManager getInstance(Context context) {

        if (realmManager == null) {
            realmManager = new RealmManager();
            realmConfig = new RealmConfiguration.Builder(context).deleteRealmIfMigrationNeeded().build();
            realm = Realm.getInstance(realmConfig);
            appContext = context;

        }

        return realmManager;
    }

    public static Realm getRealm() {
        return realm;
    }

}
