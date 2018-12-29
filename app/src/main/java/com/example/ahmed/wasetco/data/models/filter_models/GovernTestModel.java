package com.example.ahmed.wasetco.data.models.filter_models;

import java.util.ArrayList;

public class GovernTestModel {
    private String name, id;
    private ArrayList<CityFilterModel> cityFilterModels;

    public GovernTestModel(String name, String id, ArrayList cityFilterModels) {
        this.name = name;
        this.id = id;
        this.cityFilterModels = cityFilterModels;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public ArrayList<CityFilterModel> getCityFilterModels() {
        return cityFilterModels;
    }
}
