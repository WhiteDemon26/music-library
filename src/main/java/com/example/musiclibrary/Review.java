package com.example.musiclibrary;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "com.example.demo.raz.music_library.Review")
@Table(name = "review")
@Builder
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(length = 100)
    private String text;

    @Column(nullable = false)
    private int stars;

    @Column(nullable = false, name = "positive_review")
    private boolean positiveReview;

    @Column(nullable = false, name = "submitted_on")
    private LocalDateTime submittedOn;

    @Column(nullable = false, name = "submitted_on_string_format")
    private String submittedOnStringFormat;


    public void thumbUpOrThumbDown() {
        if(this.stars < 3) {
            this.positiveReview = false;
        }
        if(this.stars <= 5 && this.stars >3) {
            this.positiveReview = true;
        }
    }
}