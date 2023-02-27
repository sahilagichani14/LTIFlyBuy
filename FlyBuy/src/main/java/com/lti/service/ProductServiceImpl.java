package com.lti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.dao.ProductDao;
import com.lti.model.Product;


@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductDao productDao;

	@Override
	public int addAProductWithCategory(Product product) {

		return productDao.addAProductWithCategory(product);
	}

	@Override
	public Product findAProduct(int productId) {
		/*if(productDao.findAProduct(productId)==null) {
			//product not found
		}*/
		return productDao.findAProduct(productId);
	}

	@Override
	public List<Product> viewAllProducts() {
		return productDao.viewAllProducts();
	}

	@Override
	public List<Product> viewProductByCategory(String category) {
		return productDao.viewProductByCategory(category);
	}

	@Override
	public List<Product> searchProductByDescription(String description) {
		
		return productDao.searchProductByDescription(description);
	}

	@Override
	public List<Product> viewProductByRetailer(int retailerId) {
		
		return productDao.viewProductByRetailer(retailerId);
	}

	@Override
	public int updateProduct(Product product) {
		
		return productDao.updateProduct(product);
	}

}
