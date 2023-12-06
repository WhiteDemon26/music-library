package com.example.musiclibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity(name = "com.example.demo.raz.music_library.User")
@Table(name = "user")
@Builder
//@Data
@Getter
@Setter
//@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(length = 100, nullable = false, name = "first_name")
    private String firstName;

    @Column(length = 100, nullable = false, name = "last_name")
    private String lastName;

    @Column(length = 100, name = "user_name", unique = true)
    private String userName;

    @Column(length = 100, unique = true)
    private String password;

    @Column(length = 100)
    private Integer age;

    @Column(nullable = false, updatable = false)
    private LocalDate birthdate;

    @Column(length = 100)
    private String address;

    @Column(length = 100, name = "old_password")
    private String oldPassword;

    @Column(nullable = false)
    private LocalDateTime registration;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Review> submittedReviews;

    //@JsonIgnore
    @JsonIgnore
    @ManyToMany(mappedBy = "downloaders", fetch = FetchType.EAGER)
    private Set<Song> likedSongs;
}