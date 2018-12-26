package com.example.ahmed.wasetco.data.models;

public class FilterRequestModel {
    String max_price = "";
    String min_price = "";
    String property_name_id = "";
    String government_id = "";
    String city_id = "";
    String property_installment_id = "";
    String property_finish_id = "";
    String purpose = "";
    String type = "";
    String keyword = "";
    String bedrooms = "";
    String bathrooms = "";
    String garage = "";
    String filterResult = max_price
            + min_price
            + property_name_id
            + government_id
            + city_id
            + property_installment_id
            + property_finish_id
            + purpose
            + type
            + keyword
            + bedrooms
            + bathrooms
            + garage;

    public String getFilterResult() {
        return filterResult;
    }
}
