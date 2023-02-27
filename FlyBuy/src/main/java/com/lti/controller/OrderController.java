package com.lti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lti.model.Order;
import com.lti.model.Payment;
import com.lti.service.OrderService;

@RestController
@CrossOrigin
public class OrderController {
	
	@Autowired
	OrderService orderService;

	@GetMapping(path="/find-order")
	public Order findOrderById(@RequestBody int orderId) {

		return orderService.findOrderById(orderId);
	}

	@GetMapping(path="/find-user-order")
	public List<Order> findOrderByUserId(@RequestBody int userId) {

		return orderService.findOrderByUserId(userId);
	}

	@PostMapping(path="/payment")
	public int makePayment(@RequestBody Payment payment) {
		
		return orderService.makePayment(payment);
	}
}
