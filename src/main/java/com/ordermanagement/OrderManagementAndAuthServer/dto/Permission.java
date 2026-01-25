package com.ordermanagement.OrderManagementAndAuthServer.dto;

public enum Permission {

    //User permission
    USER_CREATE,
    USER_READ_SELF,
    USER_READ_ALL,
    USER_UPDATE,
    USER_DELETE,

    //order permission
    ORDER_CREATE,
    ORDER_READ_SELF,
    ORDER_UPDATE,

    //Inventory/Product Permission
    PRODUCT_CREATE,
    PRODUCT_UPDATE,
    PRODUCT_DELETE,
    PRODUCT_READ,

    //Cart permission
    CART_READ,
    CART_UPDATE,
    CART_READ_ALL,

    // wishlist permission
    WISHLIST_MANAGE,
    WISHLIST_MANAGE_ALL,

    // address permission
    ADDRESS_READ_ALL,

    // review permission
    REVIEW_MANAGE,
    REVIEW_MANAGE_ALL,

    // notification permission
    NOTIFICATION_READ,
    NOTIFICATION_SEND
}
