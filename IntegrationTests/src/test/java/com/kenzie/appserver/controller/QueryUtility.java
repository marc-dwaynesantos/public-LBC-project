package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.controller.model.RestaurantCreateRequest;
import com.kenzie.appserver.controller.model.ReviewCreateRequest;
import com.kenzie.appserver.controller.model.ReviewUpdateRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/** Created by Marc Santos **/

public class QueryUtility {
    public RestaurantControllerClient restaurantControllerClient;
    public ReviewControllerClient reviewControllerClient;

    private final MockMvc mvc;
    private final ObjectMapper mapper = new ObjectMapper();

    public QueryUtility(MockMvc mvc) {
        this.mvc = mvc;
        this.restaurantControllerClient = new RestaurantControllerClient();
        this.reviewControllerClient = new ReviewControllerClient();
    }

    public class RestaurantControllerClient {
        public ResultActions getRestaurants() throws Exception {
            return mvc.perform(get("/restaurants")
                    .accept(MediaType.APPLICATION_JSON));
        }

        public ResultActions getRestaurantByRestaurantId(String restaurantId) throws Exception {
            return mvc.perform(get("/restaurants/{restaurantId}", restaurantId)
                    .accept(MediaType.APPLICATION_JSON));
        }

        public ResultActions addRestaurant(RestaurantCreateRequest createRequest) throws Exception {
            return mvc.perform(post("/restaurants/")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(createRequest)));
        }
    }

    public class ReviewControllerClient {
        public ResultActions updateReview(ReviewUpdateRequest updateRequest) throws Exception {
            return mvc.perform(put("/reviews/{restaurantId}", updateRequest.getRestaurantId())
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(updateRequest)));
        }

        public ResultActions addReview(ReviewCreateRequest createRequest) throws Exception {
            return mvc.perform(post("/reviews/")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(createRequest)));
        }

        public ResultActions deleteReview(String restaurantId) throws Exception {
            return mvc.perform(delete("/reviews/{restaurantId}", restaurantId)
                    .accept(MediaType.APPLICATION_JSON));
        }

        public ResultActions getReviews() throws Exception {
            return mvc.perform(get("/reviews")
                    .accept(MediaType.APPLICATION_JSON));
        }

        public ResultActions getReviewsByRestaurantId(String restaurantId) throws Exception {
            return mvc.perform(get("/reviews/{restaurantId}", restaurantId)
                    .accept(MediaType.APPLICATION_JSON));
        }
    }
}
