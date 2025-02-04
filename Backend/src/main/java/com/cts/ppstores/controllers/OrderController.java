package com.cts.ppstores.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cts.ppstores.dtos.OrderRequest;
import com.cts.ppstores.dtos.Response;
import com.cts.ppstores.services.interfaces.OrderService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Response> placeOrder(@RequestBody @Valid OrderRequest orderRequest){
        Response response = orderService.placeOrder(orderRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-item-status/{orderItemId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> updateOrderItemStatus(@PathVariable Long orderItemId,  @RequestParam String status){
        Response response = orderService.updateOrderItemStatus(orderItemId, status);
        return ResponseEntity.ok(response);
    }

}