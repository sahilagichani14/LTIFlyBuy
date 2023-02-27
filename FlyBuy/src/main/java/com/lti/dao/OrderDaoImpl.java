package com.lti.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.lti.model.Order;
import com.lti.model.Payment;

@Repository
public class OrderDaoImpl implements OrderDao {

	@PersistenceContext
	EntityManager em;
	
	public Order findOrderById(int orderId) {
		String jpql = "select o from Order o where o.orderid=:ordId";
		TypedQuery<Order> query = em.createQuery(jpql, Order.class);
		query.setParameter("ordId", orderId);
		return query.getSingleResult();
	}
	
	public List<Order> findOrderByUserId(int userId){
		String jpql = "select o from Order o where o.user_id=:userid";
		TypedQuery<Order> query = em.createQuery(jpql, Order.class);
		query.setParameter("userid", userId);
		return query.getResultList();
	}
	
	@Transactional
	public int makePayment(Payment payment) {
		Payment persistedPayment=em.merge(payment);
		return persistedPayment.getPaymentId();
	}
}
