package com.ordermanagement.OrderManagementAndAuthServer.dto;

import java.util.Set;

public enum Role {

    ADMIN(Set.of(
            Permission.USER_CREATE,
            Permission.USER_READ_SELF,
            Permission.USER_READ_ALL,
            Permission.USER_UPDATE,
            Permission.USER_DELETE
    )),

    USER(Set.of(
            Permission.USER_CREATE,
            Permission.USER_READ_SELF,
            Permission.USER_UPDATE
    ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions){
        this.permissions=permissions;
    }

    public Set<Permission> getPermissions(){
        return permissions;
    }
}
