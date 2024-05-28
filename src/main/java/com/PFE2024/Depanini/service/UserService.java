package com.PFE2024.Depanini.service;

import java.util.List;

import com.PFE2024.Depanini.model.Category;
import com.PFE2024.Depanini.model.ServiceProvider;
import com.PFE2024.Depanini.model.User;
import com.PFE2024.Depanini.request.UpdateUserRequest;

import jakarta.validation.Valid;

public interface UserService {
    User createUser(@Valid User user);

    User loginUser(String email, String password);

    void logoutUser();

    User updateUser(Long userId, @Valid UpdateUserRequest updateUserRequest);

    User getUserById(Long userId);

    List<User> getAllUsers();

    public void deleteUser(Long userId);
}
