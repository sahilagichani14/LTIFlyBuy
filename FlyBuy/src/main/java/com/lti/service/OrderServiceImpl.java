package com.lti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.dao.OrderDao;
import com.lti.model.Order;
import com.lti.model.Payment;


@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderDao orderDao;
	
	public Order findOrderById(int orderId) {

		return orderDao.findOrderById(orderId);
	}

	public List<Order> findOrderByUserId(int userId) {

		return orderDao.findOrderByUserId(userId);
	}

	public int makePayment(Payment payment) {
		
		return orderDao.makePayment(payment);
	}

}
