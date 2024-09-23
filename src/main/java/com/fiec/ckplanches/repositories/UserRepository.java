package com.fiec.ckplanches.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.fiec.ckplanches.model.user.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    UserDetails findByUsername(String username);
    Optional<User> findByUserEmail(String userEmail);
    @Query("select u from User u where u.username = :userName")
    User findByUserNameReturnUser(String userName);

}
