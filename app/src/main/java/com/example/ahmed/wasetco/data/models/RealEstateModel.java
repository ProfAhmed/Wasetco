package com.example.ahmed.wasetco.data.models;

import java.io.Serializable;

public class RealEstateModel implements Serializable {


    private Integer id;

    private String propertyTitle;

    private Object government;
    private String owner;

    private String propertyNames;

    private String address;

    private String description;

    private String price;

    private String bathrooms;

    private String bedrooms;

    private String garage;

    private String landArea;

    private String buildArea;

    private String specialPrice;

    private String mapLatitude;

    private String mapLongitude;

    private String propertyFeatures;

    private String featuredImage;

    private Object floorPlan;

    private String videoCode;

    private String offer;

    private Object offerDateTo;

    private Object offerDateFrom;

    private String status;

    private String soldOut;

    private String commissionLevels;

    private String propertyFinish;

    private String propertyTypes;

    private String propertyInstallment;
    private final static long serialVersionUID = 995853656936627121L;

    public RealEstateModel(String propertyTitle, String address, String description, String price, String bathrooms, String bedrooms, String landArea, String featuredImage) {
        this.propertyTitle = propertyTitle;
        this.address = address;
        this.description = description;
        this.price = price;
        this.bathrooms = bathrooms;
        this.bedrooms = bedrooms;
        this.landArea = landArea;
        this.featuredImage = featuredImage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPropertyTitle() {
        return propertyTitle;
    }

    public void setPropertyTitle(String propertyTitle) {
        this.propertyTitle = propertyTitle;
    }

    public Object getGovernment() {
        return government;
    }

    public void setGovernment(Object government) {
        this.government = government;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPropertyNames() {
        return propertyNames;
    }

    public void setPropertyNames(String propertyNames) {
        this.propertyNames = propertyNames;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(String bathrooms) {
        this.bathrooms = bathrooms;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getGarage() {
        return garage;
    }

    public void setGarage(String garage) {
        this.garage = garage;
    }

    public String getLandArea() {
        return landArea;
    }

    public void setLandArea(String landArea) {
        this.landArea = landArea;
    }

    public String getBuildArea() {
        return buildArea;
    }

    public void setBuildArea(String buildArea) {
        this.buildArea = buildArea;
    }

    public String getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(String specialPrice) {
        this.specialPrice = specialPrice;
    }

    public String getMapLatitude() {
        return mapLatitude;
    }

    public void setMapLatitude(String mapLatitude) {
        this.mapLatitude = mapLatitude;
    }

    public String getMapLongitude() {
        return mapLongitude;
    }

    public void setMapLongitude(String mapLongitude) {
        this.mapLongitude = mapLongitude;
    }

    public String getPropertyFeatures() {
        return propertyFeatures;
    }

    public void setPropertyFeatures(String propertyFeatures) {
        this.propertyFeatures = propertyFeatures;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public Object getFloorPlan() {
        return floorPlan;
    }

    public void setFloorPlan(Object floorPlan) {
        this.floorPlan = floorPlan;
    }

    public String getVideoCode() {
        return videoCode;
    }

    public void setVideoCode(String videoCode) {
        this.videoCode = videoCode;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public Object getOfferDateTo() {
        return offerDateTo;
    }

    public void setOfferDateTo(Object offerDateTo) {
        this.offerDateTo = offerDateTo;
    }

    public Object getOfferDateFrom() {
        return offerDateFrom;
    }

    public void setOfferDateFrom(Object offerDateFrom) {
        this.offerDateFrom = offerDateFrom;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSoldOut() {
        return soldOut;
    }

    public void setSoldOut(String soldOut) {
        this.soldOut = soldOut;
    }

    public String getCommissionLevels() {
        return commissionLevels;
    }

    public void setCommissionLevels(String commissionLevels) {
        this.commissionLevels = commissionLevels;
    }

    public String getPropertyFinish() {
        return propertyFinish;
    }

    public void setPropertyFinish(String propertyFinish) {
        this.propertyFinish = propertyFinish;
    }

    public String getPropertyTypes() {
        return propertyTypes;
    }

    public void setPropertyTypes(String propertyTypes) {
        this.propertyTypes = propertyTypes;
    }

    public String getPropertyInstallment() {
        return propertyInstallment;
    }

    public void setPropertyInstallment(String propertyInstallment) {
        this.propertyInstallment = propertyInstallment;
    }

}