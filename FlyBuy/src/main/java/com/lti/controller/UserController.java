package com.lti.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lti.dto.AddressDto;
import com.lti.dto.EmailStatus;
import com.lti.dto.ItemDto;
import com.lti.dto.LoginDto;
import com.lti.dto.LoginStatus;
import com.lti.dto.OrderStatus;
import com.lti.dto.Status;
import com.lti.dto.UserDto;
import com.lti.dto.Status.StatusType;
import com.lti.exception.UserServiceException;
import com.lti.model.AddressUser;
import com.lti.model.Item;
import com.lti.model.Order;
import com.lti.model.User;
import com.lti.service.UserService;

@RestController
@CrossOrigin
public class UserController {
	
	@Autowired
	UserService userService;

	@PostMapping(path="/update-user")
	public int updateUser(@RequestBody User user) {

		return userService.updateUser(user);
	}

	@GetMapping(path="/find-user")
	public UserDto findUser(@RequestParam int userId) {
		UserDto userDto=new UserDto();
		User user=userService.findUser(userId);
		userDto.setUserEmail(user.getUserEmail());
		userDto.setUserId(user.getUserId());
		userDto.setUserMobile(user.getUserMobile());
		userDto.setUserName(user.getUserName());
		userDto.setUserPassword(user.getUserPassword());
		return userDto;
	}

	@PostMapping(path="/register-user")
	public Status addUserWithCart(@RequestBody User user) {		
		try {
			userService.addUserWithCart(user);
			Status status = new Status();
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("Registration successful!");
			return status;
		} catch (UserServiceException e) {
			Status status = new Status();
			status.setStatus(StatusType.FAILURE);
			status.setMessage(e.getMessage());
			return status;		
		}
		
	}

	@PostMapping("/add-user-address")
	public int addUserAddress(@RequestBody AddressDto addressDto) {
		AddressUser addressUser=new AddressUser();
		addressUser.setCity(addressDto.getCity());
		addressUser.setState(addressDto.getState());
		addressUser.setStreet(addressDto.getStreet());
		addressUser.setUser(userService.findUser(addressDto.getUserId()));
		return userService.addUserAddress(addressUser);
	}
	
	@PostMapping(path="/login-user")
	public LoginStatus loginUser(@RequestBody LoginDto loginDto) {

		try {
			User user = userService.loginUser(loginDto.getEmail(), loginDto.getPassword());
			LoginStatus loginStatus = new LoginStatus();
			loginStatus.setStatus(StatusType.SUCCESS);
			loginStatus.setMessage("Login Successful!");
			loginStatus.setUserId(user.getUserId());
			loginStatus.setUserName(user.getUserName());
			return loginStatus;
			
		} catch (UserServiceException e) {
			LoginStatus loginStatus = new LoginStatus();
			loginStatus.setStatus(StatusType.FAILURE);
			loginStatus.setMessage(e.getMessage());
			return loginStatus;
		}
	}

	@GetMapping(path="/place-order")
	public OrderStatus placeOrder(@RequestParam("userId") int userId) {
		try {
			Order order=userService.placeOrder(userId);
			OrderStatus orderStatus=new OrderStatus();
			orderStatus.setOrderId(order.getOrderId());
			orderStatus.setStatus(StatusType.SUCCESS);
			orderStatus.setDeliveryDate(order.getDeliveryDate());
			orderStatus.setOrderDate(order.getOrderDate());
			orderStatus.setMessage("Order placed successfully");
			return orderStatus;
		}
		catch(UserServiceException e) {
			OrderStatus orderStatus=new OrderStatus();
			orderStatus.setMessage(e.getMessage());
			orderStatus.setStatus(StatusType.FAILURE);
			return orderStatus;
		}
	}

	@GetMapping("view-order-details")
	public List<ItemDto> viewOrderDetails(@RequestParam("userId") int userId) {
		List<Item> items = userService.viewOrderItems(userId);
		List<ItemDto> itemDtos=new ArrayList<ItemDto>();
		for(Item i:items) {
			if(i.isOrdered()==false) {
				ItemDto itemDto=new ItemDto();
				itemDto.setItemId(i.getItemId());
				itemDto.setNoOfItems(i.getNoOfItems());
				itemDto.setProductName(i.getProduct().getProductName());
				itemDto.setProductPrice(i.getProduct().getProductPrice());
				itemDto.setImageUrl(i.getProduct().getImageUrl());
				itemDtos.add(itemDto);
			}
		}
		return itemDtos;
	}
	
	@GetMapping("/get-user-orders")
	public List<ItemDto> findByOrderById(@RequestParam("userId") int userId) {
		List<Item> items = userService.findOrderById(userId);
		List<ItemDto> itemDtos=new ArrayList<ItemDto>();
		
		for(Item i:items) {
				ItemDto itemDto=new ItemDto();
				itemDto.setItemId(i.getItemId());
				itemDto.setNoOfItems(i.getNoOfItems());
				itemDto.setProductName(i.getProduct().getProductName());
				itemDto.setProductPrice(i.getProduct().getProductPrice());
				itemDto.setImageUrl(i.getProduct().getImageUrl());
				itemDtos.add(itemDto);
			}
		return itemDtos;
	}
	
	@GetMapping(path = "/check-email")
	public EmailStatus resetUserPassword(@RequestParam("emailId") String email) {
		try {
			String generatedPassword=userService.resetUserPassword(email);
			EmailStatus emailStatus=new EmailStatus();
			emailStatus.setStatus(StatusType.SUCCESS);
			emailStatus.setMessage("Email successful!");
			emailStatus.setGeneratedPassword(generatedPassword);
			return emailStatus;
		}
		catch(UserServiceException e) {
			EmailStatus emailStatus=new EmailStatus();
			emailStatus.setStatus(StatusType.FAILURE);
			emailStatus.setMessage(e.getMessage());
			return emailStatus;
		}
	}
	
	@GetMapping(path = "/reset-password")
	public int findByEmailAndUpdate(@RequestParam("emailId") String email, @RequestParam("password") String password) {
		return userService.findByEmailAndUpdate(email, password);
	}
}
