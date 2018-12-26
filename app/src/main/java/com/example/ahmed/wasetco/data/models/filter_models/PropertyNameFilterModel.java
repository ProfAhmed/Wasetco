package com.example.ahmed.wasetco.data.models.filter_models;

public class PropertyNameFilterModel {
    private String name, id;

    public PropertyNameFilterModel(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}
