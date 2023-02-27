package com.lti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.dao.CartDao;
import com.lti.model.Cart;
import com.lti.model.Item;


@Service
public class CartServiceImpl implements CartService {

	@Autowired
	CartDao cartDao;
	
	public int addItemsToCart(Item item) {
		
		return cartDao.addItemsToCart(item);
	}

	public int emptyCart(Cart cart) {
		return cartDao.emptyCart(cart);
	}

	@Override
	public List<Item> getCartItems(int userId) {
		
		return cartDao.getCartItems(userId);
	}

	//My modifications
	@Override
	
	public int deleteItemsFromCart(Item item) {
		
		return cartDao.deleteItemsFromCart(item);
	}
	
	

}
