package com.kenzie.appserver.controller;


import com.kenzie.appserver.controller.model.ReviewCreateRequest;
import com.kenzie.appserver.controller.model.ReviewResponse;
import com.kenzie.appserver.controller.model.ReviewUpdateRequest;
import com.kenzie.appserver.service.ReviewRecordNotFoundException;
import com.kenzie.appserver.service.ReviewService;
import com.kenzie.appserver.service.model.Review;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private ReviewService reviewService;

    ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getReview() {
        List<Review> reviews = reviewService.findAll();

        if(reviews == null || reviews.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ReviewResponse> responses = reviews.stream().map(review -> reviewToResponse(review)).collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<ReviewResponse> getReviewByRestaurantId(@PathVariable("restaurantId") String restaurantId) {
        Review review = reviewService.findByRestaurantId(restaurantId);

        if (review == null) {
            return ResponseEntity.notFound().build();
        }

        ReviewResponse response = reviewToResponse(review);

        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<ReviewResponse> addNewReview(@RequestBody ReviewCreateRequest reviewCreateRequest) {
        Review userReview = new Review(reviewCreateRequest.getRestaurantId(), reviewCreateRequest.getUserId(),
                reviewCreateRequest.getRating(), reviewCreateRequest.getReview());

        try {
            reviewService.addReview(userReview);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        ReviewResponse response = reviewToResponse(userReview);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<ReviewResponse> updateReview(@RequestBody ReviewUpdateRequest reviewUpdateRequest){
        if (reviewUpdateRequest.getRestaurantId() == null || reviewUpdateRequest.getUserId() == null) {
            throw new ReviewRecordNotFoundException();
        }
        Review updatedReview = new Review(reviewUpdateRequest.getRestaurantId(), reviewUpdateRequest.getUserId(),
                reviewUpdateRequest.getRating(), reviewUpdateRequest.getReview());
        reviewService.updateReview(updatedReview);

        ReviewResponse reviewResponse = reviewToResponse(updatedReview);

        return ResponseEntity.ok().body(reviewResponse);
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<ReviewResponse> deleteReview(@PathVariable("restaurantId") String restaurantId) {
        Review review = reviewService.findByRestaurantId(restaurantId);
        reviewService.deleteReview(restaurantId);
        return ResponseEntity.ok().body(reviewToResponse(review));
    }

    private ReviewResponse reviewToResponse (Review review) {
        ReviewResponse reviewResponse = new ReviewResponse();
        reviewResponse.setRestaurantId(review.getRestaurantId());
        reviewResponse.setUserId(review.getUserId());
        reviewResponse.setRating(review.getRating());
        reviewResponse.setReview(review.getReview());

        return reviewResponse;
    }

}
