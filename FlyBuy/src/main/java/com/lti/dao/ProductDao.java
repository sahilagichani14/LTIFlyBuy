package com.lti.dao;

import java.util.List;

import com.lti.model.Product;

public interface ProductDao {

	public int addAProductWithCategory(Product product);
	public Product findAProduct(int productId);
	public List<Product> viewAllProducts();
	public List<Product> viewProductByCategory(String category);
	public List<Product> searchProductByDescription(String description);
	public List<Product> viewProductByRetailer(int retailerId);
	public int updateProduct(Product product);
}
