package com.lti.service;

import java.util.List;

import com.lti.model.Cart;
import com.lti.model.Item;

public interface CartService {

	public int addItemsToCart(Item item);
	public int emptyCart(Cart cart);
	public List<Item> getCartItems(int userId);
	
	//My modifications from here
	public int deleteItemsFromCart(Item item);
}
