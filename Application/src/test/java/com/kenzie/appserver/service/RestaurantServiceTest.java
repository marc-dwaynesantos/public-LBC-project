package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.RestaurantRepository;
import com.kenzie.appserver.repositories.model.RestaurantRecord;
import com.kenzie.appserver.service.model.Restaurant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.*;

/** Created by Marc Santos **/
public class RestaurantServiceTest {
    private RestaurantRepository restaurantRepository;
    private RestaurantService restaurantService;

    @BeforeEach
    void setup() {
        restaurantRepository = mock(RestaurantRepository.class);
        restaurantService = new RestaurantService(restaurantRepository);
    }
    /** ------------------------------------------------------------------------
     *  restaurantService.findById
     *  ------------------------------------------------------------------------ **/

    @Test
    void findById_validId_ReturnsRestaurant() {
        // GIVEN
        String id = randomUUID().toString();
        String name = "Restaurant Name";
        String rating = "3";
        String status = "active";
        String cuisine = "cuisine";
        String location = "location";
        List<String> menu = new ArrayList<>();
        menu.add("fries");
        menu.add("shake");

        RestaurantRecord record = new RestaurantRecord();
        record.setRestaurantId(id);
        record.setRestaurantName(name);
        record.setRating(rating);
        record.setStatus(status);
        record.setCuisine(cuisine);
        record.setLocation(location);
        record.setMenu(menu);

        when(restaurantRepository.findById(id)).thenReturn(Optional.of(record));

        // WHEN
        Restaurant restaurant = restaurantService.findById(id);

        // THEN
        Assertions.assertNotNull(restaurant, "The object is returned");
        Assertions.assertEquals(record.getRestaurantId(), restaurant.getRestaurantId(), "Expected record and restaurant ids to match");
        Assertions.assertEquals(record.getRestaurantName(), restaurant.getRestaurantName(), "Expected the record-restaurant and restaurant names to match");
    }

    /** ------------------------------------------------------------------------
     *  restaurantService.findAll
     *  ------------------------------------------------------------------------ **/

    @Test
    void find_all() {
        // GIVEN
        String id = randomUUID().toString();
        String name = "Restaurant Name";
        String rating = "3";
        String status = "active";
        String cuisine = "cuisine";
        String location = "location";
        List<String> menu = new ArrayList<>();
        menu.add("fries");
        menu.add("shake");

        String id2 = randomUUID().toString();
        String name2 = "Restaurant Name2";
        String rating2 = "4";
        String status2 = "active";
        String cuisine2 = "cuisine2";
        String location2 = "location2";
        List<String> menu2 = new ArrayList<>();
        menu.add("burger");
        menu.add("hot-dog");

        RestaurantRecord record1 = new RestaurantRecord();
        record1.setRestaurantId(id);
        record1.setRestaurantName(name);
        record1.setRating(rating);
        record1.setStatus(status);
        record1.setCuisine(cuisine);
        record1.setLocation(location);
        record1.setMenu(menu);

        RestaurantRecord record2 = new RestaurantRecord();
        record2.setRestaurantId(id2);
        record2.setRestaurantName(name2);
        record2.setRating(rating2);
        record2.setStatus(status2);
        record2.setCuisine(cuisine2);
        record2.setLocation(location2);
        record2.setMenu(menu2);

        List<RestaurantRecord> restaurants = new ArrayList<>();
        restaurants.add(record1);
        restaurants.add(record2);

        when(restaurantRepository.findAll()).thenReturn(restaurants);

        // WHEN
        List<Restaurant> result = restaurantService.findAll();

        // THEN
        Assertions.assertNotNull(result, "Expected list to return all restaurants");
        verify(restaurantRepository).findAll();
    }

    @Test
    void canCreateNewRestaurant() {
        String id = randomUUID().toString();
        String name = "Restaurant Name";
        String rating = "3";
        String status = "active";
        String cuisine = "cuisine";
        String location = "location";
        List<String> menu = new ArrayList<>();
        menu.add("fries");
        menu.add("shake");

        Restaurant restaurant = new Restaurant(id, name, rating, status, cuisine, location, menu);
        ArgumentCaptor<RestaurantRecord> restaurantRecordCaptor = ArgumentCaptor.forClass(RestaurantRecord.class);

        Restaurant returnedRestaurant = restaurantService.addRestaurant(restaurant);

        verify(restaurantRepository).save(restaurantRecordCaptor.capture());
        RestaurantRecord restaurantRecord = restaurantRecordCaptor.getValue();

        Assertions.assertNotNull(restaurantRecord, "The restaurant is saved");
        Assertions.assertEquals(restaurantRecord.getRestaurantId(), restaurant.getRestaurantId(), "The restaurant id matches");
        Assertions.assertEquals(restaurantRecord.getRestaurantName(), restaurant.getRestaurantName(), "The name matches");
        Assertions.assertEquals(restaurantRecord.getRating(), restaurant.getRating(), "The rating matches");
        Assertions.assertEquals(restaurantRecord.getCuisine(), restaurant.getCuisine(), "The cuisine matches");
        Assertions.assertEquals(restaurantRecord.getLocation(), restaurant.getLocation(), "The location matches");
        Assertions.assertEquals(restaurantRecord.getStatus(), restaurant.getStatus(), "The status matches");
        Assertions.assertEquals(restaurantRecord.getMenu(), restaurant.getMenu(), "The menu matches");

        Assertions.assertNotNull(returnedRestaurant, "The restaurant is saved");
        Assertions.assertEquals(returnedRestaurant.getRestaurantId(), restaurant.getRestaurantId(), "The restaurant id matches");
        Assertions.assertEquals(returnedRestaurant.getRestaurantName(), restaurant.getRestaurantName(), "The name matches");
        Assertions.assertEquals(returnedRestaurant.getRating(), restaurant.getRating(), "The rating matches");
        Assertions.assertEquals(returnedRestaurant.getCuisine(), restaurant.getCuisine(), "The cuisine matches");
        Assertions.assertEquals(returnedRestaurant.getLocation(), restaurant.getLocation(), "The location matches");
        Assertions.assertEquals(returnedRestaurant.getStatus(), restaurant.getStatus(), "The status matches");
        Assertions.assertEquals(returnedRestaurant.getMenu(), restaurant.getMenu(), "The menu matches");
    }

    @Test
    void findById_emptyRestaurantId_throwsException() {
        assertThrows(RestaurantRecordNotFoundException.class, () -> restaurantService.findById(null));
    }
}
