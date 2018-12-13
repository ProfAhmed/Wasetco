package com.example.ahmed.wasetco.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.ahmed.wasetco.data.models.AgentModel;
import com.example.ahmed.wasetco.data.repositories.Repository;

import java.util.ArrayList;

public class AgentViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<ArrayList<AgentModel>> agentLiveData;

    public AgentViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);

        agentLiveData = repository.getAgents();
    }

    public LiveData<ArrayList<AgentModel>> getAgent() {
        return agentLiveData;
    }
}
