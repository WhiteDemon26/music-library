package com.example.musiclibrary.controller;

import com.example.musiclibrary.Review;
import com.example.musiclibrary.ReviewService;
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
        String message = "you asked to see the reviews (see this response's body) !!";
        System.out.println(message);
        return new ResponseEntity<>(reviewService.readReviews(), HttpStatus.OK);
    }


    @GetMapping("/show_reviews_average")
    public ResponseEntity<String> calculateAverageReviews() {
        String message = "you asked to see the star average of all reviews!!";
        System.out.println(message);
        return new ResponseEntity<>(reviewService.getReviewAverage(), HttpStatus.OK);
    }


    @PostMapping("/add_review")
    public ResponseEntity<Review> addReview(@RequestBody Review review) throws Exception {
        Review submittedReview = reviewService.addReview(review);
        String message = "you added a new review (see this response's body) !!";
        System.out.println(message);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }
}