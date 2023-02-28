package com.example.second.repositories;

import com.example.second.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.username = :username") // JPQL syntax
//    @Query(value = "select * from users where username = :username", nativeQuery = true) // native SQL
    Optional<User> findUserByUsername(String username);
}
