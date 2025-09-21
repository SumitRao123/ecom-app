package com.ecom.ecom_application.service;

import com.ecom.ecom_application.dto.CartItemRequest;
import com.ecom.ecom_application.model.CartItem;
import com.ecom.ecom_application.model.Product;
import com.ecom.ecom_application.model.User;
import com.ecom.ecom_application.repository.CartItemRepository;
import com.ecom.ecom_application.repository.ProductRepository;
import com.ecom.ecom_application.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import java.util.*;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public boolean addItem(String user_id, CartItemRequest request) {
        Optional<Product> c = productRepository.findById(request.getProductId());
        Product product= c.get();
        if(product.getStockQuantity() < request.getQuantity()) return false;

        User user = userRepository.findById(Long.valueOf(user_id)).get();
        if(user == null) return false;
        CartItem cart = cartItemRepository.findByUserAndProduct(user,product);
        if(cart != null){
            cart.setQuantity(request.getQuantity() + cart.getQuantity());
            cart.setUser(user);
            cart.setProduct(product);
            cart.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())));
            cartItemRepository.save(cart);
        }
        else{
            CartItem cartItem = new CartItem();
            cartItem.setQuantity(request.getQuantity() );
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }
       return true;
    }

    public boolean removeItem(String userId, Long productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

        if (productOpt.isPresent() && userOpt.isPresent()){
            cartItemRepository.deleteByUserAndProduct(userOpt.get(), productOpt.get());
            return true;
        }
        return false;
    }

    public List<CartItem> getCart(String userId) {
        return userRepository.findById(Long.valueOf(userId))
                .map(cartItemRepository::findByUser)
                .orElseGet(List::of);
    }
    @Transactional
    public void clearCart(String userid){
       userRepository.findById(Long.valueOf(userid)).ifPresent(cartItemRepository::deleteByUser);
    }
}
