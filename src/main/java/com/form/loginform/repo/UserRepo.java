package com.form.loginform.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.form.loginform.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    public User findByEmail(String email);
}
