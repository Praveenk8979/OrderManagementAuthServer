package com.ordermanagement.OrderManagementAndAuthServer.controller;

import com.ordermanagement.OrderManagementAndAuthServer.model.Inventory;
import com.ordermanagement.OrderManagementAndAuthServer.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PreAuthorize("hasAuthority('PRODUCT_CREATE')")
    @PostMapping
    public Inventory createProduct(@RequestBody Inventory inventory){
        return  inventoryService.createProduct(inventory);
    }

    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    @GetMapping
    public List<Inventory> getAllProducts() {
        return inventoryService.getAllProducts();
    }

    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    @GetMapping("/{id}")
    public Inventory getProduct(@PathVariable Long id) {
        return inventoryService.getProductById(id);
    }

    @PreAuthorize("hasAuthority('PRODUCT_UPDATE')")
    @PutMapping("/{id}")
    public Inventory updateProduct(
            @PathVariable Long id,
            @RequestBody Inventory inventory) {
        return inventoryService.updateProduct(id, inventory);
    }

    @PreAuthorize("hasAuthority('PRODUCT_DELETE')")
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {

        inventoryService.deleteProduct(id);
        return "Product deleted successfully";
    }
}
