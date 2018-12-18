package com.example.ahmed.wasetco.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.ahmed.wasetco.data.models.RealEstateModel;
import com.example.ahmed.wasetco.data.repositories.Repository;

import java.util.ArrayList;

public class RealEstateViewModel extends AndroidViewModel {
    private Repository repository;
    private MutableLiveData<Integer> page = new MutableLiveData<>();

    public RealEstateViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);
    }

    public LiveData<ArrayList<RealEstateModel>> getRealEstates(int offset) {
        return repository.getRealEstates(offset);
    }
}
