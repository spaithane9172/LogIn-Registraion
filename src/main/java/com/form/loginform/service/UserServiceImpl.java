package com.form.loginform.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.form.loginform.entity.User;
import com.form.loginform.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Override
    public User saveUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public boolean deleteUser(Long id) {
        userRepo.deleteById(id);
        Optional<User> user = userRepo.findById(id);
        return user.isEmpty();
    }

    @Override
    public User updateUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public User getUser(String email) {
        return userRepo.findByEmail(email);
    }

}
