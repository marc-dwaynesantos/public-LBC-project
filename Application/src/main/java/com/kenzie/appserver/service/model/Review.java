package com.kenzie.appserver.service.model;

import com.kenzie.appserver.service.RatingOutOfBoundException;

import java.util.Random;
import java.util.UUID;
/** Created by Marc Santos **/
public class Review {
    private final String review;
    private final String restaurantId;
    private final String userId;
    private final String rating;

    public Review(String restaurantId, String userId, String rating, String review) {
        this.review = review;
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getUserId() {
        return userId;
    }

    public String getRating() {
        return rating;
    }
}
