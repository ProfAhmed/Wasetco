package com.example.ahmed.wasetco.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.ahmed.wasetco.data.models.RealEstateFeaturedModel;
import com.example.ahmed.wasetco.data.repositories.Repository;

import java.util.ArrayList;

public class RealEstateFeaturedViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<ArrayList<RealEstateFeaturedModel>> realEstatLiveData;

    public RealEstateFeaturedViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);
    }

    public LiveData<ArrayList<RealEstateFeaturedModel>> getFeaturedRealEstates(int page) {
        realEstatLiveData = repository.getFeaturedRealEstates(page);
        return realEstatLiveData;
    }

    public LiveData<ArrayList<RealEstateFeaturedModel>> getFilter(String url) {
        realEstatLiveData = repository.getFilter(url);
        return realEstatLiveData;
    }


}
