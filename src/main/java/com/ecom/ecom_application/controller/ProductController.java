package com.ecom.ecom_application.controller;



import com.ecom.ecom_application.dto.ProductRequest;
import com.ecom.ecom_application.dto.ProductResponse;
import com.ecom.ecom_application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor // auto-injects final fields
public class ProductController {

    private final ProductService productService;

    /**
     * ➕ Create a new Product
     * POST: /api/products
     */
    @PostMapping("/save")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {
        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 🔄 Update an existing Product by ID
     * PUT: /api/products/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id,
                                                @RequestBody ProductRequest request) {
        boolean updated = productService.updateProduct(id, request);
        return updated
                ? ResponseEntity.ok("Product updated successfully.")
                : ResponseEntity.notFound().build();
    }

    /**
     * 📦 Get all active Products
     * GET: /api/products/active
     */
    @GetMapping("/active")
    public ResponseEntity<List<ProductResponse>> getAllActiveProducts() {
        List<ProductResponse> activeProducts = productService.getAllActiveProducts();
        return ResponseEntity.ok(activeProducts);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
         boolean deleted = productService.deleteProduct(id);
         return (deleted) ?  ResponseEntity.noContent().build() : ResponseEntity.notFound().build();

    }

    @GetMapping("/search")
    public  ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam("keyword") String keyword){
        return ResponseEntity.ok(productService.searchProducts(keyword));
    }
}

