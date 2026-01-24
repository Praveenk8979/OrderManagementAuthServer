package com.ordermanagement.OrderManagementAndAuthServer.ExceptionHandler;

public class ExceptionHandling extends  RuntimeException{

    // Product out of stock
    public  static  class OutOfStockException extends RuntimeException {
        public OutOfStockException(String productName){
            super("Product " + productName + " is out of stock");
        }
    }

    // User not found
    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String userName){
            super("User " + userName + " not found");
        }
    }

    // Order not found
    public static class OrderNotFoundException extends RuntimeException {
        public OrderNotFoundException(Long orderId){
            super("Order with ID " + orderId + " not found");
        }
    }

    // Invalid order operation
    public static class InvalidOrderOperationException extends RuntimeException {
        public InvalidOrderOperationException(String message) {
            super(message);
        }
    }
}
