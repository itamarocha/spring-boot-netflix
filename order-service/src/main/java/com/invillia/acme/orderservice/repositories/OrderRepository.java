package com.invillia.acme.orderservice.repositories;

import com.invillia.acme.orderservice.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
