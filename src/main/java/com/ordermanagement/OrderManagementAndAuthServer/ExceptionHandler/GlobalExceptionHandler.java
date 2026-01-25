package com.ordermanagement.OrderManagementAndAuthServer.ExceptionHandler;

import com.ordermanagement.OrderManagementAndAuthServer.dto.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExceptionHandling.OutOfStockException.class)
    public ResponseEntity<ApiResponse<?>> handleOutOfStock(
            ExceptionHandling.OutOfStockException ex) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(ExceptionHandling.UserNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleUserNotFound(
            ExceptionHandling.UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(ExceptionHandling.OrderNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleOrderNotFound(
            ExceptionHandling.OrderNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(ExceptionHandling.InvalidOrderOperationException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidOperation(
            ExceptionHandling.InvalidOrderOperationException ex) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.failure(ex.getMessage()));
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidation(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.failure("Validation failed"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDenied() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.failure("Access Denied"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failure(ex.getMessage()));
    }
}
