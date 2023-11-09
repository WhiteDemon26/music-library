package com.example.musiclibrary.service;

import com.example.musiclibrary.model.Review;
import com.example.musiclibrary.model.User;
import com.example.musiclibrary.repository.ReviewRepository;
import com.example.musiclibrary.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.musiclibrary.service.MusicLibraryService.CUSTOM_FORMATTER;

@Data
@Service
@Transactional
public class ReviewService {

    public String reviewAverage;
    private static final java.text.DecimalFormat DecimalFormat = new DecimalFormat("0.0");


    @Autowired
    UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @PostConstruct
    private void postConstruct() {
        reviewAverage = calculateStarsAverage();
    }


    public Review addReview(Review review, Long userId) {

        Optional<User> checkUser = this.userRepository.findById(userId);
        if(!checkUser.isPresent()) {
            System.out.println("\n A user with the id" + userId + " is not present! \n");
            return null;
        }
        review.setUser(checkUser.get());
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
    }


    public List<Review> readReviews() {
        return reviewRepository.findAll();
    }


    private String calculateStarsAverage() {
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
    }
}
