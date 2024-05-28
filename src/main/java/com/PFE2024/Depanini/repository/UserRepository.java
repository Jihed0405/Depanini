package com.PFE2024.Depanini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PFE2024.Depanini.model.User;
import com.PFE2024.Depanini.model.UserType;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUserType(UserType userType);

    User findByEmail(String email);

}
