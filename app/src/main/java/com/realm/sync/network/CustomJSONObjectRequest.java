package com.realm.sync.network;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CustomJSONObjectRequest extends JsonObjectRequest {

    public String authKey = null;
    private Map<String, String> params;
    public static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";
    public static final String CONTENT_TYPE_HEADER_VALUE = "application/json; charset=utf-8";
    public static final String AUTH_HEADER = "Authorization";

    public CustomJSONObjectRequest(int method, String url, JSONObject jsonRequest,
                                   Response.Listener<JSONObject> listener,
                                   Response.ErrorListener errorListener, String authKey) {
        super(method, url, jsonRequest, listener, errorListener);
        this.authKey = authKey;
        this.params = null;

    }


    protected Map<String, String> getParams()
            throws AuthFailureError {

        return params;
    }

    @SuppressWarnings("ThrowableInstanceNeverThrown")
    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
            volleyError = new VolleyError(new String(volleyError.networkResponse.data) + authKey);
        }

        return volleyError;
    }

    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE_HEADER_KEY, CONTENT_TYPE_HEADER_VALUE);

        if (authKey != null) {
            headers.put(AUTH_HEADER, "Token " + authKey);
        }

        return headers;
    }


}