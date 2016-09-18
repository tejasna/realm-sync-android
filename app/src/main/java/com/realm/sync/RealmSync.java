package com.realm.sync;

import android.content.Context;
import android.content.Loader;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.realm.sync.constant.APIConstants;
import com.realm.sync.model.UserObject;
import com.realm.sync.network.CustomJSONObjectRequest;
import com.realm.sync.network.CustomVolleyRequestQueue;
import com.realm.sync.network.EndpointsEnum;
import com.realm.sync.data.RealmManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RealmSync extends Loader<List<RealmObject>> implements Response.Listener, Response.ErrorListener {

    private static final String JSON = "TAG";
    public static final String TAG = RealmSync.class.getName();

    private CustomJSONObjectRequest jsonRequest;
    private EndpointsEnum.Endpoints endpoint;
    private RequestQueue requestQueue;
    private JSONObject jsonObject;
    private UserObject user;
    private int method;
    private String url;

    private List<RealmObject> cachedData = null;


    public RealmSync(Context context, UserObject user, EndpointsEnum.Endpoints endpoint,
                     int method, JSONObject jsonObject) {
        super(context);

        this.method = method;
        this.jsonObject = jsonObject;
        this.user = user;
        this.endpoint = endpoint;

        requestQueue = CustomVolleyRequestQueue.getInstance(getContext()).getRequestQueue();
    }

    @Override
    protected void onStartLoading() {
        if (cachedData == null) {
            forceLoad();
        } else {
            deliverResult(cachedData);
        }
    }

    @Override
    public void deliverResult(List<RealmObject> realmObject) {
        super.deliverResult(realmObject);
    }

    @Override
    protected void onForceLoad() {

        Log.d(TAG, "LOADER - requesting new data");

        requestQueue.cancelAll(JSON);
        int tempMethod = 1;
        if (method == (Request.Method.GET)) {
            tempMethod = 0;
        }

        String authKey = user.getAuthToken();

        EndpointsEnum endpoint = new EndpointsEnum(this.endpoint);

        url = endpoint.resolveEndpoint(APIConstants.City);

        jsonRequest = new CustomJSONObjectRequest(
                tempMethod, url, jsonObject, this, this, authKey);

        jsonRequest.setTag(JSON);

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(

                APIConstants.SOCKET_TIMEOUT_IN_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonRequest);

        logRequest();

    }

    public void loadNewData() {
        forceLoad();
    }

    @Override
    protected void onReset() {
        requestQueue.cancelAll(JSON);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG, error.getMessage());
        deliverResult(Collections.<RealmObject>emptyList());
    }

    @Override
    public void onResponse(Object response) {

        JSONObject responseObject = new JSONObject();
        Realm realm = RealmManager.getRealm();

        try {
            responseObject = new JSONObject(response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        logResponse(responseObject);

        com.realm.sync.data.RealmSync synced = new com.realm.sync.data.RealmSync(responseObject, url, getContext());

        Class syncedClass = synced.getSyncedClass();

        RealmResults<RealmObject> realmResult = realm.where(syncedClass).findAll();

        List<RealmObject> realmObjects = new ArrayList<>();

        realmObjects.addAll(realmResult);

        this.cachedData = new ArrayList<>(realmObjects);

        deliverResult(realmObjects);

    }

    private void logRequest() {
        try {
            Log.d(TAG, "Network equest: " + jsonRequest.getMethod() + " " + url + "\n" +
                    "Content-type: " + jsonRequest.getBodyContentType() + "\n" +
                    "Headers: " + jsonRequest.getHeaders());

        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
    }

    private void logResponse(JSONObject response) {
        Log.d(TAG, response.toString());
    }

}
