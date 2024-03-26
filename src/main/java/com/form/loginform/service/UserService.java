package com.form.loginform.service;

import com.form.loginform.entity.User;

public interface UserService {
    public User saveUser(User user);

    public boolean deleteUser(Long id);

    public User updateUser(User user);

    public User getUser(String email);
}
