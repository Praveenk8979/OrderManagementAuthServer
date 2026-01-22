package com.ordermanagement.OrderManagementAndAuthServer.repo;

import com.ordermanagement.OrderManagementAndAuthServer.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory,Long> {
}
