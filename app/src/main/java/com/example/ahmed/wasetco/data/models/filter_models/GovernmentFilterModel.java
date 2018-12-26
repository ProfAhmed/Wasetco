package com.example.ahmed.wasetco.data.models.filter_models;

public class GovernmentFilterModel {
    private String name, id;

    public GovernmentFilterModel(String name, String id) {
        this.name = name;
        this.id = id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
