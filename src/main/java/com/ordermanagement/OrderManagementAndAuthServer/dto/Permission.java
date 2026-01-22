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
    PRODUCT_READ
}
