package com.ordermanagement.OrderManagementAndAuthServer.dto;


import java.util.Set;

public enum UserRole {

    USER(Role.USER),
    ADMIN(Role.ADMIN);

    private final Role role;

    UserRole(Role role){
        this.role = role;
    }

    public Set<Permission> getPermissions() {
        return role.getPermissions();
    }

}
