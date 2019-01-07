package com.invillia.acme.orderservice.web.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invillia.acme.orderservice.entities.Order;
import com.invillia.acme.orderservice.entities.OrderStatus;
import com.invillia.acme.orderservice.exceptions.OrderNotFoundException;
import com.invillia.acme.orderservice.exceptions.PaymentNotFoundException;
import com.invillia.acme.orderservice.repositories.OrderRepository;
import com.invillia.acme.orderservice.service.PaymentServiceClient;
import com.invillia.acme.orderservice.web.models.PaymentResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/orders")
@Slf4j
@Api(value = "Order")
public class OrderController {

	private OrderRepository repo;
	
	private PaymentServiceClient paymentServiceClient;

	@Autowired
	public OrderController(OrderRepository repo, PaymentServiceClient client) {
		this.repo = repo;
		this.paymentServiceClient = client;
	}

	@ApiOperation(value = "List all orders")
	@GetMapping
	@HystrixCommand
	public ResponseEntity<List<Order>> all() {
		log.info("Listing orders ");
		List<Order> orders = repo.findAll().stream()
				.collect(Collectors.toList());
		return ResponseEntity.ok(orders);
	}

	@ApiOperation(value = "Cancel a order")
	@DeleteMapping("/{id}/cancel")
	ResponseEntity cancel(@PathVariable Long id) throws OrderNotFoundException, PaymentNotFoundException {
		Order order = repo.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
		if (order.getStatus() != OrderStatus.REFUNDED) {
			PaymentResponse paymentByOrder = paymentServiceClient.getPaymentByOrder(order.getId().toString())
					.orElseThrow(() -> new PaymentNotFoundException());
			verifyPaymentRefundLimitDate(paymentByOrder.getPaymentDate());
			order.setStatus(OrderStatus.REFUNDED);
			return ResponseEntity.ok(repo.save(order));
		}
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
				.body("You can't cancel an order that is in the  " + order.getStatus() + " status");
	}
	
	private boolean verifyPaymentRefundLimitDate(Date date) {
		LocalDate now = LocalDate.now();
		LocalDate paymentDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate limitDate = paymentDate.plusDays(9);
		return limitDate.isBefore(now);

	}
	@ApiOperation(value = "Get a order by id")
	@GetMapping("/{id}")
	public Order find(@ApiParam(value = "order Id", required = true) @PathVariable Long id) throws OrderNotFoundException {
		log.info("get order by id ");
		return repo.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
	}

	@ApiOperation(value = "Create a order")
	@PostMapping
	public Order createOrder(@ApiParam(value = "Order", required = true) @RequestBody Order order) {
		log.info("Create oreder  " + order.toString());
		return repo.save(order);
	}
}
