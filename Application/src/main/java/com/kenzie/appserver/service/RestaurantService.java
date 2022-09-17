package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.RestaurantResponse;
import com.kenzie.appserver.repositories.RestaurantRepository;
import com.kenzie.appserver.repositories.model.RestaurantRecord;
import com.kenzie.appserver.service.model.Restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Created by Eric Tun **/
@Service
public class RestaurantService {
    private RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant findById(String id) {
        if ((Objects.equals(id, "")) || id == null) {
            throw new RestaurantRecordNotFoundException();
        }

        return restaurantRepository
                .findById(id)
                .map(restaurant -> new Restaurant(restaurant.getRestaurantId(),
                        restaurant.getRestaurantName(),
                        restaurant.getRating(),
                        restaurant.getStatus(),
                        restaurant.getCuisine(),
                        restaurant.getLocation(),
                        restaurant.getMenu()))
                .orElse(null);
    }

    public List<Restaurant> findAll() {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurantRepository
                .findAll()
                .forEach(restaurant -> restaurants
                        .add(new Restaurant(
                                restaurant.getRestaurantId(),
                                restaurant.getRestaurantName(),
                                restaurant.getRating(),
                                restaurant.getStatus(),
                                restaurant.getCuisine(),
                                restaurant.getLocation(),
                                restaurant.getMenu())));

        return restaurants;
    }

    public Restaurant addRestaurant(Restaurant restaurant) {
        RestaurantRecord restaurantRecord = toRestaurantRecord(restaurant);
        restaurantRepository.save(restaurantRecord);
        return restaurant;
    }

    public RestaurantRecord toRestaurantRecord(Restaurant restaurant) {
        RestaurantRecord restaurantRecord = new RestaurantRecord();
        restaurantRecord.setRestaurantId(restaurant.getRestaurantId());
        restaurantRecord.setRestaurantName(restaurant.getRestaurantName());
        restaurantRecord.setRating(restaurant.getRating());
        restaurantRecord.setCuisine(restaurant.getCuisine());
        restaurantRecord.setStatus(restaurant.getStatus());
        restaurantRecord.setLocation(restaurant.getLocation());
        restaurantRecord.setMenu(restaurant.getMenu());
        return restaurantRecord;
    }
}
