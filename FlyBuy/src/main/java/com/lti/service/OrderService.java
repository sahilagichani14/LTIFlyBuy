package com.lti.service;

import java.util.List;

import com.lti.model.Order;
import com.lti.model.Payment;

public interface OrderService {

	public Order findOrderById(int orderId);
	public List<Order> findOrderByUserId(int userId);
	public int makePayment(Payment payment);
}
