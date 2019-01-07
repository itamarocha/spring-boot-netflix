
package com.invillia.acme.orderservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.invillia.acme.orderservice.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
