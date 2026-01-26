package com.ordermanagement.OrderManagementAndAuthServer.controller;

import com.ordermanagement.OrderManagementAndAuthServer.ExceptionHandler.ExceptionHandling;
import com.ordermanagement.OrderManagementAndAuthServer.dto.PasswordChangeDto;
import com.ordermanagement.OrderManagementAndAuthServer.keys.PasswordValidator;
import com.ordermanagement.OrderManagementAndAuthServer.model.User;
import com.ordermanagement.OrderManagementAndAuthServer.repo.UserRepo;
import com.ordermanagement.OrderManagementAndAuthServer.service.AuthService;
import com.ordermanagement.OrderManagementAndAuthServer.service.EmaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepo repo;

    @Autowired
    private AuthService authService;

    @Autowired
    private EmaiService emaiService;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
        user.setEmail(updated.getEmail());
        return repo.save(user);
    }

    @PutMapping("/changePassword")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public  String changePassword(@RequestBody PasswordChangeDto dto, Authentication authentication){
        User user=repo.findByUserName(authentication.getName())
                .orElseThrow(()-> new ExceptionHandling.UserNotFoundException("User not found!."));

        //old password check
        if(!passwordEncoder.matches(dto.getOldPassword(),user.getPassword())){
            throw  new RuntimeException("Old Password is incorrect!.");
        }

        //same password check
        if(passwordEncoder.matches(dto.getNewPassword(),user.getPassword())){
            throw new RuntimeException("New Password must be different from old password");
        }

        if(!dto.getNewPassword().equals(dto.getConfirmPassword())){
            throw new RuntimeException("New password and confirm password do not match");
        }


        //strength validation
        if(!PasswordValidator.isValid(dto.getNewPassword())){
            throw new RuntimeException(
                            "Password must be at least 8 characters long, " +
                            "contain uppercase, lowercase, digit and special character"
            );
        }

        //enocde & save
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        repo.save(user);

        //send email alert
        emaiService.sendPasswordChangeAlert(user.getEmail(), user.getUserName());
        return "Password Update Successfully!.Email Alert sent!..";
    }

}
