package com.example.musiclibrary.controller;

import com.example.musiclibrary.model.Review;
import com.example.musiclibrary.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
public class ReviewsController {

    @Autowired
    ReviewService reviewService;


    @GetMapping("/show_reviews")
    public ResponseEntity<List<Review>> showReviews() {
        try {
            return new ResponseEntity<>(reviewService.readReviews(), HttpStatus.OK);
        } catch (Exception e) {
            String message = "An error occurred while asking to see all the reviews !! \n Exception: " + e.getClass().getName() + ". Message: " + e.getMessage() + ". Cause: " + e.getCause();
            System.out.println(message);
            return null;
        }
    }


    @GetMapping("/show_reviews_average")
    public ResponseEntity<String> calculateAverageReviews() {
        try {
            return new ResponseEntity<>(reviewService.getReviewAverage(), HttpStatus.OK);
        } catch (Exception e) {
            String message = "An error occurred while asking to see the star average of all reviews. \n Exception: " + e.getClass().getName() + ". Message: " + e.getMessage() + ". Cause: " + e.getCause();
            System.out.println(message);
            return null;
        }
    }


    @PostMapping("/add_review")
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        try {
            Review submittedReview = reviewService.addReview(review);
            return new ResponseEntity<>(submittedReview, HttpStatus.OK);
        } catch (Exception e) {
            String message = "An error occurred while asking to add a new review !! \n Exception: " + e.getClass().getName() + ". Message: " + e.getMessage() + ". Cause: " + e.getCause();
            System.out.println(message);
            return null;
        }
    }
}