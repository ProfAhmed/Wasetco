package com.example.ahmed.wasetco.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.ahmed.wasetco.data.models.RealEstateSaleModel;
import com.example.ahmed.wasetco.data.models.RequestType;
import com.example.ahmed.wasetco.data.repositories.Repository;

import java.util.ArrayList;

public class RealEstatSaleViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<ArrayList<RealEstateSaleModel>> realEstateSaleData;

    public RealEstatSaleViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);

    }


    public LiveData<ArrayList<RealEstateSaleModel>> getRealEstateSale(RequestType type) {
        realEstateSaleData = repository.getSaleRealEstates(type);
        return realEstateSaleData;
    }

}
