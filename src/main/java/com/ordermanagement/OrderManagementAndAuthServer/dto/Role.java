package com.ordermanagement.OrderManagementAndAuthServer.dto;

import java.util.Set;

public enum Role {

    ADMIN(Set.of(
            //user management
            Permission.USER_CREATE,
            Permission.USER_READ_SELF,
            Permission.USER_READ_ALL,
            Permission.USER_UPDATE,
            Permission.USER_DELETE,
            //order management
            Permission.ORDER_CREATE,
            Permission.ORDER_READ_SELF,
            Permission.ORDER_UPDATE,

            //Inventory Management
            Permission.PRODUCT_CREATE,
            Permission.PRODUCT_UPDATE,
            Permission.PRODUCT_DELETE,
            Permission.PRODUCT_READ

            )),

    USER(Set.of(
            //User self actions
            Permission.USER_CREATE,
            Permission.USER_READ_SELF,
            Permission.USER_UPDATE,

            //order actions
            Permission.ORDER_CREATE,
            Permission.ORDER_READ_SELF,

            //Inventory view only
            Permission.PRODUCT_READ
    ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions){
        this.permissions=permissions;
    }

    public Set<Permission> getPermissions(){
        return permissions;
    }
}
