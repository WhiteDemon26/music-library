package com.example.musiclibrary;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.example.musiclibrary.MusicLibrary.CUSTOM_FORMATTER;

@Data
@Service
public class ReviewService {

    public static ArrayList<Review> reviews = new ArrayList<>();
    public static String reviewAverage;
    private static final java.text.DecimalFormat DecimalFormat = new DecimalFormat("0.0");


    @Autowired
    private ReviewRepository reviewRepository;


    public Review addReview(Review review) throws Exception {
        if( review.getStars() > 0 && review.getStars() < 6 ) {
            review.thumbUpOrThumbDown();
            review.setSubmittedOn(LocalDateTime.now());
            review.setSubmittedOnStringFormat(LocalDateTime.now().format(CUSTOM_FORMATTER));

            reviews.add(review);
            reviewRepository.save(review);

            calculateStarsAverage();
            return review;
        } else {
            System.out.println("you must put a star between 1 and 5, thanks");
            throw new IllegalArgumentException("invalid number of stars for your review, moron!!");
        }
    }


    private void calculateStarsAverage() {
        float average;
        int sum = 0;
        float howManyReviews = reviews.size();

        for(Review review : reviews) {
            sum = sum + review.getStars();
        }
        average = sum / howManyReviews;
        System.out.println("the average value is: " + average);
        reviewAverage = DecimalFormat.format(average);
        //return average;
    }
}
