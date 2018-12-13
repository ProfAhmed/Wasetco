package com.example.ahmed.wasetco.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.example.ahmed.wasetco.data.models.RealEstateSaleModel;
import com.example.ahmed.wasetco.data.repositories.Repository;

import java.util.ArrayList;

public class RealEstatSaleViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<ArrayList<RealEstateSaleModel>> realEstateSaleData;
    private MutableLiveData<String> type = new MutableLiveData<>();

    public RealEstatSaleViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);

        realEstateSaleData = Transformations.switchMap(type, type -> {
            return repository.getSaleRealEstates(type);
        });
    }

    public void setType(String type) {
        this.type.setValue(type);
    }

    public LiveData<ArrayList<RealEstateSaleModel>> getRealEstateSale() {
        return realEstateSaleData;
    }

}
