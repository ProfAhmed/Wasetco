package com.example.ahmed.wasetco.data.api;

import android.app.Application;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ahmed.wasetco.data.patterns.VolleySingleton;

import org.json.JSONObject;

public class VolleyService {

    IResult mResultCallback = null;
    Application mContext;

    public VolleyService(IResult resultCallback, Application context) {
        mResultCallback = resultCallback;
        mContext = context;
    }


    public void getDataVolley(final String requestType, String url) {
        try {

            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response);
                            Log.v("Response Service", response.toString());
                            if (mResultCallback != null)
                                mResultCallback.notifySuccess(requestType, response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (mResultCallback != null)
                                mResultCallback.notifyError(requestType, error);
                        }
                    });

            VolleySingleton.getInstance(mContext).addToRequestQueue(jsObjRequest);
        } catch (Exception e) {

        }
    }

}
