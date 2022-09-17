package com.kenzie.appserver.controller;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.ReviewCreateRequest;
import com.kenzie.appserver.controller.model.ReviewUpdateRequest;
import com.kenzie.appserver.service.ReviewService;
import com.kenzie.appserver.service.model.Review;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReviewControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ReviewService reviewService;

    private final ObjectMapper mapper = new ObjectMapper();
    private final MockNeat mockNeat = MockNeat.threadLocal();
    private QueryUtility queryUtility;

    @BeforeAll
    public void setup() { queryUtility = new QueryUtility(mvc); }

    @Test
    public void can_create_review() throws Exception {
        String restaurantId = "3";
        String userId = mockNeat.strings().valStr();
        String review = mockNeat.strings().valStr();
        String rating = "4";

        ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest();
        reviewCreateRequest.setRestaurantId(restaurantId);
        reviewCreateRequest.setUserId(userId);
        reviewCreateRequest.setReview(review);
        reviewCreateRequest.setRating(rating);
        queryUtility.reviewControllerClient.addReview(reviewCreateRequest)
                .andExpect(status().isOk());
    }

    @Test
    public void can_delete_review() throws Exception {
        String restaurantId = "4";
        String userId = mockNeat.strings().valStr();
        String review = mockNeat.strings().valStr();
        String rating = "5";

        Review userReview = new Review(restaurantId,userId, review, rating);
        Review persistedUserReview = reviewService.addReview(userReview);

        queryUtility.reviewControllerClient.deleteReview(persistedUserReview.getRestaurantId())
                .andExpect(status().isNoContent());
    }

    @Test
    public void can_update_review() throws Exception {
        String restaurantId = "1";
        String userId = mockNeat.strings().valStr();
        String review = mockNeat.strings().valStr();
        String rating = "5";

        Review userReview = new Review(restaurantId,userId, review, rating);
        Review persistedUserReview = reviewService.addReview(userReview);

        String newReview = mockNeat.strings().valStr();
        String newRating = "2";

        ReviewUpdateRequest reviewUpdateRequest = new ReviewUpdateRequest();
        reviewUpdateRequest.setRestaurantId(restaurantId);
        reviewUpdateRequest.setUserId(userId);
        reviewUpdateRequest.setReview(newReview);
        reviewUpdateRequest.setRating(newRating);

        queryUtility.reviewControllerClient.updateReview(reviewUpdateRequest)
                .andExpect(status().isOk());
    }

    @Test
    public void can_get_restaurant_reviews() throws Exception {
        String restaurantId = "1";
        String userId = mockNeat.strings().valStr();
        String review = mockNeat.strings().valStr();
        String rating = "5";

        String restaurantId2 = "1";
        String userId2 = mockNeat.strings().valStr();
        String review2 = mockNeat.strings().valStr();
        String rating2 = "4";

        Review userReview1 = new Review(restaurantId, userId, review, rating);
        Review userReview2 = new Review(restaurantId2, userId2, review2, rating2);

        reviewService.addReview(userReview1);
        reviewService.addReview(userReview2);

        queryUtility.reviewControllerClient.getReviewsByRestaurantId(restaurantId)
                .andExpect(status().isOk());
    }

    @Test
    public void can_get_restaurants() throws Exception {
        queryUtility.reviewControllerClient.getReviews()
                .andExpect(status().isOk());
    }
}
