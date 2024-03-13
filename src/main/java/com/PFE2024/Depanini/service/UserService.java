package com.PFE2024.Depanini.service;

import com.PFE2024.Depanini.model.ServiceProvider;
import com.PFE2024.Depanini.model.User;

public interface UserService {
    User createUser(User user);

    User loginUser(String email, String password);

    void logoutUser();

    User updateUser(Long userId, User user, ServiceProvider serviceProvider);
}
