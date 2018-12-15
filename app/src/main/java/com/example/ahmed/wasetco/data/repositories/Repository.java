package com.example.ahmed.wasetco.data.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.ahmed.wasetco.data.Constants;
import com.example.ahmed.wasetco.data.api.IResult;
import com.example.ahmed.wasetco.data.api.JsonParser;
import com.example.ahmed.wasetco.data.api.VolleyService;
import com.example.ahmed.wasetco.data.models.AgentModel;
import com.example.ahmed.wasetco.data.models.RealEstateFeaturedModel;
import com.example.ahmed.wasetco.data.models.RealEstateModel;
import com.example.ahmed.wasetco.data.models.RealEstateSaleModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Repository {
    IResult mResultCallback = null;
    VolleyService mVolleyService;
    Application mContext;

    public Repository(Application mContext) {
        this.mContext = mContext;
    }

    public LiveData<ArrayList<RealEstateModel>> getRealEstates() {
        final MutableLiveData<ArrayList<RealEstateModel>> data = new MutableLiveData<>();
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {

                ArrayList<RealEstateModel> realEstateModels = JsonParser.parseRealEstates(response);
                data.setValue(realEstateModels);
                Log.v("ResponseState", "Success " + response.toString());
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.v("ResponseState", "Failer" + error.getMessage());
            }

            @Override
            public void notifySuccess(String requestType, JSONArray response) {

            }
        };

        mVolleyService = new VolleyService(mResultCallback, mContext);

        mVolleyService.getDataVolley(Constants.GET_CALL_JSON_OBJECT, Constants.GET_PROPERTIES);

        return data;
    }

    public LiveData<ArrayList<RealEstateFeaturedModel>> getFeaturedRealEstates() {
        final MutableLiveData<ArrayList<RealEstateFeaturedModel>> data = new MutableLiveData<>();
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {

                ArrayList<RealEstateFeaturedModel> realEstateFeaturedModels = JsonParser.parseFeaturedRealEstates(response);
                data.setValue(realEstateFeaturedModels);
                Log.v("ResponseStateFeatured", "Success " + response.toString());
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.v("ResponseStateFeatured", "Failer" + error.getMessage());
            }

            @Override
            public void notifySuccess(String requestType, JSONArray response) {

            }
        };

        mVolleyService = new VolleyService(mResultCallback, mContext);

        mVolleyService.getDataVolley(Constants.GET_CALL_JSON_OBJECT, Constants.GET_FEATURED);

        return data;
    }

    public LiveData<ArrayList<RealEstateSaleModel>> getSaleRealEstates(String type) {

        final MutableLiveData<ArrayList<RealEstateSaleModel>> data = new MutableLiveData<>();

        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {

                ArrayList<RealEstateSaleModel> realEstateFeaturedModels = JsonParser.parseSaleRealEstates(response);
                data.setValue(realEstateFeaturedModels);
                Log.v("ResponseStateFeatured", "Success " + response.toString());
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.v("ResponseStateFeatured", "Failer" + error.getMessage());
            }

            @Override
            public void notifySuccess(String requestType, JSONArray response) {

            }
        };

        mVolleyService = new VolleyService(mResultCallback, mContext);

        if (type.equals("sale")) {
            mVolleyService.getDataVolley(Constants.GET_CALL_JSON_OBJECT, Constants.GET_SALE);
        } else {
            mVolleyService.getDataVolley(Constants.GET_CALL_JSON_OBJECT, Constants.GET_RENT);
        }

        return data;
    }

    public LiveData<ArrayList<AgentModel>> getAgents() {
        final MutableLiveData<ArrayList<AgentModel>> data = new MutableLiveData<>();
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {

                ArrayList<AgentModel> agentModels = JsonParser.parseAgent(response);
                data.setValue(agentModels);
                Log.v("ResponseStateAgents", "Success " + response.toString());
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.v("ResponseStateAgents", "Failer" + error.getMessage());
            }

            @Override
            public void notifySuccess(String requestType, JSONArray response) {

            }
        };

        mVolleyService = new VolleyService(mResultCallback, mContext);

        mVolleyService.getDataVolley(Constants.GET_CALL_JSON_OBJECT, Constants.GET_AGENTS);

        return data;
    }

    public LiveData<ArrayList<RealEstateFeaturedModel>> getAgentsRealEstates(String agentId) {
        final MutableLiveData<ArrayList<RealEstateFeaturedModel>> data = new MutableLiveData<>();
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {

            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.v("ResponseAgentsDetalis", "Failer" + error.getMessage());
            }

            @Override
            public void notifySuccess(String requestType, JSONArray response) {

                ArrayList<RealEstateFeaturedModel> agentModels = JsonParser.parseAgentRealEstate(response);
                data.setValue(agentModels);
                Log.v("ResponseAgentsDetalis", "Success " + response.toString());
            }
        };

        mVolleyService = new VolleyService(mResultCallback, mContext);

        mVolleyService.getDataVolley(Constants.GET_CALL_JSON_ARRAY, Constants.GET_AGENTS_DETAILS + agentId);

        return data;
    }
}
