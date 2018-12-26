package com.example.ahmed.wasetco.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.ahmed.wasetco.data.models.filter_models.CityFilterModel;
import com.example.ahmed.wasetco.data.models.filter_models.FinishingTypeFilterModel;
import com.example.ahmed.wasetco.data.models.filter_models.GovernmentFilterModel;
import com.example.ahmed.wasetco.data.models.filter_models.PropertyNameFilterModel;
import com.example.ahmed.wasetco.data.repositories.Repository;

import java.util.ArrayList;

public class FilterDataViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<ArrayList<GovernmentFilterModel>> governmentsLiveData;
    private LiveData<ArrayList<CityFilterModel>> citiesLiveData;
    private LiveData<ArrayList<PropertyNameFilterModel>> propertyNamesLiveData;
    private LiveData<ArrayList<FinishingTypeFilterModel>> finishesLiveData;

    public FilterDataViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);
    }

    public LiveData<ArrayList<GovernmentFilterModel>> getGovernmentsLiveData() {
        governmentsLiveData = repository.getGovernmentsFilter();
        return governmentsLiveData;
    }

    public LiveData<ArrayList<CityFilterModel>> getCitiesLiveData(String id) {
        citiesLiveData = repository.getCitiesFilter(id);
        return citiesLiveData;
    }

    public LiveData<ArrayList<PropertyNameFilterModel>> getPropertyNamesLiveData() {
        propertyNamesLiveData = repository.getPropertyNamesFilter();
        return propertyNamesLiveData;
    }

    public LiveData<ArrayList<FinishingTypeFilterModel>> getFinishesLiveData() {
        finishesLiveData = repository.getFinishingTypeFilter();
        return finishesLiveData;
    }
}
