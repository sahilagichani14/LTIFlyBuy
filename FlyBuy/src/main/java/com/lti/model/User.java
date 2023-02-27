package com.lti.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="tbl_user")
public class User {
	
	@Id
	@SequenceGenerator(name = "userSeq", initialValue = 1001, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeq")
	int userId;
	String userName;
	String userEmail;
	String userMobile;
	String userPassword;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	Cart cart;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	List<Order> orders;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	AddressUser address;
	
	public Cart getCart() {
		return cart;
	}
	public AddressUser getAddress() {
		return address;
	}
	public void setAddress(AddressUser address) {
		this.address = address;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	

}
