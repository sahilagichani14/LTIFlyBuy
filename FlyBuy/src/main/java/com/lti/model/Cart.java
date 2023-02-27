package com.lti.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tbl_cart")
public class Cart {
	
	@Id
	@SequenceGenerator(name = "cartSeq", initialValue = 1001, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cartSeq")
	int cartId;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	User user;
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	List<Order> orders;
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	List<Item> items;
	
	public int getCartId() {
		return cartId;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

}
