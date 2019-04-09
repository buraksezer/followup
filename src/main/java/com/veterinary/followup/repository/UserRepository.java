package com.veterinary.followup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.veterinary.followup.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    User findByEmail(String email);

    List<User> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
}
