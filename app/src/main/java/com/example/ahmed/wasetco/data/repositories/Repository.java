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
import com.example.ahmed.wasetco.data.models.RequestType;
import com.example.ahmed.wasetco.data.models.filter_models.CityFilterModel;
import com.example.ahmed.wasetco.data.models.filter_models.FinishingTypeFilterModel;
import com.example.ahmed.wasetco.data.models.filter_models.GovernmentFilterModel;
import com.example.ahmed.wasetco.data.models.filter_models.PropertyNameFilterModel;

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

    public LiveData<ArrayList<RealEstateModel>> getRealEstates(int page) {
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

        mVolleyService.getDataVolley(Constants.GET_CALL_JSON_OBJECT, Constants.GET_PROPERTIES + page);

        return data;
    }

    public LiveData<ArrayList<RealEstateFeaturedModel>> getFeaturedRealEstates(int page) {
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

        mVolleyService.getDataVolley(Constants.GET_CALL_JSON_OBJECT, Constants.GET_FEATURED + page);

        return data;
    }

    public LiveData<ArrayList<RealEstateSaleModel>> getSaleRealEstates(RequestType type) {

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
            mVolleyService.getDataVolley(Constants.GET_CALL_JSON_OBJECT, Constants.GET_SALE + type.getPage());
        } else {
            mVolleyService.getDataVolley(Constants.GET_CALL_JSON_OBJECT, Constants.GET_RENT + type.getPage());
        }

        return data;
    }

    public LiveData<ArrayList<AgentModel>> getAgents(int page) {
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

        mVolleyService.getDataVolley(Constants.GET_CALL_JSON_OBJECT, Constants.GET_AGENTS + page);

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

    public LiveData<ArrayList<RealEstateFeaturedModel>> getFilter(String url) {
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
                Log.v("ResponseAgentsDetails", "Success " + response.toString());
            }
        };

        mVolleyService = new VolleyService(mResultCallback, mContext);

        mVolleyService.getDataVolley(Constants.GET_CALL_JSON_ARRAY, url);

        return data;
    }

    public LiveData<ArrayList<GovernmentFilterModel>> getGovernmentsFilter() {
        final MutableLiveData<ArrayList<GovernmentFilterModel>> data = new MutableLiveData<>();
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {

            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.v("ResponseGovernments", "Fail" + error.getMessage());
            }

            @Override
            public void notifySuccess(String requestType, JSONArray response) {

                ArrayList<GovernmentFilterModel> governmentFilterModels = JsonParser.parseGovernmentsFilter(response);
                data.setValue(governmentFilterModels);
                Log.v("ResponseGovernments", "Success " + response.toString());
            }
        };

        mVolleyService = new VolleyService(mResultCallback, mContext);

        mVolleyService.getDataVolley(Constants.GET_CALL_JSON_ARRAY, Constants.GET_ALL_GOVERNMENTS_FILTER);

        return data;
    }

    public LiveData<ArrayList<CityFilterModel>> getCitiesFilter(String id) {
        final MutableLiveData<ArrayList<CityFilterModel>> data = new MutableLiveData<>();
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {

            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.v("ResponseGovernments", "Fail" + error.getMessage());
            }

            @Override
            public void notifySuccess(String requestType, JSONArray response) {

                ArrayList<CityFilterModel> cityFilterModels = JsonParser.parseCitiesFilter(response);
                data.setValue(cityFilterModels);
                Log.v("ResponseGovernments", "Success " + response.toString());
            }
        };

        mVolleyService = new VolleyService(mResultCallback, mContext);

        mVolleyService.getDataVolley(Constants.GET_CALL_JSON_ARRAY, Constants.GET_ALL_CITIES_FILTER + id);

        return data;
    }

    public LiveData<ArrayList<PropertyNameFilterModel>> getPropertyNamesFilter() {
        final MutableLiveData<ArrayList<PropertyNameFilterModel>> data = new MutableLiveData<>();
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {

            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.v("ResponsePropertyNames", "Fail" + error.getMessage());
            }

            @Override
            public void notifySuccess(String requestType, JSONArray response) {

                ArrayList<PropertyNameFilterModel> propertyNameFilterModels = JsonParser.parsePropertyNameFilter(response);
                data.setValue(propertyNameFilterModels);
                Log.v("ResponsePropertyNames", "Success " + response.toString());
            }
        };

        mVolleyService = new VolleyService(mResultCallback, mContext);
        mVolleyService.getDataVolley(Constants.GET_CALL_JSON_ARRAY, Constants.GET_ALL_PROPERTY_NAMES_FILTER);
        return data;
    }

    public LiveData<ArrayList<FinishingTypeFilterModel>> getFinishingTypeFilter() {
        final MutableLiveData<ArrayList<FinishingTypeFilterModel>> data = new MutableLiveData<>();
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {

            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.v("ResponsePropertyNames", "Fail" + error.getMessage());
            }

            @Override
            public void notifySuccess(String requestType, JSONArray response) {

                ArrayList<FinishingTypeFilterModel> propertyNameFilterModels = JsonParser.parseFinishesFilter(response);
                data.setValue(propertyNameFilterModels);
                Log.v("ResponsePropertyNames", "Success " + response.toString());
            }
        };

        mVolleyService = new VolleyService(mResultCallback, mContext);
        mVolleyService.getDataVolley(Constants.GET_CALL_JSON_ARRAY, Constants.GET_ALL_FINISHES_FILTER);
        return data;
    }
}
