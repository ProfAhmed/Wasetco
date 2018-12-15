package com.example.ahmed.wasetco.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.example.ahmed.wasetco.data.models.RealEstateFeaturedModel;
import com.example.ahmed.wasetco.data.repositories.Repository;

import java.util.ArrayList;

public class AgentRealEstatesViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<ArrayList<RealEstateFeaturedModel>> realEstatLiveData;
    private MutableLiveData<String> agentId = new MutableLiveData<>();

    public AgentRealEstatesViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        realEstatLiveData = Transformations.switchMap(agentId, agentId -> {
            return repository.getAgentsRealEstates(agentId);
        });
    }

    public void setAgentId(String agentId) {
        this.agentId.setValue(agentId);
    }

    public LiveData<ArrayList<RealEstateFeaturedModel>> getAgentRealEstates() {
        return realEstatLiveData;
    }
}
