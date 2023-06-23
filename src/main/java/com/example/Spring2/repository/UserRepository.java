package com.example.Spring2.repository;

import com.example.Spring2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailIgnoreCase(String email);

    @Query("select u from User u where u.age>=?1 and u.age<=?2")
    List<User> findUsersInAgeRange(int min, int max);

}

