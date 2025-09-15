package com.ecom.ecom_application.service;


import com.ecom.ecom_application.dto.ProductRequest;
import com.ecom.ecom_application.dto.ProductResponse;
import com.ecom.ecom_application.model.Product;
import com.ecom.ecom_application.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private   ProductRepository productRepository;
    public ProductResponse createProduct(ProductRequest request){
        Product product = toEntity(request);
        productRepository.save(product);
        return toResponse(product);
    }

    public boolean updateProduct(Long id, ProductRequest request) {
        return productRepository.findById(id).map(existingProduct -> {
            updateProductFromRequest(existingProduct, request);
            productRepository.save(existingProduct);
            return true;
        }).orElse(false);
    }

    public List<ProductResponse> getAllActiveProducts(){
        List<Product> list = productRepository.findByActiveTrue();
        List<ProductResponse> productResp = list.stream().map(this::toResponse).collect(Collectors.toList());

        return productResp;
    }

    public boolean deleteProduct(Long id){
        Product product = productRepository.findById(id).orElse(null);
        if(product == null) return false;
        product.setActive(false);
        productRepository.save(product);

        return true;
    }
    public List<ProductResponse> searchProducts(String keyword){
         List<Product> list = productRepository.searchProduct(keyword);

         return list.stream().map(this::toResponse).collect(Collectors.toList());
    }
    private void updateProductFromRequest(Product existingProduct, ProductRequest request) {
        existingProduct.setName(
                request.getName() == null ? existingProduct.getName() : request.getName()
        );

        existingProduct.setDescription(
                request.getDescription() == null ? existingProduct.getDescription() : request.getDescription()
        );

        existingProduct.setPrice(
                request.getPrice() == null ? existingProduct.getPrice() : request.getPrice()
        );

        existingProduct.setStockQuantity(
                request.getStockQuantity() == null ? existingProduct.getStockQuantity() : request.getStockQuantity()
        );

        existingProduct.setCategory(
                request.getCategory() == null ? existingProduct.getCategory() : request.getCategory()
        );

        existingProduct.setImageUrl(
                request.getImageUrl() == null ? existingProduct.getImageUrl() : request.getImageUrl()
        );

        existingProduct.setActive(
                request.getActive() == null ? existingProduct.getActive() : request.getActive()
        );
    }
    private ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .category(product.getCategory())
                .imageUrl(product.getImageUrl())
                .active(product.getActive())
                .build();
    }
    private  Product toEntity(ProductRequest request) {
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .category(request.getCategory())
                .imageUrl(request.getImageUrl())
                .active(request.getActive() != null ? request.getActive() : true)
                .build();
    }

}
