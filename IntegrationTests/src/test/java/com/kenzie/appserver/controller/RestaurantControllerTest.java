package com.kenzie.appserver.controller;

import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.RestaurantCreateRequest;
import com.kenzie.appserver.service.RestaurantService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.service.model.Restaurant;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Created by Marc Santos **/
@IntegrationTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RestaurantControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    RestaurantService restaurantService;

    private final MockNeat mockNeat = MockNeat.threadLocal();
    private QueryUtility queryUtility;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    public void setup() {
        queryUtility = new QueryUtility(mvc);
    }

    @Test
    public void get_restaurants() throws Exception {
        queryUtility.restaurantControllerClient.getRestaurants()
                .andExpect(status().isOk());
    }

    @Test
    public void can_create_restaurant() throws Exception {
        String restaurantId = "11";
        String restaurantName = mockNeat.strings().valStr();
        String status = mockNeat.strings().valStr();
        String rating = mockNeat.strings().valStr();
        String cuisine = mockNeat.strings().valStr();
        String location = mockNeat.strings().valStr();
        List<String> menu = Arrays.asList(mockNeat.strings().valStr(), mockNeat.strings().valStr(), mockNeat.strings().valStr());

        RestaurantCreateRequest restaurantCreateRequest = new RestaurantCreateRequest();
        restaurantCreateRequest.setRestaurantId(restaurantId);
        restaurantCreateRequest.setRestaurantName(restaurantName);
        restaurantCreateRequest.setStatus(status);
        restaurantCreateRequest.setCuisine(cuisine);
        restaurantCreateRequest.setLocation(location);
        restaurantCreateRequest.setRating(rating);
        restaurantCreateRequest.setMenu(menu);
        queryUtility.restaurantControllerClient.addRestaurant(restaurantCreateRequest)
                .andExpect(status().isOk());
    }

    @Test
    public void can_get_specific_restaurant() throws Exception {
        String restaurantId = "12";
        String restaurantName = mockNeat.strings().valStr();
        String status = mockNeat.strings().valStr();
        String rating = mockNeat.strings().valStr();
        String cuisine = mockNeat.strings().valStr();
        String location = mockNeat.strings().valStr();
        List<String> menu = Arrays.asList(mockNeat.strings().valStr(), mockNeat.strings().valStr(), mockNeat.strings().valStr());

        Restaurant restaurant = new Restaurant(restaurantId, restaurantName, rating, status, cuisine, location, menu);

        restaurantService.addRestaurant(restaurant);

        queryUtility.restaurantControllerClient.getRestaurantByRestaurantId(restaurantId)
                .andExpect(status().isOk());
    }
}