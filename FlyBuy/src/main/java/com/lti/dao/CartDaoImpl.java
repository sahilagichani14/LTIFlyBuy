package com.lti.dao;

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

import com.lti.model.Cart;
import com.lti.model.Item;

@Repository
public class CartDaoImpl implements CartDao {
	
	@PersistenceContext
	EntityManager em;

	@Transactional
	public int addItemsToCart(Item item) {
		Item persistedItem=em.merge(item);
		//cannot use em.merge here
		return persistedItem.getCart().getCartId();
	}
	
	
	@Transactional
	public int emptyCart(Cart cart) {
		String jpql ="delete i from Item i where i.cart.cart_id=:cartid";
		Query query = em.createQuery(jpql);
		query.setParameter("cartid", cart.getCartId());
		int num = query.executeUpdate();
		
		if(num>0) {
			System.out.println("Cart is emptied");
		}
		return num;
	}


	@Override
	public List<Item> getCartItems(int userId) {
		String jpql = "select i from Item i where i.cart.cartId = (select c.cartId from Cart c where c.user.userId=:usid)";
		TypedQuery<Item> query = em.createQuery(jpql, Item.class);
		query.setParameter("usid", userId);
		
		List<Item> fetchedItems=new ArrayList();
		List<Item> items=query.getResultList();
		for (Item item : items) {
			if(item.isOrdered()==false)
				fetchedItems.add(item);
		}
		
		return fetchedItems;
	}
	
	public Cart findACart(int userId) {
		String jpql="select c from Cart c where c.user.userId=:uid";
		TypedQuery<Cart> query = em.createQuery(jpql, Cart.class);
		query.setParameter("uid", userId);
		return query.getSingleResult();
	}


	//My Modifications
	
	@Override
	@Transactional
	public int deleteItemsFromCart(Item item) {
		String jpql = "delete i from Item i where i.itemId=:itid and i.isOrdered=false";
		Query query = em.createQuery(jpql).setParameter("itid", item.getItemId());
		
		return query.executeUpdate();
	}
	
	
}
