package com.ordermanagement.OrderManagementAndAuthServer.controller;

import com.ordermanagement.OrderManagementAndAuthServer.model.User;
import com.ordermanagement.OrderManagementAndAuthServer.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepo repo;

    // USER – read own profile
    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('USER_READ_SELF')")
    public User myProfile(Authentication auth) {
        return repo.findByUserName(auth.getName()).orElseThrow();
    }

    // USER – update own profile
    @PutMapping("/profile")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public User updateProfile(
            @RequestBody User updated,
            Authentication auth) {

        User user = repo.findByUserName(auth.getName()).orElseThrow();
        user.setUserName(updated.getUserName());
        return repo.save(user);
    }
}
