package com.fiec.ckplanches.repositories;

import com.fiec.ckplanches.model.user.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    UserDetails findByUsername(String username);
    Optional<User>findByname(String username);
    Optional<User> findByEmail(String userEmail);
}
