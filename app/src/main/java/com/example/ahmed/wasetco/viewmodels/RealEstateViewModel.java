package com.example.ahmed.wasetco.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.ahmed.wasetco.data.models.RealEstateModel;
import com.example.ahmed.wasetco.data.repositories.Repository;

import java.util.ArrayList;

public class RealEstateViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<ArrayList<RealEstateModel>> realEstatLiveData;

    public RealEstateViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);
        realEstatLiveData = repository.getRealEstates();
    }

    public LiveData<ArrayList<RealEstateModel>> getRealEstates() {
        return realEstatLiveData;
    }
}
