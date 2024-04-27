package com.PFE2024.Depanini.request;

import com.PFE2024.Depanini.model.ServiceProvider;
import com.PFE2024.Depanini.model.User;

public class UpdateUserRequest {
    private User updatedUser;
    private ServiceProvider updatedServiceProvider;

    public User getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(User updatedUser) {
        this.updatedUser = updatedUser;
    }

    public ServiceProvider getUpdatedServiceProvider() {
        return updatedServiceProvider;
    }

    public void setUpdatedServiceProvider(ServiceProvider updatedServiceProvider) {
        this.updatedServiceProvider = updatedServiceProvider;
    }
}
