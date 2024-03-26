package com.form.loginform.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.form.loginform.entity.User;
import com.form.loginform.service.UserServiceImpl;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userServiceImpl.getUser(email);
        if (user == null)
            throw new UsernameNotFoundException("User Not Found, Please Register");

        return new CustomUserDetails(user);

    }

}
