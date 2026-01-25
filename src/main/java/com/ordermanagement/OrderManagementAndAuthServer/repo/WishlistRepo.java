package com.ordermanagement.OrderManagementAndAuthServer.repo;

import com.ordermanagement.OrderManagementAndAuthServer.model.WishlistItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepo extends JpaRepository<WishlistItem,Long> {

    Page<WishlistItem> findByUserId(Long userId,Pageable pageable);
    Page<WishlistItem> findAll(Pageable pageable);

}
