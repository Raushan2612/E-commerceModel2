package com.cts.ppstores.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.ppstores.models.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {
}