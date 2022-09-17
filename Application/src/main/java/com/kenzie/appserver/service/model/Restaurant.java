package com.kenzie.appserver.service.model;

import java.util.List;

/** Created by Shirley Tong **/
public class Restaurant {
    private final String restaurantId;
    private final String restaurantName;
    private final String rating;
    private final String status;
    private final String cuisine;
    private final String location;
    private final List<String> menu;


    public Restaurant(String restaurantId, String restaurantName, String rating, String status, String cuisine, String location, List<String> menu) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.rating = rating;
        this.status = status;
        this.cuisine = cuisine;
        this.location = location;
        this.menu = menu;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getRating() {
        return rating;
    }

    public String getStatus() {
        return status;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getLocation() {
        return location;
    }

    public List<String> getMenu() {
        return menu;
    }
}
