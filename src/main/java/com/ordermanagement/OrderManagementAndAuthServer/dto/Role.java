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
            Permission.PRODUCT_READ,

            // cart
            Permission.CART_READ_ALL,
            Permission.CART_UPDATE,

            // wishlist
            Permission.WISHLIST_MANAGE_ALL,

            // address
            Permission.ADDRESS_READ_ALL,

            // review
            Permission.REVIEW_MANAGE_ALL,

            // notification
            Permission.NOTIFICATION_SEND
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
            Permission.PRODUCT_READ,

            // cart
            Permission.CART_READ,
            Permission.CART_UPDATE,

            // wishlist
            Permission.WISHLIST_MANAGE,

            // review
            Permission.REVIEW_MANAGE,

            // notification
            Permission.NOTIFICATION_READ
    ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions){
        this.permissions=permissions;
    }

    public Set<Permission> getPermissions(){
        return permissions;
    }
}
