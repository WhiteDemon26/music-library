package com.example.musiclibrary.service;

import com.example.musiclibrary.model.Review;
import com.example.musiclibrary.repository.ReviewRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.musiclibrary.service.MusicLibraryService.CUSTOM_FORMATTER;

@Data
@Service
public class ReviewService {

    public String reviewAverage;
    private static final java.text.DecimalFormat DecimalFormat = new DecimalFormat("0.0");

    @Autowired
    private ReviewRepository reviewRepository;

    @PostConstruct
    private void postConstruct() {
        reviewAverage = calculateStarsAverage();
    }


    public Review addReview(Review review) {

        review.getStars();

        try {
            if (review.getStars() > 0 && review.getStars() < 6) {
                review.thumbUpOrThumbDown();
                review.setSubmittedOn(LocalDateTime.now());
                review.setSubmittedOnStringFormat(LocalDateTime.now().format(CUSTOM_FORMATTER));
                reviewRepository.save(review);
                calculateStarsAverage();

                String message = "You added a new review (see this response's body) !!";
                System.out.println(message);
                return review;
            } else {
                System.out.println("you must put a star between 1 and 5, thanks");
                throw new IllegalArgumentException("invalid number of stars for your review, moron!!");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while asking to add a new review !!");
            return null;
        }
    }


    public List<Review> readReviews() {
        try {
            List<Review> reviews = reviewRepository.findAll();
            System.out.println("Those are all reviews: " + reviews);
            return reviews;
        } catch (Exception e) {
            System.out.println("An error occurred while asking to see all the reviews !!");
            return null;
        }
    }


    private String calculateStarsAverage() {
        try {
            float average;
            int sum = 0;
            List<Review> reviews = reviewRepository.findAll();
            float howManyReviews = reviews.size();

            for (Review review : reviews) {
                sum = sum + review.getStars();
            }
            average = sum / howManyReviews;
            System.out.println("the average value is: " + average);
            reviewAverage = DecimalFormat.format(average);
            return DecimalFormat.format(average);

        } catch (Exception e) {
            String message = "An error occurred while asking to see the star average of all reviews.";
            System.out.println(message);
            return null;
        }
    }
}
