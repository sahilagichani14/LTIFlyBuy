package com.lti.dao;

import java.util.List;

import com.lti.model.AddressUser;
import com.lti.model.Admin;
import com.lti.model.Item;
import com.lti.model.Order;
import com.lti.model.User;

public interface UserDao {

	public int updateUser(User user);
	public User findUser(int userId);
	public int addUserWithCart(User user);
	public int findByEmailAndPassword(String userEmail, String password);
	public Order placeOrder(int userId);
	public List<Item> viewOrderItems(int userId);
	public int addUserAddress(AddressUser addressUser);
	boolean isUserPresent(String userEmail);
	public List<Item> findItemsById(int userId);
	public int findByEmailAndUpdate(String userEmail, String password);
}
