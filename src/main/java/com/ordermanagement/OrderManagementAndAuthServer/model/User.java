package com.ordermanagement.OrderManagementAndAuthServer.model;

import com.ordermanagement.OrderManagementAndAuthServer.dto.UserRole;
import com.ordermanagement.OrderManagementAndAuthServer.dto.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "userName",unique = true,nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Set<UserRole> roles;

    // Status as Enum
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    private String email;
}
