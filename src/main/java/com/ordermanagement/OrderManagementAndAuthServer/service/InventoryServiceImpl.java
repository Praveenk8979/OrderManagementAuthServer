package com.ordermanagement.OrderManagementAndAuthServer.service;


import com.ordermanagement.OrderManagementAndAuthServer.model.Inventory;
import com.ordermanagement.OrderManagementAndAuthServer.repo.InventoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepo repo;


    @Override
    public Inventory createProduct(Inventory inventory) {
        if (repo.existsById(inventory.getProductId())) {
            throw new RuntimeException("Product already exists");
        }
        return repo.save(inventory);
    }

    @Override
    public Inventory updateProduct(Long productId, Inventory updated) {
        Inventory product = getProductById(productId);

        if (updated.getProductName() != null)
            product.setProductName(updated.getProductName());

        if (updated.getAvailableStock() != null)
            product.setAvailableStock(updated.getAvailableStock());

        if (updated.getPrice() != null)
            product.setPrice(updated.getPrice());

        if (updated.getCategory() != null)
            product.setCategory(updated.getCategory());

        if (updated.getDescription() != null)
            product.setDescription(updated.getDescription());

        return repo.save(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        if (!repo.existsById(productId)) {
            throw new RuntimeException("Product not found!..");
        }
        repo.deleteById(productId);
    }

    @Override
    public List<Inventory> getAllProducts() {
        return repo.findAll();
    }

    @Override
    public Inventory getProductById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Not Found"));
    }

    //update stock (order/cancel)
    @Transactional
    @Override
    public Inventory updateStock(Long productId, Integer quantityChange) {
//        Inventory product=getProductById(productId);
//        int updateStock=product.getAvailableStock()+quantityChange;
//        if(updateStock<0){
//            throw new RuntimeException("Insufficient stock for productId :"+productId);
//        }
//        product.setAvailableStock(updateStock);
//        return repo.save(product);

        Inventory inventory = repo.findById(productId).orElseThrow(
                () -> new RuntimeException("Product not found!.")
        );
        int upadteStock = inventory.getAvailableStock() + quantityChange;

        if (upadteStock < 0) {
            throw new RuntimeException("Insufficient Stock!.");
        }
        inventory.setAvailableStock(upadteStock);
        return repo.save(inventory);
    }


}
