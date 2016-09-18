package com.realm.sync.loader;

import android.content.AsyncTaskLoader;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.realm.sync.R;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class WeatherLoader extends AsyncTaskLoader<List<String>> {

    List<String> cachedData;
    public static final String ACTION = "FORCE";


    public WeatherLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {

        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getContext());
        IntentFilter filter = new IntentFilter(ACTION);
        manager.registerReceiver(broadcastReceiver, filter);
        if (cachedData == null) {
            forceLoad();
        } else {
            super.deliverResult(cachedData);
        }
    }

    @Override
    public List<String> loadInBackground() {
        Log.d("LOADER", "STRING - loading new data");
        return Arrays.asList(getContext().getResources().getStringArray(R.array.items));
    }

    @Override
    public void deliverResult(List<String> data) {
        cachedData = data;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(broadcastReceiver);
    }

    @SuppressWarnings("unused")
    public void loadNewStrings() {
        forceLoad();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            forceLoad();
        }
    };
}
