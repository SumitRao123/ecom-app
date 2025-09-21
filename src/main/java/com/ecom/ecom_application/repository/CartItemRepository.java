package com.ecom.ecom_application.repository;

import com.ecom.ecom_application.model.CartItem;
import com.ecom.ecom_application.model.Product;
import com.ecom.ecom_application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem
        ,Long> {
    CartItem findByUserAndProduct(User user, Product product);
    void deleteByUserAndProduct(User user, Product product);
    List<CartItem> findByUser(User user);
    void deleteByUser(User user);
}
















