package com.ordermanagement.OrderManagementAndAuthServer.controller;

import com.ordermanagement.OrderManagementAndAuthServer.dto.AdminPasswordChangeDto;
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
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepo repo;

    @Autowired
    private AuthService authService;

    @Autowired
    private EmaiService emaiService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // ADMIN – dashboard
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminOnly() {
        return "Admin Access";
    }

    // ADMIN – get own profile
    @GetMapping("/profile")
    @PreAuthorize("hasRole('ADMIN')")
    public User getAdminProfile(Authentication auth) {
        return repo.findByUserName(auth.getName())
                .orElseThrow(() -> new RuntimeException("Admin not found"));
    }

    // ADMIN – update own profile (username, email)
    @PutMapping("/profile")
    @PreAuthorize("hasRole('ADMIN')")
    public User updateAdminProfile(@RequestBody User updated, Authentication auth) {
        User admin = repo.findByUserName(auth.getName())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (updated.getUserName() != null && !updated.getUserName().isBlank()) {
            admin.setUserName(updated.getUserName());
        }
        if (updated.getEmail() != null && !updated.getEmail().isBlank()) {
            admin.setEmail(updated.getEmail());
        }

        return repo.save(admin);
    }

    // ADMIN – find user by id
    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('USER_READ_ALL')")
    public User findById(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    // ADMIN – find all users
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('USER_READ_ALL')")
    public List<User> findAll() {
        return repo.findAll();
    }

    // ADMIN – update any user
    @PutMapping("/users/{id}")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public User updateUser(
            @PathVariable Long id,
            @RequestBody User updated) {

        User user = repo.findById(id).orElseThrow();
        //user update
        if (updated.getUserName() != null && !updated.getUserName().isBlank()) {
            user.setUserName(updated.getUserName());
        }

        // STATUS (ACTIVE / INACTIVE)
        if (updated.getStatus() != null) {
            user.setStatus(updated.getStatus());
        }

        // ROLE update
        if (updated.getRoles() != null && !updated.getRoles().isEmpty()) {
            user.setRoles(updated.getRoles());
        }
        return repo.save(user);
    }

    // ADMIN – delete user
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public String deleteUser(@PathVariable Long id) {
        repo.deleteById(id);
        return "User Deleted";
    }

    //admin change own password
    @PutMapping("/changePassword")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminChangeOwnPassword(
            @RequestBody PasswordChangeDto dto,
            Authentication authentication) {

        User admin = repo.findByUserName(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (!passwordEncoder.matches(dto.getOldPassword(), admin.getPassword())) {
            throw new RuntimeException("Old password incorrect");
        }

        if (!PasswordValidator.isValid(dto.getNewPassword())) {
            throw new RuntimeException("Weak password");
        }

        admin.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        repo.save(admin);
        emaiService.sendPasswordChangeAlert(admin.getEmail(), admin.getUserName());

        return "Admin password updated successfully. Email sent.";
    }

    //admin change user password
    @PutMapping("/users/{id}/changePassword")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public String adminChangeUserPassword(
            @PathVariable Long id,
            @RequestBody AdminPasswordChangeDto dto) {

        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!PasswordValidator.isValid(dto.getNewPassword())) {
            throw new RuntimeException("Weak password");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        repo.save(user);
        emaiService.sendPasswordChangeAlert(user.getEmail(), user.getUserName());

        return "User password changed by admin. Email sent.";
    }


}
