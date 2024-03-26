package com.form.loginform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.form.loginform.config.CustomUserDetails;
import com.form.loginform.entity.User;
import com.form.loginform.model.UserModel;
import com.form.loginform.service.UserServiceImpl;

@Controller
public class MController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserServiceImpl userServiceImpl;

    @RequestMapping("/userLogin")
    public String userLogin(@ModelAttribute("msg") String msg, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("msg", msg);
            return "loginUser";
        }
        return "redirect:/userDetails";
    }

    @RequestMapping("/register")
    public String registerUser(@ModelAttribute("msg") String msg, Model model) {
        model.addAttribute("msg", msg);
        return "userRegistration";
    }

    @RequestMapping("/registerUser")
    public String userRegistrationHandler(UserModel userModel, RedirectAttributes model) {
        try {
            User user = new User();
            if (userModel.getCpassword().equals(userModel.getPassword()) && userModel.getEmail().length() > 9
                    && userModel.getFirstName().length() > 3 && userModel.getLastName().length() > 3) {
                user.setFirstName(userModel.getFirstName());
                user.setLastName(userModel.getLastName());
                user.setEmail(userModel.getEmail());
                user.setPassword(passwordEncoder.encode(userModel.getPassword()));
                userServiceImpl.saveUser(user);
                model.addFlashAttribute("msg", "User Registration Successful.");
                return "redirect:/userLogin";
            }
            model.addFlashAttribute("msg", "Please fill correct details.");
            return "redirect:/register";
        } catch (Exception e) {
            model.addFlashAttribute("msg", "Something Wrong at backend.");
            return "redirect:/register";
        }

    }

    @RequestMapping("/userDetails")
    public String userDetails(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/userLogin";
        }
        User user = userServiceImpl.getUser(customUserDetails.getUsername());
        model.addAttribute("user", user);
        return "userDashboard";
    }

    @RequestMapping("/adminDetails")
    public String adminDetails(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/userLogin";
        }
        User user = userServiceImpl.getUser(customUserDetails.getUsername());
        model.addAttribute("user", user);
        return "adminDashboard";
    }

}
