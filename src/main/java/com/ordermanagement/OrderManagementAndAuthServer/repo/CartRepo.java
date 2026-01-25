package com.ordermanagement.OrderManagementAndAuthServer.repo;

import com.ordermanagement.OrderManagementAndAuthServer.model.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<CartItem,Long> {
    Page<CartItem> findByUserId(Long userId,Pageable pageable);
    Page<CartItem> findAll(Pageable pageable);

}
