package com.lti.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.dao.CartDao;
import com.lti.dao.UserDao;
import com.lti.exception.UserServiceException;
import com.lti.model.AddressUser;
import com.lti.model.Admin;
import com.lti.model.Item;
import com.lti.model.Order;
import com.lti.model.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	CartDao cartDao;
	
	public int updateUser(User user) {

		return userDao.updateUser(user);
	}

	public User findUser(int userId) {

		return userDao.findUser(userId);
	}

	public void addUserWithCart(User user) {
		if(!userDao.isUserPresent(user.getUserEmail())) {
			 int id = userDao.addUserWithCart(user);
			 String text="Thank you for registering on FlyBuy!! Your id is "+id;
	         String subject="FlyBuy Registration Confirmation";
	         //System.out.println(user.getUserEmail()+"email");
	         emailService.sendEmail(user.getUserEmail(), text, subject);
		}
		else {
			throw new UserServiceException("Email Id is already in Use!");
		}
	}

	public Order placeOrder(int userId) {
		try {	
			if(cartDao.findACart(userId).getItems()==null)
				throw new UserServiceException("No items in cart. Cannot place order");
		    Order order=userDao.placeOrder(userId);
		    String text="Your order has been received with Order ID: "+order.getOrderId()+" placed on "
		    		+order.getOrderDate()+". Thank you for "+ "ordering" + " on FlyBuy. ";
	        String subject="FlyBuy Order Confirm";
		    emailService.sendEmail(userDao.findUser(userId).getUserEmail(), text, subject);
		    return order;
		}
		catch(UserServiceException e) {
			throw new UserServiceException("Empty cart");
		}
	}

	public List<Item> viewOrderItems(int userId) {
		return userDao.viewOrderItems(userId);
	}
	
	public User loginUser(String userEmail, String password) {
		try {
		if(!userDao.isUserPresent(userEmail)) {
			throw new UserServiceException("User Not Registered");
		}
		int id = userDao.findByEmailAndPassword(userEmail, password);
		User user = userDao.findUser(id);
		return user;
		}catch (UserServiceException e) {
			throw new UserServiceException("Incorrect Email or Password");
		}
	}
	
	public String resetUserPassword(String email) {
		if(userDao.isUserPresent(email)) {
			int length=8;
			boolean useLetters = true;
		    boolean useNumbers = true;
		    String generatedPassword = RandomStringUtils.random(length, useLetters, useNumbers);
		    String text="Your new system generated password is "+generatedPassword+". Please change your"
		    		+ " password on FlyBuy.";
	        String subject="FlyBuy Password Reset";
		    emailService.sendEmail(email, text, subject);
		    return generatedPassword;
		}
		else {
			throw new UserServiceException("Email not registered!");
		}
	}
	
	public int findByEmailAndUpdate(String email, String password) {
		return userDao.findByEmailAndUpdate(email, password);
	}
	
	public List<Item> findOrderById(int userId) {
		return userDao.findItemsById(userId);
	}
	
	public int addUserAddress(AddressUser addressUser) {
		return userDao.addUserAddress(addressUser);
	}
}
