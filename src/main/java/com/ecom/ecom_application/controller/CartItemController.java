package com.ecom.ecom_application.controller;

import com.ecom.ecom_application.dto.CartItemRequest;
import com.ecom.ecom_application.model.CartItem;
import com.ecom.ecom_application.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @PostMapping("/add")
    public ResponseEntity<String> addCartEntity(@RequestHeader("x-user_id") String user_id, @RequestBody CartItemRequest request){
        boolean flag = cartItemService.addItem(user_id,request);
        if(!flag){
            return new ResponseEntity<>("No user found or shyd Product Khtm Hogye hn", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Cart Added..",HttpStatus.CREATED);
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable Long productId) {
        boolean deleted = cartItemService.removeItem(userId, productId);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(
            @RequestHeader("X-User-ID") String userId) {
        return ResponseEntity.ok(cartItemService.getCart(userId));


    }
}
