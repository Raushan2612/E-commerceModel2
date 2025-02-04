package com.cts.ppstores.services.interfaces;


import com.cts.ppstores.dtos.OrderRequest;
import com.cts.ppstores.dtos.Response;

public interface OrderService {
    Response placeOrder(OrderRequest orderRequest);
    Response updateOrderItemStatus(Long orderItemId, String status);
}