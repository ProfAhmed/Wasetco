package com.example.ahmed.wasetco.events;

import com.example.ahmed.wasetco.data.models.RealEstateModel;

import java.util.ArrayList;

public class EventRealEstates {
    private ArrayList<RealEstateModel> realEstatelList;

    public EventRealEstates(ArrayList<RealEstateModel> realEstatelList) {
        this.realEstatelList = realEstatelList;
    }

    public ArrayList<RealEstateModel> getRealEstatelList() {
        return realEstatelList;
    }
}
