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
        return new ResponseEntity<>(reviewService.readReviews(), HttpStatus.OK);
    }


    @GetMapping("/show_reviews_average")
    public ResponseEntity<String> calculateAverageReviews() {
        return new ResponseEntity<>(reviewService.getReviewAverage(), HttpStatus.OK);
    }


    @PostMapping("/add_review")
    public ResponseEntity<Review> addReview(@RequestBody Review review) throws Exception {
        Review submittedReview = reviewService.addReview(review);
        return new ResponseEntity<>(submittedReview, HttpStatus.OK);
    }
}