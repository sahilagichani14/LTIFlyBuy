package com.lti.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.lti.exception.UserServiceException;
import com.lti.model.AddressUser;
import com.lti.model.Admin;
import com.lti.model.Cart;
import com.lti.model.Item;
import com.lti.model.Order;
import com.lti.model.User;

@Repository
public class UserDaoImpl implements UserDao {
	
	@PersistenceContext
	EntityManager em;
	
	@Transactional
	public int updateUser(User user) {
		User persistedUser=em.merge(user);
		return persistedUser.getUserId();
	}
	
	public User findUser(int userId) {
		return em.find(User.class, userId);
	}
	
	@Transactional
	public int addUserWithCart(User user) {
		Cart cart=new Cart();
		cart.setUser(user);
		user.setCart(cart);
		User persistedUser=em.merge(user);
		
		//System.out.println("User merged");
		return persistedUser.getUserId();
		
	}
	
	@Transactional
	public int addAdmin(Admin admin) {
		Admin persistedAdmin=em.merge(admin);
		return persistedAdmin.getAdminId();
	}
	
	public int findByEmailAndPassword(String userEmail, String password)
	{
		return (Integer) em
                .createQuery("select u.userId from User u where u.userEmail =:uem and u.userPassword =:upw")
                .setParameter("uem", userEmail)
                .setParameter("upw", password)
                .getSingleResult();
	}
	
	@Transactional
	public Order placeOrder(int userId)
	{
		TypedQuery<Cart> query=em.createQuery("select c from Cart c where c.user.userId=:uid",Cart.class);
		query.setParameter("uid", userId);
		Cart cart = query.getSingleResult();
		Order order=new Order();
		order.setCart(cart);
		order.setDeliveryDate(LocalDate.now().plusDays(5));
		order.setOrderDate(LocalDate.now());
		order.setUser(cart.getUser());
		
		//order.setPayment(payment);
		//Query query1=em.createQuery("select i from Item i where i.cart.cartId=(select c.cartId from Cart c where c.user.userId=:uid)");
		List<Item> items=cart.getItems();
		for (Item item : items) {
			item.setOrdered(true);
		}
		
		Order persistedOrder=em.merge(order);
		return persistedOrder;
	}
	
	public List<Item> viewOrderItems(int userId) {
		String jpql="select i from Item i where i.cart.cartId = (select c.cartId from Cart c where c.user.userId=:uid)";
		TypedQuery<Item> query=em.createQuery(jpql, Item.class);
		query.setParameter("uid", userId);
		return query.getResultList();
	}

	public boolean isUserPresent(String userEmail) {
		return (Long)
                em
                .createQuery("select count(u.userId) from User u where u.userEmail = :em")
                .setParameter("em", userEmail)
                .getSingleResult() == 1 ? true : false;
	}
	
	@Transactional
	public int findByEmailAndUpdate(String userEmail, String password) {
		User user=(User) em
                .createQuery("select u from User u where u.userEmail =:uem")
                .setParameter("uem", userEmail)
                .getSingleResult();
		
		user.setUserPassword(password);
		User persistedUser=em.merge(user);
		return persistedUser.getUserId();
	}
	
	public List<Item> findItemsById(int userId) {
		List<Item> items= em
                .createQuery("select i from Item i where i.cart.cartId=(select c.cartId from Cart c where c.user.userId=:uid)")
                .setParameter("uid", userId)
                .getResultList();
		List<Item> orderedItems=new ArrayList<>();
		for (Item item : items) {
			if(item.isOrdered()==true)
			{
				orderedItems.add(item);
			}
		}
		return orderedItems;
	}
	
	@Transactional
	public int addUserAddress(AddressUser addressUser) {
		AddressUser persistAddressUser=em.merge(addressUser);
		return persistAddressUser.getId();
	}
}
