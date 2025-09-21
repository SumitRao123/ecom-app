package com.ecom.ecom_application.service;

import com.ecom.ecom_application.dto.OrderItemDTO;
import com.ecom.ecom_application.dto.OrderResponse;
import com.ecom.ecom_application.model.*;
import com.ecom.ecom_application.repository.CartItemRepository;
import com.ecom.ecom_application.repository.OrderRepository;
import com.ecom.ecom_application.repository.ProductRepository;
import com.ecom.ecom_application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartItemService cartItemService;
    private final UserRepository userRepository;


    public Optional<OrderResponse> createOrder(String userId){
        // extract cart from user
        List<CartItem> cart  = cartItemService.getCart(userId);


        if(cart.isEmpty()) return null;
        // validate the user
        Optional<User>  user=  userRepository.findById(Long.valueOf(userId));
        if(user.isEmpty()){
            return Optional.empty();
        }
        BigDecimal totalprice = cart.stream().map(CartItem::getPrice).reduce(BigDecimal.ZERO,BigDecimal::add);

        Order order = new Order();
        order.setUser(user.get());
        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalprice);
        order.setOrderItemList(cart.stream().
                      map(cartItem ->
                              new OrderItem(
                                      null
                                      ,cartItem.getProduct(),
                                      order,cartItem.getQuantity(),
                                      cartItem.getPrice())).toList());

        Order savedOrder = orderRepository.save(order);

        cartItemService.clearCart(userId);
       return Optional.of(MapToOrderResponse(order));
    }
    private OrderResponse MapToOrderResponse(Order order){
        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getOrderStatus(),
                order.getOrderItemList().stream()
                        .map((item)-> new OrderItemDTO(
                                item.getId(),
                                item.getProduct().getId()
                                ,item.getQuantity(),
                                item.getPrice(),
                                item.getPrice().
                                        multiply(new
                                                BigDecimal(item.getQuantity())))).toList(),
                order.getCreationTime()
        );

    }
}
