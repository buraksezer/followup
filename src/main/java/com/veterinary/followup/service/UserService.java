package com.veterinary.followup.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.veterinary.followup.web.dto.UserUpdateDto;
import com.veterinary.followup.model.User;
import com.veterinary.followup.web.dto.UserRegistrationDto;

import java.util.List;
import java.util.Optional;

public interface UserService  extends UserDetailsService {
    User findByEmail(String email);

    Optional <User> findById(Long id);

    User save(UserRegistrationDto registration);

    void update(UserUpdateDto userUpdate, User user);

    List <User> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
}
