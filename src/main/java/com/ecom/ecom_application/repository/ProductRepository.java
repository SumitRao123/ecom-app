package com.ecom.ecom_application.repository;

import com.ecom.ecom_application.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByActiveTrue();

    @Query(value = "SELECT p from product_table p where p.active = true AND p.stockQuantity > 0 AND LOWER(p.name) LIKE LOWER(CONCAT('%',:keyword,'%'))",nativeQuery = true)
    List<Product> searchProduct(@Param("keyword") String keyword);
}

