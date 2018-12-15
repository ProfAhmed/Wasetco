package com.example.ahmed.wasetco.data.api;

import com.example.ahmed.wasetco.data.models.AgentModel;
import com.example.ahmed.wasetco.data.models.RealEstateFeaturedModel;
import com.example.ahmed.wasetco.data.models.RealEstateModel;
import com.example.ahmed.wasetco.data.models.RealEstateSaleModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParser {
    static public ArrayList<RealEstateModel> parseRealEstates(JSONObject jsonObject) {
        ArrayList<RealEstateModel> realEstateModels = new ArrayList<>();

        try {
            JSONArray realEstatesList = jsonObject.getJSONArray("data");
            for (int i = 0; i < realEstatesList.length(); i++) {
                JSONObject currentObject = realEstatesList.getJSONObject(i);
                String property_title = currentObject.getString("property_title");
                String address = currentObject.getString("address");
                String description = currentObject.getString("description");
                String price = currentObject.getString("price");
                String bathrooms = currentObject.getString("bathrooms");
                String bedrooms = currentObject.getString("bedrooms");
                String land_area = currentObject.getString("land_area");
                String featured_image = currentObject.getString("featured_image");
                String map_latitude = currentObject.getString("map_latitude");
                String map_longitude = currentObject.getString("map_longitude");


                RealEstateModel realEstateModel = new RealEstateModel(property_title, address, description, price,
                        bathrooms, bedrooms, land_area, featured_image, map_latitude, map_longitude);
                realEstateModels.add(realEstateModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return realEstateModels;
    }

    static public ArrayList<RealEstateFeaturedModel> parseFeaturedRealEstates(JSONObject jsonObject) {
        ArrayList<RealEstateFeaturedModel> realEstateModels = new ArrayList<>();

        try {
            JSONArray realEstatesList = jsonObject.getJSONArray("data");
            for (int i = 0; i < realEstatesList.length(); i++) {
                JSONObject currentObject = realEstatesList.getJSONObject(i);
                String property_name = currentObject.getString("property_name");
                String price = currentObject.getString("price");
                String address = currentObject.getString("address");
                String bathrooms = currentObject.getString("bathrooms");
                String bedrooms = currentObject.getString("bedrooms");
                String land_area = currentObject.getString("land_area");
                String description = currentObject.getString("description");
                String featured_image = currentObject.getString("featured_image");

                RealEstateFeaturedModel realEstateFeaturedModel = new RealEstateFeaturedModel(property_name, price, address, description,
                        bathrooms, bedrooms, land_area, featured_image);
                realEstateModels.add(realEstateFeaturedModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return realEstateModels;
    }

    static public ArrayList<RealEstateSaleModel> parseSaleRealEstates(JSONObject jsonObject) {
        ArrayList<RealEstateSaleModel> realEstateModels = new ArrayList<>();

        try {
            JSONArray realEstatesList = jsonObject.getJSONArray("data");
            for (int i = 0; i < realEstatesList.length(); i++) {
                JSONObject currentObject = realEstatesList.getJSONObject(i);
                String property_names = currentObject.getString("property_names");
                String price = currentObject.getString("price");
                String address = currentObject.getString("address");
                String bathrooms = currentObject.getString("bathrooms");
                String bedrooms = currentObject.getString("bedrooms");
                String land_area = currentObject.getString("land_area");
                String description = currentObject.getString("description");
                String featured_image = currentObject.getString("featured_image");

                RealEstateSaleModel realStateSaleModel = new RealEstateSaleModel(property_names, price, address,
                        bathrooms, bedrooms, land_area, description, featured_image);
                realEstateModels.add(realStateSaleModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return realEstateModels;
    }

    static public ArrayList<AgentModel> parseAgent(JSONObject jsonObject) {
        ArrayList<AgentModel> agents = new ArrayList<>();

        try {
            JSONArray agentList = jsonObject.getJSONArray("data");
            for (int i = 0; i < agentList.length(); i++) {
                JSONObject currentObject = agentList.getJSONObject(i);
                int id = currentObject.getInt("id");
                String name = currentObject.getString("name");
                String facebook = currentObject.getString("facebook");
                String twitter = currentObject.getString("twitter");
                String gplus = currentObject.getString("gplus");
                String linkedin = currentObject.getString("linkedin");
                String image_icon = currentObject.getString("image_icon");

                AgentModel agentModel = new AgentModel(id, name, facebook, twitter, gplus, linkedin, image_icon);
                agents.add(agentModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return agents;
    }

    static public ArrayList<RealEstateFeaturedModel> parseAgentRealEstate(JSONArray jsonArray) {
        ArrayList<RealEstateFeaturedModel> agentRealEstate = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject currentObject = jsonArray.getJSONObject(i);
                String property_name = currentObject.getString("property_name");
                String price = currentObject.getString("price");
                String address = currentObject.getString("address");
                String bathrooms = currentObject.getString("bathrooms");
                String bedrooms = currentObject.getString("bedrooms");
                String land_area = currentObject.getString("land_area");
                String description = currentObject.getString("description");
                String featured_image = currentObject.getString("featured_image");

                RealEstateFeaturedModel realEstateFeaturedModel = new RealEstateFeaturedModel(property_name, price, address, description,
                        bathrooms, bedrooms, land_area, featured_image);
                agentRealEstate.add(realEstateFeaturedModel);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return agentRealEstate;
    }
}
