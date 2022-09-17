package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
/** Created by Marc Santos **/
public class ReviewCreateRequest {

    @NotEmpty
    @JsonProperty("restaurantId")
    private String restaurantId;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("rating")
    private String rating;
    @JsonProperty("review")
    private String review;

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
