package com.PFE2024.Depanini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PFE2024.Depanini.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
