package com.example.ahmed.wasetco.data.models;

public class RequestType {
    private int page;
    private String type;

    public RequestType(int page, String type) {
        this.page = page;
        this.type = type;
    }

    public int getPage() {
        return page;
    }

    public String getType() {
        return type;
    }
}
