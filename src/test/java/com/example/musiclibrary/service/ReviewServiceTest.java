package com.example.musiclibrary.service;

import com.example.musiclibrary.model.Review;
import com.example.musiclibrary.model.User;
import com.example.musiclibrary.repository.ReviewRepository;
import com.example.musiclibrary.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    ReviewService reviewService;


    @Test
    void testAddReview() {

        User user = User.builder()
                        .id(2L)
                        .userName("Razvan")
                        .age(20)
                        .build();

        Review review = Review.builder()
                                .id(1L)
                                .stars(5)
                                .text("Nice app!")
                                .build();

        Review review2 = Review.builder()
                                .id(2L)
                                .stars(3)
                                .text("Nice app!")
                                .build();
        List<Review> reviews = List.of(review, review2);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Review myFakeReview = reviewService.addReview(review, 1L);
        assertNull(myFakeReview);

        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        when(reviewRepository.findAll()).thenReturn(reviews);

        Review myReview = reviewService.addReview(review, 2L);

        verify(reviewRepository).save(myReview);
        assertTrue(myReview.isPositiveReview());
        assertNotNull(myReview.getSubmittedOnStringFormat());

        String average = reviewService.reviewAverage;
        assertEquals(average, "4.0");

    }


    @Test
    void testExceptionThrownForWrongStarsNumber() {

        Review review = Review.builder()
                .id(1L)
                .stars(52)
                .text("Nice app!")
                .build();

        User user = User.builder()
                .id(2L)
                .userName("Razvan")
                .age(20)
                .build();

        when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        Exception exception = assertThrows(
                                IllegalArgumentException.class,
                                () -> {reviewService.addReview(review, 2L);},
                        "IllegalArgumentException thrown if the stars are not 1, 2, 3, 4, 5"
                                );

        String expectedMessage = "invalid number of stars ";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    void testReadReviews() {

        Review review = Review.builder()
                .id(1L)
                .stars(5)
                .text("Nice app!")
                .positiveReview(true)
                .build();

        Review review2 = Review.builder()
                .id(2L)
                .stars(7)
                .text("Nice app!")
                .positiveReview(true)
                .build();
        List<Review> fakeReview = new ArrayList<>(List.of(review, review2));
        verify(reviewRepository).findAll();
        assertEquals(fakeReview.size(), 2);
    }
}
