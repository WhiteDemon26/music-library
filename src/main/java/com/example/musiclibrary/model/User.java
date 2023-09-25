package com.example.musiclibrary.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "com.example.demo.raz.music_library.User")
@Table(name = "user")
@Builder
@Data
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

    @Column(length = 100, name = "user_name")
    private String userName;

    @Column(length = 100)
    private String password;

    @Column(length = 100)
    private Integer age;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Column(length = 100)
    private String address;

    @Column(length = 100, name = "old_password")
    private String oldPassword;

    @Column(nullable = false)
    private LocalDateTime registration;
}