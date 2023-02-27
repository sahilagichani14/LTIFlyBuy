package com.lti.controller;

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

import com.lti.dto.CartDto;
import com.lti.dto.ItemDto;
import com.lti.model.Cart;
import com.lti.model.Item;
import com.lti.model.Product;
import com.lti.model.User;
import com.lti.service.CartService;
import com.lti.service.ProductService;
import com.lti.service.UserService;

@RestController
@CrossOrigin
public class CartController {

	@Autowired
	CartService cartService;
	
	@Autowired
	ProductService prodService;
	
	@Autowired
	UserService userService;

	@PostMapping("/add-item-to-cart")
	public int addItemsToCart(@RequestBody CartDto cartDto) {
		Product p = prodService.findAProduct(cartDto.getProductId());
		Item item = new Item();
		item.setProduct(p);
		item.setNoOfItems(cartDto.getNoOfItems());
//		item.setProductName(p.getProductName());
//		item.setProductDescription(p.getProductDescription());
//		item.setProductManufacturerName(p.getProductManufacturerName());
//		item.setProductPrice(p.getProductPrice());
//		item.setProductQuantity(p.getProductQuantity());
//		item.setImageUrl(p.getImageUrl());
		
		Cart cart=userService.findUser(cartDto.getUserId()).getCart();
		//System.out.println("cartid: "+cart.getCartId());
		
		//List<Item> items = new ArrayList<>();
		//items.add(item);
		//cart.setItems(items);
		item.setCart(cart);
		
		//System.out.println(item.getCart().getCartId());

		return cartService.addItemsToCart(item);
	}

	@PostMapping("/empty-cart")
	public int emptyCart(int userId) {
		Cart cart=userService.findUser(userId).getCart();
		return cartService.emptyCart(cart);
	}
	
	@GetMapping("/get-cart-items")
	public List<ItemDto> getCartItems(@RequestParam int userId) {
		List<Item> items = cartService.getCartItems(userId);
		List<ItemDto> itemDto = new ArrayList<>();
		
		for(Item i : items) {
			ItemDto dto= new ItemDto();
			dto.setItemId(i.getItemId());
			dto.setNoOfItems(i.getNoOfItems());
			dto.setProductId(i.getProduct().getProductId());
			dto.setProductName(i.getProduct().getProductName());
			dto.setProductPrice(i.getProduct().getProductPrice());
			dto.setProductQuantity(i.getProduct().getProductQuantity());
			dto.setImageUrl(i.getProduct().getImageUrl());
			dto.setCartId(i.getCart().getCartId());
			
			itemDto.add(dto);
		}
		
		return itemDto;
	}
	
	//my modifications
	
	/*
	@PostMapping("/delete-item")
	public int deleteItemsFromCart(@RequestParam int itemId) {
		//find item by id
		//cartService.deleteItemsFromCart();
	}
	*/
}
