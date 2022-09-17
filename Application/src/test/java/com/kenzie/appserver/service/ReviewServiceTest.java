package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.ReviewRepository;
import com.kenzie.appserver.repositories.model.ReviewRecord;
import com.kenzie.appserver.service.model.Review;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReviewServiceTest {
    private ReviewRepository reviewRepository;
    private ReviewService reviewService;

    @BeforeEach
    void setup() {
        reviewRepository = mock(ReviewRepository.class);
        reviewService = new ReviewService(reviewRepository);
    }

    @Test
    //rename to whatever actual method is called
    void canCreateNewReview() {
        // GIVEN
        String restaurantId = "4";
        String userId = "5";
        String review = "very cool place, nice food";
        String rating = "3";

        Review userReview = new Review(restaurantId, userId, rating, review);
        ArgumentCaptor<ReviewRecord> reviewRecordCaptor = ArgumentCaptor.forClass(ReviewRecord.class);

        when(reviewRepository.save(any(ReviewRecord.class))).then(i -> i.getArgumentAt(0, ReviewRecord.class));

        // WHEN
        //change name of method to whatever it will be
        Review returnedReview = reviewService.addReview(userReview);

        // THEN
        verify(reviewRepository).save(reviewRecordCaptor.capture());
        ReviewRecord reviewRecord = reviewRecordCaptor.getValue();

        Assertions.assertNotNull(reviewRecord, "The review is saved");
        Assertions.assertEquals(reviewRecord.getRestaurantId(), userReview.getRestaurantId(), "The restaurant id matches");
        Assertions.assertEquals(reviewRecord.getUserId(), userReview.getUserId(), "The user id matches");
        Assertions.assertEquals(reviewRecord.getReview(), userReview.getReview(), "The review matches");
        Assertions.assertEquals(reviewRecord.getRating(), userReview.getRating(), "The rating matches");

        Assertions.assertNotNull(returnedReview, "The review is saved");
        Assertions.assertEquals(returnedReview.getRestaurantId(), userReview.getRestaurantId(), "The restaurant id matches");
        Assertions.assertEquals(returnedReview.getUserId(), userReview.getUserId(), "The user id matches");
        Assertions.assertEquals(returnedReview.getReview(), userReview.getReview(), "The review matches");
        Assertions.assertEquals(returnedReview.getRating(), userReview.getRating(), "The rating matches");
    }

    @Test
    void deleteReview_validReview_reviewDeleted() {
        //GIVEN
        String restaurantId = "1";
        String userId = "123";
        String rating = "3";
        String review = "This restaurant is very decorative.";

        ReviewRecord record = new ReviewRecord();
        record.setRestaurantId(restaurantId);
        record.setUserId(userId);
        record.setRating(rating);
        record.setReview(review);

        //WHEN
        reviewService.deleteReview(restaurantId);

        //THEN
        verify(reviewRepository).deleteById(restaurantId);
    }

    @Test
    void deleteReview_nullReview_throwsException() {
        //GIVEN

        //WHEN //THEN
        assertThrows(ReviewRecordNotFoundException.class, () -> reviewService.deleteReview(null));
    }

    @Test
    void updateReview_validInfo_reviewUpdated() {
        // GIVEN
        String restaurantId1 = "1";
        String userId1 = "123";
        String rating1 = "5";
        String review1 = "the food is delicious";

        Review userReview = new Review(restaurantId1, userId1, rating1, review1);
        ReviewRecord record1 = new ReviewRecord();
        record1.setRestaurantId(restaurantId1);
        record1.setUserId(userId1);
        record1.setRating(rating1);
        record1.setReview(review1);

        ArgumentCaptor<ReviewRecord> reviewRecordCaptor = ArgumentCaptor.forClass(ReviewRecord.class);
        when(reviewRepository.save(any())).thenReturn(record1);

        // WHEN
        Review returnedReview = reviewService.updateReview(userReview);

        // THEN
        verify(reviewRepository).save(reviewRecordCaptor.capture());
        ReviewRecord reviewRecord = reviewRecordCaptor.getValue();

        Assertions.assertNotNull(reviewRecord, "The review is updated");
        Assertions.assertEquals(reviewRecord.getRestaurantId(), returnedReview.getRestaurantId(), "The restaurant id matches");
        Assertions.assertEquals(reviewRecord.getUserId(), returnedReview.getUserId(), "The user id matches");
        Assertions.assertEquals(reviewRecord.getReview(), returnedReview.getReview(), "The review matches");
        Assertions.assertEquals(reviewRecord.getRating(), returnedReview.getRating(), "The rating matches");
    }

    @Test
    void updateReview_nullReview_throwsException() {
        //GIVEN

        //WHEN //THEN
        assertThrows(ReviewRecordNotFoundException.class, () -> reviewService.updateReview(null));
    }

    @Test
    void findByRestaurantId_validRestaurantId_reviewsReturned() {
        //GIVEN
        String restaurantId = "1";
        Review userReview1 = new Review(restaurantId, "123", "4", "Nice Place");

        ReviewRecord record1 = new ReviewRecord();
        record1.setRestaurantId(userReview1.getRestaurantId());
        record1.setUserId(userReview1.getUserId());
        record1.setRating(userReview1.getRating());
        record1.setReview(userReview1.getReview());

        when(reviewRepository.findById(restaurantId)).thenReturn(Optional.of(record1));

        //THEN
        assertNotNull(reviewService.findByRestaurantId(restaurantId), "Not Null");
    }

    @Test
    void findByRestaurantId_emptyRestaurantId_throwsException() {
        //GIVEN

        //WHEN //THEN
        assertThrows(ReviewRecordNotFoundException.class, () -> reviewService.findByRestaurantId(""));
    }

    @Test
    void findByRestaurantId_nullRestaurantId_throwsException() {
        //GIVEN

        //WHEN //THEN
        assertThrows(ReviewRecordNotFoundException.class, () -> reviewService.findByRestaurantId(null));
    }

    @Test
    void findAllReviews() {
        //GIVEN
        Review userReview1 = new Review("2", "123", "4", "Nice Place");
        Review userReview2 = new Review("4", "456", "5", "Romantic Place");
        Review userReview3 = new Review("3", "456", "5", "Romantic Place");

        ReviewRecord record1 = new ReviewRecord();
        record1.setRestaurantId(userReview1.getRestaurantId());
        record1.setUserId(userReview1.getUserId());
        record1.setRating(userReview1.getRating());
        record1.setReview(userReview1.getReview());

        ReviewRecord record2 = new ReviewRecord();
        record2.setRestaurantId(userReview2.getRestaurantId());
        record2.setUserId(userReview2.getUserId());
        record2.setRating(userReview2.getRating());
        record2.setReview(userReview2.getReview());

        ReviewRecord record3 = new ReviewRecord();
        record3.setRestaurantId(userReview3.getRestaurantId());
        record3.setUserId(userReview3.getUserId());
        record3.setRating(userReview3.getRating());
        record3.setReview(userReview3.getReview());

        List<ReviewRecord> recordList = new ArrayList<>();
        recordList.add(record1);
        recordList.add(record2);
        recordList.add(record3);

        when(reviewRepository.findAll()).thenReturn(recordList);

        //WHEN
        List<Review> reviewListReturned = reviewService.findAll();

        //THEN
        assertNotNull(reviewListReturned, "List is null");
        assertEquals(userReview1.getRestaurantId(), reviewListReturned.get(0).getRestaurantId(), "RestaurantId does not match");
        assertEquals(userReview1.getUserId(), reviewListReturned.get(0).getUserId(), "UserId does not match");
        assertEquals(userReview1.getRating(), reviewListReturned.get(0).getRating(), "Rating does not match");
        assertEquals(userReview1.getReview(), reviewListReturned.get(0).getReview(), "Review does not match");

        assertEquals(userReview2.getRestaurantId(), reviewListReturned.get(1).getRestaurantId(), "RestaurantId does not match");
        assertEquals(userReview2.getUserId(), reviewListReturned.get(1).getUserId(), "UserId does not match");
        assertEquals(userReview2.getRating(), reviewListReturned.get(1).getRating(), "Rating does not match");
        assertEquals(userReview2.getReview(), reviewListReturned.get(1).getReview(), "Review does not match");

        assertEquals(userReview3.getRestaurantId(), reviewListReturned.get(2).getRestaurantId(), "RestaurantId does not match");
        assertEquals(userReview3.getUserId(), reviewListReturned.get(2).getUserId(), "UserId does not match");
        assertEquals(userReview3.getRating(), reviewListReturned.get(2).getRating(), "Rating does not match");
        assertEquals(userReview3.getReview(), reviewListReturned.get(2).getReview(), "Review does not match");
    }
}
