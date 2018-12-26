package com.example.ahmed.wasetco.data.models.filter_models;

public class FinishingTypeFilterModel {
    private String name, id;

    public FinishingTypeFilterModel(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
