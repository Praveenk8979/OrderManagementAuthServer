package com.ordermanagement.OrderManagementAndAuthServer.service;

import com.ordermanagement.OrderManagementAndAuthServer.model.Inventory;

import java.util.List;

public interface InventoryService {

    Inventory createProduct(Inventory inventory);

    Inventory updateProduct(Long productId,Inventory updated);
    void deleteProduct(Long productId);

    List<Inventory> getAllProducts();
    Inventory getProductById(Long id);
    Inventory updateStock(Long productId,Integer quantityChange);
}
