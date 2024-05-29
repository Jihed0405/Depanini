package com.PFE2024.Depanini.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PFE2024.Depanini.model.User;
import com.PFE2024.Depanini.model.UserType;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUserType(UserType userType);

    Optional<User> findByUsername(String username);

    User findByEmail(String email);

}
