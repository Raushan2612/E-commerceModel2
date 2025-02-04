package com.cts.ppstores.services.implementations;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.ppstores.dtos.OrderItemRequest;
import com.cts.ppstores.dtos.OrderRequest;
import com.cts.ppstores.dtos.Response;
import com.cts.ppstores.enums.OrderStatus;
import com.cts.ppstores.exceptions.NotFoundException;
import com.cts.ppstores.models.Order;
import com.cts.ppstores.models.OrderItem;
import com.cts.ppstores.models.Product;
import com.cts.ppstores.models.User;
import com.cts.ppstores.repositories.OrderItemRepo;
import com.cts.ppstores.repositories.OrderRepo;
import com.cts.ppstores.repositories.ProductRepo;
import com.cts.ppstores.services.interfaces.OrderService;
import com.cts.ppstores.services.interfaces.UserService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
   
    private final OrderRepo orderRepo;
  
    private final OrderItemRepo orderItemRepo;
    private final ProductRepo productRepo;
    private final UserService userService;

    @Override
    public Response placeOrder(OrderRequest orderRequest) {

    	log.info("Placing order: {}", orderRequest);
    	
        User user = userService.getLoginUser();
        
        List<OrderItem> orderItemList = new ArrayList<>();
        for (OrderItemRequest orderItemRequest : orderRequest.getItems()) {
        	
            Product product = productRepo.findById(orderItemRequest.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product Not Found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemRequest.getQuantity());
            orderItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(orderItemRequest.getQuantity()))); // set price according to the quantity
            orderItem.setStatus(OrderStatus.PENDING);
            orderItem.setUser(user);

            orderItemList.add(orderItem);
        }

//        BigDecimal totalPrice;
//        if (orderRequest.getTotalPrice() != null && orderRequest.getTotalPrice().compareTo(BigDecimal.ZERO) > 0) {
//            totalPrice = orderRequest.getTotalPrice();
//        } else {
//            totalPrice = BigDecimal.ZERO;
//            for (OrderItem orderItem : orderItemList) {
//                totalPrice = totalPrice.add(orderItem.getPrice());
//            }
//        }
        
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItemList) {
        	totalPrice = totalPrice.add(orderItem.getPrice());
        }
        
        Order order = new Order();
        order.setOrderItemList(orderItemList);
        order.setTotalPrice(totalPrice);

        for(OrderItem orderItem : orderItemList) {
        	orderItem.setOrder(order);
        }

        orderRepo.save(order);
        
        log.info("Order placed successfully: {}", order);

        return Response.builder()
                .status(200)
                .message("Order was successfully placed")
                .build();
    }

    @Override
    public Response updateOrderItemStatus(Long orderItemId, String status) {
    	
    	log.info("Updating order item status for ID {}: {}", orderItemId, status);
    	
        OrderItem orderItem = orderItemRepo.findById(orderItemId)
                .orElseThrow(()-> new NotFoundException("Order Item not found"));

        orderItem.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        orderItemRepo.save(orderItem);
        
        log.info("Order item status updated successfully: {}", orderItem);
        
        return Response.builder()
                .status(200)
                .message("Order status updated successfully")
                .build();
    }
}