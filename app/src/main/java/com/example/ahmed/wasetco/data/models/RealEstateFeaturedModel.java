package com.example.ahmed.wasetco.data.models;

public class RealEstateFeaturedModel {

    private Integer id;
    private String userId;
    private String featuredProperty;
    private String propertyName;
    private String propertyNameId;
    private String propertyFinishId;
    private String propertySlug;
    private String propertyType;
    private Object propertyInstallmentId;
    private String propertyPurpose;
    private Object governmentId;
    private Object cityId;
    private String price;
    private String sprice;
    private String address;
    private String mapLatitude;
    private String mapLongitude;
    private String bathrooms;
    private String  bedrooms;
    private Object garage;
    private String landArea;
    private String buildArea;
    private String description;
    private String propertyFeatures;
    private String featuredImage;
    private String floorPlan;
    private String videoCode;
    private String offer;
    private Object offerDateFrom;
    private Object offerDateTo;
    private String status;
    private String soldOut;
    private String commissionLevels;
    private String createdAt;
    private String updatedAt;

    public RealEstateFeaturedModel(String propertyName,String price, String address, String bathrooms, String bedrooms, String landArea, String description, String featuredImage) {
        this.propertyName = propertyName;
        this.price = price;
        this.address = address;
        this.bathrooms = bathrooms;
        this.bedrooms = bedrooms;
        this.landArea = landArea;
        this.description = description;
        this.featuredImage = featuredImage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFeaturedProperty() {
        return featuredProperty;
    }

    public void setFeaturedProperty(String featuredProperty) {
        this.featuredProperty = featuredProperty;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyNameId() {
        return propertyNameId;
    }

    public void setPropertyNameId(String propertyNameId) {
        this.propertyNameId = propertyNameId;
    }

    public String getPropertyFinishId() {
        return propertyFinishId;
    }

    public void setPropertyFinishId(String propertyFinishId) {
        this.propertyFinishId = propertyFinishId;
    }

    public String getPropertySlug() {
        return propertySlug;
    }

    public void setPropertySlug(String propertySlug) {
        this.propertySlug = propertySlug;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public Object getPropertyInstallmentId() {
        return propertyInstallmentId;
    }

    public void setPropertyInstallmentId(Object propertyInstallmentId) {
        this.propertyInstallmentId = propertyInstallmentId;
    }

    public String getPropertyPurpose() {
        return propertyPurpose;
    }

    public void setPropertyPurpose(String propertyPurpose) {
        this.propertyPurpose = propertyPurpose;
    }

    public Object getGovernmentId() {
        return governmentId;
    }

    public void setGovernmentId(Object governmentId) {
        this.governmentId = governmentId;
    }

    public Object getCityId() {
        return cityId;
    }

    public void setCityId(Object cityId) {
        this.cityId = cityId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSprice() {
        return sprice;
    }

    public void setSprice(String sprice) {
        this.sprice = sprice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Object getGarage() {
        return garage;
    }

    public void setGarage(Object garage) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getFloorPlan() {
        return floorPlan;
    }

    public void setFloorPlan(String floorPlan) {
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

    public Object getOfferDateFrom() {
        return offerDateFrom;
    }

    public void setOfferDateFrom(Object offerDateFrom) {
        this.offerDateFrom = offerDateFrom;
    }

    public Object getOfferDateTo() {
        return offerDateTo;
    }

    public void setOfferDateTo(Object offerDateTo) {
        this.offerDateTo = offerDateTo;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}