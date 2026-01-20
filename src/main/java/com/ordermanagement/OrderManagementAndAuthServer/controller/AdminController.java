package com.ordermanagement.OrderManagementAndAuthServer.controller;

import com.ordermanagement.OrderManagementAndAuthServer.model.User;
import com.ordermanagement.OrderManagementAndAuthServer.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepo repo;

    // ADMIN – dashboard
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminOnly() {
        return "Admin Access";
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
}
