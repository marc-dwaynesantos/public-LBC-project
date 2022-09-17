package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.RestaurantCreateRequest;
import com.kenzie.appserver.controller.model.RestaurantResponse;
import com.kenzie.appserver.service.RestaurantService;

import com.kenzie.appserver.service.model.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private RestaurantService restaurantService;

    RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getRestaurant() {
        List<Restaurant> restaurants= restaurantService.findAll();

        if(restaurants == null || restaurants.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<RestaurantResponse> responses = restaurants.stream().map(this::restaurantToResponse).collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponse> getRestaurantByRestaurantId(@PathVariable("restaurantId") String restaurantId) {
        Restaurant restaurant = restaurantService.findById(restaurantId);

        if (restaurant == null) {
            return ResponseEntity.notFound().build();
        }

        RestaurantResponse response = restaurantToResponse(restaurant);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<RestaurantResponse> addNewRestaurant(@RequestBody RestaurantCreateRequest restaurantCreateRequest) {
        Restaurant restaurant = new Restaurant(restaurantCreateRequest.getRestaurantId(),
                restaurantCreateRequest.getRestaurantName(), restaurantCreateRequest.getRating(),
                restaurantCreateRequest.getStatus(), restaurantCreateRequest.getCuisine(),
                restaurantCreateRequest.getLocation(), restaurantCreateRequest.getMenu());

        try {
            restaurantService.addRestaurant(restaurant);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        RestaurantResponse response = restaurantToResponse(restaurant);

        return ResponseEntity.ok().body(response);
    }

    private RestaurantResponse restaurantToResponse (Restaurant restaurant) {
        RestaurantResponse restaurantResponse = new RestaurantResponse();
        restaurantResponse.setRestaurantId(restaurant.getRestaurantId());
        restaurantResponse.setRestaurantName(restaurant.getRestaurantName());
        restaurantResponse.setRating(restaurant.getRating());
        restaurantResponse.setStatus(restaurant.getStatus());
        restaurantResponse.setLocation(restaurant.getLocation());
        restaurantResponse.setCuisine(restaurant.getCuisine());
        restaurantResponse.setMenu(restaurant.getMenu());
        return restaurantResponse;
    }
}
