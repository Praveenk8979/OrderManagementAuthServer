package com.ordermanagement.OrderManagementAndAuthServer.service;

import com.ordermanagement.OrderManagementAndAuthServer.dto.Permission;
import com.ordermanagement.OrderManagementAndAuthServer.dto.UserRole;
import com.ordermanagement.OrderManagementAndAuthServer.model.User;
import com.ordermanagement.OrderManagementAndAuthServer.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;  // ✅ Spring Security ka
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));

        List<GrantedAuthority> authorities = new ArrayList<>();

        // LOOP THROUGH ROLES
        for (UserRole role : user.getRoles()) {

            // ROLE_ADMIN / ROLE_USER
            authorities.add(
                    new SimpleGrantedAuthority("ROLE_" + role.name())
            );

            // permissions of each role
            for (Permission permission : role.getPermissions()) {
                authorities.add(
                        new SimpleGrantedAuthority(permission.name())
                );
            }
        }


        // Return Spring Security User with roles
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                authorities
        );
    }
}

