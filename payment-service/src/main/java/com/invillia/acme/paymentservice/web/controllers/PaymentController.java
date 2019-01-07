package com.invillia.acme.paymentservice.web.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.invillia.acme.paymentservice.entities.Payment;
import com.invillia.acme.paymentservice.entities.PaymentStatus;
import com.invillia.acme.paymentservice.exceptions.PaymentNotFoundException;
import com.invillia.acme.paymentservice.repositories.PaymentRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/payment")
@Slf4j
public class PaymentController {

    private final PaymentRepository repo;

    @Autowired
    public PaymentController(PaymentRepository paymentRepository) {
        this.repo = paymentRepository;
    }

    @GetMapping
    public List<Payment> all(HttpServletRequest request) {
        log.info("Finding all payments");
        String auth_header = request.getHeader("AUTH_HEADER");
        log.info("AUTH_HEADER: "+auth_header);
        return repo.findAll();
    }

    @GetMapping("/{order}")
    public Payment paymentByOrder(@PathVariable String order) {
        log.info("Finding payment by code :"+order);
        return repo.findByOrderId(order)
                .orElseThrow(() -> new PaymentNotFoundException("Payment with order code  ["+order+"] doesn't exist"));
    }
    @ApiOperation(value = "Refund payment")
	@DeleteMapping("/{orderId}/cancel")
	ResponseEntity<?> cancel(@PathVariable String orderId)  {
    	Payment payment = repo.findByOrderId(orderId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment with order code  ["+orderId+"] doesn't exist"));
		if (payment.getStatus() == PaymentStatus.COMPLETE) {
			payment.setStatus(PaymentStatus.REFUNDED);
			return ResponseEntity.ok(repo.save(payment));
		}
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
				.body("You can't cancel an payment that is in the  " + payment.getStatus() + " status");
	}
    @ApiOperation(value = "Create a payment")
	@PostMapping
	public Payment createOrder(@ApiParam(value = "Payment", required = true) @RequestBody Payment pay) {
		log.info("Create pay  " + pay.toString());
		return repo.save(pay);
	}
}
