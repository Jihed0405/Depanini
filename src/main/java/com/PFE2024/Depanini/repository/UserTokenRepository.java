package com.PFE2024.Depanini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PFE2024.Depanini.model.User;

public interface UserTokenRepository extends JpaRepository<User, Long> {
}
