package com.lti.service;

import java.io.IOException;
import java.util.List;

import com.lti.model.AddressUser;
import com.lti.model.Item;
import com.lti.model.Order;
import com.lti.model.User;

public interface UserService {

	public int updateUser(User user);
	public User findUser(int userId);
	public void addUserWithCart(User user);
	public User loginUser(String userEmail, String password);
	public Order placeOrder(int userId);
	public List<Item> viewOrderItems(int userId);
	public String resetUserPassword(String email);
	public int findByEmailAndUpdate(String email, String password);
	public int addUserAddress(AddressUser addressUser);
	public List<Item> findOrderById(int userId);
}
