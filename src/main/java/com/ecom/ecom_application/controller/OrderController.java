package com.ecom.ecom_application.controller;

import com.ecom.ecom_application.dto.OrderResponse;
import com.ecom.ecom_application.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")

public class OrderController {


    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestHeader("x-userid") String userId){
         return orderService.createOrder(userId)
                 .map(orderResponse -> new ResponseEntity<>(orderResponse, HttpStatus.CREATED))
                 .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
