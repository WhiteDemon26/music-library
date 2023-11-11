package com.example.musiclibrary.repository;

import com.example.musiclibrary.model.Song;
import com.example.musiclibrary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUserName(String userName);
    User findByPassword(String password);

}
